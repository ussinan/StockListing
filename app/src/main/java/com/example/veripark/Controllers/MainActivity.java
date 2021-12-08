package com.example.veripark.Controllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.MenuItem;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;
import com.example.veripark.Models.HandshakeResponseModel;
import com.example.veripark.R;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Boolean isConnected = false;

    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;

    private HandshakeResponseModel handshakeModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);
        findViewById(R.id.button).setOnClickListener(this);

        Gson gson = new Gson();
        handshakeModel = gson.fromJson(getIntent().getStringExtra("handshakeModel"), HandshakeResponseModel.class);

        if(handshakeModel != null) {
            isConnected = handshakeModel.getIsSuccess();
        }

        drawerLayout = findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,R.string.nav_open, R.string.nav_close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();
                Intent intent = new Intent(MainActivity.this, StockListActivity.class);
                String type = "";

                switch (id){

                    case R.id.stocks:
                        type = "all";
                        break;
                    case R.id.increasing:
                        type = "increasing";
                        break;
                    case R.id.decreasing:
                        type = "decreasing";
                        break;
                    case R.id.vol_thirty:
                        type = "volume30";
                        break;
                    case R.id.vol_fifty:
                        type = "volume50";
                        break;
                    case R.id.vol_hundred:
                        type = "volume100";
                        break;
                    default:
                        break;
                }

                CryptionService.setAesKey(handshakeModel.aesKey);
                CryptionService.setAesIV(handshakeModel.aesIV);
                type = CryptionService.encrypt(type);

                intent.putExtra("type", type);
                drawerLayout.closeDrawer(GravityCompat.START);

                MainActivity.this.startActivity(intent);

                return true;
            }
        });

        if(!isConnected){
            Toast toast = Toast.makeText(getApplicationContext(), "Sunucuya bağlanırken bir sorunla karşılaşıldı", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.button) {
            if (!isConnected) {
                Toast toast = Toast.makeText(getApplicationContext(), "Sunucuya bağlanırken bir sorunla karşılaşıldı", Toast.LENGTH_LONG);
                toast.show();
            } else {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}