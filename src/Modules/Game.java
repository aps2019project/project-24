package Modules;

import java.util.ArrayList;
import View.*;

public class Game{
    private Player[] playersOfGame;
    private int turn;
    private ArrayList<Card>[] decksOfPLayers;
    private ArrayList<Card>[] handsOfPlayers;
    private ArrayList<Item>[] collectableItems;
    private Cell[][] map;
    private String gameMode;
    private ArrayList<Card> graveYard;
    private int[] manaOfPlayers;
    private int numberOfFlagsToWin;
    private ArrayList<String> input;
    private ArrayList<String> output;
    private Viewer viewer;
    private Card currentCard ;
    private Item currentItem;
    private boolean areWeInTheGraveYard;

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

    public ArrayList<String> getInput() {
        return input;
    }

    public void setInput(ArrayList<String> input) {
        this.input = input;
    }

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
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

    public void startTheGame(){
        // instanse gereftan az deck haye mellat berize tu deckaye game
    }

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
