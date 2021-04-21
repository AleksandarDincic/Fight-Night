package com.example.fightnight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.fightnight.db.helpers.GameAddHelper;
import com.example.fightnight.db.helpers.HelperListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddGameActivity extends AppCompatActivity implements HelperListener {

    public static final int GAME_IMAGE_WIDTH = 600;
    public static final int GAME_IMAGE_HEIGHT = 300;

    private EditText editText;
    private Bitmap imageBitmap = null;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        setTitle("Add Game");

        editText = findViewById(R.id.gameNameTextField);
        imageView = findViewById(R.id.gameImage);
    }

    public void addGame(View view) {
        byte[] imgBlob = null;

        if (imageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imgBlob = stream.toByteArray();
            imageBitmap.recycle();
        }

        new GameAddHelper(this, this, editText.getText().toString(), imgBlob).start();
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

    public void addImage(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                return;
            }
            try {
                InputStream inputStream = this.getContentResolver().openInputStream(data.getData());
                imageBitmap = BitmapFactory.decodeStream(inputStream);
                if (imageBitmap.getWidth() > GAME_IMAGE_WIDTH || imageBitmap.getHeight() > GAME_IMAGE_HEIGHT)
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, GAME_IMAGE_WIDTH, GAME_IMAGE_HEIGHT, false);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageBitmap(imageBitmap);
                    }
                });
            } catch (FileNotFoundException e) {
                Toast.makeText(getApplicationContext(), "Cannot open image", Toast.LENGTH_SHORT).show();
            }
        }
    }
}