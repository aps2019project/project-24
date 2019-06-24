package Controller;
///////////////////////////////////////////////////////////ali inja comment gozashte

import View.*;
import Modules.*;
import cardBuilder.CardBuilder;

import java.io.File;
import java.util.ArrayList;

public class Server {
    private Shop shop = new Shop();
    private Player currentPlayer = null;
    private Game currentGame;
    private ArrayList<Game> allGames;
    private Viewer viewer;

    {
        ArrayList<String> AICards = new ArrayList<>();
        AICards.add("1,1 7 10 11 12 18 20,1 9 11 11 13 17 18 21 22 26 38 36 37,1");
        AICards.add("5,2 3 5 8 9 13 19,2 3 5 8 12 15 15 19 23 27 30 33 39,18");
        AICards.add("7,6 10 12 14 15 16 17,6 7 10 14 16 16 20 24 25 28 29 31 34,12");
        // hero spell minion item
        for ( int i=0; i<Player.getAIplayers().size(); i++) {
            String[] str = AICards.get(i).split("\\,");
            for (int j = 0; j < 4; j++)
                outerloop:
                for (String s : str[j].split("\\s"))
                    for (int k=shop.getCards().size()-1; k>=0; k--) {
                        Card card = shop.getCards().get(k);
                        switch (j) {
                            case 0:
                                if (card.getCardID() / 100 == 3000 + Integer.parseInt(s)) {
                                    shop.buy(Player.getAIplayers().get(i), card.getName());
                                    continue outerloop;
                                }
                                break;
                            case 1:
                                if (card.getCardID() / 100 == 1000 + Integer.parseInt(s)) {
                                    shop.buy(Player.getAIplayers().get(i), card.getName());
                                    continue outerloop;
                                }
                                break;
                            case 2:
                                if (card.getCardID() / 100 == 2000 + Integer.parseInt(s)) {
                                    shop.buy(Player.getAIplayers().get(i), card.getName());
                                    continue outerloop;
                                }
                                break;
                            case 3:
                                if (card.getCardID() / 100 == 4000 + Integer.parseInt(s)) {
                                    shop.buy(Player.getAIplayers().get(i), card.getName());
                                    continue outerloop;
                                }
                                break;
                        }
                    }
                Player.getAIplayers().get(i).setMainDeck(new Deck());
                Player.getAIplayers().get(i).getMainDeck().getCards().addAll(Player.getAIplayers().get(i).getCollection());
            }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Server(){
        File folder = new File(".\\.\\");
        File[] listOfFiles = folder.listFiles();
        if ( listOfFiles != null )
        for (File file : listOfFiles) {
            if ( file.getName().matches("\\w+\\.(json)")) {
                Player player = CardBuilder.loadAPlayerFromJsonFile(file.getName().substring(0, file.getName().length() - 5));
//                if ( player.getCollection().size() > 0 && ( player.getCollection().get(0) ) instanceof Card )
  //                  System.out.println("salam");
                Player.getPlayers().add(player);
            }
        }
    }


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
        currentPlayer = new Player(username, password);
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
        for ( Player player : Player.getPlayers() )
            CardBuilder.createJsonFileFromTheObject(player);
    }

    public void logOut() {
        currentPlayer = null;
    }

//================================= Shop functions ===================================//

    public String searchCardInShop(String cardName) {
        for (int i = 0; i < shop.getCards().size(); i++)
            if (shop.getCards().get(i).getName().equals(cardName))
                return shop.getCards().get(i).getCardID() + "";
        return "couldnt find card in shop";
    }

    public String  searchCardInCollection(String cardName) {
        for (int i = 0; i < currentPlayer.getCollection().size(); i++) {
            if (currentPlayer.getCollection().get(i).getName().equals(cardName))
                return currentPlayer.getCollection().get(i).getCardID() + "" ;
        }
        return "couldnt find card in collection";
    }

    public void buyCard(String name) {
        shop.buy(currentPlayer, name);
    }

    public void removeSelledCardFromDecks(int cardID){
        ArrayList<Deck> decks = currentPlayer.getAllDecks();
        for(int i = 0 ; i < decks.size() ; i++){
            ArrayList<Card> cards = decks.get(i).getCards();
            for(int j = 0 ; j < cards.size() ; j++ )
                if(cards.get(j).getCardID() == cardID)
                    cards.remove(j);
        }
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

    public boolean isDeckFullForPlayer(Player p , String keyword) {
        Deck d = Collection.searchDeck(p, keyword);
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
        if ( d != null )
        for (int i = 0; i < d.getCards().size(); i++)
            if (d.getCards().get(i) instanceof Unit && ((Unit) d.getCards().get(i)).isHero())
                return true;
        return false;
    }

    public boolean deckHasHeroForPlayer(Player p , String keyword) {
        Deck d = Collection.searchDeck(p, keyword);
        for (int i = 0; i < d.getCards().size(); i++)
            if (d.getCards().get(i) instanceof Unit && ((Unit) d.getCards().get(i)).isHero())
                return true;
        return false;
    }

    public void addCardToTheDeck(String keyword, int cardID) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        Card c = Collection.searchCard(currentPlayer, cardID);
        if ( c instanceof Unit )
            d.getUnits().add((Unit)c);
        else if ( c instanceof SpellCard )
            d.getSpells().add((SpellCard)c);
        else
            d.getItems().add((Item) c);
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

    public boolean isDeckValidForPlayer(Player p , String keyword){
        if (deckHasHeroForPlayer(p,keyword) && isDeckFullForPlayer(p,keyword))
            return true;
        return false;
    }

    public void setMainDeck(String keyword) {
        Deck d = Collection.searchDeck(currentPlayer, keyword);
        currentPlayer.setMainDeck(d);
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
    public int[] getCardCoord(int cardID){
        return currentGame.cardCoordination(currentGame.findCardByID(cardID));
    }
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
    public boolean selectItem(int itemID){
        if(currentGame.findItemByItemID(itemID) == null)
            return false;
        else{
            currentGame.setCurrentItem(currentGame.findItemByItemID(itemID));
            return true;
        }
    }
    public boolean selectCard(int cardID) {
        if (this.currentGame.findCardByID(cardID) == null)
            return false;
        else {
            for (Cell[] cells : this.currentGame.getMap())
                for (Cell cell : cells)
                    if ( cell.getCard() != null && cell.getCard().getCardID() == cardID && this.currentGame.getCardOwner(cell.getCard()).equals
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
        if ( item == null )
            return "no item has beedn selected";
        else
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
        else if(currentGame.isUnitDisarm(currentGame.getCurrentCard()))
            return "This Unit is Disarm or Stunned and Cant attack !";
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
        if(currentGame.getDecksOfPLayers()[currentGame.getTurn()].size() == 0)
            return "Next Card : Nothing !";
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
    public Cell[][] getGameMap(){
        return currentGame.getMap();
    }
    public void newGame(){
        Player p1 = Player.getPlayers().get(0);
        Player p2 = Player.getPlayers().get(1);
        currentGame = new Game(p1,p2,"heroMode");
    }

    public String getCurrentPlayerName(){return currentPlayer.getUsername();}

    public boolean isValidMainDeck(String name){
        Player p = Player.getPlayerObj(name);
//        if ( p.getMainDeck() == null )
//            return false;
        String deckName = p.getMainDeck().getName();
        if(isDeckValidForPlayer(p,deckName))
            return true;
        return false;
    }

    public void startSinglePlayer(String gameMode){
        Player p1 = currentPlayer , p2 ;
        String mode = "";
        if(gameMode.equals("heromode")) {
            p2 = Player.getAIplayers().get(0);
            mode = "heroMode";
        }
        else if(gameMode.equals("flagholding")) {
            p2 = Player.getAIplayers().get(1);
            mode = "flagHolding";
        }
        else {
            p2 = Player.getAIplayers().get(2);
            mode = "flagsCollecting";
        }
        currentGame = new Game(p1,p2,mode);
    }

    public boolean isValidPlayer(String name){
        if(Player.getPlayerObj(name) == null)
            return false;
        return true;
    }

    public void startMultiPlayer(String name1 , String name2 , String gameMode){
        Player p1 = Player.getPlayerObj(name1);
        Player p2 = Player.getPlayerObj(name2);
        String mode = "";
        if(gameMode.equals("heromode"))
            mode = "heroMode";
        else if(gameMode.equals("flagholding"))
            mode = "flagHolding";
        else
            mode = "flagsCollecting";
        currentGame = new Game(p1,p2,mode);
    }
    public boolean canAIHeroMove(){
        int[] coord = currentGame.freeCellToMove();
        if(coord[0] == -1 || coord[1] == -1)
            return false;
        return true;
    }
    public boolean canAIHeroAttack(){
        int[] coord = currentGame.unitCoordCanAttack();
        if(coord[0] == -1 || coord[1] == -1)
            return false;
        return true;
    }
    public int[] coordAIHeroMove(){
        return currentGame.freeCellToMove();
    }
    public int[] coordAIHeroAttack(){
        return currentGame.unitCoordCanAttack();
    }
    public int getAIHeroCardID(){
        return currentGame.getPlayerHero(1).getCardID();
    }
    public int getCardIDByCoord(int x , int y){
        return currentGame.getMap()[x][y].getCard().getCardID();
    }
    public void resignGame(){
        Player p1 = currentGame.getPlayersOfGame()[0] , p2 = currentGame.getPlayersOfGame()[1];
        Player winner = currentGame.getPlayersOfGame()[(currentGame.getTurn()+1)%2];
        new EndedMatches(p1,p2,winner,10);
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
        if(currentGame.getDecksOfPLayers()[currentGame.getTurn()].size() != 0) {
            Card card = currentGame.getDecksOfPLayers()[currentGame.getTurn()].get(0);
            return card;
        }
        else return null;
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
            return "Invalid target";
        else if (currentGame.getManaOfPlayers()[currentGame.getTurn()] < ((Unit) card).getManaCost())
            return "You dont have enough mana";
        else {
            currentGame.getMap()[x][y].setCard(card);
            currentGame.getHandsOfPlayers()[currentGame.getTurn()].remove(card);
            currentGame.decreaseManaOfPlayers(((Unit) card).getManaCost());
            Cell currentCell = currentGame.getMap()[x][y];
            if(currentCell.getItems().size() != 0){
                for(int i = 0; i < currentCell.getItems().size(); i++){
                    if(currentCell.getItems().get(i).isFlag()){
                        if(currentGame.getGameMode().equals("flagHolding"))
                            currentGame.increaseRoundsPlayerHasTheFlag();
                        if(currentGame.getGameMode().equals("flagsCollecting"))
                            currentGame.increaseCountOfFlagsInFlagsCollecting();
                        ((Unit)card).setHasFlag(true);
                        ((Unit)card).addFlag(currentCell.getItems().get(i));
                    }
                    if(currentCell.getItems().get(i).isCollectAble()){
                        currentGame.addCollectableItems(currentCell.getItems().get(i));
                    }
                }
                currentCell.setItems(null);
            }
            if ( ((Unit) card).getSpecialPower() != null && ((Unit)card).getSpecialPowerCastTime().equals("onRespawn"))
                this.currentGame.useSpecialPowerOfTheCard(card, x, y);
            currentGame.getDidCardAttack().put(card, false);
            return "Insert successful";
        }

    }

    public String insertSpell(Card card, int x, int y) {
        if(currentGame.getManaOfPlayers()[currentGame.getTurn()] < ((SpellCard)card).getManaCost())
            return "you dont have enough mana";
        else if(currentGame.useSpecialPowerOfTheCard(card, x, y)){
            currentGame.decreaseManaOfPlayers(((SpellCard) card).getManaCost());
            currentGame.getHandsOfPlayers()[currentGame.getTurn()].remove(card);
            return card.getName() + " with " + card.getCardID() + " inserted to " + "(" + x + "," + y + ")";
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
        currentGame.endTurn();
    }

    public String showCollectables() {
        ArrayList<Item> collectables = currentGame.getCollectableItems()[currentGame.getTurn()];
        String answer = "";
        for(int i = 0; i < collectables.size(); i++){
            answer.concat(collectables.get(i).getName() + "\n");
        }
        return answer;
    }

//    public Item showCurrentItemInfo() {
//
//    }
//
//    public boolean useCurrentItem(int x, int y) {
//
//    }
//
//    public void showNextCard() {
//
//    }
//
//    public boolean enterGraveYard() {
//
//    }
//
    public String checkEndGame() {
        if( currentGame.checkEndGame().equals("nothing happen")){
            return "nothing happen";
        }
        else {
            int indexOfWinner = 0;
            String endGameCheckerSTR = currentGame.checkEndGame();
            if (currentGame.getPlayersOfGame()[1].getUsername().equals(endGameCheckerSTR))
                indexOfWinner = 1;
            if ( currentGame.getPlayersOfGame()[(indexOfWinner+1)%2] .getUsername().matches("AI\\w+") ) {
                getCurrentPlayer().setMoney(getCurrentPlayer().getMoney() + getCurrentPlayer().getStoryModeLevel() * 500 + 500);
                getCurrentPlayer().setStoryModeLevel((getCurrentPlayer().getStoryModeLevel() + 1) % 3);
            }
            else
                getCurrentPlayer().setMoney(getCurrentPlayer().getMoney() + 1000);
            getCurrentPlayer().setNumberOfWins(getCurrentPlayer().getNumberOfWins()+1);
            int time = 1;
            new EndedMatches(currentGame.getPlayersOfGame()[0], currentGame.getPlayersOfGame()[1], currentGame.getPlayersOfGame()[indexOfWinner], time);
            ///////////////////////////time cherte int nabayd bashe bayad az in saat maata bashe
            currentGame = null;
            return endGameCheckerSTR;
        }
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void setCurrentGame(Game currentGame) {
        this.currentGame = currentGame;
    }
}