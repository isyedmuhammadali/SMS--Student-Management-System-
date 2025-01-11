package com.example.maddb;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.HashMap;

public class ViewAllRecordsActivity extends AppCompatActivity {

    private ListView listViewStudents;
    private DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_all_records);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });




        listViewStudents = findViewById(R.id.listViewStudents);
        dbHelper = new DBHelper(this);

        // Fetch all students from the database
        ArrayList<HashMap<String, String>> studentList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllStudents();

        // Iterate through the result and add students' data to the list
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> student = new HashMap<>();
                student.put("id", String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_ID))));
                student.put("name", cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_NAME)));
                student.put("age", String.valueOf(cursor.getInt(cursor.getColumnIndex(DBHelper.COLUMN_AGE))));
                student.put("email", cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_EMAIL)));
                student.put("course", cursor.getString(cursor.getColumnIndex(DBHelper.COLUMN_COURSE)));

                // Add the student record to the list
                studentList.add(student);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // Set up the adapter to bind the student data to the ListView
        SimpleAdapter adapter = new SimpleAdapter(
                this,
                studentList,
                R.layout.list_item_student, // Use the custom layout for list items
                new String[]{"id","name", "age", "email", "course"},
                new int[]{R.id.studentId,R.id.studentName, R.id.studentAge, R.id.studentEmail, R.id.studentCourse}
        );

        // Set the adapter to the ListView
        listViewStudents.setAdapter(adapter);

        // Check if no data found
        if (studentList.isEmpty()) {
            Toast.makeText(ViewAllRecordsActivity.this, "No students found", Toast.LENGTH_SHORT).show();
        }
    }
}