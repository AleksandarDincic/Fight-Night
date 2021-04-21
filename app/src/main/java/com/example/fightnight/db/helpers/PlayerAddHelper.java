package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.FightNightDB;
import com.example.fightnight.db.Player;

public class PlayerAddHelper extends Helper {

    private String name;

    public PlayerAddHelper(HelperListener listener, Context context, String name) {
        super(listener, context);
        this.name = name;
    }

    @Override
    protected void getData() {
        Player newPlayer = new Player();
        newPlayer.name = name;
        FightNightDB.getInstance(context).playerDao().insertAll(newPlayer);
    }
}
