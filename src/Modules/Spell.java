package Modules;

public class Spell {
    private String name;
    private boolean stun;
    private boolean disarm;
    private boolean poisone;
    private int weakness;
    private boolean dispel;
    private boolean fire;
    private int attackChange;
    private int HPChanger;
    private boolean holyBuff;
    private boolean comboCheck;
    private boolean canDisarm;
    private boolean canStun;
    private boolean canPoisoned;
    private boolean canBeBuffed;
    private boolean deBuff;
    private int attack;
    private int changeMana;
    private int rounds;
    private String recipe;

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isCanPoisoned() {
        return canPoisoned;
    }

    public void setCanPoisoned(boolean canPoisoned) {
        this.canPoisoned = canPoisoned;
    }

    public int getChangeMana() {
        return changeMana;
    }

    public void setChangeMana(int changeMana) {
        this.changeMana = changeMana;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeakness() {
        return weakness;
    }

    public void setWeakness(int weakness) {
        this.weakness = weakness;
    }

    public boolean isDeBuff() {
        return deBuff;
    }

    public void setDeBuff(boolean deBuff) {
        this.deBuff = deBuff;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public boolean isDispel() {
        return dispel;
    }

    public void setDispel(boolean dispel) {
        this.dispel = dispel;
    }

    public int getAttackChange() {
        return attackChange;
    }

    public void setAttackChange(int attackChange) {
        this.attackChange = attackChange;
    }

    public int getHPChanger() {
        return HPChanger;
    }

    public void setHPChanger(int HPChanger) {
        this.HPChanger = HPChanger;
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

    public boolean isCanPoisened() {
        return canPoisoned;
    }

    public void setCanPoisened(boolean canPoisened) {
        this.canPoisoned = canPoisened;
    }

    public boolean isCanBeBuffed() {
        return canBeBuffed;
    }

    public void setCanBeBuffed(boolean canBeBuffed) {
        this.canBeBuffed = canBeBuffed;
    }

    public boolean isStun() {
        return stun;
    }

    public void setStun(boolean stun) {
        this.stun = stun;
    }

    public boolean isDisarm() {
        return disarm;
    }

    public void setDisarm(boolean disarm) {
        this.disarm = disarm;
    }

    public boolean isPoisone() {
        return poisone;
    }

    public void setPoisone(boolean poisone) {
        this.poisone = poisone;
    }

    public boolean isFire() {
        return fire;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public boolean isHolyBuff() {
        return holyBuff;
    }

    public void setHolyBuff(boolean holyBuff) {
        this.holyBuff = holyBuff;
    }
}
