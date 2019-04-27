package Modules;

import java.util.ArrayList;

public class Spell {
    private String name;
    private int numberOfStunedrounds;
    private int numberOfDisarmedRounds;
    private int numberOfPoisonedRounds;
    private int weakness;
    private boolean dispel;
    private int numberOfFiredRounds;
    private int attackChange;
    private int HPchanger;
    private int numberOfHolyBuff;
    private boolean comboCheck;
    private boolean canDisarm;
    private boolean canStun;
    private boolean canPoisenned;
    private boolean canBeBuffed;
    private boolean respawnSnake;
    private int heroAttack;
    private String recipe;

    public int getHeroAttack() {
        return heroAttack;
    }

    public void setHeroAttack(int heroAttack) {
        this.heroAttack = heroAttack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfStunedrounds() {
        return numberOfStunedrounds;
    }

    public void setNumberOfStunedrounds(int numberOfStunedrounds) {
        this.numberOfStunedrounds = numberOfStunedrounds;
    }

    public int getNumberOfDisarmedRounds() {
        return numberOfDisarmedRounds;
    }

    public void setNumberOfDisarmedRounds(int numberOfDisarmedRounds) {
        this.numberOfDisarmedRounds = numberOfDisarmedRounds;
    }

    public int getNumberOfPoisonedRounds() {
        return numberOfPoisonedRounds;
    }

    public void setNumberOfPoisonedRounds(int numberOfPoisonedRounds) {
        this.numberOfPoisonedRounds = numberOfPoisonedRounds;
    }

    public int getWeakness() {
        return weakness;
    }

    public void setWeakness(int weakness) {
        this.weakness = weakness;
    }

    public boolean isDispel() {
        return dispel;
    }

    public void setDispel(boolean dispel) {
        this.dispel = dispel;
    }

    public int getNumberOfFiredRounds() {
        return numberOfFiredRounds;
    }

    public void setNumberOfFiredRounds(int numberOfFiredRounds) {
        this.numberOfFiredRounds = numberOfFiredRounds;
    }

    public int getAttackChange() {
        return attackChange;
    }

    public void setAttackChange(int attackChange) {
        this.attackChange = attackChange;
    }

    public int getHPchanger() {
        return HPchanger;
    }

    public void setHPchanger(int HPchanger) {
        this.HPchanger = HPchanger;
    }

    public int getNumberOfHolyBuff() {
        return numberOfHolyBuff;
    }

    public void setNumberOfHolyBuff(int numberOfHolyBuff) {
        this.numberOfHolyBuff = numberOfHolyBuff;
    }

    public boolean isComboCheck() {
        return comboCheck;
    }

    public void setComboCheck(boolean comboCheck) {
        this.comboCheck = comboCheck;
    }

    public boolean isCanDisarm() {
        return canDisarm;
    }

    public void setCanDisarm(boolean canDisarm) {
        this.canDisarm = canDisarm;
    }

    public boolean isCanStun() {
        return canStun;
    }

    public void setCanStun(boolean canStun) {
        this.canStun = canStun;
    }

    public boolean isCanPoisenned() {
        return canPoisenned;
    }

    public void setCanPoisenned(boolean canPoisenned) {
        this.canPoisenned = canPoisenned;
    }

    public boolean isCanBeBuffed() {
        return canBeBuffed;
    }

    public void setCanBeBuffed(boolean canBeBuffed) {
        this.canBeBuffed = canBeBuffed;
    }

    public boolean isRespawnSnake() {
        return respawnSnake;
    }

    public void setRespawnSnake(boolean respawnSnake) {
        this.respawnSnake = respawnSnake;
    }
}
