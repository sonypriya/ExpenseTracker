package com.squareandcube.expensetracker;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    Button mSave;
    private final AppCompatActivity activity = LoginActivity.this;

    private ScrollView scrollView;
    private EditText textInputEditTextEmail;
    private EditText textInputEditTextPassword;

    private Button buttonLogin;
    private Button buttonRegistration;

    private TextView textViewLinkForgetPassword;
    private AppCompatTextView textViewLinkHelp;

    private InputValidation inputValidation;
    private Database registrationDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        initListeners();
        initObjects();
    }
    private void initViews() {
        scrollView = (ScrollView) findViewById(R.id.Login_ScrollView);

        textInputEditTextEmail = (EditText) findViewById(R.id.username_EditText);
        textInputEditTextPassword = (EditText) findViewById(R.id.password_EditText);

        buttonLogin = (Button) findViewById(R.id.sign_in_Button);
        buttonRegistration=(Button)findViewById(R.id.Registration_Button);
        textViewLinkForgetPassword = (TextView) findViewById(R.id.Password_TextView);

    }

    private void initListeners() {
        buttonLogin.setOnClickListener(this);
        buttonRegistration.setOnClickListener(this);
       textViewLinkForgetPassword.setOnClickListener(this);
        //textViewLinkHelp.setOnClickListener(this);;
    }
    private void initObjects() {
        registrationDatabaseHelper = new Database(activity);
        inputValidation = new InputValidation(activity);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_Button:
                verifyFromSQLite();
                break;

            // Navigate to RegistrationActivity
            case R.id.Registration_Button:
                Intent intentRegister = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.Password_TextView:
                Intent intentForgetPassword=new Intent(getApplicationContext(),ForgetPasswordActivity.class);
                intentForgetPassword.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                emptyInputEditText();
                startActivity(intentForgetPassword);
                break;


        }
    }

    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail,  getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(textInputEditTextEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword,  getString(R.string.error_message_password_text))) {
            return;
        }

        if (registrationDatabaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            Intent accountsIntent = new Intent(activity, SignIn.class);
            startActivity(accountsIntent);

        }
        else
            {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(scrollView, R.string.error_valid_email_password_text, Snackbar.LENGTH_LONG).show();
        }
    }
    private void emptyInputEditText()
    {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}

