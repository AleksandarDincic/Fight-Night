package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.CharacterStats;
import com.example.fightnight.db.FightNightDB;

import java.util.List;

public class CharacterReadHelper extends Helper {

    private List<Character> characters;
    private List<CharacterStats> characterStats;
    private int idG;

    public CharacterReadHelper(HelperListener listener, Context context, int idG) {
        super(listener, context);
        this.idG = idG;
    }

    @Override
    protected void getData() {
        characters = FightNightDB.getInstance(context).characterDao().getAllGame(idG);
        characterStats = FightNightDB.getInstance(context).characterDao().getAllCharacterStats(idG);
        for (CharacterStats stat : characterStats)
            stat.winRate = stat.gamesPlayed == 0 ? 0 : ((double) stat.gamesWon / stat.gamesPlayed * 100);
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<CharacterStats> getCharacterStats() {
        return characterStats;
    }
}
