package View;

import java.util.*;
import Controller.*;
import Modules.*;

import javax.sound.midi.Soundbank;
import java.util.Comparator;

public class Viewer {
    private Scanner scanner = new Scanner(System.in);
    private Server controller = new Server();
    private int menuMode = 0;
    /////////////////////
    // 0 -> before login
    // 1 -> MainMenu
    // 2 -> Collection
    // 3 -> Shop
    // 4 -> Battle
    ////////////////////
    public void gameHandle(){
        while(true){
            String input = scanner.nextLine();
            //============== Before Login =============//
            if(menuMode == 0){
                //====================================== Create Account =================================//
                if(input.matches("create account \\w+"))
                    createAccount(input);
                    //====================================== Login into Account =================================//
                else if(input.matches("login \\w+"))
                    loginAccount(input);
                    //====================================== Show LeaderBoard =================================//
                else if(input.matches("show leaderboard"))
                    showLeaderBoard();
                    //====================================== Save =================================//
                else if(input.matches("save"))
                    controller.save();
                    //====================================== Help Account =================================//
                else if(input.matches("help"))
                    accountHelp();
                else
                    System.out.println("Invalid Command !!!");
            }
            //============== MainMenu =============//
            else if(menuMode == 1){
                if(input.matches("Enter \\w+"))
                    goToMenu(input.split(" ")[1]);
                else if(input.toLowerCase().matches("exit"))
                    this.menuMode = 0;
                else if(input.toLowerCase().matches("help"))
                    printMainMenu();
                    //========= Logout Account ======//
                else if(input.matches("logout")) {
                    controller.logOut();
                    this.menuMode = 0;
                }
                else
                    System.out.println("invalid Command !!!");
            }
            //============== Collection =============//
            else if(menuMode == 2){
                if(input.toLowerCase().matches("exit"))
                    this.menuMode = 0;
                else if(input.toLowerCase().matches("show"))
                    showCollection();
                else if(input.toLowerCase().matches("search \\w+"))
                    printSearchCollection(input.split(" ")[1]);
                else if(input.toLowerCase().matches("save"))
                    System.out.println("Saved Successfully !!!");
                else if(input.toLowerCase().matches("create deck \\w+"))
                    createPlayerDeck(input.split(" ")[2]);
                else if(input.toLowerCase().matches("delete deck \\w+"))
                    deletePlayerDeck(input.split(" ")[2]);
                else if(input.toLowerCase().matches("add \\d+ to deck \\w+"))
                    addCardToDeck(Integer.parseInt(input.split(" ")[1]),input.split(" ")[4]);
                else if(input.toLowerCase().matches("remove \\d+ from deck \\w+"))
                    removeCardFromDeck(Integer.parseInt(input.split(" ")[1]),input.split(" ")[4]);
                else if(input.toLowerCase().matches("validate deck \\w+"))
                    checkDeckValidation(input.split(" ")[2]);
                else if(input.toLowerCase().matches("select deck \\w+"))
                    setMainDeck(input.split(" ")[2]);
                else if(input.toLowerCase().matches("show all decks"))
                    showAllDecks();
                else if(input.toLowerCase().matches("show deck \\w+"))
                    showOneDeck(input.split(" ")[2]);
                else if(input.toLowerCase().matches("help"))
                    collectionHelp();
                else
                    System.out.println("Invalid Command !!!");
            }
            //============== Shop =============//
            else if(menuMode == 3){
                if(input.matches("exit"))
                    menuMode = 1;
                else if(input.matches("show collection"))
                    controller.showCollection();
                else if(input.matches("search \\w+"))
                    controller.searchCard(input.split(" ")[1]);
                    ////////////////////////////////////////////////////search too collection bayad az server collection sseda she
                    //////////////////////////INVALID HA RO HANOOZ NAZADAM
                else if(input.matches("buy \\w+"))
                    controller.buyCard(input.split(" ")[1]);
                else if(input.matches("sell \\d+"))
                    controller.sellCardwithID(Integer.parseInt(input.split(" ")[1]));
                else if(input.matches("sell \\D+"))
                    controller.sellCardWithName(input.split(" ")[1]);
                else if(input.matches("show"))
                    controller.showItems();
                else if(input.matches("help"))
                    printShopMenu();
            }
        }
    }
    //======================== Account Function ====================//
    private void createAccount(String input){
        String username = input.split(" ")[2];
        if(controller.userExists(username))
            System.out.println("This Username Already Exists !!!");
        else{
            System.out.println("Enter Your Password : ");
            String password = scanner.nextLine();
            controller.createAccount(username,password);
            controller.login(username, password);
            this.menuMode = 1;
        }
    }
    private void loginAccount(String input){
        String username = input.split(" ")[1];
        if(!controller.userExists(username))
            System.out.println("This Username Doesn't Exists !!!");
        else{
            System.out.println("Enter Your Password :");
            String password = scanner.nextLine();
            if(!controller.isValidLogin(username,password))
                System.out.println("Invalid Password for This Username !!!");
            else {
                controller.login(username, password);
                this.menuMode = 1;
            }
        }
    }
    private void showLeaderBoard(){
        ArrayList<Player> players = Player.getPlayers();
        Collections.sort(players, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                if(p1.getNumberOfWins() > p2.getNumberOfWins())
                    return -1;
                else if(p1.getNumberOfWins() < p2.getNumberOfWins())
                    return 1;
                return p1.getUsername().compareTo(p2.getUsername());
            }
        });
        for(int i = 0 ; i < players.size() ; i++)
            System.out.println( i+1 + " - UserName : " + players.get(i).getUsername() + " - Wins : " + players.get(i).getNumberOfWins());
    }
    private void accountHelp(){
        System.out.println("============= Commands =============");
        System.out.println("create account [user name]");
        System.out.println("login [user name]");
        System.out.println("show leaderboard");
        System.out.println("save");
        System.out.println("logout");
        System.out.println("=====================================");
    }
    //======================== Main Menu Function ====================//
    private void printMainMenu(){
        System.out.print("1. Collection\n2. Shop\n3. Battle\n4. Exit\n5. Help\n");
    }
    private void printShopMenu(){
        System.out.print("1. exit\n2. show collection\n3. search\n4. search collection\n5. buy\n6. sell\n7. show");
    }
    private void goToMenu(String name){
        if(name.toLowerCase().equals("collection"))
            this.menuMode = 2;
        else if(name.toLowerCase().equals("shop"))
            this.menuMode = 3;
        else if(name.toLowerCase().equals("battle"))
            this.menuMode = 4;
    }
    //======================== Collection Functions ====================//
    public void showCollection(){
        ArrayList<Card> cardsToShow = controller.cardsInCollection();
        System.out.println("Heroes : ");
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Unit && ((Unit)cardsToShow.get(i)).isHero()){
                Card card = cardsToShow.get(i);
                System.out.println(i+1 + ". Name : " + card.getName() + " - AP : " + ((Unit)card).getAttackPower() + " - HP : " + ((Unit)card).getHP()
                        + " - Class : " + ((Unit)card).getAttackType() + " - Special Power : " + ((Unit)card).getSpecialPower().getName());
            }
        }
        System.out.println("Items : ");
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Item){
                Card card = cardsToShow.get(i);
                System.out.println(i+1 + ". Name : " + card.getName() + " - Desc : " + ((Item)card).getDescription());
            }
        }
    }
    public void printSearchCollection(String keyword){
        ArrayList<Integer> cardIDs = controller.searchCollection(keyword);
        if(cardIDs.size() == 0)
            System.out.println("Nothing Found in Search !!!");
        else{
            System.out.println("Search Result :");
            for(int i = 0 ; i < cardIDs.size() ; i++)
                System.out.println(cardIDs.get(i));
        }
    }
    public void createPlayerDeck(String keyword){
        if(controller.isDeckExists(keyword))
            System.out.println("a Deck with this name already exists !!!");
        else {
            controller.createDeckForPlayer(keyword);
            System.out.println("Deck with name " + keyword + " Successfully Created !");
        }
    }
    public void deletePlayerDeck(String keyword){
        if(!controller.isDeckExists(keyword))
            System.out.println("a Deck with this name does not exist !!!");
        else {
            controller.deleteDeckPlayer(keyword);
            System.out.println("Deck with name " + keyword + " Successfully Deleted !");
        }
    }
    public void addCardToDeck(int cardID,String keyword){
        if(!controller.isDeckExists(keyword))
            System.out.println("Deck with this name does not exists !!!");
        else if(!controller.isCardInCollection(cardID))
            System.out.println("This CardID is not in your Collection !!!");
        else if(controller.isCardInDeck(cardID,keyword))
            System.out.println("This CardID is already in your Deck !!!");
        else if(controller.isDeckFull(keyword))
            System.out.println("This Deck is Full and cant Add your Card !!!");
        else if(controller.isCardIDHero(cardID) && controller.deckHasHero(keyword))
            System.out.println("Your Deck already has a Hero !!!");
        else{
            controller.addCardToTheDeck(keyword,cardID);
            System.out.println("Card Successfully added to your Deck !");
        }
    }
    public void removeCardFromDeck(int cardID , String keyword){
        if(!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else if(!controller.isCardInDeck(cardID,keyword))
            System.out.println("This Card Does not exist in your Deck !!!");
        else{
            controller.deleteCardFromDeck(keyword,cardID);
            System.out.println("Card Successfully Deleted From Deck !!!");
        }
    }
    public void checkDeckValidation(String keyword){
        if(!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else if(controller.isDeckValid(keyword))
            System.out.println("This Deck is Valid !");
        else
            System.out.println("Deck is not Valid !!!");
    }
    public void setMainDeck(String keyword){
        if(!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else{
            controller.setMainDeck(keyword);
            System.out.println("Main Deck Successfully been Set !");
        }
    }
    public void showDeck(String keyword){
        ArrayList<Card> cardsToShow = controller.getDeck(keyword).getCards();
        System.out.println("Heroes : ");
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Unit && ((Unit)cardsToShow.get(i)).isHero()){
                Card card = cardsToShow.get(i);
                System.out.println(i+1 + ". Name : " + card.getName() + " - AP : " + ((Unit)card).getAttackPower() + " - HP : " + ((Unit)card).getHP()
                        + " - Class : " + ((Unit)card).getAttackType() + " - Special Power : " + ((Unit)card).getSpecialPower().getName());
            }
        }
        System.out.println("Items : ");
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Item){
                Card card = cardsToShow.get(i);
                System.out.println(i+1 + ". Name : " + card.getName() + " - Desc : " + ((Item)card).getDescription());
            }
        }
    }
    public void showOneDeck(String keyword){
        if(!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else
            showDeck(keyword);
    }
    public void showAllDecks(){
        ArrayList<Deck> decks = controller.getPlayerDecks();
        System.out.println("1 : " + controller.getPlayerMainDeck().getName());
        showDeck(controller.getPlayerMainDeck().getName());
        int cnt = 2;
        for(int i = 0 ; i < decks.size() ; i++ ){
            System.out.println(cnt++ + " :");
            showDeck(decks.get(i).getName());
        }
    }
    public void collectionHelp(){
        System.out.println("============= Commands =============");
        System.out.println("exit");
        System.out.println("show");
        System.out.println("search [card name | item name]");
        System.out.println("save");
        System.out.println("create deck [deck name]");
        System.out.println("delete deck [deck name]");
        System.out.println("add [card id | card id | hero id] to deck [deck name]");
        System.out.println("remove [card id | card id| hero id] from deck [deck name]");
        System.out.println("validate deck [deck name");
        System.out.println("select deck [deck name]");
        System.out.println("show all decks");
        System.out.println("show deck [deck name]");
        System.out.println("=====================================");
    }
    //======================== Shop Function ====================//
    // Ali Here !



    public void showCards(int mode){ }
    public void showMap(){}
    public void showError(){ }

    public int getMenuMode(){
        return this.menuMode;
    }
    public void setMenuMode(int i){
        this.menuMode = i;
    }
}