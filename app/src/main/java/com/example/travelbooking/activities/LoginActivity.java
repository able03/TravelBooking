package com.example.travelbooking.activities;

import android.accounts.Account;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.travelbooking.CustomToast;
import com.example.travelbooking.DBHelper;
import com.example.travelbooking.R;
import com.example.travelbooking.static_models.AccountStaticModel;
import com.example.travelbooking.utils.EmailHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private EditText et1, et2, et3, et4;
    private MaterialButton btn_login;
    private TextView tv_no_account;
    DBHelper db;

    CustomToast toast;

    EmailHandler emailHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initValue();
        setPin();
    }

    private void initValue()
    {
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        et3 = findViewById(R.id.et3);
        et4 = findViewById(R.id.et4);

        btn_login = findViewById(R.id.btn_login);

        tv_no_account = findViewById(R.id.tv_no_account);

        db = new DBHelper(this);
        toast = new CustomToast();
        emailHandler = new EmailHandler();

    }

    public void LoginProcess(View v){
        String pinTyped = et1.getText().toString() + et2.getText().toString() + et3.getText().toString() + et4.getText().toString();

        if(!pinTyped.isEmpty()){
            Cursor c = db.FindAccount(pinTyped);
            if(c.moveToFirst()){
                int id = c.getInt(0);
                String fname = c.getString(1);
                String lname = c.getString(2);
                String bdate = c.getString(3);
                String address = c.getString(4);
                String contact = c.getString(5);
                String email = c.getString(6);
                int color_rid = c.getInt(7);



                new AccountStaticModel(id, fname, lname, bdate, address, contact, email, color_rid);

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                toast.myToast(this, R.drawable.ic_check, "Successful!", "Account login successfully!", R.color.success_color_bg, R.color.success_color_text);
            }else{
                toast.myToast(this, R.drawable.ic_error, "Failed!", "Account not found!", R.color.failed_color_bg, R.color.failed_color_text);
            }
        }else{
            toast.myToast(this, R.drawable.ic_error, "Failed!", "You have not typed anything yet!", R.color.failed_color_bg, R.color.failed_color_text);
        }
    }

    private void setPin()
    {
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
    }

    public void GoToRegister(View v ){
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));


    }

    public void ForgotPIN(View v){
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.forgot_pin, null);
        View progressView = inflater.inflate(R.layout.progress_bar, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        builder.setCancelable(true);

        TextInputEditText et_emailForgotPIN = dialogView.findViewById(R.id.et_emailForgotPIN);
        MaterialButton btn_sendCode = dialogView.findViewById(R.id.btn_sendCode);

        AlertDialog alertDialog = builder.create();
        alertDialog.show();

        btn_sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clone the progressView


                if(!et_emailForgotPIN.getText().toString().isEmpty()){

                    View clonedProgressView = LayoutInflater.from(LoginActivity.this).inflate(R.layout.progress_bar, null);

                    AlertDialog.Builder progressBuilder = new AlertDialog.Builder(LoginActivity.this);
                    progressBuilder.setView(clonedProgressView);
                    progressBuilder.setCancelable(false);
                    AlertDialog progressDialog = progressBuilder.create();
                    progressDialog.show();

                    TextView tv_status = clonedProgressView.findViewById(R.id.tv_status);
                    ProgressBar progressBar = clonedProgressView.findViewById(R.id.progress_bar);

                    tv_status.setText("Sending code...");
                    String typedEmail = et_emailForgotPIN.getText().toString();



                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressDialog.dismiss();
                            alertDialog.dismiss();
                            progressBar.setVisibility(View.VISIBLE);
                            if(db.FindPinWithEmail(typedEmail) != null){
                                String pinFound = db.FindPinWithEmail(typedEmail);
                                if(!pinFound.isEmpty()){
                                    toast.myToast(LoginActivity.this, R.drawable.ic_check, "Successful!", "4-digit PIN Code sent successfully!", R.color.success_color_bg, R.color.success_color_text);
                                    emailHandler.sendEmail("You have requested your PIN code to be resent to your email.", "If you have not requested your pin code to be resent, please ignore this message. Your pin code is : " + pinFound, typedEmail);

                                }else{
                                    toast.myToast(LoginActivity.this, R.drawable.ic_error, "Failed!", "Email not found in the database!", R.color.failed_color_bg, R.color.failed_color_text);
                                }
                            }else{
                                toast.myToast(LoginActivity.this, R.drawable.ic_error, "Failed!", "Email not found in the database!", R.color.failed_color_bg, R.color.failed_color_text);
                            }



                        }
                    }, 3000);
                }else{
                    toast.myToast(LoginActivity.this, R.drawable.ic_error, "Failed!", "You have not typed anything yet!", R.color.failed_color_bg, R.color.failed_color_text);
                }

            }
        });


    }

}