package View;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
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
    // 4 -> Before Battle
    // 5 -> in Battle
    ////////////////////
    boolean isInGraveYard = false;
    public void gameHandle() {
        while (true) {
            String input = scanner.nextLine();
            //============== Before Login =============//
            if (menuMode == 0) {
                //====================================== Create Account =================================//
                if (input.toLowerCase().matches("create account \\w+"))
                    createAccount(input);
                    //====================================== Login into Account =================================//
                else if (input.toLowerCase().matches("login \\w+"))
                    loginAccount(input);
                    //====================================== Show LeaderBoard =================================//
                else if (input.toLowerCase().matches("show leaderboard"))
                    showLeaderBoard();
                    //====================================== Save =================================//
                else if (input.toLowerCase().matches("save"))
                    controller.save();
                    //====================================== Help Account =================================//
                else if (input.toLowerCase().matches("help"))
                    accountHelp();
                else
                    System.out.println("Invalid Command !!!");
            }
            //============== MainMenu =============//
            else if (menuMode == 1) {
                if (input.toLowerCase().matches("enter \\w+"))
                    goToMenu(input.split(" ")[1]);
                else if (input.toLowerCase().matches("exit"))
                    this.menuMode = 0;
                else if (input.toLowerCase().matches("help"))
                    printMainMenu();
                else if (input.toLowerCase().matches("logout")) {
                    controller.logOut();
                    this.menuMode = 0;
                } else
                    System.out.println("invalid Command !!!");
            }
            //============== Collection =============//
            else if (menuMode == 2) {
                if (input.toLowerCase().matches("exit"))
                    this.menuMode = 1;
                else if (input.toLowerCase().matches("show"))
                    showCollection();
                else if (input.toLowerCase().matches("search \\w+"))
                    printSearchCollection(input.split(" ")[1]);
                else if (input.toLowerCase().matches("save"))
                    System.out.println("Saved Successfully !!!");
                else if (input.toLowerCase().matches("create deck \\w+"))
                    createPlayerDeck(input.split(" ")[2]);
                else if (input.toLowerCase().matches("delete deck \\w+"))
                    deletePlayerDeck(input.split(" ")[2]);
                else if (input.toLowerCase().matches("add \\d+ to deck \\w+"))
                    addCardToDeck(Integer.parseInt(input.split(" ")[1]), input.split(" ")[4]);
                else if (input.toLowerCase().matches("remove \\d+ from deck \\w+"))
                    removeCardFromDeck(Integer.parseInt(input.split(" ")[1]), input.split(" ")[4]);
                else if (input.toLowerCase().matches("validate deck \\w+"))
                    checkDeckValidation(input.split(" ")[2]);
                else if (input.toLowerCase().matches("select deck \\w+"))
                    setMainDeck(input.split(" ")[2]);
                else if (input.toLowerCase().matches("show all decks"))
                    showAllDecks();
                else if (input.toLowerCase().matches("show deck \\w+"))
                    showOneDeck(input.split(" ")[2]);
                else if (input.toLowerCase().matches("help"))
                    collectionHelp();
                else
                    System.out.println("Invalid Command !!!");
            }
            //============== Shop =============//
            else if (menuMode == 3) {
                if (input.toLowerCase().matches("exit"))
                    menuMode = 1;
                else if (input.toLowerCase().matches("show collection"))
                    showCollectionInShop();
                else if (input.toLowerCase().matches("search collection \\w+"))
                    searchCollectionInShop(input.split(" ")[2]);
                else if (input.toLowerCase().matches("search \\w+") && !input.split(" ")[1].matches("collection"))
                    searchInShop(input.split(" ")[1]);
                else if (input.toLowerCase().matches("buy \\w+"))
                buyCard(input.split(" ")[1]);
                else if (input.toLowerCase().matches("sell \\d+"))
                    sellCardWithID(Integer.parseInt(input.split(" ")[1]));
                else if (input.toLowerCase().matches("sell \\w+"))
                    sellCardWithName(input.split(" ")[1]);
                else if (input.toLowerCase().matches("show"))
                    showCardsInShop();
                else if (input.toLowerCase().matches("help"))
                    printShopMenu();
                else
                    System.out.println("Invalid Command !!!");
            }
            //============== Before Game =============//
            else if(menuMode == 4){
                if(input.toLowerCase().matches("start single player mode \\w+"))
                    setSinglePlayer(input.split(" ")[4]);
                else if(input.toLowerCase().matches("start multi player between \\w+ and \\w+ mode \\w+"))
                    setMultiPlayer(input.split(" ")[4],input.split(" ")[6],input.split(" ")[8]);
                else if(input.toLowerCase().matches("exit"))
                    menuMode = 1;
                else
                    System.out.println("Invalid Command !!!");
            }
            //============== Game =============//
            else if (menuMode == 5) {
                if(!isInGraveYard){
                    printGameMap();
                    if (input.toLowerCase().matches("game info"))
                        showGameInfo();
                    else if (input.toLowerCase().matches("select [\\d+]"))
                        select(Integer.parseInt(input.split("\\s")[1]));
                    else if (input.toLowerCase().matches("show my minions"))
                        showPlayerMinions("my");
                    else if (input.toLowerCase().matches("show opponent minions"))
                        showPlayerMinions("opponent");
                    else if (input.toLowerCase().matches("show card info \\d+"))
                        showCardInfo(Integer.parseInt(input.split(" ")[3]));
                    else if(input.matches("show hand"))
                        showHand();
                    else if(input.matches("insert \\w+ in \\(\\d,\\d\\)"))
                        insert(input.split(" ")[1],input.split(" ")[3]);
                    //////////////////////////// ARMAN ////////////////////////////////
                    else if(input.toLowerCase().matches("attack \\d+"))
                        attack(Integer.parseInt(input.split(" ")[1]));
                    else if(input.toLowerCase().matches("attack combo \\d+ [\\d+]+")){
                        ArrayList<Integer> cardsID = new ArrayList<>();
                        for(int i = 3 ; i < input.split(" ").length ; i++ )
                            cardsID.add(Integer.parseInt(input.split(" ")[i]));
                        comboAttack(Integer.parseInt(input.split(" ")[2]),cardsID);
                    }
                    else if(input.toLowerCase().matches("show collectables"))
                        showCollectables();
                    else if(input.toLowerCase().matches("show next card"))
                        showNextCard();
                    else if(input.toLowerCase().matches("enter graveyard"))
                        isInGraveYard = true;
                    else if(input.toLowerCase().matches("help"))
                        showHelpInGame();
                    else if(input.toLowerCase().matches("move to \\d \\d"))
                        moveCurrentCard(Integer.parseInt(input.split(" ")[2]) , Integer.parseInt(input.split(" ")[3]) );
                    else if(input.toLowerCase().matches("select card \\d+"))
                        select(Integer.parseInt(input.split("\\s")[2]));
                    else if(input.toLowerCase().matches("end turn")) {
                        controller.endTurn();
                        endGame();
                    }
                    ///////////////////////////// END ARMAN ////////////////////////////////
                    else if(input.toLowerCase().matches("show info"))
                        showCurrentItemInfo();
                    else if(input.toLowerCase().matches("Use \\[location \\d, \\d\\]"))
                        useItem(input);
                    else
                        System.out.println("Invalid Command !!!");
                }
                else{
                    ///////////////////////////// ARMAN ////////////////////////////////
                    if(input.toLowerCase().matches("exit"))
                        isInGraveYard = false;
                    else if(input.toLowerCase().matches("show info \\d+"))
                        showInfoCardIDGraveyard(Integer.parseInt(input.split(" ")[2]));
                    else if(input.toLowerCase().matches("show cards"))
                        showGraveyardCards();
                    else if(input.toLowerCase().matches("help"))
                        showHelpGraveyard();
                    else
                        System.out.println("Invalid Command !");
                    ///////////////////////////// END ARMAN ////////////////////////////////
                }
            }
        }
    }

    //======================== Account Function ====================//
    private void createAccount(String input) {
        String username = input.split(" ")[2];
        if (controller.userExists(username))
            System.out.println("This Username Already Exists !!!");
        else {
            System.out.println("Enter Your Password : ");
            String password = scanner.nextLine();
            controller.createAccount(username, password);
            controller.login(username, password);
            this.menuMode = 1;
        }
    }

    private void loginAccount(String input) {
        String username = input.split(" ")[1];
        if (!controller.userExists(username))
            System.out.println("This Username Doesn't Exists !!!");
        else {
            System.out.println("Enter Your Password :");
            String password = scanner.nextLine();
            if (!controller.isValidLogin(username, password))
                System.out.println("Invalid Password for This Username !!!");
            else {
                controller.login(username, password);
                this.menuMode = 1;
            }
        }
    }

    private void showLeaderBoard() {
        ArrayList<Player> players = Player.getPlayers();
        Collections.sort(players, new Comparator<Player>() {
            public int compare(Player p1, Player p2) {
                if (p1.getNumberOfWins() > p2.getNumberOfWins())
                    return -1;
                else if (p1.getNumberOfWins() < p2.getNumberOfWins())
                    return 1;
                return p1.getUsername().compareTo(p2.getUsername());
            }
        });
        for (int i = 0; i < players.size(); i++)
            System.out.println(i + 1 + " - UserName : " + players.get(i).getUsername() + " - Wins : " + players.get(i).getNumberOfWins());
    }

    private void accountHelp() {
        System.out.println("============= Commands =============");
        System.out.println("create account [user name]");
        System.out.println("login [user name]");
        System.out.println("show leaderboard");
        System.out.println("save");
        System.out.println("logout");
        System.out.println("=====================================");
    }

    //======================== Main Menu Function ====================//
    private void printMainMenu() {
        System.out.print("1. Collection\n2. Shop\n3. Battle\n4. Exit\n5. Help\n");
    }

    private void goToMenu(String name) {
        if (name.toLowerCase().equals("collection"))
            this.menuMode = 2;
        else if (name.toLowerCase().equals("shop"))
            this.menuMode = 3;
        else if (name.toLowerCase().equals("battle")) {
            this.menuMode = 4;
            printBeforeBattleMenu();
        }
    }

    private void printBeforeBattleMenu(){
        System.out.println("1. Start Single Player Mode [Mode Name]");
        System.out.println("2. Start Multi Player Between [Player1 Name] and [Player2 Name] Mode [Mode Name]");
    }
    //======================== Collection Functions ====================//
    public void showCollection() {
        ArrayList<Card> cardsToShow = controller.cardsInCollection();
        System.out.println("Heroes : ");
        int cnt = 1;
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i) instanceof Unit && ((Unit) cardsToShow.get(i)).isHero()) {
                Card card = cardsToShow.get(i);
                System.out.println(cnt + ". Name : " + card.getName() + " - AP : " + ((Unit) card).getAttackPower() + " - HP : " + ((Unit) card).getHP()
                        + " - Class : " + ((Unit) card).getAttackType() + " - Special Power : " + ((Unit) card).getSpecialPower().getName());
                cnt++;
            }
        }
        System.out.println("Items : ");
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i) instanceof Item) {
                Card card = cardsToShow.get(i);
                System.out.println(i + 1 + ". Name : " + card.getName() + " - Desc : " + ((Item) card).getDescription());
            }
        }

        System.out.println("Cards : ");
        int counter = 0;
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (!(cardsToShow.get(i) instanceof Item) && !(cardsToShow.get(i) instanceof Unit &&
                    ((Unit) cardsToShow.get(i)).isHero())) {
                counter++;
                if (cardsToShow.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) cardsToShow.get(i);
                    System.out.println("\t" + counter + " : Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Description : " + " - Sell Cost : "
                            + card.getPrice());///////////////////////////////////////////////desc nadare
                }
                if (cardsToShow.get(i) instanceof Unit) {
                    Unit card = (Unit) cardsToShow.get(i);
                    System.out.println("\t" + counter + " : Type : Minion - Name : " + card.getName()
                            + " - class : " + ((Unit) card).getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - Special Power : " +
                            card.getSpecialPower().getRecipe() + " - Sell Cost : " + card.getPrice());
                }

            }
        }
    }

    public void printSearchCollection(String keyword) {
        ArrayList<Integer> cardIDs = controller.searchCollection(keyword);
        if (cardIDs.size() == 0)
            System.out.println("Nothing Found in Search !!!");
        else {
            System.out.println("Search Result :");
            for (int i = 0; i < cardIDs.size(); i++)
                System.out.println(cardIDs.get(i));
        }
    }

    public void createPlayerDeck(String keyword) {
        if (controller.isDeckExists(keyword))
            System.out.println("a Deck with this name already exists !!!");
        else {
            controller.createDeckForPlayer(keyword);
            System.out.println("Deck with name " + keyword + " Successfully Created !");
        }
    }

    public void deletePlayerDeck(String keyword) {
        if (!controller.isDeckExists(keyword))
            System.out.println("a Deck with this name does not exist !!!");
        else {
            controller.deleteDeckPlayer(keyword);
            System.out.println("Deck with name " + keyword + " Successfully Deleted !");
        }
    }

    public void addCardToDeck(int cardID, String keyword) {
        if (!controller.isDeckExists(keyword))
            System.out.println("Deck with this name does not exists !!!");
        else if (!controller.isCardInCollection(cardID))
            System.out.println("This CardID is not in your Collection !!!");
        else if (controller.isCardInDeck(cardID, keyword))
            System.out.println("This CardID is already in your Deck !!!");
        else if (controller.isDeckFull(keyword))
            System.out.println("This Deck is Full and cant Add your Card !!!");
        else if (controller.isCardIDHero(cardID) && controller.deckHasHero(keyword))
            System.out.println("Your Deck already has a Hero !!!");
        else {
            controller.addCardToTheDeck(keyword, cardID);
            System.out.println("Card Successfully added to your Deck !");
        }
    }

    public void removeCardFromDeck(int cardID, String keyword) {
        if (!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else if (!controller.isCardInDeck(cardID, keyword))
            System.out.println("This Card Does not exist in your Deck !!!");
        else {
            controller.deleteCardFromDeck(keyword, cardID);
            System.out.println("Card Successfully Deleted From Deck !!!");
        }
    }

    public void checkDeckValidation(String keyword) {
        if (!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else if (controller.isDeckValid(keyword))
            System.out.println("This Deck is Valid !");
        else
            System.out.println("Deck is not Valid !!!");
    }

    public void setMainDeck(String keyword) {
        if (!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else {
            controller.setMainDeck(keyword);
            System.out.println("Main Deck Successfully been Set !");
        }
    }

    public void showDeck(String keyword) {
        System.out.println("Deck Name : " + controller.getDeck(keyword).getName());
        ArrayList<Card> cardsToShow = controller.getDeck(keyword).getCards();
        System.out.println("Heroes : ");
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i+1-1) instanceof Unit && ((Unit) cardsToShow.get(i)).isHero()) {
                Card card = cardsToShow.get(i);
                System.out.println("\t" + (i + 2 - 1) + ". Name : " + card.getName() + " - AP : " + ((Unit) card).getAttackPower() + " - HP : " + ((Unit) card).getHP()
                        + " - Class : " + ((Unit) card).getAttackType() + " - Special Power : " + ((Unit) card).getSpecialPower().getName());
            }
        }
        System.out.println("Items : ");
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i) instanceof Item) {
                Card card = cardsToShow.get(i);
                System.out.println("\t" + (i + 1 + 0) + ". Name : " + card.getName() + " - Desc : " + ((Item) card).getDescription());
            }
        }
        System.out.println("Cards : ");
        int counter = 0;
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (!(cardsToShow.get(i) instanceof Item) && !(cardsToShow.get(i) instanceof Unit &&
                    ((Unit) cardsToShow.get(i)).isHero())) {
                counter++;
                if (cardsToShow.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) cardsToShow.get(i);
                    System.out.println("\t" + counter + " : Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Description : " + " - Sell Cost : "
                            + card.getPrice());///////////////////////////////////////////////desc nadare
                }
                if (cardsToShow.get(i) instanceof Unit) {
                    Unit card = (Unit) cardsToShow.get(i);
                    System.out.println("\t" + counter + " : Type : Minion - Name : " + card.getName()
                            + " - class : " + ((Unit) card).getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - Special Power : " +
                            card.getSpecialPower() + " - Sell Cost : " + card.getPrice());
                }

            }
        }
    }

    public void showOneDeck(String keyword) {
        if (!controller.isDeckExists(keyword))
            System.out.println("Deck with this name doesnt exist !!!");
        else
            showDeck(keyword);
    }

    public void showAllDecks() {
        ArrayList<Deck> decks = controller.getPlayerDecks();
        if ( controller.getPlayerMainDeck() == null )
            System.out.println("Main Deck : Player doesnt Have Main Deck !!!");
        else {
            System.out.println("Main Deck : ");
            showDeck(controller.getPlayerMainDeck().getName());
        }
        int cnt = 1;
        for (int i = 0; i < decks.size(); i++) {
            if(controller.getPlayerMainDeck() == null || !decks.get(i).getName().equals(controller.getPlayerMainDeck().getName())) {
                System.out.println(cnt++ + " :");
                showDeck(decks.get(i).getName());
            }
        }
    }

    public void collectionHelp() {
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
    public void searchCollectionInShop(String keyword){
        System.out.println(controller.searchCardInCollection(keyword));
    }
    public void searchInShop(String keyword){
        System.out.println(controller.searchCardInShop(keyword));
    }
    public void showCollectionInShop() {
        ArrayList<Card> collection = controller.cardsInCollection();
        System.out.println("Heroes :");
        int counter = 0;
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i) instanceof Unit && ((Unit) collection.get(i)).isHero()) {
                Unit hero = (Unit) collection.get(i);
                counter++;
                System.out.println("\t" + counter + " : Name : " + hero.getName() + " - AP : " + hero.getAttackPower() + " - HP : " +
                        hero.getHP() + " - Class" + hero.getAttackType() + " - Special power : " + hero.getSpecialPower().getRecipe() +
                        " - Sell Cost : " + hero.getPrice());
                ///////////////////getclass ina ro nadarim asan too unit va inke new unit chera khakestarie
            }
        }
        System.out.println("Items : ");
        counter = 0;
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i) instanceof Item) {
                Item item = (Item) collection.get(i);
                counter++;
                System.out.println("\t" + counter + " : Name " + item.getName() + " - Desc : " +
                        item.getDescription() + " - Sell Cost : " + item.getPrice());
            }
        }
        System.out.println("Cards : ");
        counter = 0;
        for (int i = 0; i < collection.size(); i++) {
            if (!(collection.get(i) instanceof Item) && !(collection.get(i) instanceof Unit &&
                    ((Unit) collection.get(i)).isHero())) {
                counter++;
                if (collection.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) collection.get(i);
                    System.out.println("\t" + counter + " : Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Description : " + " - Sell Cost : "
                            + card.getPrice());///////////////////////////////////////////////desc nadare
                }
                if (collection.get(i) instanceof Unit) {
                    Unit card = (Unit) collection.get(i);
                    System.out.println("\t" + counter + " : Type : Minion - Name : " + card.getName()
                            + " - class : " + card.getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - Special Power : " +
                            card.getSpecialPower().getRecipe() + " - Sell Cost : " + card.getPrice());
                }

            }
        }
    }

    public void showCardsInShop() {
        ArrayList<Card> cardsInShop = controller.getCardsInShop();
        if(cardsInShop == null)
            System.out.println("shop is empty");
        System.out.println("Heroes :");
        int counter = 0;
        for (int i = 0; i < cardsInShop.size(); i++) {
            if (cardsInShop.get(i) instanceof Unit && ((Unit) cardsInShop.get(i)).isHero()) {
                Unit hero = (Unit) cardsInShop.get(i);
                counter++;
                System.out.println("\t" + counter + " : Name : " + hero.getName() + " - AP : " + hero.getAttackPower()
                        + " - HP : " + hero.getHP() + " - Special power : " + hero.getSpecialPower().getRecipe() +
                        " - Buy Cost : " + hero.getPrice());
            }
        }
        System.out.println("Items : ");
        counter = 0;
        for (int i = 0; i < cardsInShop.size(); i++) {
            if (cardsInShop.get(i) instanceof Item) {
                Item item = (Item) cardsInShop.get(i);
                counter++;
                System.out.println("\t" + counter + " : Name " + item.getName() + " - Desc : " +
                        item.getDescription() + " - Buy Cost : " + item.getPrice());
            }
        }
        System.out.println("Cards : ");
        counter = 0;
        for (int i = 0; i < cardsInShop.size(); i++) {
            if (!(cardsInShop.get(i) instanceof Item) && !(cardsInShop.get(i) instanceof Unit &&
                    ((Unit) cardsInShop.get(i)).isHero())) {
                counter++;
                if (cardsInShop.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) cardsInShop.get(i);
                    System.out.println("\t" + counter + " : Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Description : " + " - Buy Cost : "
                            + card.getPrice());///////////////////////////////////////////////desc nadare
                }
                if (cardsInShop.get(i) instanceof Unit) {
                    Unit card = (Unit) cardsInShop.get(i);
                    System.out.println("\t" + counter + " : Type : Minion - Name : " + card.getName()
                            + " - class : " + ((Unit) card).getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - Special Power : " +
                            card.getSpecialPower().getRecipe() + " - Buy Cost : " + card.getPrice());
                }

            }
        }
    }

    public void sellCardWithID(int cardID) {
        ArrayList<Card> cardsInCollection = controller.cardsInCollection    ();
        boolean found = false;
        for (int i = 0; i < cardsInCollection.size(); i++) {
            if (cardsInCollection.get(i).getCardID() == cardID) {
                controller.sellCardWithID(cardID);
                controller.removeSelledCardFromDecks(cardID);
                System.out.println("selling Done!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("you dont have this card!");
        }
    }

    public void sellCardWithName(String name) {
        ArrayList<Card> cardsInCollection = controller.cardsInCollection();
        boolean found = false;
        for (int i = 0; i < cardsInCollection.size(); i++) {
            if (cardsInCollection.get(i).getName().equals(name)) {
                controller.sellCardWithName(name);
                System.out.println("selling Done!");
                found = true;
            }
        }
        if (!found) {
            System.out.println("you dont have this card!");
        }
    }

    private void printShopMenu() {
        System.out.print("1. exit\n2. show collection\n3. search [item name | card name]\n4. search collection [item name | card name]\n5. buy [card name | item name]\n6. sell [card id | card id]\n7. show");
    }

    public void buyCard(String name) {
        ArrayList<Card> cardsInShop = controller.getCardsInShop();
        boolean found = false;
        for (int i = 0; i < cardsInShop.size(); i++) {
            if (cardsInShop.get(i).getName().equals(name)) {
                found = true;
                if (cardsInShop.get(i).getPrice() > controller.getPlayerMoney()) {
                    System.out.println("you dont have enough money");
                } else if (controller.getNumberOfItemsOfPlayers() > 3 && cardsInShop.get(i) instanceof Item) {
                    System.out.println("you cant buy any more items");
                } else {
                    controller.buyCard(name);
                    System.out.println("buying successful");
                }
                break;
            }
        }
        if (!found)
            System.out.println("we dont have this card");
    }

    public void printSearchedID(int cardID) {
        System.out.println(cardID);
    }




    public void showCards(int mode){ }
    public void showMap(){}
    public void showError(){ }

    public int getMenuMode(){
        return this.menuMode;
    }
    public void setMenuMode(int i){
        this.menuMode = i;
    }


    //======================== Battle Functions ====================//
    public void showGameInfo() {
        System.out.println("-------- Game Info ---------");
        System.out.println("Mana => " + controller.showPlayersMana());
        System.out.println(controller.showGameModeInfo());
    }

    public void showPlayerMinions(String string) {
        ArrayList<String> unitsInfo = controller.getPlayerUnitsInfo(string);
        for (int i = 0; i < unitsInfo.size(); i++)
            System.out.println(i + 1 + ". " + unitsInfo.get(i));
    }

    public void showCardInfo(int cardID) {
        System.out.println(controller.showCardInfo(cardID));
    }
    public void select(int cardID){
        if (controller.selectCard(cardID)) {
            System.out.println("card has been selected!");
        }
        else if(controller.selectItem(cardID))
            System.out.println("Item has been selected");
        else
            System.out.println("Invalid select");

    }


    public void moveCurrentCard(int x, int y) {
        int ans = controller.moveCurrentCard(x, y);
        switch (ans) {
            case 2:
                System.out.println("the card has been moved this round!");
                break;
            case 3:
                System.out.println("invalid target");
                break;
            case 4:
                System.out.println("no moveable card has been selected!");
                break;
            case 5:
                System.out.println("your selected card is stunned");
                break;
            default:
                System.out.println("card " + ans + " moved to " + x + " " + y);
                break;

        }
        endGame();
    }

    //////////////////////////// ARMAN ////////////////////////////////
    public void attack(int opponentCardID){
        System.out.println(controller.attack(opponentCardID));
        endGame();
    }

    public void comboAttack(int opponentCardID, ArrayList<Integer> comboAttackers){
        System.out.println(controller.comboAttack(opponentCardID,comboAttackers));
    }
    public void showCollectables(){
        System.out.println(controller.getCollectablesInfo());
    }
    public void showNextCard(){
        System.out.println(controller.getNextCardInfo());
    }
    public void showInfoCardIDGraveyard(int id){
        System.out.println(controller.getInfoCardInGraveyard(id));
    }
    public void showGraveyardCards(){
        System.out.println(controller.getCardsInfoGraveyard());
    }
    public void showHelpGraveyard(){
        System.out.print("Show info [card id]\nShow cards\nExit\n");
    }
    public void showHelpInGame(){
        System.out.println("Game info");
        System.out.println("Show my minions");
        System.out.println("Show opponent minions");
        System.out.println("Show card info [card id]");
        System.out.println("Select [card id]");
        System.out.println("Move to ([x], [y])");
        System.out.println("Attack [opponent card id]");
        System.out.println("Attack combo [opponent card id] [my card id] [my card id] [...]");
        System.out.println("Use special power (x, y)");
        System.out.println("Show hand");
        System.out.println("Insert [card name] in (x, y)");
        System.out.println("End turn");
        System.out.println("Show collectables");
        System.out.println("Select [collectable id]");
        System.out.println("Show Next Card");
        System.out.println("Enter graveyard");
        System.out.println("End Game");
        System.out.println("Exit");
    }
    public void printGameMap() {
        Cell[][] map = controller.getGameMap();
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 9; j++) {
                boolean isFlag = false;
                for(int k = 0 ; k < map[i][j].getItems().size() ; k++)
                    if(map[i][j].getItems().get(k).isFlag())
                        isFlag = true;
                boolean isCollectable = false;
                for(int k = 0 ; k < map[i][j].getItems().size() ; k++)
                    if(map[i][j].getItems().get(k).isCollectAble())
                        isCollectable = true;
                if(isFlag)
                    System.out.print("F");
                else if(isCollectable)
                    System.out.print("C");
                else if (map[i][j].getCard() == null)
                    System.out.print("_");
                else if(map[i][j].getCard() instanceof Unit && ((Unit)map[i][j].getCard()).isHero())
                    System.out.print("H");
                else
                    System.out.print("M");
                if(j != 8)
                    System.out.print("|");
            }
            System.out.print("\n");
        }
    }
    public void setSinglePlayer(String gameMode){
        if(!(gameMode.toLowerCase().equals("heromode") || gameMode.toLowerCase().equals("flagholding") || gameMode.toLowerCase().equals("flagscollecting")))
            System.out.println("invalid Mode Name !!!");
        else if(!controller.isValidMainDeck(controller.getCurrentPlayerName()))
            System.out.println("Your Main Deck is Invalid !!!");
        else {
            controller.startSinglePlayer(gameMode.toLowerCase());
            menuMode = 5;
        }
    }
    public void setMultiPlayer(String name1 , String name2 , String gameMode){
        if(!(gameMode.toLowerCase().equals("heromode") || gameMode.toLowerCase().equals("flagholding") || gameMode.toLowerCase().equals("flagscollecting")))
            System.out.println("invalid Mode Name !!!");
        else if(!controller.isValidPlayer(name1) || !controller.isValidPlayer(name2))
            System.out.println("invalid Players !");
        else if(!controller.isValidMainDeck(name1))
            System.out.println("Player " + name1 + "'s Main Deck is Invalid !!!");
        else if(!controller.isValidMainDeck(name2))
            System.out.println("Player " + name2 + "'s Main Deck is Invalid !!!");
        else{
            controller.startMultiPlayer(name1,name2,gameMode.toLowerCase());
            menuMode = 5;
        }
    }
    //////////////////////////// END ARMAN ////////////////////////////////

    public void useSpecialPowerOfHero(int x, int y) {
        int ans = controller.useSpecialPowerOfHero(x, y);
        switch (ans){
            case 0:
                System.out.println("wrong target");
                break;
            case 2:
                System.out.println("not enough mana");
                break;
            case 3:
                System.out.println("this card has no special power");
                break;
            case 1:
                System.out.println("special power used successfully");
                break;
            case 4:
                System.out.println("no card has been selected");
                break;
            case 5:
                System.out.println("the selected card is not hero!");
                break;
            case 6:
                System.out.println("the special power is not cast able");
                break;
        }
    }

    public void showHand(){
        ArrayList<Card> hand = controller.showHand();
        for(int i = 0; i < hand.size(); i++)
            System.out.println(hand.get(i).getName());
        Card nextCard = controller.getNextCard();
        if(nextCard == null)
            System.out.println("next card : nothing");
        else
            System.out.println("next card : " + nextCard.getName());
    }

//    public void showCollectables(){
//        System.out.println(controller.showCollectables());
//    }
    public void insert(String cardName, String coords){
        int x = Integer.parseInt(coords.substring(coords.indexOf("(") + 1,coords.indexOf(",")));
        int y = Integer.parseInt(coords.substring(coords.indexOf(",") + 1, coords.indexOf(")")));
        System.out.println(controller.insert(cardName, x, y));
        endGame();
    }
    public void useItem(String input){
        int x = Integer.parseInt(input.substring(input.indexOf("n") + 2, input.indexOf(",")));
        int y = Integer.parseInt(input.substring(input.indexOf(",") + 2 , input.indexOf("]")));
        System.out.println(controller.useItem(x, y));
    }
    public void endTrun() {

    }

//    public void ShowCollectables() {
//
//    }

    public void selectItemByID(int itemID) {

    }

    public void showCurrentItemInfo() {
        System.out.println(controller.getCurrentItemInfo());
    }

    public void useCurrentItem(int x, int y) {

    }

    public void EnterGraveYard() {

    }

    public void showInfoOfCard(int cardID) {

    }

    public void help() {

    }

    public void endGame() {
        String str = controller.checkEndGame();
        if(str.equals("nothing happen"))
            return;
        else {
            System.out.println("Player " + str + " Won");
            menuMode = 1;
        }

    }

    public void exit() {

    }

    public void showMenu() {

    }

}