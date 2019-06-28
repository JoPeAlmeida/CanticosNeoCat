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
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

public class DataRepository {

    private static DataRepository instance;
    private CanticoDao canticoDao;
    private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;
    private LiveData<List<Cantico>> canticos;

    public DataRepository(Application application) {
        AppDatabase database = AppDatabase.getInstance(application);
        canticoDao = database.canticoDao();
        canticos = canticoDao.getAll();
        canticoEtiquetaJoinDao = database.canticoEtiquetaJoinDao();
    }

    /*public void generateCanticos(List<Cantico> canticos) {
        new GenerateCanticosAsyncTask(canticoDao).execute(canticos);
    }*/

    public LiveData<List<Cantico>> getCanticos() {
        return canticos;
    }

    public LiveData<Cantico> getCantico(final String nome) {
        return canticoDao.getCantico(nome);
    }

    public LiveData<List<Cantico>> searchCanticos(String query) {
        return canticoDao.searchCanticos(query);
    }

    public LiveData<List<Etiqueta>> getEtiquetasOfCantico(String canticoNome) {
        return canticoEtiquetaJoinDao.getEtiquetasForCantico(canticoNome);
    }

    /*private static class GenerateCanticosAsyncTask extends AsyncTask<List<Cantico>, Void, Void> {
        private CanticoDao canticoDao;

        private  GenerateCanticosAsyncTask(CanticoDao canticoDao) {
            this.canticoDao = canticoDao;
        }
        @Override
        protected Void doInBackground(List<Cantico>... lists) {
            canticoDao.insertAll(lists[0]);
            return null;
        }
    }*/
}
