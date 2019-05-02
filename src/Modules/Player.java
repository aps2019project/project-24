package Modules;

import java.util.ArrayList;

public class Player {
    private static ArrayList<Player> players = new ArrayList<>();
    private String username;
    private Deck mainDeck;
    private ArrayList<Card> collection;
    private ArrayList<Deck> allDecks;
    private int money;
    private ArrayList<EndedMatches> listOfMatches;
    private String password;
    private int numberOfWins;
    public Player(String username , String password){
        this.username = username;
        this.password = password;
        this.numberOfWins = 0;
        this.money = 15000;
        this.mainDeck = null;
        this.collection = new ArrayList<>();
        this.allDecks = new ArrayList<>();
        this.listOfMatches = new ArrayList<>();
        players.add(this);
    }
    public static Player getPlayerObj(String username){
        for(int i = 0 ; i < players.size() ; i++)
            if(players.get(i).getUsername().equals(username))
                return players.get(i);
        return null;
    }
    public void addCard(Card card){
        this.collection.add(card);
    }
    public void removeCard(Card card){
        this.collection.remove(card);
    }
    public void decreaseMoney(int price){
        this.money -= price;
    }

    public void increaseMoney(int price){
        this.money += price;
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

    public ArrayList<Card> getCollection() {
        return collection;
    }
    public void setCollection(ArrayList<Card> collection) {
        this.collection = collection;
    }
    public int getMoney() {
        return money;
    }

    public static void setPlayers(ArrayList<Player> players) {
        Player.players = players;
    }

    public Deck getMainDeck() {
        return mainDeck;
    }

    public void setMainDeck(Deck mainDeck) {
        this.mainDeck = mainDeck;
    }

    public ArrayList<Deck> getAllDecks() {
        return allDecks;
    }

    public void setAllDecks(ArrayList<Deck> allDecks) {
        this.allDecks = allDecks;
    }

    public void setNumberOfWins(int numberOfWins) {
        this.numberOfWins = numberOfWins;
    }

    public void setMoney(int money) {
        this.money = money;
    }
    public ArrayList<EndedMatches> getListOfMatches() {
        return listOfMatches;
    }
    public void setListOfMatches(ArrayList<EndedMatches> listOfMatches) {
        this.listOfMatches = listOfMatches;
    }

}
