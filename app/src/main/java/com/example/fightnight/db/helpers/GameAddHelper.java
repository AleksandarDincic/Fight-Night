package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.FightNightDB;
import com.example.fightnight.db.Game;

public class GameAddHelper extends Helper {
    private String name;
    private byte[] img;

    public GameAddHelper(HelperListener listener, Context context, String name, byte[] img) {
        super(listener, context);
        this.name = name;
        this.img = img;
    }

    @Override
    protected void getData() {
        Game newGame = new Game();
        newGame.name = name;
        newGame.img = img;
        FightNightDB.getInstance(context).gameDao().insertAll(newGame);
    }
}
