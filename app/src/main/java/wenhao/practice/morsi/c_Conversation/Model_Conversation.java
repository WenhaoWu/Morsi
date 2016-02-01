package wenhao.practice.morsi.c_Conversation;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import wenhao.practice.morsi.Constant_ApplicationConstant;

/**
 * Created by wenhaowu on 22/01/16.
 */
public class Model_Conversation {

    public static final int DIRECTION_INCOMING = 0;
    public static final int DIRECTION_OUTGOING = 1;

    private Firebase fromRef;
    private Firebase toRef;

    private ChildEventListener mListener;
    private ChildEventListener mListener1;

    public Model_Conversation(String usr_from, String usr_to) {
        Firebase rootBase = new Firebase(Constant_ApplicationConstant.FirebaseURL);
        this.fromRef = rootBase.child("msg").child(usr_from).child(usr_to);
        this.toRef = rootBase.child("msg").child(usr_to).child(usr_from);
    }


    public interface msgCallBack{
        void onSuccess(ArrayList<Object_msg> items);
        void onFail(FirebaseError err);
    }
    public void getMsgs(final msgCallBack cb){


        final ArrayList<Object_msg> msgList = new ArrayList<>();

        mListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object_msg msg;
                Long ts;
                String content;
                ts = Long.valueOf(dataSnapshot.getKey());
                content = String.valueOf(dataSnapshot.getValue());
                msg = new Object_msg(ts, content, DIRECTION_OUTGOING);
                msgList.add(msg);
                cb.onSuccess(msgList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                cb.onFail(firebaseError);
            }
        };

        mListener1 = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object_msg msg;
                Long ts;
                String content;
                ts = Long.valueOf(dataSnapshot.getKey());
                content = String.valueOf(dataSnapshot.getValue());
                msg = new Object_msg(ts, content, DIRECTION_INCOMING);
                msgList.add(msg);
                cb.onSuccess(msgList);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                cb.onFail(firebaseError);
            }
        };

        fromRef.addChildEventListener(mListener);
        toRef.addChildEventListener(mListener1);

    }

    public void cleanup(){
        fromRef.removeEventListener(mListener);
        toRef.removeEventListener(mListener1);
    }

    public interface talkCallBack{
        void onFail(FirebaseError err);
        void onSuccess();
    }
    public void talk(String msg, final talkCallBack cb){
        Long tsLong = System.currentTimeMillis()/1000;
        fromRef.child(String.valueOf(tsLong)).setValue(msg, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null){
                    cb.onFail(firebaseError);
                }
                else {
                    cb.onSuccess();
                }
            }
        });
    }



}
