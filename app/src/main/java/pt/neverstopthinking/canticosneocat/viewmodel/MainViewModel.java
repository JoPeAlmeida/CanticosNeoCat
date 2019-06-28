package pt.neverstopthinking.canticosneocat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import pt.neverstopthinking.canticosneocat.DataRepository;

public class MainViewModel extends AndroidViewModel {

    private DataRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
    }
}
