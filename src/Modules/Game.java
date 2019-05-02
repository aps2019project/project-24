package Modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import View.*;

public class Game {
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
    private Card currentCard;
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
        for (int i = 0; i < 2; i++) {
            decksOfPLayers[i] = new ArrayList<>();
            decksOfPLayers[i].addAll(playersOfGame[i].getMainDeck().getCards());
            primaryCards[i] = new ArrayList<>();
            primaryCards[i].addAll(decksOfPLayers[i]);
            collectableItems[i] = new ArrayList<>();
            Collections.shuffle(decksOfPLayers[i]);
        }
        handsOfPlayers = new ArrayList[2];
        for (int i = 0; i < 2; i++) {
            handsOfPlayers[i] = new ArrayList<>();
            for (int j = 0; j < 1; j++)
                handsOfPlayers[i].add(decksOfPLayers[i].get(j));
            for (int j = 0; j < 1; j++)
                decksOfPLayers[i].remove(0);
        }
        map = new Cell[5][9];
        for(int i = 0 ; i < 5 ; i++) {
            map[i] = new Cell[9];
            for (int j = 0; j < 9; j++)
                map[i][j] = new Cell();
        }
        this.gameMode = gameMode;
        this.numberOfFlagsToWin = numberOfFlagsToWin;
        this.currentCard = null;
        this.currentItem = null;
        this.areWeInTheGraveYard = false;
        this.graveYard = new ArrayList<>();
        this.manaOfPlayers = new int[2];
        for (int i = 0; i < 2; i++)
            manaOfPlayers[i] = 5;
        map[2][0].setCard(getPlayerHero(0));
        map[2][8].setCard(getPlayerHero(1));
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
            for (int j = 0; j < 9; j++)
                if (this.map[i][j].getItem().isFlag()) {
                    coord[0] = i;
                    coord[1] = j;
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
                if (this.map[i][j].getItem().isFlag())
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
        for(int i = 0; i < 2; i++)
            for(Card card : primaryCards[i])
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
                if (this.currentCard.getCardID() == map[i][j].getCard().getCardID()) {
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
        if (java.lang.Math.abs(priviousX - x) > 0 || java.lang.Math.abs(priviousY - y) == 2)
            return 3;
        if (map[x][y].getCard() != null)
            return 3;
        if (x - priviousX == 2 && map[x + 1][y] != null)
            return 3;
        if (priviousX - x == 2 && map[x - 1][y] != null)
            return 3;
        if (y - priviousY == 2 && map[x][y + 1] != null)
            return 3;
        if (priviousY - y == 2 && map[x][y - 1] != null)
            return 3;

        Unit unit = (Unit) this.currentCard;
        if (!unit.isCanMove())
            return 2;
        for (Spell buff : unit.getBuffs())
            if (buff.isStun())
                return 5;

        map[priviousX][priviousY] = null;
        map[x][y].setCard(currentCard);
        return this.currentCard.getCardID();
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
    public Card searchCardByIDinGraveyard(int cardID){
        ArrayList<Card> cards = this.getGraveYard();
        for(int i = 0 ; i < cards.size() ; i++)
            if(cards.get(i).getCardID() == cardID)
                return cards.get(i);
        return null;
    }
    //////////////////////////// END ARMAN ////////////////////////////////

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
        ((Unit)card).getBuffs().add(buff);
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


//    public boolean insertSpell(Card card, Cell cell) {
//
//    }
//
//    public void endTrun() {
//
//    }
//
//    public boolean useCurrentItem(int x, int y) {
//
//    }
//
//    public void sendCardToTHeGraveYard(Card card) {
//
//    }
//
//    public int checkEndGame() {
//
//    }


}
