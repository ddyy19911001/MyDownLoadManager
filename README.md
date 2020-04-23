# MyDownLoadManager
Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
Step 2. Add the dependency

	dependencies {
	        implementation 'com.github.ddyy19911001:MyDownLoadManager:1.0.0'
	}

Step 3 如何使用
    
    1.初始化    
      
      MyDownLoadManager.getInstance().init(this);
    
    2.请求权限
    
     requestRunTimePermission(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},           new PermissionListener() {
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
        
       3.创建下载任务
      
         taskInfo = new DownLoadTaskInfo();
         taskInfo.setDownUrl("http://gdown.baidu.com/data/wisegame/2c7bc4dee91e4f4f/dc7a2c7bc4dee91e4f4f914da7b4a0a8.apk");
         taskInfo.setName("我是测试内容1.apk");
         taskInfo.setTaskId(UUID.randomUUID().toString());
         
        4.添加任务开始下载 
        
         MyDownLoadManager.getInstance().addTask(taskInfo);
	 
        5.设置监听
	 
	 //taskInfo 可以不要，要了只监听这一个任务，不要则监听所有下载任务，判断任务Id来区分每个任务，在需要的地方设置监听即可
	 
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
