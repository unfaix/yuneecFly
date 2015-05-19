package com.yuneec.android.flyingexpert.settings;

import java.io.File;
import java.util.LinkedList;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.decode.BaseImageDecoder;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.yuneec.android.flyingexpert.entity.UserInfo;
import com.yuneec.android.flyingexpert.util.CrashCollectionUtil;
import com.yuneec.android.flyingexpert.util.SystemUtil;



import android.app.Activity;
import android.app.Application;
import android.content.Context;


/**
 * Init Project Variable
 * @author yongdaimi
 * @remark 
 * @date 2014-10-9 上午10:13:47
 * @company Copyright (C) Yuneec.Inc. All Rights Reserved.
 */
public class MyApplication extends Application {

	private static MyApplication instance;
	
	
	private UserInfo user = new UserInfo();
	private LinkedList<Activity> mActivityList;
	private Context context;
	private IWXAPI api;
	
	@Override
	public void onCreate() {
		super.onCreate();
		instance = this;
		mActivityList = new LinkedList<Activity>();
		context = getApplicationContext();
	  	initCrash();
	  	initWXAPI();
	    initImageLoader();
	}

	
	private void initWXAPI() {
		api = WXAPIFactory.createWXAPI(context, AppConfig.APP_ID);
		api.registerApp(AppConfig.APP_ID);
	}

	

	/**
	 * init crash
	 */
	private void initCrash() {
		CrashCollectionUtil mCustomCrashHandler = CrashCollectionUtil.getInstance();
		mCustomCrashHandler.setCustomCrashHandler(context);
	}



	/**
	 * init ImageLoader
	 */
	private void initImageLoader() {
		File cacheDir = new File(SystemUtil.getDiskCacheDir(
				getApplicationContext(), "bitmap"));
		int cacheSize = SystemUtil.getAppMaxRunningMemory() / 5; // set Image Cache Pool Size, Now I set it is 5;
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				getApplicationContext())
				.memoryCacheExtraOptions(480, 800)
				// default = device screen dimensions
				.diskCacheExtraOptions(480, 800, null)
				.taskExecutor(null)
				.taskExecutorForCachedImages(null)
				// set ThreadPool nums
				.threadPoolSize(8)
				// set Thread priority
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.denyCacheImageMultipleSizesInMemory()
				// set memory cache strategy
				.memoryCache(new LruMemoryCache(cacheSize))
				.memoryCacheSize(cacheSize)
				.memoryCacheSizePercentage(13)
				// set disk cache strategy
				.diskCache(new UnlimitedDiscCache(cacheDir))
				.diskCacheSize(50 * 1024 * 1024)
				.diskCacheFileCount(100)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.imageDownloader(new BaseImageDownloader(getApplicationContext()))
				.imageDecoder(new BaseImageDecoder(true))
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				// .writeDebugLogs() // set is logcat debug info
				.build();

		ImageLoader.getInstance().init(configuration);
	}


	public MyApplication(){};

	public synchronized static MyApplication getInstance() {
		if (instance == null) {
			instance = new MyApplication();
		}
		return instance;
	}
	
	
	public void addActivity(Activity activity) {
		mActivityList.add(activity);
	}
	 
	
	public void exit() {
		try {
			for (Activity activity : mActivityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.exit(0);
		}
	}

	
	public synchronized UserInfo getUser() {
		return user;
	}

	public synchronized void setUser(UserInfo user) {
		this.user = user;
	}
	
	
	@Override
	public void onLowMemory() {
		super.onLowMemory();
		System.gc();
	}
	


	public IWXAPI getApi() {
		return api;
	}

	public Context getContext() {
		return context;
	}
	

}