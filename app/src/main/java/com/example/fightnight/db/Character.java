package com.example.fightnight.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Character {
    @PrimaryKey(autoGenerate = true)
    public int idC;

    public String name;
    public byte[] img;

    public int idG;

    public String toString(){
        return name;
    }
}
