package com.e.database_registration;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.e.database_registration.database.StoreDatabase;
import com.example.registrationform.database.StoreDatabase;

import static com.example.registrationform.database.StoreDatabase.COLUMN_USERNAME;
import static com.example.registrationform.database.StoreDatabase.COLUMN_USER_PASSWORD;
import static com.example.registrationform.database.StoreDatabase.COLUMN_USER_PHONE;
import static com.example.registrationform.database.StoreDatabase.TABLE_USERS;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private StoreDatabase storeDatabase;
    private SQLiteDatabase sqbd;

    EditText et_phone;
    EditText et_password;
    Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("Login Form");

        storeDatabase = new StoreDatabase(this);
        sqbd = storeDatabase.getWritableDatabase();

        et_phone = findViewById(R.id.et_phone);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(TextUtils.isEmpty(et_phone.getText())){
            et_phone.setError("Fill again");
        }
        if(TextUtils.isEmpty(et_password.getText())){
            et_password.setError("Fill again");
        }

        Cursor userCursor = sqbd.rawQuery(" SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USER_PHONE + " =? AND " + COLUMN_USER_PASSWORD + " =? ", new String[]{et_phone.getText().toString(), et_password.getText().toString()});

        if (((userCursor != null) && (userCursor.getCount() > 0))){
            userCursor.moveToFirst();
            String userName = userCursor.getString(userCursor.getColumnIndex(COLUMN_USERNAME));
            Toast.makeText(this, "User is here! Welcome, " + userName + "!", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Invalid User!", Toast.LENGTH_SHORT).show();
        }

        Toast.makeText(this, "Inserted to Database", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
