package com.example.fightnight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PlayerDao {
    @Query("SELECT * FROM Player")
    List<Player> getAll();

    @Query("SELECT * FROM Player WHERE idP = :id LIMIT 1")
    Player findById(int id);

    @Insert
    void insertAll(Player... players);

    @Delete
    void delete(Player player);

    @Query("SELECT idP, (SELECT COUNT(*) FROM Fight WHERE idG = :idG AND (idP = idP1 OR idP = idP2)) AS gamesPlayed, " +
            "(SELECT COUNT(*) FROM Fight WHERE idG = :idG AND ((idP = idP1 AND winner = 1) OR (idP = idP2 AND winner = 2))) AS gamesWon, 0.0 as winRate FROM Player")
    List<PlayerStats> getAllByGame(int idG);
}
