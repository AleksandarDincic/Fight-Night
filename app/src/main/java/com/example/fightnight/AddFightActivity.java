package com.example.fightnight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.CharacterStats;
import com.example.fightnight.db.Player;
import com.example.fightnight.db.helpers.FightAddHelper;
import com.example.fightnight.db.helpers.HelperListener;

import java.util.ArrayList;
import java.util.List;

public class AddFightActivity extends AppCompatActivity implements HelperListener {

    private int idG;

    private Spinner player1Spinner;
    private Spinner player1CharSpinner;
    private Spinner player2Spinner;
    private Spinner player2CharSpinner;

    private RadioGroup winner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fight);
        setTitle("Add Fight");

        if (getIntent().hasExtra("idG"))
            idG = getIntent().getIntExtra("idG", -1);

        List<Player> playerList = new ArrayList<>(GameActivity.players.values());
        playerList.sort((o1, o2) -> {
            return o1.name.compareTo(o2.name);
        });
        ArrayAdapter playersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, playerList.toArray());
        playersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        List<Character> characterList = new ArrayList<>(GameActivity.characters.values());
        characterList.sort((o1, o2) -> {
            return o1.name.compareTo(o2.name);
        });
        ArrayAdapter charactersAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, characterList.toArray());
        charactersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        player1Spinner = findViewById(R.id.player1Spinner);
        player1Spinner.setAdapter(playersAdapter);
        player1CharSpinner = findViewById(R.id.player1CharSpinner);
        player1CharSpinner.setAdapter(charactersAdapter);

        player2Spinner = findViewById(R.id.player2Spinner);
        player2Spinner.setAdapter(playersAdapter);
        player2CharSpinner = findViewById(R.id.player2CharSpinner);
        player2CharSpinner.setAdapter(charactersAdapter);

        winner = findViewById(R.id.winnerGroup);
    }

    public void addFight(View view) {
        Player player1 = (Player) player1Spinner.getSelectedItem();
        Player player2 = (Player) player2Spinner.getSelectedItem();

        if (player1 == player2) {
            Toast.makeText(getApplicationContext(), "One cannot fight oneself", Toast.LENGTH_SHORT).show();
        } else {
            Character player1char = (Character) player1CharSpinner.getSelectedItem();
            Character player2char = (Character) player2CharSpinner.getSelectedItem();

            int winnerNum = winner.getCheckedRadioButtonId() == R.id.player1Winner ? 1 : 2;

            System.out.println("Sacu napravim fajt sa idG" + idG);

            new FightAddHelper(this, this, idG, player1.idP, player2.idP, player1char.idC, player2char.idC, winnerNum).start();
        }
    }

    @Override
    public void notifyListener() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
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