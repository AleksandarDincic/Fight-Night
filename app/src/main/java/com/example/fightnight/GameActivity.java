package com.example.fightnight;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.fightnight.db.Character;
import com.example.fightnight.db.Player;
import com.example.fightnight.db.helpers.CharacterReadHelper;
import com.example.fightnight.db.helpers.FightReadHelper;
import com.example.fightnight.db.helpers.HelperListener;
import com.example.fightnight.db.helpers.PlayerReadHelper;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

public class GameActivity extends AppCompatActivity implements HelperListener {

    public static final int FIGHTS_PER_PAGE = 10;

    public static HashMap<Integer, Character> characters = new HashMap<>();
    public static HashMap<Integer, Player> players = new HashMap<>();

    private int idG;
    private int dataFetchProgress = 0;

    private CharacterReadHelper characterReadHelper;
    private PlayerReadHelper playerReadHelper;
    private FightReadHelper fightReadHelper;

    private RecyclerView recyclerView;
    private TabLayout tabLayout;
    private Spinner sortSpinner;

    private ConstraintLayout fightModeLayout;
    private TextView fightPageView;
    private Button fightPrevPage;
    private Button fightNextPage;

    private int fightPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        if (getIntent().hasExtra("name"))
            setTitle(getIntent().getStringExtra("name"));
        if (getIntent().hasExtra("idG"))
            idG = getIntent().getIntExtra("idG", -1);

        recyclerView = findViewById(R.id.charactersRecyclerView);
        tabLayout = findViewById(R.id.tabLayout);
        sortSpinner = findViewById(R.id.characterSortSpinner);

        fightModeLayout = findViewById(R.id.fightModeLayout);
        fightPageView = findViewById(R.id.fightPageLabel);
        fightPrevPage = findViewById(R.id.prevFightButton);
        fightNextPage = findViewById(R.id.nextFightButton);

        addListeners();
        refreshStats();
    }

    private void addListeners() {
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                refreshGUI();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        sortSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sortAndRefreshGUI();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void sortCharacterData() {
        String[] sortTypes = getResources().getStringArray(R.array.character_sort_array);
        String selectedSort = (String) sortSpinner.getSelectedItem();
        if (selectedSort.equals(sortTypes[0])) { //Names A-Z
            characterReadHelper.getCharacterStats().sort((o1, o2) -> {
                return characters.get(o1.idC).name.compareTo(characters.get(o2.idC).name);
            });
        } else if (selectedSort.equals(sortTypes[1])) { //Names Z-A
            characterReadHelper.getCharacterStats().sort((o1, o2) -> {
                return characters.get(o1.idC).name.compareTo(characters.get(o2.idC).name) * -1;
            });
        } else if (selectedSort.equals(sortTypes[2])) { //Win rate lowest
            characterReadHelper.getCharacterStats().sort((o1, o2) -> {
                return o1.winRate < o2.winRate ? -1 : 1;
            });
        } else if (selectedSort.equals(sortTypes[3])) { //Win rate highest
            characterReadHelper.getCharacterStats().sort((o1, o2) -> {
                return o1.winRate > o2.winRate ? -1 : 1;
            });
        }
    }

    private void sortPlayerData() {
        String[] sortTypes = getResources().getStringArray(R.array.character_sort_array);
        String selectedSort = (String) sortSpinner.getSelectedItem();
        if (selectedSort.equals(sortTypes[0])) { //Names A-Z
            playerReadHelper.getPlayerStats().sort((o1, o2) -> {
                return players.get(o1.idP).name.compareTo(players.get(o2.idP).name);
            });
        } else if (selectedSort.equals(sortTypes[1])) { //Names Z-A
            playerReadHelper.getPlayerStats().sort((o1, o2) -> {
                return players.get(o1.idP).name.compareTo(players.get(o2.idP).name) * -1;
            });
        } else if (selectedSort.equals(sortTypes[2])) { //Win rate lowest
            playerReadHelper.getPlayerStats().sort((o1, o2) -> {
                return o1.winRate < o2.winRate ? -1 : 1;
            });
        } else if (selectedSort.equals(sortTypes[3])) { //Win rate highest
            playerReadHelper.getPlayerStats().sort((o1, o2) -> {
                return o1.winRate > o2.winRate ? -1 : 1;
            });
        }
    }

    private void sortAndRefreshGUI() {
        sortCharacterData();
        sortPlayerData();
        refreshGUI();
    }

    private void setFightMode(boolean value) {
        sortSpinner.setVisibility(value ? View.INVISIBLE : View.VISIBLE);

        fightModeLayout.setVisibility(value ? View.VISIBLE : View.INVISIBLE);
    }

    private void refreshGUI() {
        Context context = this;
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setFightMode(false);
                        recyclerView.setAdapter(new CharactersAdapter(context, characterReadHelper.getCharacterStats()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
                break;
            case 1:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setFightMode(false);
                        recyclerView.setAdapter(new PlayersAdapter(context, playerReadHelper.getPlayerStats()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
                break;
            case 2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setFightMode(true);
                        System.out.println(fightReadHelper.getFightNum());
                        int firstFight = fightReadHelper.getFightNum() == 0 ? 0 : (fightPage * FIGHTS_PER_PAGE + 1);
                        fightPrevPage.setVisibility(fightPage != 0 ? View.VISIBLE : View.INVISIBLE);

                        int lastFight = (fightPage + 1) * FIGHTS_PER_PAGE;
                        if (lastFight >= fightReadHelper.getFightNum()) {
                            lastFight = fightReadHelper.getFightNum();
                            fightNextPage.setVisibility(View.INVISIBLE);
                        } else fightNextPage.setVisibility(View.VISIBLE);

                        fightPageView.setText("Showing " + firstFight + "-" + lastFight + " of " + fightReadHelper.getFightNum());

                        recyclerView.setAdapter(new FightsAdapter(context, fightReadHelper.getFights()));
                        recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    }
                });
                break;
        }
    }

    private void refreshStats() {
        dataFetchProgress = 0;
        characters.clear();
        players.clear();
        characterReadHelper = new CharacterReadHelper(this, this, idG);
        characterReadHelper.start();
    }

    private void updateMatchHistory(){
        fightReadHelper = new FightReadHelper(this, this, idG, FIGHTS_PER_PAGE, fightPage * FIGHTS_PER_PAGE);
        fightReadHelper.start();
    }

    public void nextPage(View view){
        fightPage++;
        dataFetchProgress = 4;
        updateMatchHistory();
    }

    public void prevPage(View view){
        fightPage--;
        dataFetchProgress = 4;
        updateMatchHistory();
    }

    @Override
    public void notifyListener() {
        switch (dataFetchProgress) {
            case 0:
                dataFetchProgress = 1;
                for (Character c : characterReadHelper.getCharacters())
                    characters.put(c.idC, c);
                playerReadHelper = new PlayerReadHelper(this, this, idG);
                playerReadHelper.start();
                break;
            case 1:
                dataFetchProgress = 2;
                for (Player p : playerReadHelper.getPlayers())
                    players.put(p.idP, p);
                updateMatchHistory();
                break;
            case 2:
                dataFetchProgress = 3;
                sortAndRefreshGUI();
                break;
            case 4:
                refreshGUI();
                break;
        }

    }

    public void goToAdd(View view) {
        switch (tabLayout.getSelectedTabPosition()) {
            case (0):
                Intent intent = new Intent(this, AddCharacterActivity.class);
                intent.putExtra("idG", idG);
                startActivityForResult(intent, 1);
                break;
            case (1):
                intent = new Intent(this, AddPlayerActivity.class);
                startActivityForResult(intent, 1);
                break;
            case (2):
                intent = new Intent(this, AddFightActivity.class);
                System.out.println("Saljem idG " + idG);
                intent.putExtra("idG", idG);
                startActivityForResult(intent, 1);
                break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        refreshStats();
    }
}