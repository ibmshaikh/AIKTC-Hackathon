package com.example.codept.hackathon;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.codept.hackathon.R.styleable.View;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText memail,mpassword;
    private String email,password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth=FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser()!=null){

            finish();

            startActivity(new Intent(MainActivity.this,Main2Activity.class));

        }

        memail=(EditText)findViewById(R.id.email);
        mpassword=(EditText)findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email=memail.getText().toString();
                password=mpassword.getText().toString();

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(MainActivity.this,Main2Activity.class));

                        }
                        else {

                            memail.setError("email or password is INVALID");

                        }
                    }
                });


            }
        });




    }

    public void Register(android.view.View view){

        Intent i=new Intent(MainActivity.this,Register.class);

       startActivity(i);


    }
}
