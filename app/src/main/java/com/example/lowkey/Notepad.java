package com.example.lowkey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


public class Notepad extends AppCompatActivity {

    EditText EditTxtNote;
    SharedPreferences notesPage = getApplicationContext().getSharedPreferences("Notes", Context.MODE_PRIVATE);

    int notenum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notepad);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                notenum++;
//                Save("Note1.txt");
//                EditTxtNote.setText("");
//            }
//        });

        EditTxtNote = (EditText) findViewById(R.id.noteEditText);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void Save(String fileName) {
        try {
            SharedPreferences notesPage = getApplicationContext().getSharedPreferences("Notes", Context.MODE_PRIVATE);
             notesPage.edit().putString(fileName, EditTxtNote.getText().toString()).commit();
            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
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
        SharedPreferences notesPage = getApplicationContext().getSharedPreferences("Notes", Context.MODE_PRIVATE);
        EditTxtNote.setText(notesPage.getString("Note1.txt", "DNE"));
        return true;

    case R.id.change_pass:
        Intent newerIntent = new Intent(Notepad.this, ChangPassword.class);
        Notepad.this.startActivity(newerIntent);


    default:
        return super.onOptionsItemSelected(item);
}
    }

}
