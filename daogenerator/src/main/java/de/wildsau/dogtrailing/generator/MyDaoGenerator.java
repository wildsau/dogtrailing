package de.wildsau.dogtrailing.generator;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(5, "de.wildsau.dogtrailing.entities");

        schema.enableActiveEntitiesByDefault();
        schema.enableKeepSectionsByDefault();

        Entity trailingSession = schema.addEntity("TrailingSession");
        trailingSession.addIdProperty();
        trailingSession.addStringProperty("title");
        trailingSession.addStringProperty("notes");
        trailingSession.addStringProperty("distractions");
        trailingSession.addStringProperty("finds");
        trailingSession.addBooleanProperty("test");
        trailingSession.addBooleanProperty("blind");
        trailingSession.addDateProperty("created");
        trailingSession.addDateProperty("searched");
        //TODO: Decide if really necessary.
        trailingSession.addLongProperty("exposureTime");
        trailingSession.addStringProperty("weather");
        trailingSession.addIntProperty("temperature");
        trailingSession.addIntProperty("humidity");
        trailingSession.addStringProperty("wind");
        trailingSession.addStringProperty("windDirection");
        trailingSession.addStringProperty("terrain");
        trailingSession.addStringProperty("locality");
        trailingSession.addBooleanProperty("selfCreated");
        trailingSession.addStringProperty("laidBy");
        trailingSession.addStringProperty("searchItem");
        trailingSession.addStringProperty("dogHandler");
        trailingSession.addStringProperty("dog");
        trailingSession.addDoubleProperty("length");
        trailingSession.addFloatProperty("rating");
        trailingSession.addLongProperty("tags");

        //TODO: The following fields may be added:
        //fährtenart
        //fotos
        //Anzahl Winkel
        //Anazahl Verleitungen



        new DaoGenerator().generateAll(schema, args[0]);
    }
}
