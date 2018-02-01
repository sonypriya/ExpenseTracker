package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 08-12-2017.
 */

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayExpenseDetailsActivity extends AppCompatActivity {
    List<ExpenseDataModel> expenseDataModelList;
    ListView listView;
    Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_expense_details);

        mDatabase = new Database(this);

        expenseDataModelList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewExpenseDetail);

        loadIncomesFromDatabase();
    }

    private void loadIncomesFromDatabase() {
        Cursor cursor = mDatabase.getAllExpense();

        if (cursor.moveToFirst()) {
            do {
                expenseDataModelList.add(new ExpenseDataModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getDouble(2)
                ));
            } while (cursor.moveToNext());

            ExpenseAdapter adapter = new ExpenseAdapter(this, R.layout.expense_display_layout, expenseDataModelList, mDatabase);
            listView.setAdapter(adapter);
        }
    }
}

