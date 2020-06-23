package com.genzcreations.shakthi.challenge;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    String StrEmailLogin, StrPassLogin;
    String passRetrieved, MentorStatusRetrieved, emailretrieved;
    EditText EditNameLogin, EditEmailLogin, EditPassLogin;
    Button btnLoginLogin, btnLoginRegister, btnSubmit;
    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        EditEmailLogin = (EditText) findViewById(R.id.EditEmailLogin);
        EditPassLogin = (EditText) findViewById(R.id.EditPassLogin);
        btnLoginLogin = (Button) findViewById(R.id.btnLoginLogin);
        btnLoginRegister = (Button) findViewById(R.id.btnLoginRegister);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);

        StrEmailLogin =null;
        StrPassLogin = null;
        passRetrieved = null;
        MentorStatusRetrieved = null;
        emailretrieved = null;


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInput();
                Toast.makeText(LoginActivity.this, "Please wait...", Toast.LENGTH_SHORT).show();
                dbFetch();
            }
        });



        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                dbFetch();
                loginVerify();

            }
        });

        btnLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);

            }
        });

        StrEmailLogin =null;
        StrPassLogin = null;
        passRetrieved = null;
        MentorStatusRetrieved = null;
        emailretrieved = null;
    }

    void getInput(){

        StrEmailLogin = EditEmailLogin.getText().toString().trim();
        StrPassLogin = EditPassLogin.getText().toString().trim();

    }

    void dbFetch(){


        try {

            reff = FirebaseDatabase.getInstance().getReference().child("users").child(StrEmailLogin);

        }catch (Exception e){
            e.printStackTrace();


        }

        if(reff==null){
            Toast.makeText(LoginActivity.this, "Incorrect Email", Toast.LENGTH_SHORT).show();
        }
        else {
            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    passRetrieved = dataSnapshot.child("pass").getValue().toString();
                    MentorStatusRetrieved = dataSnapshot.child("mentor").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });

            reff.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    passRetrieved = dataSnapshot.child("pass").getValue().toString();
                    MentorStatusRetrieved = dataSnapshot.child("mentor").getValue().toString();
                    emailretrieved = dataSnapshot.child("email").getValue().toString();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(LoginActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    void loginVerify(){
        if( StrPassLogin.equals(passRetrieved) && StrEmailLogin.equals(emailretrieved)){

                if(MentorStatusRetrieved.equals("Mentor")){
                    Intent intent = new Intent(LoginActivity.this, MentorChatActivity.class);
                    intent.putExtra("message1",StrEmailLogin);
                    startActivity(intent);
                }
                else{
                    Intent intent = new Intent(LoginActivity.this, MenteeChatActivity.class);
                    intent.putExtra("message2",StrEmailLogin);
                    startActivity(intent);
                }

        }
        else
        {
            Toast.makeText(LoginActivity.this,"Try Once More" , Toast.LENGTH_SHORT).show();
        }
    }

}
