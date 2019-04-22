package com.example.lowkey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView passcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        passcode = (TextView) findViewById(R.id.passcode);
        passcode.setText("");
    }

    public void onClick(View view)
    {
        Button b = (Button) view;
        if(b.getText().toString().toLowerCase().equals("submit")){
            login();
            passcode.setText("");
        }
        else {
            String pword = passcode.getText().toString();
            pword = pword.concat(b.getText().toString());
            passcode.setText(pword);
//        System.out.println(b.getText());
        }
    }

    private void login(){
        Context context = this.getApplicationContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        String correct_passcode = sharedPreferences.getString("password", "0000");
        if(passcode.getText().toString().equals(correct_passcode)){
            System.out.println("Correct password entered!");
            Intent intent = new Intent (this, Notepad.class);
            startActivity(intent);
        }
        else{
            Intent intent = new Intent(this, Dummy.class);
            startActivity(intent);
        }
    }
}
