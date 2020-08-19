package com.example.learnswedish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

    }

    public void goToImport(View view) {
        Intent i = new Intent(getApplicationContext(), ImportActivity.class);
        startActivity(i);
    }

    public void goToFlashcard(View view) {
        Intent i = new Intent(getApplicationContext(), FlashcardActivity.class);
        startActivity(i);
    }
}