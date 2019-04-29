package Controller;
///////////////////////////////////////////////////////////ali inja comment gozashte
import View.*;
import Modules.*;
import java.util.ArrayList;

public class Server {
    private Shop shop;
    private Player currentPlayer = null;
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

//=================================shop functions ===================================//

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
}