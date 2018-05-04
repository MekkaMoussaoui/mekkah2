package dz.atelier.ntic.remplircases.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class statistique extends AppCompatActivity {

    EditText score;
    private FirebaseAuth mAuth;
    DatabaseReference myref;
    FirebaseDatabase mdatabase;

    int value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique);

        score = (EditText) findViewById(R.id.scoreId);
        mAuth = FirebaseAuth.getInstance();



    }
    private void updateData() {
        mdatabase = FirebaseDatabase.getInstance();
        myref = mdatabase.getReference();
        String user_id = mAuth.getCurrentUser().getUid();

        value = Integer.valueOf(score.getText().toString());

        myref.child("User").child(user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("score").setValue(value);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }



    public void btnonClick(View view) {
       updateData();


    }
}
