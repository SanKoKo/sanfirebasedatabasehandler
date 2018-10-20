package net.sandevelopment.sanfirebasedbhandler.ac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import net.sandevelopment.sanfirebasedatabasehandler.ChatMessage;
import net.sandevelopment.sanfirebasedatabasehandler.SanFireDbResult;
import net.sandevelopment.sanfirebasedbhandler.R;
import net.sandevelopment.sanfirebasedbhandler.adaptor.ChatMessageAdaptor;
import net.sandevelopment.sanfirebasedbhandler.adaptor.RVChatMessageAdaptor;
import net.sandevelopment.sanfirebasedbhandler.model.ChatUser;

import java.util.List;

public class ChatList extends AppCompatActivity {

    ChatUser me,other;
    //ListView lv;
    EditText edChat;
    Button btSend;
    RecyclerView rv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        me = (ChatUser) getIntent().getExtras().get("me");
        other = (ChatUser) getIntent().getExtras().get("other");
       // lv = findViewById(R.id.ls_chatlist);
        rv = findViewById(R.id.rvChating);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        rv.setLayoutManager(llm);
        edChat = findViewById(R.id.ed_chatlist);
        btSend = findViewById(R.id.bt_chatlist);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            ChatMessage chatMessage = new ChatMessage(me.getUid(),other.getUid(),edChat.getText().toString(),me.getUserName(),other.getUserName());
            SanFireDbResult.chat(chatMessage);
            edChat.setText("");
            }
        });

        new SanFireDbResult<ChatMessage>().getChatListCallback(me.getUid(), other.getUid(), new SanFireDbResult.CallbackResult<ChatMessage>() {
            @Override
            public void result(List<ChatMessage> mList) {
                //ChatMessageAdaptor chatMessageAdaptor = new ChatMessageAdaptor(ChatList.this,mList,me.getUid());
               // lv.setAdapter(chatMessageAdaptor);
                RVChatMessageAdaptor rvChatMessageAdaptor = new RVChatMessageAdaptor(ChatList.this,mList,me.getUid());
                rv.setAdapter(rvChatMessageAdaptor);
            }
        });

    }
}
