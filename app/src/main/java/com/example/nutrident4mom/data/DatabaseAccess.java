package com.example.nutrident4mom.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nutrident4mom.model.Food;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    Cursor c = null;
    Cursor c2 = null;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }


    //query methods
    public String foodName() {
        String selectQuery = "SELECT  * FROM food";
        c = db.rawQuery(selectQuery, null);
        c.moveToFirst();
        String foodName = c.getString(1);
        return foodName;
    }


    //query methods
    public List<Food> foods(String name) {
        String selectQuery = "SELECT  * FROM food WHERE Description LIKE '%" + name + "%'";
        Cursor c = db.rawQuery(selectQuery, null);
        List<Food> foods = new ArrayList<>();

        if (c != null && c.moveToFirst()) {

            do {
                int id = c.getInt(0);
                String foodName = c.getString(1);
                double Energy= c.getDouble(2);
                double Protein = c.getDouble(3);
                double Fat = c.getDouble(4);
                double Carbohydrate = c.getDouble(5);
                double Totalsugars = c.getDouble(6);
                double Totaldietaryfibre = c.getDouble(7);
                double Iron = c.getDouble(8);
                double FolicAcid = c.getDouble(9);
                double VitaminA = c.getDouble(10);
                double VitaminC = c.getDouble(11);
                double VitaminD = c.getDouble(12);
                double Calcium = c.getDouble(13);
                double Iodine = c.getDouble(14);
                double Zinc = c.getDouble(15);
                double Fluoride = c.getDouble(16);


                Food food = new Food(id, foodName, Energy,Protein,Fat,Carbohydrate,Totalsugars,Totaldietaryfibre,Iron,FolicAcid,VitaminA,VitaminC,VitaminD,Calcium,Iodine,Zinc,Fluoride);
                foods.add(food);

                //c2.close();

            } while (c.moveToNext());

            c.close();
        }
        return foods;
    }

}
