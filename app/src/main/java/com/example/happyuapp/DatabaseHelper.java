package com.example.happyuapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.slider.Slider;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "login.db";
    public static final String DATABASE_NAME3 = "happyu.db";
    public static final int DATABASE_VERSION = 1;
    public static final int DATABASE_VERSION5 = 4;

    // Table pour stocker les comptes
    public static final String TABLE_COMPTES = "comptes";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";

    public static final String TABLE_MOOD = "mood";
    public static final String COLUMN_MOOD_ID = "mood_id";
    public static final String COLUMN_MOOD_SCORE1 = "mood_score1";
    public static final String COLUMN_MOOD_SCORE2 = "mood_score2";
    public static final String COLUMN_MOOD_SCORE3 = "mood_score3";
    public static final String COLUMN_MOOD_SCORE4 = "mood_score4";
    public static final String COLUMN_MOOD_SCORE5 = "mood_score5";
    public static final String COLUMN_MOOD_SCORE6 = "mood_score6";

    public static final String COLUMN_MOOD_DATE = "mood_date";
    public static final String COLUMN_MOOD_ACCOUNT_EMAIL = "account_email";



    private static final String CREATE_MOOD_TABLE = "CREATE TABLE " + TABLE_MOOD + " (" +
            COLUMN_MOOD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_MOOD_SCORE1 + " TEXT, " +
            COLUMN_MOOD_SCORE2 + " TEXT, " +
            COLUMN_MOOD_SCORE3 + " TEXT, " +
            COLUMN_MOOD_SCORE4 + " TEXT, " +
            COLUMN_MOOD_SCORE5 + " TEXT, " +
            COLUMN_MOOD_SCORE6 + " TEXT, " +
            COLUMN_MOOD_DATE + " TEXT, " +
            COLUMN_MOOD_ACCOUNT_EMAIL + " TEXT, "+
            "FOREIGN KEY(" + COLUMN_MOOD_ACCOUNT_EMAIL + ") REFERENCES " + TABLE_COMPTES + "(" + COLUMN_EMAIL + "))";




    // Requête SQL pour créer la table des comptes
    private static final String CREATE_COMPTES_TABLE = "CREATE TABLE " + TABLE_COMPTES + " (" +
            COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
            COLUMN_PASSWORD + " TEXT)";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME3, null, DATABASE_VERSION5);
    }

    public DatabaseHelper(View.OnClickListener onClickListener) {
        super((Context) onClickListener, DATABASE_NAME3, null, DATABASE_VERSION5);
    }

    public DatabaseHelper(FirstRegisterFragment firstRegisterFragment) {
        super(firstRegisterFragment.getContext(), DATABASE_NAME3, null, DATABASE_VERSION5);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_COMPTES_TABLE);
        db.execSQL(CREATE_MOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOD);
        onCreate(db);
    }

    // Méthode pour insérer un compte dans la table des comptes
    public boolean insertCompte(String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_EMAIL, email);
        contentValues.put(COLUMN_PASSWORD, password);
        long result = db.insert(TABLE_COMPTES, null, contentValues);
        return result != -1;
    }

    // Méthode pour insérer une humeur dans la table "Mood"
    public boolean insertMood(float moodScore1, String moodScore2, String moodScore3, String moodScore4, String moodScore5, String moodScore6, String moodDate) {
        String currentLoggedInEmail = CustomAuthManager.getCurrentLoggedInEmail();
        if (currentLoggedInEmail == null) {
            // Gérer le cas où aucun utilisateur n'est connecté
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_MOOD_ACCOUNT_EMAIL, currentLoggedInEmail); // Associer l'email du compte connecté
        contentValues.put(COLUMN_MOOD_SCORE1, moodScore1);
        contentValues.put(COLUMN_MOOD_SCORE2, moodScore2);
        contentValues.put(COLUMN_MOOD_SCORE3, moodScore3);
        contentValues.put(COLUMN_MOOD_SCORE4, moodScore4);
        contentValues.put(COLUMN_MOOD_SCORE5, moodScore5);
        contentValues.put(COLUMN_MOOD_SCORE6, moodScore6);
        contentValues.put(COLUMN_MOOD_DATE, moodDate);
        long result = db.insert(TABLE_MOOD, null, contentValues);
        return result != -1;
    }

    // Méthode pour récupérer les humeurs d'un compte spécifique
    public Cursor getMoods(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MOOD + " WHERE " + COLUMN_MOOD_ACCOUNT_EMAIL + " = ?";
        return db.rawQuery(query, new String[]{email});
    }

    // Méthode pour vérifier les informations de connexion
    public boolean checkLoginCredentials(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_EMAIL,
                COLUMN_PASSWORD
        };

        String selection = COLUMN_EMAIL + " = ? AND " +
                COLUMN_PASSWORD + " = ?";

        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                TABLE_COMPTES,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        boolean isValid = cursor.getCount() > 0;

        cursor.close();
        db.close();

        return isValid;
    }
}