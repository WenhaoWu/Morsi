package wenhao.practice.morsi.c_Conversation;

import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by wenhaowu on 25/01/16.
 */
public class Presenter_Conversation {

    private ArrayList<Object_msg> msgs = new ArrayList<>();
    private FirebaseError mErr = null;

    private View_Activity_Conversation view;
    private Model_Conversation model;

    public Presenter_Conversation(String usr_from, String usr_to){
        model = new Model_Conversation(usr_from, usr_to);
    }

    public void prepareItem(){
        model.getMsgs(new Model_Conversation.msgCallBack() {
            @Override
            public void onSuccess(ArrayList<Object_msg> items) {
                msgs = items;
                publish();
            }

            @Override
            public void onFail(FirebaseError err) {
                mErr = err;
                publish();
            }
        });
    }

    public void addItem(String msg){
        model.cleanup();

        model.talk(msg, new Model_Conversation.talkCallBack() {
            @Override
            public void onFail(FirebaseError err) {
                mErr = err;
                publish();
            }

            @Override
            public void onSuccess() {
                prepareItem();
            }
        });
    }

    public void onTakeView(View_Activity_Conversation view){
        this.view = view;
        publish();
    }

    private void publish() {
        if (view != null){
            if (!msgs.isEmpty()){
                view.onItemNext(msgs);
            }
            else if(mErr != null){
                view.onItemErr(mErr);
            }
        }
    }


}
