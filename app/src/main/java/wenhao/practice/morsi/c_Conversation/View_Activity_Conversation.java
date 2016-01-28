package wenhao.practice.morsi.c_Conversation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import wenhao.practice.morsi.R;


public class View_Activity_Conversation extends AppCompatActivity {

    public static final String Tag_usrFrom = "Usr_From";
    public static final String Tag_usrTo = "Usr_To";

    private RecyclerView mRecyclerView;
    private View_Adapter_Conversation mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private EditText et_msg;
    private Button btn_send;

    private static Presenter_Conversation presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conversation_activity_main);

        String usr_from = null, usr_to = null;
        if (getIntent().getStringExtra(Tag_usrFrom)!=null && getIntent().getStringExtra(Tag_usrTo)!= null){
            usr_from = getIntent().getStringExtra(Tag_usrFrom);
            usr_to = getIntent().getStringExtra(Tag_usrTo);
        }

        mRecyclerView = (RecyclerView)findViewById(R.id.conv_RV);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new View_Adapter_Conversation(this);
        mRecyclerView.setAdapter(mAdapter);

        if (presenter == null){
            presenter = new Presenter_Conversation(usr_from,usr_to);
        }
        presenter.prepareItem();
        presenter.onTakeView(this);

        this.getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN
        );

        et_msg = (EditText)findViewById(R.id.conv_Rel_ET);
        btn_send = (Button)findViewById(R.id.conv_Rel_Btn);
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et_msg.getText().toString().isEmpty()){
                    presenter.addItem(et_msg.getText().toString());
                    et_msg.setText("");
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onTakeView(null);
        if (isFinishing()){
            presenter = null;
        }
    }

    public void onItemNext(ArrayList<Object_msg> msgs) {
        mAdapter.setDataList(msgs);
        mRecyclerView.smoothScrollToPosition(msgs.size()-1);
    }

    public void onItemErr(FirebaseError mErr) {
        Toast.makeText(this,mErr.toString(),Toast.LENGTH_SHORT).show();
    }
}
