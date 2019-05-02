package Modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import View.*;

public class Game{
    private Player[] playersOfGame;
    private int turn;
    private ArrayList<Card>[] decksOfPLayers;
    private ArrayList<Card>[] primaryCards;
    private ArrayList<Card>[] handsOfPlayers;
    private ArrayList<Item>[] collectableItems;
    private Cell[][] map;
    private String gameMode; // heroMode - flagHolding - flagsCollecting
    private ArrayList<Card> graveYard;
    private int[] manaOfPlayers;
    private int numberOfFlagsToWin;
    private Card currentCard ;
    private Item currentItem;
    private boolean areWeInTheGraveYard;

    //////////////////////////// ARMAN ////////////////////////////////
    private HashMap<Card,Boolean> didCardAttack;

    public HashMap<Card, Boolean> getDidCardAttack(){return this.didCardAttack;}
    //////////////////////////// End ARMAN ////////////////////////////////

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
        this.graveYard = new ArrayList<>();
    }

    public Player[] getPlayersOfGame() {
        return playersOfGame;
    }

    public ArrayList<Card>[] getPrimaryCards(){return primaryCards;}

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

    public Card getPlayerHero(int turn){
        for(int i = 0 ; i < this.primaryCards[turn].size() ; i++){
            if(this.primaryCards[turn].get(i) instanceof Unit && ((Unit)this.primaryCards[turn].get(i)).isHero())
                return this.primaryCards[turn].get(i);
        }
        return null;
    }
    public int[] getFlagHoldedCoordination(){
        int[] coord = new int[2];
        for(int i = 0 ; i < 5 ; i ++)
            for(int j = 0 ; j < 9 ; j++)
                if(this.map[i][j].getItem().isFlag()){
                    coord[0] = i;
                    coord[1] = j;
                }
        return coord;
    }
    public Player getCardOwner(Card card){
        for(int i = 0 ; i < this.getPrimaryCards()[0].size() ; i++)
            if(this.getPrimaryCards()[0].get(i).equals(card))
                return this.playersOfGame[0];
        for(int i = 0 ; i < this.getPrimaryCards()[1].size() ; i++)
            if(this.getPrimaryCards()[1].get(i).equals(card))
                return this.playersOfGame[1];
        return null;
    }
    public Player getFlagOwner(int x , int y){
        Card card = this.map[x][y].getCard();
        return getCardOwner(card);
    }
    public ArrayList<String> getFlagsInfo(){
        ArrayList<String> ans = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i++)
            for(int j = 0 ; j < 9 ; j++)
                if(this.map[i][j].getItem().isFlag())
                    if(this.getFlagOwner(i,j) != null){
                        Card card = this.map[i][j].getCard();
                        ans.add("Team " + this.getFlagOwner(i,j).getUsername() + " : Soldier " + card.getName() + " Has Flag !");
                    }
        return ans;
    }

    public ArrayList<String> getPlayerUnitsInfo(String turn){
        ArrayList<String> ans = new ArrayList<>();
        Player p;
        if(turn.equals("my"))
            p = this.playersOfGame[this.turn];
        else{
            p = this.playersOfGame[0];
            if(this.turn == 0)
                p = this.playersOfGame[1];
        }
        for(int i = 0 ; i < 5 ; i++)
            for(int j = 0 ; j < 9 ; j++)
                if(this.map[i][j].getCard() instanceof Unit && this.getCardOwner(this.map[i][j].getCard()).equals(p) ) {
                    Unit unit = (Unit)this.map[i][j].getCard();
                    ans.add(unit.getCardID() + " : " + unit.getName() + " , Health : " + unit.getHP() + " , Location : ("
                    + i + "," + j + ") , Power : " + unit.getAttackPower());
                }
        return ans;
    }

    public int[] findByCardID(int cardID){
        int[] ans = new int[2];

        return ans;
    }

    public Card findCardByID(int cardID){
        for (int i=0; i<2; i++ )
            for ( Card card : primaryCards[i] )
                if ( cardID == card.getCardID() )
                    return card;
        return null;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public int moveCurrentCard(int x, int y){
        if ( true )
            return this.getCurrentCard().getCardID();
        else
            return 0;
    }

    //////////////////////////// ARMAN ////////////////////////////////
    public int[] cardCoordination(Card card){
        int[] coord = new int[2];
        for(int i = 0 ; i < 5 ; i++)
            for(int j = 0 ; j < 9 ; j++)
                if(this.map[i][j].getCard().equals(card)){
                    coord[0] = i;
                    coord[1] = j;
                    return coord;
                }
        return coord;
    }
    public boolean isValidUnitInMap(int cardID){
        for(int i = 0 ; i < 5 ; i++ )
            for(int j = 0 ; j < 9 ; j++)
                if(this.map[i][j].getCard() instanceof Unit && this.map[i][j].getCard().getCardID() == cardID)
                    return true;
        return false;
    }
    public boolean isInAttackRange(Card attackerCard,int cardID){
        int[] coordsCurrentUnit = cardCoordination(attackerCard);
        int[] coordsOpponentUnit = cardCoordination(findCardByID(cardID));
        int xDistance = Math.abs(coordsCurrentUnit[0]-coordsOpponentUnit[0]);
        int yDistance = Math.abs(coordsCurrentUnit[1]-coordsOpponentUnit[1]);
        int distance = Math.max(xDistance,yDistance);
        if(distance == ((Unit)attackerCard).getRange())
            return true;
        return false;
    }
    public void unitLost(Card card){
        int x = cardCoordination(card)[0] , y = cardCoordination(card)[1];
        this.map[x][y].setCard(null);
        this.graveYard.add(card);
    }
    public boolean canCounterAttack(Card defender,Card attacker){
        boolean isInRange = isInAttackRange(defender,attacker.getCardID());
        boolean isDisarman = false; // Check is Stun and Disarm
        if( isInRange && !isDisarman )
            return true;
        return false;
    }
    public void attack(int opponentCardID){
        Unit attacker = (Unit)this.currentCard;
        Unit defender = (Unit)findCardByID(opponentCardID);
        defender.setHP(defender.getHP() - attacker.getAttackPower());
        if(canCounterAttack(defender,attacker))
            attacker.setHP(attacker.getHP() - defender.getAttackPower());
        this.didCardAttack.put(this.currentCard,true);
        if(defender.getHP() <= 0)
            unitLost(findCardByID(opponentCardID));
        if(attacker.getHP() <= 0)
            unitLost(this.currentCard);
    }
    public boolean canCombo(ArrayList<Integer> comboAttackersID){
        boolean doAllHaveComboPower = true;
        for(int i = 0 ; i < comboAttackersID.size() ; i++)
            if(!((Unit)findCardByID(comboAttackersID.get(i))).getCanCombo())
                doAllHaveComboPower = false;
        return doAllHaveComboPower;
    }
    public void comboAttack(ArrayList<Integer> attackersID , int defenderID){
        for(int i = 0 ; i < attackersID.size() ; i++){
            Unit attacker = (Unit)findCardByID(attackersID.get(i)) , defender = (Unit)findCardByID(defenderID);
            defender.setHP(defender.getHP() - attacker.getAttackPower());
            this.didCardAttack.put(this.currentCard,true);
            if(i == 0 && canCounterAttack(defender,attacker))
                attacker.setHP(attacker.getHP() - defender.getAttackPower());
        }
        Unit firstAttacker = (Unit)findCardByID(attackersID.get(0)) , defender = (Unit)findCardByID(defenderID);
        if(defender.getHP() <= 0)
            unitLost(findCardByID(defenderID));
        if(firstAttacker.getHP() <= 0)
            unitLost(firstAttacker);
    }
    //////////////////////////// END ARMAN ////////////////////////////////

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
