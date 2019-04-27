package Modules;

public class Unit extends Card{
    private boolean isHero;
    private int attackPower;
    private int HP;
    private Spell specialPower;
    private String attackType;
    private String race;//irani toorani ...
    private int range;
    private int manaCost;
    private Target SpecialPwerTarget;
    private String SpecialPowerCastTime;

    public String getSpecialPowerCastTime() {
        return SpecialPowerCastTime;
    }

    public void setSpecialPowerCastTime(String specialPowerCastTime) {
        SpecialPowerCastTime = specialPowerCastTime;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Target getSpecialPwerTarget() {
        return SpecialPwerTarget;
    }

    public void setSpecialPwerTarget(Target specialPwerTarget) {
        SpecialPwerTarget = specialPwerTarget;
    }

    public boolean isHero() {
        return isHero;
    }

    public void setHero(boolean hero) {
        isHero = hero;
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
