package com.example.myapplication22;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.mindrot.jbcrypt.BCrypt;

public class DB extends SQLiteOpenHelper {

    public DB(@Nullable Context context) {

        super(context, "2database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE users(firstName TEXT, lastName TEXT, email TEXT PRIMARY KEY, password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop table if exists users");
        onCreate(db);
    }

    public boolean insertData(String firstName, String lastName, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        String hashPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        contentValues.put("firstName", firstName);
        contentValues.put("lastName", lastName);
        contentValues.put("email", email);
        contentValues.put("password", hashPassword);

        long result = db.insert("users", null, contentValues);
        return result != -1;

    }

    public boolean validateUser(String email, String password) {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select password from users where email=?", new String[]{email});

        if (cursor.moveToFirst()) {
            String hashed = cursor.getString(0);
            cursor.close();
            return BCrypt.checkpw(password, hashed);
        }
        cursor.close();
        return false;

    }

    public boolean checkEmail(String email) {

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("Select email from users where email=?", new String[]{email});

        return cursor.getCount() > 0;
    }



    public boolean deleteUser(String email){
        SQLiteDatabase db=this.getWritableDatabase();
        int row=db.delete("users","email=?",new String[]{email});
        return row>0;
    }
}
