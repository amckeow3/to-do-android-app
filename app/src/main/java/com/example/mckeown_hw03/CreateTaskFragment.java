package com.example.mckeown_hw03;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mckeown_hw03.databinding.FragmentCreateTaskBinding;
import com.example.mckeown_hw03.databinding.FragmentToDoListBinding;

import java.util.Calendar;

public class CreateTaskFragment extends Fragment {
    CreateTaskFragment.CreateTaskFragmentListener mListener;
    FragmentCreateTaskBinding binding;
    DatePickerDialog datePicker;
    String priority = "High";
    private Task taskObject;

    public CreateTaskFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentCreateTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Clicking the “CANCEL” button communicates with the Main Activity which
        // pops the back stack which shows the To Do List fragment.
        binding.buttonCancelNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.goBack();
                mListener.sendTaskList();
            }
        });

        // Date Picker Dialog called when "Set Date" button clicked
        binding.buttonSetDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                // date picker dialog
                datePicker = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String dateString = String.format("%02d/%02d/%d", monthOfYear + 1, dayOfMonth, year);
                                binding.viewDateSelected.setText(dateString);
                            }
                        }, year, month, day);
                datePicker.show();
            }
        });

        binding.priorityRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButton_high){
                    priority = "High";
                } else if (checkedId == R.id.radioButton_med) {
                    priority = "Medium";
                } else if (checkedId == R.id.radioButton_low) {
                    priority = "Low";
                }
            }
        });
        
        binding.buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskName = binding.editTextName.getText().toString();
                String taskDate = binding.viewDateSelected.getText().toString();
                if (taskName.isEmpty()) {
                    Toast.makeText(getActivity().getApplicationContext(), "Task name is required.", Toast.LENGTH_SHORT).show();
                } else if (taskDate.isEmpty() || taskName == null) {
                    Toast.makeText(getActivity().getApplicationContext(), "Task date is required.", Toast.LENGTH_SHORT).show();
                } else {
                    taskObject = new Task(taskName, taskDate, priority);
                    mListener.sendNewTask(taskObject);
                    mListener.sendTaskList();
                }
            }
        });


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreateTaskFragment.CreateTaskFragmentListener) context;
    }

    // Interface to handle communication with Main Activity
    public interface CreateTaskFragmentListener {
        void goBack();
        void sendNewTask(Task taskObject);
        void sendTaskList();
    }
}