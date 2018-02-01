package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 07-12-2017.
 */

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class IncomeActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mEditTextIncome;
    Spinner mSpinnerIncomeSource;
    Button mButtonAddIncome, mButtonViewAllIncome;
    Database mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income1);
        mDatabase = new Database(this);
        mEditTextIncome = (EditText) findViewById(R.id.editTextIncome);
        mSpinnerIncomeSource = (Spinner) findViewById(R.id.spinnerIncomeSource);
        mButtonAddIncome = (Button) findViewById(R.id.buttonAddIncome);
        mButtonViewAllIncome = (Button) findViewById(R.id.buttonViewAllIncome);
        mButtonAddIncome.setOnClickListener(this);
        mButtonViewAllIncome.setOnClickListener(this);


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonAddIncome:
                mAddIncome();
                break;
            case R.id.buttonViewAllIncome:
                startActivity(new Intent(this, DisplayIncomeDetailsActivity.class));
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void mAddIncome()
    {
        String Expense = mEditTextIncome.getText().toString().trim();
        String source = mSpinnerIncomeSource.getSelectedItem().toString();
        if (Expense.isEmpty())
        {
            mEditTextIncome.setError("ExpenseDataModel can't be empty");
            mEditTextIncome.requestFocus();
            return;
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String joiningDate = sdf.format(cal.getTime());

        if (mDatabase.addIncome(source, Double.parseDouble(Expense),joiningDate))
            Toast.makeText(this, "Income Added Successfully", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "Could not add Income", Toast.LENGTH_SHORT).show();
    }

}

