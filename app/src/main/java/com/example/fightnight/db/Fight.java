package com.example.fightnight.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Fight {
    @PrimaryKey(autoGenerate = true)
    public int idF;

    public int idG;
    public Date date;
    public int idP1;
    public int idP2;
    public int idC1;
    public int idC2;
    public int winner;
}
