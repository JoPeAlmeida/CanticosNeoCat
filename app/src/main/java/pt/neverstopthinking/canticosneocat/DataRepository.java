package pt.neverstopthinking.canticosneocat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.AppDatabase;
import pt.neverstopthinking.canticosneocat.db.dao.CanticoDao;
import pt.neverstopthinking.canticosneocat.db.dao.CanticoEtiquetaJoinDao;
import pt.neverstopthinking.canticosneocat.db.dao.EtiquetaDao;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

public class DataRepository {

    private static DataRepository instance;
    private CanticoDao canticoDao;
    private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;
    private EtiquetaDao etiquetaDao;
    private LiveData<List<Cantico>> canticos;

    public DataRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        canticoDao = database.canticoDao();
        canticos = canticoDao.getAll();
        etiquetaDao = database.etiquetaDao();
        canticoEtiquetaJoinDao = database.canticoEtiquetaJoinDao();
    }

    public LiveData<List<Cantico>> getCanticos() {
        return canticos;
    }

    public LiveData<Cantico> getCantico(final String nome) {
        return canticoDao.getCantico(nome);
    }

    public LiveData<List<Etiqueta>> getEtiquetasOfCantico(String canticoNome) {
        return canticoEtiquetaJoinDao.getEtiquetasForCantico(canticoNome);
    }

    public void insertEtiquetaOfCantico(String etiquetaNome, String canticoNome) {
        new InsertEtiquetaAsyncTask(this).execute(new String[]{etiquetaNome, canticoNome});
    }

    private static class InsertEtiquetaAsyncTask extends AsyncTask<String, Void, Void> {
        private EtiquetaDao etiquetaDao;
        private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;

        private InsertEtiquetaAsyncTask(DataRepository repository) {
            etiquetaDao = repository.etiquetaDao;
            canticoEtiquetaJoinDao = repository.canticoEtiquetaJoinDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            etiquetaDao.insert(new Etiqueta(strings[0]));
            canticoEtiquetaJoinDao.insert(new CanticoEtiquetaJoin(strings[1], strings[0]));
            return null;
        }
    }
}
