package Modules;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import cardBuilder.CardBuilder;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.nio.file.*;
import java.io.IOException;
import java.io.FileReader;

public class Shop{

    private ArrayList<Card> cards;
    private Player player;

    public Shop(){
        cards = new ArrayList<>();
        File folder = new File(".\\.\\cards\\units");
        File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles)
                for (int i = 0; i < 10; i++) {
                    Unit unit = CardBuilder.loadAUnitFromJsonFile(file.getName().substring(0,file.getName().length()-5));
                    unit.setCardID(unit.getCardID() * 10 + i);
                    if ( unit.getSpecialPower() == null )
                        unit.setSpecialPower(new Spell("default"));
                    cards.add(unit);
                }
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }
    public Player getPlayer(){
        return player;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void sellWithID(Player seller, int cardID){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getCardID() == cardID){
                cards.add(seller.getCollection().get(i));
                seller.removeCard(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                break;
            }
        }
    }

    public void sellWithName(Player seller, String name){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getName().equals(name)){
                cards.add(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                seller.removeCard(seller.getCollection().get(i));
                break;
            }
        }
    }
    public void buy(Player buyer, String name){
        for(int i = 0; i < this.cards.size(); i++){
            if(this.cards.get(i).getName().equals(name)){
                buyer.addCard(this.cards.get(i));
                buyer.decreaseMoney(this.cards.get(i).getPrice());
                cards.remove(cards.get(i));
                break;
            }
        }
    }


}