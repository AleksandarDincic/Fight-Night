package com.example.fightnight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fightnight.db.Game;
import com.example.fightnight.db.helpers.GameReadHelper;
import com.example.fightnight.db.helpers.HelperListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements HelperListener {
    private GameReadHelper gameReadHelper;

    private RecyclerView gamesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Select Game");
        gamesRecyclerView = findViewById(R.id.gamesRecyclerView);
        System.out.println(getDatabasePath("fightnight.db").getAbsolutePath());
        refreshGames();
    }

    private void refreshRecycler(){
        Context context = this;
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                gamesRecyclerView.setAdapter(new GamesAdapter(context, gameReadHelper.getGames()));
                gamesRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            }
        });
    }

    public void refreshGames() {
        gameReadHelper = new GameReadHelper(this, this);
        gameReadHelper.start();
    }

    @Override
    public void notifyListener() {
        refreshRecycler();
    }

    public void goToAddGame(View view) {
        Intent intent = new Intent(this, AddGameActivity.class);

        startActivityForResult(intent, 1);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshGames();
    }
}