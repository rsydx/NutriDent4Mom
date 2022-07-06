package com.example.nutrident4mom.model;

//import android.graphics.Bitmap;

import android.graphics.Bitmap;

public class UsersFood {

    private static int User3foodCount = 0;

    public String foodname,category;
    public String energy, protein, fat, carb,date;
    //public Bitmap icon;

    public UsersFood(){}

    public static void incrementFoodCount() {
        User3foodCount = User3foodCount +1;
    }

    public static int getFoodCount() {
        return User3foodCount;
    }


    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }
    public void setFoodCount(int User3foodCount) {
        this.User3foodCount = User3foodCount;
    }


    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFoodname() {
        return foodname;
    }


    public String getCategory() {
        return category;
    }

    public String getEnergy() {
        return energy;
    }

    public static int getUser3foodCount() {
        return User3foodCount;
    }

    public static void setUser3foodCount(int user3foodCount) {
        User3foodCount = user3foodCount;
    }

    public void setEnergy(String  energy) {
        this.energy = energy;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getCarb() {
        return carb;
    }

    public void setCarb(String carb) {
        this.carb = carb;
    }

    public UsersFood(String foodname, String energy, String protein, String fat, String carb, String category, String date) {
        this.foodname = foodname;
        this.category = category;
        this.energy = energy;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.date = date;
    }
}