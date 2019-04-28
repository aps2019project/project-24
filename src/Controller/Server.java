package Controller;

import View.*;
import Modules.*;
import java.util.ArrayList;

public class Server {
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
    public void login(String username, String password){
        currentPlayer = Player.getPlayerObj(username);
    }
    public void save(){}
    public void logOut(){currentPlayer = null;}
    //================================= Collection Functions ===============================//
    public ArrayList<Card> cardsInCollection(){
        return currentPlayer.getCollection();
    }
}
