package Modules;

import java.util.ArrayList;

public class Target {

    private String number;// 1, ALL, 3*3, 2*2, row, column, himself, 8around, hisrow, hiscolumn
    private String targetGroup;//cell, enemy, ally
    private String targetType;//minion, hero, both


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }
}