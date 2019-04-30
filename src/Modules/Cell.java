package Modules;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Cell {
    private Card card;
    private Item item;
    private ArrayList<Spell> buffs;

    public Card getCard() {
        return card;
    }
    public Item getItem(){ return item; }
    public void setItem(Item item){ this.item = item; }
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
