package com.squareandcube.expensetracker;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;


public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener{
    TextView mMember;
    private ScrollView scrollView;
    private EditText mDateOfBirth;
    private int mDay;
    private Calendar mCalendar;
    private int mMonth;
    private int mYear;
    private EditText mFirstName;
    private EditText mLastName;
    private EditText mEmail;
    private EditText mMobileNumber;
    private EditText mPassword;
    private EditText mConfirmPassword;
    private RadioGroup mGender;
    private RadioButton mMale;
    private RadioButton mFemale;
    private Button mSaveButton;
    private TextView appCompatTextViewLoginLink;
    private InputValidation inputValidation;
    private Database registrationDatabaseHelper;
    private User user;
    private TextView mAlready;
    private final AppCompatActivity activity=RegistrationActivity.this;
    DatePickerDialog datePickerDialog;
    EditText date;
    final int PICK_IMAGE_REQUEST = 2;
    private static final String TAG = "GalleryUtil";
    private TextView mGoToLoginTextView;
    private Button mButtonSave;
    private DatePickerDialog mDatePickerDialog;
    private CircleImageView mRegistrationProfileImage;
    private static final int REQUEST = 112;
    private String Gender;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
      //  getSupportActionBar().hide();

        initViews();
        initListeners();
        initObjects();


        if (Build.VERSION.SDK_INT >= 23) {
            String[] PERMISSIONS = {android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };
           if (!Utils.hasPermissions(this, PERMISSIONS)) {
               ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST);
           }

        }
        mGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                Gender = radioButton.getText().toString().trim();
            }
        });



        mDateOfBirth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    if (event.getRawX() >= (mDateOfBirth.getRight() - mDateOfBirth.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        final Calendar c = Calendar.getInstance();
                        int mYear = c.get(Calendar.YEAR);
                        int mMonth = c.get(Calendar.MONTH);
                        int mDay = c.get(Calendar.DAY_OF_MONTH);
                        datePickerDialog = new DatePickerDialog(RegistrationActivity.this,
                                new DatePickerDialog.OnDateSetListener() {

                                    @Override
                                    public void onDateSet(DatePicker view, int year,
                                                          int monthOfYear, int dayOfMonth) {

                                        mDateOfBirth.setText(dayOfMonth + "/"
                                                + (monthOfYear + 1) + "/" + year);

                                    }
                                }, mYear, mMonth, mDay);

                        Calendar maxDate = Calendar.getInstance();
                        datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
                        datePickerDialog.show();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {
        scrollView = (ScrollView) findViewById(R.id.Reg_ScrollView);
        mFirstName = (EditText) findViewById(R.id.name_EditText);
        mLastName = (EditText) findViewById(R.id.Lastname_EditText);
        mEmail = (EditText) findViewById(R.id.Email_EditText);
        mMobileNumber = (EditText) findViewById(R.id.Mobile_EditText);
        mPassword = (EditText) findViewById(R.id.Reg_Password_EditText);
        mConfirmPassword = (EditText) findViewById(R.id.Reg_ConfirmPassword_EditText);
        mGender = (RadioGroup) findViewById(R.id.Gender_RadioButton);
        mDateOfBirth = (EditText) findViewById(R.id.DateOfBirth_EditText);
        mRegistrationProfileImage = (CircleImageView) findViewById(R.id.profile_image);
        mSaveButton = (Button) findViewById(R.id.save_Button);
        mAlready=(TextView)findViewById(R.id.alreadymember);
    }



    /**
     * This method is to initialize listeners
     */
    private void initListeners()
    {
        mSaveButton.setOnClickListener((View.OnClickListener) this);
        mAlready.setOnClickListener((View.OnClickListener) this);
        mRegistrationProfileImage.setOnClickListener(this);

    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        inputValidation = new InputValidation(activity);
        registrationDatabaseHelper = new Database(activity);
        user = new User();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.profile_image:
                selectImage();
                break;



            case R.id.save_Button:
                postDataToSQLite();
                break;

            case R.id.alreadymember:
                finish();
                break;
        }
    }

    /**
     * This method is to validate the input text fields and post data to SQLite
     */
    private void postDataToSQLite()
    {
        if (!inputValidation.isInputEditTextFilled(mFirstName, getString(R.string.first_name_error_message))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mLastName, getString(R.string.last_name_error_message))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(mEmail, getString(R.string.error_message_email))) {
            return;
        }

        if (!inputValidation.isInputEditTextFilled(mPassword, getString(R.string.enter_password_text))) {
            return;
        }
        if (!inputValidation.isInputEditTextMatches(mPassword, mConfirmPassword,
                getString(R.string.password_match))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(mDateOfBirth,  getString(R.string.error_message_date_of_birth))) {
            return;
        }




        if (!registrationDatabaseHelper.checkUser(mEmail.getText().toString().trim())) {

            user.setFirstname(mFirstName.getText().toString().trim());
            user.setLastname(mLastName.getText().toString().trim());
            user.setEmail(mEmail.getText().toString().trim());
            //user.setMobileNumber(mMobileNumber.getText().toString().trim());
            user.setPassword(mPassword.getText().toString().trim());
            user.setDateofbirth(mDateOfBirth.getText().toString().trim());
            user.setGender(Gender);

            registrationDatabaseHelper.addUser(user);

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(scrollView, getString(R.string.success_message), Snackbar.LENGTH_LONG).show();
            emptyInputEditText();


        } else
            {
            // Snack Bar to show error message that record already exists
            Snackbar.make(scrollView, getString(R.string.email_already_exist_text), Snackbar.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText()
    {
        mFirstName.setText(null);
        mLastName.setText(null);
        mEmail.setText(null);
         mMobileNumber.setText(null);
        mPassword.setText(null);
        mConfirmPassword.setText(null);
        mDateOfBirth.setText(null);
        //mMember.setText(null);
    }


    private void selectImage() {
        String[] imageOptions = getResources().getStringArray(R.array.image_options);
        AlertDialog.Builder mOptionBuilder = new AlertDialog.Builder(RegistrationActivity.this);
        mOptionBuilder.setTitle(R.string.text_add_photo);
        mOptionBuilder.setItems(imageOptions, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        if (checkRuntimePermissions())
                        {
                            Intent mCameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(mCameraIntent, 1);
                        }
                        break;
                    case 1:
                        openGallery();
                        break;
                    default:
                        dialog.dismiss();
                        break;
                }
            }
        });
        mOptionBuilder.show();
    }


    private void openGallery()
    {
        Intent mGalleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        mGalleryIntent.setType("image/*");
         startActivityForResult(Intent.createChooser(mGalleryIntent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private boolean checkRuntimePermissions() {
        if (ActivityCompat.checkSelfPermission(RegistrationActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        AlertDialog.Builder mCameraPermissionAlertDialog = new AlertDialog.Builder(RegistrationActivity.this);
        mCameraPermissionAlertDialog.setTitle(R.string.txt_permission_enable_error);

        mCameraPermissionAlertDialog
                .setMessage(Html.fromHtml(getString(R.string.txt_camera_message)))
                .setCancelable(false)
                .setPositiveButton(R.string.txt_open_settings,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent mIntentSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                mIntentSettings.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                mIntentSettings.setData(uri);
                                startActivity(mIntentSettings);
                            }
                        })

                .setNegativeButton(R.string.txt_no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog mCameraOpenAlertDialog = mCameraPermissionAlertDialog.create();
        mCameraOpenAlertDialog.show();
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == 1)
            {
                Bitmap mCameraPicBitmap = (Bitmap) data.getExtras().get("data");
                mRegistrationProfileImage.setImageBitmap(mCameraPicBitmap);
            }
            else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null)
            {
                Uri uri = data.getData();
                try
                {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    Log.d(TAG, String.valueOf(bitmap));

                    ImageView mRegistrationProfileImage = (CircleImageView) findViewById(R.id.profile_image);
                    mRegistrationProfileImage.setImageBitmap(bitmap);
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}

