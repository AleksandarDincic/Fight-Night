package com.example.fightnight;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fightnight.db.PlayerStats;

import java.util.List;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.PlayersViewHolder> {

    private List<PlayerStats> playerStats;
    private Context context;

    public PlayersAdapter(Context context, List<PlayerStats> playerStats) {
        this.context = context;
        this.playerStats = playerStats;
    }

    @NonNull
    @Override
    public PlayersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.player_row, parent, false);
        return new PlayersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayersViewHolder holder, int position) {
        holder.name.setText(GameActivity.players.get(playerStats.get(position).idP).name);
        holder.gamesPlayed.setText(Integer.toString(playerStats.get(position).gamesPlayed));
        holder.gamesWon.setText(Integer.toString(playerStats.get(position).gamesWon));
        holder.winRate.setText(String.format("%.2f", playerStats.get(position).winRate) + "%");
    }

    @Override
    public int getItemCount() {
        return playerStats.size();
    }

    public class PlayersViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView gamesPlayed;
        TextView gamesWon;
        TextView winRate;
        ConstraintLayout mainLayout;

        public PlayersViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            gamesPlayed = itemView.findViewById(R.id.gamesPlayedNum);
            gamesWon = itemView.findViewById(R.id.gamesWonNum);
            winRate = itemView.findViewById(R.id.winRateNum);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
