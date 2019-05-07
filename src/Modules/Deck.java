package Modules;

import java.util.ArrayList;

public class Deck {
    private String name;
    private ArrayList<Card> cards;
    private ArrayList<Unit> units;
    private ArrayList<SpellCard> spells;
    private ArrayList<Item> items;


    public Deck(String name){
        this.cards = new ArrayList<>();
        this.name = name;
        this.units = new ArrayList<>();
        this.spells = new ArrayList<>();
        this.items = new ArrayList<>();
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }

    public void setUnits(ArrayList<Unit> units) {
        this.units = units;
    }

    public ArrayList<SpellCard> getSpells() {
        return spells;
    }

    public void setSpells(ArrayList<SpellCard> spells) {
        this.spells = spells;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
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
