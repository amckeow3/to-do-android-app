

//  Homework 03
//  McKeown_HW03
//  Adrianna McKeown

package com.example.mckeown_hw03;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Parcelable;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ToDoListFragment.ToDoListFragmentListener, CreateTaskFragment.CreateTaskFragmentListener,
        DisplayTaskFragment.DisplayTaskFragmentListener {

    final ArrayList<Task> tasks = new ArrayList<>();    //Array list for Tasks
    Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ToDoList fragment called when onCreate() is called in Main Activity
        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, ToDoListFragment.newInstance(tasks), "fragment")
                .commit();
    }

    @Override
    public void createTaskGo() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new CreateTaskFragment(), "fragment")
                .addToBackStack("ToDoList") // To Do List Fragment pushed to back stack
                .commit();
    }

    // Replaces the current fragment with the Display Task Fragment to display the details of the
    // selected task. Sends the selected Task to the Display Task Fragment & pushes the current fragment on
    // to the back stack.
    @Override
    public void sendSelectedTask(int selectedTask) {
        int which = selectedTask;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, DisplayTaskFragment.newInstance(tasks.get(which)), "fragment")
                .addToBackStack("ToDoList")
                .commit();
    }

    @Override
    public void goBack() {
        getSupportFragmentManager().popBackStack("ToDoList", FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    // Deletes from the tasks array the task sent from the Display Task Fragment
    @Override
    public void deleteTask(Task taskObject) {
        task = taskObject;
        tasks.remove(task);
    }

    // Adds to the tasks array the task sent from the Create Task Fragment
    @Override
    public void sendNewTask(Task taskObject) {
        task = taskObject;
        tasks.add(task);
    }

    //sends the To Do List Fragment the latest list of tasks
    @Override
    public void sendTaskList() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, ToDoListFragment.newInstance(tasks), "fragment")
                .commit();
    }
}