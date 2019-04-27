package Modules;

import java.util.ArrayList;

public class Target {
    private ArrayList <Card> targets;
    private ArrayList <Integer> x;
    private ArrayList <Integer> y;

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
}