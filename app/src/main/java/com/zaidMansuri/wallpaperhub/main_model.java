package com.zaidMansuri.wallpaperhub;

public class main_model {
    String image,name;

    public main_model(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public main_model() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
