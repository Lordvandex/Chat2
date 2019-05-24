package com.example.chat.chat.java;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chat.chat.java.models.Post;
import com.example.chat.chat.java.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.samsung.barracudapff.firebaseexample.R;

public class NewPostActivity extends BaseActivity {

    private static final String TAG = "NewPostActivity";
    private static final String REQUIRED = "Required";

    private DatabaseReference mDatabase;

    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = findViewById(R.id.fieldTitle);
        mBodyField = findViewById(R.id.fieldBody);
        mSubmitButton = findViewById(R.id.fabSubmitPost);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPost();
            }
        });
    }

    private void submitPost() {
        final String title = mTitleField.getText().toString();
        final String body = mBodyField.getText().toString();

        // TODO: 02.03.2019 empty text

        // Disable button so there are no multi-posts
        setEditingEnabled(false);
        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        final String userId = getUid();
        FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userId)
                .addListenerForSingleValueEvent(
                        new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);

                                writeNewPost(userId, user.name, title, body);
                                setEditingEnabled(false);
                                finish();
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
                                setEditingEnabled(true);
                            }
                        });
        // [END single_value_read]
    }

    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
    }

    private void writeNewPost(String userId, String username, String title, String body) {
        String key = mDatabase.child("posts")
                .push().getKey();
        Post post = new Post(userId,username,title,body);
        FirebaseDatabase.getInstance().getReference()
                .child("posts")
                .child(key).updateChildren(post.toMap());
        FirebaseDatabase.getInstance().getReference()
                .child("user-posts")
                .child(getUid())
                .child(key).updateChildren(post.toMap());
    }
}
