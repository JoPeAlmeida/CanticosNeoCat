package pt.neverstopthinking.canticosneocat.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

@Dao
public interface EtiquetaDao {

    @Insert
    void insert(Etiqueta etiqueta);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Etiqueta> etiquetas);
}
