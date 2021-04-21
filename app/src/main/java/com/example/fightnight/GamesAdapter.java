package com.example.fightnight;

import android.content.Context;
import android.content.Intent;
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

import com.example.fightnight.db.Game;

import java.math.BigInteger;
import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GamesViewHolder> {

    private List<Game> games;
    private Context context;

    public GamesAdapter(Context context, List<Game> games) {
        this.context = context;
        this.games = games;
    }

    @NonNull
    @Override
    public GamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.game_row, parent, false);
        return new GamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GamesViewHolder holder, int position) {
        holder.gameNameText.setText(games.get(position).name);
        if(games.get(position).img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(games.get(position).img, 0, games.get(position).img.length);
            holder.gameImg.setImageBitmap(bitmap);
        }

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra("name", games.get(position).name);
                intent.putExtra("idG", games.get(position).idG);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return games.size();
    }

    public class GamesViewHolder extends RecyclerView.ViewHolder {

        TextView gameNameText;
        ImageView gameImg;
        ConstraintLayout mainLayout;

        public GamesViewHolder(@NonNull View itemView) {
            super(itemView);
            gameNameText = itemView.findViewById(R.id.gameNameText);
            gameImg = itemView.findViewById(R.id.gameImageView);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
