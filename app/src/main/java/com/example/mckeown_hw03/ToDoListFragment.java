package com.example.mckeown_hw03;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.mckeown_hw03.databinding.FragmentToDoListBinding;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class ToDoListFragment extends Fragment {
    private static final String TAG = "ToDoList Fragment: ";
    private ArrayList<Task> task_list;
    ToDoListFragmentListener mListener;
    FragmentToDoListBinding binding;
    Date taskDate;

    public ArrayList<Task> getTask_list() {
        return task_list;
    }

    private ArrayList<Date> dateList = new ArrayList<>();
    private ArrayList<String> nameList = new ArrayList<>();

    public ToDoListFragment() {
        // Required empty public constructor
    }

    public static ToDoListFragment newInstance(ArrayList<Task> tasks) {
        ToDoListFragment fragment = new ToDoListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("task_list", tasks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            task_list = getArguments().getParcelableArrayList("task_list");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentToDoListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Displays the number of tasks currently in the list
        binding.viewNumTasks.setText("You have " + task_list.size() + " tasks");

        for (Task task : task_list) {
            nameList.add(task.name);
        }

        // If the list is empty the upcoming task is shown as “None”.
        if (task_list.size() == 0) {
            binding.upcomingTaskName.setText("None");
        }

        if (task_list.size() > 0) {
            //Sort task dates in descending order to find the most recent date
            for (Task task : task_list) {
                try {
                    taskDate = new SimpleDateFormat("MM/dd/yyyy").parse(task.date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                dateList.add(taskDate);
            }

            Log.d(TAG, "Formatted Date List: " + dateList);
            Collections.sort(dateList);
            Log.d(TAG, "Dates after sorting: " + dateList);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            String mostRecentDate = dateFormat.format(dateList.get(0));
            Log.d(TAG, "Most recent date: " + mostRecentDate);

            for (Task task : task_list) {
                if (task.date.matches(mostRecentDate)) {
                    Log.d(TAG, "Matching date: " + task.date);
                    binding.upcomingTaskName.setText(task.name);
                    binding.upcomingTaskDate.setText(task.date);
                    binding.upcomingTaskPriority.setText(task.priority);
                }
            }
        }

        // Display an AlertDialog when "View Tasks" button is clicked.
        // All tasks shown in Alert Dialog.
        binding.buttonViewTasks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                ArrayAdapter<String> taskAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, nameList);
                builder.setTitle("Select Task")
                        .setAdapter(taskAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                                // Upon selecting a task from the list, the fragment sends the selected task to
                                // the Main Activity
                                mListener.sendSelectedTask(which);
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // User cancelled the dialog
                                mListener.sendTaskList();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        binding.buttonCreateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.createTaskGo();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (ToDoListFragment.ToDoListFragmentListener) context;

    }

    // Interface to handle communication with Main Activity
    public interface ToDoListFragmentListener {
        void createTaskGo();
        void sendSelectedTask(int selectedTask);
        void sendTaskList();
    }
}