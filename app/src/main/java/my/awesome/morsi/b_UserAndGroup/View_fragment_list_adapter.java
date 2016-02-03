package my.awesome.morsi.b_UserAndGroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import my.awesome.morsi.R;
import my.awesome.morsi.c_Conversation.View_Activity_Conversation;

/**
 * Created by wenhaowu on 18/01/16.
 */
public class View_fragment_list_adapter extends RecyclerView.Adapter<View_fragment_list_adapter.mViewHolder>{

    private ArrayList<Object_User> usrList;
    private Context mContext;
    private String self_name;

    public View_fragment_list_adapter(ArrayList<Object_User> usrList, Context mContext, String self_name) {
        this.usrList = usrList;
        this.mContext = mContext;
        this.self_name = self_name;
    }

    public View_fragment_list_adapter(Context mContext) {
        this.usrList = new ArrayList<>();
        this.self_name = null;
        this.mContext = mContext;
    }

    public void setUsrList(ArrayList<Object_User> usrList) {
        this.usrList = usrList;
        notifyDataSetChanged();
    }

    public void setSelf_name(String self_name) {
        this.self_name = self_name;
        notifyDataSetChanged();
    }

    public static class mViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public static interface IMyViewHolderClicks {
           void onSelect(View caller, int position);
        }

        private ImageView iv_avatar;
        private TextView tv_name;
        public IMyViewHolderClicks mListener;


        public mViewHolder(View itemView, IMyViewHolderClicks listener) {
            super(itemView);
            this.mListener = listener;
            this.iv_avatar = (ImageView)itemView.findViewById(R.id.UsrList_img);
            this.tv_name = (TextView)itemView.findViewById(R.id.UsrList_txt);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
           mListener.onSelect(v,getLayoutPosition());
        }
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ug_layout_rv_item, parent, false);
        mViewHolder vh = new mViewHolder(v, new mViewHolder.IMyViewHolderClicks() {
            @Override
            public void onSelect(View caller, int position) {
                Intent intent = new Intent();
                intent.putExtra(View_Activity_Conversation.Tag_usrTo, usrList.get(position).getUserName());
                intent.putExtra(View_Activity_Conversation.Tag_usrFrom, self_name);
                intent.setClass(mContext, View_Activity_Conversation.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        return vh;
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position) {
        holder.tv_name.setText(usrList.get(position).getUserName());

        String uri = "drawable/"+"avatar"+usrList.get(position).getUserAvatar();
        Log.e("Avatar", uri);

        int imageRes = mContext.getResources().getIdentifier(uri,null,mContext.getPackageName());
        holder.iv_avatar.setImageResource(imageRes);


    }

    @Override
    public int getItemCount() {
        return usrList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
