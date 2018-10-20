package net.sandevelopment.sanfirebasedbhandler.adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.sandevelopment.sanfirebasedbhandler.R;
import net.sandevelopment.sanfirebasedbhandler.model.ChatUser;

import java.util.List;

public class AdaptorUser extends BaseAdapter {
    private List<ChatUser> chatUsers;
    private Context context;

    public AdaptorUser( Context context,List<ChatUser> chatUsers) {
        this.chatUsers = chatUsers;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatUsers.size();
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
        Holder holder=new Holder();
        if(convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_layout, viewGroup,false);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.tvUser = convertView.findViewById(R.id.tv_user);
        holder.tvEmail = convertView.findViewById(R.id.tv_email);
        holder.tvUser.setText(chatUsers.get(position).getUserName());
        holder.tvEmail.setText(chatUsers.get(position).getEmail());

        return convertView;
    }
    class Holder{
        TextView tvUser;
        TextView tvEmail;
    }
}
