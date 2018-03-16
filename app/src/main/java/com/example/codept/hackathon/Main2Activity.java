package com.example.codept.hackathon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Main2Activity extends AppCompatActivity {

    private EditText mno1,mno2,mno3;
    private long no1,no2,no3;
    private String mCurrentuser;
    private Button save;
    private ProgressDialog mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mCurrentuser= FirebaseAuth.getInstance().getCurrentUser().getUid().toString();


        save=(Button)findViewById(R.id.button3);

        mno1=(EditText)findViewById(R.id.no1);
        mno2=(EditText)findViewById(R.id.no2);
        mno3=(EditText)findViewById(R.id.no3);

        mDialog=new ProgressDialog(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDialog.setMessage("Please Wait");
                mDialog.show();
                mDialog.setCanceledOnTouchOutside(false);
                no1=Long.parseLong(mno1.getText().toString());
                no2=Long.parseLong(mno2.getText().toString());
                no3=Long.parseLong(mno3.getText().toString());

                DatabaseReference mref = FirebaseDatabase.getInstance().getReference().child("UserContacts").child(mCurrentuser);

                HashMap h=new HashMap();
                h.put("Phone 1",no1);
                h.put("Phone 2",no2);
                h.put("Phone 3",no3);

               mref.push().setValue(h).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){

                            startActivity(new Intent(Main2Activity.this,SOSActivity.class));
                            finish();

                        }
                    }
                });



            }
        });


    }

    public void next(View v){

        startActivity(new Intent(Main2Activity.this,SOSActivity.class));

    }

}
