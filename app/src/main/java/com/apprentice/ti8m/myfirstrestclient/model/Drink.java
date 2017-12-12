package com.apprentice.ti8m.myfirstrestclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gol on 26.09.17.
 */


public class Drink {

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Float price;

    @SerializedName("id")
    private Integer id;

    @SerializedName("imagepath")
    private String imagepath;


    public Drink(String name, Float price, String imagepath) {
        this.name = name;
        this.price = price;

        this.imagepath = imagepath;
    }

    public Drink(String name, Float price, Integer id) {
        this.name = name;
        this.price = price;
        this.id = id;
    }

    public String getImagepath() {
        return imagepath;
    }

    public String getName() {
        return name;
    }

    public Float getPrice() {
        return price;
    }

    public Integer getId() {
        return id;
    }
}
