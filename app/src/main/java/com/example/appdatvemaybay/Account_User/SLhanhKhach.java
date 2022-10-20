package com.example.appdatvemaybay.Account_User;

public class SLhanhKhach {
    String SLNL,SLTE,SLEB;

    public SLhanhKhach(String SLNL, String SLTE, String SLEB) {
        this.SLNL = SLNL;
        this.SLTE = SLTE;
        this.SLEB = SLEB;
    }

    public String getSLNL() {
        return SLNL;
    }

    public void setSLNL(String SLNL) {
        this.SLNL = SLNL;
    }

    public String getSLTE() {
        return SLTE;
    }

    public void setSLTE(String SLTE) {
        this.SLTE = SLTE;
    }

    public String getSLEB() {
        return SLEB;
    }

    public void setSLEB(String SLEB) {
        this.SLEB = SLEB;
    }
}
