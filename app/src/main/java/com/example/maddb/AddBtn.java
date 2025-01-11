package com.example.maddb;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddBtn extends AppCompatActivity {

    private EditText STid, STname, STage, STemail, STcourse;
    private Button btnAdd;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_btn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        STid = findViewById(R.id.STId);
        STname = findViewById(R.id.STname);
        STage = findViewById(R.id.STage);
        STemail = findViewById(R.id.stemail);
        STcourse = findViewById(R.id.stcourse);
        btnAdd = findViewById(R.id.btnAdd);


        dbHelper = new DBHelper(this);



        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch details from EditTexts
                String id = STid.getText().toString().trim();
                String name = STname.getText().toString().trim();
                String ageStr = STage.getText().toString().trim();
                String email = STemail.getText().toString().trim();
                String course = STcourse.getText().toString().trim();

                // Validate input
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(id) ||
                        TextUtils.isEmpty(email) || TextUtils.isEmpty(course)) {
                    Toast.makeText(AddBtn.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age;
                int idi;

                try {
                    idi = Integer.parseInt(id);
                    age = Integer.parseInt(ageStr);
                } catch (NumberFormatException e) {
                    Toast.makeText(AddBtn.this, "Invalid age", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert into database
                boolean isInserted = dbHelper.insertStudent(idi,name, age, email, course);
                if (isInserted) {
                    Toast.makeText(AddBtn.this, "Student added successfully!", Toast.LENGTH_SHORT).show();
                    // Clear the input fields
                    clearFields();
                } else {
                    Toast.makeText(AddBtn.this, "Failed to add student", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        STid.setText("");
        STname.setText("");
        STage.setText("");
        STemail.setText("");
        STcourse.setText("");
    }


}