package com.genzcreations.shakthi.challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    String StrNameRegister, StrEmailRegister, StrPassRegister, StrMentorYesNo;
    EditText EditNameRegister, EditEmailRegister, EditPassRegister;
    Button btnRegisterSubmit;
    RadioGroup radioGroup;
    RadioButton radioButton;

    DatabaseReference reff;
    users users;
    chats chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditNameRegister = (EditText) findViewById(R.id.IdRegisterName);
        EditEmailRegister = (EditText) findViewById(R.id.IdRegisterEmail);
        EditPassRegister = (EditText) findViewById(R.id.IdRegisterPass);
        btnRegisterSubmit = (Button) findViewById(R.id.btnRegisterSubmit);
        radioGroup = findViewById(R.id.radioGroup);

        users = new users();
        chats = new chats();
        reff = FirebaseDatabase.getInstance().getReference().child("users");
//        reff = FirebaseDatabase.getInstance().getReference().child("mentors");
//        reff = FirebaseDatabase.getInstance().getReference().child("mentees");

        //Button
        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StrNameRegister = EditNameRegister.getText().toString().trim();
                StrEmailRegister = EditEmailRegister.getText().toString().trim();
                StrPassRegister = EditPassRegister.getText().toString().trim();

                int radioId = radioGroup.getCheckedRadioButtonId();
                radioButton = findViewById(radioId);
                StrMentorYesNo = radioButton.getText().toString();

//                Toast.makeText(RegisterActivity.this, StrNameRegister, Toast.LENGTH_SHORT).show();
//                Toast.makeText(RegisterActivity.this, StrEmailRegister, Toast.LENGTH_SHORT).show();
//                Toast.makeText(RegisterActivity.this, StrPassRegister, Toast.LENGTH_SHORT).show();
//                Toast.makeText(RegisterActivity.this, StrMentorYesNo, Toast.LENGTH_SHORT).show();


                //firebase upload

                users.setName(StrNameRegister);
                users.setEmail(StrEmailRegister);
                users.setPass(StrPassRegister);
                users.setMentor(StrMentorYesNo);


                reff.child(StrEmailRegister).setValue(users);


                chats.setMenteeTypedMsg(" ");
                reff.child(StrEmailRegister).child("chats").setValue(chats);
                Toast.makeText(RegisterActivity.this, "Registered", Toast.LENGTH_SHORT).show();



            }
        });


    }
}
