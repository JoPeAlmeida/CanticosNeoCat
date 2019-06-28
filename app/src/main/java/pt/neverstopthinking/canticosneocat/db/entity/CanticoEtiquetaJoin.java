package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import androidx.annotation.NonNull;

@Entity(tableName="cantico_etiqueta_join",
        primaryKeys = {"canticoNome", "etiquetaNome"},
        foreignKeys = {
                @ForeignKey(entity = Cantico.class,
                        parentColumns = "nome",
                        childColumns = "canticoNome"),
                @ForeignKey(entity = Etiqueta.class,
                        parentColumns = "nome",
                        childColumns = "etiquetaNome")
        })
public class CanticoEtiquetaJoin {

    @NonNull
    private String canticoNome;
    @NonNull
    private String etiquetaNome;

    public CanticoEtiquetaJoin(@NonNull String canticoNome, @NonNull String etiquetaNome) {
        this.canticoNome = canticoNome;
        this.etiquetaNome = etiquetaNome;
    }

    @NonNull
    public String getCanticoNome() {
        return canticoNome;
    }

    public void setCanticoNome(@NonNull String canticoNome) {
        this.canticoNome = canticoNome;
    }

    @NonNull
    public String getEtiquetaNome() {
        return etiquetaNome;
    }

    public void setEtiquetaNome(@NonNull String etiquetaNome) {
        this.etiquetaNome = etiquetaNome;
    }
}
