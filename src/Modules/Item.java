package Modules;

public class Item extends Card {
    private boolean isFlag;
    private boolean isCollectAble;
    private boolean isUseAble;
    private Spell spell;
    private Target target;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFlag() {
        return isFlag;
    }

    public void setFlag(boolean flag) {
        isFlag = flag;
    }

    public boolean isCollectAble() {
        return isCollectAble;
    }

    public void setCollectAble(boolean collectAble) {
        isCollectAble = collectAble;
    }

    public boolean isUseAble() {
        return isUseAble;
    }

    public void setUseAble(boolean useAble) {
        isUseAble = useAble;
    }

    public Spell getSpell() {
        return spell;
    }

    public void setSpell(Spell spell) {
        this.spell = spell;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }
}
