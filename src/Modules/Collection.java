package Modules;

import java.util.ArrayList;

public class Collection {
    public static ArrayList<Card> heroCards(Player player){
        ArrayList<Card> finalArr = new ArrayList<>();
        for(int i = 0 ; i < player.getCollection().size(); i++){
            if(player.getCollection().get(i) instanceof Unit && ((Unit) player.getCollection().get(i)).isHero())
                finalArr.add(player.getCollection().get(i));
        }
        return finalArr;
    }
    public static ArrayList<Card> itemCards(Player player){
        ArrayList<Card> finalArr = new ArrayList<>();
        for(int i = 0 ; i < player.getCollection().size(); i++){
            if(player.getCollection().get(i) instanceof Item)
                finalArr.add(player.getCollection().get(i));
        }
        return finalArr;
    }
    public static ArrayList<Card> minionCards(Player player){
        ArrayList<Card> finalArr = new ArrayList<>();
        for(int i = 0 ; i < player.getCollection().size(); i++){
            if(player.getCollection().get(i) instanceof Unit && !((Unit) player.getCollection().get(i)).isHero())
                finalArr.add(player.getCollection().get(i));
        }
        return finalArr;
    }
    public static ArrayList<Card> spellCards(Player player){
        ArrayList<Card> finalArr = new ArrayList<>();
        for(int i = 0 ; i < player.getCollection().size(); i++){
            if(player.getCollection().get(i) instanceof SpellCard)
                finalArr.add(player.getCollection().get(i));
        }
        return finalArr;
    }
}
