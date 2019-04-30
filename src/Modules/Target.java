package Modules;

import java.util.ArrayList;

public class Target {
    private ArrayList <Card> targets;
    private ArrayList <Integer> x;
    private ArrayList <Integer> y;
    private ArrayList <Cell> cells;
    private String targetType;

    public ArrayList<Cell> getCells() {
        return cells;
    }

    public void setCells(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public ArrayList<Card> getTargets() {
        return targets;
    }

    public void setTargets(ArrayList<Card> targets) {
        this.targets = targets;
    }

    public ArrayList<Integer> getX() {
        return x;
    }

    public void setX(ArrayList<Integer> x) {
        this.x = x;
    }

    public ArrayList<Integer> getY() {
        return y;
    }

    public void setY(ArrayList<Integer> y) {
        this.y = y;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public void setTheTarget() {
        //az ruye string khoonde mishe dastoor va cartaye hadaf daqiqan moshakhas mishan!!
    }
    //tedad minionYaHeroYaHarchi khodiYaDoshman
}