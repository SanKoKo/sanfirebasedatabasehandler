package net.sandevelopment.sanfirebasedbhandler.adaptor;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import net.sandevelopment.sanfirebasedatabasehandler.ChatMessage;
import net.sandevelopment.sanfirebasedbhandler.R;

import java.util.List;

public class RVChatMessageAdaptor extends RecyclerView.Adapter<RVChatMessageAdaptor.PlaceHolder> {
    private Context context;
    private List<ChatMessage> chatMessageList;
    private String meUid;

    public RVChatMessageAdaptor(Context context, List<ChatMessage> chatMessageList, String meUid) {
        this.context = context;
        this.chatMessageList = chatMessageList;
        this.meUid = meUid;
    }

    @Override
    public PlaceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PlaceHolder(LayoutInflater.from(context).inflate(R.layout.chat_list, parent,false));
    }

    @Override
    public void onBindViewHolder(PlaceHolder holder, int position) {
        if(chatMessageList.get(position).getChatFromUserUid().equals(meUid)){
            holder.tvChatRight.setText(chatMessageList.get(position).getChatMessage());
            holder.frameLayoutRight.setVisibility(View.VISIBLE);
            holder.frameLayoutLeft.setVisibility(View.INVISIBLE);
        } else {
            holder.tvChatLeft.setText(chatMessageList.get(position).getChatMessage());
            holder.frameLayoutLeft.setVisibility(View.VISIBLE);
            holder.frameLayoutRight.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return chatMessageList.size();
    }

    public class PlaceHolder extends RecyclerView.ViewHolder {
        TextView tvChatLeft,tvChatRight;
        FrameLayout frameLayoutLeft,frameLayoutRight;
        public PlaceHolder(View itemView) {
            super(itemView);
            tvChatLeft = itemView.findViewById(R.id.tv_chat_left);
            tvChatRight = itemView.findViewById(R.id.tv_chat_right);
            frameLayoutLeft = itemView.findViewById(R.id.frameLeft);
            frameLayoutRight = itemView.findViewById(R.id.frameRight);
        }
    }
}
