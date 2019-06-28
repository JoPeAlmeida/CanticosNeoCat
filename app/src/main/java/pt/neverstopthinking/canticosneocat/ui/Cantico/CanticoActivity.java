package pt.neverstopthinking.canticosneocat.ui.Cantico;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;
import pt.neverstopthinking.canticosneocat.ui.AZSearch.AZCanticoAdapter;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoViewModel;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoViewModelFactory;

public class CanticoActivity extends AppCompatActivity implements LifecycleOwner {

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
        canticoViewModel.getEtiquetas().observe(this, new Observer<List<Etiqueta>>() {
            @Override
            public void onChanged(List<Etiqueta> etiquetas) {
                etiquetasAdapter.updateEtiquetas(etiquetas);
            }
        });
        canticoViewModel.getCantico().observe(this, new Observer<Cantico>() {
            @Override
            public void onChanged(Cantico cantico) {
                txtCanticoNome.setText(cantico.getNome());
                txtCanticoReferenciaBiblica.setText(cantico.getReferenciaBiblica());
                txtCanticoTempoLiturgico.setText(cantico.getTempoLiturgico());
                Resources resources = getResources();
                canticoImage.setImageResource(resources.getIdentifier(cantico.getPdfPath(), "drawable", getPackageName()));
                playerAdapter.loadMedia(resources.getIdentifier(cantico.getAudio_path(), "raw", getPackageName()));
            }
        });

        initializeSeekbar();
        initializePlaybackController();

        canticoImage = (ImageView) findViewById(R.id.cantico_image);

        canticoImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new PhotoFullPopupWindow(getApplicationContext(), R.layout.popup_image_full, v, null, null);
            }
        });
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

        audioPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerAdapter.pause();
                view.setVisibility(View.GONE);
                audioPlay.setVisibility(View.VISIBLE);
            }
        });
        audioPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playerAdapter.play();
                view.setVisibility(View.GONE);
                audioPause.setVisibility(View.VISIBLE);
            }
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
