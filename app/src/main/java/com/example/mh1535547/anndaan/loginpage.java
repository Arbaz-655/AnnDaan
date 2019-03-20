package com.example.mh1535547.anndaan;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.widget.Toast;
public class loginpage extends AppCompatActivity {
    Button login;
    EditText email;
    EditText password;
    TextView forgotten;
    TextView nuser;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    CheckBox check;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,

                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.loginpage);

        firebaseAuth = FirebaseAuth.getInstance();


        email = (EditText) findViewById(R.id.emailtext);
        password = (EditText) findViewById(R.id.passwordtext);
        login = (Button) findViewById(R.id.logintohome);
        forgotten = (TextView) findViewById(R.id.forget);
        nuser = (TextView) findViewById(R.id.newuser);
        check=(CheckBox) findViewById(R.id.checkBox);

        progressDialog = new ProgressDialog(this);

        //to check if the user is currently logged in or not
        //if yes allow user directly to dashboard otherwise let him to login
        if (firebaseAuth.getCurrentUser() != null) {
            //close this activity

            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), dashboard.class));
        }


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userlogin();


            }
        });

        
        nuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),signup.class);
                startActivity(intent);
            }
        });


        check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean value) {
                if(value){

                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });


    }


    private void userlogin() {

        String em = email.getText().toString();
        String pass = password.getText().toString();

        if (em.isEmpty()) {
            Toast.makeText(loginpage.this, "Please enter your registered emailid", Toast.LENGTH_LONG).show();
            email.requestFocus();
        } else if (pass.isEmpty()) {
            Toast.makeText(loginpage.this, "Please enter your  password", Toast.LENGTH_LONG).show();
            password.requestFocus();
        } else {


            progressDialog.setMessage("Logging in..");
            progressDialog.show();

            firebaseAuth.signInWithEmailAndPassword(em, pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();

                            if (task.isSuccessful()) {

                                finish();
                                Intent intent = new Intent(loginpage.this, dashboard.class);
                                startActivity(intent);

                            } else {

                                Toast.makeText(loginpage.this, "Email/password is incorrect", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

        }
    }

}