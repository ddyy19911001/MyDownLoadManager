package com.dy.download;

import com.dy.downlibrary.DownLoadTaskInfo;
import com.dy.downlibrary.MyDownLoadManager;
import com.dy.downlibrary.TaskManager;

import yin.deng.superbase.activity.LogUtils;
import yin.deng.superbase.activity.SuperBaseActivity;

public class SecondActivity extends SuperBaseActivity {
    private TaskManager.OnDownLoadListener listener;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFirst() {
        listener=new TaskManager.OnDownLoadListener() {
            @Override
            public void onStart(DownLoadTaskInfo taskInfo) {
                LogUtils.w("开始下载任务");
            }

            @Override
            public void onErro(DownLoadTaskInfo taskInfo, String msg) {
                LogUtils.e("下载出错");
            }

            @Override
            public void onProgress(DownLoadTaskInfo taskInfo) {
                LogUtils.i("下载进行中:"+taskInfo.getCurrentFormatSize()+"/"+taskInfo.getTotalFormatSize()
                        +"，时速："+taskInfo.getFormatSpeed()+"/s");
            }

            @Override
            public void onPause(DownLoadTaskInfo taskInfo) {
                LogUtils.d("暂停下载");
            }

            @Override
            public void onFinish(DownLoadTaskInfo taskInfo) {
                LogUtils.d("下载完成");
            }
        };
        MyDownLoadManager.getInstance().addDownLoadListener(listener);
    }

    @Override
    public void onDestroy() {
        if(listener!=null) {
            MyDownLoadManager.getInstance().removeListener();
        }
        super.onDestroy();
    }
}
