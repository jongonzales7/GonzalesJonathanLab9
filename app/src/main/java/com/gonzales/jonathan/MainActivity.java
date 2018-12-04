package com.gonzales.jonathan;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase db;
    DatabaseReference persons;
    EditText eFullname, eAge, eGender;
    TextView tFullname, tAge, tGender;
    ArrayList<Person> personList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = FirebaseDatabase.getInstance("https://gonzalesjonathanlab9.firebaseio.com/");
        persons = db.getReference("persons");
        eFullname = findViewById(R.id.etFullname);
        eAge = findViewById(R.id.etAge);
        eGender = findViewById(R.id.etGender);
        tFullname = findViewById(R.id.tvFullname);
        tAge = findViewById(R.id.tvAge);
        tGender = findViewById(R.id.tvGender);
        personList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        persons.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                personList.clear();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Person p = dataSnapshot1.getValue(Person.class);
                    personList.add(p);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void addRecord(View v) {
        tFullname.setText("");
        tAge.setText("");
        tGender.setText("");

        String fullname = eFullname.getText().toString().trim();
        int age;
        String gender = eGender.getText().toString().trim();

        if((eAge.getText().toString().trim()).length() <= 0){
            Toast.makeText(this, "All fields are required. Please input missing fields...", Toast.LENGTH_LONG).show();
            return;
        }else{
           try{
                age = Integer.parseInt(eAge.getText().toString().trim());
           }catch (Exception ex){
               Toast.makeText(this, "Please input number only...", Toast.LENGTH_LONG).show();
               return;
           }

        }

        if(fullname.length() <= 0 ||  gender.length() <= 0){
            Toast.makeText(this, "All fields are required. Please input missing fields...", Toast.LENGTH_LONG).show();
            return;
        }

        for (int x = 0; x < personList.size(); x++){
            if((personList.get(x).getFullname()).equals(fullname)){
                Toast.makeText(this, "Record already exists...", Toast.LENGTH_LONG).show();
                return;
            }

        }

        String key = persons.push().getKey();
        Person person = new Person(fullname, gender, age);
        persons.child(key).setValue(person);
        Toast.makeText(this, "Record saved...", Toast.LENGTH_LONG).show();
        eFullname.setText("");
        eAge.setText("");
        eGender.setText("");
    }

    public void retrieveRecord(View v) {
        tFullname.setText("");
        tAge.setText("");
        tGender.setText("");

        int count = 0;
        String fullname = eFullname.getText().toString().trim();
        if(fullname.length() <= 0) {
            Toast.makeText(this, "Please enter fullname...", Toast.LENGTH_LONG).show();
            return;
        }

       for (int x = 0; x < personList.size(); x++){
            if((personList.get(x).getFullname()).equals(fullname)){
                tFullname.setText(personList.get(x).getFullname());
                tAge.setText(personList.get(x).getAge()+"");
                tGender.setText(personList.get(x).getGender());
                Toast.makeText(this, "Record retrieved...", Toast.LENGTH_LONG).show();
                eFullname.setText("");
                eAge.setText("");
                eGender.setText("");
                count = 1;
            }

        }

        if(count == 0){
            Toast.makeText(this, "Record doesn't exist...", Toast.LENGTH_LONG).show();
        }

    }
}
