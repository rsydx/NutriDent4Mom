package com.example.nutrident4mom.model;

import android.os.Parcel;
import android.os.Parcelable;


public class Food implements Parcelable {
    int id;
    String desc, portion,temp;
    double energy , protein , fat , carb , sugar , fibre , iron , folic, vitaminA, vitaminC, vitaminD, calcium, iodine, zinc, fluoride;

    public Food() {
    }

    public Food(int id, String desc, double energy, double protein, double fat, double carb, double sugar, double fibre, double iron, double folic, double vitaminA, double vitaminC, double vitaminD, double calcium, double iodine, double zinc, double fluoride) {
        this.id = id;
        this.temp = desc.toLowerCase();
        char[] array = temp.toCharArray();
        // Uppercase first letter.
        array[0] = Character.toUpperCase(array[0]);

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1]) ) {
                array[i] = Character.toUpperCase(array[i]);
                if(array[i] == '('){
                    array[i+1] = Character.toUpperCase(array[i+1]);
                }
            }

        }

        // convert the char array to the string
        this.desc = String.valueOf(array);
        this.energy = energy;
        this.protein = protein;
        this.fat = fat;
        this.carb = carb;
        this.sugar = sugar;
        /*
        this.fibre = fibre;
        this.iron = iron;
        this.folic = folic;
        this.vitaminA = vitaminA;
        this.vitaminC = vitaminC;
        this.vitaminD = vitaminD;
        this.calcium = calcium;
        this.iodine = iodine;
        this.zinc = zinc;
        this.fluoride = fluoride;
         */
    }

    public Food(int id, String desc) {
        this.id = id;
        this.desc = desc;
        energy = 0.0;
        fat = 0.0;
        carb = 0.0;
        sugar = 0.0;
    }



    protected Food(Parcel in) {
        id = in.readInt();
        desc = in.readString();
        portion = in.readString();
        energy = in.readDouble();
        protein = in.readDouble();
        fat = in.readDouble();
        carb = in.readDouble();
        sugar = in.readDouble();
/*
        fibre = in.readDouble();
        iron = in.readDouble();
        folic = in.readDouble();
        vitaminA = in.readDouble();
        vitaminC = in.readDouble();
        vitaminD = in.readDouble();
        calcium = in.readDouble();
        iodine = in.readDouble();
        zinc = in.readDouble();
        fluoride = in.readDouble();



 */
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public double getEnergy() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarb() {
        return carb;
    }

    public void setCarb(double carb) {
        this.carb = carb;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getFibre() {
        return fibre;
    }

    public void setFibre(double fibre) {
        this.fibre = fibre;
    }

    public double getIron() {
        return iron;
    }

    public void setIron(double iron) {
        this.iron = iron;
    }

    public double getFolic() {
        return folic;
    }

    public void setFolic(double folic) {
        this.folic = folic;
    }

    public double getVitaminA() {
        return vitaminA;
    }

    public void setVitaminA(double vitaminA) {
        this.vitaminA = vitaminA;
    }

    public double getVitaminC() {
        return vitaminC;
    }

    public void setVitaminC(double vitaminC) {
        this.vitaminC = vitaminC;
    }

    public double getVitaminD() {
        return vitaminD;
    }

    public void setVitaminD(double vitaminD) {
        this.vitaminD = vitaminD;
    }

    public double getCalcium() {
        return calcium;
    }

    public void setCalcium(double calcium) {
        this.calcium = calcium;
    }

    public double getIodine() {
        return iodine;
    }

    public void setIodine(double iodine) {
        this.iodine = iodine;
    }

    public double getZinc() {
        return zinc;
    }

    public void setZinc(double zinc) {
        this.zinc = zinc;
    }

    public double getFluoride() {
        return fluoride;
    }

    public void setFluoride(double fluoride) {
        this.fluoride = fluoride;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //continue here
        dest.writeInt(id);
        dest.writeString(desc);
        dest.writeString(portion);
        dest.writeDouble(energy);
        dest.writeDouble(protein);
        dest.writeDouble(fat);
        dest.writeDouble(carb);
        dest.writeDouble(sugar);
    }
}

