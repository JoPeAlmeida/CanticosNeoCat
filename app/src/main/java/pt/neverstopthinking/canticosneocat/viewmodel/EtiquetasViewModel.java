package pt.neverstopthinking.canticosneocat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import pt.neverstopthinking.canticosneocat.DataRepository;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;

public class EtiquetasViewModel extends AndroidViewModel {

    private final DataRepository repository;
    private final LiveData<List<CanticoEtiquetaJoin.EtiquetaCanticoPair>> etiquetas;

    public EtiquetasViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        etiquetas = repository.getEtiquetasAndTheirCanticos();
    }

    public LiveData<List<CanticoEtiquetaJoin.EtiquetaCanticoPair>> getEtiquetas() {
        return etiquetas;
    }
}
