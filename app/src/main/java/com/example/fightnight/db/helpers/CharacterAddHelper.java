package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.FightNightDB;
import com.example.fightnight.db.helpers.Helper;
import com.example.fightnight.db.helpers.HelperListener;

public class CharacterAddHelper extends Helper {

    private int idG;
    private String name;
    private byte[] img;

    public CharacterAddHelper(HelperListener listener, Context context, int idG, String name, byte[] img) {
        super(listener, context);
        this.idG = idG;
        this.name = name;
        this.img = img;
    }

    @Override
    protected void getData() {
        if (idG != -1) {
            Character newChar = new Character();
            newChar.idG = idG;
            newChar.name = name;
            newChar.img = img;
            FightNightDB.getInstance(context).characterDao().insertAll(newChar);
        }
    }
}
