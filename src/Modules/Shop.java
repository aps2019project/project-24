package Modules;

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
        Unit hero1 = CardBuilder.loadAUnitFromJsonFile("HeroNumberOne");
        cards.add(hero1);
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