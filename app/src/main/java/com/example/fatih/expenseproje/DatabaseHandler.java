package com.example.fatih.expenseproje;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "expenseManager";
    private static final String TABLE_TAKIP = "takip";
    private static final String COLUMD_ID = "id";
    private static final String COLUMN_TIP = "tip";
    private static final String COLUMN_NOT= "note";
    private static final String COLUMN_MIKTAR = "miktar";
    private static final String COLUMN_GUN = "gun";
    private static final String COLUMN_AY = "ay";
    private static final String COLUMN_YIL = "yil";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_TAKIP + "("
                + COLUMD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_TIP + " INTEGER,"
                + COLUMN_NOT + " TEXT, "+ COLUMN_MIKTAR + " INTEGER, "+ COLUMN_GUN + " INTEGER,"
                + COLUMN_AY + " INTEGER, "+ COLUMN_YIL + " INTEGER " + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TAKIP);
        onCreate(db);
    }

    // Adding new contact
    public void addExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_TIP, String.valueOf(expense.getTip()));
        values.put(COLUMN_NOT, expense.getNot());
        values.put(COLUMN_MIKTAR, String.valueOf(expense.getMiktar()));
        values.put(COLUMN_GUN, String.valueOf(expense.getGun()));
        values.put(COLUMN_AY, String.valueOf(expense.getAy()));
        values.put(COLUMN_YIL, String.valueOf(expense.getYil()));
        db.insert(TABLE_TAKIP, null, values);
        db.close();

    }

    // Getting single contact
    public Expense getExpense(int tip,int ay) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_TAKIP, new String[] { COLUMN_TIP,
                        COLUMN_NOT, COLUMN_MIKTAR,COLUMN_AY }, COLUMN_TIP + "=?"+tip+" AND "+COLUMN_AY+"=?",
                new String[]{String.valueOf(tip),String.valueOf(ay)}, null, null, null,null);
        if (cursor != null)
            cursor.moveToFirst();

        Expense expense = new Expense(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),
                Integer.parseInt(cursor.getString(2)),
                Integer.parseInt(cursor.getString(3)));
        // return contact
        return expense;

    }

    public List<Expense> getAllExpenses() {
        List<Expense> expenseList = new ArrayList<Expense>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAKIP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                expense.setTip(Integer.parseInt(cursor.getString(1)));
                expense.setNot(cursor.getString(2));
                expense.setMiktar(Integer.parseInt(cursor.getString(3)));
                expense.setGun(Integer.parseInt(cursor.getString(4)));
                expense.setAy(Integer.parseInt(cursor.getString(5)));
                expense.setYil(Integer.parseInt(cursor.getString(6)));
                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        return expenseList;

    }

    public List<Expense> getSpecificExpenses(int tip,int ay) {
        List<Expense> expenseList = new ArrayList<Expense>();
        String selectQuery = "SELECT  * FROM " + TABLE_TAKIP+" WHERE tip="+String.valueOf(tip)+" AND ay="+String.valueOf(ay);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Expense expense = new Expense();
                expense.setId(Integer.parseInt(cursor.getString(0)));
                expense.setTip(Integer.parseInt(cursor.getString(1)));
                expense.setNot(cursor.getString(2));
                expense.setMiktar(Integer.parseInt(cursor.getString(3)));
                expense.setGun(Integer.parseInt(cursor.getString(4)));
                expense.setAy(Integer.parseInt(cursor.getString(5)));
                expense.setYil(Integer.parseInt(cursor.getString(6)));

                expenseList.add(expense);
            } while (cursor.moveToNext());
        }

        // return contact list
        return expenseList;

    }
    public int getDifferenceExpenses(int ay) {
        int s1=0;
        int s2=0;
        String sorgu1="SELECT SUM("+COLUMN_MIKTAR+") FROM "+TABLE_TAKIP+" WHERE ay=? AND tip=?"+" LIMIT 1";
        String sorgu2="SELECT SUM("+COLUMN_MIKTAR+") FROM "+TABLE_TAKIP+" WHERE ay=? AND tip=? LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(sorgu1, new String[]{String.valueOf(ay),"2131427416"});
        Cursor cursor2 = db.rawQuery(sorgu2, new String[]{String.valueOf(ay),"2131427417"});
            if (cursor1.moveToFirst()) {
                s1 = cursor1.getInt(0);
                Log.d("S1: ",""+s1);
            }else {
                s1=0;
            }
            if (cursor2.moveToFirst()) {
                s2 = cursor2.getInt(0);
                Log.d("S2: ",""+s2);
            } else {
                s2=0;
            }
        cursor1.close();
        cursor2.close();
        return s1-s2;

    }
    public int getAylikGelirToplam(int ay){
        int gelir=0;
        String sorgu1="SELECT SUM("+COLUMN_MIKTAR+") FROM "+TABLE_TAKIP+" WHERE ay=? AND tip=?"+" LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor1 = db.rawQuery(sorgu1, new String[]{String.valueOf(ay),"2131427416"});
        if (cursor1.moveToFirst()) {
            gelir = cursor1.getInt(0);
            Log.d("Aylık Geliri : ",""+gelir);
        }
        cursor1.close();
        return gelir;

    }
    public int getAylikGiderToplam(int ay){
        int gider=0;
        String sorgu2="SELECT SUM("+COLUMN_MIKTAR+") FROM "+TABLE_TAKIP+" WHERE ay=? AND tip=? LIMIT 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor2 = db.rawQuery(sorgu2, new String[]{String.valueOf(ay),"2131427417"});
        if (cursor2.moveToFirst()) {
            gider = cursor2.getInt(0);
            Log.d("Aylik Gideri : ",""+gider);
        }
        cursor2.close();
        return gider;
    }


    // Getting contacts Count
    public int getExpensesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TAKIP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count=cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }
    // Updating single contact
    public int updateContact(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_MIKTAR, expense.getMiktar());
        values.put(COLUMN_NOT, expense.getNot());

        // updating row
        return db.update(TABLE_TAKIP, values, COLUMD_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) });

    }

    // Deleting single contact
    public void deleteExpense(Expense expense) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TAKIP, COLUMD_ID + " = ?",
                new String[] { String.valueOf(expense.getId()) });
        db.close();

    }
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
}
