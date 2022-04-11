
package com.snat.main.Ranks;

public enum Rank {


    ADMIN("&cAdmin"),
    MOD("&bMod"),
    APPRENTINCE("&6Apprentice"),
    MEMBER("&eMember");



    private String display;

    Rank(String display) {
        this.display = display;
    }

    public String getDisplay() { return display; }

}
