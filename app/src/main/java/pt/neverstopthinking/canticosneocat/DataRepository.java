package pt.neverstopthinking.canticosneocat;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.lang.reflect.Array;
import java.util.ArrayList;
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

    public LiveData<List<CanticoEtiquetaJoin>> getEtiquetasOfCantico(String canticoNome) {
        return canticoEtiquetaJoinDao.getEtiquetasForCantico(canticoNome);
    }

    public void insertEtiquetaOfCantico(String etiquetaNome, String canticoNome) {
        new InsertEtiquetaAsyncTask(this).execute(new CanticoEtiquetaJoin(canticoNome, etiquetaNome));
    }

    public void updateEtiquetaOfCantico(String oldEtiquetaNome, String etiquetaNome, String canticoNome) {
        String[] array = new String[]{oldEtiquetaNome, etiquetaNome, canticoNome};
        new UpdateEtiquetaAsyncTask(this).execute(array);
    }

    public void deleteEtiquetaOfCantico(String etiquetaNome, String canticoNome) {
        String[] array = new String[]{etiquetaNome, canticoNome};
        new DeleteEtiquetaAsyncTask(this).execute(array);
    }

    private static class InsertEtiquetaAsyncTask extends AsyncTask<CanticoEtiquetaJoin, Void, Void> {
        private EtiquetaDao etiquetaDao;
        private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;

        private InsertEtiquetaAsyncTask(DataRepository repository) {
            etiquetaDao = repository.etiquetaDao;
            canticoEtiquetaJoinDao = repository.canticoEtiquetaJoinDao;
        }

        @Override
        protected Void doInBackground(CanticoEtiquetaJoin... canticoEtiquetaJoins) {
            etiquetaDao.insert(new Etiqueta(canticoEtiquetaJoins[0].getEtiquetaNome()));
            canticoEtiquetaJoinDao.insert(new CanticoEtiquetaJoin(canticoEtiquetaJoins[0].getCanticoNome(), canticoEtiquetaJoins[0].getEtiquetaNome()));
            return null;
        }
    }

    private static class UpdateEtiquetaAsyncTask extends AsyncTask<String, Void, Void> {
        private EtiquetaDao etiquetaDao;
        private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;

        private UpdateEtiquetaAsyncTask(DataRepository repository) {
            etiquetaDao = repository.etiquetaDao;
            canticoEtiquetaJoinDao = repository.canticoEtiquetaJoinDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            canticoEtiquetaJoinDao.delete(new CanticoEtiquetaJoin(strings[2], strings[0]));
            if (canticoEtiquetaJoinDao.getCountByEtiquetaNome(strings[0]) == 0) {
                etiquetaDao.delete(new Etiqueta(strings[0]));
            }
            if (canticoEtiquetaJoinDao.getCountByEtiquetaNome(strings[1]) == 0) {
                etiquetaDao.insert(new Etiqueta(strings[1]));
            }
            canticoEtiquetaJoinDao.insert(new CanticoEtiquetaJoin(strings[2], strings[1]));
            return null;
        }
    }

    private static class DeleteEtiquetaAsyncTask extends AsyncTask<String, Void, Void> {
        private EtiquetaDao etiquetaDao;
        private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;

        private DeleteEtiquetaAsyncTask(DataRepository repository) {
            etiquetaDao = repository.etiquetaDao;
            canticoEtiquetaJoinDao = repository.canticoEtiquetaJoinDao;
        }

        @Override
        protected Void doInBackground(String... strings) {
            canticoEtiquetaJoinDao.delete(new CanticoEtiquetaJoin(strings[1], strings[0]));
            if (canticoEtiquetaJoinDao.getCountByEtiquetaNome(strings[0]) == 0) etiquetaDao.delete(new Etiqueta(strings[0]));
            return null;
        }
    }
}
