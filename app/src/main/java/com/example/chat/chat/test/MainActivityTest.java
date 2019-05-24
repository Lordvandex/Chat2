package com.example.chat.chat.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsung.barracudapff.firebaseexample.R;

public class MainActivityTest extends AppCompatActivity {
    Button buttonCheck;
    TextView textViewType;
    TextView textViewVersion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_test);

       init();

       setUpFirebase();
    }

    private void setUpFirebase() {
        buttonCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference numberRef = FirebaseDatabase.getInstance().getReference()
                        .child("app");

                numberRef.child("version")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                textViewType.setText(dataSnapshot.getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                numberRef.child("type")
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                textViewVersion.setText(dataSnapshot.getValue(String.class));
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
            }
        });
    }

    private void init() {
        buttonCheck = findViewById(R.id.button);
        textViewType = findViewById(R.id.textViewType);
        textViewVersion = findViewById(R.id.textViewVersion);
    }
}
