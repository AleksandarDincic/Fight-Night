package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.Fight;
import com.example.fightnight.db.FightNightDB;

import java.util.Date;

public class FightAddHelper extends Helper {

    private int idG;
    private int idP1;
    private int idP2;
    private int idC1;
    private int idC2;
    private int winner;

    public FightAddHelper(HelperListener listener, Context context, int idG, int idP1, int idP2, int idC1, int idC2, int winner) {
        super(listener, context);
        this.idG = idG;

        this.idP1 = idP1;
        this.idP2 = idP2;

        this.idC1 = idC1;
        this.idC2 = idC2;

        this.winner = winner;
    }

    @Override
    protected void getData() {
        Fight newFight = new Fight();
        newFight.idG = idG;

        newFight.idP1 = idP1;
        newFight.idP2 = idP2;

        newFight.idC1 = idC1;
        newFight.idC2 = idC2;

        newFight.winner = winner;
        newFight.date = new Date();

        FightNightDB.getInstance(context).fightDao().insertAll(newFight);
    }
}
