package Modules;

import java.util.ArrayList;

public class Collection {
    public static ArrayList<Integer> searchCard(Player player , String keyword){
        ArrayList<Card> collection = player.getCollection();
        ArrayList<Integer> ansArr = new ArrayList<>();
        for(int i = 0 ; i < collection.size() ; i++)
            if(collection.get(i).getName().equals(keyword))
                ansArr.add(collection.get(i).getCardID());
        return ansArr;
    }
    public static Deck searchDeck(Player player , String keyword){
        for(int i = 0 ; i < player.getAllDecks().size() ; i++){
            Deck d = player.getAllDecks().get(i);
            if(d.getName().equals(keyword)){
                return d;
            }
        }
        return null;
    }
    public static Card searchCard(Player player , int cardID){
        for(int i = 0 ; i < player.getCollection().size() ; i++)
            if(player.getCollection().get(i).getCardID() == cardID)
                return player.getCollection().get(i);
        return null;
    }
}
