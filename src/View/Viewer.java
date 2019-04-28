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
    ////////////////////
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
                if(input.matches("Enter \\w"))
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
            }
            //============== Shop =============//
            else if(menuMode == 3){
                // Ali Code Here
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
    private void goToMenu(String name){
        if(name.toLowerCase().equals("collection"))
            this.menuMode = 2;
        else if(name.toLowerCase().equals("shop"))
            this.menuMode = 3;
        else if(name.toLowerCase().equals("battle"))
            this.menuMode = 4;
    }
    //======================== Collection Functions ====================//
    private void showCollection(){
        ArrayList<Card> cardsToShow = controller.cardsInCollection();
        System.out.println("Heroes : ");
        int cnt = 1;
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Unit && ((Unit)cardsToShow.get(i)).isHero()){
                Card card = cardsToShow.get(i);
                System.out.println(cnt + ". Name : " + card.getName() + " - AP : " + ((Unit)card).getAttackPower() + " - HP : " + ((Unit)card).getHP()
                        + " - Class : " + ((Unit)card).getAttackType() + " - Special Power : " + ((Unit)card).getSpecialPower().getName());
                cnt++;
            }
        }
        cnt = 1;
        System.out.println("Items : ");
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Item){
                Card card = cardsToShow.get(i);
                System.out.println(cnt + ". Name : " + card.getName() + " - Desc : " + ((Item)card).getDescription());
                cnt++;
            }
        }
        cnt = 1;
        System.out.println("Cards : ");
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof Unit && !((Unit)cardsToShow.get(i)).isHero()){
                Card card = cardsToShow.get(i);
                System.out.println(cnt + ". Type : Minion - Name : " + card.getName() + " - AP : " + ((Unit)card).getAttackPower() + " - HP : " + ((Unit)card).getHP()
                        + " - MP : " + ((Unit)card).getManaCost()
                        + " - Class : " + ((Unit)card).getAttackType() + " - Special Power : " + ((Unit)card).getSpecialPower().getName());
                cnt++;
            }
        }
        for(int i = 0 ; i < cardsToShow.size() ; i++ ){
            if(cardsToShow.get(i) instanceof SpellCard){
                Card card = cardsToShow.get(i);
                System.out.println(cnt + ". Type : Spell - Name : " + card.getName()
                        + " - MP : " + ((SpellCard)card).getManaCost()
                        + " - Desc : " + ((SpellCard)card).getDescription());
                cnt++;
            }
        }
    }
    private void printSearchCollection(String keyword){
        ArrayList<Integer> cardIDs = controller.searchCollection(keyword);
        if(cardIDs.size() == 0)
            System.out.println("Nothing Found !");
        else {
            System.out.println("Search Result : ");
            for (int i = 0; i < cardIDs.size(); i++)
                System.out.println(cardIDs.get(i));
        }
    }
    private void createPlayerDeck(String keyword){
        if(!controller.isDeckExists(keyword)) {
            System.out.println("Deck " + keyword + " Successfully Created !");
            controller.createDeckForPlayer(keyword);
        }
        else
            System.out.println("Deck with this name already exists !!!");
    }
    private void deletePlayerDeck(String keyword){
        if(controller.isDeckExists(keyword)){
            controller.deleteDeckPlayer(keyword);
            System.out.println("Deck " + keyword + " Successfully deleted !");
        }
        else
            System.out.println("Deck with this name doesnt Exists !!!");
    }
    //======================== Shop Function ====================//
    // Ali Here !



    public void showCards(int mode){ }
    public void showMap(){}
    public void showError(){}

    public int getMenuMode(){
        return this.menuMode;
    }
    public void setMenuMode(int i){
        this.menuMode = i;
    }
}
