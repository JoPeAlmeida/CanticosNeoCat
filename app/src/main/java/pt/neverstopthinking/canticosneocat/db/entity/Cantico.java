package pt.neverstopthinking.canticosneocat.db.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Cantico {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nome")
    public String nome;

    @ColumnInfo(name = "referencia_biblica")
    public String referenciaBiblica;

    @ColumnInfo(name = "tempo_liturgico")
    public String tempoLiturgico;

    @ColumnInfo(name = "pdf_path")
    public String pdfPath;

    @ColumnInfo(name = "audio_path")
    public String audio_path;

}
