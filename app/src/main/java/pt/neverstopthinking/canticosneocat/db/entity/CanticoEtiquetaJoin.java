package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName="cantico_etiqueta_join",
        primaryKeys = {"canticoNome", "etiquetaNome"},
        foreignKeys = {
                @ForeignKey(entity = Cantico.class,
                        parentColumns = "nome",
                        childColumns = "canticoNome"),
                @ForeignKey(entity = Etiqueta.class,
                        parentColumns = "nome",
                        childColumns = "etiquetaNome",
                        onDelete = CASCADE)
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

    public static class EtiquetaCanticoPair {
        @Embedded(prefix = "etiqueta_")
        private Etiqueta etiqueta;

        @Embedded
        private Cantico cantico;

        public EtiquetaCanticoPair(Cantico cantico, Etiqueta etiqueta) {
            this.cantico = cantico;
            this.etiqueta = etiqueta;
        }

        public Cantico getCantico() {
            return cantico;
        }

        public Etiqueta getEtiqueta() {
            return etiqueta;
        }
    }

    public static class EtiquetaCanticos implements Comparable<EtiquetaCanticos> {

        private Etiqueta etiqueta;
        private List<Cantico> canticos;
        private boolean expanded;

        public EtiquetaCanticos(Etiqueta etiqueta, List<Cantico> canticos) {
            this.etiqueta = etiqueta;
            this.canticos = canticos;
            expanded = true;
        }

        public Etiqueta getEtiqueta() {
            return etiqueta;
        }

        public List<Cantico> getCanticos() {
            return canticos;
        }

        public boolean isExpanded() {return expanded; }

        public void setExpanded(boolean expanded) {
            this.expanded = expanded;
        }

        @Override
        public int compareTo(EtiquetaCanticos etiquetaCanticos) {
            if (getEtiqueta() == null || etiquetaCanticos.getEtiqueta() == null) return 0;
            return getEtiqueta().getNome().compareTo(etiquetaCanticos.getEtiqueta().getNome());
        }
    }

    public static List<EtiquetaCanticos> groupCanticos(List<EtiquetaCanticoPair> etiquetaCanticoPairs) {
        Map<Etiqueta, List<Cantico>> map = etiquetaCanticoPairs.stream().collect(
                Collectors.groupingBy(
                        EtiquetaCanticoPair::getEtiqueta, Collectors.mapping(EtiquetaCanticoPair::getCantico, Collectors.toList())));
        List<EtiquetaCanticos> etiquetaCanticosList = map.entrySet().stream().map(e -> new EtiquetaCanticos(e.getKey(), e.getValue())).collect(Collectors.toList());
        etiquetaCanticosList.sort(EtiquetaCanticos::compareTo);
        return etiquetaCanticosList;
    }
}
