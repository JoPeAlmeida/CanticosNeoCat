package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Etiqueta {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome")
    public String nome;
}
