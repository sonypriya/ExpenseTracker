package com.squareandcube.expensetracker;


import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.SharedElementCallback;
import android.support.v7.app.AlertDialog;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class SignIn extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    Database incomeDatabase;
    Database expenseDatabase;
    SQLiteDatabase incomedb,expensedb;
    private Button mSavingsButton,mExpenseButton,mIncomeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        incomeDatabase = new Database(this);
        expenseDatabase=new Database(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        mIncomeButton=(Button)findViewById(R.id.income_button);
        mExpenseButton=(Button)findViewById(R.id.expense_button);
        mSavingsButton=(Button)findViewById(R.id.savings_button);
        mSavingsButton.setOnClickListener(this);
        mIncomeButton.setOnClickListener(this);
        mExpenseButton.setOnClickListener(this);
        mSavingsButton.setOnClickListener(this);

    }

    @Override
    public void setEnterSharedElementCallback(SharedElementCallback callback) {
        super.setEnterSharedElementCallback(callback);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
         else {

            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent mIntentSettings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(mIntentSettings);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            startActivity(takePictureIntent);
        }
        else if (id == R.id.nav_gallery) {
            Intent i=new Intent(Intent.ACTION_PICK);
            i.setType("image/*");
            startActivity(i);


        } else if (id == R.id.nav_slideshow) {
            Intent i=new Intent(SignIn.this,SlideShowActivity.class);
            startActivity(i);
            finish();


        } else if (id == R.id.nav_audio) {
            Intent i=new Intent(SignIn.this,AudioRecorder.class);
            startActivity(i);


        } else if (id == R.id.nav_share) {

        }
        else if (id == R.id.nav_scancode) {
            Intent i =new Intent(SignIn.this,QrCodeScanning.class);
            startActivity(i);

        }
        else if (id == R.id.nav_logout) {
           // public void open(View view){
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Are you sure to logout?");
                        alertDialogBuilder.setPositiveButton("yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0, int arg1) {
                                        Intent i =new Intent(SignIn.this,LoginActivity.class);
                                        startActivity(i);
                                    }
                                });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void income(View view) {

        Intent i =new Intent(SignIn.this,IncomeActivity.class);
        startActivity(i);
    }




    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.income_button:
                Intent i=new Intent(SignIn.this,IncomeActivity.class);
                startActivity(i);
                break;

            case R.id.expense_button:
                Intent t =new Intent(SignIn.this,ExpenseActivity.class);
                startActivity(t);
                break;



            case R.id.savings_button:
                Intent mSavingActivity = new Intent(SignIn.this, Savings.class);
                String incomeData;
                incomedb=incomeDatabase.getReadableDatabase();
                Cursor incomeCursor=incomedb.rawQuery("select sum(income) from income", null);
                incomeCursor.moveToFirst();
                incomeData=incomeCursor.getString(0);

                String expenseData;
                expensedb=expenseDatabase.getReadableDatabase();
                Cursor expenseCursor=expensedb.rawQuery("select sum(expense) from expense", null);
                expenseCursor.moveToFirst();
                expenseData=expenseCursor.getString(0);

                Bundle b = new Bundle();
                b.putString("amount", String.valueOf(incomeData));
                b.putString("expenses",String.valueOf(expenseData));
                mSavingActivity.putExtras(b);
                startActivity(mSavingActivity);
                break;
        }
    }

    public void investmentNews(View view) {
        Intent i=new Intent(SignIn.this,InvestmentNewsActivity.class);
        startActivity(i);
    }
}
