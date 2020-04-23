package com.dy.downlibrary;

import android.app.TaskInfo;

import java.util.ArrayList;
import java.util.List;

public class TaskInfoList {
    private List<DownLoadTaskInfo> taskInfos=new ArrayList<>();

    public List<DownLoadTaskInfo> getTaskInfos() {
        return taskInfos;
    }

    public void setTaskInfos(List<DownLoadTaskInfo> taskInfos) {
        this.taskInfos = taskInfos;
    }
}
