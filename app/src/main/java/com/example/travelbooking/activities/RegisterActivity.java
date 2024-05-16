package com.example.travelbooking.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelbooking.CustomToast;
import com.example.travelbooking.DBHelper;
import com.example.travelbooking.R;
import com.example.travelbooking.utils.EmailHandler;
import com.example.travelbooking.utils.MyBroadcastReceiver;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    private TextInputLayout lo_fname, lo_lname, lo_bdate, lo_address, lo_contact, lo_email;
    private TextInputEditText et_fname, et_lname, et_bdate, et_address, et_contact, et_email;
    private CheckBox cb_privacy;
    private MaterialButton btn_register;
    private TextView tv_have_an_account;

    AlarmManager alarmManager;

    EmailHandler emailHandler;

    String fn,ln,bdate,address,contact,email;

    int mYear, mMonth, mDay;

    DBHelper db;

    CustomToast toast;

    boolean isSent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        iniValues();
        setListeners();
    }

    private void iniValues()
    {
        lo_fname = findViewById(R.id.lo_fname);
        lo_lname = findViewById(R.id.lo_lname);
        lo_bdate = findViewById(R.id.lo_bdate);
        lo_address = findViewById(R.id.lo_address);
        lo_contact = findViewById(R.id.lo_contact);
        lo_email = findViewById(R.id.lo_email);

        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_bdate = findViewById(R.id.et_bdate);
        et_address = findViewById(R.id.et_address);
        et_contact = findViewById(R.id.et_contact);
        et_email = findViewById(R.id.et_email);

        cb_privacy = findViewById(R.id.cb_privacy);

        btn_register = findViewById(R.id.btn_register);

        tv_have_an_account = findViewById(R.id.tv_have_an_account);

        emailHandler = new EmailHandler();
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        db = new DBHelper(this);
        toast = new CustomToast();
    }

    public void setListeners(){
        cb_privacy.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    btn_register.setEnabled(true);
                }else{
                    btn_register.setEnabled(false);
                }
            }
        });

        et_bdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int currentYear = calendar.get(Calendar.YEAR);
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        mYear = year;
                        mMonth = month;
                        mDay = day;

                        et_bdate.setText((month + 1) + "/" + day + "/" + year );
                    }
                }, currentYear, currentMonth, currentDay);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
                datePickerDialog.show();
            }
        });
    }

    public void sendNotification(String subject){
        Intent z = new Intent(RegisterActivity.this, MyBroadcastReceiver.class);
        PendingIntent xpendingIntent = PendingIntent.getBroadcast(RegisterActivity.this, 0 ,z, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), xpendingIntent);

        Intent i = new Intent(this, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(i);
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "Notify")
                .setSmallIcon(R.drawable.logo)
                .setContentText("On the Go!")
                .setContentText(subject)
                .setAutoCancel(false)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
       try {
           notificationManagerCompat.notify(200, builder.build());
       }
       catch (SecurityException e)
       {
           Log.e("Debugging", String.valueOf(e));
       }
    }

    public void SetErrors(){
        if(fn.isEmpty()){
            lo_fname.setErrorEnabled(true);
            lo_fname.setError("First name is missing");
        }

        if(ln.isEmpty()){
            lo_lname.setErrorEnabled(true);
            lo_lname.setError("Last name is missing");
        }

        if(bdate.isEmpty()){
            lo_bdate.setErrorEnabled(true);
            lo_bdate.setError("Birthdate is missing");
        }

        if(address.isEmpty()){
            lo_address.setErrorEnabled(true);
            lo_address.setError("Address is missing");
        }

        if(contact.isEmpty()){
            lo_contact.setErrorEnabled(true);
            lo_contact.setError("Contact number is missing");
        }

        if(email.isEmpty()){
            lo_email.setErrorEnabled(true);
            lo_email.setError("Email is missing");
        }


        et_fname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lo_fname.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_lname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lo_lname.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_bdate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lo_bdate.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lo_address.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_contact.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lo_contact.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        et_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                lo_email.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void RegisterProcess(View v){

        fn = et_fname.getText().toString();
        ln = et_lname.getText().toString();
        bdate = et_bdate.getText().toString();
        address = et_address.getText().toString();
        contact = et_contact.getText().toString();
        email = et_email.getText().toString();

        SetErrors();



        if(!fn.isEmpty() && !ln.isEmpty() && !bdate.isEmpty() && !address.isEmpty() && !contact.isEmpty() && !email.isEmpty()){
            Random r = new Random();
            String newPIN = String.format("%04d", r.nextInt(10003));




            View ProgressView = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.progress_bar, null);

            AlertDialog.Builder progressBuilder = new AlertDialog.Builder(RegisterActivity.this);
            progressBuilder.setView(ProgressView);
            progressBuilder.setCancelable(false);
            AlertDialog progressDialog = progressBuilder.create();


            TextView tv_status = ProgressView.findViewById(R.id.tv_status);
            ProgressBar progressBar = ProgressView.findViewById(R.id.progress_bar);

            progressDialog.show();
            tv_status.setText("Sending code");
            emailHandler.sendEmail("App name : Your 4 digit PIN Code", "Your 4 digit PIN Code is: " + newPIN, email);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressDialog.dismiss();

                   View PinView = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.progress_bar_register, null);

                   AlertDialog.Builder pinViewBuilder = new AlertDialog.Builder(RegisterActivity.this);
                   pinViewBuilder.setView(PinView);
                   pinViewBuilder.setCancelable(false);

                   AlertDialog pinDialog = pinViewBuilder.create();
                   pinDialog.show();

                   sendNotification("Your verification code is: " + newPIN);

                   EditText et1 = PinView.findViewById(R.id.et1);
                   EditText et2 = PinView.findViewById(R.id.et2);
                   EditText et3 = PinView.findViewById(R.id.et3);
                   EditText et4 = PinView.findViewById(R.id.et4);
                   MaterialButton btn_confirmcode = PinView.findViewById(R.id.btn_confirmcode);
                   TextView tv_resendCode = PinView.findViewById(R.id.tv_resendCode);


                        et1.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if(charSequence.length() == 1)
                                {
                                    et2.requestFocus();
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        et2.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if(charSequence.length() == 1)
                                {
                                    et3.requestFocus();
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });
                        et3.addTextChangedListener(new TextWatcher() {
                            @Override
                            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                            }

                            @Override
                            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                if(charSequence.length() == 1)
                                {
                                    et4.requestFocus();
                                }
                            }

                            @Override
                            public void afterTextChanged(Editable editable) {

                            }
                        });


                   tv_resendCode.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           if(isSent == false){
                               emailHandler.sendEmail("You have requested your PIN code to be resent to your email.", "If you have not requested your pin code to be resent, please ignore this message. Your pin code is : " + newPIN, email);
                               sendNotification("You have created an account! Your passcode is: " + newPIN);
                                isSent = true;
                                int color = ContextCompat.getColor(RegisterActivity.this, R.color.failed_color_text);
                                tv_resendCode.setTextColor(color);

                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        isSent = false;
                                        int color = ContextCompat.getColor(RegisterActivity.this, R.color.teal_700);
                                        tv_resendCode.setTextColor(color);

                                    }
                                }, 5000);
                           }else{
                               Toast.makeText(RegisterActivity.this, "We have resent your code already, Please wait for the cooldown.", Toast.LENGTH_SHORT).show();

                           }
                       }
                   });


                   btn_confirmcode.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           String pinTyped = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();

                           if(pinTyped.equals(newPIN)){


                               new Handler().postDelayed(new Runnable() {
                                   @Override
                                   public void run() {
                                       View ProgressView = LayoutInflater.from(RegisterActivity.this).inflate(R.layout.progress_bar, null);

                                       AlertDialog.Builder progressBuilder = new AlertDialog.Builder(RegisterActivity.this);
                                       progressBuilder.setView(ProgressView);
                                       progressBuilder.setCancelable(false);
                                       AlertDialog progressDialog = progressBuilder.create();


                                       TextView tv_status = ProgressView.findViewById(R.id.tv_status);
                                       ProgressBar progressBar = ProgressView.findViewById(R.id.progress_bar);

                                       tv_status.setText("Processing");

                                       progressDialog.show();

                                       new Handler().postDelayed(new Runnable() {
                                           @Override
                                           public void run() {
                                               progressDialog.dismiss();
                                               toast.myToast(RegisterActivity.this, R.drawable.ic_check, "Successful!", "4-digit PIN Code sent successfully!", R.color.success_color_bg, R.color.success_color_text);
                                               startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                               db.CreateAccount(fn,ln,bdate,address,contact,email,newPIN, getRandomColor());
                                               sendNotification("You have created an account! Your passcode is: " + newPIN);

                                           }
                                       }, 4000);
                                   }
                               }, 3000);




                           }else{
                               toast.myToast(RegisterActivity.this, R.drawable.ic_error, "Failed!", "Incorrect PIN Code", R.color.failed_color_bg, R.color.failed_color_text);
                           }
                       }
                   });


                 /*
                   */




                }
            }, 4000);





           // startActivity(new Intent(RegisterActivity.this, LoginActivity.class));*/
        }








    }



    public int getRandomColor()
    {
        Random r = new Random();
        int[] colors = {R.color.red_pastel, R.color.fuchsia_pastel, R.color.cyan_pastel, R.color.green_pastel,
                R.color.yellow_pastel, R.color.orange_pastel, R.color.blue_pastel, R.color.purple_pastel};

        int colorIndex = r.nextInt(colors.length);

        return ContextCompat.getColor(this, colors[colorIndex]);
    }

    public void GoToLogin(View v){
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
    }



}