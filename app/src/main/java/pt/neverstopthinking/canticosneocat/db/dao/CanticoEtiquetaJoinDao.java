package pt.neverstopthinking.canticosneocat.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

@Dao
public interface CanticoEtiquetaJoinDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CanticoEtiquetaJoin canticoEtiquetaJoin);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void update(CanticoEtiquetaJoin canticoEtiquetaJoin);

    @Delete
    void delete(CanticoEtiquetaJoin canticoEtiquetaJoin);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<CanticoEtiquetaJoin> canticoEtiquetaJoins);

    @Query("SELECT * FROM cantico_etiqueta_join ORDER BY etiquetaNome")
    LiveData<List<CanticoEtiquetaJoin>> getAll();

    @Query("SELECT * FROM cantico_etiqueta_join WHERE cantico_etiqueta_join.canticoNome=:canticoNome")
    LiveData<List<CanticoEtiquetaJoin>> getEtiquetasForCantico(final String canticoNome);

    @Query("SELECT COUNT(*) FROM cantico_etiqueta_join WHERE etiquetaNome=:etiquetaNome")
    int getCountByEtiquetaNome(String etiquetaNome);
}
