package cardBuilder;

import Modules.*;

import java.io.FileWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;

import com.gilecode.yagson.YaGson;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.nio.file.*;
import java.io.IOException;
import java.io.FileReader;

public class CardBuilder {
    public static void createJsonFileFromTheObject(Player player){
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(player);
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(player.getUsername()+".json");
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


    public static Player loadAPlayerFromJsonFile(String fileName){
        Player player = null;
        Gson gson = new Gson();
        YaGson mapper = new YaGson();
        try {
            FileReader reader = new FileReader(fileName+".json");
            player = mapper.fromJson(reader, Player.class);
            player.setCollection(new ArrayList<>());
            for ( Unit unit : player.getUnits() )
                player.getCollection().add(unit);
            for ( SpellCard spellCard : player.getSpellcards() )
                player.getCollection().add(spellCard);
            for ( Item item : player.getItems() )
                player.getCollection().add(item);
            for ( Deck deck : player.getAllDecks() ){
                deck.setCards(new ArrayList<>());
                for ( Unit unit : deck.getUnits() )
                    deck.getCards().add(unit);
                for ( SpellCard spellCard : deck.getSpells() )
                    deck.getCards().add(spellCard);
                for ( Item item : deck.getItems() )
                    deck.getCards().add(item);
            }
            player.getMainDeck().setCards(new ArrayList<>());
            for ( Unit unit : player.getMainDeck().getUnits() )
                player.getMainDeck().getCards().add(unit);
            for ( SpellCard spellCard : player.getMainDeck().getSpells() )
                player.getMainDeck().getCards().add(spellCard);
            for ( Item item : player.getMainDeck().getItems() )
                player.getMainDeck().getCards().add(item);
        }
        catch( Exception e ){

        }
        return player;
    }


    public static Unit loadAUnitFromJsonFile(String fileName){
        Unit unit = null;
        Gson gson = new Gson();
        try {
            JsonReader reader = new JsonReader(new FileReader(".\\.\\cards\\units\\"+fileName+".json"));
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
            JsonReader reader = new JsonReader(new FileReader(".\\.\\cards\\spellCards\\"+fileName+".json"));
            spellCard = gson.fromJson(reader, SpellCard.class);
        }
        catch( Exception e ){

        }
        return spellCard;
    }

    public static Item loadAnItemFromJsonFile(String fileName, int a){
        Item item = null;
        Gson gson = new Gson();
        try {
            JsonReader reader = null;
            if ( a == 0)
                reader = new JsonReader(new FileReader(".\\.\\cards\\items\\"+fileName+".json"));
            else
                reader = new JsonReader(new FileReader(".\\.\\cards\\collectable item\\" + fileName + ".json"));
            item = gson.fromJson(reader, Item.class);
        }
        catch( Exception e ){

        }
        return item;
    }



}
