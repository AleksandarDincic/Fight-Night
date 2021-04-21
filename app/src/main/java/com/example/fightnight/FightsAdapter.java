package com.example.fightnight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fightnight.db.Fight;

import org.w3c.dom.Text;

import java.util.List;

public class FightsAdapter extends  RecyclerView.Adapter<FightsAdapter.FightsViewHolder>{

    private List<Fight> fights;
    private Context context;

    public FightsAdapter(Context context, List<Fight> fights) {
        this.context = context;
        this.fights = fights;
    }

    @NonNull
    @Override
    public FightsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fight_row, parent, false);
        return new FightsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FightsViewHolder holder, int position) {
        holder.player1name.setText(GameActivity.players.get(fights.get(position).idP1).name);
        holder.player2name.setText(GameActivity.players.get(fights.get(position).idP2).name);

        byte[] char1Img = GameActivity.characters.get(fights.get(position).idC1).img;
        if(char1Img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(char1Img, 0, char1Img.length);
            holder.player1charImg.setImageBitmap(bitmap);
        }

        holder.player1status.setText(fights.get(position).winner == 1 ? "WIN" : "LOSS");
        holder.player2status.setText(fights.get(position).winner == 2 ? "WIN" : "LOSS");
        holder.player1card.setCardBackgroundColor(holder.itemView.getResources().getColor(fights.get(position).winner == 1 ? R.color.winBg : R.color.lossBg));
        holder.player2card.setCardBackgroundColor(holder.itemView.getResources().getColor(fights.get(position).winner == 2 ? R.color.winBg : R.color.lossBg));

        byte[] char2Img = GameActivity.characters.get(fights.get(position).idC2).img;
        if(char2Img != null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(char2Img, 0, char2Img.length);
            holder.player2charImg.setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return fights.size();
    }

    public class FightsViewHolder extends RecyclerView.ViewHolder {

        CardView player1card;
        TextView player1name;
        TextView player1status;
        ImageView player1charImg;

        CardView player2card;
        TextView player2name;
        TextView player2status;
        ImageView player2charImg;

        public FightsViewHolder(@NonNull View itemView) {
            super(itemView);

            player1card = itemView.findViewById(R.id.player1CardView);
            player1name = itemView.findViewById(R.id.player1name);
            player1status = itemView.findViewById(R.id.player1status);
            player1charImg = itemView.findViewById(R.id.player1charImg);

            player2card = itemView.findViewById(R.id.player2CardView);
            player2name = itemView.findViewById(R.id.player2name);
            player2status = itemView.findViewById(R.id.player2status);
            player2charImg = itemView.findViewById(R.id.player2charImg);
        }
    }
}
