package com.example.projektzaliczeniowy;

public class RankingModel {
    private String nick;
    private int id, poziom;

    public RankingModel(int id, String nick, int poziom) {
        this.id = id;
        this.nick = nick;
        this.poziom = poziom;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int getPoziom() {
        return poziom;
    }

    public void setPoziom(int poziom) {
        this.poziom = poziom;
    }
}
