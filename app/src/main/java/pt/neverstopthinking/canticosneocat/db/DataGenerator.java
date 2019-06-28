package pt.neverstopthinking.canticosneocat.db;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import pt.neverstopthinking.canticosneocat.db.entity.Cantico;
import pt.neverstopthinking.canticosneocat.db.entity.CanticoEtiquetaJoin;
import pt.neverstopthinking.canticosneocat.db.entity.Etiqueta;

public class DataGenerator {

    private static final String CANTICOS_FILE_PATH = "canticos_load_data.xml";
    private static final String ETIQUETAS_FILE_PATH = "etiquetas_load_data.xml";

    public static DataHolder generateData(final Context context) {
        List<Cantico> canticos = new ArrayList<>();
        generateCanticos(context, canticos);
        List<Etiqueta> etiquetas = new ArrayList<>();
        List<CanticoEtiquetaJoin> canticoEtiquetaJoins = new ArrayList<>();
        generateEtiquetas(context, etiquetas, canticoEtiquetaJoins);

        return new DataHolder(canticos, etiquetas, canticoEtiquetaJoins);
    }

    private static void generateEtiquetas(final Context context, List<Etiqueta> etiquetas, List<CanticoEtiquetaJoin> canticoEtiquetaJoins) {
        Etiqueta etiqueta;
        CanticoEtiquetaJoin canticoEtiquetaJoin;

        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(context.getAssets().open(ETIQUETAS_FILE_PATH), null);
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("etiqueta")) {
                            String attNome = parser.getAttributeValue(null, "nome");
                            String attCantico = parser.getAttributeValue(null, "cantico");
                            if (!attNome.isEmpty() && !attCantico.isEmpty()) {
                                etiqueta = new Etiqueta(attNome);
                                canticoEtiquetaJoin = new CanticoEtiquetaJoin(attCantico, attNome);
                                etiquetas.add(etiqueta);
                                canticoEtiquetaJoins.add(canticoEtiquetaJoin);
                            }
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void generateCanticos(final Context context, List<Cantico> canticos) {
        Cantico cantico;

        XmlPullParserFactory factory = null;
        XmlPullParser parser = null;
        try {
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(context.getAssets().open(CANTICOS_FILE_PATH), null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagname = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagname.equalsIgnoreCase("cantico")) {
                            cantico = new Cantico(parser.getAttributeValue(null, "nome"));
                            if (!parser.getAttributeValue(null, "referencia_biblica").isEmpty()) {
                                cantico.setReferenciaBiblica(parser.getAttributeValue(null, "referencia_biblica"));
                            }
                            if (!parser.getAttributeValue(null, "tempo_liturgico").isEmpty()) {
                                cantico.setTempoLiturgico(parser.getAttributeValue(null, "tempo_liturgico"));
                            }
                            if (!parser.getAttributeValue(null, "pdf_path").isEmpty()) {
                                cantico.setPdfPath(parser.getAttributeValue(null, "pdf_path"));
                            }
                            if (!parser.getAttributeValue(null, "audio_path").isEmpty()) {
                                cantico.setAudio_path(parser.getAttributeValue(null, "audio_path"));
                            }
                            canticos.add(cantico);
                        }
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class DataHolder {
        List<Cantico> canticos;
        List<Etiqueta> etiquetas;
        List<CanticoEtiquetaJoin> canticoEtiquetaJoins;

        public DataHolder(List<Cantico> canticos, List<Etiqueta> etiquetas, List<CanticoEtiquetaJoin> canticoEtiquetaJoins) {
            this.canticos = canticos;
            this.etiquetas = etiquetas;
            this.canticoEtiquetaJoins = canticoEtiquetaJoins;
        }
    }
}
