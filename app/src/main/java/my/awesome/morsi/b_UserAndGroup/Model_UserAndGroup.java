package my.awesome.morsi.b_UserAndGroup;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.WindowManager;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import my.awesome.morsi.Constant_ApplicationConstant;
import my.awesome.morsi.R;

/**
 * Created by wenhaowu on 01/02/16.
 */
public class Model_UserAndGroup {

    private Context mContext;

    public Model_UserAndGroup(Context mContext) {
        this.mContext = mContext;
    }

    public interface usrListCallback{
        void onSuccess(ArrayList<Object_User> usrList, String self_name, String self_avatar);
        void onFail(FirebaseError error);
    }
    public void getUsrList(final String self_uid, final usrListCallback callback) {
        final ProgressDialog pd = createProgressDialog(mContext);
        pd.show();

        Firebase usrListRef = new Firebase(Constant_ApplicationConstant.FirebaseURL+"/users");
        usrListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("UsrCount", dataSnapshot.getChildrenCount() + "");
                ArrayList<Object_User> usrs = new ArrayList<Object_User>();
                String self_name = null, self_avatar=null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.e("usr", data.getValue().toString());
                    Object_User usr = data.getValue(Object_User.class);
                    if (data.getKey().equals(self_uid)){
                        self_name = usr.getUserName();
                        self_avatar = usr.getUserAvatar();
                    }
                    else {
                        usrs.add(usr);
                    }

                }

                callback.onSuccess(usrs,self_name, self_avatar);

                pd.dismiss();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                callback.onFail(firebaseError);
                /*
                Toast.makeText(getBaseContext(), "Can't get the user list", Toast.LENGTH_SHORT).show();
                Log.e("UsrListErr", firebaseError.toString());
                */
                pd.dismiss();
            }
        });

    }


    public static ProgressDialog createProgressDialog(Context mContext){
        ProgressDialog result = new ProgressDialog(mContext);
        try {
            result.show();
        }
        catch (WindowManager.BadTokenException e){

        }
        result.setCancelable(true);
        result.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        result.setContentView(R.layout.util_layout_progress_dialog);
        result.dismiss();
        return result;
    }
}
