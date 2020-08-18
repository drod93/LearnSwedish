package com.example.learnswedish;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.learnswedish.sqldb.DBController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ImportActivity extends AppCompatActivity {

    DBController controller;
    SQLiteDatabase db;
    final Context context = this;

    ArrayList<HashMap<String, String>> myList;
    private static final int WRITE_PERMISSION_CODE = 100;
    private static final int READ_PERMISSION_CODE = 101;
    private Button readPerm, writePerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.import_activity);

        readPerm = findViewById(R.id.readPerm);
        writePerm = findViewById(R.id.writePerm);

        // Set Buttons on Click Listeners
        writePerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkPermission(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        WRITE_PERMISSION_CODE);
            }
        });

        readPerm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                        READ_PERMISSION_CODE);
            }
        });

        controller = new DBController(getApplicationContext());
        db = controller.getWritableDatabase();
        controller.onCreate(db);


    }

    public void writeToDB(View view) {


        ContentValues contentValues = new ContentValues();

        EditText swedishField = findViewById(R.id.swedishField);
        String swedishWord = swedishField.getText().toString();
        EditText englishField = findViewById(R.id.englishField);
        String englishWord = englishField.getText().toString();

        contentValues.put(DBController.colSwedish, swedishWord);
        contentValues.put(DBController.colEnglish, englishWord);
        db.insert(DBController.tableName, null, contentValues);


    }

    public void readFromDB(View view) {

        myList = controller.getAllProducts();
        TextView tv = findViewById(R.id.displayDB);
        if (myList.size() != 0) {

            tv.setText(myList.toString());
        }
        else {
            tv.setText(context.getString(R.string.empty_db));
        }
    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(ImportActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(ImportActivity.this,
                    new String[] { permission },
                    requestCode);
        }
        else {
            Toast.makeText(ImportActivity.this,
                    "Permission already granted",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super
                .onRequestPermissionsResult(requestCode,
                        permissions,
                        grantResults);

        if (requestCode == WRITE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ImportActivity.this,
                        "Write Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(ImportActivity.this,
                        "Write Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
        else if (requestCode == READ_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(ImportActivity.this,
                        "Read Permission Granted",
                        Toast.LENGTH_SHORT)
                        .show();
            }
            else {
                Toast.makeText(ImportActivity.this,
                        "Read Permission Denied",
                        Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}


