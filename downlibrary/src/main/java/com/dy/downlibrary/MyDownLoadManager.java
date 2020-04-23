package com.dy.downlibrary;

import android.content.Context;

import com.dy.downlibrary.Db.DbController;

import java.util.List;

public class MyDownLoadManager {
    private static MyDownLoadManager manager;
    private Context context;
    public static final String listenAllId="-0x999";
    private MyDownLoadManager(){

    }

    public static MyDownLoadManager getInstance(){
        if(manager==null){
            manager=new MyDownLoadManager();
        }
        return manager;
    }

    public void init(Context context){
        this.context=context;
        TaskManager.getInstance().initLastData(context);
    }


    /**
     * 带分隔符号的文件夹下载路径
     * @param dirWithFileSeparator
     */
    public void setDownLoadDir(String dirWithFileSeparator){
        TaskManager.getInstance().setDownLoadDir(dirWithFileSeparator);
    }

    public void addTask(DownLoadTaskInfo taskInfo){
        TaskManager.getInstance().addTask(taskInfo);
    }

    public void stopTask(DownLoadTaskInfo taskInfo){
        TaskManager.getInstance().stopTask(taskInfo);
    }


    /**
     * 查询本地所有的下载任务数据（包含已下载完成的）
     * @return
     */
    public List<DownLoadTaskInfo> findAllTaskInfoInLocal(){
        List<DownLoadTaskInfo> taskInfos= DbController.getInstance(context).searchAll();
        return taskInfos;
    }

    /**
     * 查询本地所有的已下载的数据
     * @return
     */
    public List<DownLoadTaskInfo> findAllTaskDownLoaded(){
        List<DownLoadTaskInfo> taskInfos= DbController.getInstance(context).searchByWhereStatus(String.valueOf(DownLoadTaskInfo.TYPE_FINISHED));
        return taskInfos;
    }

    /**
     * 查询本地所有的除已下载的数据
     * @return
     */
    public List<DownLoadTaskInfo> findAllTaskDownLoadingAndErroPause(){
        List<DownLoadTaskInfo> taskInfos= DbController.getInstance(context).searchByWhereStatusNotEq(String.valueOf(DownLoadTaskInfo.TYPE_FINISHED));
        return taskInfos;
    }


    /**
     * 一个任务添加一个监听
     * 也可以直接添加监听不传任务对象，监听所有的下载进度
     * @param taskInfo
     * @param onDownLoadListener
     */
    public void addDownLoadListener(DownLoadTaskInfo taskInfo, TaskManager.OnDownLoadListener onDownLoadListener){
        if(taskInfo==null){
            taskInfo=new DownLoadTaskInfo();
            taskInfo.setTaskId(listenAllId);
        }
        TaskManager.getInstance().addDownLoadListener(taskInfo.getTaskId(), onDownLoadListener);
    }

    public void addDownLoadListener(TaskManager.OnDownLoadListener onDownLoadListener){
        DownLoadTaskInfo taskInfo = new DownLoadTaskInfo();
        taskInfo.setTaskId(listenAllId);
        TaskManager.getInstance().addDownLoadListener(taskInfo.getTaskId(), onDownLoadListener);
    }

    public void removeListener(DownLoadTaskInfo taskInfo){
        TaskManager.getInstance().removeDownLoadListener(taskInfo.getTaskId());
    }

    public void removeListener(){
        DownLoadTaskInfo taskInfo = new DownLoadTaskInfo();
        taskInfo.setTaskId(listenAllId);
        TaskManager.getInstance().removeDownLoadListener(taskInfo.getTaskId());
    }

    public void removeAllDownLoadListener(){
        TaskManager.getInstance().clearAllListner();
    }

}
