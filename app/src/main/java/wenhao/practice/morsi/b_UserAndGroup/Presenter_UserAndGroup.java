package wenhao.practice.morsi.b_UserAndGroup;

import android.content.Context;
import android.util.Log;

import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by wenhaowu on 01/02/16.
 */
public class Presenter_UserAndGroup {
    private String self_uid;

    private ArrayList<Object_User> users = new ArrayList<>();
    private FirebaseError mError = null;

    private View_fragment view;
    private Model_UserAndGroup model;

    public Presenter_UserAndGroup(Context mContext, String self_uid) {
        model = new Model_UserAndGroup(mContext);
        this.self_uid = self_uid;
    }

    public void prepareUser(){

        model.getUsrList(self_uid, new Model_UserAndGroup.usrListCallback() {
            @Override
            public void onSuccess(ArrayList<Object_User> usrList, String self_name, String self_avatar) {
                users = usrList;
                publish(self_name,self_avatar);
            }

            @Override
            public void onFail(FirebaseError error) {
                mError = error;
                publish("try",null);
            }
        });
    }

    public void onTakeView(View_fragment view){
        this.view = view;
    }

    private void publish(String own_name, String own_avatar) {
        Log.e("Selfname123", own_name);
        if (view != null){
            view.onItemNext(users, own_name, own_avatar);
        }
        else if(mError != null){
            view.onItemErr(mError);
        }
    }
}
