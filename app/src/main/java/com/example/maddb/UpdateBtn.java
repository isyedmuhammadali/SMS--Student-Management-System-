package com.example.maddb;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class UpdateBtn extends AppCompatActivity {

    private EditText stdID, stdName, stdAge, stdEmail, stdCourse;
    private Button  btnUpdate;
    private ImageView btnSearch;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_btn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        stdID = findViewById(R.id.STid);
        stdName = findViewById(R.id.STname);
        stdAge = findViewById(R.id.STage);
        stdEmail = findViewById(R.id.STemail);
        stdCourse = findViewById(R.id.STcourse);
        btnSearch = findViewById(R.id.btnSearch);
        btnUpdate = findViewById(R.id.btnUpdate);


        dbHelper = new DBHelper(this);

        // Search button click listener
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentIdStr = stdID.getText().toString().trim();

                if (TextUtils.isEmpty(studentIdStr)) {
                    Toast.makeText(UpdateBtn.this, "Please enter a valid Student ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int studentId = Integer.parseInt(studentIdStr);
                    // Fetch student data from the database
                    Cursor cursor = dbHelper.getAllStudents();
                    boolean isFound = false;

                    while (cursor.moveToNext()) {
                        int id = cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID));
                        if (id == studentId) {
                            // Populate EditTexts with student data
                            stdName.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
                            stdAge.setText(String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_AGE))));
                            stdEmail.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EMAIL)));
                            stdCourse.setText(cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COURSE)));

                            // Disable editing of the Student ID field
                            stdID.setEnabled(false);

                            isFound = true;
                            break;
                        }
                    }

                    if (!isFound) {
                        Toast.makeText(UpdateBtn.this, "No student found with this ID", Toast.LENGTH_SHORT).show();
                    }

                    cursor.close();

                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateBtn.this, "Invalid Student ID", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Update button click listener
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String studentIdStr = stdID.getText().toString().trim();

                if (TextUtils.isEmpty(studentIdStr)) {
                    Toast.makeText(UpdateBtn.this, "Please enter a valid Student ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int studentId = Integer.parseInt(studentIdStr);
                    String name = stdName.getText().toString().trim();
                    String ageStr = stdAge.getText().toString().trim();
                    String email = stdEmail.getText().toString().trim();
                    String course = stdCourse.getText().toString().trim();

//                    if (TextUtils.isEmpty(name) || TextUtils.isEmpty(ageStr) || TextUtils.isEmpty(email) || TextUtils.isEmpty(course)) {
//                        Toast.makeText(UpdateBtn.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//                        return;
//                    }

                    int age = Integer.parseInt(ageStr);

                    // Update student record
                    boolean isUpdated = dbHelper.updateStudent(studentId, name, age, email, course);

                    if (isUpdated) {
                        Toast.makeText(UpdateBtn.this, "Student record updated successfully!", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toast.makeText(UpdateBtn.this, "Failed to update student record", Toast.LENGTH_SHORT).show();
                    }

                } catch (NumberFormatException e) {
                    Toast.makeText(UpdateBtn.this, "Invalid data input", Toast.LENGTH_SHORT).show();
                }
            }


            private void clearFields() {
                stdID.setText("");
                stdName.setText("");
                stdCourse.setText("");
                stdAge.setText("");
                stdEmail.setText("");
            }


        });



    }
}