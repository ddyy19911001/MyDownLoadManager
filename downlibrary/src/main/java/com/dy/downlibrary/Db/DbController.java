package com.dy.downlibrary.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.dy.downlibrary.DaoMaster;
import com.dy.downlibrary.DaoSession;
import com.dy.downlibrary.DownLoadTaskInfo;
import com.dy.downlibrary.DownLoadTaskInfoDao;

import java.util.List;

public class DbController {
    private final String dbName="download.db";
    /**
     * Helper
     */
    private DaoMaster.DevOpenHelper mHelper;//获取Helper对象
    /**
     * 数据库
     */
    private SQLiteDatabase db;
    /**
     * DaoMaster
     */
    private DaoMaster mDaoMaster;
    /**
     * DaoSession
     */
    private DaoSession mDaoSession;
    /**
     * 上下文
     */
    private Context context;
    /**
     * dao
     */
    private DownLoadTaskInfoDao downLoadTaskInfoDao;

    private static DbController mDbController;

    /**
     * 获取单例
     */
    public static DbController getInstance(Context context){
        if(mDbController == null){
            synchronized (DbController.class){
                if(mDbController == null){
                    mDbController = new DbController(context);
                }
            }
        }
        return mDbController;
    }
    /**
     * 初始化
     * @param context
     */
    public DbController(Context context) {
        this.context = context;
        //1.创建数据库
        mHelper = new DaoMaster.DevOpenHelper(context,dbName);
        mDaoMaster =new DaoMaster(getWritableDatabase());
        mDaoSession = mDaoMaster.newSession();
        downLoadTaskInfoDao = mDaoSession.getDownLoadTaskInfoDao();
    }
    /**
     * 获取可读数据库
     */
    private SQLiteDatabase getReadableDatabase(){
        if(mHelper == null){
            mHelper = new DaoMaster.DevOpenHelper(context,dbName,null);
        }
        SQLiteDatabase db =mHelper.getReadableDatabase();
        return db;
    }

    /**
     * 获取可写数据库
     * @return
     */
    private SQLiteDatabase getWritableDatabase(){
        if(mHelper == null){
            mHelper =new DaoMaster.DevOpenHelper(context,dbName,null);
        }
        SQLiteDatabase db = mHelper.getWritableDatabase();
        return db;
    }

    /**
     * 会自动判定是插入还是替换
     * @param personInfor
     */
    public void insertOrReplace(DownLoadTaskInfo personInfor){
        downLoadTaskInfoDao.insertOrReplace(personInfor);
    }
    /**插入一条记录，表里面要没有与之相同的记录
     *
     * @param personInfor
     */
    public long insert(DownLoadTaskInfo personInfor){
        return  downLoadTaskInfoDao.insert(personInfor);
    }

    /**
     * 更新数据
     * @param personInfor
     */
    public void update(DownLoadTaskInfo personInfor){
        DownLoadTaskInfo mOldPersonInfor = downLoadTaskInfoDao.queryBuilder().where(DownLoadTaskInfoDao.Properties.DaoId.eq(personInfor.getDaoId())).build().unique();//拿到之前的记录
        if(mOldPersonInfor !=null){
            mOldPersonInfor.setName("张三");
            downLoadTaskInfoDao.update(mOldPersonInfor);
        }
    }
    /**
     * 按条件查询数据
     */
    public List<DownLoadTaskInfo> searchByWhere(String wherecluse){
        List<DownLoadTaskInfo>personInfors = (List<DownLoadTaskInfo>) downLoadTaskInfoDao.queryBuilder().where(DownLoadTaskInfoDao.Properties.DaoId.eq(wherecluse)).build().unique();
        return personInfors;
    }

    /**
     * 按条件查询数据
     */
    public List<DownLoadTaskInfo> searchByWhereStatus(String wherecluse){
        List<DownLoadTaskInfo>personInfors = (List<DownLoadTaskInfo>) downLoadTaskInfoDao.queryBuilder().where(DownLoadTaskInfoDao.Properties.NowStatus.eq(wherecluse)).build().unique();
        return personInfors;
    }

    /**
     * 按条件查询数据
     */
    public List<DownLoadTaskInfo> searchByWhereStatusNotEq(String wherecluse){
        List<DownLoadTaskInfo>personInfors = (List<DownLoadTaskInfo>) downLoadTaskInfoDao.queryBuilder().where(DownLoadTaskInfoDao.Properties.NowStatus.notEq(wherecluse)).build().unique();
        return personInfors;
    }

    /**
     * 查询所有数据
     */
    public List<DownLoadTaskInfo> searchAll(){
        List<DownLoadTaskInfo>personInfors= downLoadTaskInfoDao.queryBuilder().list();
        return personInfors;
    }
    /**
     * 删除数据
     */
    public void delete(String wherecluse){
        downLoadTaskInfoDao.queryBuilder().where(DownLoadTaskInfoDao.Properties.DaoId.eq(wherecluse)).buildDelete().executeDeleteWithoutDetachingEntities();
    }
}
