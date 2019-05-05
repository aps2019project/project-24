package Modules;

import java.util.ArrayList;

public class Unit extends Card {
    private int attackPower;
    private int HP;
    private Spell specialPower;
    private String attackType;
    private int range;
    private int manaCost;
    private boolean isHero;
    private boolean hasFlag;
    private Target specialPowerTarget;
    private String specialPowerCastTime;//onAttack, onRespawn, passive, onDeath, onDefend, castAble,
    private int specialPowerManaCost;
    private String description;
    private boolean canCombo;
    private boolean hasBeenMovedThisRound;
    private ArrayList<Spell> buffs;

    {
        buffs = new ArrayList<>();
    }

    public void setIsHero(boolean b){ this.isHero = b; }
    public boolean getIsHero(){ return this.isHero; }

    public int getSpecialPowerManaCost() {
        return specialPowerManaCost;
    }

    public void setSpecialPowerManaCost(int specialPowerManaCost) {
        this.specialPowerManaCost = specialPowerManaCost;
    }

    public ArrayList<Spell> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Spell> buffs) {
        this.buffs = buffs;
    }

    public boolean isHasFlag() {
        return hasFlag;
    }

    public boolean isCanCombo() {
        return canCombo;
    }

    public boolean getCanCombo() {
        return this.canCombo;
    }

    public void setCanCombo(boolean canDoCombo) {
        this.canCombo = canDoCombo;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public boolean isHasBeenMovedThisRound() {
        return hasBeenMovedThisRound;
    }

    public void setHasBeenMovedThisRound(boolean hasBeenMovedThisRound) {
        this.hasBeenMovedThisRound = hasBeenMovedThisRound;
    }

    public boolean getHasFlag() {
        return this.hasFlag;
    }

    public void setHasFlag(boolean unitHasFlag) {
        this.hasFlag = unitHasFlag;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setHero(boolean hero) {
        isHero = hero;
    }

    public String getSpecialPowerCastTime() {
        return specialPowerCastTime;
    }

    public void setSpecialPowerCastTime(String specialPowerCastTime) {
        this.specialPowerCastTime = specialPowerCastTime;
    }

    public Target getSpecialPowerTarget() {
        return specialPowerTarget;
    }

    public void setSpecialPowerTarget(Target specialPowerTarget) {
        this.specialPowerTarget = specialPowerTarget;
    }

    public int getAttackPower() {
        return attackPower;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public int getHP() {
        return HP;
    }

    public void setHP(int HP) {
        this.HP = HP;
    }

    public Spell getSpecialPower() {
        return specialPower;
    }

    public void setSpecialPower(Spell specialPower) {
        this.specialPower = specialPower;
    }

    public String getAttackType() {
        return attackType;
    }

    public void setAttackType(String attackType) {
        this.attackType = attackType;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getManaCost() {
        return manaCost;
    }

    public void setManaCost(int manaCost) {
        this.manaCost = manaCost;
    }
}
