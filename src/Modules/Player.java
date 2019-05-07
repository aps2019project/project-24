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
    private ArrayList<Unit> units;
    private ArrayList<SpellCard> spellcards;
    private ArrayList<Item> items;
    private static ArrayList<Player> AIplayers;
    private int storyModeLevel;

    static{
        Player.setAIplayers(new ArrayList<>());
        Player AIplayer1 = new Player("AIplayer1", "0");
        AIplayer1.setMoney(100000);
        Player AIplayer2 = new Player("AIplayer2", "0");
        AIplayer2.setMoney(100000);
        Player AIplayer3 = new Player("AIplayer3", "0");
        AIplayer3.setMoney(100000);
        AIplayers.add(AIplayer1);
        AIplayers.add(AIplayer2);
        AIplayers.add(AIplayer3);
        players.addAll(AIplayers);
    }

    public int getStoryModeLevel() {
        return storyModeLevel;
    }

    public void setStoryModeLevel(int storyModeLevel) {
        this.storyModeLevel = storyModeLevel;
    }

    {
        this.units = new ArrayList<>();
        this.spellcards = new ArrayList<>();
        this.items = new ArrayList<>();
    }
    public Player(String username , String password){
        this.username = username;
        this.password = password;
        this.numberOfWins = 0;
        this.money = 150000;// ye sefr ezaf
        this.mainDeck = null;
        this.collection = new ArrayList<>();
        this.allDecks = new ArrayList<>();
        this.listOfMatches = new ArrayList<>();
        players.add(this);
    }

    public static ArrayList<Player> getAIplayers() {
        return AIplayers;
    }

    public static void setAIplayers(ArrayList<Player> AIplayers) {
        Player.AIplayers = AIplayers;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<SpellCard> getSpellcards() {
        return spellcards;
    }

    public void setSpellcards(ArrayList<SpellCard> spellcards) {
        this.spellcards = spellcards;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
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
