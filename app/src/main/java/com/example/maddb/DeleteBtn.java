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

public class DeleteBtn extends AppCompatActivity {


    private EditText stdId;
    private Button btnDelete;
    private DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_delete_btn);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        stdId = findViewById(R.id.stdId1);
        btnDelete = findViewById(R.id.btnDelete1);

        // Initialize DBHelper
        dbHelper = new DBHelper(this);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Fetch student ID from input
                String studentIdStr = stdId.getText().toString().trim();

                // Validate input
                if (TextUtils.isEmpty(studentIdStr)) {
                    Toast.makeText(DeleteBtn.this, "Please enter a valid Student ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int studentId = Integer.parseInt(studentIdStr);

                    // Attempt to delete the record
                    boolean isDeleted = dbHelper.deleteStudent(studentId);

                    if (isDeleted) {
                        Toast.makeText(DeleteBtn.this, "Student record deleted successfully!", Toast.LENGTH_SHORT).show();
                        stdId.setText(""); // Clear input field
                    } else {
                        Toast.makeText(DeleteBtn.this, "No record found with the given ID", Toast.LENGTH_SHORT).show();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(DeleteBtn.this, "Invalid Student ID", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}