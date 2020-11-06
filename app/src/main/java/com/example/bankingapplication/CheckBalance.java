package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class CheckBalance extends AppCompatActivity {

    SQLiteDatabase db;
TextView bal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_balance);

        bal=findViewById(R.id.bal);
        db=openOrCreateDatabase("broker", Context.MODE_PRIVATE,null);

        Cursor c=db.rawQuery("select * from balance;",null);
int flag=0;
        while(c.moveToNext()){
            if(LoginActivity.loggedInUser.equals(c.getString(0))) {
                bal.setText("Your Balance is :\n"+c.getString(1));
                flag=1;
            }


        }

        if(flag==0){
            Toast.makeText(this, "No account found!", Toast.LENGTH_SHORT).show();
        }



    }
}
