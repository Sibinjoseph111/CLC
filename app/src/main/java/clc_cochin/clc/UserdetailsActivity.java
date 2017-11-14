package clc_cochin.clc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UserdetailsActivity extends AppCompatActivity {

    boolean userflag= false;

    private static final String TAG = "MyActivity";

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText Username,City,Mobileno,Locality,Buildingno,Pincode;
    Button Addudetails;
    FirebaseUser Currentuser;
    FirebaseAuth mAuth;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdetails);
        Currentuser= mAuth.getInstance().getCurrentUser();
        final String Userid= Currentuser.getUid();

        Username = findViewById(R.id.Username_ET);
        City = findViewById(R.id.City_ET);
        Mobileno=findViewById(R.id.Phone_ET);
        Locality=findViewById(R.id.Area_ET);
        Buildingno=findViewById(R.id.BuildingNo_ET);
        Pincode=findViewById(R.id.Pincode_ET);
        Addudetails=findViewById(R.id.Adddetails_BT);




        Addudetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Username.getText().toString();
                String mobile=Mobileno.getText().toString();
                String cityy=City.getText().toString();
                String area=Locality.getText().toString();
                String building=Buildingno.getText().toString();
                String pincode=Pincode.getText().toString();

                Map<String, Object> city = new HashMap<>();
                city.put("name",name );
                city.put("Mobile",mobile );
                city.put("City",cityy );
                city.put("Area",area );
                city.put("Building",building );
                city.put("Pincode",pincode );


                db.collection("usrrr").document(Userid)
                        .set(city)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully written!");
                                userflag= true;
                                Toast.makeText(UserdetailsActivity.this,"Data added successfully",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error writing document", e);
                                userflag= false;
                                Toast.makeText(UserdetailsActivity.this,"errod db",Toast.LENGTH_SHORT).show();
                            }
                        });

                if(userflag=true){
                    Intent intent= new Intent(UserdetailsActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        });






    }


}
