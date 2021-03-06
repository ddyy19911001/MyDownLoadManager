package com.dy.downlibrary;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DOWN_LOAD_TASK_INFO".
*/
public class DownLoadTaskInfoDao extends AbstractDao<DownLoadTaskInfo, Long> {

    public static final String TABLENAME = "DOWN_LOAD_TASK_INFO";

    /**
     * Properties of entity DownLoadTaskInfo.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property RetryTimes = new Property(0, int.class, "retryTimes", false, "RETRY_TIMES");
        public final static Property NowStatus = new Property(1, int.class, "nowStatus", false, "NOW_STATUS");
        public final static Property DaoId = new Property(2, Long.class, "daoId", true, "_id");
        public final static Property TaskId = new Property(3, String.class, "taskId", false, "TASK_ID");
        public final static Property DownUrl = new Property(4, String.class, "downUrl", false, "DOWN_URL");
        public final static Property Name = new Property(5, String.class, "name", false, "NAME");
        public final static Property CurrentFormatSize = new Property(6, String.class, "currentFormatSize", false, "CURRENT_FORMAT_SIZE");
        public final static Property TotalFormatSize = new Property(7, String.class, "totalFormatSize", false, "TOTAL_FORMAT_SIZE");
        public final static Property CurrentDownLoadedSize = new Property(8, long.class, "currentDownLoadedSize", false, "CURRENT_DOWN_LOADED_SIZE");
        public final static Property TotalSize = new Property(9, long.class, "totalSize", false, "TOTAL_SIZE");
        public final static Property FormatSpeed = new Property(10, String.class, "formatSpeed", false, "FORMAT_SPEED");
        public final static Property Percent = new Property(11, float.class, "percent", false, "PERCENT");
        public final static Property IsDownLoading = new Property(12, boolean.class, "isDownLoading", false, "IS_DOWN_LOADING");
    }


    public DownLoadTaskInfoDao(DaoConfig config) {
        super(config);
    }
    
    public DownLoadTaskInfoDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DOWN_LOAD_TASK_INFO\" (" + //
                "\"RETRY_TIMES\" INTEGER NOT NULL ," + // 0: retryTimes
                "\"NOW_STATUS\" INTEGER NOT NULL ," + // 1: nowStatus
                "\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ," + // 2: daoId
                "\"TASK_ID\" TEXT," + // 3: taskId
                "\"DOWN_URL\" TEXT," + // 4: downUrl
                "\"NAME\" TEXT," + // 5: name
                "\"CURRENT_FORMAT_SIZE\" TEXT," + // 6: currentFormatSize
                "\"TOTAL_FORMAT_SIZE\" TEXT," + // 7: totalFormatSize
                "\"CURRENT_DOWN_LOADED_SIZE\" INTEGER NOT NULL ," + // 8: currentDownLoadedSize
                "\"TOTAL_SIZE\" INTEGER NOT NULL ," + // 9: totalSize
                "\"FORMAT_SPEED\" TEXT," + // 10: formatSpeed
                "\"PERCENT\" REAL NOT NULL ," + // 11: percent
                "\"IS_DOWN_LOADING\" INTEGER NOT NULL );"); // 12: isDownLoading
        // Add Indexes
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_RETRY_TIMES ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"RETRY_TIMES\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_NOW_STATUS ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"NOW_STATUS\" ASC);");
        db.execSQL("CREATE UNIQUE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_TASK_ID ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"TASK_ID\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_DOWN_URL ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"DOWN_URL\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_NAME ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"NAME\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_CURRENT_FORMAT_SIZE ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"CURRENT_FORMAT_SIZE\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_TOTAL_FORMAT_SIZE ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"TOTAL_FORMAT_SIZE\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_CURRENT_DOWN_LOADED_SIZE ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"CURRENT_DOWN_LOADED_SIZE\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_TOTAL_SIZE ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"TOTAL_SIZE\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_FORMAT_SPEED ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"FORMAT_SPEED\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_PERCENT ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"PERCENT\" ASC);");
        db.execSQL("CREATE INDEX " + constraint + "IDX_DOWN_LOAD_TASK_INFO_IS_DOWN_LOADING ON \"DOWN_LOAD_TASK_INFO\"" +
                " (\"IS_DOWN_LOADING\" ASC);");
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DOWN_LOAD_TASK_INFO\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DownLoadTaskInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getRetryTimes());
        stmt.bindLong(2, entity.getNowStatus());
 
        Long daoId = entity.getDaoId();
        if (daoId != null) {
            stmt.bindLong(3, daoId);
        }
 
        String taskId = entity.getTaskId();
        if (taskId != null) {
            stmt.bindString(4, taskId);
        }
 
        String downUrl = entity.getDownUrl();
        if (downUrl != null) {
            stmt.bindString(5, downUrl);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String currentFormatSize = entity.getCurrentFormatSize();
        if (currentFormatSize != null) {
            stmt.bindString(7, currentFormatSize);
        }
 
        String totalFormatSize = entity.getTotalFormatSize();
        if (totalFormatSize != null) {
            stmt.bindString(8, totalFormatSize);
        }
        stmt.bindLong(9, entity.getCurrentDownLoadedSize());
        stmt.bindLong(10, entity.getTotalSize());
 
        String formatSpeed = entity.getFormatSpeed();
        if (formatSpeed != null) {
            stmt.bindString(11, formatSpeed);
        }
        stmt.bindDouble(12, entity.getPercent());
        stmt.bindLong(13, entity.getIsDownLoading() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DownLoadTaskInfo entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getRetryTimes());
        stmt.bindLong(2, entity.getNowStatus());
 
        Long daoId = entity.getDaoId();
        if (daoId != null) {
            stmt.bindLong(3, daoId);
        }
 
        String taskId = entity.getTaskId();
        if (taskId != null) {
            stmt.bindString(4, taskId);
        }
 
        String downUrl = entity.getDownUrl();
        if (downUrl != null) {
            stmt.bindString(5, downUrl);
        }
 
        String name = entity.getName();
        if (name != null) {
            stmt.bindString(6, name);
        }
 
        String currentFormatSize = entity.getCurrentFormatSize();
        if (currentFormatSize != null) {
            stmt.bindString(7, currentFormatSize);
        }
 
        String totalFormatSize = entity.getTotalFormatSize();
        if (totalFormatSize != null) {
            stmt.bindString(8, totalFormatSize);
        }
        stmt.bindLong(9, entity.getCurrentDownLoadedSize());
        stmt.bindLong(10, entity.getTotalSize());
 
        String formatSpeed = entity.getFormatSpeed();
        if (formatSpeed != null) {
            stmt.bindString(11, formatSpeed);
        }
        stmt.bindDouble(12, entity.getPercent());
        stmt.bindLong(13, entity.getIsDownLoading() ? 1L: 0L);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2);
    }    

    @Override
    public DownLoadTaskInfo readEntity(Cursor cursor, int offset) {
        DownLoadTaskInfo entity = new DownLoadTaskInfo( //
            cursor.getInt(offset + 0), // retryTimes
            cursor.getInt(offset + 1), // nowStatus
            cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2), // daoId
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // taskId
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // downUrl
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // name
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // currentFormatSize
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // totalFormatSize
            cursor.getLong(offset + 8), // currentDownLoadedSize
            cursor.getLong(offset + 9), // totalSize
            cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10), // formatSpeed
            cursor.getFloat(offset + 11), // percent
            cursor.getShort(offset + 12) != 0 // isDownLoading
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DownLoadTaskInfo entity, int offset) {
        entity.setRetryTimes(cursor.getInt(offset + 0));
        entity.setNowStatus(cursor.getInt(offset + 1));
        entity.setDaoId(cursor.isNull(offset + 2) ? null : cursor.getLong(offset + 2));
        entity.setTaskId(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setDownUrl(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setName(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setCurrentFormatSize(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setTotalFormatSize(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setCurrentDownLoadedSize(cursor.getLong(offset + 8));
        entity.setTotalSize(cursor.getLong(offset + 9));
        entity.setFormatSpeed(cursor.isNull(offset + 10) ? null : cursor.getString(offset + 10));
        entity.setPercent(cursor.getFloat(offset + 11));
        entity.setIsDownLoading(cursor.getShort(offset + 12) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(DownLoadTaskInfo entity, long rowId) {
        entity.setDaoId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(DownLoadTaskInfo entity) {
        if(entity != null) {
            return entity.getDaoId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(DownLoadTaskInfo entity) {
        return entity.getDaoId() != null;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
