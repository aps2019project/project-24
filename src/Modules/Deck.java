package Modules;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> cards;

    public Deck(String name){
        this.cards = new ArrayList<>();
        this.name = name;
    }

    public Deck(){
        this.cards = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCards(ArrayList<Card> cards) {
        this.cards = cards;
    }
}