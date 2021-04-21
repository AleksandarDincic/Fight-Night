package com.example.fightnight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CharacterDao {
    @Query("SELECT * FROM Character WHERE idG = :idG")
    List<Character> getAllGame(int idG);

    @Query("SELECT * FROM Character WHERE idC = :id LIMIT 1")
    Character findById(int id);

    @Insert
    void insertAll(Character... characters);

    @Delete
    void delete(Character character);

    @Transaction
    @Query("SELECT idC, (SELECT COUNT(*) FROM Fight WHERE idC = idC1 OR idc = idC2) + (SELECT COUNT(*) FROM Fight WHERE idC = idC1 AND idC = idC2) AS gamesPlayed, " +
            "(SELECT COUNT(*) FROM Fight WHERE (idC = idC1 AND winner = 1) OR (idC = idC2 AND winner = 2)) AS gamesWon, 0.0 AS winRate FROM Character WHERE idG = :idG")
    List<CharacterStats> getAllCharacterStats(int idG);
}
