package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 07-12-2017.
 */

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class DisplayIncomeDetailsActivity extends AppCompatActivity {
    List<IncomeDataModel> incomeDataModelList;
    ListView listView;
    Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_income_details);

        mDatabase = new Database(this);
        incomeDataModelList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listViewIncomeDetail);

        loadIncomesFromDatabase();
    }

    private void loadIncomesFromDatabase() {
        Cursor cursor = mDatabase.getAllIncome();

        if (cursor.moveToFirst()) {
            do {
                incomeDataModelList.add(new IncomeDataModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getDouble(2)
                ));
            } while (cursor.moveToNext());

            IncomeAdapter adapter = new IncomeAdapter(this, R.layout.income_display_layout, incomeDataModelList, mDatabase);
            listView.setAdapter(adapter);
        }
    }
}

