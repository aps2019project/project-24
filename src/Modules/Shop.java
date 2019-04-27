package Modules;

import java.util.ArrayList;

public class Shop{
    private ArrayList<Card> cards;
    private String input;
    private String output;
    private Player player;
    private Card card;
    private ArrayList<String> cardsString;

    public void sell(Player seller, Card card){
    }
    public void buy(Player player, Card card){
    }

//    public Card makeCardFromInput(String input){
//
//    }
//
//    public Player makePlayerFromInput(String input){
//
//    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public ArrayList<String> getCardsString() {
        return cardsString;
    }

    public void setCardsString(ArrayList<String> cardsString) {
        this.cardsString = cardsString;
    }

    public void showCollenctions(Player player){

    }
}