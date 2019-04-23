package com.example.lowkey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangeQuery extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_query);

        final Button saveQueryButton = (Button) findViewById(R.id.querySaveButton);
        saveQueryButton.setOnClickListener(new View.OnClickListener() {

            EditText editQuery = (EditText) findViewById(R.id.queryEditText);

            @Override
            public void onClick(View v) {
                String newQuery = editQuery.getText().toString();

                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

                sharedPreferences.edit().putString("query", newQuery).apply();
                Toast.makeText(getApplicationContext(), "New query set!", Toast.LENGTH_SHORT).show();

                Intent newerIntent = new Intent(ChangeQuery.this, Notepad2.class);
                ChangeQuery.this.startActivity(newerIntent);

            }
        });
    }
}
