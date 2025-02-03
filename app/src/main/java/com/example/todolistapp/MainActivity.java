package com.example.todolistapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAddTask;
    private RecyclerView recyclerViewTasks;
    private TaskAdapter taskAdapter;
    private List<String> taskList;
    private int editingPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAddTask = findViewById(R.id.buttonAddTask);
        recyclerViewTasks = findViewById(R.id.recyclerViewTasks);

        taskList = new ArrayList<>();
        taskAdapter = new TaskAdapter(taskList, new TaskAdapter.OnTaskListener() {
            @Override
            public void onTaskEdit(int position) {
                editTextTask.setText(taskList.get(position));
                editingPosition = position;
            }

            @Override
            public void onTaskDelete(int position) {
                taskList.remove(position);
                taskAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Task deleted", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerViewTasks.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTasks.setAdapter(taskAdapter);

        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (task.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter a task", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (editingPosition == -1) {
                    taskList.add(task);
                    taskAdapter.notifyItemInserted(taskList.size() - 1);
                    Toast.makeText(MainActivity.this, "Task added", Toast.LENGTH_SHORT).show();
                } else {
                    taskList.set(editingPosition, task);
                    taskAdapter.notifyItemChanged(editingPosition);
                    editingPosition = -1;
                    Toast.makeText(MainActivity.this, "Task updated", Toast.LENGTH_SHORT).show();
                }

                editTextTask.setText("");
            }
        });
    }
}
