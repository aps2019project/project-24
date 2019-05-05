package cardBuilder;

import Modules.*;

import java.io.FileWriter;
import java.util.ArrayList;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.nio.file.*;
import java.io.IOException;
import java.io.FileReader;

public class CardBuilder {
    public static void createJsonFileFromTheObject(Card card){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(card);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(card.getName()+".json");
            fileWriter.write(json.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Unit loadAUnitFromJsonFile(String fileName){
        Unit unit = null;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(".\\.\\cards\\units"+fileName+".json"));
            unit = gson.fromJson(reader, Unit.class);
        }
        catch( Exception e ){

        }
        return unit;
    }

    public static SpellCard loadASpellCardFromJsonFile(String fileName){
        SpellCard spellCard = null;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(".\\.\\cards\\spellCards"+fileName+".json"));
            spellCard = gson.fromJson(reader, Unit.class);
        }
        catch( Exception e ){

        }
        return spellCard;
    }

    public static Item loadAnItemFromJsonFile(String fileName){
        Item item = null;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(".\\.\\cards\\items"+fileName+".json"));
            item = gson.fromJson(reader, Unit.class);
        }
        catch( Exception e ){

        }
        return item;
    }
}
