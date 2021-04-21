package com.example.fightnight.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey(autoGenerate = true)
    public int idP;

    public String name;

    public String toString(){
        return name;
    }
}
