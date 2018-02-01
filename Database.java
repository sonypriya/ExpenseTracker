package com.squareandcube.expensetracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import static com.squareandcube.expensetracker.Database.expense_table.COLUMN_DATE_TIME;
import static com.squareandcube.expensetracker.Database.expense_table.COLUMN_EXPENSE;
import static com.squareandcube.expensetracker.Database.expense_table.COLUMN_ID;
import static com.squareandcube.expensetracker.Database.expense_table.COLUMN_SOURCE;
import static com.squareandcube.expensetracker.Database.expense_table.TABLE_NAME_expense;
import static com.squareandcube.expensetracker.Database.income_table.COLUMN_DATE_TIME1;
import static com.squareandcube.expensetracker.Database.income_table.COLUMN_ID1;
import static com.squareandcube.expensetracker.Database.income_table.COLUMN_INCOME1;
import static com.squareandcube.expensetracker.Database.income_table.COLUMN_SOURCE1;
import static com.squareandcube.expensetracker.Database.income_table.TABLE_NAME_income;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_DATE_OF_BIRTH;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_EMAIL;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_FIRST_NAME;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_GENDER;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_ID;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_LAST_NAME;
import static com.squareandcube.expensetracker.Database.user_table.COLUMN_USER_PASSWORD;
import static com.squareandcube.expensetracker.Database.user_table.TABLE_USER;


/**
 * Created by Sony priya on 15-12-2017.
 */

public class Database extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION =1;
    private static final String DataBase_Name = "mDatabase";


    public static abstract class expense_table {
        static final String TABLE_NAME_expense = "expense";
        static final String COLUMN_ID = "id";
        static final String COLUMN_SOURCE = "espensesource";
        static final String COLUMN_DATE_TIME = "joiningdate";
        static final String COLUMN_EXPENSE = "expense";

    }

    public static abstract class income_table
    {
        static final String TABLE_NAME_income = "income";
        static final String COLUMN_ID1 = "id";
        static final String COLUMN_SOURCE1 = "incomesource";
        static final String COLUMN_DATE_TIME1 = "joiningdate";
        static final String COLUMN_INCOME1 = "income";

    }
    public static abstract class user_table
    {
         static final String TABLE_USER = "user";
         static final String COLUMN_USER_ID = "user_id";
         static final String COLUMN_USER_FIRST_NAME = "user_firstname";
         static final String COLUMN_USER_LAST_NAME = "user_lastname";
         static final String COLUMN_USER_DATE_OF_BIRTH = "user_dateofbirth";
         static final String COLUMN_USER_GENDER = "user_gender";
         static final String COLUMN_USER_EMAIL = "user_email";
         static final String COLUMN_USER_PASSWORD = "user_password";

    }

    public Database(Context context) {
        super(context, DataBase_Name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE " + TABLE_NAME_expense + " (\n" +
                "    " + COLUMN_ID + " INTEGER NOT NULL CONSTRAINT income_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_SOURCE + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_EXPENSE + " double NOT NULL,\n" +
                "    " + COLUMN_DATE_TIME + " datetime NOT NULL\n" +
                ");";
        db.execSQL(sql);


        sql = "CREATE TABLE " + TABLE_NAME_income + " (\n" +
                "    " + COLUMN_ID1 + " INTEGER NOT NULL CONSTRAINT income_pk PRIMARY KEY AUTOINCREMENT,\n" +
                "    " + COLUMN_SOURCE1 + " varchar(200) NOT NULL,\n" +
                "    " + COLUMN_INCOME1 + " double NOT NULL,\n" +
                "    " + COLUMN_DATE_TIME1 + " datetime NOT NULL\n" +
                ");";
         db.execSQL(sql);



        sql =  "CREATE TABLE " + TABLE_USER + " ("
                + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_USER_FIRST_NAME + " TEXT," +
                  COLUMN_USER_LAST_NAME + " TEXT," +
                  COLUMN_USER_DATE_OF_BIRTH + " TEXT," +
                  COLUMN_USER_GENDER + " TEXT,"
                + COLUMN_USER_EMAIL + " TEXT," +
                  COLUMN_USER_PASSWORD + " TEXT" + ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_expense + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

        sql = "DROP TABLE IF EXISTS " + TABLE_NAME_income + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);

        sql = "DROP TABLE IF EXISTS " + TABLE_USER + ";";
        sqLiteDatabase.execSQL(sql);
        onCreate(sqLiteDatabase);


    }

    boolean addExpense(String dept, double expense, String joiningdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOURCE, dept);
        contentValues.put(COLUMN_EXPENSE, expense);
        contentValues.put(COLUMN_DATE_TIME, joiningdate);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME_expense, null, contentValues) != -1;
    }

    Cursor getAllExpense()
    {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_expense, null);
    }
    boolean updateExpense(int id, String source, double salary)
    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOURCE, source);
        contentValues.put(COLUMN_EXPENSE, salary);
        return db.update(TABLE_NAME_expense, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    boolean deleteExpxense(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_expense, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    boolean addIncome(String dept, double income, String joiningdate) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOURCE1, dept);
        contentValues.put(COLUMN_INCOME1, income);
        contentValues.put(COLUMN_DATE_TIME1, joiningdate);
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(TABLE_NAME_income, null, contentValues) != -1;
    }

    Cursor getAllIncome() {
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_income, null);
    }
    boolean updateIncome(int id, String source, double salary) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_SOURCE1, source);
        contentValues.put(COLUMN_INCOME1, salary);
        return db.update(TABLE_NAME_income, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }
    boolean deleteIncome(int id)
    {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_income, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstname());
        values.put(COLUMN_USER_LAST_NAME, user.getLastname());
        values.put(COLUMN_USER_DATE_OF_BIRTH, user.getDateofbirth());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    /**
     * This method is to fetch all user and return the list of user records
     *
     * @return list
     */
    public List<User> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_FIRST_NAME,
                COLUMN_USER_LAST_NAME,
                COLUMN_USER_DATE_OF_BIRTH,
                COLUMN_USER_GENDER,
                COLUMN_USER_PASSWORD
        };
        // sorting orders
        String sortOrder =
                COLUMN_USER_FIRST_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        // query the user table
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id,user_name,user_email,user_password FROM user ORDER BY user_name;
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,    //columns to return
                null,        //columns for the WHERE clause
                null,        //The values for the WHERE clause
                null,       //group the rows
                null,       //filter by row groups
                sortOrder); //The sort order


        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setFirstname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_FIRST_NAME)));
                user.setLastname(cursor.getString(cursor.getColumnIndex(COLUMN_USER_LAST_NAME)));
                user.setDateofbirth(cursor.getString(cursor.getColumnIndex(COLUMN_USER_DATE_OF_BIRTH)));
                user.setGender(cursor.getString(cursor.getColumnIndex(COLUMN_USER_GENDER)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }

    /**
     * This method to update user record
     *
     * @param user
     */
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_FIRST_NAME, user.getFirstname());
        values.put(COLUMN_USER_LAST_NAME, user.getLastname());
        values.put(COLUMN_USER_DATE_OF_BIRTH, user.getDateofbirth());
        values.put(COLUMN_USER_GENDER, user.getGender());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method is to delete user record
     *
     * @param user
     */
    public void deleteUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0)
        {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public boolean checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

}
