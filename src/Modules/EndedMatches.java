package Modules;

import java.util.ArrayList;

public class EndedMatches {
    ArrayList<EndedMatches> endedMatches;
    private Player player1;
    private Player player2;
    private Player winner;
    private int time;

    public EndedMatches(Player p1, Player p2, Player winner, int time){
        this.player1 = p1;
        this.player2 = p2;
        this.winner = winner;
        this.time = time;
        p1.getListOfMatches().add(this);
        p2.getListOfMatches().add(this);
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
