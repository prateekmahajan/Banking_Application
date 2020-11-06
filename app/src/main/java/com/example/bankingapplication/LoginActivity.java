package com.example.bankingapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

//import android.support.design.widget.TextInputLayout;

public class LoginActivity extends AppCompatActivity {

    //TextInputLayout emailTextInputLayoutA, passwordTextInputLayoutA;
    EditText emailA, passwordA;
    Button signInA,regA,forA,h;
    public static String loggedInUser;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
// get the reference of View's
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        db=openOrCreateDatabase("broker", Context.MODE_PRIVATE,null);
       emailA = (EditText) findViewById(R.id.emailEditText);
        passwordA = (EditText) findViewById(R.id.passwordEditText);
        signInA = (Button) findViewById(R.id.signInButton);
        regA = (Button) findViewById(R.id.registerButton);
        forA = (Button) findViewById(R.id.forget);






        regA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
            }
        });

        forA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailA.getText().toString().equals("")) {
                    emailA.setError("Enter Username");
                } else {


                    Cursor c = db.rawQuery("select * from userpersonal", null);
                    while (c.moveToNext()) {
                        if (emailA.getText().toString().equals(c.getString(2))) {

                            loggedInUser = emailA.getText().toString();
                            Toast.makeText(LoginActivity.this, "Password is => "+c.getString(3), Toast.LENGTH_SHORT).show();


                        }

                    }


                }
            }
        });



        signInA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate(emailA) && validate(passwordA)) {
//  display a Thank You message

                    verify(emailA.getText().toString(),passwordA.getText().toString()) ;



                }

            }
        });
    }

    private void verify(String uname, String pass) {
        int flag=0,flag1=0;
        Cursor c=db.rawQuery("select * from userpersonal",null);
        while(c.moveToNext()){
            if(uname.equals(c.getString(2))&& pass.equals(c.getString(3))){
                flag=1;
                loggedInUser=c.getString(0);
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();



            }
        }

        if(flag==0){

            Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
        }


    }

    private void alert() {

        try {
            AlertDialog.Builder b1 = new AlertDialog.Builder(LoginActivity.this);
            b1.setTitle("Invalid credentials");
            b1.setMessage("Please try again with correct credentials!");
            b1.setCancelable(true);
            b1.setIcon(R.drawable.invalid);
            b1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    emailA.setText("");
                    passwordA.setText("");
                    dialogInterface.cancel();
                }
            });

            AlertDialog a = b1.create();
            a.show();
        }catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "E2==>"+e.toString(), Toast.LENGTH_SHORT).show();

        }



    }

    // validate fields
    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.requestFocus(); // set focus on fields
        // textInputLayout.setError("Please Fill This.!!!"); // set error message
        return false;
    }

}
