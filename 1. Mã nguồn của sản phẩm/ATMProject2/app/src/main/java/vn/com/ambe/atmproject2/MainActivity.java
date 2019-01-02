package vn.com.ambe.atmproject2;

import android.app.FragmentManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import vn.com.ambe.fragment.HomeFragment;
import vn.com.ambe.fragment.SearchFragment;
import vn.com.ambe.fragment.SettingFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fr;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    public static String DATABASE_NAME="dbATM.sqlite";
    public static String DATABASE_SUFFIX_PATH="/databases/";
    public static SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        saoChepCSDLTuAssetVaoHeThong();
        setContentView(R.layout.activity_main);

        addControls();
        addEvents();


    }

    private void saoChepCSDLTuAssetVaoHeThong() {

        File dbFile=getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists()){
            try {
                coppy();
                Toast.makeText(this, getResources().getString(R.string.thanh_cong), Toast.LENGTH_SHORT).show();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void coppy() {

        try {
            InputStream inputStream=getAssets().open(DATABASE_NAME);
            String outFilePath=layDuongDan();

            File file=new File(getApplicationInfo().dataDir+DATABASE_SUFFIX_PATH);
            if (!file.exists()){
                file.mkdir();
            }

            OutputStream outputStream=new FileOutputStream(outFilePath);
            byte[] buffer=new byte[1024];
            int length;

            while ((length=inputStream.read(buffer))>0){
                outputStream.write(buffer,0,length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addEvents() {
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void addControls() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        database=openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        setSupportActionBar(toolbar);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        fr=getFragmentManager();
        fr.beginTransaction().replace(R.id.content,new HomeFragment()).commit();
        drawer.closeDrawers();
    }

    @Override
    public void onBackPressed() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        fr=getFragmentManager();
        int id = item.getItemId();

        switch (id){
            case R.id.menu_home:
                fr.beginTransaction().replace(R.id.content,new HomeFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                item.setChecked(true);
                drawer.closeDrawers();
                break;
            case R.id.menu_search:
                fr.beginTransaction().replace(R.id.content,new SearchFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                item.setChecked(true);
                drawer.closeDrawers();
                break;
            case R.id.menu_setting:
                fr.beginTransaction().replace(R.id.content,new SettingFragment()).commit();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
                item.setChecked(true);
                drawer.closeDrawers();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private String layDuongDan(){
        return getApplicationInfo().dataDir+DATABASE_SUFFIX_PATH+DATABASE_NAME;
    }
}
