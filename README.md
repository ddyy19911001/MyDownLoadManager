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
