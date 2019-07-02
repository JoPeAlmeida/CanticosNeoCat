package pt.neverstopthinking.canticosneocat.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.Cantico;

@Dao
public interface CanticoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Cantico cantico);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(List<Cantico> canticos);

    @Query("SELECT * FROM Cantico")
    LiveData<List<Cantico>> getAll();

    @Query("SELECT * FROM Cantico WHERE nome = :nome")
    LiveData<Cantico> getCantico(String nome);

    @Query("SELECT * FROM Cantico WHERE nome LIKE :query")
    LiveData<List<Cantico>> searchCanticos(String query);
}
