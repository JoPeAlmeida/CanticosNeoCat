package pt.neverstopthinking.canticosneocat.ui.AZSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.ui.Cantico.CanticoActivity;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoListViewModel;

public class AZActivity extends AppCompatActivity implements AZCanticoAdapter.OnCanticoClickListener, LifecycleOwner {

    private CanticoListViewModel canticoListViewModel;
    private AZCanticoAdapter azCanticoAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_az);

        RecyclerView recyclerView = findViewById(R.id.az_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        azCanticoAdapter = new AZCanticoAdapter();
        recyclerView.setAdapter(azCanticoAdapter);

        searchView = findViewById(R.id.az_search);
        searchView.setIconifiedByDefault(true);
        searchView.setOnCloseListener(() -> {
            ImageView iconSearch = findViewById(R.id.az_search_icon);
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
                azCanticoAdapter.getFilter().filter(s);
                return false;
            }
        });

        canticoListViewModel = new ViewModelProvider(this).get(CanticoListViewModel.class);
        canticoListViewModel.getCanticos().observe(this, canticos -> {
            azCanticoAdapter.updateCanticos(canticos);
        });
        azCanticoAdapter.setOnCanticoClickListener(this);
    }

    public void activateSearch(View view) {
        searchView.setVisibility(View.VISIBLE);
        searchView.setIconified(false);
        view.setVisibility(View.GONE);
    }

    @Override
    public void launchCantico(String canticoNome) {
        startActivity(new Intent(this, CanticoActivity.class).putExtra("canticoNome", canticoNome));
    }
}
