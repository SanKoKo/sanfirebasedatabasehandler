package net.sandevelopment.sanfirebasedbhandler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import net.sandevelopment.sanfirebasedatabasehandler.SanDatabaseHandler;
import net.sandevelopment.sanfirebasedbhandler.ac.RegisterUser;
import net.sandevelopment.sanfirebasedbhandler.ac.UserListAc;
import net.sandevelopment.sanfirebasedbhandler.model.ChatUser;

public class MainActivity extends AppCompatActivity {
    SanDatabaseHandler san;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        san = new SanDatabaseHandler(this,FirebaseDatabase.getInstance().getReference());
        san.createDatabaseByClass("SampleChatting",ChatUser.class);






    }

    public void tvRegister(View view) {
        startActivity(new Intent(this,RegisterUser.class));
        finish();
    }

    public void btSignIn(View view) {
        ChatUser me = new ChatUser("-LPFDD96NtKF5qEic6tJ","mg","mg@gmail.com");
        //ChatUser me = new ChatUser("-LPFEPuj48Sy3r53fvfP","kaung","kaung@gmail.com");
        startActivity(new Intent(this, UserListAc.class).putExtra("CHATUSER", me));
        finish();
    }

    public void bt2SingIn(View view) {
        ChatUser me = new ChatUser("-LPFEPuj48Sy3r53fvfP","kaung","kaung@gmail.com");
        startActivity(new Intent(this, UserListAc.class).putExtra("CHATUSER", me));
        finish();
    }
}
