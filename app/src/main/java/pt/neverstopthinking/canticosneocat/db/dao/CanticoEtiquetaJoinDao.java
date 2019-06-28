package pt.neverstopthinking.canticosneocat.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

@Dao
public interface CanticoEtiquetaJoinDao {

    @Insert
    void insert(CanticoEtiquetaJoin canticoEtiquetaJoin);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CanticoEtiquetaJoin> canticoEtiquetaJoins);

    @Query("SELECT * FROM cantico_etiqueta_join")
    LiveData<List<CanticoEtiquetaJoin>> getAll();

    @Query("SELECT * FROM Etiqueta INNER JOIN cantico_etiqueta_join " +
            "ON Etiqueta.nome=cantico_etiqueta_join.etiquetaNome " +
            "WHERE cantico_etiqueta_join.canticoNome=:canticoNome")
    LiveData<List<Etiqueta>> getEtiquetasForCantico(final String canticoNome);
}
