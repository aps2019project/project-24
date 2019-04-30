package Controller;
///////////////////////////////////////////////////////////ali inja comment gozashte
import View.*;
import Modules.*;

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
    public ArrayList<Game> getAllGames() { return allGames; }
    //================================= Account Functions ===============================//
    public boolean userExists(String username){
        ArrayList<Player> allPlayers = Player.getPlayers();
        for(int i = 0 ; i < allPlayers.size() ; i++)
            if(allPlayers.get(i).getUsername().equals(username))
                return true;
        return false;
    }
    public void createAccount(String username,String password){
        new Player(username,password);
    }
    public boolean isValidLogin(String username , String password){
        Player player = Player.getPlayerObj(username);
        if(player.getPassword().equals(password))
            return true;
        return false;
    }
    public void login(String username, String password){
        currentPlayer = Player.getPlayerObj(username);
    }
    public void save(){}
    public void logOut(){currentPlayer = null;}

//================================= Shop functions ===================================//

    public void searchCardInShop(String cardName){
        for(int i = 0; i < shop.getCards().size(); i++) {
            if (shop.getCards().get(i).getName() == cardName) {
                viewer.printSearchedID(shop.getCards().get(i).getCardID());
                break;
            }
        }
    }
    public void searchCardInCollection(String cardName){
        for(int i = 0; i < currentPlayer.getCollection().size(); i++){
            if(currentPlayer.getCollection().get(i).getName() == cardName)
                viewer.printSearchedID(currentPlayer.getCollection().get(i).getCardID());
        }
    }

    public void buyCard(String name){
        shop.buy(currentPlayer, name);
    }

    public void sellCardWithID(int cardID){
        shop.sellWithID(currentPlayer, cardID);
    }

    public void sellCardWithName(String name){
        shop.sellWithName(currentPlayer, name);
    }


    public ArrayList<Card> getCardsInShop(){
        return shop.getCards();
    }
    public int getPlayerMoney(){
        return currentPlayer.getMoney();
    }
    public int getNumberOfItemsOfPlayers(){
        int counter = 0;
        for(int i = 0; i < currentPlayer.getCollection().size(); i++){
            if(currentPlayer.getCollection().get(i) instanceof Item)
                counter++;
        }
        return counter;
    }

    //================================= Collection Functions ===============================//
    public ArrayList<Card> cardsInCollection(){
        return currentPlayer.getCollection();
    }
    public ArrayList<Integer> searchCollection(String keyword){
        return Collection.searchCard(currentPlayer,keyword);
    }
    public boolean isDeckExists(String keyword){
        for(int i = 0 ; i < currentPlayer.getAllDecks().size() ; i++ )
            if(currentPlayer.getAllDecks().get(i).getName().equals(keyword))
                return true;
        return false;
    }
    public void createDeckForPlayer(String keyword){
        Deck d = new Deck(keyword);
        currentPlayer.getAllDecks().add(d);
    }
    public void deleteDeckPlayer(String keyword){
        for(int i = 0 ; i < currentPlayer.getAllDecks().size() ; i++)
            if(currentPlayer.getAllDecks().get(i).getName().equals(keyword))
                currentPlayer.getAllDecks().remove(i);
    }
    public boolean isCardInCollection(int cardID){
        for(int i = 0 ; i < currentPlayer.getCollection().size() ; i++)
            if(currentPlayer.getCollection().get(i).getCardID() == cardID)
                return true;
        return false;
    }
    public boolean isCardInDeck(int cardID , String keyword){
        Deck d = Collection.searchDeck(currentPlayer,keyword);
        for(int j = 0 ; j < d.getCards().size() ; j++)
            if(d.getCards().get(j).getCardID() == cardID)
                return true;
        return false;
    }
    public boolean isDeckFull(String keyword){
        Deck d = Collection.searchDeck(currentPlayer,keyword);
        if(d.getCards().size() == 20)
            return true;
        return false;
    }
    public boolean isCardIDHero(int cardID){
        for(int i = 0 ; i < currentPlayer.getCollection().size() ; i++)
            if(currentPlayer.getCollection().get(i).getCardID() == cardID){
                if( currentPlayer.getCollection().get(i) instanceof Unit && ((Unit)currentPlayer.getCollection().get(i)).isHero())
                    return true;
            }
        return false;
    }
    public boolean deckHasHero(String keyword){
        Deck d = Collection.searchDeck(currentPlayer,keyword);
        for(int i = 0 ; i < d.getCards().size() ; i++)
            if(d.getCards().get(i) instanceof Unit && ((Unit)d.getCards().get(i)).isHero())
                return true;
        return false;
    }
    public void addCardToTheDeck(String keyword , int cardID){
        Deck d = Collection.searchDeck(currentPlayer,keyword);
        Card c = Collection.searchCard(currentPlayer,cardID);
        d.getCards().add(c);
    }
    public void deleteCardFromDeck(String keyword , int cardID){
        Deck d = Collection.searchDeck(currentPlayer,keyword);
        Card c = Collection.searchCard(currentPlayer,cardID);
        d.getCards().remove(c);
    }
    public boolean isDeckValid(String keyword){
        if(deckHasHero(keyword) && isDeckFull(keyword))
            return true;
        return false;
    }
    public void setMainDeck(String keyword){
        Deck d = Collection.searchDeck(currentPlayer,keyword);
        currentPlayer.setMainDeck(d);
        currentPlayer.getAllDecks().remove(d);
    }
    public Deck getDeck(String keyword){
        return Collection.searchDeck(currentPlayer,keyword);
    }
    public ArrayList<Deck> getPlayerDecks(){
        return currentPlayer.getAllDecks();
    }
    public Deck getPlayerMainDeck(){
        return currentPlayer.getMainDeck();
    }

    //================================= Battle Functions ===============================//
    public String showPlayersMana(){
        String ans = "";
        ans = ans.concat(currentGame.getPlayersOfGame()[0].getUsername() + " " + currentGame.getManaOfPlayers()[0] + " - ");
        ans = ans.concat(currentGame.getPlayersOfGame()[1].getUsername() + " " + currentGame.getManaOfPlayers()[1]);
        return ans;
    }
    public String showGameModeInfo(){
        String ans = "Game Mode => ";
        if(currentGame.getGameMode().equals("heroMode")){
            ans = ans.concat("Hero Mode\n1. Health Of Hero Player " + currentGame.getPlayersOfGame()[0].getUsername() + " : " + ((Unit)currentGame.getPlayerHero(0)).getHP() + "\n");
            ans = ans.concat("2. Health Of Hero Player " + currentGame.getPlayersOfGame()[1].getUsername() + " : " + ((Unit)currentGame.getPlayerHero(1)).getHP() + "\n");
        }
        else if(currentGame.getGameMode().equals("flagHolding")){
            int[] coord = new int[2];
            coord[0] = currentGame.getFlagHoldedCoordination()[0];
            coord[1] = currentGame.getFlagHoldedCoordination()[1];
            ans = ans.concat("Flag Holding Mode\nFlag Coordination = X : " + coord[0] + " , Y : " + coord[1] + "\n");
            if(currentGame.getFlagOwner(coord[0],coord[1]) == null)
                ans = ans.concat("Nobody has the flag !!!");
            else
                ans = ans.concat("Player " + currentGame.getFlagOwner(coord[0],coord[1]).getUsername() + " Has The Flag !");
        }
        else{
            ArrayList<String> flagsInfo = currentGame.getFlagsInfo();
            ans = ans.concat("Flags Collecting Mode\n Soldiers That have The flag :\n");
            for(int i = 0 ; i < flagsInfo.size() ; i++ )
                ans = ans.concat(i+1 + ". " + flagsInfo.get(i) + "\n");
        }
        return ans;
    }


    public ArrayList<String> getAllMinionsInfo(String whom){
        ArrayList<String> ans = new ArrayList<>();


        return ans;
    }

    public boolean selectCard(int cardID){
        if ( this.currentGame.findCardByID(cardID) == null )
            return false;
        else{
            for ( Cell[] cells : this.currentGame.getMap() )
                for ( Cell cell : cells )
                    if ( cell.getCard().getCardID() == cardID && this.currentGame.getCardOwner(cell.getCard()).equals
                            (this.currentGame.getPlayersOfGame()[this.currentGame.getTurn()]) ) {
                        this.currentGame.setCurrentCard(cell.getCard());
                        return true;
                    }
        }
        return false;
    }

    public int moveCard(int x, int y){
        if ( true )
            return this.currentGame.getCurrentCard().getCardID();
        else
            return 0;
    }

    public int attack(int opponentCardID){
        // return -1 -> opponent is missed
        // return -2 -> range !
        // return -3 -> already attacked
        // return -4 -> no card has been selected !
        return this.currentGame.getCurrentCard().getCardID();
    }

    public int counterAttack(int opponentCardID){

    }

    public int comboAttack(int opponentCardID, ArrayList<Integer> comboAttackers){

    }

    public int useSpecialPower(int x, int y){

    }

    public ArrayList<Card>showHand(){

    }

    public int insertUnit(String cardName, int x, int y){

    }

    public int insertSpell(String cardName, int x, int y){

    }

    public void endTurn(){

    }

    public ArrayList<Item> ShowCollectables(){

    }

    public int selectItem(int itemID){

    }

    public Item showCurrentItemInfo(){

    }

    public boolean useCurrentItem(int x, int y){

    }

    public void showNextCard(){

    }

    public boolean enterGraveYard(){

    }

    public void endGame(){

    }


}