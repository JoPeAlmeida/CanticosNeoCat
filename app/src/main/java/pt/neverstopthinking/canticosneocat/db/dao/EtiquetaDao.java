package pt.neverstopthinking.canticosneocat.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

@Dao
public interface EtiquetaDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Etiqueta etiqueta);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Etiqueta> etiquetas);

    @Delete
    void delete(Etiqueta etiqueta);

    @Query("SELECT COUNT(*) FROM Etiqueta WHERE nome=:nome")
    int getCountByNome(String nome);
}
