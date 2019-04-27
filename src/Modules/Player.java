package Modules;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> players;
    private String username;
    private ArrayList<Card> mainDeck;
    private ArrayList<Card> collection;
    private ArrayList<Card[]> allDecks;
    private int money;
    private ArrayList<EndedMatches> listOfMacthes;
    private String password;
    private int numberOfWins;
    public Player(String username , String password){
        this.username = username;
        this.password = password;
        this.numberOfWins = 0;
        this.money = 15000;
        this.mainDeck = new ArrayList<>();
        this.collection = new ArrayList<>();
        this.allDecks = new ArrayList<>();
        this.listOfMacthes = new ArrayList<>();
        players.add(this);
    }
    public static Player getPlayerObj(String username){
        for(int i = 0 ; i < players.size() ; i++)
            if(players.get(i).getUsername().equals(username))
                return players.get(i);
        return null;
    }
    public static ArrayList<Player> getPlayers(){return players;}
    public int getNumberOfWins() {
        return numberOfWins;
    }
    public void addWin(int numberOfWins) {
        this.numberOfWins ++;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {this.password = password;}
    public String getUsername() {
        return username;
    }
    public void setUsername(String name) {this.username = name;}
    public ArrayList<Card> getMainDeck() {
        return mainDeck;
    }
    public void setMainDeck(ArrayList<Card> mainDeck) {
        this.mainDeck = mainDeck;
    }
    public ArrayList<Card> getCollection() {
        return collection;
    }
    public void setCollection(ArrayList<Card> collection) {
        this.collection = collection;
    }
    public ArrayList<Card[]> getAllDecks() {
        return allDecks;
    }
    public void setAllDecks(ArrayList<Card> allDecks) {
        allDecks = allDecks;
    }
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    public ArrayList<EndedMatches> getListOfMacthes() {
        return listOfMacthes;
    }
    public void setListOfMacthes(ArrayList<EndedMatches> listOfMacthes) {
        this.listOfMacthes = listOfMacthes;
    }
}
