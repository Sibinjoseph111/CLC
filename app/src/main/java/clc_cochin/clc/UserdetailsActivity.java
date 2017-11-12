package clc_cochin.clc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class UserdetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
    }

    public void Adduserdetails(View view) {
        Intent intent= new Intent(UserdetailsActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
