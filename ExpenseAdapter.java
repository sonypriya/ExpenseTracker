package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 08-12-2017.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
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
class ExpenseAdapter extends ArrayAdapter<ExpenseDataModel> {
    Context mCtx;
    int layoutRes;
    List<ExpenseDataModel> expenseDataModelList;
    Database mDatabase;

    public ExpenseAdapter(Context mCtx, int layoutRes, List<ExpenseDataModel> expenseDataModelList, Database mDatabase) {
        super(mCtx, layoutRes, expenseDataModelList);

        this.mCtx = mCtx;
        this.layoutRes = layoutRes;
        this.expenseDataModelList = expenseDataModelList;
        this.mDatabase = mDatabase;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);

        View view = inflater.inflate(layoutRes, null);

        TextView textViewDisplayExpenseSource = view.findViewById(R.id.textViewDisplayExpenseSource);
        TextView textViewDisplayExpense = view.findViewById(R.id.textViewDisplayExpense);
        TextView textViewDisplayDateAndTime = view.findViewById(R.id.textViewDisplayDateAndTime);

        final ExpenseDataModel expenseDataModel = expenseDataModelList.get(position);

        textViewDisplayExpenseSource.setText(expenseDataModel.getSource());
        textViewDisplayExpense.setText(String.valueOf(expenseDataModel.getExpense()));
        textViewDisplayDateAndTime.setText(expenseDataModel.getExpenseAddDate());

        view.findViewById(R.id.buttonDeleteExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteExpense(expenseDataModel);
            }
        });

        view.findViewById(R.id.buttonEditExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateExpense(expenseDataModel);
            }
        });

        return view;
    }

    private void updateExpense(final ExpenseDataModel expenseDataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_expense, null);
        builder.setView(view);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final EditText editTextUpadateExpense = view.findViewById(R.id.editTextUpdateExpense);
        final Spinner spinnerUpdateExpenseSource = view.findViewById(R.id.spinnerUpadateExpenseSource);

        editTextUpadateExpense.setText(String.valueOf(expenseDataModel.getExpense()));


        view.findViewById(R.id.buttonUpdateExpense).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String source = spinnerUpdateExpenseSource.getSelectedItem().toString().trim();
                String ExpenseData = editTextUpadateExpense.getText().toString().trim();


                if (ExpenseData.isEmpty()) {
                    editTextUpadateExpense.setError("ExpenseDataModel can't be empty");
                    editTextUpadateExpense.requestFocus();
                    return;
                }

                if (mDatabase.updateExpense(expenseDataModel.getId(),source, Double.valueOf(ExpenseData))) {
                    Toast.makeText(mCtx, "Expense Updated", Toast.LENGTH_SHORT).show();
                    loadExpenseFromDatabaseAgain();
                }
                alertDialog.dismiss();
            }
        });
    }

    private void deleteExpense(final ExpenseDataModel expenseDataModel) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
        builder.setTitle("Are you sure?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                if (mDatabase.deleteExpxense(expenseDataModel.getId()))
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

    private void loadExpenseFromDatabaseAgain() {
        Cursor cursor = mDatabase.getAllExpense();

        expenseDataModelList.clear();
        if (cursor.moveToFirst()) {
            do {
                expenseDataModelList.add(new ExpenseDataModel(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(3),
                        cursor.getDouble(2)
                ));
            } while (cursor.moveToNext());
        }
        notifyDataSetChanged();
    }
}
