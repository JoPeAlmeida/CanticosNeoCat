package pt.neverstopthinking.canticosneocat.ui.EtiquetasSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.ui.AZSearch.AZCanticoAdapter;
import pt.neverstopthinking.canticosneocat.ui.Cantico.CanticoActivity;
import pt.neverstopthinking.canticosneocat.viewmodel.EtiquetasViewModel;

public class EtiquetasActivity extends AppCompatActivity implements EtiquetasAdapter.OnEtiquetaClickListener, AZCanticoAdapter.OnCanticoClickListener, LifecycleOwner {

    private EtiquetasViewModel etiquetasViewModel;
    private EtiquetasAdapter etiquetasAdapter;
    private SearchView searchView;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etiquetas);

        recyclerView = findViewById(R.id.etiquetas_main_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        etiquetasAdapter = new EtiquetasAdapter(this);
        recyclerView.setAdapter(etiquetasAdapter);

        searchView = findViewById(R.id.etiquetas_search);
        searchView.setIconifiedByDefault(true);
        searchView.setOnCloseListener(() -> {
            ImageView iconSearch = findViewById(R.id.etiquetas_search_icon);
            iconSearch.setVisibility(View.VISIBLE);
            searchView.setVisibility(View.GONE);
            return true;
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                etiquetasAdapter.getFilter().filter(s);
                return false;
            }
        });
        etiquetasViewModel = ViewModelProviders.of(this).get(EtiquetasViewModel.class);
        etiquetasViewModel.getEtiquetas().observe(this, etiquetaCanticoPairs -> {

            etiquetasAdapter.updateEtiquetas(CanticoEtiquetaJoin.groupCanticos(etiquetaCanticoPairs));
        });
        etiquetasAdapter.setOnCanticoClickListener(this::launchCantico);
        etiquetasAdapter.setOnEtiquetaClickListener(this::toggleChildRecyclerView);
    }

    public void activateSearch(View view) {
        searchView.setVisibility(View.VISIBLE);
        searchView.setIconified(false);
        view.setVisibility(View.GONE);
    }

    @Override
    public void launchCantico(String nome) {
        startActivity(new Intent(this, CanticoActivity.class).putExtra("canticoNome", nome));
    }

    @Override
    public void toggleChildRecyclerView(int position) {
        EtiquetasAdapter.EtiquetaHolder etiquetaHolder = (EtiquetasAdapter.EtiquetaHolder) recyclerView.findViewHolderForAdapterPosition(position);
        if (etiquetaHolder != null) {
            if (etiquetaHolder.recyclerView.getVisibility() == View.VISIBLE) {
                etiquetaHolder.recyclerView.setVisibility(View.GONE);
            } else {
                etiquetaHolder.recyclerView.setVisibility(View.VISIBLE);
            }
        }
    }
}
