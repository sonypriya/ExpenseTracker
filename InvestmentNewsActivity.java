package com.squareandcube.expensetracker;

/**
 * Created by Sony priya on 12-12-2017.
 */

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.net.ConnectivityManager;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

    public class InvestmentNewsActivity extends AppCompatActivity {
    private WebView mWebNews;
    private ProgressBar mProgressBar;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.InvestmentprogressBar);
        final ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Please wait......");
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.show();

        mWebNews = (WebView) findViewById(R.id.InvestmentwebNews);
        WebSettings webSettings = mWebNews.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        mWebNews.loadUrl("http://www.moneycontrol.com/");
        mWebNews.setWebViewClient(new WebViewClient());
        mWebNews.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                if (progress.isShowing()) {
                    progress.dismiss();
                }
                Log.d("Website", "onPageFinished");

            }

        });

    }

    @Override
    public void onBackPressed() {
        if (mWebNews.canGoBack()) {
            mWebNews.goBack();
        } else
            {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_call:
                Intent mCallIntent = new Intent(Intent.ACTION_DIAL);
                startActivity(mCallIntent);
                break;

            case R.id.action_message:
                Intent mMesageIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("sms:"));
                startActivity(mMesageIntent);
                break;
            case R.id.action_networkstatus:
                checkConnection();


        }
        return super.onOptionsItemSelected(item);
    }
    protected boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        else
            {
            return false;
        }
    }
    public void checkConnection(){
        if(isOnline()){
            ImageView image = new ImageView(this);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setIcon(R.drawable.ic_internet_success);
            alertDialogBuilder.setTitle("INTERNET CONNECTION");
            alertDialogBuilder.setMessage("yeah! Internet Found!");
            alertDialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        }
        else
            {
            AlertDialog.Builder alertDialogBuilder1 = new AlertDialog.Builder(this);
            alertDialogBuilder1.setIcon(R.drawable.ic_internet_fail);
            alertDialogBuilder1.setTitle("INTERNET CONNECTION");
            alertDialogBuilder1.setMessage(" Internet  not Found!");
            alertDialogBuilder1.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent mIntentSettings = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(mIntentSettings);
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = alertDialogBuilder1.create();
            alertDialog.show();
        }
    }

}
