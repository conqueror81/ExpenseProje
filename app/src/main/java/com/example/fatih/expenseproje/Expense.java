package com.example.fatih.expenseproje;


public class Expense {
    private int _id;
    private int _tip;
    private String _not;
    private int _miktar;
    private int _gun,_ay,_yil;
    private int fark;

    public int getFark() {
        return fark;
    }

    public void setFark(int fark) {
        this.fark = fark;
    }

    public Expense() {
    }

    public Expense(int _id) {
        this._id = _id;
    }

    public Expense(int tip, String not, int miktar, int ay) {
        this._tip = tip;
        this._not = not;
        this._miktar = miktar;
        this._ay = ay;
    }

    public Expense(int id, int tip, String not, int miktar, int gun, int ay, int yil) {
        this._id = id;
        this._tip = tip;
        this._not = not;
        this._miktar = miktar;
        this._gun = gun;
        this._ay = ay;
        this._yil = yil;
    }

    public Expense(int tip, String not, int miktar, int gun, int ay, int yil) {
        this._tip = tip;
        this._not = not;
        this._miktar = miktar;
        this._gun = gun;
        this._ay = ay;
        this._yil = yil;
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        this._id = id;
    }

    public Expense(int ay, int tip, int yil) {
        this._ay = ay;
        this._tip = tip;
        this._yil = yil;
    }


    public Expense(int miktar, String not,int id) {
        this._miktar = miktar;
        this._not = not;
        this._id=id;
    }

    public int getTip() {
        return _tip;
    }

    public void setTip(int tip) {
        this._tip = tip;
    }

    public String getNot() {
        return _not;
    }

    public void setNot(String not) {
        this._not = not;
    }

    public int getMiktar() {
        return _miktar;
    }

    public void setMiktar(int miktar) {
        this._miktar = miktar;
    }

    public int getGun() {
        return _gun;
    }

    public void setGun(int gun) {
        this._gun = gun;
    }

    public int getAy() {
        return _ay;
    }

    public void setAy(int ay) {
        this._ay = ay;
    }

    public int getYil() {
        return _yil;
    }

    public void setYil(int yil) {
        this._yil = yil;
    }



}
