package clc_cochin.clc;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    FragmentTransaction fragmentTransaction;
    private boolean shouldLoadProductFragOnBackPress = false;
    public static int navItemIndex = 0;
    public FirebaseAuth mAuth;
    private productRecyclerViewAdapter mAdapter;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private void Load_Product_fragment() {
        navItemIndex = 0;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.maincontainer,new ProductFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.Productfragment_Title);
        drawerLayout.closeDrawers();


    }
    private void Load_Service_fragment(){
        navItemIndex = 1;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.maincontainer,new ServiceFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.Servicefragmnet_Title);
        drawerLayout.closeDrawers();
        shouldLoadProductFragOnBackPress = true;

    }
    private void Load_Account_fragment(){
        navItemIndex = 2;
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.maincontainer,new AccountFragment());
        fragmentTransaction.commit();
        getSupportActionBar().setTitle(R.string.Accountfragment_Title);
        drawerLayout.closeDrawers();
        shouldLoadProductFragOnBackPress = true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.Toolbar_Layout);
        setSupportActionBar(toolbar);
        drawerLayout=(DrawerLayout) findViewById(R.id.Drawer_Layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);

        drawerLayout.openDrawer(Gravity.LEFT);

        Load_Product_fragment();

        navigationView= findViewById(R.id.Navigation_View);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.Productsfragment_ND:
                        Load_Product_fragment();
                        item.setChecked(true);
                        break;

                    case R.id.Servicefragment_ND:
                        Load_Service_fragment();
                        item.setChecked(true);
                        break;

                    case R.id.Accountfragemnt_ND:
                        Load_Account_fragment();
                        item.setChecked(true);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadProductFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex !=0) {
                navItemIndex = 0;
                shouldLoadProductFragOnBackPress = false;
                Load_Product_fragment();


                return;
            }
        }

        super.onBackPressed();
    }




    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public void del(View view) {db.collection("products").document("test")
            .delete()
            .addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {

                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

    }
}
//test
