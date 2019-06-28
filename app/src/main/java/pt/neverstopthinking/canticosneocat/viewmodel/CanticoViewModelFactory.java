package pt.neverstopthinking.canticosneocat.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CanticoViewModelFactory implements ViewModelProvider.Factory {
    private Application application;
    private String canticoNome;

    public CanticoViewModelFactory(Application application, String canticoNome) {
        this.application = application;
        this.canticoNome = canticoNome;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CanticoViewModel(application, canticoNome);
    }
}
