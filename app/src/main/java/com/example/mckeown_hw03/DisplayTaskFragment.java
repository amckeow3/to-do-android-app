package com.example.mckeown_hw03;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mckeown_hw03.databinding.FragmentDisplayTaskBinding;
import com.example.mckeown_hw03.databinding.FragmentToDoListBinding;

public class DisplayTaskFragment extends Fragment {
    private static final String ARG_TASK = "param1";

    private String name_string;
    private String date_string;
    private String priority_string;
    private Task taskObject;

    DisplayTaskFragment.DisplayTaskFragmentListener mListener;
    FragmentDisplayTaskBinding binding;

    public DisplayTaskFragment() {
        // Required empty public constructor
    }

    public static DisplayTaskFragment newInstance(Task task) {
        DisplayTaskFragment fragment = new DisplayTaskFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            taskObject = getArguments().getParcelable(ARG_TASK);
            name_string = taskObject.getName();
            date_string = taskObject.getDate();
            priority_string = taskObject.getPriority();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDisplayTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.viewTaskName.setText(name_string);
        binding.viewTaskDate.setText(date_string);
        binding.viewTaskPriority.setText(priority_string);

        // Clicking the “CANCEL” button communicates with the Main Activity which
        // pops the back stack which shows the To Do List fragment.
        binding.buttonCancelDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBack();
                mListener.sendTaskList();
            }
        });

        // Clicking the “DELETE” button performs the following:
        //      a. Deletes the task from the tasks list that is hosted in the Main Activity.
        //      b. Finds the To Do List fragment, and send it the updated tasks list.
        //      c. Pops the back stack, which displays the To Do List fragment and shows the
        //         updated list of tasks which does not include the deleted task.
        binding.buttonDeleteTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.deleteTask(taskObject); // Sends task to be deleted to Main Activity
                mListener.sendTaskList();
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (DisplayTaskFragment.DisplayTaskFragmentListener) context;
    }

    // Interface to handle communication with Main Activity
    public interface DisplayTaskFragmentListener {
        void goBack();
        void deleteTask(Task taskObject);
        void sendTaskList();
    }
}