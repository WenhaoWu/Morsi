package wenhao.practice.morsi.c_Conversation;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import wenhao.practice.morsi.R;

/**
 * Created by wenhaowu on 25/01/16.
 */
public class View_Adapter_Conversation extends RecyclerView.Adapter<View_Adapter_Conversation.mViewHolder>{

    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;

    private Context mContext;
    private ArrayList<Object_msg> dataList = new ArrayList<>();

    public View_Adapter_Conversation(Context mContext) {
        mContext = mContext;
    }

    public void setDataList(ArrayList<Object_msg> dataList) {
        Collections.sort(dataList);
        this.dataList.clear();
        this.dataList.addAll(dataList);
        notifyDataSetChanged();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder{

        private TextView msgSender;
        private TextView msgTime;
        private TextView msgContent;

        public mViewHolder(View itemView) {
            super(itemView);
            this.msgSender = (TextView)itemView.findViewById(R.id.msgSender);
            this.msgTime = (TextView)itemView.findViewById(R.id.msgDate);
            this.msgContent = (TextView)itemView.findViewById(R.id.msg);
        }

    }

    @Override
    public int getItemViewType(int position) {
        return dataList.get(position).getDirection();
    }

    @Override
    public View_Adapter_Conversation.mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=null;

        if (viewType == DIRECTION_OUTGOING){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_layout_right_message,parent,false);
        }
        else if(viewType == DIRECTION_INCOMING){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.conversation_layout_left_message,parent,false);
        }

        mViewHolder vh = new mViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(View_Adapter_Conversation.mViewHolder holder, int position) {
        holder.msgContent.setText(dataList.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
