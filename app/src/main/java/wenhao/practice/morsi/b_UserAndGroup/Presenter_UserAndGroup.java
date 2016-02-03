package wenhao.practice.morsi.b_UserAndGroup;

import android.content.Context;

import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by wenhaowu on 01/02/16.
 */
public class Presenter_UserAndGroup {
    private String self_uid;
    private String own_name;

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
            public void onSuccess(ArrayList<Object_User> usrList, String self_name) {
                users = usrList;
                own_name = self_name;
                publish();
            }

            @Override
            public void onFail(FirebaseError error) {
                mError = error;
                publish();
            }
        });
    }

    public void onTakeView(View_fragment view){
        this.view = view;
        publish();
    }

    private void publish() {
        if (view != null){
            view.onItemNext(users,own_name );
        }
        else if(mError != null){
            view.onItemErr(mError);
        }
    }
}
