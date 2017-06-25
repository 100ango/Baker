package sani.ango.bakingapp.ui.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import sani.ango.bakingapp.R;
import sani.ango.bakingapp.ui.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG_FRAGMENT = "Fragment";
    private MainFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar menuToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        //setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle(getString(R.string.main_title));
        //getSupportActionBar().setLogo(R.drawable.menu_icon);
        //menuToolbar.setLogo(R.mipmap.menu_icon);

        FragmentManager fm = getSupportFragmentManager();
        fragment = (MainFragment) fm.findFragmentByTag(TAG_FRAGMENT);

        if (fragment == null){
            fragment = new MainFragment();
            fm.beginTransaction().add(fragment, TAG_FRAGMENT).commit();
        }
        //else{
            //FragmentManager f = getSupportFragmentManager();
            //MainFragment m = new MainFragment();
            //f.beginTransaction().add(R.id.fragment, m).commit();
        //}
    }

    //@Override
    //protected void onResume() {
        //super.onResume();

        //FragmentManager fm = getSupportFragmentManager();
        //fragment = (MainFragment) fm.findFragmentByTag(TAG_FRAGMENT);

        //if (fragment == null){
            //fragment = new MainFragment();
            //fm.beginTransaction().add(fragment, TAG_FRAGMENT).commit();
        //}
    //}
}
