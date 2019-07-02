package pt.neverstopthinking.canticosneocat.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.AppExecutors;
import pt.neverstopthinking.canticosneocat.db.dao.CanticoDao;
import pt.neverstopthinking.canticosneocat.db.dao.CanticoEtiquetaJoinDao;
import pt.neverstopthinking.canticosneocat.db.dao.EtiquetaDao;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

@Database(entities = {Cantico.class, Etiqueta.class, CanticoEtiquetaJoin.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final String DATABASE_NAME = "app-database";

    public abstract CanticoDao canticoDao();
    public abstract EtiquetaDao etiquetaDao();
    public abstract CanticoEtiquetaJoinDao canticoEtiquetaJoinDao();

    public synchronized static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            new PopulateDbAsyncTask(instance).execute(DataGenerator.generateData(context));
                        }
                    })
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    private static class PopulateDbAsyncTask extends AsyncTask<DataGenerator.DataHolder, Void, Void> {
        private CanticoDao canticoDao;
        private EtiquetaDao etiquetaDao;
        private CanticoEtiquetaJoinDao canticoEtiquetaJoinDao;

        private PopulateDbAsyncTask(AppDatabase database) {
            canticoDao = database.canticoDao();
            etiquetaDao = database.etiquetaDao();
            canticoEtiquetaJoinDao = database.canticoEtiquetaJoinDao();
        }
        @Override
        protected Void doInBackground(DataGenerator.DataHolder... dataHolders) {
            canticoDao.insertAll(dataHolders[0].canticos);
            etiquetaDao.insertAll(dataHolders[0].etiquetas);
            canticoEtiquetaJoinDao.insertAll(dataHolders[0].canticoEtiquetaJoins);
            return null;
        }
    }
}
