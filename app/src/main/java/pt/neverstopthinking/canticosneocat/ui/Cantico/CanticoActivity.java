package pt.neverstopthinking.canticosneocat.ui.Cantico;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoViewModel;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoViewModelFactory;

public class CanticoActivity extends AppCompatActivity implements EtiquetasAdapter.LongClickListener, LifecycleOwner {

    private ImageView canticoImage;
    private TextView txtCanticoNome;
    private TextView txtCanticoReferenciaBiblica;
    private TextView txtCanticoTempoLiturgico;

    private CanticoViewModel canticoViewModel;
    private EtiquetasAdapter etiquetasAdapter;

    private  ImageView audioPlay;
    private ImageView audioPause;
    private SeekBar audioSeekBar;
    private PlayerAdapter playerAdapter;
    private boolean mUserIsSeeking = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantico);
        initializeUI();

        Intent intent = getIntent();
        String canticoNome = intent.getExtras().getString("canticoNome");

        RecyclerView recyclerView = findViewById(R.id.cantico_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        etiquetasAdapter = new EtiquetasAdapter();
        recyclerView.setAdapter(etiquetasAdapter);
        canticoViewModel = ViewModelProviders.of(this, new CanticoViewModelFactory(this.getApplication(), canticoNome)).get(CanticoViewModel.class);
        canticoViewModel.getEtiquetas().observe(this, etiquetas -> etiquetasAdapter.submitList(etiquetas));
        canticoViewModel.getCantico().observe(this, cantico -> {
            txtCanticoNome.setText(cantico.getNome());
            txtCanticoReferenciaBiblica.setText(cantico.getReferenciaBiblica());
            txtCanticoTempoLiturgico.setText(cantico.getTempoLiturgico());
            Resources resources = getResources();
            canticoImage.setImageResource(resources.getIdentifier(cantico.getPdfPath(), "drawable", getPackageName()));
            playerAdapter.loadMedia(resources.getIdentifier(cantico.getAudio_path(), "raw", getPackageName()));
        });

        initializeSeekbar();
        initializePlaybackController();

        canticoImage = findViewById(R.id.cantico_image);

        canticoImage.setOnClickListener(v -> new PhotoFullPopupWindow(getApplicationContext(), R.layout.popup_image_full, v, null, null));

        FloatingActionButton addEtiqueta = findViewById(R.id.cantico_add_etiqueta);
        addEtiqueta.setOnClickListener(view -> showAddEtiquetaDialog());

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                canticoViewModel.deleteEtiqueta(etiquetasAdapter.getEtiquetaAt(viewHolder.getAdapterPosition()).getEtiquetaNome());
            }
        }).attachToRecyclerView(recyclerView);
        etiquetasAdapter.setLongClickListener(this);
    }

    private void showAddEtiquetaDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EtiquetaFragment etiquetaFragment = EtiquetaFragment.newInstance("add", null);
        etiquetaFragment.show(fragmentManager, null);
    }

    @Override
    public void showEditEtiquetaDialog(String etiquetaNome) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        EtiquetaFragment etiquetaFragment = EtiquetaFragment.newInstance("edit", etiquetaNome);
        etiquetaFragment.show(fragmentManager, null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //playerAdapter.loadMedia(R.raw.aleluia_louvai_o_senhor);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isChangingConfigurations() && playerAdapter.isPlaying()) {
        } else {
            playerAdapter.release();
        }
    }

    private void initializeUI() {
        txtCanticoNome = findViewById(R.id.cantico_title);
        txtCanticoReferenciaBiblica = findViewById(R.id.cantico_rb);
        txtCanticoTempoLiturgico = findViewById(R.id.cantico_tl);
        audioPlay = findViewById(R.id.audio_play);
        audioPause = findViewById(R.id.audio_pause);
        audioSeekBar = findViewById(R.id.audio_seekbar);

        audioPause.setOnClickListener(view -> {
                playerAdapter.pause();
                view.setVisibility(View.GONE);
                audioPlay.setVisibility(View.VISIBLE);
        });
        audioPlay.setOnClickListener(view -> {
            playerAdapter.play();
            view.setVisibility(View.GONE);
            audioPause.setVisibility(View.VISIBLE);
        });
    }

    private void initializePlaybackController() {
        MediaPlayerHolder mediaPlayerHolder = new MediaPlayerHolder(this);
        mediaPlayerHolder.setPlaybackInfoListener(new PlaybackListener());
        playerAdapter = mediaPlayerHolder;
    }

    private void initializeSeekbar() {
        audioSeekBar.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    int userSelectedPosition = 0;

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = true;
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            userSelectedPosition = progress;
                        }
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        mUserIsSeeking = false;
                        playerAdapter.seekTo(userSelectedPosition);
                    }
                });
    }

    public class PlaybackListener extends PlaybackInfoListener {

        @Override
        public void onDurationChanged(int duration) {
            audioSeekBar.setMax(duration);
        }

        @Override
        public void onPositionChanged(int position) {
            if (!mUserIsSeeking) {
                audioSeekBar.setProgress(position, true);
            }
        }

        @Override
        public void onStateChanged(@State int state) {
            String stateToString = PlaybackInfoListener.convertStateToString(state);
            onLogUpdated(String.format("onStateChanged(%s)", stateToString));
        }

        @Override
        public void onPlaybackCompleted() {
        }
    }
}
