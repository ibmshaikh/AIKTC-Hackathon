package com.example.codept.hackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    private EditText mname,memail,mpassword,mretypepasswor,madharno,marepincode,mphoneno;
    private Button registerButton;
    private String name,email,password,retype;
    private int aadhar,areapin,phoneno;
    private FirebaseAuth mAuth;
    private DatabaseReference mref;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();
        mref= FirebaseDatabase.getInstance().getReference();

        mname=(EditText)findViewById(R.id.name);
        memail=(EditText)findViewById(R.id.email);
        mpassword=(EditText)findViewById(R.id.password);
        mretypepasswor=(EditText)findViewById(R.id.retypepassword);
        madharno=(EditText)findViewById(R.id.aadharnumber);
        marepincode=(EditText)findViewById(R.id.areapin);
        mphoneno=(EditText)findViewById(R.id.phone);
        registerButton=(Button)findViewById(R.id.register);
        mDialog=new ProgressDialog(Register.this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.show();

                name=mname.getText().toString();
                email=memail.getText().toString();
                password=mpassword.getText().toString();
                retype=mretypepasswor.getText().toString();
                aadhar=Integer.parseInt(madharno.getText().toString());
                areapin=Integer.parseInt(marepincode.getText().toString());
                phoneno=Integer.parseInt(mphoneno.getText().toString());

                if (!name.isEmpty() && !email.isEmpty() && !password.isEmpty() && !retype.isEmpty() && aadhar!=0 && areapin!=0 && phoneno!=0){

                    if (password.equals(retype)){


                        final HashMap hashMap=new HashMap();
                        hashMap.put("Name",name);
                        hashMap.put("email",email);
                        hashMap.put("Aadhar Number",aadhar);
                        hashMap.put("Area Pin",areapin);
                        hashMap.put("Phone Number",phoneno);


                        mAuth.createUserWithEmailAndPassword(email,retype).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {


                                mref.child("User").child(String.valueOf(aadhar));

                                mref.push().setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        mDialog.dismiss();
                                        Intent i=new Intent(Register.this,Main2Activity.class);
                                        startActivity(i);

                                    }
                                });



                            }
                        });



                    }
                    else {

                        mretypepasswor.setError("Password did not maatch");

                    }


                }

                else {

                    Toast.makeText(Register.this,"Field Should not me empty",Toast.LENGTH_LONG).show();

                }

            }
        });




    }
}
