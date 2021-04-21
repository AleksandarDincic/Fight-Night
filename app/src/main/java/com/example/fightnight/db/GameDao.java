package com.example.fightnight.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GameDao {
    @Query("SELECT * FROM Game")
    List<Game> getAll();

    @Query("SELECT * FROM Game WHERE idG = :id LIMIT 1")
    Game findById(int id);

    @Insert
    void insertAll(Game... games);

    @Delete
    void delete(Game game);
}