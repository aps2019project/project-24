package Modules;

import java.util.ArrayList;
import java.util.Collections;

import View.*;

public class Game{
    private Player[] playersOfGame;
    private int turn;
    private ArrayList<Card>[] decksOfPLayers;
    private ArrayList<Card>[] primaryCards;
    private ArrayList<Card>[] handsOfPlayers;
    private ArrayList<Item>[] collectableItems;
    private Cell[][] map;
    private String gameMode;
    private ArrayList<Card> graveYard;
    private int[] manaOfPlayers;
    private int numberOfFlagsToWin;
    private Card currentCard ;
    private Item currentItem;
    private boolean areWeInTheGraveYard;

    public Game(Player player1, Player player2, String gameMode, int numberOfFlagsToWin){
        playersOfGame = new Player[2];
        playersOfGame[0] = player1;
        playersOfGame[1] = player2;
        this.turn = 0;
        decksOfPLayers = new ArrayList[2];
        primaryCards = new ArrayList[2];
        collectableItems = new ArrayList[2];
        for (int i=0; i<2; i++ ) {
            decksOfPLayers[i] = new ArrayList<>();
            decksOfPLayers[i].addAll(playersOfGame[i].getMainDeck().getCards());
            primaryCards[i] = new ArrayList<>();
            primaryCards[i].addAll(decksOfPLayers[i]);
            collectableItems[i] = new ArrayList<>();
            Collections.shuffle(decksOfPLayers[i]);
        }
        handsOfPlayers  = new ArrayList[2];
        for (int i=0; i<2; i++) {
            handsOfPlayers[i] = new ArrayList<>();
            for (int j = 0; j < 5; j++)
                handsOfPlayers[i].add(decksOfPLayers[i].get(j));
            for ( int j=0; j < 5; j++)
                decksOfPLayers[i].remove(0);
        }
        map = new Cell[5][9];
        for ( Cell[] cells : map )
            for ( Cell cell : cells )
                cell = new Cell();
        this.gameMode = gameMode;
        this.numberOfFlagsToWin = numberOfFlagsToWin;
        this.currentCard = null;
        this.currentItem = null;
        this.areWeInTheGraveYard = false;
    }

    public Player[] getPlayersOfGame() {
        return playersOfGame;
    }

    public void setPlayersOfGame(Player[] playersOfGame) {
        this.playersOfGame = playersOfGame;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public ArrayList<Card>[] getDecksOfPLayers() {
        return decksOfPLayers;
    }

    public void setDecksOfPLayers(ArrayList<Card>[] decksOfPLayers) {
        this.decksOfPLayers = decksOfPLayers;
    }

    public ArrayList<Card>[] getHandsOfPlayers() {
        return handsOfPlayers;
    }

    public void setHandsOfPlayers(ArrayList<Card>[] handsOfPlayers) {
        this.handsOfPlayers = handsOfPlayers;
    }

    public Cell[][] getMap() {
        return map;
    }

    public void setMap(Cell[][] map) {
        this.map = map;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public ArrayList<Card> getGraveYard() {
        return graveYard;
    }

    public void setGraveYard(ArrayList<Card> graveYard) {
        this.graveYard = graveYard;
    }

    public int[] getManaOfPlayers() {
        return manaOfPlayers;
    }

    public void setManaOfPlayers(int[] manaOfPlayers) {
        this.manaOfPlayers = manaOfPlayers;
    }

    public int getNumberOfFlagsToWin() {
        return numberOfFlagsToWin;
    }

    public void setNumberOfFlagsToWin(int numberOfFlagsToWin) {
        this.numberOfFlagsToWin = numberOfFlagsToWin;
    }

    public Viewer getViewer() {
        return viewer;
    }

    public void setViewer(Viewer viewer) {
        this.viewer = viewer;
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public ArrayList<Item>[] getCollectableItems() {
        return collectableItems;
    }

    public void setCollectableItems(ArrayList<Item>[] collectableItems) {
        this.collectableItems = collectableItems;
    }

    public Item getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(Item currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isAreWeInTheGraveYard() {
        return areWeInTheGraveYard;
    }

    public void setAreWeInTheGraveYard(boolean areWeInTheGraveYard) {
        this.areWeInTheGraveYard = areWeInTheGraveYard;
    }



    ///////////////// GAme/////////////////

    public int[] findByCardID(int cardID){
        int[] ans = new int[2];

        return ans;
    }

    public Card findCardByID(int cardID){
        Card ans = null;

        return ans;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public int moceCurrentCard(int x, int y){
        if ( true )
            return this.getCurrentCard().getCardID();
        else
            return 0;
    }

    public int attack(int opponentCardID){
        // return -1 -> opponent is missed
        // return -2 -> range !
        // return -3 -> already attacked
        // return -4 -> no card has been selected !
        return this.currentCard.getCardID();
    }

    public int counterAttack(int opponentCardID){

    }

    public boolean canCombo(ArrayList<Integer> comboAttackers){

    }

    public int useSpecialPower(int x, int y){

    }

    public Card findCardByName(String name){
        Card ans = null;

        return ans;
    }

    public boolean insertUnit(Card card, Cell cell){
        if ( cell.getCard() == null ) {
            cell.setCard(card);
            return true;
        }
        else
            return false;
    }

    public boolean insertSpell(Card card, Cell cell){

    }

    public int getOwnerOfTheCard(Card card){

    }

    public void endTrun(){

    }

    public boolean useCurrentItem(int x, int y){

    }

    public void sendCardToTHeGraveYard(Card card){

    }

    public int checkEndGame(){

    }


}