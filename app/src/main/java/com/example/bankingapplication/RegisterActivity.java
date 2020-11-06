package com.example.bankingapplication;

import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.net.URISyntaxException;

//import android.support.design.widget.TextInputLayout;


public class RegisterActivity extends AppCompatActivity {

    //TextInputLayout addressTextInputLayoutU,idTextInputLayoutU, nameTextInputLayoutU , unameTextInputLayoutU, passwordTextInputLayoutU,mobileTextInputLayoutU, emailTextInputLayoutU;
    EditText nameU,unameU, passwordU,mobileU,emailU,addressU,idU;
    Button registerU;
    SQLiteDatabase db;
    String name,uname,pass,med="",crime="",mob="",email="",add="";
    int id=0;
    Context context=this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar=getSupportActionBar();

        setContentView(R.layout.activity_register);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        try {

            db=openOrCreateDatabase("broker", Context.MODE_PRIVATE,null);
            db.execSQL("create table if not exists userpersonal(id int, name varchar, uname varchar, password varchar, address varchar,mob varchar,email varchar, valid varchar)");
            db.execSQL("create table if not exists balance(id int, balance varchar);");



        }catch (Exception e){
            Toast.makeText(this, "User Add==>"+e.toString(), Toast.LENGTH_SHORT).show();
        }

      //  idTextInputLayoutU = (TextInputLayout) findViewById(R.id.idTextInputLayoutUser);
        idU = (EditText) findViewById(R.id.idEditTextUser);

        //nameTextInputLayoutU = (TextInputLayout) findViewById(R.id.nameTextInputLayoutUser);
        nameU = (EditText) findViewById(R.id.nameEditTextUser);

        //unameTextInputLayoutU = (TextInputLayout) findViewById(R.id.UnameTextInputLayoutUser);
        unameU = (EditText) findViewById(R.id.unameEditTextUser);

        //passwordTextInputLayoutU = (TextInputLayout) findViewById(R.id.passwordTextInputLayoutUser);
        passwordU = (EditText) findViewById(R.id.passwordEditTextUser);


        //mobileTextInputLayoutU = (TextInputLayout) findViewById(R.id.mobileTextInputLayoutUser);
        mobileU = (EditText) findViewById(R.id.mobileEditTextUser);

        //emailTextInputLayoutU = (TextInputLayout) findViewById(R.id.emailTextInputLayoutUser);
        emailU = (EditText) findViewById(R.id.emailEditTextUser);

        //addressTextInputLayoutU = (TextInputLayout) findViewById(R.id.addTextInputLayoutUser);
        addressU = (EditText) findViewById(R.id.addEditTextUser);

        registerU = (Button) findViewById(R.id.regUser);
       // medRecU=(Button)findViewById(R.id.medicalUser);





        registerU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (validate(idU) && validate(nameU) && validate(unameU) && validate(passwordU)){
                    try{
                        name=nameU.getText().toString();
                        uname=unameU.getText().toString();
                        pass=passwordU.getText().toString();
                        id= Integer.parseInt(idU.getText().toString());
                        mob=mobileU.getText().toString();
                        email=emailU.getText().toString();
                        add=addressU.getText().toString();
                    }catch (Exception ex){
                        Toast.makeText(RegisterActivity.this, "e33==>"+ex.toString(), Toast.LENGTH_SHORT).show();
                    }

                    try {
                        db.execSQL("insert into userpersonal values('"+id+"','"+name+"','"+uname+"','"+pass+"','"+add+"','"+mob+"','"+email+"','"+"0"+"')");
                        db.execSQL("insert into balance values('"+id+"','"+"0"+"')");



                        Toast.makeText(RegisterActivity.this, "Successfully Inserted!!", Toast.LENGTH_SHORT).show();
                        nameU.setText("");
                        unameU.setText("");
                        passwordU.setText("");
                        addressU.setText("");
                        mobileU.setText("");
                        emailU.setText("");


                        Cursor c = db.rawQuery("select * from userpersonal", null);
                        while (c.moveToNext()) {

                            Toast.makeText(RegisterActivity.this, c.getString(0)+"==> " + c.getString(1) + "\n" + c.getString(2) + "\n" + c.getString(3), Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        Toast.makeText(RegisterActivity.this, "Insert==> "+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }

    private void alert() {

        AlertDialog.Builder alert = new AlertDialog.Builder(RegisterActivity.this);
        alert.setTitle("Insert Medical record:"); //Set Alert dialog title here
        alert.setMessage("Details:"); //Message here
        alert.setCancelable(false);

        // Set an EditText view to get user input
        final EditText input = new EditText(RegisterActivity.this);
        input.setHint("Enter details about medical record");
        alert.setView(input);


        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //You will get as string input data in this variable.
                // here we convert the input to a string and show in a toast.
                String srt = input.getEditableText().toString();
                Toast.makeText(RegisterActivity.this,srt, Toast.LENGTH_LONG).show();
            } // End of onClick(DialogInterface dialog, int whichButton)
        }); //End of alert.setPositiveButton
        alert.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                // Canceled.
                dialog.cancel();
            }
        }); //End of alert.setNegativeButton
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
       /* Alert Dialog Code End*/
    }

    private boolean validate(EditText editText) {
        if (editText.getText().toString().trim().length() > 0) {
            return true;
        }
        editText.requestFocus(); // set focus on fields
       // textInputLayout.setError("Please Fill This.!!!"); // set error message
        return false;
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {



        Uri Fpath = data.getData();
        Toast.makeText(this, "Path of File => "+Fpath, Toast.LENGTH_SHORT).show();
        try {
            String rp = getFilePath(context, Fpath);
            db.execSQL("insert into userdocs values('" + id + "','" + rp + "')");
            Toast.makeText(RegisterActivity.this, "Record Inserted Successfully!", Toast.LENGTH_SHORT).show();

        }catch (Exception e){

        }


    }

    @SuppressLint("NewApi")
    public static String getFilePath(Context context, Uri uri) throws URISyntaxException {
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


}
