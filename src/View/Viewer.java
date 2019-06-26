package View;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.*;

import Controller.*;
import Modules.*;
import Modules.Cell;
import javafx.animation.Animation;
import javafx.animation.PathTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.effect.InnerShadow;
import javafx.scene.shape.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Shear;
import javafx.scene.transform.Translate;
import javafx.util.Duration;

import javax.sound.midi.Soundbank;
import javax.swing.*;
import java.util.Comparator;

public class Viewer {
    private Group group;
    private Scene scene;
    private Scanner scanner = new Scanner(System.in);
    private Server controller = new Server();
    private int menuMode = 0;
    private boolean isAIPlayerActive = false;
    private final ImageView[] selectedCardImageView = new ImageView[1];
    private final ImageView[] selectedHandCardImageView = new ImageView[1];
    private final Text[] selectedHandCardTextMana = new Text[1];
    private final int[] selectedHandCardID = new int[1];
    private final int[] selectedHandCardIndex = new int[1];
    private final ImageView[] handCardsImageView = new ImageView[5];
    private final Text[] handCardsManaText = new Text[5];
    private final int[] handCardsID = new int[5];
    private final boolean[] handCardsDidInserted = new boolean[5];
    /////////////////////
    // 0 -> before login
    // 1 -> MainMenu
    // 2 -> Collection
    // 3 -> Shop
    // 4 -> Before Battle
    // 5 -> in Battle
    ////////////////////
    boolean isInGraveYard = false;
    public Viewer(Group group, Scene scene){
        this.scene = scene;
        this.group = group;
    }


    //======================== Graphic Function ====================//
    private Button createInvisibleBtn(int w , int h , int x , int y){
        Button btn = new Button();
        btn.relocate(x,y);
        btn.setStyle("-fx-min-width: " + w + "px;-fx-min-height: " + h + "px;-fx-background-color: transparent;");
        return btn;
    }
    public void graphicShowLogin(){
        //------------------ Username -----------------//
        TextField userName = new TextField();
        userName.relocate(410,319);
        userName.setStyle("-fx-min-width: 280px;-fx-min-height: 30px;-fx-font-weight: bold;");
        //------------------ Password -----------------//
        PasswordField password = new PasswordField();
        password.relocate(410,387);
        password.setStyle("-fx-min-width: 280px;-fx-min-height: 30px;-fx-font-weight: bold;");
        //------------------ Send Button -----------------//
        Button exitBtn = createInvisibleBtn(50,50,0,0);
        Button registerBtn = createInvisibleBtn(88,35,507,445);
        Button loginBtn = createInvisibleBtn(88,35,410,445);
        //------------------ Event Handling -----------------//
        registerBtn.setOnMouseClicked(event -> {
            createAccount(userName.getText(),password.getText());
        });
        loginBtn.setOnMouseClicked(event -> {
            loginAccount(userName.getText(),password.getText());
        });
        exitBtn.setOnMouseClicked(mouseEvent -> {
            Platform.exit();
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/loginBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,userName, password, registerBtn,loginBtn,exitBtn);
        }
        catch (Exception e){
            System.out.println("Error While Showing Menu !");
        }
    }
    public void graphicShowUserMenu(){
        //------------------ Texts -------------------//
        Text money = new Text(getCurrentMoney());
        money.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 17));
        money.setFill(Color.rgb(229,227,6));
        money.relocate(975 , 644);
        Text username = new Text(getCurrentUsername());
        username.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 17));
        username.setFill(Color.rgb(11,178,191));
        username.relocate(70 , 35);
        //------------------ Buttons -------------------//
        Button battle = createInvisibleBtn(224,56,438,160);
        Button collection = createInvisibleBtn(224,56,438,240);
        Button shop = createInvisibleBtn(224,56,438,325);
        Button customCard = createInvisibleBtn(224,56,438,407);
        Button logout = createInvisibleBtn(224,56,438,490);
        //------------------ Event Handling -----------------//
        customCard.setOnMouseClicked(event -> {
            graphicShowCustomCard();
        });
        collection.setOnMouseClicked(event -> {
            group.getChildren().removeAll(group.getChildren());
            graphicShowCollection();
        });
        shop.setOnMouseClicked(event -> {
            group.getChildren().removeAll(group.getChildren());
            graphicShowShop();
        });
        battle.setOnMouseClicked(mouseEvent -> {
            graphicShowGameModes();
        });
        logout.setOnMouseClicked(mouseEvent -> {
            controller.logOut();
            group.getChildren().removeAll(group.getChildren());
            graphicShowLogin();
            this.menuMode = 0;
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/userMenuBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,battle,collection,logout,shop,money,username,customCard);
        }
        catch (Exception e){
            System.out.println("Error While Showing Menu !");
        }
    }
    public void graphicShowCustomCard(){
        Label createCustomCardBtn = new Label("Create Custom Card");
        createCustomCardBtn.relocate(947,640);
        createCustomCardBtn.setTextFill(Color.rgb(39, 174, 96));
        createCustomCardBtn.setStyle("-fx-border-color: rgb(39, 174, 96);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;");
        //------------------ input Box -----------------//
        TextField inputBox = new TextField();
        inputBox.setPromptText("Field Place Holder ...");
        inputBox.relocate(40,180);
        inputBox.setStyle("-fx-min-width: 105px;-fx-border-style: solid;-fx-border-width: 1px;-fx-border-color: #666;-fx-max-width: 105px;-fx-min-height: 37px;-fx-font-weight: bold;-fx-background-color: rgba(0,0,0,1);-fx-text-fill: white");
        //------------------ Buttons -------------------//
        Button back = createInvisibleBtn(65,65,1027,9);
        //------------------ Event Handling -----------------//
        createCustomCardBtn.setOnMouseEntered(event -> {
            createCustomCardBtn.setTextFill(Color.WHITE);
            createCustomCardBtn.setStyle("-fx-background-color: rgb(39, 174, 96);-fx-border-color: rgb(39, 174, 96);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
        });
        createCustomCardBtn.setOnMouseExited(event -> {
            createCustomCardBtn.setTextFill(Color.rgb(39, 174, 96));
            createCustomCardBtn.setStyle("-fx-background-color: transparent;-fx-border-color: rgb(39, 174, 96);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
        });
        createCustomCardBtn.setOnMouseClicked(mouseEvent -> {
            // Create function
        });
        back.setOnMouseClicked(mouseEvent -> {
            group.getChildren().removeAll(group.getChildren());
            graphicShowUserMenu();
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/customCardBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,back,inputBox,createCustomCardBtn);
        }
        catch (Exception e){
            System.err.println("Error While Showing Custom Card !");
        }
    }
    public void graphicShowBattleModes(){
        //------------------ Buttons -------------------//
        Button captureTheFlag = createInvisibleBtn(265,487,110,72);
        Button heroMode = createInvisibleBtn(265,487,416,72);
        Button flagHolding = createInvisibleBtn(265,487,726,72);
        //------------------ Event Handling -----------------//
        heroMode.setOnMouseClicked(mouseEvent -> {
            this.menuMode = 5;
            setMultiPlayer("Arman Zarei","Ariyan Zarei","heromode");
            graphicShowGame();
        });
        flagHolding.setOnMouseClicked(mouseEvent -> {
            this.menuMode = 5;
            setMultiPlayer("Arman Zarei","Ariyan Zarei","flagholding");
            graphicShowGame();
        });
        captureTheFlag.setOnMouseClicked(mouseEvent -> {
            this.menuMode = 5;
            setMultiPlayer("Arman Zarei","Ariyan Zarei","flagscollecting");
            graphicShowGame();
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/battleModesBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,flagHolding,heroMode,captureTheFlag);
        }
        catch (Exception e){
            System.err.println("Error While Showing Battle Modes !");
        }
    }
    public void graphicShowGameModes(){
        //------------------ Buttons -------------------//
        Button customMode = createInvisibleBtn(267,492,269,80);
        Button storyMode = createInvisibleBtn(267,492,562,80);
        //------------------ Event Handling -----------------//
        customMode.setOnMouseClicked(mouseEvent -> {
            System.out.println("-- Custom Mode --");
            graphicShowBattleModes();
        });
        storyMode.setOnMouseClicked(mouseEvent -> {
            System.out.println("-- Story Mode --");
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/gameModesBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,storyMode,customMode);
        }
        catch (Exception e){
            System.err.println("Error While Showing Game Modes !");
        }
    }
    public void graphicShowShop(){
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setMinSize(770,400);
        scrollPane.setMaxSize(770,400);
        scrollPane.relocate(140,170);
        scrollPane.setStyle("-fx-background: transparent;-fx-background-color: transparent;");
        Pane cardsList = new Pane();
        cardsList.setStyle("-fx-background: transparent;-fx-background-color: transparent;");
        scrollPane.setContent(cardsList);

        //------------------ Search Box -----------------//
        TextField searchBox = new TextField();
        searchBox.setPromptText("Search ...");
        searchBox.relocate(349,118);
        searchBox.setStyle("-fx-min-width: 105px;-fx-max-width: 105px;-fx-min-height: 37px;-fx-font-weight: bold;");
        //------------------ Texts -------------------//
        Text money = new Text(getCurrentMoney());
        money.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 17));
        money.setFill(Color.rgb(229,227,6));
        money.relocate(960 , 629);
        //------------------ Buttons -------------------//
        Button shop = createInvisibleBtn(92,37,138,118);
        Button collection = createInvisibleBtn(92,37,246,118);
        Button search = createInvisibleBtn(48,37,459,118);
        Button back = createInvisibleBtn(65,65,1027,9);
        //------------------ Event Handling -----------------//
        shop.setOnMouseClicked(event -> {
            cardsList.getChildren().removeAll(cardsList.getChildren());
            showCardsInShop(cardsList,money,"");
            searchBox.setText("");
        });
        collection.setOnMouseClicked(event -> {
            cardsList.getChildren().removeAll(cardsList.getChildren());
            showCollectionInShop(cardsList,money);
            searchBox.setText("");
        });
        search.setOnMouseClicked(mouseEvent -> {
            cardsList.getChildren().removeAll(cardsList.getChildren());
            showCardsInShop(cardsList,money,searchBox.getText());
            searchBox.setText("");
        });
        searchBox.setOnKeyReleased(mouseEvent -> {
            if(mouseEvent.getCode() == KeyCode.ENTER){
                cardsList.getChildren().removeAll(cardsList.getChildren());
                showCardsInShop(cardsList,money,searchBox.getText());
                searchBox.setText("");
            }
        });
        back.setOnMouseClicked(mouseEvent -> {
            group.getChildren().removeAll(group.getChildren());
            graphicShowUserMenu();
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/shopBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,collection,shop,money,search,back,searchBox,scrollPane);
        }
        catch (Exception e){
            System.err.println("Error While Showing Shop !");
        }
    }
    private void labelMainDeckOff(Label mainDeck){
        mainDeck.setText("Set as Main Deck");
        mainDeck.setTextFill(Color.rgb(52, 152, 219));
        mainDeck.setStyle("-fx-border-color: rgb(52, 152, 219);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;");
    }
    private void labelMainDeckOn(Label mainDeck){
        mainDeck.setText("Main Deck");
        mainDeck.setTextFill(Color.rgb(39, 174, 96));
        mainDeck.setStyle("-fx-border-color: rgb(39, 174, 96);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;");
    }
    public void graphicShowCollection(){
        ComboBox deckSelector = new ComboBox();
        for(int i = 0 ; i < controller.getPlayerDecks().size() ; i++)
            deckSelector.getItems().add(controller.getPlayerDecks().get(i).getName());
        deckSelector.setMinWidth(120);
        deckSelector.setVisibleRowCount(3);
        deckSelector.relocate(256,120);

        ScrollPane scrollPane1 = new ScrollPane();
        scrollPane1.setMinSize(770,200);
        scrollPane1.setMaxSize(770,200);
        scrollPane1.relocate(140,170);
        scrollPane1.setStyle("-fx-background: transparent;-fx-background-color: transparent;-fx-border-width: 2px;-fx-border-color: #34495e;-fx-border-style: solid;");
        Pane deckList = new Pane();
        deckList.setStyle("-fx-background: transparent;-fx-background-color: transparent;");
        scrollPane1.setContent(deckList);
        scrollPane1.setVisible(false);

        ScrollPane scrollPane2 = new ScrollPane();
        scrollPane2.setMinSize(770,200);
        scrollPane2.setMaxSize(770,200);
        scrollPane2.relocate(140,390);
        scrollPane2.setStyle("-fx-background: transparent;-fx-background-color: transparent;-fx-border-width: 2px;-fx-border-color: #34495e;-fx-border-style: solid;");
        Pane collectionList = new Pane();
        deckList.setStyle("-fx-background: transparent;-fx-background-color: transparent;");
        scrollPane2.setContent(collectionList);
        scrollPane2.setVisible(false);


        Label mainDeck = new Label();
        mainDeck.relocate(385,122);
        labelMainDeckOff(mainDeck);
        mainDeck.setVisible(false);


        Label removeDeck = new Label("Remove Deck");
        removeDeck.relocate(32,122);
        removeDeck.setTextFill(Color.rgb(231, 76, 60));
        removeDeck.setStyle("-fx-border-color: rgb(231, 76, 60);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;");
        removeDeck.setVisible(false);

        Label importDeck = new Label("Import Deck");
        importDeck.relocate(29,642);
        importDeck.setTextFill(Color.rgb(116, 185, 255));
        importDeck.setStyle("-fx-border-color: rgb(116, 185, 255);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;");

        Label exportDeck = new Label("Export Deck");
        exportDeck.relocate(115,642);
        exportDeck.setTextFill(Color.rgb(85, 239, 196));
        exportDeck.setStyle("-fx-border-color: rgb(85, 239, 196);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;");
        exportDeck.setVisible(false);

        //------------------ input Box -----------------//
        TextField inputBox = new TextField();
        inputBox.setPromptText("New Deck ...");
        inputBox.relocate(782,118);
        inputBox.setStyle("-fx-min-width: 105px;-fx-max-width: 105px;-fx-min-height: 37px;-fx-font-weight: bold;-fx-background-color: rgba(0,0,0,1);-fx-text-fill: white");
        //------------------ Texts -------------------//
        Text money = new Text(getCurrentMoney());
        money.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 17));
        money.setFill(Color.rgb(229,227,6));
        money.relocate(960 , 629);
        //------------------ Buttons -------------------//
        Button createDeckBtn = createInvisibleBtn(40,38,895,117);
        Button back = createInvisibleBtn(65,65,1027,9);
        //------------------ Event Handling -----------------//
        importDeck.setOnMouseEntered(mouseEvent -> {
            importDeck.setTextFill(Color.WHITE);
            importDeck.setStyle("-fx-background-color: rgb(9, 132, 227);-fx-border-color: rgb(9, 132, 227);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px");
        });
        importDeck.setOnMouseExited(mouseEvent -> {
            importDeck.setTextFill(Color.rgb(116, 185, 255));
            importDeck.setStyle("-fx-background-color: transparent;-fx-border-color: rgb(116, 185, 255);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
        });
        importDeck.setOnMouseClicked(mouseEvent -> {
            // Ali Do Here
        });
        exportDeck.setOnMouseEntered(mouseEvent -> {
            exportDeck.setTextFill(Color.WHITE);
            exportDeck.setStyle("-fx-background-color: rgb(0, 184, 148);-fx-border-color: rgb(0, 184, 148);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px");
        });
        exportDeck.setOnMouseExited(mouseEvent -> {
            exportDeck.setTextFill(Color.rgb(85, 239, 196));
            exportDeck.setStyle("-fx-background-color: transparent;-fx-border-color: rgb(85, 239, 196);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
        });
        exportDeck.setOnMouseClicked(mouseEvent -> {
            // Ali Do Here
        });
        removeDeck.setOnMouseEntered(mouseEvent -> {
            removeDeck.setTextFill(Color.BLACK);
            removeDeck.setStyle("-fx-background-color: rgb(231, 76, 60);-fx-border-color: rgb(231, 76, 60);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px");
        });
        removeDeck.setOnMouseExited(mouseEvent -> {
            removeDeck.setTextFill(Color.rgb(231, 76, 60));
            removeDeck.setStyle("-fx-background-color: transparent;-fx-border-color: rgb(231, 76, 60);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
        });
        removeDeck.setOnMouseClicked(mouseEvent -> {
            deletePlayerDeck(deckSelector.getValue().toString());
            deckSelector.getItems().remove(deckSelector.getValue());
            removeDeck.setVisible(false);
            mainDeck.setVisible(false);
            scrollPane1.setVisible(false);
            scrollPane2.setVisible(false);
            exportDeck.setVisible(false);
            removeDeck.relocate(500,122);
            collectionList.getChildren().removeAll(collectionList.getChildren());
            deckList.getChildren().removeAll(deckList.getChildren());
            deckSelector.getSelectionModel().select(null);
        });
        mainDeck.setOnMouseEntered(event -> {
            if(!mainDeck.getTextFill().equals(Color.rgb(39, 174, 96))){
                mainDeck.setTextFill(Color.BLACK);
                mainDeck.setStyle("-fx-background-color: rgb(52, 152, 219);-fx-border-color: rgb(52, 152, 219);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
            }
        });
        mainDeck.setOnMouseExited(event -> {
            if(!mainDeck.getTextFill().equals(Color.rgb(39, 174, 96))){
                mainDeck.setTextFill(Color.rgb(52, 152, 219));
                mainDeck.setStyle("-fx-background-color: transparent;-fx-border-color: rgb(52, 152, 219);-fx-border-style: solid;-fx-border-width: 2px;-fx-padding: 2px 5px;-fx-border-radius: 5px;-fx-background-radius: 5px;");
            }
        });
        mainDeck.setOnMouseClicked(event -> {
            if(!mainDeck.getTextFill().equals(Color.rgb(39, 174, 96))){
                labelMainDeckOn(mainDeck);
                removeDeck.relocate(465,122);
                setMainDeck(deckSelector.getValue().toString());
            }
        });
        deckSelector.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                if(t1 == null)
                    return;
                removeDeck.setVisible(true);
                mainDeck.setVisible(true);
                scrollPane1.setVisible(true);
                scrollPane2.setVisible(true);
                exportDeck.setVisible(true);
                deckList.getChildren().removeAll(deckList.getChildren());
                collectionList.getChildren().removeAll(collectionList.getChildren());
                showCollection(collectionList,deckList,t1);
                showDeck(t1,deckList,collectionList);
                if(controller.getPlayerMainDeck()!= null && t1.matches(controller.getPlayerMainDeck().getName())){
                    labelMainDeckOn(mainDeck);
                    removeDeck.relocate(465,122);
                }
                else{
                    labelMainDeckOff(mainDeck);
                    removeDeck.relocate(500,122);
                }
            }
        });
        createDeckBtn.setOnMouseClicked(mouseEvent -> {
            createPlayerDeck(inputBox.getText());
            if(!inputBox.getText().matches("")) {
                deckSelector.getItems().add(inputBox.getText());
                deckSelector.getSelectionModel().select(inputBox.getText());
                deckList.getChildren().removeAll(deckList.getChildren());
                collectionList.getChildren().removeAll(collectionList.getChildren());
                showCollection(collectionList, deckList, inputBox.getText());
                showDeck(inputBox.getText(),deckList,collectionList);
                mainDeck.setVisible(true);
                removeDeck.setVisible(true);
                scrollPane1.setVisible(true);
                scrollPane2.setVisible(true);
                exportDeck.setVisible(true);
                labelMainDeckOff(mainDeck);
                removeDeck.relocate(500,122);
            }
            inputBox.setText("");
        });
        inputBox.setOnKeyReleased(mouseEvent -> {
            if(mouseEvent.getCode() == KeyCode.ENTER && !inputBox.getText().matches("")){
                createPlayerDeck(inputBox.getText());
                deckSelector.getItems().add(inputBox.getText());
                deckSelector.getSelectionModel().select(inputBox.getText());
                deckList.getChildren().removeAll(deckList.getChildren());
                collectionList.getChildren().removeAll(collectionList.getChildren());
                showCollection(collectionList, deckList, inputBox.getText());
                showDeck(inputBox.getText(),deckList,collectionList);
                mainDeck.setVisible(true);
                removeDeck.setVisible(true);
                scrollPane1.setVisible(true);
                scrollPane2.setVisible(true);
                exportDeck.setVisible(true);
                labelMainDeckOff(mainDeck);
                inputBox.setText("");
                removeDeck.relocate(500,122);
            }
        });
        back.setOnMouseClicked(mouseEvent -> {
            group.getChildren().removeAll(group.getChildren());
            graphicShowUserMenu();
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/collectionBg.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,money,createDeckBtn,back,inputBox,scrollPane1,scrollPane2,deckSelector,importDeck,exportDeck,mainDeck,removeDeck);
        }
        catch (Exception e){
            System.err.println("Error While Showing Shop !");
        }
    }
    public void graphicShowGame(){
        //------------------ Show Players Name -----------------//
        Text namePlayer1 = new Text(controller.getCurrentGame().getPlayersOfGame()[0].getUsername());
        namePlayer1.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 17));
        namePlayer1.setFill(Color.rgb(231, 76, 60));
        namePlayer1.relocate(783 + 147.0/2.0 - namePlayer1.getLayoutBounds().getWidth()/2 , 57);
        Text namePlayer2 = new Text(controller.getCurrentGame().getPlayersOfGame()[1].getUsername());
        namePlayer2.setFont(Font.font("Tahoma", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 17));
        namePlayer2.setFill(Color.rgb(52, 152, 219));
        namePlayer2.relocate(182 + 141.0/2.0 - namePlayer2.getLayoutBounds().getWidth()/2 , 54);
        //------------------ Show Player's Mana -----------------//
        Text manaPlayer1 = new Text(String.valueOf(controller.getCurrentGame().getManaOfPlayers()[0]));
        manaPlayer1.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 14));
        manaPlayer1.setFill(Color.rgb(255,255,255));
        manaPlayer1.relocate(252 , 96);
        Text manaPlayer2 = new Text(String.valueOf(controller.getCurrentGame().getManaOfPlayers()[1]));
        manaPlayer2.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 14));
        manaPlayer2.setFill(Color.rgb(255,255,255));
        manaPlayer2.relocate(849 , 98);
        //------------------ Effect Cell Map -----------------//
        InnerShadow eff = new InnerShadow();
        eff.setRadius(10);
        eff.setColor(Color.WHITE);
        //------------------ Show Map -----------------//
        Rectangle[][] mapCellsGraphic = new Rectangle[5][9];
        ArrayList<Rectangle> mapGraphic = new ArrayList<>();
        for(int i = 0 ; i < 5 ; i++){
            for(int j = 0 ; j < 9 ; j++){
                Rectangle r = new Rectangle();
                r.relocate(202 + j*72 - i*7,170 + i*72);
                r.setWidth(70);
                r.setHeight(70);
                r.setArcWidth(5);
                r.setArcHeight(5);
                r.setFill(Color.rgb(0,0,0,0.3));
                double shearX = -0.1;
                Shear shear = new Shear();
                shear.setX(shearX);
                r.getTransforms().add(shear);
                final int xCoord = i , yCoord = j;
                r.setOnMouseEntered(mouseEvent -> {
                    r.setEffect(eff);
                });
                r.setOnMouseExited(mouseEvent -> {
                    r.setEffect(null);
                });
                r.setOnMouseClicked(mouseEvent -> {
                    if(selectedCardImageView[0] != null && moveCurrentCard(xCoord,yCoord)){
                        double xCoordNew = 202 + 72*yCoord + 70.0/2.0 - selectedCardImageView[0].getLayoutBounds().getWidth()/2 - 2*7;
                        double yCoordNew = 170 + xCoord*72 + 55.0/2.0 - selectedCardImageView[0].getLayoutBounds().getHeight()/2;
                        double xCoordPrev = selectedCardImageView[0].getLayoutX();
                        double yCoordPrev = selectedCardImageView[0].getLayoutY();
                        System.out.println("x : " + xCoordPrev + " , y : " + yCoordPrev);
                        System.out.println("x : " + xCoordNew + " , y : " + yCoordNew);
                        Path path = new Path();
                        path.getElements().add(new MoveTo(selectedCardImageView[0].getLayoutBounds().getWidth()/2,selectedCardImageView[0].getLayoutBounds().getHeight()/2));
                        path.getElements().add(new LineTo(xCoordNew - xCoordPrev + selectedCardImageView[0].getLayoutBounds().getWidth()/2,yCoordNew - yCoordPrev + selectedCardImageView[0].getLayoutBounds().getWidth()/2));
                        path.setVisible(false);
                        PathTransition pathTransition = new PathTransition(Duration.millis(1000), path, selectedCardImageView[0]);
                        group.getChildren().add(path);
                        pathTransition.play();
                        selectedCardImageView[0] = null;
                        controller.getCurrentGame().setCurrentCard(null);
                        System.out.println("Graphic : Card Moved !!!");
                    }
                    else if(selectedHandCardImageView[0] != null && insert(selectedHandCardID[0],xCoord,yCoord)){
                        selectedHandCardID[0] = 0;
                        double xNew = 202 + 72*yCoord + 70.0/2.0 - selectedHandCardImageView[0].getLayoutBounds().getWidth()/2 - 2*7;
                        double yNew = 170 + xCoord*72 + 55.0/2.0 - selectedHandCardImageView[0].getLayoutBounds().getHeight()/2;
                        selectedHandCardImageView[0].relocate(xNew,yNew);
                        manaPlayer1.setText(String.valueOf(controller.getCurrentGame().getManaOfPlayers()[0]));
                        manaPlayer2.setText(String.valueOf(controller.getCurrentGame().getManaOfPlayers()[1]));
                        for(int p = 0 ; p < 5 ; p++ ){
                            if(!controller.getCurrentGame().cardIsInHand(controller.getCurrentGame().findCardByID(handCardsID[p]))){
                                handCardsImageView[p] = null;
                                handCardsManaText[p] = null;
                            }
                        }
                        group.getChildren().remove(selectedHandCardTextMana[0]);
                        selectedHandCardTextMana[0] = null;
                        selectedHandCardImageView[0] = null;
                        handCardsDidInserted[selectedHandCardIndex[0]] = false;
                        selectedHandCardIndex[0] = -1;
                        System.out.println("Graphic : Card inserted !!!");
                    }
                    else{
                        selectedCardImageView[0] = null;
                        controller.getCurrentGame().setCurrentCard(null);
                        selectedHandCardImageView[0] = null;
                        selectedHandCardTextMana[0] = null;
                        selectedHandCardID[0] = 0;
                        System.out.println("Graphic : Can't Do Shit , Selected Card in map And Hand been set to Null");
                    }
                });
                mapGraphic.add(r);
                mapCellsGraphic[i][j] = r;
            }
        }
        //------------------ Buttons -------------------//
        Button endTurn = createInvisibleBtn(201,66,828,587);
        //------------------ Event Handling -----------------//
        endTurn.setOnMouseClicked(mouseEvent -> {
            controller.endTurn();
            manaPlayer1.setText(String.valueOf(controller.getCurrentGame().getManaOfPlayers()[0]));
            manaPlayer2.setText(String.valueOf(controller.getCurrentGame().getManaOfPlayers()[1]));
            for(int p = 0 ; p < 5 ; p++) {
                if(!handCardsDidInserted[p]) {
                    group.getChildren().remove(handCardsImageView[p]);
                    group.getChildren().remove(handCardsManaText[p]);
                }
            }
            graphicShowHand();
        });
        //------------------ Show Fields Register Or Login -----------------//
        try {
            Image image;
            image = new Image(new FileInputStream("img/gameMap.png"));
            ImageView imageView = new ImageView(image);
            imageView.setPreserveRatio(true);
            group.getChildren().addAll(imageView,endTurn,manaPlayer1,manaPlayer2,namePlayer1,namePlayer2);
            group.getChildren().addAll(mapGraphic);
        }
        catch (Exception e){
            System.err.println("Error While Showing Battle !");
        }
        //------------------ MoveMents -------------------//
        selectedCardImageView[0] = null;
        selectedHandCardImageView[0] = null;
        selectedHandCardTextMana[0] = null;
        selectedHandCardID[0] = 0;
        //------------------ Test Sprite -------------------//
        try {
            for(int i = 0 ; i < 2 ; i++ ){
                int j = (i==0)?0:8;
                String str = controller.getCurrentGame().getPlayerHero(i).getName().toLowerCase();
                Image boyImage = new Image(new FileInputStream("img/sprites/arash.png"));
                ImageView boyView = new ImageView(boyImage);
                group.getChildren().add(boyView);
                boyView.setViewport(new Rectangle2D(505,202,100,100));
                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>();
                x.add(707);
                x.add(707);
                x.add(707);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(505);
                y.add(202);
                y.add(101);
                y.add(0);
                y.add(909);
                y.add(808);
                y.add(707);
                y.add(606);
                y.add(505);
                y.add(404);
                y.add(303);
                y.add(202);
                y.add(101);
                y.add(0);
                y.add(909);
                boyView.relocate(202 + 72*j + 70.0/2.0 - boyView.getLayoutBounds().getWidth()/2 - 2*7 ,170 + 2*72 + 55.0/2.0 - boyView.getLayoutBounds().getHeight()/2);
                final Animation animation = new SpriteAnimation(14 , x,y,100,boyView,Duration.millis(1000));
                animation.setCycleCount(Animation.INDEFINITE);
                animation.play();
                final int k = i;
                int cardID = controller.getCurrentGame().getPlayerHero(k).getCardID();
                boyView.setOnMouseEntered(mouseEvent -> {
                    int[] coord = controller.getCardCoord(cardID);
                    mapCellsGraphic[coord[0]][coord[1]].setEffect(eff);
                });
                boyView.setOnMouseExited(mouseEvent -> {
                    int[] coord = controller.getCardCoord(cardID);
                    mapCellsGraphic[coord[0]][coord[1]].setEffect(null);
                });
                final ImageView imgView = boyView;
                boyView.setOnMouseClicked(mouseEvent -> {
                    System.out.println(controller.getCurrentGame().getCardOwner(controller.getCurrentGame().findCardByID(cardID)).getUsername());
                    selectedCardImageView[0] = imgView;
                    select(cardID);
                    System.out.println("Graphic : Card in map Selected !!!");
                });
            }
        }
        catch (Exception e){
            System.err.println("Animation Sprite Failed !!!");
        }
        graphicShowHand();
    }
    private void graphicShowHand(){
        //------------------ Show Hand -----------------//
        ArrayList<Card> handCards = controller.showHand();
        try {
            for(int i = 0 ; i < handCards.size() ; i++ ){
                int manaCost = 0;
                if(handCards.get(i) instanceof Unit)
                    manaCost = ((Unit)((Unit) handCards.get(i))).getManaCost();
                else if(handCards.get(i) instanceof SpellCard)
                    manaCost = ((SpellCard)((SpellCard) handCards.get(i))).getManaCost();
                Text cardMana = new Text(String.valueOf(manaCost));
                cardMana.setFill(Color.rgb(60,60,60));
                cardMana.setFont(Font.font("verdana", FontWeight.EXTRA_BOLD , FontPosture.REGULAR, 15));
                cardMana.relocate(279 + i*115,656 );
                group.getChildren().add(cardMana);
                Image cardImage = new Image(new FileInputStream("img/sprites/arash.png"));
                ImageView cardView = new ImageView(cardImage);
                group.getChildren().add(cardView);
                cardView.setViewport(new Rectangle2D(505,202,100,100));
                ArrayList<Integer> x = new ArrayList<>();
                ArrayList<Integer> y = new ArrayList<>();
                x.add(707);
                x.add(707);
                x.add(707);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(606);
                x.add(505);
                y.add(202);
                y.add(101);
                y.add(0);
                y.add(909);
                y.add(808);
                y.add(707);
                y.add(606);
                y.add(505);
                y.add(404);
                y.add(303);
                y.add(202);
                y.add(101);
                y.add(0);
                y.add(909);
                final Animation animation = new SpriteAnimation(14 , x,y,100,cardView,Duration.millis(1000));
                animation.setCycleCount(Animation.INDEFINITE);
                animation.play();
                cardView.relocate(233 + i*115,550 );
                handCardsImageView[i] = cardView;
                handCardsManaText[i] = cardMana;
                handCardsID[i] = handCards.get(i).getCardID();
                handCardsDidInserted[i] = false;
                final int k = i;
                final ImageView imgView = cardView;
                final Text manaTxt = cardMana;
                cardView.setOnMouseClicked(mouseEvent -> {
                    if(controller.getCurrentGame().cardIsInHand(controller.getCurrentGame().findCardByID(handCards.get(k).getCardID()))){
                        selectedHandCardID[0] = handCards.get(k).getCardID();
                        selectedHandCardImageView[0] = imgView;
                        selectedHandCardTextMana[0] = manaTxt;
                        selectedHandCardIndex[0] = k;
                        System.out.println("Graphic : Card in Hand Selected !!!");
                    }
                    else{
                        System.out.println("Clicked in Here");
                        selectedCardImageView[0] = imgView;
                        select(handCards.get(k).getCardID());
                        System.out.println("Graphic : Card in Map Selected !!!");
                    }
                    System.out.println("Graphic : Cardname > " + handCards.get(k).getName() + " with ID > " + handCards.get(k).getCardID() + " Clicked !!!");
                });
            }
        }
        catch (Exception e){
            System.err.println("Animation Sprite Failed !!!");
        }
    }
    public void gameHandle() {
        try{
            Image image = new Image(new FileInputStream("img/cursor.png"));  //pass in the image path
            scene.setCursor(new ImageCursor(image));
        }catch (Exception e){
            System.out.println(e);
        }
        for(int k = 0 ; k < 2 ; k++){
            String user = (k==0)?"Arman Zarei":"Ariyan Zarei";
            createAccount(user,user);
            buyCard("Arash");
            buyCard("giv");
            buyCard("giv");
            buyCard("gorgSefid");
            buyCard("gorgSefid");
            buyCard("Iraj");
            buyCard("Iraj");
            buyCard("jadoogar");
            buyCard("jadoogar");
            buyCard("jen");
            buyCard("jen");
            buyCard("marSammi");
            buyCard("marSammi");
            buyCard("NaneSarma");
            buyCard("NaneSarma");
            buyCard("oqab");
            buyCard("oqab");
            buyCard("piran");
            buyCard("piran");
            buyCard("Shock");
            createPlayerDeck("deck1");
            for(int i = 0 ; i < controller.getCurrentPlayer().getCollection().size() ; i++){
                int cardID = controller.getCurrentPlayer().getCollection().get(i).getCardID();
                addCardToDeck(cardID,"deck1");
            }
            setMainDeck("deck1");
            controller.logOut();
        }
//
//        this.menuMode = 5;
//        setMultiPlayer("Arman Zarei","Ariyan Zarei","heromode");
//        graphicShowGame();
        graphicShowLogin();



//        while (true) {
//            String input = scanner.nextLine();
//            //============== Before Login =============//r
//            if (menuMode == 0) {
//                //====================================== Create Account =================================//
//                if (input.toLowerCase().matches("create account \\w+"))
//                    createAccount(input);
//                    //====================================== Login into Account =================================//
//                else if (input.toLowerCase().matches("login \\w+"))
//                    loginAccount(input);
//                    //====================================== Show LeaderBoard =================================//
//                else if (input.toLowerCase().matches("show leaderboard"))
//                    showLeaderBoard();
//                    //====================================== Save =================================//
//                else if (input.toLowerCase().matches("save"))
//                    controller.save();
//                    //====================================== Help Account =================================//
//                else if (input.toLowerCase().matches("help"))
//                    accountHelp();
//                else
//                    System.out.println("Invalid Command !!!");
//            }
//            //============== MainMenu =============//
//            else if (menuMode == 1) {
//                if (input.toLowerCase().matches("enter \\w+"))
//                    goToMenu(input.split(" ")[1]);
//                else if (input.toLowerCase().matches("exit"))
//                    this.menuMode = 0;
//                else if (input.toLowerCase().matches("help"))
//                    printMainMenu();
//                else if (input.toLowerCase().matches("logout")) {
//                    controller.logOut();
//                    this.menuMode = 0;
//                } else
//                    System.out.println("invalid Command !!!");
//            }
//            //============== Collection =============//
//            else if (menuMode == 2) {
//                if (input.toLowerCase().matches("exit"))
//                    this.menuMode = 1;
//                else if (input.toLowerCase().matches("show"))
//                    showCollection();
//                else if (input.toLowerCase().matches("search \\w+"))
//                    printSearchCollection(input.split(" ")[1]);
//                else if (input.toLowerCase().matches("save"))
//                    System.out.println("Saved Successfully !!!");
//                else if (input.toLowerCase().matches("create deck \\w+"))
//                    createPlayerDeck(input.split(" ")[2]);
//                else if (input.toLowerCase().matches("delete deck \\w+"))
//                    deletePlayerDeck(input.split(" ")[2]);
//                else if (input.toLowerCase().matches("add \\d+ to deck \\w+"))
//                    addCardToDeck(Integer.parseInt(input.split(" ")[1]), input.split(" ")[4]);
//                else if (input.toLowerCase().matches("remove \\d+ from deck \\w+"))
//                    removeCardFromDeck(Integer.parseInt(input.split(" ")[1]), input.split(" ")[4]);
//                else if (input.toLowerCase().matches("validate deck \\w+"))
//                    checkDeckValidation(input.split(" ")[2]);
//                else if (input.toLowerCase().matches("select deck \\w+"))
//                    setMainDeck(input.split(" ")[2]);
//                else if (input.toLowerCase().matches("show all decks"))
//                    showAllDecks();
//                else if (input.toLowerCase().matches("show deck \\w+"))
//                    showOneDeck(input.split(" ")[2]);
//                else if (input.toLowerCase().matches("help"))
//                    collectionHelp();
//                else
//                    System.out.println("Invalid Command !!!");
//            }
//            //============== Shop =============//
//            else if (menuMode == 3) {
//                if (input.toLowerCase().matches("exit"))
//                    menuMode = 1;
//                else if (input.toLowerCase().matches("show collection"))
//                    showCollectionInShop();
//                else if (input.toLowerCase().matches("search collection \\w+"))
//                    searchCollectionInShop(input.split(" ")[2]);
//                else if (input.toLowerCase().matches("search \\w+") && !input.split(" ")[1].matches("collection"))
//                    searchInShop(input.split(" ")[1]);
//                else if (input.toLowerCase().matches("buy \\w+"))
//                buyCard(input.split(" ")[1]);
//                else if (input.toLowerCase().matches("sell \\d+"))
//                    sellCardWithID(Integer.parseInt(input.split(" ")[1]));
//                else if (input.toLowerCase().matches("sell \\w+"))
//                    sellCardWithName(input.split(" ")[1]);
//                else if (input.toLowerCase().matches("show"))
//                    showCardsInShop();
//                else if (input.toLowerCase().matches("help"))
//                    printShopMenu();
//                else
//                    System.out.println("Invalid Command !!!");
//            }
//            //============== Before Game =============//
//            else if(menuMode == 4){
//                if(input.toLowerCase().matches("single player")) {
//                    System.out.println("Story");
//                    System.out.println("Custom Game");
//                    menuMode = 6;
//                }
//                else if(input.toLowerCase().matches("start multi player between \\w+ and \\w+ mode \\w+"))
//                    setMultiPlayer(input.split(" ")[4],input.split(" ")[6],input.split(" ")[8]);
//                else if(input.toLowerCase().matches("exit"))
//                    menuMode = 1;
//                else
//                    System.out.println("Invalid Command !!!");
//            }
//            //============== Game =============//
//            else if (menuMode == 5) {
//                if(!isInGraveYard){
//                    printGameMap();
//                    if (input.toLowerCase().matches("game info"))
//                        showGameInfo();
//                    else if (input.toLowerCase().matches("select [\\d+]"))
//                        select(Integer.parseInt(input.split("\\s")[1]));
//                    else if (input.toLowerCase().matches("show my minions"))
//                        showPlayerMinions("my");
//                    else if (input.toLowerCase().matches("show opponent minions"))
//                        showPlayerMinions("opponent");
//                    else if (input.toLowerCase().matches("show card info \\d+"))
//                        showCardInfo(Integer.parseInt(input.split(" ")[3]));
//                    else if(input.matches("show hand"))
//                        showHand();
//                    else if(input.matches("insert \\w+ in \\(\\d,\\d\\)"))
//                        insert(input.split(" ")[1],input.split(" ")[3]);
//                    //////////////////////////// ARMAN ////////////////////////////////
//                    else if(input.toLowerCase().matches("attack \\d+"))
//                        attack(Integer.parseInt(input.split(" ")[1]));
//                    else if(input.toLowerCase().matches("attack combo \\d+ [\\d+]+")){
//                        ArrayList<Integer> cardsID = new ArrayList<>();
//                        for(int i = 3 ; i < input.split(" ").length ; i++ )
//                            cardsID.add(Integer.parseInt(input.split(" ")[i]));
//                        comboAttack(Integer.parseInt(input.split(" ")[2]),cardsID);
//                    }
//                    else if(input.toLowerCase().matches("show collectables"))
//                        showCollectables();
//                    else if(input.toLowerCase().matches("show next card"))
//                        showNextCard();
//                    else if(input.toLowerCase().matches("enter graveyard"))
//                        isInGraveYard = true;
//                    else if(input.toLowerCase().matches("help"))
//                        showHelpInGame();
//                    else if(input.toLowerCase().matches("move to \\d \\d"))
//                        moveCurrentCard(Integer.parseInt(input.split(" ")[2]) , Integer.parseInt(input.split(" ")[3]) );
//                    else if(input.toLowerCase().matches("select card \\d+"))
//                        select(Integer.parseInt(input.split("\\s")[2]));
//                    else if(input.toLowerCase().matches("end turn")) {
//                        controller.endTurn();
//                        endGame();
//                        if(isAIPlayerActive)
//                            playerAIMoves();
//                    }
//                    ///////////////////////////// END ARMAN ////////////////////////////////
//                    else if(input.toLowerCase().matches("show info"))
//                        showCurrentItemInfo();
//                    else if(input.toLowerCase().matches("Use \\[location \\d, \\d\\]"))
//                        useItem(input);
//                    else if(input.toLowerCase().matches("end game"))
//                        resignGame();
//                    else
//                        System.out.println("Invalid Command !!!");
//                }
//                else{
//                    ///////////////////////////// ARMAN ////////////////////////////////
//                    if(input.toLowerCase().matches("exit"))
//                        isInGraveYard = false;
//                    else if(input.toLowerCase().matches("show info \\d+"))
//                        showInfoCardIDGraveyard(Integer.parseInt(input.split(" ")[2]));
//                    else if(input.toLowerCase().matches("show cards"))
//                        showGraveyardCards();
//                    else if(input.toLowerCase().matches("help"))
//                        showHelpGraveyard();
//                    else
//                        System.out.println("Invalid Command !");
//                    ///////////////////////////// END ARMAN ////////////////////////////////
//                }
//            } else if ( menuMode == 6 ){
//                if ( input.toLowerCase().matches("story")){
//                    switch (controller.getCurrentPlayer().getStoryModeLevel()){
//                        case 0 :
//                            setSinglePlayer("heromode");
//                            break;
//                        case 1:
//                            setSinglePlayer("flagholding");
//                            break;
//                        case 2:
//                            setSinglePlayer("flagscollecting");
//                            break;
//                    }
//                }
//            }
//        }
    }

    //======================== Account Function ====================//
    private void createAccount(String username,String password) {
        if(username.equals(""))
            System.err.println("Please Enter a Username !!!");
        else if(controller.userExists(username))
            System.err.println("This Username Already Exists !!!");
        else if(password.equals(""))
            System.err.println("Please Enter A Password !!!");
        else {
            controller.createAccount(username, password);
            controller.login(username,password);
            this.menuMode = 1;
            System.out.println("Account Created !!");
            group.getChildren().removeAll(group.getChildren());
            graphicShowUserMenu();
        }
    }
    private void loginAccount(String username,String password) {
        if(username.equals(""))
            System.err.println("Please Enter a Username !!!");
        else if(!controller.userExists(username))
            System.err.println("This Username Doesn't Exists !!!");
        else if(password.equals(""))
            System.err.println("Please Enter A Password !!!");
        else if (!controller.isValidLogin(username, password))
            System.err.println("Invalid Password for This Username !!!");
        else {
            controller.login(username, password);
            this.menuMode = 1;
            System.out.println("Login Successfully !!");
            group.getChildren().removeAll(group.getChildren());
            graphicShowUserMenu();
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
    private String getCurrentUsername(){
        return controller.getCurrentPlayerName();
    }
    private String getCurrentMoney(){
        return String.valueOf(controller.getCurrentPlayer().getMoney());
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
        System.out.println("1. Single Player");
        System.out.println("2. Start Multi Player Between [Player1 Name] and [Player2 Name] Mode [Mode Name]");
    }
    //======================== CodidChoosellection Functions ====================//
    private void addCardToDeckGraphic(String str,Pane deckList,int cardID, String deckName,Text itemTextInCollection){
        Text txt = new Text(str.replace("+","-"));
        txt.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT , FontPosture.REGULAR, 13));
        txt.setFill(Color.rgb(255,255,255));
        double yCoord;
        if(deckList.getChildren().size() == 0 )
            yCoord = 5;
        else
            yCoord = deckList.getChildren().get(deckList.getChildren().size()-1).getLayoutY();
        txt.relocate(5 ,  yCoord );
        txt.setOnMouseEntered(mouseEvent -> {
            txt.setFill(Color.rgb(11,178,191));
        });
        txt.setOnMouseExited(mouseEvent -> {
            txt.setFill(Color.rgb(255,255,255));
        });
        txt.setOnMouseClicked(mouseEvent -> {
            removeCardFromDeck(cardID,deckName);
            deckList.getChildren().remove(txt);
            itemTextInCollection.setFill(Color.rgb(255,255,255));
        });
        deckList.getChildren().add(txt);
    }
    private void printCollectionItemsInDecks(String str , int yCoord,Pane cardsList,Pane deckList,int cardID,String deckName){
        boolean didChoose = controller.isCardInDeck(cardID,deckName);
        Text txt = new Text(str);
        txt.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT , FontPosture.REGULAR, 13));
        txt.setFill(Color.rgb(255,255,255));
        if(didChoose)
            txt.setFill(Color.rgb(192, 57, 43));
        txt.relocate(5 , yCoord);
        txt.setOnMouseClicked(mouseEvent -> {
            boolean valid = addCardToDeck(cardID,deckName);
            if(valid) {
                addCardToDeckGraphic(str, deckList, cardID, deckName, txt);
                txt.setFill(Color.rgb(192, 57, 43));
            }
        });
        txt.setOnMouseEntered(mouseEvent -> {
            if(txt.getFill().equals(Color.rgb(255,255,255)))
                txt.setFill(Color.rgb(11,178,191));
        });
        txt.setOnMouseExited(mouseEvent -> {
            if(txt.getFill().equals(Color.rgb(11,178,191)))
                txt.setFill(Color.rgb(255,255,255));
        });
        cardsList.getChildren().add(txt);
    }
    public void showCollection(Pane cardsList,Pane deckList,String deckName) {
        ArrayList<Card> cardsToShow = controller.cardsInCollection();
        int yCoord = 5;
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i) instanceof Unit && ((Unit) cardsToShow.get(i)).isHero()) {
                Card card = cardsToShow.get(i);
                String str = "+ Name : " + card.getName() + " - AP : " + ((Unit) card).getAttackPower() + " - HP : " + ((Unit) card).getHP()
                        + " - Class : " + ((Unit) card).getAttackType() + " - SP : " + ((Unit) card).getSpecialPower().getRecipe();
                printCollectionItemsInDecks(str,yCoord,cardsList,deckList,cardsToShow.get(i).getCardID(),deckName);
                yCoord += 13;
            }
        }
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i) instanceof Item) {
                Card card = cardsToShow.get(i);
                String str = "+ Name : " + card.getName() + " - Desc : " + ((Item) card).getDescription();
                printCollectionItemsInDecks(str,yCoord,cardsList,deckList,cardsToShow.get(i).getCardID(),deckName);
                yCoord += 13;
            }
        }
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (!(cardsToShow.get(i) instanceof Item) && !(cardsToShow.get(i) instanceof Unit &&
                    ((Unit) cardsToShow.get(i)).isHero())) {
                if (cardsToShow.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) cardsToShow.get(i);
                    String str = "+ Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Desc : " + " - Cost : "
                            + card.getPrice();
                    printCollectionItemsInDecks(str,yCoord,cardsList,deckList,cardsToShow.get(i).getCardID(),deckName);
                    yCoord += 13;
                }
                if (cardsToShow.get(i) instanceof Unit) {
                    Unit card = (Unit) cardsToShow.get(i);
                    String str = "+ Type : Minion - Name : " + card.getName()
                            + " - class : " + ((Unit) card).getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - SP : " +
                            card.getSpecialPower().getRecipe() + " - Cost : " + card.getPrice();
                    printCollectionItemsInDecks(str,yCoord,cardsList,deckList,cardsToShow.get(i).getCardID(),deckName);
                    yCoord += 13;
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
        if(keyword.matches(""))
            System.err.println("Please Enter Something in Input !!!");
        else if(controller.isDeckExists(keyword))
            System.err.println("a Deck with this name already exists !!!");
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

    public boolean addCardToDeck(int cardID, String keyword) {
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
            return true;
        }
        return false;
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
    private void showDeckItemGraphic(String str,int yCoord , Pane deckList,int cardID, String deckName,Pane cardList){
        Text txt = new Text(str);
        txt.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT , FontPosture.REGULAR, 13));
        txt.setFill(Color.rgb(255,255,255));
        txt.relocate(5 ,  yCoord );
        txt.setOnMouseEntered(mouseEvent -> {
            txt.setFill(Color.rgb(11,178,191));
        });
        txt.setOnMouseExited(mouseEvent -> {
            txt.setFill(Color.rgb(255,255,255));
        });
        txt.setOnMouseClicked(mouseEvent -> {
            removeCardFromDeck(cardID,deckName);
            deckList.getChildren().remove(txt);
            showCollection(cardList,deckList,deckName);
        });
        deckList.getChildren().add(txt);
    }
    public void showDeck(String keyword,Pane deckList,Pane cardList) {
        ArrayList<Card> cardsToShow = controller.getDeck(keyword).getCards();
        int yCoord = 5;
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i+1-1) instanceof Unit && ((Unit) cardsToShow.get(i)).isHero()) {
                Card card = cardsToShow.get(i);
                String str = "- Name : " + card.getName() + " - AP : " + ((Unit) card).getAttackPower() + " - HP : " + ((Unit) card).getHP()
                        + " - Class : " + ((Unit) card).getAttackType() + " - SP : " + ((Unit) card).getSpecialPower().getRecipe();
                showDeckItemGraphic(str,yCoord ,deckList,cardsToShow.get(i).getCardID(),keyword,cardList);
                yCoord += 13;
            }
        }
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (cardsToShow.get(i) instanceof Item) {
                Card card = cardsToShow.get(i);
                String str = "- Name : " + card.getName() + " - Desc : " + ((Item) card).getDescription();
                showDeckItemGraphic(str,yCoord ,deckList,cardsToShow.get(i).getCardID(),keyword,cardList);
                yCoord += 13;
            }
        }
        for (int i = 0; i < cardsToShow.size(); i++) {
            if (!(cardsToShow.get(i) instanceof Item) && !(cardsToShow.get(i) instanceof Unit &&
                    ((Unit) cardsToShow.get(i)).isHero())) {
                if (cardsToShow.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) cardsToShow.get(i);
                    String str = "- Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Desc : " + " - Cost : "
                            + card.getPrice();
                    showDeckItemGraphic(str,yCoord ,deckList,cardsToShow.get(i).getCardID(),keyword,cardList);
                    yCoord += 13;
                }
                if (cardsToShow.get(i) instanceof Unit) {
                    Unit card = (Unit) cardsToShow.get(i);
                    String str = "- Type : Minion - Name : " + card.getName()
                            + " - class : " + ((Unit) card).getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - SP : " +
                            card.getSpecialPower().getRecipe() + " - Cost : " + card.getPrice();
                    showDeckItemGraphic(str,yCoord ,deckList,cardsToShow.get(i).getCardID(),keyword,cardList);
                    yCoord += 13;
                }
            }
        }
    }

//    public void showOneDeck(String keyword) {
//        if (!controller.isDeckExists(keyword))
//            System.out.println("Deck with this name doesnt exist !!!");
//        else
//            showDeck(keyword);
//    }

//    public void showAllDecks() {
//        ArrayList<Deck> decks = controller.getPlayerDecks();
//        if ( controller.getPlayerMainDeck() == null )
//            System.out.println("Main Deck : Player doesnt Have Main Deck !!!");
//        else {
//            System.out.println("Main Deck : ");
//            showDeck(controller.getPlayerMainDeck().getName());
//        }
//        int cnt = 1;
//        for (int i = 0; i < decks.size(); i++) {
//            if(controller.getPlayerMainDeck() == null || !decks.get(i).getName().equals(controller.getPlayerMainDeck().getName())) {
//                System.out.println(cnt++ + " :");
//                showDeck(decks.get(i).getName());
//            }
//        }
//    }

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
    private void printCollectionItem(String str , int yCoord,Pane cardsList,int cardID,Text money){
        Text txt = new Text(str);
        txt.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT , FontPosture.REGULAR, 13));
        txt.setFill(Color.rgb(255,255,255));
        txt.relocate(5 , yCoord);
        txt.setOnMouseClicked(mouseEvent -> {
            sellCardWithID(cardID);
            cardsList.getChildren().remove(txt);
            money.setText(getCurrentMoney());
        });
        txt.setOnMouseEntered(mouseEvent -> {
            txt.setFill(Color.rgb(11,178,191));
        });
        txt.setOnMouseExited(mouseEvent -> {
            txt.setFill(Color.rgb(255,255,255));
        });
        cardsList.getChildren().add(txt);
    }
    public void showCollectionInShop(Pane cardsList,Text money) {
        ArrayList<Card> collection = controller.cardsInCollection();
        int yCoord = 5;
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i) instanceof Unit && ((Unit) collection.get(i)).isHero()) {
                Unit hero = (Unit) collection.get(i);
                 String str = "- Name : " + hero.getName() + " - AP : " + hero.getAttackPower() + " - HP : " +
                        hero.getHP() + " - Class : " + hero.getAttackType() + " - SP : " + hero.getSpecialPower().getRecipe() +
                        " - Cost : " + hero.getPrice();
                printCollectionItem(str,yCoord,cardsList,collection.get(i).getCardID(),money);
                yCoord += 13;
            }
        }
        for (int i = 0; i < collection.size(); i++) {
            if (collection.get(i) instanceof Item) {
                Item item = (Item) collection.get(i);
                String str = "- Name " + item.getName() + " - Desc : " +
                        item.getDescription() + " - Cost : " + item.getPrice();
                printCollectionItem(str,yCoord,cardsList,collection.get(i).getCardID(),money);
                yCoord += 13;
            }
        }
        for (int i = 0; i < collection.size(); i++) {
            if (!(collection.get(i) instanceof Item) && !(collection.get(i) instanceof Unit &&
                    ((Unit) collection.get(i)).isHero())) {
                if (collection.get(i) instanceof SpellCard) {
                    SpellCard card = (SpellCard) collection.get(i);
                    String str = "- Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Desc : " + " - Cost : "
                            + card.getPrice();
                    printCollectionItem(str,yCoord,cardsList,collection.get(i).getCardID(),money);
                    yCoord += 13;
                }
                if (collection.get(i) instanceof Unit) {
                    Unit card = (Unit) collection.get(i);
                    String str = "- Type : Minion - Name : " + card.getName()
                            + " - class : " + card.getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - SP : " +
                            card.getSpecialPower().getRecipe() + " - Cost : " + card.getPrice();
                    printCollectionItem(str,yCoord,cardsList,collection.get(i).getCardID(),money);
                    yCoord += 13;
                }

            }
        }
    }
    private void printShopItem(String str , int yCoord,Pane cardsList,String name,Text money){
        Text txt = new Text(str);
        txt.setFont(Font.font("verdana", FontWeight.EXTRA_LIGHT , FontPosture.REGULAR, 13));
        txt.setFill(Color.rgb(255,255,255));
        txt.relocate(5 , yCoord);
        txt.setOnMouseClicked(mouseEvent -> {
            buyCard(name);
            cardsList.getChildren().remove(txt);
            money.setText(getCurrentMoney());
        });
        txt.setOnMouseEntered(mouseEvent -> {
            txt.setFill(Color.rgb(11,178,191));
        });
        txt.setOnMouseExited(mouseEvent -> {
            txt.setFill(Color.rgb(255,255,255));
        });
        cardsList.getChildren().add(txt);
    }
    public void showCardsInShop(Pane cardsList,Text money,String search) {
        ArrayList<Card> cardsInShop = controller.getCardsInShop();
        if(cardsInShop == null)
            System.out.println("Shop is empty");
        int yCoord = 5;
        for (int i = 0; i < cardsInShop.size(); i++) {
            boolean matchSearch = search.matches("") || cardsInShop.get(i).getName().toLowerCase().matches(".*"+search.toLowerCase()+".*");
            if (cardsInShop.get(i) instanceof Unit && ((Unit) cardsInShop.get(i)).isHero() && matchSearch) {
                Unit hero = (Unit) cardsInShop.get(i);
                String str = "+ Name : " + hero.getName() + " - AP : " + hero.getAttackPower()
                        + " - HP : " + hero.getHP() + " - SP : " + hero.getSpecialPower().getRecipe() +
                        " - Cost : " + hero.getPrice();
                printShopItem(str,yCoord,cardsList,hero.getName(),money);
                yCoord += 13;
            }
        }
        for (int i = 0; i < cardsInShop.size(); i++) {
            boolean matchSearch = search.matches("") || cardsInShop.get(i).getName().toLowerCase().matches(".*"+search.toLowerCase()+".*");
            if (cardsInShop.get(i) instanceof Item && matchSearch) {
                Item item = (Item) cardsInShop.get(i);
                String str = "+ Name : " + item.getName() + " - Desc : " +
                        item.getDescription() + " - Cost : " + item.getPrice();
                printShopItem(str,yCoord,cardsList,item.getName(),money);
                yCoord += 13;
            }
        }
        for (int i = 0; i < cardsInShop.size(); i++) {
            if (!(cardsInShop.get(i) instanceof Item) && !(cardsInShop.get(i) instanceof Unit &&
                    ((Unit) cardsInShop.get(i)).isHero())) {
                boolean matchSearch = search.matches("") || cardsInShop.get(i).getName().toLowerCase().matches(".*"+search.toLowerCase()+".*");
                if (cardsInShop.get(i) instanceof SpellCard && matchSearch) {
                    SpellCard card = (SpellCard) cardsInShop.get(i);
                    String str = "+ Type : Spell - Name : " + card.getName() +
                            " - MP : " + card.getManaCost() + " - Desc : " + " - Cost : "
                            + card.getPrice();
                    printShopItem(str,yCoord,cardsList,card.getName(),money);
                    yCoord += 13;
                }
                if (cardsInShop.get(i) instanceof Unit && matchSearch) {
                    Unit card = (Unit) cardsInShop.get(i);
                    String str = "+ Type : Minion - Name : " + card.getName()
                            + " - class : " + ((Unit) card).getAttackType() + " - AP : " + card.getAttackPower() + " - HP : "
                            + card.getHP() + " - MP : " + card.getManaCost() + " - SP : " +
                            card.getSpecialPower().getRecipe() + " - Cost : " + card.getPrice();
                    printShopItem(str,yCoord,cardsList,card.getName(),money);
                    yCoord += 13;

                }

            }
        }
    }

    public void sellCardWithID(int cardID) {
        ArrayList<Card> cardsInCollection = controller.cardsInCollection();
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


    public boolean moveCurrentCard(int x, int y) {
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
                return true;
        }
        endGame(); // Should be Checked
        return false;
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
        else if(!controller.isValidMainDeck(controller.getCurrentPlayerName())) {
            System.out.println("Your Main Deck is Invalid !!!");
            System.out.println(controller.getCurrentPlayerName());
        }
        else {
            isAIPlayerActive = true;
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
            isAIPlayerActive = false;
            controller.startMultiPlayer(name1,name2,gameMode.toLowerCase());
            menuMode = 5;
        }
    }
    public void playerAIMoves(){
        if(controller.canAIHeroMove()){
            int[] coord = controller.coordAIHeroMove();
            int cardIDAIHero = controller.getAIHeroCardID();
            controller.selectCard(cardIDAIHero);
            System.out.println("AI Command : Select card " + cardIDAIHero);
            controller.moveCurrentCard(coord[0],coord[1]);
            System.out.println("AI Command : Move to (" + coord[0] + "," + coord[1] + ")");
            controller.endTurn();
            System.out.println("AI Command : End Turn");
            endGame();
        }
        else if(controller.canAIHeroAttack()){
            int[] coord = controller.coordAIHeroAttack();
            int cardIDAIHero = controller.getAIHeroCardID();
            controller.selectCard(cardIDAIHero);
            System.out.println("AI Command : Select card " + cardIDAIHero);
            controller.attack(controller.getCardIDByCoord(coord[0],coord[1]));
            System.out.println("AI Command : Attack " + controller.getCardIDByCoord(coord[0],coord[1]));
            controller.endTurn();
            System.out.println("AI Command : End Turn");
            endGame();
        }
        else{
            controller.endTurn();
            System.out.println("AI Command : End Turn");
            endGame();
        }
    }
    public void resignGame(){
        controller.resignGame();
        menuMode = 1;
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
    public boolean insert(int cardID, int x , int y){ ;
        String result = controller.insert(cardID, x, y);
        System.out.println(result);
        endGame(); // should be Checked
        return !( result.matches(".*Invalid.*") || result.matches(".*enough.*"));
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
//            if ( !str.matches("AI\\w+") )
//                controller.getCurrentPlayer().setStoryModeLevel( (controller.getCurrentPlayer().getStoryModeLevel()+1)%3 ) ;
            menuMode = 1;
        }

    }

    public void exit() {

    }

    public void showMenu() {

    }

}