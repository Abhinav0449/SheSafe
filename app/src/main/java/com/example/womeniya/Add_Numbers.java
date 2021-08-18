package com.example.womeniya;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class Add_Numbers extends AppCompatActivity {
    EditText Number1, Number2;
    db database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_numbers);

        getSupportActionBar().hide();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        database = new db(this, null, null, 1);
        if (database.number() == 2) {
            String phone1 = database.databaseToPhoneFirst();
            String phone2 = database.databaseToPhoneSecond();
            Number1 = findViewById(R.id.Number1);
            Number2 = findViewById(R.id.Number2);
            Number1.setHint(phone1);
            Number2.setHint(phone2);

        }
    }

    public void addcontact1(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        SomeActivity.launch(intent);

    }

    public void addcontact2(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        SomeActivity.launch(intent);
    }

    ActivityResultLauncher<Intent> SomeActivity = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
            }
        }
    });

    public void Onclick(View view) {
        Number1 = findViewById(R.id.Number1);
        Number2 = findViewById(R.id.Number2);
        String n1 = Number1.getText().toString();
        String n2 = Number2.getText().toString();
        if (n1.length() != 10 || n2.length() != 10) {
            Toast.makeText(Add_Numbers.this, "Invalid Phone Number...Please Enter 10 digit phone numbers", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Number1", n1);
            intent.putExtra("Number2", n2);
            startActivity(intent);
            overridePendingTransition(R.anim.animation_in, R.anim.animation_out);
        }
    }

}
