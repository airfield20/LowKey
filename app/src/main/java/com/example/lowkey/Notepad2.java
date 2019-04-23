package com.example.lowkey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

public class Notepad2 extends AppCompatActivity {

    public EditText EditTxtNote;
    public Toolbar toolbar;
    SharedPreferences notesPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad2);
        EditTxtNote = (EditText) findViewById(R.id.noteEditText);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        notesPage = getApplicationContext().getSharedPreferences("Notes", Context.MODE_PRIVATE);
        EditTxtNote.setText(notesPage.getString("Note1.txt", ""));
        EditTxtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Save("Note1.txt");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void Save(String fileName) {
        try {

            notesPage.edit().putString(fileName, EditTxtNote.getText().toString()).commit();
//            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
        } catch (Throwable t) {
            Toast.makeText(this, "Exception: " + t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {

            case R.id.change_query:
                //user chose the "list notes" item
                Intent newerIntent2 = new Intent(Notepad2.this, ChangeQuery.class);
                Notepad2.this.startActivity(newerIntent2);
                return true;
            case R.id.change_pass:
                Intent newerIntent = new Intent(Notepad2.this, ChangPassword.class);
                Notepad2.this.startActivity(newerIntent);


            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
