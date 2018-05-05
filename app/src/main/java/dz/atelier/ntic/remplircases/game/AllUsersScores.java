package dz.atelier.ntic.remplircases.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllUsersScores extends AppCompatActivity {

    TextView textView, textSort;
    boolean b = false;
    String text = " ";
    String text1 = " ";
    private FirebaseAuth mAuth;
    DatabaseReference mRef2;
    DatabaseReference mRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_scores);

        mAuth = FirebaseAuth.getInstance();
        mRef = FirebaseDatabase.getInstance().getReference(".info/connected");
        mRef2 = FirebaseDatabase.getInstance().getReference();


    }


    public void statClick(View view) {


        textView = findViewById(R.id.textView);

        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.getValue(Boolean.class)) {
                    b = true;
                    Toast.makeText(AllUsersScores.this, "connected", Toast.LENGTH_SHORT).show();
                }

                else {
                    Toast.makeText(AllUsersScores.this, "Error Network", Toast.LENGTH_SHORT).show();
                    b = false;
                }
            }
            @Override
            public void onCancelled(DatabaseError error) {

            }
        });


        if (b){
            mRef2.child("User").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        User ur = ds.getValue(User.class);
                        text= text + ur.getUsername() + " : " + ur.getAge() + " : " + ur.getScore() + "\n";
                    }
                    textView.setText(text.toString());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }});

        }

        textView = findViewById(R.id.textView);
    }

    public void SortOn(View view) {
        textSort=findViewById(R.id.textSort);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference highscoreRef = database.getReference();



        mRef2.orderByChild("score").limitToFirst(10).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildKey) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    User ur = ds.getValue(User.class);


                    text1= text1 + ur.getUsername() + " : " + ur.getAge() + " : " + ur.getScore() + "\n";

                }

                textSort.setText(text1.toString());

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "Error sending data.", Toast.LENGTH_LONG).show();
            }

        });

        /*
        * Order by child
        * https://stackoverflow.com/questions/39489398/firebase-retrieve-highest-100-score
        * */
    }
}
