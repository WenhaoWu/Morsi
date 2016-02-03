package wenhao.practice.morsi.b_UserAndGroup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import wenhao.practice.morsi.R;

/**
 * Created by wenhaowu on 01/02/16.
 */
public class View_fragment extends android.support.v4.app.Fragment{

    private final static String ARG_IDX = "Tab_Idx";
    private final static String ARG_UID = "Self_UID";

    private static Presenter_UserAndGroup presenter;

    private RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;
    private View_fragment_list_adapter adapter;

    public View_fragment() {
    }

    public static Fragment newInstance(int id, String self_uid) {
        View_fragment fragment = new View_fragment();

        Bundle args = new Bundle();
        args.putInt(ARG_IDX, id);
        args.putString(ARG_UID, self_uid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (presenter == null){
            presenter = new Presenter_UserAndGroup(getContext(), getArguments().getString(ARG_UID));
        }

        //get the ui
        CoordinatorLayout myView = (CoordinatorLayout)inflater.inflate(R.layout.ug_main_fragment, container, false);
        FloatingActionButton fab = (FloatingActionButton)myView.findViewById(R.id.UG_Frag_Fab);
        rv = (RecyclerView)myView.findViewById(R.id.UG_Frag_RV);

        //deal with rv
        rv.setHasFixedSize(true);

        adapter = new View_fragment_list_adapter(getContext());
        switch (getArguments().getInt(ARG_IDX)){
            case 0:
                mLayoutManager = new LinearLayoutManager(getContext());
                presenter.prepareUser();
                presenter.onTakeView(this);
                fab.setVisibility(View.GONE);
                break;
            case 1:
                mLayoutManager = new GridLayoutManager(getContext(),2);
                break;
            default:
                mLayoutManager = new LinearLayoutManager(getContext());
                break;
        }


        rv.setLayoutManager(mLayoutManager);
        rv.setAdapter(adapter);


        return myView;
    }

    public void onItemErr(FirebaseError mError) {
        Log.e("UGFirebaseErr",mError.toString());
        Toast.makeText(getContext(), mError.getMessage(), Toast.LENGTH_LONG).show();
    }

    public void onItemNext(ArrayList<Object_User> users, String self_name) {
        adapter.setSelf_name(self_name);
        adapter.setUsrList(users);
    }
}
