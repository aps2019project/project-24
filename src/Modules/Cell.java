package Modules;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cell {
    private Card card;
    private ArrayList<Item> items;
    private ArrayList<Spell> buffs;

    public Cell(){
        this.card = null;
        this.items = new ArrayList<>();
        this.buffs = new ArrayList<>();
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Card getCard() {
        return card;
    }
    public void setCard(Card card) {
        this.card = card;
    }

    public ArrayList<Spell> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Spell> buffs) {
        this.buffs = buffs;
    }
}
