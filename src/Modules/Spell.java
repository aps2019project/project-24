package Modules;

public class Spell {
    private String name;
    private boolean stun;
    private boolean disarm;
    private boolean poison;
    private int weakness; // 1 attackPower , -1 hp
    private boolean fire;
    private int attackChange;
    private int HPChanger;
    private boolean holyBuff;
    private boolean canBeDisarm;
    private boolean canBeStun;
    private boolean canBePoisoned;
    private boolean canBeBuffed;
    private boolean deBuff;
    private int attack;
    private int changeMana;
    private int rounds;
    private String recipe;
    private boolean positive;

    public Spell(){

    }

    public Spell(Spell spell){
        this.attack = spell.attack;
        this.attackChange = spell.attackChange;
        this.canBeBuffed = spell.canBeBuffed;
        this.canBeDisarm = spell.canBeDisarm;
        this.canBePoisoned = spell.canBePoisoned;
        this.canBeStun = spell.canBeStun;
        this.changeMana = spell.changeMana;
        this.deBuff = spell.deBuff;
        this.disarm = spell.disarm;
        this.fire = spell.fire;
        this.holyBuff = spell.holyBuff;
        this.HPChanger = spell.HPChanger;
        this.name = spell.name;
        this.poison = spell.poison;
        this.rounds = spell.rounds;
        this.stun = spell.stun;
        this.weakness = spell.weakness;
        this.poison = spell.positive;
    }

    public Spell(String s){
        if ( s.equals("default")){
            this.stun = false;
                    this.disarm = false;
                    this.poison = false;
                    this.weakness = 0;
                    this.fire =  false;
                    this.attackChange = 0;
                    this.HPChanger = 0;
                    this.holyBuff = false;
                    this.canBeDisarm = true;
                    this.canBeStun = true;
                    this.canBePoisoned = true;
                    this.canBeBuffed = true;
                    this.deBuff =  false;
                    this.attack = 0;
                    this.changeMana = 0;
                    this.rounds = 0;
                    this.positive = false;
        }
    }

    public boolean isPositive() {
        return positive;
    }

    public void setPositive(boolean positive) {
        this.positive = positive;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int rounds) {
        this.rounds = rounds;
    }

    public boolean isCanBePoisoned() {
        return canBePoisoned;
    }

    public void setCanBePoisoned(boolean canBePoisoned) {
        this.canBePoisoned = canBePoisoned;
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

    public boolean isCanBeDisarm() {
        return canBeDisarm;
    }

    public void setCanBeDisarm(boolean canBeDisarm) {
        this.canBeDisarm = canBeDisarm;
    }

    public boolean isCanBeStun() {
        return canBeStun;
    }

    public void setCanBeStun(boolean canBeStun) {
        this.canBeStun = canBeStun;
    }

    public boolean isCanPoisened() {
        return canBePoisoned;
    }

    public void setCanPoisened(boolean canPoisened) {
        this.canBePoisoned = canPoisened;
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

    public boolean isPoison() {
        return poison;
    }

    public void setPoison(boolean poison) {
        this.poison = poison;
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
