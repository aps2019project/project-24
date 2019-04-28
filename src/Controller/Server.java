package Controller;

import View.*;
import Modules.*;
import java.util.ArrayList;

public class Server {
    private Shop shop;
    private Player currentPlayer = null;
    private ArrayList<Game> allGames;
    private Viewer viewer;
    public ArrayList<Player> getAllPlayers() {
        return Player.getPlayers();
    }
    public ArrayList<Game> getAllGames() { return allGames; }
    //================================= Account Functions ===============================//
    public boolean userExists(String username){
        ArrayList<Player> allPlayers = Player.getPlayers();
        for(int i = 0 ; i < allPlayers.size() ; i++)
            if(allPlayers.get(i).getUsername().equals(username))
                return true;
        return false;
    }
    public void createAccount(String username,String password){
        new Player(username,password);
    }
    public boolean isValidLogin(String username , String password){
        Player player = Player.getPlayerObj(username);
        if(player.getPassword().equals(password))
            return true;
        return false;
    }
    public void showCollection(){
        shop.showCollection();
    }
    public void searchCard(String cardName){
        shop.searchCard(cardName);
    }
    public void buyCard(String name){
        shop.buy(currentPlayer, name);
    }

    public void sellCardwithID(int cardID){
        boolean found = false;
        for(int i = 0; i < currentPlayer.getCollection().size(); i++){
            if(currentPlayer.getCollection().get(i).getCardID() == cardID){
                found = true;
                shop.sellWithID(currentPlayer, cardID);
                break;
            }
        }
        if(!found){
            System.out.println("selling failed");
        }
    }
    public void sellCardWithName(String name){
        shop.sellWithName(currentPlayer, name);
    }

    public void showItems(){
        shop.showItems();
    }
    public void login(String username, String password){
        currentPlayer = Player.getPlayerObj(username);
    }
    public void save(){}
    public void logOut(){currentPlayer = null;}
}
