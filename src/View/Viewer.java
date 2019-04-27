package View;
// Arman Change
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import Controller.*;
import Modules.Player;

import javax.sound.midi.Soundbank;
import java.util.Comparator;
import java.util.Collections;

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
        System.out.println("reza");
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
                //====================================== Logout Account =================================//
                else if(input.matches("logout")) {
                    controller.logOut();
                    this.menuMode = 0;
                }
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
                else
                    System.out.println("invalid Command !!!");
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


    public void showCards(int mode){ }
    public void showMap(){}
    public void showError(){ }
    public void showCollection(){}

    public int getMenuMode(){
        return this.menuMode;
    }
    public void setMenuMode(int i){
        this.menuMode = i;
    }
}
