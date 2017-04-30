package pes.twochange.presentation.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import pes.twochange.R;
import pes.twochange.domain.model.Chat;
import pes.twochange.presentation.Config;

public class MainMenuActivity extends AppCompatActivity {

    private static final String TAG = "MenuActivitiy";

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String currentUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_provisional);

        // region getting username from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SP_NAME, MODE_PRIVATE);
        final String currentUsername = sharedPreferences.getString("username", null);
        // endregion

        TextView uidLbl = (TextView)findViewById(R.id.uidLbl);
        if (currentUsername != null) {
            uidLbl.setText(currentUsername);
        }

        // Esto no se pregunta antes?
        /*
        FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    //No logeado
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    finish();
                }
            }
        };
        */


        //Show chats button + Pressed button listener
        Button showChatsBtn = (Button)findViewById(R.id.showChatsBtn);
        showChatsBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ADRI CONECTATE AQUI", Toast.LENGTH_LONG).show();
                Intent showChats = new Intent(getApplicationContext(), RecyclerChatActivity.class);
                FirebaseUser userSender = FirebaseAuth.getInstance().getCurrentUser();
                showChats.putExtra("currentUserUID",userSender.getUid());
                startActivity(showChats);
            }
        });

        //Open chat button + Pressed button listener
        Button openChatBtn = (Button)findViewById(R.id.openChatBtn);
        openChatBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                FirebaseUser userSender = FirebaseAuth.getInstance().getCurrentUser();
                if (userSender != null) {
                    Intent chatIntent = new Intent (getApplicationContext(), ChatActivity.class);
                    String userReciverUid = "DEEgGOdjjmVkR29uEtvNi0W2zrv1";
                    if (userSender.getUid().equals("DEEgGOdjjmVkR29uEtvNi0W2zrv1")) userReciverUid = "PtkvVdIGqdVzx5KJ35t2OJ1wXKm2";
                    Chat chat = new Chat(userSender.getUid(),userReciverUid);
                    //Chat chat = new Chat(userReciverUid, userSender.getUid());
                    chatIntent.putExtra("chat", chat);
                    startActivity(chatIntent);
                } else {
                    Toast.makeText(getApplicationContext(), "You must LogIn first!", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
                    finish();
                }
            }
        });

        //Add post button + Pressed button listener
        Button addPostBtn = (Button)findViewById(R.id.addPostBtn);
        addPostBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "ANDRES CONECTATE AQUI", Toast.LENGTH_LONG).show();
            }
        });

        //View Profile button + Pressed button listener
        Button viewProfileBtn = (Button)findViewById(R.id.viewProfileBtn);
        viewProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            }
        });

        //Show chats button + Pressed button listener
        Button searchUserBtn = (Button)findViewById(R.id.searchUserBtn);
        searchUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchProfileActivity.class));
            }
        });


        Button logoutBtn = (Button) findViewById(R.id.logOutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Log.d(TAG, String.valueOf(FirebaseAuth.getInstance().getCurrentUser()));
                startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
                finish();


            }
        });
    }
}