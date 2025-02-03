package com.example.todolistapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {

    private List<String> taskList;
    private OnTaskListener onTaskListener;

    public TaskAdapter(List<String> taskList, OnTaskListener onTaskListener) {
        this.taskList = taskList;
        this.onTaskListener = onTaskListener;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view, onTaskListener);
    }

    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {
        String task = taskList.get(position);
        holder.textViewTask.setText(task);
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewTask;
        Button buttonEdit, buttonDelete;
        OnTaskListener onTaskListener;

        public TaskViewHolder(View itemView, OnTaskListener onTaskListener) {
            super(itemView);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            buttonEdit = itemView.findViewById(R.id.buttonEdit);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
            this.onTaskListener = onTaskListener;

            buttonEdit.setOnClickListener(this);
            buttonDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == buttonEdit) {
                onTaskListener.onTaskEdit(getAdapterPosition());
            } else if (v == buttonDelete) {
                onTaskListener.onTaskDelete(getAdapterPosition());
            }
        }
    }

    public interface OnTaskListener {
        void onTaskEdit(int position);
        void onTaskDelete(int position);
    }
}
