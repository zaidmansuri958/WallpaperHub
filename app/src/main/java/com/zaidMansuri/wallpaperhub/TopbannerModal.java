package com.zaidMansuri.wallpaperhub;

public class TopbannerModal {
    String image,name;

    public TopbannerModal(String image,String name) {
        this.image = image;
        this.name=name;
    }

    public TopbannerModal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
