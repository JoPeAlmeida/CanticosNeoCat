package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Etiqueta etiqueta = (Etiqueta) o;
        return nome.equals(etiqueta.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
