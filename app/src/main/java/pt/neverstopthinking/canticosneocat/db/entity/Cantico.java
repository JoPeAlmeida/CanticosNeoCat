package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Cantico {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome")
    private String nome;

    @ColumnInfo(name = "referencia_biblica")
    private String referenciaBiblica;

    @ColumnInfo(name = "tempo_liturgico")
    private String tempoLiturgico;

    @ColumnInfo(name = "pdf_path")
    private String pdfPath;

    @ColumnInfo(name = "audio_path")
    private String audio_path;

    public Cantico(String nome) {
        this.nome = nome;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    public String getReferenciaBiblica() {
        return referenciaBiblica;
    }

    public void setReferenciaBiblica(String referenciaBiblica) {
        this.referenciaBiblica = referenciaBiblica;
    }

    public String getTempoLiturgico() {
        return tempoLiturgico;
    }

    public void setTempoLiturgico(String tempoLiturgico) {
        this.tempoLiturgico = tempoLiturgico;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getAudio_path() {
        return audio_path;
    }

    public void setAudio_path(String audio_path) {
        this.audio_path = audio_path;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cantico cantico = (Cantico) o;
        return nome.equals(cantico.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
