package android.com.spinner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //spinner
    Spinner allusers;
    //list to be set to spinner
    List<String> users = new ArrayList<>();
    // edittext for user's name and user's password
    EditText uname, upassword;
    // Button to add user to database
    Button adduser;
    ArrayAdapter<String> adapter;
    DbHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        allusers = (Spinner) findViewById(R.id.selectuser);
        uname = (EditText) findViewById(R.id.username);
        upassword = (EditText) findViewById(R.id.password);
        adduser = (Button) findViewById(R.id.adduser);
        db = new DbHandler(MainActivity.this);
        //handling click of adduser button
        adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString();
                String password = upassword.getText().toString();
                if (username.equalsIgnoreCase("") || password.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Please Enter Username and Password", Toast.LENGTH_SHORT).show();
                } else {
                    db.addUser(new User(username, password));
                    prepareData();
                    Toast.makeText(MainActivity.this, "User was Successfully added to Database", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //prepare  data for spinner
        prepareData();
        //handle click of spinner item
        allusers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // clicked item will be shown as spinner
                Toast.makeText(getApplicationContext(), "" + parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void prepareData() {
        users = db.getAllUsers();
        //adapter for spinner
        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, android.R.id.text1, users);
        //attach adapter to spinner
        allusers.setAdapter(adapter);
    }

   
}