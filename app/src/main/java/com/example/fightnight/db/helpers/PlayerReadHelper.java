package com.example.fightnight.db.helpers;

import android.content.Context;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.CharacterStats;
import com.example.fightnight.db.FightNightDB;
import com.example.fightnight.db.Player;
import com.example.fightnight.db.PlayerStats;

import java.util.List;

public class PlayerReadHelper extends Helper{

    private List<Player> players;
    private List<PlayerStats> playerStats;
    private int idG;

    public PlayerReadHelper(HelperListener listener, Context context, int idG) {
        super(listener, context);
        this.idG = idG;
    }

    @Override
    protected void getData() {
        players = FightNightDB.getInstance(context).playerDao().getAll();
        playerStats = FightNightDB.getInstance(context).playerDao().getAllByGame(idG);
        for (PlayerStats stat: playerStats)
            stat.winRate = stat.gamesPlayed == 0 ? 0 : ((double) stat.gamesWon / stat.gamesPlayed * 100);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<PlayerStats> getPlayerStats() {
        return playerStats;
    }
}
