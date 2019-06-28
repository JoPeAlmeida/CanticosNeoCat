package pt.neverstopthinking.canticosneocat.ui.AZSearch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.SearchView;

import android.widget.ImageView;

import java.util.List;

import pt.neverstopthinking.canticosneocat.R;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.ui.Cantico.CanticoActivity;
import pt.neverstopthinking.canticosneocat.viewmodel.CanticoListViewModel;

public class AZActivity extends AppCompatActivity implements AZCanticoAdapter.ClickListener, LifecycleOwner {

    private CanticoListViewModel canticoListViewModel;
    private AZCanticoAdapter azCanticoAdapter;
    private SearchView searchView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_az);

        RecyclerView recyclerView = findViewById(R.id.az_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        azCanticoAdapter = new AZCanticoAdapter(this);
        recyclerView.setAdapter(azCanticoAdapter);

        searchView = findViewById(R.id.az_search);
        searchView.setIconifiedByDefault(true);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                ImageView iconSearch = findViewById(R.id.search_icon);
                iconSearch.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);
                return true;
            }
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

        canticoListViewModel = ViewModelProviders.of(this).get(CanticoListViewModel.class);
        canticoListViewModel.getCanticos().observe(this, new Observer<List<Cantico>>() {
            @Override
            public void onChanged(List<Cantico> canticos) {
                //update RecyclerView
                azCanticoAdapter.updateCanticos(canticos);
            }
        });
    }

    public void activateSearch(View view) {
        searchView.setVisibility(View.VISIBLE);
        searchView.setIconified(false);
        view.setVisibility(View.GONE);
    }

    @Override
    public void launchIntent(String canticoNome) {
        startActivity(new Intent(this, CanticoActivity.class).putExtra("canticoNome", canticoNome));
    }
}
