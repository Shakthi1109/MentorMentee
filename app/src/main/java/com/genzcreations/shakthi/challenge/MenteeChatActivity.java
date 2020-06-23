package com.genzcreations.shakthi.challenge;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenteeChatActivity extends AppCompatActivity {

    String MenteeTypedMsg,MenteeMsgShown;
    String Menteemail, existingMsg;
    TextView MenteeViewMsg;
    EditText MenteeTypeMsg;
    Button MenteeSend;

    DatabaseReference reff;
    chats chats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentee_chat);

        MenteeViewMsg = (TextView) findViewById(R.id.MenteeViewMsg);
        MenteeTypeMsg = (EditText) findViewById(R.id.MenteeTypeMsg);
        MenteeSend = (Button) findViewById(R.id.MenteeSend);

        Bundle bundle = getIntent().getExtras();
        Menteemail = bundle.getString("message2");

        chats = new chats();
        reff = FirebaseDatabase.getInstance().getReference().child("users").child(Menteemail).child("chats");
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                try
                {
                    existingMsg = dataSnapshot.child("MenteeTypedMsg").getValue().toString();
                }
                catch(NullPointerException e)
                {
                    Log.d("err1",e.toString());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        MenteeSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(MenteeChatActivity.this,existingMsg, Toast.LENGTH_SHORT).show();
                MenteeTypedMsg = MenteeTypeMsg.getText().toString().trim();

                existingMsg = existingMsg+";;"+MenteeTypedMsg;
                chats.setMenteeTypedMsg(existingMsg);



                reff.setValue(chats);
            }
        });

    }
}
