package com.example.lowkey;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangPassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_password);

        final Button passBtn = (Button)findViewById(R.id.passButton);
        passBtn.setOnClickListener(new View.OnClickListener() {
            EditText editPass = (EditText)findViewById(R.id.passEditText);
            @Override
            public void onClick(View v) {
                String newPassCode = editPass.getText().toString();
                if (newPassCode.length() ==4 )
                {
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("settings", Context.MODE_PRIVATE);

                     sharedPreferences.edit().putString("password", newPassCode ).commit();
                    Toast.makeText(getApplicationContext(), "New passcode set!", Toast.LENGTH_SHORT).show();

                    Intent newerIntent = new Intent(ChangPassword.this, Notepad.class);
                    ChangPassword.this.startActivity(newerIntent);                }
                else {
                    Toast.makeText(getApplicationContext(), "4 DIGITS ONLY! TRY AGAIN!", Toast.LENGTH_SHORT).show();


                }
            }
        });


    }


}
