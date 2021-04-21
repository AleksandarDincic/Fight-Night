package com.example.fightnight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.fightnight.db.helpers.HelperListener;
import com.example.fightnight.db.helpers.PlayerAddHelper;

public class AddPlayerActivity extends AppCompatActivity implements HelperListener {

    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_player);
        setTitle("Add Player");

        editText = findViewById(R.id.playerNameTextField);
    }

    @Override
    public void notifyListener() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    public void addPlayer(View view) {
        new PlayerAddHelper(this, this, editText.getText().toString()).start();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}