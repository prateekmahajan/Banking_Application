package com.example.bankingapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DepositeActivity extends AppCompatActivity {

    EditText bal;
    Button d;
    SQLiteDatabase db;
    int old=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposite);
        bal=findViewById(R.id.amt);
        d=findViewById(R.id.deposite);

        db=openOrCreateDatabase("broker", Context.MODE_PRIVATE,null);
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c=db.rawQuery("select * from balance;",null);
                while(c.moveToNext()){

                    if(c.getString(0).equals(LoginActivity.loggedInUser)){
                        old=Integer.parseInt(c.getString(1));
                    }
                }
try {
    String newa = bal.getText().toString();
    int newb = Integer.parseInt(newa);

    int balance = old + newb;

    db.execSQL("update balance set balance='" + balance + "' where id='" + LoginActivity.loggedInUser + "'");
    Toast.makeText(DepositeActivity.this, "Amount Deposited Successfully!!", Toast.LENGTH_SHORT).show();
    finish();
}catch (Exception e){
    Toast.makeText(DepositeActivity.this, "error=> "+e.toString(), Toast.LENGTH_SHORT).show();
}

            }
        });
    }
}
