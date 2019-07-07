package pt.neverstopthinking.canticosneocat.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;

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

    @Query("SELECT etiquetaNome, canticoNome FROM cantico_etiqueta_join ORDER BY etiquetaNome")
    LiveData<List<CanticoEtiquetaJoin>> getAll();

    @Query("SELECT * FROM cantico_etiqueta_join WHERE cantico_etiqueta_join.canticoNome=:canticoNome")
    LiveData<List<CanticoEtiquetaJoin>> getEtiquetasForCantico(final String canticoNome);

    @Query("SELECT COUNT(*) FROM cantico_etiqueta_join WHERE etiquetaNome=:etiquetaNome")
    int getCountByEtiquetaNome(String etiquetaNome);

    @Query("SELECT Etiqueta.nome as etiqueta_nome, Cantico.nome FROM Etiqueta " +
            "LEFT OUTER JOIN cantico_etiqueta_join ON etiquetaNome = Etiqueta.nome " +
            "LEFT OUTER JOIN Cantico ON canticoNome = Cantico.nome ORDER BY Etiqueta.nome")
    LiveData<List<CanticoEtiquetaJoin.EtiquetaCanticoPair>> getEtiquetasAndTheirCanticos();

    @Query("SELECT Cantico.* FROM Cantico, cantico_etiqueta_join WHERE Cantico.nome = canticoNome AND etiquetaNome =:nome ORDER BY Cantico.nome")
    LiveData<List<Cantico>> getCanticosOfEtiqueta(String nome);
}
