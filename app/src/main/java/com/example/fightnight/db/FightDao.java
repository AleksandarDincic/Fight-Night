package com.example.fightnight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FightDao {
    @Query("SELECT * FROM Fight WHERE idG = :idG ORDER BY date DESC LIMIT :limit OFFSET :offset")
    List<Fight> getAllGame(int idG, int limit, int offset);

    @Query("SELECT * FROM Fight WHERE idP1 = :idP or idP2 = :idP ORDER BY date DESC LIMIT :limit OFFSET :offset")
    List<Fight> getAllPlayer(int idP, int limit, int offset);

    @Query("SELECT * FROM Fight WHERE idC1 = :idC OR idC2 = :idC ORDER BY date DESC LIMIT :limit OFFSET :offset")
    List<Fight> getAllCharacter(int idC, int limit, int offset);

    @Query("SELECT * FROM Fight WHERE idF = :id LIMIT 1")
    Fight findById(int id);

    @Query("SELECT COUNT(*) FROM Fight WHERE idG = :idG")
    int getNumOfFightsGame(int idG);

    @Insert
    void insertAll(Fight... fights);

    @Delete
    void delete(Fight fight);
}
