package com.developer.ti.mapa.Model;

/**
 * Created by tecnicoairmovil on 27/06/17.
 */

public class Test {

    private int icon;
    private String section;

    public Test() {
    }

    public Test(int icon, String section){
        this.icon = icon;
        this.section = section;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }
}
