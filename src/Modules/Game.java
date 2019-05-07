package Modules;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import View.*;
import cardBuilder.CardBuilder;

public class Game {
    private Player[] playersOfGame;
    private int turn;
    private ArrayList<Card>[] decksOfPLayers;
    private ArrayList<Card>[] primaryCards;
    private ArrayList<Card>[] handsOfPlayers;
    private ArrayList<Item>[] collectableItems;
    private ArrayList<Item> collectablesMap;
    private Cell[][] map;
    private String gameMode; // heroMode - flagHolding - flagsCollecting
    private ArrayList<Card> graveYard;
    private int[] manaOfPlayers;
    private int numberOfFlagsToWin;
    private Card currentCard;
    private Item currentItem;
    private boolean areWeInTheGraveYard;
    private int[] manaOfTheStartOfTheTrun;
    private int countsOfRoundsToWinInFlagHoldingMode = 8;
    private int[] countOfFlagsInFlagsCollecting;
    private int[] roundsPlayerHasTheFlag;
    private Item[] mainItem;

    //////////////////////////// ARMAN ////////////////////////////////
    private HashMap<Card,Boolean> didCardAttack;

    public HashMap<Card, Boolean> getDidCardAttack(){return this.didCardAttack;}
    //////////////////////////// End ARMAN ////////////////////////////////

    public Game(Player player1, Player player2, String gameMode){
//        for(int j = 0 ; j < 2 ; j++){
//            for(int i = 0 ; i < getDecksOfPLayers()[j].size() ; i++){
//                if(getDecksOfPLayers()[j].get(i) instanceof Item) {
//                    mainItem[j] = (Item) getDecksOfPLayers()[j].get(i);
//                    getDecksOfPLayers()[j].remove(i);
//                    break;
//                }
//            }
//        }
        playersOfGame = new Player[2];
        playersOfGame[0] = player1;
        playersOfGame[1] = player2;
        manaOfTheStartOfTheTrun = new int[2];
        roundsPlayerHasTheFlag = new int[2];
        countOfFlagsInFlagsCollecting = new int[2];
        this.countsOfRoundsToWinInFlagHoldingMode = countsOfRoundsToWinInFlagHoldingMode;
        this.turn = 0;
        decksOfPLayers = new ArrayList[2];
        primaryCards = new ArrayList[2];
        collectableItems = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            decksOfPLayers[i] = new ArrayList<>();
            decksOfPLayers[i].addAll(playersOfGame[i].getMainDeck().getCards());
            primaryCards[i] = new ArrayList<>();
            primaryCards[i].addAll(decksOfPLayers[i]);
            for ( Card card : decksOfPLayers[i] )
                if ( card instanceof Unit && ((Unit)card).isHero() ) {
                    decksOfPLayers[i].remove(card);
                    break;
                }
            collectableItems[i] = new ArrayList<>();
            Collections.shuffle(decksOfPLayers[i]);
        }
        handsOfPlayers = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            handsOfPlayers[i] = new ArrayList<>();
            for (int j = 0; j < 5; j++)
                handsOfPlayers[i].add(decksOfPLayers[i].get(j));
            for (int j = 0; j < 5; j++)
                decksOfPLayers[i].remove(0);
        }
        map = new Cell[5][9];
        for(int i = 0 ; i < 5 ; i++) {
            map[i] = new Cell[9];
            for (int j = 0; j < 9; j++)
                map[i][j] = new Cell();
        }
        this.gameMode = gameMode;
        this.numberOfFlagsToWin = randNumberInRange(3,5);
        this.currentCard = null;
        this.currentItem = null;
        this.areWeInTheGraveYard = false;
        this.graveYard = new ArrayList<>();
        this.manaOfPlayers = new int[2];
        for (int i = 0; i < 2; i++) {
            manaOfPlayers[i] = 5;
            manaOfTheStartOfTheTrun[i] = 5;
        }
        map[2][0].setCard(getPlayerHero(0));
        map[2][8].setCard(getPlayerHero(1));
        didCardAttack = new HashMap<>();
        didCardAttack.put(getPlayerHero(0),false);
        didCardAttack.put(getPlayerHero(1),false);
        if(gameMode.equals("flagsCollecting"))
            setFlagsInMap(numberOfFlagsToWin);
        else if(gameMode.equals("flagHolding"))
            setMainFlagInMap();

        collectablesMap = new ArrayList<>();
        File folder = new File(".\\.\\cards\\collectable item");
        File[]listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            Item item = CardBuilder.loadAnItemFromJsonFile(file.getName().substring(0, file.getName().length() - 5), 1);
            item.setCardID(item.getCardID() * 10 + 1);
            collectablesMap.add(item);
        }
        setCollectablesMap(3);
    }

    public void setPrimaryCards(ArrayList<Card>[] primaryCards) {
        this.primaryCards = primaryCards;
    }

    public int[] getCountOfFlagsInFlagsCollecting() {
        return countOfFlagsInFlagsCollecting;
    }

    public void increaseCountOfFlagsInFlagsCollecting() {
        countOfFlagsInFlagsCollecting[turn]++;
    }

    public void setManaOfPlayers(int[] manaOfPlayers) {
        this.manaOfPlayers = manaOfPlayers;
    }

    public int[] getManaOfTheStartOfTheTrun() {
        return manaOfTheStartOfTheTrun;
    }

    public void setManaOfTheStartOfTheTrun(int[] manaOfTheStartOfTheTrun) {
        this.manaOfTheStartOfTheTrun = manaOfTheStartOfTheTrun;
    }

    public int getCountsOfRoundsToWinInFlagHoldingMode() {
        return countsOfRoundsToWinInFlagHoldingMode;
    }

    public void setCountsOfRoundsToWinInFlagHoldingMode(int countsOfRoundsToWinInFlagHoldingMode) {
        this.countsOfRoundsToWinInFlagHoldingMode = countsOfRoundsToWinInFlagHoldingMode;
    }

    public int[] getRoundsPlayerHasTheFlag() {
        return roundsPlayerHasTheFlag;
    }

    public void increaseRoundsPlayerHasTheFlag() {
        roundsPlayerHasTheFlag[turn]++;
    }

    public Player[] getPlayersOfGame() {
        return playersOfGame;
    }

    public ArrayList<Card>[] getPrimaryCards() {
        return primaryCards;
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

        public void decreaseManaOfPlayers(int manacost) {
            this.manaOfPlayers[this.turn] -= manacost;
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

    public void addCollectableItems(Item item) {
        collectableItems[turn].add(item);
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

    public Card getPlayerHero(int turn) {
        for (int i = 0; i < this.primaryCards[turn].size(); i++) {
            if (this.primaryCards[turn].get(i) instanceof Unit && ((Unit) this.primaryCards[turn].get(i)).isHero())
                return this.primaryCards[turn].get(i);
        }
        return null;
    }

    public Item findItemByItemID(int itemID){
        for(int i = 0; i < getCollectableItems()[this.turn].size(); i++){
            if(getCollectableItems()[this.turn].get(i).getCardID() == itemID)
                return getCollectableItems()[this.turn].get(i);
        }
        return null;
    }
    public int[] getFlagHoldedCoordination() {
        int[] coord = new int[2];
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < map[i][j].getItems().size(); k++)
                    if (this.map[i][j].getItems().get(k).isFlag()) {
                        coord[0] = i;
                        coord[1] = j;
                        return coord;
                    }
                if(((Unit)this.map[i][j].getCard()).getFlags().size() != 0){
                    coord[0] = i;
                    coord[1] = j;
                    return coord;
                }
            }
        return coord;
    }

    public Player getCardOwner(Card card) {
        for (int i = 0; i < this.getPrimaryCards()[0].size(); i++)
            if (this.getPrimaryCards()[0].get(i).equals(card))
                return this.playersOfGame[0];
        for (int i = 0; i < this.getPrimaryCards()[1].size(); i++)
            if (this.getPrimaryCards()[1].get(i).equals(card))
                return this.playersOfGame[1];
        return null;
    }

    public Player getFlagOwner(int x, int y) {
        Card card = this.map[x][y].getCard();
        return getCardOwner(card);
    }

    public ArrayList<String> getFlagsInfo() {
        ArrayList<String> ans = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
                for (int k = 0; k < map[i][j].getItems().size(); k++)
                    if (this.map[i][j].getItems().get(k).isFlag())
                        if (this.getFlagOwner(i, j) != null) {
                            Card card = this.map[i][j].getCard();
                            ans.add("Team " + this.getFlagOwner(i, j).getUsername() + " : Soldier " + card.getName() + " Has Flag !");
                        }
        return ans;
    }

    public ArrayList<String> getPlayerUnitsInfo(String turn) {
        ArrayList<String> ans = new ArrayList<>();
        Player p;
        if (turn.equals("my"))
            p = this.playersOfGame[this.turn];
        else {
            p = this.playersOfGame[0];
            if (this.turn == 0)
                p = this.playersOfGame[1];
        }
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++)
                if (this.map[i][j].getCard() instanceof Unit && this.getCardOwner(this.map[i][j].getCard()).equals(p)) {
                    Unit unit = (Unit) this.map[i][j].getCard();
                    ans.add(unit.getCardID() + " : " + unit.getName() + " , Health : " + unit.getHP() + " , Location : ("
                            + i + "," + j + ") , Power : " + unit.getAttackPower());
                }
        return ans;
    }

    public Card findCardByID(int cardID) {
        for (int i = 0; i < 2; i++)
            for (Card card : primaryCards[i])
                if (cardID == card.getCardID())
                    return card;
        return null;
    }
    public int[] coordinationOfHero(){
        int[] ans = new int[2];
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 9; j++) {
                if (this.map[i][j].getCard() instanceof Unit && ((Unit) this.map[i][j].getCard()).isHero() &&
                        getCardOwner(this.map[i][j].getCard()).getUsername().equals(this.playersOfGame[this.turn].getUsername())) {
                    ans[0] = i;
                    ans[1] = j;
                    return ans;
                }
            }
        return ans;
    }

    public boolean isNearHero(int x, int y){
        int[] coordOfHero = coordinationOfHero();
        int xDistance = Math.abs(x - coordOfHero[0]);
        int yDistance = Math.abs(y - coordOfHero[1]);
        int distance = Math.max(xDistance, yDistance);
        if(distance == 1)
            return true;
        return false;
    }


    public Card findCardByName(String name){
        for(Card card : primaryCards[turn])
            if(card.getName().equals(name))
                return card;
        return null;
    }

    public boolean cardIsInHand(Card card){
        ArrayList<Card> hand = this.handsOfPlayers[this.turn];
        for(int i = 0; i < hand.size(); i++)
            if(hand.get(i).getCardID() == card.getCardID())
                return true;
        return false;
    }

    public void setCurrentCard(Card currentCard) {
        this.currentCard = currentCard;
    }

    public int moveCurrentCard(int x, int y) {
        int priviousX = 0, priviousY = 0;
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 9; j++) {
                boolean flag = false;
                if ( map[i][j].getCard() != null && this.currentCard.getCardID() == map[i][j].getCard().getCardID()) {
                    priviousX = i;
                    priviousY = j;
                    flag = true;
                }
                if (flag)
                    break;
            }
        if (java.lang.Math.abs(priviousX - x) > 2 || java.lang.Math.abs(priviousY - y) > 2)
            return 3;
        if (java.lang.Math.abs(priviousX - x) == 2 && java.lang.Math.abs(priviousY - y) > 0)
            return 3;
        if (java.lang.Math.abs(priviousX - x) > 0 && java.lang.Math.abs(priviousY - y) == 2)
            return 3;
        if (map[x][y].getCard() != null)
            return 3;
        if (x - priviousX == 2 && map[priviousX + 1][priviousY].getCard() != null)
            return 3;
        if (priviousX - x == 2 && map[priviousX - 1][priviousY].getCard() != null)
            return 3;
        if (y - priviousY == 2 && map[priviousX][priviousY + 1].getCard() != null)
            return 3;
        if (priviousY - y == 2 && map[priviousX][priviousY - 1].getCard() != null)
            return 3;

        Unit unit = (Unit) this.currentCard;
        if (unit.isHasBeenMovedThisRound())
            return 2;

        boolean stun=false;
        boolean canBeStunned=true;

        for ( Spell spell : unit.getBuffs() )
            if (spell.isStun()) {
                stun = true;
                break;
            }

        for ( Spell spell : unit.getBuffs() )
            if ( !spell.isCanBeStun() ){
                canBeStunned = false;
                break;
            }

        if ( stun && canBeStunned )
            return 5;

        map[priviousX][priviousY].setCard(null);
        map[x][y].setCard(currentCard);
        unit.setHasBeenMovedThisRound(true);
        if(map[x][y].getItems() != null ){
            for(int i = 0; i < map[x][y].getItems().size(); i++){
                if(map[x][y].getItems().get(i).isFlag()){
                    if(this.gameMode.equals("flagHolding"))
                        increaseRoundsPlayerHasTheFlag();
                    if(this.gameMode.equals("flagsCollecting"))
                        increaseCountOfFlagsInFlagsCollecting();
                    ((Unit)currentCard).setHasFlag(true);
                    ((Unit)currentCard).addFlag(map[x][y].getItems().get(i));
                }
                else if(map[x][y].getItems().get(i).isCollectAble()){
                    collectableItems[turn].add(map[x][y].getItems().get(i));
                }
            }
            map[x][y].setItems(new ArrayList<>());
        }


        return this.currentCard.getCardID();
    }

    //////////////////////////// ARMAN ////////////////////////////////
    public int[] cardCoordination(Card card){
        int[] coord = new int[2];
        for(int i = 0 ; i < 5 ; i++)
            for(int j = 0 ; j < 9 ; j++)
                if(this.map[i][j].getCard() != null && this.map[i][j].getCard().equals(card)){
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
        if(distance == ((Unit)attackerCard).getRange() || (distance <= ((Unit)attackerCard).getRange() && ((Unit)attackerCard).getAttackType().equals("hybrid") ))
            return true;
        return false;
    }
    public void unitLost(Card card){
        int x = cardCoordination(card)[0] , y = cardCoordination(card)[1];
        if ( ((Unit)card).getSpecialPowerCastTime().equals("onDeath") )
            useSpecialPowerOfTheCard(card, x, y);
        if(((Unit)card).getHasFlag()) {
            if(map[x][y].getItems().size() != 0) {
                Item ownItem = map[x][y].getItems().get(0);
                map[x][y].setItems(((Unit)card).getFlags());
                map[x][y].addItem(ownItem);
            }
            else{
                map[x][y].setItems(((Unit)card).getFlags());
            }

            Player lostUnitOwner = getCardOwner(card);
            int indexOfPlayer = 0;
            if(lostUnitOwner.getUsername().equals(this.playersOfGame[1].getUsername()))
                indexOfPlayer = 1;
            if(this.gameMode.equals("flagHolding"))
                roundsPlayerHasTheFlag[indexOfPlayer] = 0;
            else if(this.gameMode.equals("flagsCollecting"))
                countOfFlagsInFlagsCollecting[indexOfPlayer] -= ((Unit)card).getFlags().size();

        }
        this.map[x][y].setCard(null);
        this.graveYard.add(card);
    }
    public boolean isUnitDisarm(Card unit){
        ArrayList<Spell> buffs = ((Unit)unit).getBuffs();
        boolean isDisarm = false , isStun = false , canBeDisarm = true , canBeStun = true;
        for(int i = 0 ; i < buffs.size(); i++){
            if(buffs.get(i).isDisarm())
                isDisarm = true;
            if(!buffs.get(i).isCanBeDisarm())
                canBeDisarm = false;
            if(buffs.get(i).isStun())
                isStun = true;
            if(!buffs.get(i).isCanBeStun())
                canBeStun = false;
        }
        return (isStun && canBeStun) || (isDisarm && canBeDisarm);
    }
    public boolean canCounterAttack(Card defender,Card attacker){
        boolean isInRange = isInAttackRange(defender,attacker.getCardID());
        boolean isDisarm = isUnitDisarm(defender); // Check is Stun and Disarm
        if( isInRange && !isDisarm )
            return true;
        return false;
    }
    public void attack(int opponentCardID){
        Unit attacker = (Unit)this.currentCard;
        Unit defender = (Unit)findCardByID(opponentCardID);
        if ( attacker.getSpecialPower() != null && attacker.getSpecialPowerCastTime().equals("onAttack") )
            useSpecialPowerOfTheCard(attacker, cardCoordination(attacker)[0], cardCoordination(attacker)[1]);
        if ( defender.getSpecialPower() != null && defender.getSpecialPowerCastTime().equals("onDefend") )
            useSpecialPowerOfTheCard(defender, cardCoordination(attacker)[0], cardCoordination(attacker)[1]);
        defender.setHP(defender.getHP() - attacker.getAttackPower());
        if(defender.hasHolyBuff())
            defender.setHP(defender.getHP()+1);
        if(canCounterAttack(defender,attacker)) {
            attacker.setHP(attacker.getHP() - defender.getAttackPower());
            if(attacker.hasHolyBuff())
                attacker.setHP(attacker.getHP() + 1);
        }
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
    public Card searchCardByIDinGraveyard(int cardID){
        ArrayList<Card> cards = this.getGraveYard();
        for(int i = 0 ; i < cards.size() ; i++)
            if(cards.get(i).getCardID() == cardID)
                return cards.get(i);
        return null;
    }
    //-------- Set Flags In Map -------//
    public int randNumberInRange(int min , int max){
        int range = max - min + 1;
        int ans = (int)(Math.random() * range) + min;
        return ans;
    }
    public boolean canPutFlag(int i , int j){
        boolean noUnitInCell = (map[i][j].getCard() == null);
        boolean noFlagInCell = true;
        for(int k = 0 ; k < map[i][j].getItems().size() ; k++)
            if(map[i][j].getItems().get(k).isFlag())
                noFlagInCell = false;
        return noUnitInCell && noFlagInCell;
    }
    public void setFlagsInMap(int numberOfFlags){
        while(numberOfFlags > 0){
            int i = randNumberInRange(0,4);
            int j = randNumberInRange(0,8);
            if(canPutFlag(i,j)){
                Item flag = new Item("flag");
                map[i][j].getItems().add(flag);
                numberOfFlags--;
            }
        }
    }
    public void setMainFlagInMap(){
        boolean isSet = false;
        while(!isSet){
            int i = randNumberInRange(0,4);
            int j = 4;
            if(canPutFlag(i,j)){
                Item flag = new Item("flag");
                map[i][j].getItems().add(flag);
                isSet = true;
            }
        }
    }
    //------- End Set Flags In Map --------//
    public void setCollectablesMap(int numberOfCollectables){
        while(numberOfCollectables > 0){
            int i = randNumberInRange(0,4);
            int j = randNumberInRange(0,8);
            if(canPutFlag(i,j)){
                map[i][j].getItems().add(collectablesMap.get(0));
                collectablesMap.remove(0);
                numberOfCollectables--;
            }
        }
    }
    //------------- erer ------------//
    public int[] freeCellToMove(){
        int[] coord = new int[2];
        int[] heroCoord = coordinationOfHero();
        int minI = Math.max(heroCoord[0]-1,0) , maxI = Math.min(heroCoord[0]+1,4);
        int minJ = Math.max(heroCoord[1]-1,0) , maxJ = Math.min(heroCoord[1]+1,8);
        for(int i = minI ; i < maxI ; i++ )
            for(int j = minJ ; j < maxJ ; j++ )
                if( i != heroCoord[0] && j != heroCoord[1] )
                    if( map[i][j].getCard() == null ){
                        coord[0] = i;
                        coord[1] = j;
                        return coord;
                    }
        coord[0] = -1;
        coord[1] = -1;
        return coord;
    }
    public int[] unitCoordCanAttack(){
        int[] ansCoord = new int[2];
        int[] heroCoord = coordinationOfHero();
        Unit hero = (Unit) getPlayerHero(turn);
        int range = hero.getRange();
        boolean isHybrid = hero.getAttackType().equals("hybrid");
        int minI = Math.max(heroCoord[0]-range,0) , maxI = Math.min(heroCoord[0]+range,4);
        int minJ = Math.max(heroCoord[1]-range,0) , maxJ = Math.min(heroCoord[1]+range,8);
        for(int i = minI ; i < maxI ; i++){
            for(int j = minJ ; j < maxJ ; j++ ){
                if( i != heroCoord[0] && j != heroCoord[1] ){
                    if( isHybrid )
                        if(map[i][j].getCard() != null && !getCardOwner(map[i][j].getCard()).getUsername().equals(playersOfGame[turn].getUsername())){
                            ansCoord[0] = i;
                            ansCoord[1] = j;
                            return ansCoord;
                        }
                    else{
                        int xDistance = Math.abs(i - heroCoord[0]);
                        int yDistance = Math.abs(j - heroCoord[1]);
                        int distance = Math.max(xDistance, yDistance);
                        if(distance == range && map[i][j].getCard() != null && !getCardOwner(map[i][j].getCard()).getUsername().equals(playersOfGame[turn].getUsername()) ){
                            ansCoord[0] = i;
                            ansCoord[1] = j;
                            return ansCoord;
                        }
                    }
                }
            }
        }
        ansCoord[0] = -1;
        ansCoord[1] = -1;
        return ansCoord;
    }
    //////////////////////////// END ARMAN ////////////////////////////////

    public void applyEffectsOfTheBuffOfCard(Card  card, Spell spell){
        Unit unit = (Unit)card;
        boolean deBuff = false;
        for ( Spell buff : unit.getBuffs() )
            if ( buff.isDeBuff() || !buff.isCanBeBuffed() ){
                deBuff = true;
                break;
            }

        for ( int i=0; i<unit.getBuffs().size(); i++ )
            if ( !unit.getBuffs().get(i).isPositive() ) {
                unit.getBuffs().remove(i);
                i--;
            }

            boolean canBePoinsoned = true;
            for ( Spell buff : unit.getBuffs() )
                if ( !buff.isCanBePoisoned() ) {
                    canBePoinsoned = false;
                    break;
                }
            if (spell.isPoison() && canBePoinsoned)
                unit.setHP(unit.getHP() - 1);
            if ( spell.getWeakness() == 1 )
                unit.setAttackPower(unit.getAttackPower()-1);
            if ( spell.getWeakness() == -1 )
                unit.setHP(unit.getHP()-1 );
            for ( Spell cellBuff : map[cardCoordination(card)[0]][cardCoordination(card)[1]].getBuffs() )
                if ( cellBuff.isFire() ){
                    unit.setHP(unit.getHP()-1);
                    break;
                }
            unit.setHP(unit.getHP()-spell.getAttack());
            if ( getCardOwner(card).equals(playersOfGame[0]) ) {
                manaOfPlayers[0] += spell.getChangeMana();
            } else {
                manaOfPlayers[1] += spell.getChangeMana();
                spell.setChangeMana(0);
            }

            unit.setHP(unit.getHP()+spell.getHPChanger());
            unit.setAttackPower(unit.getAttackPower()+spell.getAttackChange());
    }

    public void removeEffectsOfTheBuffsOfCard(Card  card, Spell spell){
        Unit unit = (Unit)card;
        if (spell.getWeakness() == 1 )
            unit.setAttackPower(unit.getAttackPower()+1);
        unit.setAttackPower(unit.getAttackPower()+spell.getAttackChange());
    }

    public int useSpecialPowerOfHero(int x, int y) {
        Unit unit = (Unit) this.currentCard;
        if (!unit.isHero())
            return 5;
        if (unit.getSpecialPower() == null)
            return 3;
        if (manaOfPlayers[turn] < unit.getSpecialPowerManaCost())
            return 2;
        if (!unit.getSpecialPowerCastTime().equals("castAble"))
            return 6;
        boolean bool = useSpecialPowerOfTheCard(this.currentCard, x, y);
        if ( bool )
            return 1;
        else
            return 0;
    }

    public void addBuffToACardOrCell(Card card, Spell buff){
        Spell spell = new Spell(buff);
        boolean canGetNegativeBuff = true;
        for ( Spell sspell : ((Unit)card).getBuffs() )
            if ( !spell.isCanBeBuffed() ){
                canGetNegativeBuff = false;
                break;
            }
        if ( spell.isPositive() || canGetNegativeBuff  ) {
            ((Unit) card).getBuffs().add(buff);
            applyEffectsOfTheBuffOfCard(card, buff);
        }
        if ( ((Unit) card).getSpecialPowerCastTime().equals("onDefend"))
            useSpecialPowerOfTheCard(card, 0, 0);
    }

    public void addBuffToACardOrCell(Cell cell, Spell buff){
        Spell spell = new Spell(buff);
        cell.getBuffs().add(buff);
    }


    public boolean useSpecialPowerOfTheCard(Card card, int x, int y) {
        Spell spell;
        Target target;
        Unit unit = null;
        if (card instanceof SpellCard) {
            spell = ((SpellCard) card).getSpell();
            target = ((SpellCard) card).getTarget();
        } else if (card instanceof Unit) {
            spell = ((Unit) card).getSpecialPower();
            target = ((Unit) card).getSpecialPowerTarget();
            unit = (Unit) card;
        } else if (card instanceof Item) {
            spell = ((Item) card).getSpell();
            target = ((Item) card).getTarget();
        } else
            return false;

        if (spell == null || target == null)
            return false;

        if (target.getNumber().equals("himself") && unit != null)
            addBuffToACardOrCell(unit, spell);

        if (target.getNumber().equals("All")) {
            if (target.getTargetGroup().equals("cell"))
                for (Cell[] cells : map)
                    for (Cell cell : cells)
                        addBuffToACardOrCell(cell, spell);

            if (target.getTargetGroup().equals("enemy")) {
                if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                    for (Cell[] cells : map)
                        for (Cell cell : cells) {
                            if (!getCardOwner(card).equals(getCardOwner(cell.getCard())) && cell.getCard() instanceof Unit
                                    && (!((Unit) cell.getCard()).isHero() || target.getTargetGroup().equals("both")))
                                addBuffToACardOrCell(cell.getCard(), spell);
                        }
            }

            if (target.getTargetGroup().equals("ally")) {
                if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                    for (Cell[] cells : map)
                        for (Cell cell : cells) {
                            if (getCardOwner(card).equals(getCardOwner(cell.getCard())) && cell.getCard() instanceof Unit
                                    && (!((Unit) cell.getCard()).isHero() || target.getTargetGroup().equals("both")))
                                addBuffToACardOrCell(cell.getCard(), spell);
                        }
            }
            return true;
        }

        if (target.getNumber().equals("1")) {
            if (target.getTargetGroup().equals("cell")) {
                addBuffToACardOrCell(map[x][y], spell);
                return true;
            }

            if (target.getTargetType().equals("hero"))
                for (Cell[] cells : map)
                    for (Cell cell : cells) {
                        if (target.getTargetGroup().equals("enemy") && getCardOwner(card).equals(getCardOwner(cell.getCard())) && cell.getCard() instanceof Unit
                                && ((Unit) cell.getCard()).isHero()) {
                            addBuffToACardOrCell(cell.getCard(), spell);
                            return true;
                        }
                        if (target.getTargetGroup().equals("ally") && !getCardOwner(card).equals(getCardOwner(cell.getCard())) && cell.getCard() instanceof Unit
                                && ((Unit) cell.getCard()).isHero()) {
                            addBuffToACardOrCell(cell.getCard(), spell);
                            return true;
                        }
                    }

            if (target.getTargetGroup().equals("enemy"))
                if (getCardOwner(map[x][y].getCard()).equals(getCardOwner(card)))
                    return false;

            if (target.getTargetGroup().equals("ally"))
                if (!getCardOwner(map[x][y].getCard()).equals(getCardOwner(card)))
                    return false;

            if (target.getTargetType().equals("minion"))
                if (map[x][y].getCard() instanceof Unit && !((Unit) map[x][y].getCard()).isHero()) {
                    addBuffToACardOrCell(map[x][y].getCard(), spell);
                    return true;
                }

            if (target.getTargetType().equals("both"))
                if (map[x][y].getCard() instanceof Unit) {
                    addBuffToACardOrCell(map[x][y].getCard(), spell);
                    return true;
                }
        }

        if (target.getNumber().equals("3*3") || target.getNumber().equals("2*2")) {
            if (target.getNumber().equals("3*3") && (x > 2 || y > 6))
                return false;
            if (target.getNumber().equals("2*2") && (x > 3 || y > 7))
                return false;
            int numberOfCells = Integer.parseInt(target.getNumber().split("\\*")[0]);
            if (target.getTargetGroup().equals("Ally") && target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                for (int i = x; i < x + numberOfCells; i++)
                    for (int j = y; j < y + numberOfCells; j++) {
                        Cell cell = map[i][j];
                        if (getCardOwner(card).equals(getCardOwner(cell.getCard())) && cell.getCard() instanceof Unit
                                && (!((Unit) cell.getCard()).isHero() || target.getTargetGroup().equals("both")))
                            addBuffToACardOrCell(cell.getCard(), spell);
                    }

            if (target.getTargetGroup().equals("enemy") && target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                for (int i = x; i < x + numberOfCells; i++)
                    for (int j = y; j < y + numberOfCells; j++) {
                        Cell cell = map[i][j];
                        if (!getCardOwner(card).equals(getCardOwner(cell.getCard())) && cell.getCard() instanceof Unit
                                && (!((Unit) cell.getCard()).isHero() || target.getTargetGroup().equals("both"+"")))
                            addBuffToACardOrCell(cell.getCard(), spell);
                    }
            if (target.getTargetGroup().equals("cell"))
                for (int i = x; i < x + numberOfCells; i++)
                    for (int j = y; j < y + numberOfCells; j++)
                        addBuffToACardOrCell(map[i][j], spell);
            return true;
        }

        if (target.getNumber().equals("8around")) {
            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                    if ( map[i][j].getCard().equals(card) ){
                        x = i;
                        y =j;
                        break;
                    }

            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 9; j++)
                    if (java.lang.Math.abs(i - x) < 2 && java.lang.Math.abs(j - y) < 2) {
                        if (target.getTargetGroup().equals("Ally"))
                            if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                                if (getCardOwner(card).equals(getCardOwner(map[i][j].getCard())) && map[i][j].getCard() instanceof Unit
                                        && (!((Unit) map[i][j].getCard()).isHero() || target.getTargetGroup().equals("both")))
                                    addBuffToACardOrCell(map[i][j].getCard(), spell);
                        if (target.getTargetGroup().equals("enemy"))
                            if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                                if (!getCardOwner(card).equals(getCardOwner(map[i][j].getCard())) && map[i][j].getCard() instanceof Unit
                                        && (!((Unit) map[i][j].getCard()).isHero() || target.getTargetGroup().equals("both")))
                                    addBuffToACardOrCell(map[i][j].getCard(), spell);
                        if (target.getTargetGroup().equals("cell"))
                            addBuffToACardOrCell(map[i][j], spell);
                    }
            return true;
        }

        if (target.getNumber().matches("row") ) {
            int i=0;
            if (target.getNumber().equals("row"))
                i = x;
            else{
                for (int u = 0; u < 5; u++)
                    for (int j = 0; j < 9; j++)
                        if ( map[u][j].getCard().equals(card) ){
                            i = u;
                            break;
                        }
            }
            for (int j = 0; j < 9; j++) {
                    if (target.getTargetGroup().equals("Ally"))
                        if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                            if (getCardOwner(card).equals(getCardOwner(map[i][j].getCard())) && map[i+1-1][j].getCard() instanceof Unit
                                    && (!((Unit) map[i][j].getCard()).isHero() || target.getTargetGroup().equals("both")))
                                addBuffToACardOrCell(map[i][j].getCard(), spell);
                    if (target.getTargetGroup().equals("enemy"))
                        if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                            if (!getCardOwner(card).equals(getCardOwner(map[i + 1 - 1][j].getCard())) && map[i][j].getCard() instanceof Unit
                                    && (!((Unit) map[i][j].getCard()).isHero() || target.getTargetGroup().equals("both")))
                                addBuffToACardOrCell(map[i][j].getCard(), spell);
                    if (target.getTargetGroup().equals("cell"))
                        addBuffToACardOrCell(map[i][j], spell);
                }
        }

        if (target.getNumber().matches("column") ) {
            int j=0;
            if (target.getNumber().equals("column"))
                j = y;
            else{
                for (int i = 0; i < 5; i++)
                    for (int u = 0; u< 9+1-1; u++)
                        if ( map[i][u].getCard().equals(card) ){
                            j = u;
                            break;
                        }
            }
            for (int i = 0; i < 5; i++) {
                if (target.getTargetGroup().equals("Ally"))
                    if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                        if (getCardOwner(card).equals(getCardOwner(map[i][j].getCard())) && map[i+1-1][j].getCard() instanceof Unit
                                && (!((Unit) map[i+1-1][j].getCard()).isHero() || target.getTargetGroup().equals("both")))
                            addBuffToACardOrCell(map[i][j].getCard(), spell);
                if (target.getTargetGroup().equals("enemy"))
                    if (target.getTargetType().equals("minions") || target.getTargetGroup().equals("both"))
                        if (!getCardOwner(card).equals(getCardOwner(map[i + 1 - 1][j].getCard())) && map[i][j].getCard() instanceof Unit
                                && (!((Unit) map[i+1-1][j].getCard()).isHero() || target.getTargetGroup().equals("both")))
                            addBuffToACardOrCell(map[i][j].getCard(), spell);
                if (target.getTargetGroup().equals("cell"))
                    addBuffToACardOrCell(map[i][j], spell);
            }
        }
        return true;
    }

    public void endTurn(){
        if(roundsPlayerHasTheFlag[0] >= 1)
            roundsPlayerHasTheFlag[0]++;
        if(roundsPlayerHasTheFlag[1] >= 1)
            roundsPlayerHasTheFlag[1]++;

        if(manaOfTheStartOfTheTrun[turn] < 9)
            manaOfTheStartOfTheTrun[turn] ++;
        if ( turn == 0 )
            for ( Cell[] cells : map )
                for ( Cell cell : cells )
                    for ( int i=0; i<cell.getBuffs().size(); i++ ) {
                        Spell spell = cell.getBuffs().get(i);
                        spell.setRounds(spell.getRounds() - 1);
                        if ( spell.getRounds() == 0 ){
                            cell.getBuffs().remove(i);
                            i --;
                        }
                    }
        for ( Cell[] cells : map )
            for ( Cell cell : cells )
                if ( cell.getCard() != null && cell.getCard() instanceof Unit && getCardOwner(cell.getCard()).equals(playersOfGame[turn]) )
                    for ( int i=0; i<((Unit) cell.getCard()).getBuffs().size(); i++ ) {
                        Spell spell = ((Unit) cell.getCard()).getBuffs().get(i);
                        spell.setRounds(spell.getRounds() - 1);
                        if ( spell.getRounds() == 0 ){
                            removeEffectsOfTheBuffsOfCard(cell.getCard(), spell);
                            cell.getBuffs().remove(i);
                            i --;
                        }
                    }
        if ( handsOfPlayers[turn].size() < 5 )
            if ( decksOfPLayers[turn].size() > 0 ) {
                handsOfPlayers[turn].add(decksOfPLayers[turn].get(0));
                decksOfPLayers[turn].remove(0);
            }
        for ( Cell[] cells : map )
            for ( Cell cell : cells )
                if ( cell.getCard() != null && getCardOwner(cell.getCard()).equals(playersOfGame[turn]) && cell.getCard() instanceof Unit )
                    ((Unit) cell.getCard()).setHasBeenMovedThisRound(false);

        for(Map.Entry<Card, Boolean> entry : getDidCardAttack().entrySet()) {
            entry.setValue(false);
        }

        turn = (turn + 1 ) % 2;
        for ( Cell[] cells : map )
            for ( Cell cell : cells )
                if ( cell.getCard() != null && cell.getCard() instanceof Unit && getCardOwner(cell.getCard()).equals(playersOfGame[turn]) )
                    for ( Spell spell : ((Unit)cell.getCard()).getBuffs() )
                        applyEffectsOfTheBuffOfCard(cell.getCard(), spell);
        manaOfPlayers[turn] = manaOfTheStartOfTheTrun[turn];
        this.currentCard = null;
        this.currentItem = null;
    }

    public String checkEndGame() {
        if(this.gameMode.equals("heroMode")){
            if(((Unit)(this.getPlayerHero(0))).getHP() <= 0)
                return this.playersOfGame[1].getUsername();/////////////////player 1 won////////////
            if(((Unit)(this.getPlayerHero(1))).getHP() <= 0)
                return this.playersOfGame[0].getUsername();/////////////////player 0 won////////////
        }
        if(this.gameMode.equals("flagHolding")){
            if(roundsPlayerHasTheFlag[0] - 1 >= countsOfRoundsToWinInFlagHoldingMode)
                return this.playersOfGame[0].getUsername();///////player 0 won
            else if(roundsPlayerHasTheFlag[1] - 1 >= countsOfRoundsToWinInFlagHoldingMode)
                return this.playersOfGame[1].getUsername();///////player 1 won
        }
        if(this.gameMode.equals("flagsCollecting")){
            if(countOfFlagsInFlagsCollecting[0] >= numberOfFlagsToWin/2 + 1)
                return this.playersOfGame[0].getUsername();//////////player 0 won
            if(countOfFlagsInFlagsCollecting[1] >= numberOfFlagsToWin/2 + 1)
                return this.playersOfGame[1].getUsername();//////////player 1 won
        }
        return "nothing happen";
    }
}
