package net.sandevelopment.sanfirebasedbhandler.ac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import net.sandevelopment.sanfirebasedbhandler.R;
import net.sandevelopment.sanfirebasedbhandler.model.ChatUser;

public class RegisterUser extends AppCompatActivity {

        EditText edName;
        EditText edEmail;
        EditText edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
         edName = findViewById(R.id.ed_username);
         edEmail = findViewById(R.id.ed_email);
         edPassword = findViewById(R.id.ed_password);

    }

    public void signUp(View view) {
        ChatUser chatUser = new ChatUser(edName.getText().toString(),edEmail.getText().toString());
        chatUser.insert();
        startActivity(new Intent(this,UserListAc.class).putExtra("CHATUSER", chatUser));
        finish();
    }
}
