package com.example.codept.hackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;


public class Register extends AppCompatActivity  implements View.OnClickListener {

    private Button buttonSignup;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private EditText retype;
    private TextView textViewSignin;
    private EditText mphone,mname;
    private String name;
    private Long phno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        firebaseAuth = FirebaseAuth.getInstance();

        //if getCurrentUser does not returns null
        if(firebaseAuth.getCurrentUser() != null){
            //that means user is already logged in
            //so close this activity
            finish();

            //and open profile activity
            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
        }

        editTextEmail = (EditText) findViewById(R.id.input_email);
        editTextPassword = (EditText) findViewById(R.id.input_password);
        buttonSignup = (Button) findViewById(R.id.btn_login);
        retype = (EditText) findViewById(R.id.input_retype);
        progressDialog = new ProgressDialog(this);
        buttonSignup.setOnClickListener(this);
        mname=(EditText)findViewById(R.id.Name);
        mphone=(EditText)findViewById(R.id.phoneno);



    }





    public void onClick(View v) {
        if (v == this.buttonSignup) {
            registerUser();
        }
    }

    private void registerUser(){

        //getting email and password from edit texts
        final String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();

        phno=Long.parseLong(mphone.getText().toString());

        name=mname.getText().toString();

        String ret=retype.getText().toString().trim();
        if (ret.equals(password)){
            Toast.makeText(Register.this,"Password not match",Toast.LENGTH_SHORT).show();
        }
        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        //creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        //checking if success
                        if(task.isSuccessful()){

                            HashMap h=new HashMap();
                            h.put("Name",name);
                            h.put("Email",email);
                            h.put("Phone Number",phno);
                            String a=FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
                            DatabaseReference mref=FirebaseDatabase.getInstance().getReference().child("Users").child(a);
                            mref.push().setValue(h);

                            finish();
                            startActivity(new Intent(getApplicationContext(), Main2Activity.class));
                        }else{
                            //display some message here
                            Toast.makeText(Register.this,"Registration Error",Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });

    }

}