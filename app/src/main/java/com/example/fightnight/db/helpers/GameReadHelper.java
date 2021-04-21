package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.FightNightDB;
import com.example.fightnight.db.Game;

import java.util.List;

public class GameReadHelper extends Helper {

    private List<Game> games;

    @Override
    protected void getData() {
        games = FightNightDB.getInstance(context).gameDao().getAll();
    }

    public GameReadHelper(HelperListener listener, Context context) {
        super(listener, context);
    }

    public List<Game> getGames() { return games; }
}
