package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 07-12-2017.
 */

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;


class IncomeAdapter extends ArrayAdapter<IncomeDataModel> {
    Context mCtx;
    int layoutRes;
    List<IncomeDataModel> incomeDataModelList;
    Database mDatabase;

    public IncomeAdapter(Context mCtx, int layoutRes, List<IncomeDataModel> incomeDataModelList, Database mDatabase) {
        super(mCtx, layoutRes, incomeDataModelList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.incomeDataModelList = incomeDataModelList;
        this.mDatabase = mDatabase;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewDisplayExpenseSource = view.findViewById(R.id.textViewDisplayIncomeSource);
        TextView textViewDisplayExpense = view.findViewById(R.id.textViewDisplayincome);
        TextView textViewDisplayDateAndTime = view.findViewById(R.id.textViewDisplayDateAndTimeincome);

        final IncomeDataModel expenseDataModel = incomeDataModelList.get(position);

        textViewDisplayExpenseSource.setText(expenseDataModel.getSource());
        textViewDisplayExpense.setText(String.valueOf(expenseDataModel.getincome()));
        textViewDisplayDateAndTime.setText(expenseDataModel.getIncomeAddDate());

        view.findViewById(R.id.buttonDeleteIncome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExpense(expenseDataModel);
            }
        });

        view.findViewById(R.id.buttonEditIncome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateIcome(expenseDataModel);
            }
        });

        return view;
    }

    private void updateIcome(final IncomeDataModel expenseDataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialogue_update_income, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextUpadateExpense = view.findViewById(R.id.editTextUpdateIncome);
        final Spinner spinnerUpdateExpenseSource = view.findViewById(R.id.spinnerUpadateExpenseSource);

        editTextUpadateExpense.setText(String.valueOf(expenseDataModel.getincome()));


        view.findViewById(R.id.buttonUpdateIncome).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String source = spinnerUpdateExpenseSource.getSelectedItem().toString().trim();
                String ExpenseData = editTextUpadateExpense.getText().toString().trim();


                if (ExpenseData.isEmpty()) {
                    editTextUpadateExpense.setError("ExpenseDataModel can't be empty");
                    editTextUpadateExpense.requestFocus();
                    return;
                }

                if (mDatabase.updateIncome(expenseDataModel.getId(),source, Double.valueOf(ExpenseData))) {
                    Toast.makeText(mCtx, "Expense Updated", Toast.LENGTH_SHORT).show();
                    loadExpenseFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteExpense(final IncomeDataModel expenseDataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mDatabase.deleteIncome(expenseDataModel.getId()))
                    loadExpenseFromDatabaseAgain();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadExpenseFromDatabaseAgain()
    {
        Cursor cursor = mDatabase.getAllIncome();
        incomeDataModelList.clear();
        if (cursor.moveToFirst())
        {
            do
            {
                incomeDataModelList.add(new IncomeDataModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getDouble(2)
                ));
            }
            while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}
