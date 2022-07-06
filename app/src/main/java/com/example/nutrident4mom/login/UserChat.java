package com.example.nutrident4mom.login;

public class UserChat {
    private int carbs, TDEE, fat, protein;
    private String email,userId;
    private boolean isAdmin, isDiabetic;

    public UserChat(){

    }

    public UserChat(int carbs, int TDEE, int fat, int protein, String email, boolean isAdmin, boolean isDiabetic,String userId) {
        this.carbs = carbs;
        this.TDEE = TDEE;
        this.fat = fat;
        this.protein = protein;
        this.email = email;
        this.isAdmin = isAdmin;
        this.isDiabetic = isDiabetic;
        this.userId = userId;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTDEE() {
        return TDEE;
    }

    public void setTDEE(int TDEE) {
        this.TDEE = TDEE;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isDiabetic() {
        return isDiabetic;
    }

    public void setDiabetic(boolean diabetic) {
        isDiabetic = diabetic;
    }
}
