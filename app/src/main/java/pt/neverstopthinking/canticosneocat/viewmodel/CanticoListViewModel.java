package pt.neverstopthinking.canticosneocat.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.neverstopthinking.canticosneocat.DataRepository;
import pt.neverstopthinking.canticosneocat.db.DataGenerator;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;

public class CanticoListViewModel extends AndroidViewModel {

    private final DataRepository repository;
    private final LiveData<List<Cantico>> canticos;

    public CanticoListViewModel(Application application) {
        super(application);
        repository = new DataRepository(application);
        //repository.generateCanticos(DataGenerator.generateCanticos(application));
        canticos = repository.getCanticos();
    }

    public LiveData<List<Cantico>> getCanticos() {
        return canticos;
    }
}
