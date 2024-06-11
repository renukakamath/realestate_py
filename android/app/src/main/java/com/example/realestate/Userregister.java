package com.example.realestate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class Userregister extends AppCompatActivity implements JsonResponse {
    EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
    Button b1;


    String fname,lname,housename,place,phone,pincode,email,user,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userregister);

        e1=(EditText)findViewById(R.id.etfname);
        e2=(EditText)findViewById(R.id.etlname);
        e6=(EditText)findViewById(R.id.etplace);

        e4=(EditText)findViewById(R.id.etemail);




        e3=(EditText)findViewById(R.id.etphone);
        e8=(EditText)findViewById(R.id.etuname);
        e9=(EditText)findViewById(R.id.etpass);
        b1=(Button)findViewById(R.id.btsignup);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fname=e1.getText().toString();
                lname=e2.getText().toString();
                phone=e3.getText().toString();
                email=e4.getText().toString();


                place=e6.getText().toString();


                user=e8.getText().toString();
                password=e9.getText().toString();


                if(fname.equalsIgnoreCase("")|| !fname.matches("[a-zA-Z ]+"))
                {
                    e1.setError("Enter your firstname");
                    e1.setFocusable(true);
                }
                else if(lname.equalsIgnoreCase("")|| !lname.matches("[a-zA-Z ]+"))
                {
                    e2.setError("Enter your lastname");
                    e2.setFocusable(true);
                }


                else if(place.equalsIgnoreCase("")|| !place.matches("[a-zA-Z ]+"))
                {
                    e6.setError("Enter your place");
                    e6.setFocusable(true);
                }
                else if(phone.equalsIgnoreCase("")|| phone.length()!=10)
                {
                    e3.setError("Enter your phone");
                    e3.setFocusable(true);
                }

                else if(email.equalsIgnoreCase("")|| !email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"))
                {
                    e4.setError("Enter your email");
                    e4.setFocusable(true);
                }
                else if(user.equalsIgnoreCase(""))
                {
                    e8.setError("Enter your username");
                    e8.setFocusable(true);
                }
                else if(password.equalsIgnoreCase(""))
                {
                    e9.setError("Enter your password");
                    e9.setFocusable(true);
                }
                else {
                    JsonReq JR = new JsonReq();
                    JR.json_response = (JsonResponse) Userregister.this;
                    String q ="/userregister?fname="+fname+"&lname="+lname+"&place="+place+"&phone="+phone+"&email="+email+"&username="+user+"&password="+password;
                    q = q.replace(" ","%20");
                    JR.execute(q);
                }
            }
        });

    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("pearl", status);


            if (status.equalsIgnoreCase("success")) {
                Toast.makeText(getApplicationContext(), "REGISTRATION SUCCESS", Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(), Login.class));

            } else if (status.equalsIgnoreCase("duplicate")) {
                startActivity(new Intent(getApplicationContext(), Userregister.class));
                Toast.makeText(getApplicationContext(), "Username and Password already Exist...", Toast.LENGTH_LONG).show();

            } else {
                startActivity(new Intent(getApplicationContext(), Userregister.class));

                Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
            }

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent b=new Intent(getApplicationContext(),Login.class);
        startActivity(b);
    }
}
