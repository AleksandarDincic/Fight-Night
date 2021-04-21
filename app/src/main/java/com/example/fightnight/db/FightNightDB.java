package com.example.fightnight.db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Game.class, Character.class, Player.class, Fight.class},  version = 6)
@TypeConverters({Converters.class})
public abstract class FightNightDB extends RoomDatabase {

    private static volatile FightNightDB instance;

    static final Migration MIGRATION5_6 = new Migration(5, 6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DELETE FROM Game");
            database.execSQL("DELETE FROM Character");
            database.execSQL("DELETE FROM Player");
            database.execSQL("DELETE FROM Fight");
        }
    };

    public synchronized static FightNightDB getInstance(Context context) {
        if(instance == null)
            instance = Room.databaseBuilder(context.getApplicationContext(), FightNightDB.class, "Fightnight.db").addMigrations(MIGRATION5_6).build();
            return instance;
    }

    public abstract GameDao gameDao();

    public abstract CharacterDao characterDao();

    public abstract PlayerDao playerDao();

    public abstract FightDao fightDao();
}