package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Etiqueta {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;

    public Etiqueta(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }
}
