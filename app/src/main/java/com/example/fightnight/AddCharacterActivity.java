package com.example.fightnight;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

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

import com.example.fightnight.db.helpers.CharacterAddHelper;
import com.example.fightnight.db.helpers.GameAddHelper;
import com.example.fightnight.db.helpers.HelperListener;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddCharacterActivity extends AppCompatActivity implements HelperListener {

    public static final int CHARACTER_IMAGE_WIDTH = 300;
    public static final int CHARACTER_IMAGE_HEIGHT = 300;

    private EditText editText;
    private int idG;
    private ImageView imageView;
    private Bitmap imageBitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_character);
        setTitle("Add Character");

        if (getIntent().hasExtra("idG"))
            idG = getIntent().getIntExtra("idG", -1);
        editText = findViewById(R.id.characterNameTextField);
        imageView = findViewById(R.id.characterImage);
    }

    public void addCharacter(View view) {
        byte[] imgBlob = null;

        if (imageBitmap != null) {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            imgBlob = stream.toByteArray();
            imageBitmap.recycle();
        }

        new CharacterAddHelper(this, this, idG, editText.getText().toString(), imgBlob).start();
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
                if (imageBitmap.getWidth() > CHARACTER_IMAGE_WIDTH || imageBitmap.getHeight() > CHARACTER_IMAGE_HEIGHT)
                    imageBitmap = Bitmap.createScaledBitmap(imageBitmap, CHARACTER_IMAGE_WIDTH, CHARACTER_IMAGE_HEIGHT, false);
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