package dz.atelier.ntic.remplircases.game;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AllUsersScores extends AppCompatActivity {
    DatabaseReference mref;
    TextView textView;
    String text = " ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users_scores);

        mref = FirebaseDatabase.getInstance().getReference();

        textView = findViewById(R.id.textView);

        mref.child("User").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    User ur = ds.getValue(User.class);

                   text= text + ur.getUsername() + " : " + ur.getAge() + " : " + ur.getScore() + "\n";

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void onComplete() {
        Task<AuthResult> task = null;
        if(task.isSuccessful()){
            textView.setText(text.toString());

        }else{
            Toast.makeText(AllUsersScores.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    public void statClick(View view) {
        onComplete();
    }
}
