package com.example.mh1535547.anndaan;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;


public class signup extends AppCompatActivity {
    Button signup;
    EditText pswd;
    EditText cpswd;
    EditText emailid;
    EditText fname;
    EditText lname;
    Spinner spinnercity, spinnerstate;
    private FirebaseAuth firebaseAuth;//defining firebaseauth object
    private ProgressDialog progressDialog;
   private FirebaseDatabase mfirebasedatabase;
   private FirebaseAuth.AuthStateListener mAuthstatelistener;
   private DatabaseReference myref;


   public static final String TAG = "Registeration";
    public static final String TAG1 = "getcurrentuser";


    public final Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile(
            "[a-zA-Z0-9+._%-+]{1,256}" +
                    "@" +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,64}" +
                    "(" +
                    "." +
                    "[a-zA-Z0-9][a-zA-Z0-9-]{0,25}" +
                    ")+"
    );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        firebaseAuth=FirebaseAuth.getInstance();//firebaseauth instance
        mfirebasedatabase=FirebaseDatabase.getInstance();
        myref=mfirebasedatabase.getReference();


        signup = (Button) findViewById(R.id.signupbutton);
        fname=(EditText) findViewById(R.id.firstnameedit);
        pswd = (EditText) findViewById(R.id.passwordedit);
        cpswd = (EditText) findViewById(R.id.confirmpasswordedit);
        emailid = (EditText) findViewById(R.id.emailidedit);
        lname = (EditText) findViewById(R.id.lastnameedit);
        spinnercity = (Spinner) findViewById(R.id.spinner_city);
        spinnerstate = (Spinner) findViewById(R.id.spinner_state);
        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }
        });
        //click listener on signup button


    }//end of oncreate method
    //to register unew user


    @Override
    protected void onStart() {
        super.onStart();

        if(firebaseAuth.getCurrentUser()!=null){
           Log.d(TAG1,"getcurrent user is executed");


        }

    }

    private void registeruser(){
        String email= emailid.getText().toString().trim();
        String password= pswd.getText().toString().trim();
        final String fn= fname.getText().toString();
        final String ln=lname.getText().toString();
        if(TextUtils.isEmpty(email)){
            String tage="Emailerror";
            Log.i(TAG,"Please nter emailid");
            Toast.makeText(this,"Please enter email id",Toast.LENGTH_LONG).show();
            emailid.requestFocus();
                 }
        if(password==""){
           Toast.makeText(this,"Please enter password id",Toast.LENGTH_LONG).show();
           emailid.requestFocus();
                 }
        progressDialog.setMessage("Registering please wait...");
        progressDialog.show();
        //creating user with email and password
         firebaseAuth.createUserWithEmailAndPassword(email,password).
                addOnCompleteListener(new OnCompleteListener<AuthResult>() {


                    @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){

                                   Savedatatofirebase save = new Savedatatofirebase(
                                           fn,ln
                                   );

                                   FirebaseDatabase.getInstance().getReference("Users")
                                   .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(save)
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {

                                           if(task.isSuccessful()){

                                              Intent intent = new Intent(getApplicationContext(),dashboard.class);
                                              startActivity(intent);
                                           }else{

                                               Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                           }
                                       }
                                   });





                                     } else{
                                            Toast.makeText(signup.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();

                                            }
                                            progressDialog.dismiss();

                                              }



                      });


    }

}
