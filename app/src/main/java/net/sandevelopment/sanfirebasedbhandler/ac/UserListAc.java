package net.sandevelopment.sanfirebasedbhandler.ac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import net.sandevelopment.sanfirebasedatabasehandler.SanFireDbResult;
import net.sandevelopment.sanfirebasedbhandler.adaptor.AdaptorUser;
import net.sandevelopment.sanfirebasedbhandler.MainActivity;
import net.sandevelopment.sanfirebasedbhandler.R;
import net.sandevelopment.sanfirebasedbhandler.model.ChatUser;

import java.util.List;

public class UserListAc extends AppCompatActivity {

    ListView listView;
    ChatUser me;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        listView = findViewById(R.id.ls);
        me = (ChatUser) getIntent().getExtras().get("CHATUSER");

        new SanFireDbResult<ChatUser>().getAllDataListCallback(ChatUser.class, new SanFireDbResult.CallbackResult<ChatUser>() {
            @Override
            public void result(final List<ChatUser> mList) {
                listView.setAdapter(new AdaptorUser(UserListAc.this,mList));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        startActivity(new Intent(UserListAc.this,ChatList.class).putExtra("me", me).putExtra("other",mList.get(position) ));
                    }
                });
            }
        });

    }
}
