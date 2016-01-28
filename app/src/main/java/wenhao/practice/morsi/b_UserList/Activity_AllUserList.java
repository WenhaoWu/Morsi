package wenhao.practice.morsi.b_UserList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import wenhao.practice.morsi.Constant_ApplicationConstant;
import wenhao.practice.morsi.Obj_Usr;
import wenhao.practice.morsi.R;
import wenhao.practice.morsi.a_loginAndRegi.Activity_Login;

public class Activity_AllUserList extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public static final String TAG_UID = "tag_UID";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View mUsrListProgress;

    private boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_list_activity_userlist);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        String self_uid=null;
        if(getIntent().getStringExtra(TAG_UID)!=null){
            self_uid = getIntent().getStringExtra(TAG_UID);
        }

        mUsrListProgress = findViewById(R.id.usrList_progress);

        mRecyclerView = (RecyclerView) findViewById(R.id.RV_UserList);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        getUsrList(self_uid,new usrListCallback() {
            @Override
            public void onSuccess(ArrayList<Obj_Usr> usrs, String self_name) {
                mAdapter = new Adapter_UsrListAdapter(usrs,getBaseContext(), self_name);
                mRecyclerView.setAdapter(mAdapter);
            }
        });
    }



    private interface usrListCallback{
        void onSuccess(ArrayList<Obj_Usr> usrList, String self_name);
    }

    public void getUsrList(final String self_uid, final usrListCallback callback) {
        showProgress(true);

        Firebase usrListRef = new Firebase(Constant_ApplicationConstant.FirebaseURL+"/users");
        usrListRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("UsrCount", dataSnapshot.getChildrenCount() + "");
                ArrayList<Obj_Usr> usrs = new ArrayList<Obj_Usr>();
                String self_name = null;
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Log.e("usr", data.getValue().toString());
                    Obj_Usr usr = data.getValue(Obj_Usr.class);
                    if (data.getKey().equals(self_uid)){
                        self_name = usr.getUserName();
                    }
                    else {
                        usrs.add(usr);
                    }

                }
                callback.onSuccess(usrs,self_name);

                showProgress(false);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Toast.makeText(getBaseContext(),"Can't get the user list",Toast.LENGTH_SHORT).show();
                Log.e("UsrListErr", firebaseError.toString());
                showProgress(false);
            }
        });

    }

    private void showProgress(final boolean show) {

        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mUsrListProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        mUsrListProgress.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mUsrListProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce){
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this,"Click Back again to exti",Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity__all_user_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_logout) {
            SharedPreferences sp = getSharedPreferences("my_prefs",MODE_PRIVATE);
            SharedPreferences.Editor ed = sp.edit();
            ed.remove("UID");
            ed.commit();
            Intent intent = new Intent();
            intent.setClass(getBaseContext(), Activity_Login.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
