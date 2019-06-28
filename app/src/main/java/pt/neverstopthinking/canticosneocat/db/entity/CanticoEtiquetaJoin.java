package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.room.Entity;
import androidx.room.ForeignKey;

import androidx.annotation.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

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
@Data
@NoArgsConstructor
public class CanticoEtiquetaJoin {

    @NonNull
    public String canticoNome;
    @NonNull
    public String etiquetaNome;
}
