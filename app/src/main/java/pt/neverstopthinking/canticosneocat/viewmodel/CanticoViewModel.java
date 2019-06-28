package pt.neverstopthinking.canticosneocat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.databinding.ObservableField;

import java.util.List;

import pt.neverstopthinking.canticosneocat.DataRepository;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

public class CanticoViewModel extends AndroidViewModel {

    private final DataRepository repository;
    private final LiveData<List<Etiqueta>> etiquetas;
    private final LiveData<Cantico> cantico;

    public CanticoViewModel(@NonNull Application application, String canticoNome) {
        super(application);
        repository = new DataRepository(application);
        etiquetas = repository.getEtiquetasOfCantico(canticoNome);
        cantico = repository.getCantico(canticoNome);
    }

    public LiveData<List<Etiqueta>> getEtiquetas() {
        return etiquetas;
    }

    public LiveData<Cantico> getCantico() {return cantico;}

    public void addEtiqueta(String etiquetaNome) {
        repository.insertEtiquetaOfCantico(etiquetaNome, cantico.getValue().getNome());
    }
}
