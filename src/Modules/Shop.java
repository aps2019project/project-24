package Modules;

import java.util.ArrayList;


public class Shop{
    private ArrayList<Card> cards;
    private Player player;

    public void sellWithID(Player seller, int cardID){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getCardID() == cardID){
                seller.removeCard(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                System.out.println("selling Done");
                break;
            }
        }
    }

    public void sellWithName(Player seller, String name){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getName() == name){
                seller.removeCard(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                System.out.println("selling Done");
                break;
            }
        }
    }
    //////////////////////////sell ba nameam mishe

    public void buy(Player buyer, String name){
        for(int i = 0; i < this.cards.size(); i++){
            if(this.cards.get(i).getName() == name){
                buyer.addCard(this.cards.get(i));
                buyer.decreaseMoney(this.cards.get(i).getPrice());
                break;
            }
        }
    }
    public void showCollection(){

    }
    public void searchCard(String cardName){
        for(int i = 0; i < this.cards.size(); i++){
            if(this.cards.get(i).getName() == cardName){
                System.out.println(this.cards.get(i).getCardID());
                break;
            }
        }
    }
    public void showItems(){

    }

}