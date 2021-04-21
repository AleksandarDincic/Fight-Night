package com.example.fightnight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fightnight.db.CharacterStats;

import java.util.List;

public class CharactersAdapter extends RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder> {

    private List<CharacterStats> characterStats;
    private Context context;

    public CharactersAdapter(Context context, List<CharacterStats> characterStats) {
        this.context = context;
        this.characterStats = characterStats;
    }

    @NonNull
    @Override
    public CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.character_row, parent, false);
        return new CharactersAdapter.CharactersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersViewHolder holder, int position) {
        holder.characterName.setText(GameActivity.characters.get(characterStats.get(position).idC).name);
        holder.gamesPlayed.setText(Integer.toString(characterStats.get(position).gamesPlayed));
        holder.gamesWon.setText(Integer.toString(characterStats.get(position).gamesWon));
        holder.winRate.setText(String.format("%.2f", characterStats.get(position).winRate) + "%");
        byte[] charImg = GameActivity.characters.get(characterStats.get(position).idC).img;
        if(charImg != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(charImg, 0, charImg.length);
            holder.characterImg.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return characterStats.size();
    }

    public class CharactersViewHolder extends RecyclerView.ViewHolder {

        TextView characterName;
        TextView gamesPlayed;
        TextView gamesWon;
        TextView winRate;
        ImageView characterImg;
        ConstraintLayout mainLayout;

        public CharactersViewHolder(@NonNull View itemView) {
            super(itemView);
            characterName = itemView.findViewById(R.id.name);
            gamesPlayed = itemView.findViewById(R.id.gamesPlayedNum);
            gamesWon = itemView.findViewById(R.id.gamesWonNum);
            winRate = itemView.findViewById(R.id.winRateNum);
            characterImg = itemView.findViewById(R.id.characterImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
