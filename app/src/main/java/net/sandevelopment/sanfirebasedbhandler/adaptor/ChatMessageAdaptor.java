package net.sandevelopment.sanfirebasedbhandler.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.sandevelopment.sanfirebasedatabasehandler.ChatMessage;
import net.sandevelopment.sanfirebasedbhandler.R;

import java.util.List;

public class ChatMessageAdaptor extends BaseAdapter {
    private Context context;
    private List<ChatMessage> chatMessageList;
    private String me;
    private Holder holder;
    public ChatMessageAdaptor(Context context, List<ChatMessage> chatMessageList, String meUid) {
        holder = new Holder();
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.me = meUid;
    }

    @Override
    public int getCount() {
        return chatMessageList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View convertView = view;
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.chat_list, viewGroup,false);
        holder.tvChatLeft = convertView.findViewById(R.id.tv_chat_left);
        holder.tvChatRight = convertView.findViewById(R.id.tv_chat_right);
        holder.frameLayoutLeft = convertView.findViewById(R.id.frameLeft);
        holder.frameLayoutRight = convertView.findViewById(R.id.frameRight);
            convertView.setTag(holder);
       } else {
            holder = (Holder) convertView.getTag();
        }

        if(chatMessageList.get(position).getChatFromUserUid().equals(me)){
            holder.tvChatRight.setText(chatMessageList.get(position).getChatMessage());
            holder.frameLayoutRight.setVisibility(View.VISIBLE);
            holder.frameLayoutLeft.setVisibility(View.INVISIBLE);
        } else {
            holder.tvChatLeft.setText(chatMessageList.get(position).getChatMessage());
            holder.frameLayoutLeft.setVisibility(View.VISIBLE);
            holder.frameLayoutRight.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }
    static class  Holder{
        TextView tvChatLeft;
        FrameLayout frameLayoutLeft;
        TextView tvChatRight;
        FrameLayout frameLayoutRight;
    }
}
