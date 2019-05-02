package Controller;
///////////////////////////////////////////////////////////ali inja comment gozashte

import View.*;
import Modules.*;
import sun.awt.geom.AreaOp;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Server {
    private Shop shop;
    private Player currentPlayer = null;
    private Game currentGame;
    private ArrayList<Game> allGames;
    private Viewer viewer;


    public ArrayList<Player> getAllPlayers() {
        return Player.getPlayers();
    }

    public ArrayList<Game> getAllGames() {
        return allGames;
    }

    //================================= Account Functions ===============================//
    public boolean userExists(String username) {
        ArrayList<Player> allPlayers = Player.getPlayers();
        for (int i = 0; i < allPlayers.size(); i++)
            if (allPlayers.get(i).getUsername().equals(username))
                return true;
        return false;
    }

    public void createAccount(String username, String password) {
        new Player(username, password);
    }

    public boolean isValidLogin(String username, String password) {
        Player player = Player.getPlayerObj(username);
        if (player.getPassword().equals(password))
            return true;
        return false;
    }

    public void login(String username, String password) {
        currentPlayer = Player.getPlayerObj(username);
    }

    public void save() {
    }

    public void logOut() {
        currentPlayer = null;
    }

//================================= Shop functions ===================================//

    public void searchCardInShop(String cardName) {
        for (int i = 0; i < shop.getCards().size(); i++) {
            if (shop.getCards().get(i).getName() == cardName) {
                viewer.printSearchedID(shop.getCards().get(i).getCardID());
                break;
            }
        }
    }

    public void searchCardInCollection(String cardName) {
        for (int i = 0; i < currentPlayer.getCollection().size(); i++) {
            if (currentPlayer.getCollection().get(i).getName() == cardName)
                viewer.printSearchedID(currentPlayer.getCollection().get(i).getCardID());
        }
    }

    public void buyCard(String name) {
        shop.buy(currentPlayer, name);
    }

    public void sellCardWithID(int cardID) {
        shop.sellWithID(currentPlayer, cardID);
    }

    public void sellCardWithName(String name) {
        shop.sellWithName(currentPlayer, name);
    }


    public ArrayList<Card> getCardsInShop() {
        return shop.getCards();
    }

    public int getPlayerMoney() {
        return currentPlayer.getMoney();
    }

    public int getNumberOfItemsOfPlayers() {
        int counter = 0;
        for (int i = 0; i < currentPlayer.getCollection().size(); i++) {
            if (currentPlayer.getCollection().get(i) instanceof Item)
                counter++;
        }
        return counter;
    }

    //================================= Collection Functions ===============================//
    public ArrayList<Card> cardsInCollection() {
        return currentPlayer.getCollection();
    }

    public ArrayList<Integer> searchCollection(String keyword) {
        return Collection.searchCard(currentPlayer, keyword);
    }

    public boolean isDeckExists(String keyword) {
        for (int i = 0; i < currentPlayer.getAllDecks().size(); i++)
            if (currentPlayer.getAllDecks().get(i).getName().equals(keyword))
                return true;
        return false;
    }

    public void createDeckForPlayer(String keyword) {
        Deck d = new Deck(keyword);
        currentPlayer.getAllDecks().add(d);
    }

    public void deleteDeckPlayer(String keyword) {
        for (int i = 0; i < currentPlayer.getAllDecks().size(); i++)
            if (currentPlayer.getAllDecks().get(i).getName().equals(keyword))
                currentPlayer.getAllDecks().remove(i);
    }

    public boolean isCardInCollection(int cardID) {
        for (int i = 0; i < currentPlayer.getCollection().size(); i++)
            if (currentPlayer.getCollection().get(i).getCardID() == cardID)
                return true;
        return false;
    }

    public boolean isCardInDeck(int cardID, String keyword) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        for (int j = 0; j < d.getCards().size(); j++)
            if (d.getCards().get(j).getCardID() == cardID)
                return true;
        return false;
    }

    public boolean isDeckFull(String keyword) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        if (d.getCards().size() == 20)
            return true;
        return false;
    }

    public boolean isCardIDHero(int cardID) {
        for (int i = 0; i < currentPlayer.getCollection().size(); i++)
            if (currentPlayer.getCollection().get(i).getCardID() == cardID) {
                if (currentPlayer.getCollection().get(i) instanceof Unit && ((Unit) currentPlayer.getCollection().get(i)).isHero())
                    return true;
            }
        return false;
    }

    public boolean deckHasHero(String keyword) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        for (int i = 0; i < d.getCards().size(); i++)
            if (d.getCards().get(i) instanceof Unit && ((Unit) d.getCards().get(i)).isHero())
                return true;
        return false;
    }

    public void addCardToTheDeck(String keyword, int cardID) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        Card c = Collection.searchCard(currentPlayer, cardID);
        d.getCards().add(c);
    }

    public void deleteCardFromDeck(String keyword, int cardID) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        Card c = Collection.searchCard(currentPlayer, cardID);
        d.getCards().remove(c);
    }

    public boolean isDeckValid(String keyword) {
        if (deckHasHero(keyword) && isDeckFull(keyword))
            return true;
        return false;
    }

    public void setMainDeck(String keyword) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        currentPlayer.setMainDeck(d);
        currentPlayer.getAllDecks().remove(d);
    }

    public Deck getDeck(String keyword) {
        return Collection.searchDeck(currentPlayer, keyword);
    }

    public ArrayList<Deck> getPlayerDecks() {
        return currentPlayer.getAllDecks();
    }

    public Deck getPlayerMainDeck() {
        return currentPlayer.getMainDeck();
    }

    //================================= Battle Functions ===============================//
    public String showPlayersMana() {
        String ans = "";
        ans = ans.concat(currentGame.getPlayersOfGame()[0].getUsername() + " " + currentGame.getManaOfPlayers()[0] + " - ");
        ans = ans.concat(currentGame.getPlayersOfGame()[1].getUsername() + " " + currentGame.getManaOfPlayers()[1]);
        return ans;
    }

    public String showGameModeInfo() {
        String ans = "Game Mode => ";
        if (currentGame.getGameMode().equals("heroMode")) {
            ans = ans.concat("Hero Mode\n1. Health Of Hero Player " + currentGame.getPlayersOfGame()[0].getUsername() + " : " + ((Unit) currentGame.getPlayerHero(0)).getHP() + "\n");
            ans = ans.concat("2. Health Of Hero Player " + currentGame.getPlayersOfGame()[1].getUsername() + " : " + ((Unit) currentGame.getPlayerHero(1)).getHP() + "\n");
        } else if (currentGame.getGameMode().equals("flagHolding")) {
            int[] coord = new int[2];
            coord[0] = currentGame.getFlagHoldedCoordination()[0];
            coord[1] = currentGame.getFlagHoldedCoordination()[1];
            ans = ans.concat("Flag Holding Mode\nFlag Coordination = X : " + coord[0] + " , Y : " + coord[1] + "\n");
            if (currentGame.getFlagOwner(coord[0], coord[1]) == null)
                ans = ans.concat("Nobody has the flag !!!");
            else
                ans = ans.concat("Player " + currentGame.getFlagOwner(coord[0], coord[1]).getUsername() + " Has The Flag !");
        } else {
            ArrayList<String> flagsInfo = currentGame.getFlagsInfo();
            ans = ans.concat("Flags Collecting Mode\n Soldiers That have The flag :\n");
            for (int i = 0; i < flagsInfo.size(); i++)
                ans = ans.concat(i + 1 + ". " + flagsInfo.get(i) + "\n");
        }
        return ans;
    }

    public ArrayList<String> getPlayerUnitsInfo(String turn) {
        return currentGame.getPlayerUnitsInfo(turn);
    }

    public String showCardInfo(int cardID) {
        Card card = currentGame.findCardByID(cardID);
        if (card == null)
            return "This Card ID is invalid !!!";
        else if (card instanceof Unit && ((Unit) card).isHero()) {
            String ans = "";
            ans = ans.concat("Hero :\nName : " + card.getName() + "\nCost : " + ((Unit) card).getManaCost() + "\nDesc : " + ((Unit) card).getDescription());
            return ans;
        } else if (card instanceof Unit) {
            String ans = "";
            String canCombo;
            if (((Unit) card).getCanCombo())
                canCombo = "Yes";
            else
                canCombo = "No";
            ans = ans.concat("Minion :\nName : " + card.getName() + "\nHP : " + ((Unit) card).getHP() + " , AP : " + ((Unit) card).getAttackPower()
                    + " , MP : " + ((Unit) card).getManaCost() + "\nRange : " + ((Unit) card).getRange() + "\nCombo-Ability : "
                    + canCombo + "\nCost : " + ((Unit) card).getManaCost() + "\nDesc : " + ((Unit) card).getDescription());
            return ans;
        }
        String ans = "";
        ans = ans.concat("Spell :\nName : " + card.getName() + "\nMP : " + ((SpellCard) card).getManaCost() + "\nDesc : " + ((SpellCard) card).getDescription());
        return ans;
    }


    public ArrayList<String> getAllMinionsInfo(String whom) {
        ArrayList<String> ans = new ArrayList<>();


        return ans;
    }

    public boolean selectCard(int cardID) {
        if (this.currentGame.findCardByID(cardID) == null)
            return false;
        else {
            for (Cell[] cells : this.currentGame.getMap())
                for (Cell cell : cells)
                    if (cell.getCard().getCardID() == cardID && this.currentGame.getCardOwner(cell.getCard()).equals
                            (this.currentGame.getPlayersOfGame()[this.currentGame.getTurn()])) {
                        this.currentGame.setCurrentCard(cell.getCard());
                        return true;
                    }
        }
        return false;
    }

//    public boolean selectItem(int cardID){
//        for(int i = 0; i < currentGame.getCollectableItems()[currentGame.getTurn()].size(); i++){
//            if()
//        }
//    }

    public String getCurrentItemInfo(){
        Item item = currentGame.getCurrentItem();
        return "name : " + item.getName() + "\ndescription : " + item.getDescription();
    }

    public int moveCurrentCard(int x, int y) {
        if (this.currentGame.getCurrentCard() == null || !(this.currentGame.getCurrentCard() instanceof Unit))
            return 4;
        return this.currentGame.moveCurrentCard(x, y);
    }

    //////////////////////////// ARMAN ////////////////////////////////
    public String attack(int opponentCardID){
        if(currentGame.getCurrentCard() == null)
            return "No Unit Selected For Attack !";
        else if(!currentGame.isValidUnitInMap(opponentCardID) || currentGame.getCardOwner(currentGame.findCardByID(opponentCardID)).equals(currentGame.getPlayersOfGame()[currentGame.getTurn()]))
            return "Invalid Opponent's Card ID !";
        else if(!(currentGame.getCurrentCard() instanceof Unit) || currentGame.getDidCardAttack().get(currentGame.getCurrentCard()) )
            return "Card with CardID : " + currentGame.getCurrentCard().getCardID() + " can't attack !";
        else if(!currentGame.isInAttackRange(currentGame.getCurrentCard(),opponentCardID))
            return "Opponent minion is unavailable for attack !";
        else {
            currentGame.attack(opponentCardID);
            return "Attack Was Successful !";
        }
    }
    public String comboAttack(int opponentCardID, ArrayList<Integer> comboAttackers){
        if(!currentGame.isValidUnitInMap(opponentCardID) || currentGame.getCardOwner(currentGame.findCardByID(opponentCardID)).equals(currentGame.getPlayersOfGame()[currentGame.getTurn()]))
            return "Invalid Opponent's Card ID !";
        String errorLog = "";
        for(int i = 0 ; i < comboAttackers.size() ; i++){
            int cardID = comboAttackers.get(i);
            if(!currentGame.isValidUnitInMap(cardID) || !currentGame.getCardOwner(currentGame.findCardByID(cardID)).equals(currentGame.getPlayersOfGame()[currentGame.getTurn()]))
            errorLog = errorLog.concat("Card with CardID : " + currentGame.findCardByID(cardID).getCardID() + " is Not Valid !\n");
        }
        if(errorLog.length() != 0)
            return errorLog;
        for(int i = 0 ; i < comboAttackers.size() ; i++){
            int cardID = comboAttackers.get(i);
            if(!(currentGame.findCardByID(cardID) instanceof Unit) || currentGame.getDidCardAttack().get(currentGame.findCardByID(cardID)) )
                errorLog = errorLog.concat("Card with CardID : " + currentGame.findCardByID(cardID).getCardID() + " can't attack !\n");
        }
        if(errorLog.length() != 0)
            return errorLog;
        for(int i = 0 ; i < comboAttackers.size() ; i++){
            int cardID = comboAttackers.get(i);
            if(!currentGame.isInAttackRange(currentGame.findCardByID(cardID),opponentCardID))
                errorLog = errorLog.concat("Opponent minion is unavailable for attack for Card with cardID : " + cardID + " !");
        }
        if(errorLog.length() != 0)
            return errorLog;
        currentGame.comboAttack(comboAttackers,opponentCardID);
        return "Combo Attack was successful !";
    }
    public String getCollectablesInfo(){
        String ans = "";
        ArrayList<Item> items = currentGame.getCollectableItems()[currentGame.getTurn()];
        for(int i = 0 ; i < items.size() ; i++)
            ans = ans.concat( i+1 + ". Name : " + items.get(i).getName() + "\n");
        return ans;
    }
    public String getNextCardInfo(){
        Card card = currentGame.getDecksOfPLayers()[currentGame.getTurn()].get(0);
        return "Name : " + card.getName() + " , CardID : " + card.getCardID();
    }
    public String getInfoCardInGraveyard(int cardID){
        Card card = currentGame.searchCardByIDinGraveyard(cardID);
        if(card == null)
            return "This Card Does not exists in the graveyard !!!";
        return "Card Name : " + card.getName();
    }
    public String getCardsInfoGraveyard(){
        String ans = "";
        ArrayList<Card> cards = currentGame.getGraveYard();
        for(int i = 0 ; i < cards.size() ; i++)
            ans = ans.concat(i+1 + ". Name : " + cards.get(i).getName() + " , CardID : " + cards.get(i).getCardID());
        return ans;
    }
    //////////////////////////// END ARMAN ////////////////////////////////

    public int useSpecialPowerOfHero(int x, int y) {
        if (this.currentGame.getCurrentCard() == null || !(this.currentGame.getCurrentCard() instanceof Unit))
            return 4;
        return this.currentGame.useSpecialPowerOfHero(x, y);
    }

    public ArrayList<Card> showHand() {
        ArrayList<Card> hand = currentGame.getHandsOfPlayers()[currentGame.getTurn()];
        return hand;
    }

    public Card getNextCard() {
        Card card = currentGame.getDecksOfPLayers()[currentGame.getTurn()].get(0);
        return card;
    }

    public String insert(String cardName, int x, int y){
        Card card = currentGame.findCardByName(cardName);
        if (card == null)
            return "Invalid card name";
        else if (!currentGame.cardIsInHand(card))
            return "Invalid card name";
        else if (x > 4 || y > 8)
            return "Invalid target";
        else if(card instanceof Unit) {
            return insertUnit(card, x, y);
        }
        else if(card instanceof SpellCard)
            return insertSpell(card, x, y);
        else{
            return "Invalid card name";
        }
    }

    public String insertUnit(Card card, int x, int y) {
        if (currentGame.getMap()[x][y].getCard() != null)
            return "Invalid target";
        else if (!currentGame.isNearHero(x, y))
            return "Invalid target";/////////////////////////unit o faghat mishe nazdik hero gozasht
        else if (currentGame.getManaOfPlayers()[currentGame.getTurn()] < ((Unit) card).getManaCost())
            return "You dont have enough mana";
        else {
            currentGame.getMap()[x][y].setCard(card);
            currentGame.getHandsOfPlayers()[currentGame.getTurn()].remove(card);
            currentGame.decreaseManaOfPlayers(((Unit) card).getManaCost());
            return "Insert successful";
        }

    }

    public String insertSpell(Card card, int x, int y) {
        if(currentGame.getManaOfPlayers()[currentGame.getTurn()] < ((SpellCard)card).getManaCost())
            return "you dont have enough mana";
        else if(currentGame.useSpecialPowerOfTheCard(card, x, y)){
            currentGame.decreaseManaOfPlayers(((SpellCard) card).getManaCost());
            currentGame.getMap()[x][y].setCard(card);
            currentGame.getHandsOfPlayers()[currentGame.getTurn()].remove(card);
            return card.getName() + " with " + card.getCardID() + " inserted to " + "(" + x + "," + ")";
        }
        else
            return "Invalid target";
    }

    public String useItem(int x, int y){
        if(currentGame.getCurrentItem() == null)
            return "no item selected";
        else if(currentGame.useSpecialPowerOfTheCard(currentGame.getCurrentItem(), x, y))
            return "item used successfully";
        else
            return "invalid target";
    }

    public void endTurn() {

    }

    public String showCollectables() {
        ArrayList<Item> collectables = currentGame.getCollectableItems()[currentGame.getTurn()];
        String answer = "";
        for(int i = 0; i < collectables.size(); i++){
            answer.concat(collectables.get(i).getName() + "\n");
        }
        return answer;
    }

    public Item showCurrentItemInfo() {

    }

    public boolean useCurrentItem(int x, int y) {

    }

    public void showNextCard() {

    }

    public boolean enterGraveYard() {

    }

    public void endGame() {

    }


}