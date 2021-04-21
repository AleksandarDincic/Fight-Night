package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.CharacterStats;
import com.example.fightnight.db.Fight;
import com.example.fightnight.db.FightNightDB;

import java.util.List;

public class FightReadHelper extends Helper {

    private List<Fight> fights;
    private int idG;
    private int limit;
    private int offset;
    private int fightNum;

    public FightReadHelper(HelperListener listener, Context context, int idG, int limit, int offset) {
        super(listener, context);
        this.idG = idG;
        this.limit = limit;
        this.offset = offset;
    }

    @Override
    protected void getData() {
        fights = FightNightDB.getInstance(context).fightDao().getAllGame(idG, limit, offset);
        fightNum = FightNightDB.getInstance(context).fightDao().getNumOfFightsGame(idG);
    }

    public List<Fight> getFights() {
        return fights;
    }

    public int getFightNum() {
        return fightNum;
    }
}
