package Modules;

import java.io.FileWriter;
import java.util.ArrayList;

import cardBuilder.CardBuilder;
import com.google.gson.*;
import com.google.gson.stream.JsonReader;

import java.nio.file.*;
import java.io.IOException;
import java.io.FileReader;

public class Shop{
    private ArrayList<Card> cards;
    private Player player;

    public Shop(){
        cards = new ArrayList<>();
        Spell spell = new Spell();
        //spell.setStun(true);
        spell.setCanBeStun(true);
        //////////////////// HERO 1 ///////////////////
//        Unit hero1 = new Unit();
//        hero1.getBuffs().add(spell);
//        hero1.setCardID(512312);
//        hero1.setName("HeroNumberOne");
//        hero1.setPrice(5500);
//        Unit uHero1 = hero1;
//        uHero1.setHP(100);
//        uHero1.setAttackPower(10);
//        uHero1.setAttackType("range");
//        Spell spellHero1 = new Spell();
//        spellHero1.setAttackChange(2);
//        uHero1.setSpecialPower(spellHero1);
//        uHero1.setRange(2);
//        uHero1.setManaCost(0);
//        uHero1.setIsHero(true);
//        uHero1.setHasFlag(false);
        Target t1 = new Target();
        t1.setNumber("1");
        t1.setTargetGroup("ally");
       t1.setTargetType("minion");
//        uHero1.setSpecialPowerTarget(t1);
//        uHero1.setSpecialPowerCastTime("castAble");
//        uHero1.setSpecialPowerManaCost(3);
//        uHero1.setDescription("Hero1 : This hero is like Changiz !");
//        uHero1.setCanCombo(true);
//        uHero1.setHasBeenMovedThisRound(false);
        //uHero1.setBuffs(new ArrayList<>());
        //////////////////// HERO 2 ///////////////////
        Unit hero2 = new Unit();
        hero2.setCardID(41232);
        hero2.setName("HeroNumberTwo");
        hero2.setPrice(2500);
        Unit uHero2 = hero2;
        uHero2.setHP(100);
        uHero2.setAttackPower(6);
        uHero2.setAttackType("melee");
        Spell spellHero2 = new Spell();
        spellHero2.setWeakness(3);
        uHero2.setSpecialPower(spellHero2);
        uHero2.setRange(1);
        uHero2.setBuffs(new ArrayList<>());
        uHero2.setManaCost(0);
        uHero2.setIsHero(true);
        uHero2.setHasFlag(false);
        Target t2 = new Target();
        t2.setNumber("2*2");
        t2.setTargetGroup("enemy");
        t2.setTargetType("both");
        uHero2.setSpecialPowerTarget(t2);
        uHero2.setSpecialPowerCastTime("castAble");
        uHero2.setSpecialPowerManaCost(4);
        uHero2.setDescription("Hero2 : This hero Fucks Enemy's Units !");
        uHero2.setCanCombo(true);
        uHero2.setHasBeenMovedThisRound(false);
        uHero2.setBuffs(new ArrayList<>());
        //////////////////// HERO 3 ///////////////////
        Unit hero3 = new Unit();
        hero3.setCardID(3125123);
        hero3.setName("HeroNumberThree");
        hero3.setPrice(10000);
        Unit uHero3 = hero3;
        uHero3.setHP(100);
        uHero3.setAttackPower(12);
        uHero3.setAttackType("hybrid");
        Spell spellHero3 = new Spell();
        spellHero3.setDeBuff(true);
        uHero3.setSpecialPower(spellHero3);
        uHero3.setRange(2);
        uHero3.setBuffs(new ArrayList<>());
        uHero3.setManaCost(0);
        uHero3.setIsHero(true);
        uHero3.setHasFlag(false);
        Target t3 = new Target();
        t3.setNumber("himself");
        t3.setTargetGroup("ally");
        t3.setTargetType("hero");
        uHero3.setSpecialPowerTarget(t3);
        uHero3.setSpecialPowerCastTime("onRespawn");
        uHero3.setSpecialPowerManaCost(0);
        uHero3.setDescription("Hero3 : This hero Doesnt give a shit on buffs !");
        uHero3.setCanCombo(true);
        uHero3.setHasBeenMovedThisRound(false);
        uHero3.setBuffs(new ArrayList<>());
        //////////////////// Minion 1 ///////////////////
        Unit minion1 = new Unit();
        minion1.setCardID(912831);
        minion1.setName("MinionNumberOne");
        minion1.setPrice(1500);
        Unit uMinion1 = minion1;
        uMinion1.setHP(100);
        uMinion1.setAttackPower(3);
        uMinion1.setAttackType("range");
        Spell spellMinion1 = new Spell();
        spellMinion1.setAttackChange(2);
        uMinion1.setSpecialPower(spellMinion1);
        uMinion1.setRange(2);
        uMinion1.setBuffs(new ArrayList<>());
        uMinion1.setManaCost(2);
        uMinion1.setIsHero(false);
        uMinion1.setHasFlag(false);
        t1 = new Target();
        t1.setNumber("1");
        t1.setTargetGroup("ally");
        t1.setTargetType("minion");
        uMinion1.setSpecialPowerTarget(t1);
        uMinion1.setSpecialPowerCastTime("castAble");
        uMinion1.setSpecialPowerManaCost(1);
        uMinion1.setDescription("Minion1 : This Minion is Mini Changiz !");
        uMinion1.setCanCombo(true);
        uMinion1.setHasBeenMovedThisRound(false);
        uMinion1.setBuffs(new ArrayList<>());
        //////////////////// Minion 2 ///////////////////
        Unit minion2 = new Unit();
        minion2.setCardID(5123612);
        minion2.setName("MinionNumberTwo");
        minion2.setPrice(500);
        Unit uMinion2 = minion2;
        uMinion2.setHP(100);
        uMinion2.setAttackPower(2);
        uMinion2.setAttackType("melee");
        Spell spellMinion2 = new Spell();
        spellMinion2.setWeakness(3);
        uMinion2.setSpecialPower(spellMinion2);
        uMinion2.setRange(1);
        uMinion2.setBuffs(new ArrayList<>());
        uMinion2.setManaCost(2);
        uMinion2.setIsHero(false);
        uMinion2.setHasFlag(false);
        t2 = new Target();
        t2.setNumber("2*2");
        t2.setTargetGroup("enemy");
        t2.setTargetType("both");
        uMinion2.setSpecialPowerTarget(t2);
        uMinion2.setSpecialPowerCastTime("castAble");
        uMinion2.setSpecialPowerManaCost(2);
        uMinion2.setDescription("Minion2 : This Minion Almost Fucks Enemy's Units !");
        uMinion2.setCanCombo(false);
        uMinion2.setHasBeenMovedThisRound(false);
        uMinion2.setBuffs(new ArrayList<>());
        //////////////////// Minion 3 ///////////////////
        Unit minion3 = new Unit();
        minion3.setCardID(123486);
        minion3.setName("MinionNumberThree");
        minion3.setPrice(6000);
        Unit uMinion3 = minion3;
        uMinion3.setHP(100);
        uMinion3.setAttackPower(9);
        uMinion3.setAttackType("hybrid");
        Spell spellMinion3 = new Spell();
        spellMinion3.setDeBuff(true);
        uMinion3.setSpecialPower(spellMinion3);
        uMinion3.setRange(2);
        uMinion3.setBuffs(new ArrayList<>());
        uMinion3.setManaCost(3);
        uMinion3.setIsHero(false);
        uMinion3.setHasFlag(false);
        t3 = new Target();
        t3.setNumber("himself");
        t3.setTargetGroup("ally");
        t3.setTargetType("hero");
        uMinion3.setSpecialPowerTarget(t3);
        uMinion3.setSpecialPowerCastTime("onRespawn");
        uMinion3.setSpecialPowerManaCost(4);
        uMinion3.setDescription("Minion3 : This Minion Doesnt give a shit on buffs !");
        uMinion3.setCanCombo(true);
        uMinion3.setHasBeenMovedThisRound(false);
        uMinion3.setBuffs(new ArrayList<>());

        cards.add(hero2);
        cards.add(hero3);
        cards.add(minion1);
        cards.add(minion2);
        cards.add(minion3);


        // GSON FUCKING MAKER !!!!!!!!!!!
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        String json = gson.toJson(hero1);
//        FileWriter fileWriter = null;
//        try {
//            fileWriter = new FileWriter("hero1.json");
//            fileWriter.write(json.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            try {
//                if (fileWriter != null) {
//                    fileWriter.flush();
//                    fileWriter.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        // GSON FUCKING READER !!!!!!!!!!!!!!!!!!!!!!
        Unit hero1 = null;

        hero1 = CardBuilder.loadAUnitFromJsonFile("hero1");
        cards.add(hero1);
        // System.out.println(json.toString());
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public void setCards(ArrayList<Card> cards){
        this.cards = cards;
    }
    public Player getPlayer(){
        return player;
    }
    public void setPlayer(Player player){
        this.player = player;
    }
    public void sellWithID(Player seller, int cardID){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getCardID() == cardID){
                cards.add(seller.getCollection().get(i));
                seller.removeCard(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                break;
            }
        }
    }

    public void sellWithName(Player seller, String name){
        for(int i = 0; i < seller.getCollection().size(); i++){
            if(seller.getCollection().get(i).getName().equals(name)){
                cards.add(seller.getCollection().get(i));
                seller.increaseMoney(seller.getCollection().get(i).getPrice());
                seller.removeCard(seller.getCollection().get(i));
                break;
            }
        }
    }
    public void buy(Player buyer, String name){
        for(int i = 0; i < this.cards.size(); i++){
            if(this.cards.get(i).getName().equals(name)){
                buyer.addCard(this.cards.get(i));
                buyer.decreaseMoney(this.cards.get(i).getPrice());
                cards.remove(cards.get(i));
                break;
            }
        }
    }


}