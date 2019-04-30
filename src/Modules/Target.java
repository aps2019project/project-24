package Modules;

import java.util.ArrayList;

public class Target {

    private String number;// 1, ALL, 3*3, 4*4, row, column
    private String targetGroup;//cell, enemy, allie
    private String targetType;//minion, hero


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