package com.example.bankingapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class WithdrawActivity extends AppCompatActivity {

    EditText bal;
    Button d;
    SQLiteDatabase db;
    int old=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);
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

    int balance = old - newb;
    if(balance<0){
        Toast.makeText(WithdrawActivity.this, "Insufficient Balance!", Toast.LENGTH_SHORT).show();
    }else{
        db.execSQL("update balance set balance='" + balance + "' where id='" + LoginActivity.loggedInUser + "'");
        Toast.makeText(WithdrawActivity.this, "Amount Withdrawn Successfully!!", Toast.LENGTH_SHORT).show();
        finish();
    }


}catch (Exception e){
    Toast.makeText(WithdrawActivity.this, "error=> "+e.toString(), Toast.LENGTH_SHORT).show();
}

            }
        });
    }
}
