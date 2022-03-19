package com.example.mckeown_hw03;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Task implements Parcelable {
    public String name;
    public String date;
    public String priority;
    public List<Task> tasks = new ArrayList<>();

    public Task() { }

    protected Task(Parcel in) {
        name = in.readString();
        date = in.readString();
        priority = in.readString();
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getPriority() {
        return priority;
    }

    public Task(String name, String date, String priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(priority);
    }
}