package com.apprentice.ti8m.myfirstrestclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by gol on 26.09.17.
 */


public class Pizza {

    @SerializedName("name")
    private String name;

    @SerializedName("price")
    private Float price;

    @SerializedName("id")
    private Integer id;


    public Pizza(String name, Float price){
       this(name, price, null);
    }

    public Pizza(String name, Float price, Integer id){
        this.name=name;
        this.price=price;
        this.id=id;
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
