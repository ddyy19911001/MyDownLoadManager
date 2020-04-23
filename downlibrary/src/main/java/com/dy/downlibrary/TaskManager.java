package com.dy.downlibrary;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import com.dy.downlibrary.Db.DbController;
import com.dy.fastframework.downloader.internal.DownloadTask;
import com.vise.xsnow.http.ssl.MySSLSocketFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import yin.deng.normalutils.utils.LogUtils;
import yin.deng.normalutils.utils.MyFileUtils;

public class TaskManager {
    private static TaskManager manager;
    private Context context;
    private ExecutorService executorService;
    private int connTimeout=20*1000;
    private int readTimeout=20*1000;
    private String dir;
    private OnDownLoadListener onDownLoadListener;
    private HashMap<String,OnDownLoadListener> listeners=new HashMap<>();
    private int retryMaxTimes=5;//下载错误重试最大次数
    private int speedSlowRetryTimes=10;//下载速度慢，重试最大次数
    private boolean needReDownLoad=false;//是否需要重新下载（已存在这个文件时）

    public void setNeedReDownLoad(boolean needReDownLoad) {
        this.needReDownLoad = needReDownLoad;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public void setRetryMaxTimes(int retryMaxTimes) {
        this.retryMaxTimes = retryMaxTimes;
    }

    public void setSpeedSlowRetryTimes(int speedSlowRetryTimes) {
        this.speedSlowRetryTimes = speedSlowRetryTimes;
    }

    private TaskManager(){

    }

    public void addDownLoadListener(String taskId,OnDownLoadListener onDownLoadListener) {
        listeners.put(taskId,onDownLoadListener);
    }

    public void removeDownLoadListener(String taskId){
        if(onDownLoadListener!=null&&listeners.size()>0){
            for (Iterator<Map.Entry<String, OnDownLoadListener>> it = listeners.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry<String, OnDownLoadListener> item = it.next();
                if(item.getKey().equals(taskId)){
                    it.remove();
                }
            }
        }
    }

    public void clearAllListner(){
       listeners.clear();
    }

    public static TaskManager getInstance(){
        if(manager==null){
            manager=new TaskManager();
        }
        return manager;
    }


    /**
     * 带分隔符号的文件夹下载路径
     * @param dirWithFileSeparator
     */
    public void setDownLoadDir(String dirWithFileSeparator){
        dir=dirWithFileSeparator;
    }

    public TaskManager initLastData(final Context context){
        this.context=context;
        //默认Sd卡根目录创建DyDownLoad文件夹，再根据文件名称命名进行下载
        dir= Environment.getExternalStorageDirectory().getAbsolutePath()+File.separator+"DyDownLoad"+File.separator;
        initExcutors();
        onDownLoadListener=new OnDownLoadListener() {
            @Override
            public void onStart(DownLoadTaskInfo taskInfo) {
                for(HashMap.Entry data:listeners.entrySet()){
                    OnDownLoadListener downLoadListener = (OnDownLoadListener) data.getValue();
                    String key= (String) data.getKey();
                    if(key.equals(MyDownLoadManager.listenAllId)||(downLoadListener!=null&&key.equals(taskInfo.getTaskId()))){
                        downLoadListener.onStart(taskInfo);
                    }
                }
            }

            @Override
            public void onErro(final DownLoadTaskInfo taskInfo, String msg) {
                if(taskInfo.getRetryTimes()<retryMaxTimes){
                    try {
                        taskInfo.setRetryTimes(taskInfo.getRetryTimes()+1);
                        updateTaskInfo(taskInfo);
                        LogUtils.i("错误重试！！第"+taskInfo.getRetryTimes()+"次");
                        Thread.sleep(1000);
                        startDown(taskInfo);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                taskInfo.setRetryTimes(0);
                updateTaskInfo(taskInfo);
                for(HashMap.Entry data:listeners.entrySet()){
                    OnDownLoadListener downLoadListener = (OnDownLoadListener) data.getValue();
                    String key= (String) data.getKey();
                    if(key.equals(MyDownLoadManager.listenAllId)||(downLoadListener!=null&&key.equals(taskInfo.getTaskId()))){
                        downLoadListener.onErro(taskInfo,msg);
                    }
                }
            }

            @Override
            public void onProgress(DownLoadTaskInfo taskInfo) {
                for(HashMap.Entry data:listeners.entrySet()){
                    OnDownLoadListener downLoadListener = (OnDownLoadListener) data.getValue();
                    String key= (String) data.getKey();
                    if(key.equals(MyDownLoadManager.listenAllId)||(downLoadListener!=null&&key.equals(taskInfo.getTaskId()))){
                        downLoadListener.onProgress(taskInfo);
                    }
                }
            }

            @Override
            public void onPause(DownLoadTaskInfo taskInfo) {
                for(HashMap.Entry data:listeners.entrySet()){
                    OnDownLoadListener downLoadListener = (OnDownLoadListener) data.getValue();
                    String key= (String) data.getKey();
                    if(key.equals(MyDownLoadManager.listenAllId)||(downLoadListener!=null&&key.equals(taskInfo.getTaskId()))){
                        downLoadListener.onPause(taskInfo);
                    }
                }
            }

            @Override
            public void onFinish(DownLoadTaskInfo taskInfo) {
                for(HashMap.Entry data:listeners.entrySet()){
                    OnDownLoadListener downLoadListener = (OnDownLoadListener) data.getValue();
                    String key= (String) data.getKey();
                    if(key.equals(MyDownLoadManager.listenAllId)||(downLoadListener!=null&&key.equals(taskInfo.getTaskId()))){
                        downLoadListener.onFinish(taskInfo);
                    }
                }
            }
        };
        return getInstance();
    }

    private void initExcutors() {
        executorService= Executors.newFixedThreadPool(5);
    }


    public void addTask(DownLoadTaskInfo taskInfo){
        try {
            updateTaskInfo(taskInfo);
            startDown(taskInfo);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addTask(DownLoadTaskInfo taskInfo,boolean needReDownLoad){
        try {
            updateTaskInfo(taskInfo);
            startDown(taskInfo,needReDownLoad);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 开始下载
     */
    public void startDown(final DownLoadTaskInfo taskInfo) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_READY);
                updateTaskInfo(taskInfo);
                if(onDownLoadListener!=null){
                    onDownLoadListener.onStart(taskInfo);
                }
               downLoad(taskInfo,needReDownLoad);
            }
        });

    }

    /**
     * 开始下载
     */
    public void startDown(final DownLoadTaskInfo taskInfo, final boolean needD) {
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_READY);
                updateTaskInfo(taskInfo);
                if(onDownLoadListener!=null){
                    onDownLoadListener.onStart(taskInfo);
                }
                downLoad(taskInfo,needD);
            }
        });

    }





    private void downLoad(DownLoadTaskInfo taskInfo,boolean needReDownLoad){
        int slowSpeedTimes = 0;
        taskInfo.setDownLoading(true);
        //设置下载位置
        HttpURLConnection conn = null;
        RandomAccessFile raf = null;
        InputStream input = null;
        try {
            HttpsURLConnection.setDefaultSSLSocketFactory(MySSLSocketFactory.createSSLSocketFactory());
            URL url = new URL(taskInfo.getDownUrl());
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(connTimeout);
            conn.setReadTimeout(readTimeout);
            conn.setRequestMethod("GET");
            //设置文件写入位置
            File file;
            File originalFile;
            File parent=new File(dir);
            if(!parent.exists()){
                parent.mkdirs();
            }
            try {
                String fileName =taskInfo.getName();
                originalFile=new File(dir+fileName);
                fileName=fileName+"_temp";
                file = new File(dir  + fileName);
            } catch (Exception e) {
                originalFile=new File(dir+taskInfo.getDownUrl());
                file = new File(dir  + taskInfo.getDownUrl()+"_temp");
            }
            //设置下载位置
            long start = file.length();
            conn.setRequestProperty("Range", "bytes=" + start + "-");
            LogUtils.e("文件路径为："+file.getAbsolutePath());
            raf = new RandomAccessFile(file, "rwd");
            raf.seek(start);
            //开始下载
            long mFinished = start;
            long nowLeaveTotal=conn.getContentLength();//剩余未下载数据总大小
            if(nowLeaveTotal<0){
                //erro
                taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_ERRO);
                updateTaskInfo(taskInfo);
                if(onDownLoadListener!=null){
                    onDownLoadListener.onErro(taskInfo,"远程服务器文件异常");
                }
                return;
            }else if(nowLeaveTotal==0&&mFinished>0){
                //此文件已经下载过了
                if(!needReDownLoad){
                    taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_FINISHED);
                    taskInfo.setRetryTimes(0);
                    updateTaskInfo(taskInfo);
                    if(onDownLoadListener!=null){
                        onDownLoadListener.onFinish(taskInfo);
                    }
                    closeAll(conn, raf, input);
                    return;
                }else{
                    originalFile.delete();
                    file.delete();
                    closeAll(conn, raf, input);
                    downLoad(taskInfo, needReDownLoad);
                    return;
                }
            }else{
                LogUtils.e("下载开始：当前下载位置-->"+getFormatStr(mFinished));
            }
            if (conn.getResponseCode() == HttpURLConnection.HTTP_PARTIAL) {
                //读取数据
                taskInfo.setTotalSize(mFinished+nowLeaveTotal);
                taskInfo.setTotalFormatSize(getFormatStr(taskInfo.getTotalSize()));
                input = conn.getInputStream();
                byte[] buffer = new byte[1024 * 8];
                int len = -1;
                long lastTime = System.currentTimeMillis();
                long lastLength=start;
                while ((len = input.read(buffer)) != -1) {
                    //写入文件
                    raf.write(buffer, 0, len);
                    //把下载进度发送广播给Activity
                    mFinished += len;
                    taskInfo.setCurrentDownLoadedSize(mFinished);
                    taskInfo.setPercent(mFinished*100/taskInfo.getTotalSize());
                    taskInfo.setCurrentFormatSize(getFormatStr(mFinished));
                    if(System.currentTimeMillis()-lastTime>=1000){
                        if(lastLength>0) {
                            long perSpeed = mFinished - lastLength;
                            if(perSpeed<50){
                                slowSpeedTimes++;
                                if(slowSpeedTimes>=speedSlowRetryTimes){
                                    LogUtils.e("网速过慢，尝试重新连接资源下载");
                                    closeAll(conn, raf, input);
                                    downLoad(taskInfo, needReDownLoad);
                                    return;
                                }
                            }else{
                                slowSpeedTimes=0;
                            }
                            LogUtils.e("下载时速为：" + getFormatStr(perSpeed) + "/s");
                            taskInfo.setFormatSpeed(getFormatStr(perSpeed) + "/s");
                            taskInfo.setRetryTimes(0);
                            taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_PROGRESS);
                            updateTaskInfo(taskInfo);
                            if(onDownLoadListener!=null){
                                onDownLoadListener.onProgress(taskInfo);
                            }
                        }
                        lastLength=mFinished;
                        lastTime=System.currentTimeMillis();
                    }
                    if(!taskInfo.isDownLoading()){
                        taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_PAUSE);
                        taskInfo.setRetryTimes(0);
                        updateTaskInfo(taskInfo);
                        if(onDownLoadListener!=null){
                            onDownLoadListener.onPause(taskInfo);
                        }
                        closeAll(conn, raf, input);
                        return;
                    }
                }
                taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_PROGRESS);
                updateTaskInfo(taskInfo);
                if(onDownLoadListener!=null) {
                    onDownLoadListener.onProgress(taskInfo);
                }
                raf.close();
                input.close();
                conn.disconnect();
                originalFile.delete();
                MyFileUtils.renameFile(file, taskInfo.getName());
                taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_FINISHED);
                taskInfo.setRetryTimes(0);
                updateTaskInfo(taskInfo);
                if(onDownLoadListener!=null){
                    onDownLoadListener.onFinish(taskInfo);
                }
                LogUtils.i("下载完成！！");
            }else{
                LogUtils.e("当前下载响应为："+conn.getResponseCode()+","+conn.getResponseMessage());
                taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_ERRO);
                updateTaskInfo(taskInfo);
                if(onDownLoadListener!=null){
                    onDownLoadListener.onErro(taskInfo,"下载出错："+conn.getResponseMessage());
                }
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
            taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_ERRO);
            updateTaskInfo(taskInfo);
            if(onDownLoadListener!=null){
                onDownLoadListener.onErro(taskInfo,"下载出错："+e.getMessage());
            }
        } catch (IOException e) {
            e.printStackTrace();
            taskInfo.setNowStatus(DownLoadTaskInfo.TYPE_ERRO);
            updateTaskInfo(taskInfo);
            if(onDownLoadListener!=null){
                onDownLoadListener.onErro(taskInfo,"下载出错："+e.getMessage());
            }
        } finally {
            conn.disconnect();
            try {
                if(raf!=null) {
                    raf.close();
                }
                if(input!=null) {
                    input.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 更新数据库
     * @param taskInfo
     */
    private void updateTaskInfo(DownLoadTaskInfo taskInfo) {
        if(taskInfo.getNowStatus()== DownLoadTaskInfo.TYPE_ERRO
          ||taskInfo.getNowStatus()== DownLoadTaskInfo.TYPE_FINISHED
          ||taskInfo.getNowStatus()== DownLoadTaskInfo.TYPE_PAUSE){
            taskInfo.setDownLoading(false);
        }
        DbController.getInstance(context).insertOrReplace(taskInfo);
    }


    private void closeAll(HttpURLConnection conn, RandomAccessFile radAccF, InputStream inputStream) {
        try {
            if(conn!=null){
                conn.disconnect();
            }
            if(radAccF!=null){
                radAccF.close();
            }
            if(inputStream!=null){
                inputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String getFormatStr(long data){
        if(data>100*10000){
            BigDecimal bigDecimal=new BigDecimal(data);
            BigDecimal dec = bigDecimal.divide(new BigDecimal(1024 * 1024),1, RoundingMode.HALF_DOWN);
            return dec.toString()+"m";
        }else if(data>=100){
            BigDecimal bigDecimal=new BigDecimal(data);
            BigDecimal dec = bigDecimal.divide(new BigDecimal( 1024),2, RoundingMode.HALF_DOWN);
            return dec.toString()+"kb";
        }else{
            BigDecimal bigDecimal=new BigDecimal(data);
            return bigDecimal.toString()+"b";
        }
    }

    public void stopTask(DownLoadTaskInfo taskInfo) {
        taskInfo.setDownLoading(false);
    }

    public interface OnDownLoadListener{
        void onStart(DownLoadTaskInfo taskInfo);
        void onErro(DownLoadTaskInfo taskInfo,String msg);
        void onProgress(DownLoadTaskInfo taskInfo);
        void onPause(DownLoadTaskInfo taskInfo);
        void onFinish(DownLoadTaskInfo taskInfo);
    }
}
