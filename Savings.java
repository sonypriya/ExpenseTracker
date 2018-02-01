package com.squareandcube.expensetracker;

import android.content.ActivityNotFoundException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.content.Intent;
import android.net.Uri;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;


public class Savings extends AppCompatActivity
{

    TextView totalIncome,totalExpenses,totalSavings;
    Button mSavings;
    Double income,expenses,total;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_savings, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId())
        {
                case R.id.gmail:
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                emailIntent.setType("vnd.android.cursor.item/email");
                emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {"sony21priya@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "My Email Subject");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My email content");
                startActivity(Intent.createChooser(emailIntent, "Send mail using..."));
        }
                return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        totalIncome=(TextView)findViewById(R.id.total_income_edittext);
        totalExpenses=(TextView)findViewById(R.id.total_expense_edittext);
        totalSavings=(TextView)findViewById(R.id.result_edittext);

        Bundle b=getIntent().getExtras();
        totalIncome.setText(b.getCharSequence("amount"));
        totalExpenses.setText(b.getCharSequence("expenses"));


        mSavings=(Button)findViewById(R.id.total_save_button);
        mSavings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                income = Double.parseDouble(totalIncome.getText().toString());
                expenses = Double.parseDouble(totalExpenses.getText().toString());
                total = income-expenses;
                totalSavings.setText(Double.toString(total));

            }
        });


    }

}

