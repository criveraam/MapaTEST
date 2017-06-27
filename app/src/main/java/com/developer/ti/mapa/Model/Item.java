package com.developer.ti.mapa.Model;

/**
 * Created by tecnicoairmovil on 27/06/17.
 */

public class Item {
    private String title;
    private String subtitle;

    public Item(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }
}

