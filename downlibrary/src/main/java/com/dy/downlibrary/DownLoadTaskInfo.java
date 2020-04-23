package com.dy.downlibrary;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.Generated;
@Entity
public class DownLoadTaskInfo {
    public static final int TYPE_READY=0;//已添加列表，请求数据准备下载
    public static final int TYPE_PROGRESS=1;//下载进行中
    public static final int TYPE_PAUSE=2;//暂停下载
    public static final int TYPE_ERRO=3;//下载出错
    public static final int TYPE_FINISHED=4;//下载完成
    @Index
    private int retryTimes;
    @Index
    private int nowStatus=TYPE_READY;//下载状态
    @Id(autoincrement = true)//设置自增长
    private Long daoId;
    @Index(unique = true)//设置唯一性
    private String taskId;//唯一id
    @Index
    private String downUrl;//下载地址
    @Index
    private String name;//文件名称
    @Index
    private String currentFormatSize;//当前已下载大小（已被格式化）
    @Index
    private String totalFormatSize;//总大小（已格式化）
    @Index
    private long currentDownLoadedSize;//当前已下载大小
    @Index
    private long totalSize;//总大小
    @Index
    private String formatSpeed;//下载速度（已格式化）
    @Index
    private float percent;//下载进度百分数
    @Index
    private boolean isDownLoading;//当前是否正在下载


    public DownLoadTaskInfo() {
    }


    @Generated(hash = 1200785718)
    public DownLoadTaskInfo(int retryTimes, int nowStatus, Long daoId,
            String taskId, String downUrl, String name, String currentFormatSize,
            String totalFormatSize, long currentDownLoadedSize, long totalSize,
            String formatSpeed, float percent, boolean isDownLoading) {
        this.retryTimes = retryTimes;
        this.nowStatus = nowStatus;
        this.daoId = daoId;
        this.taskId = taskId;
        this.downUrl = downUrl;
        this.name = name;
        this.currentFormatSize = currentFormatSize;
        this.totalFormatSize = totalFormatSize;
        this.currentDownLoadedSize = currentDownLoadedSize;
        this.totalSize = totalSize;
        this.formatSpeed = formatSpeed;
        this.percent = percent;
        this.isDownLoading = isDownLoading;
    }


    @Override
    public String toString() {
        return "DownLoadTaskInfo{" +
                "retryTimes=" + retryTimes +
                ", nowStatus=" + nowStatus +
                ", daoId=" + daoId +
                ", taskId='" + taskId + '\'' +
                ", downUrl='" + downUrl + '\'' +
                ", name='" + name + '\'' +
                ", currentFormatSize='" + currentFormatSize + '\'' +
                ", totalFormatSize='" + totalFormatSize + '\'' +
                ", currentDownLoadedSize=" + currentDownLoadedSize +
                ", totalSize=" + totalSize +
                ", formatSpeed='" + formatSpeed + '\'' +
                ", percent=" + percent +
                ", isDownLoading=" + isDownLoading +
                '}';
    }

    public int getNowStatus() {
        return nowStatus;
    }

    public void setNowStatus(int nowStatus) {
        this.nowStatus = nowStatus;
    }

    public boolean isDownLoading() {
        return isDownLoading;
    }

    public void setDownLoading(boolean downLoading) {
        isDownLoading = downLoading;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getCurrentFormatSize() {
        return currentFormatSize;
    }

    public void setCurrentFormatSize(String currentFormatSize) {
        this.currentFormatSize = currentFormatSize;
    }

    public String getTotalFormatSize() {
        return totalFormatSize;
    }

    public void setTotalFormatSize(String totalFormatSize) {
        this.totalFormatSize = totalFormatSize;
    }

    public long getCurrentDownLoadedSize() {
        return currentDownLoadedSize;
    }

    public void setCurrentDownLoadedSize(long currentDownLoadedSize) {
        this.currentDownLoadedSize = currentDownLoadedSize;
    }

    public long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(long totalSize) {
        this.totalSize = totalSize;
    }

    public String getFormatSpeed() {
        return formatSpeed;
    }

    public void setFormatSpeed(String formatSpeed) {
        this.formatSpeed = formatSpeed;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Long getDaoId() {
        return this.daoId;
    }


    public void setDaoId(Long DaoInfoId) {
        this.daoId = DaoInfoId;
    }


    public boolean getIsDownLoading() {
        return this.isDownLoading;
    }


    public void setIsDownLoading(boolean isDownLoading) {
        this.isDownLoading = isDownLoading;
    }


    public int getRetryTimes() {
        return this.retryTimes;
    }


    public void setRetryTimes(int retryTimes) {
        this.retryTimes = retryTimes;
    }
}
