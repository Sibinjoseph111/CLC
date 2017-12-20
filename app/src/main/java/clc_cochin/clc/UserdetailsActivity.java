package clc_cochin.clc;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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



    public boolean ValidateForm(int[] ids){

        boolean isempty= false;
        for (int id:ids){

            EditText et= findViewById(id);

            if (TextUtils.isEmpty(et.getText().toString())){
                et.setError("Must enter value");
                isempty=true;
            }
        }
     return isempty;

    }


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

        Mobileno.setText(Currentuser.getPhoneNumber());

        final int[] ids= new int[]{
                R.id.Username_ET,R.id.City_ET,R.id.City_ET,R.id.Phone_ET,R.id.Area_ET,R.id.BuildingNo_ET,R.id.Pincode_ET
        };



        Addudetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=Username.getText().toString();
                String mobile=Mobileno.getText().toString();
                String cityy=City.getText().toString();
                String area=Locality.getText().toString();
                String building=Buildingno.getText().toString();
                String pincode=Pincode.getText().toString();

                if (!ValidateForm(ids))
                {

                Map<String, Object> user = new HashMap<>();
                user.put("name",name );
                user.put("Mobile",mobile );
                user.put("City",cityy );
                user.put("Area",area );
                user.put("Building",building );
                user.put("Pincode",pincode );


                db.collection("usrrr").document(Userid)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                userflag= true;
                                Toast.makeText(UserdetailsActivity.this,"Data added successfully",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                userflag= false;
                                Toast.makeText(UserdetailsActivity.this,"Errod creating Database",Toast.LENGTH_SHORT).show();
                            }
                        });

                if(!userflag){
                    Intent intent= new Intent(UserdetailsActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else {
                    Intent oldintent= new Intent(UserdetailsActivity.this,LoginActivity.class);
                    startActivity(oldintent);
                    finish();

                }

            }

            else {
                    Toast.makeText(UserdetailsActivity.this, "Please complete all the fields",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



}
