package com.example.tresenratlla;

//Una clase Statistics, donde indicaremos el nombre del jugador, las ganadas, perdidas y empatadas
public class Statistics {
    private String player;
    private int wins = 0;
    private int loses = 0;
    private int ties = 0;

    //Un constructor vacio
    public Statistics() {
    }

    //Un constructor con todos los parametros
    public Statistics(String player, int wins, int loses, int ties) {
        this.player = player;
        this.wins = wins;
        this.loses = loses;
        this.ties = ties;
    }

    //Getters y Setters de todos los atributos
    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLoses() {
        return loses;
    }

    public void setLoses(int loses) {
        this.loses = loses;
    }

    public int getTies() {
        return ties;
    }

    public void setTies(int ties) {
        this.ties = ties;
    }

    public void playerWins(){
        this.wins++;
    }

    public void playerLoses(){
        this.loses++;
    }

    public void playerTies(){
        this.ties++;
    }
}
