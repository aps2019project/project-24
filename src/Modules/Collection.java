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
}
