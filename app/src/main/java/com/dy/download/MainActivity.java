package com.dy.download;

import android.Manifest;
import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.dy.downlibrary.Db.DbController;
import com.dy.downlibrary.DownLoadTaskInfo;
import com.dy.downlibrary.MyDownLoadManager;
import com.dy.downlibrary.TaskManager;
import com.dy.fastframework.activity.BaseActivity;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import yin.deng.normalutils.utils.NoDoubleClickListener;
import yin.deng.superbase.activity.LogUtils;
import yin.deng.superbase.activity.permission.PermissionListener;

public class MainActivity extends BaseActivity {


    private DownLoadTaskInfo taskInfo;
    private DownLoadTaskInfo taskInfo2;
    private DownLoadTaskInfo taskInfo3;

    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initFirst() {
        MyDownLoadManager.getInstance().init(this);
        requestRunTimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, new PermissionListener() {
            @Override
            public void onGranted() {

            }

            @Override
            public void onGranted(List<String> grantedPermission) {

            }

            @Override
            public void onDenied(List<String> deniedPermission) {

            }
        });
    }


    String testUrl="https://www.xiaojinger.com/addDownLoad?id=d2507d03fd42b9ec761ab74f8f56b308a588f588";
    public void addTask(View view) {
        if(taskInfo==null) {
            taskInfo = new DownLoadTaskInfo();
            taskInfo.setDownUrl("http://gdown.baidu.com/data/wisegame/2c7bc4dee91e4f4f/dc7a2c7bc4dee91e4f4f914da7b4a0a8.apk");
            taskInfo.setName("我是测试内容1.apk");
            taskInfo.setTaskId(UUID.randomUUID().toString());
        }
        final Button bt1 = findViewById(R.id.bt_1);
        MyDownLoadManager.getInstance().addTask(taskInfo);
        MyDownLoadManager.getInstance().addDownLoadListener(taskInfo,new TaskManager.OnDownLoadListener() {
            @Override
            public void onStart(DownLoadTaskInfo taskInfo) {
                LogUtils.w("开始下载任务");
                bt1.setText("正在获取数据");
            }

            @Override
            public void onErro(DownLoadTaskInfo taskInfo, String msg) {
                LogUtils.e("下载出错");
                bt1.setText("下载出错，点击重试");
            }

            @Override
            public void onProgress(DownLoadTaskInfo taskInfo) {
                LogUtils.i("下载进行中:"+taskInfo.getCurrentFormatSize()+"/"+taskInfo.getTotalFormatSize()
                +"，时速："+taskInfo.getFormatSpeed()+"/s"+"\n下载进度："+taskInfo.getPercent()+"%");
                bt1.setText("已下载："+taskInfo.getCurrentFormatSize()+"总共："+taskInfo.getTotalSize());
            }

            @Override
            public void onPause(DownLoadTaskInfo taskInfo) {
                LogUtils.d("暂停下载");
                bt1.setText("已暂停");
            }

            @Override
            public void onFinish(DownLoadTaskInfo taskInfo) {
                LogUtils.d("下载完成");
                bt1.setText("下载完成");
            }
        });
        bt1.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MyDownLoadManager.getInstance().stopTask(taskInfo);
            }
        });
    }

    public void addTask2(View view) {
        if(taskInfo2==null) {
            taskInfo2 = new DownLoadTaskInfo();
            taskInfo2.setDownUrl("http://gdown.baidu.com/data/wisegame/2c7bc4dee91e4f4f/dc7a2c7bc4dee91e4f4f914da7b4a0a8.apk");
            taskInfo2.setName("我是测试内容2.apk");
            taskInfo2.setTaskId(UUID.randomUUID().toString());
        }
        final Button bt1 = findViewById(R.id.bt_2);
        MyDownLoadManager.getInstance().addTask(taskInfo2);
        MyDownLoadManager.getInstance().addDownLoadListener(taskInfo2,new TaskManager.OnDownLoadListener() {
            @Override
            public void onStart(DownLoadTaskInfo taskInfo) {
                LogUtils.w("开始下载任务");
                bt1.setText("正在获取数据");
            }

            @Override
            public void onErro(DownLoadTaskInfo taskInfo, String msg) {
                LogUtils.e("下载出错");
                bt1.setText("下载出错，点击重试");
            }

            @Override
            public void onProgress(DownLoadTaskInfo taskInfo) {
                LogUtils.i("下载进行中:"+taskInfo.getCurrentFormatSize()+"/"+taskInfo.getTotalFormatSize()
                        +"，时速："+taskInfo.getFormatSpeed()+"/s");
                bt1.setText("已下载："+taskInfo.getCurrentFormatSize()+"总共："+taskInfo.getTotalSize());
            }

            @Override
            public void onPause(DownLoadTaskInfo taskInfo) {
                LogUtils.d("暂停下载");
                bt1.setText("已暂停");
            }

            @Override
            public void onFinish(DownLoadTaskInfo taskInfo) {
                LogUtils.d("下载完成");
                bt1.setText("下载完成");
            }
        });
        bt1.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MyDownLoadManager.getInstance().stopTask(taskInfo2);
            }
        });
    }

    public void addTask3(View view) {
        if(taskInfo3==null) {
            taskInfo3 = new DownLoadTaskInfo();
            taskInfo3.setDownUrl("http://gdown.baidu.com/data/wisegame/2c7bc4dee91e4f4f/dc7a2c7bc4dee91e4f4f914da7b4a0a8.apk");
            taskInfo3.setName("我是测试内容3.apk");
            taskInfo3.setTaskId(UUID.randomUUID().toString());
        }
        final Button bt1 = findViewById(R.id.bt_3);
        MyDownLoadManager.getInstance().addTask(taskInfo3);
        MyDownLoadManager.getInstance().addDownLoadListener(taskInfo3,new TaskManager.OnDownLoadListener() {
            @Override
            public void onStart(DownLoadTaskInfo taskInfo) {

                LogUtils.w("开始下载任务");
                bt1.setText("正在获取数据");
            }

            @Override
            public void onErro(DownLoadTaskInfo taskInfo, String msg) {
                LogUtils.e("下载出错");
                bt1.setText("下载出错，点击重试");
            }

            @Override
            public void onProgress(DownLoadTaskInfo taskInfo) {
                LogUtils.i("下载进行中:"+taskInfo.getCurrentFormatSize()+"/"+taskInfo.getTotalFormatSize()
                        +"，时速："+taskInfo.getFormatSpeed());
                bt1.setText("已下载："+taskInfo.getCurrentFormatSize()+"总共："+taskInfo.getTotalSize());
            }

            @Override
            public void onPause(DownLoadTaskInfo taskInfo) {
                LogUtils.d("暂停下载");
                bt1.setText("已暂停");
            }

            @Override
            public void onFinish(DownLoadTaskInfo taskInfo) {
                LogUtils.d("下载完成");
                bt1.setText("下载完成");
            }
        });
        bt1.setOnClickListener(new NoDoubleClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                MyDownLoadManager.getInstance().stopTask(taskInfo3);
            }
        });
    }

    public void stopTask(View view) {
        if(taskInfo!=null) {
            MyDownLoadManager.getInstance().stopTask(taskInfo);
        }
    }

    public void toNextAc(View view) {
        startActivity(new Intent(this,SecondActivity.class));
    }

    public void findAll(View view) {
        List<DownLoadTaskInfo> taskInfos=DbController.getInstance(this).searchAll();
        if(taskInfos!=null&&taskInfos.size()>0) {
            Collections.reverse(taskInfos);
            LogUtils.e("数据总共有：" + taskInfos.size() + "条");
            for(int i=0;i<taskInfos.size();i++){
                String id=taskInfos.get(i).getTaskId();
                LogUtils.e("数据id:"+id);
            }
            LogUtils.i(taskInfos.get(0).toString());
        }else{
            LogUtils.e("数据总共有：" + 0 + "条");
        }
    }


}
