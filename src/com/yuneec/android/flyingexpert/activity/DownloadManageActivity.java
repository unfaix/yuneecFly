package com.yuneec.android.flyingexpert.activity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.VideoView;

import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.http.HttpRequest;
import com.yuneec.android.flyingexpert.transfer.download.DownloadHelper;
import com.yuneec.android.flyingexpert.transfer.download.Downloader;
import com.yuneec.android.flyingexpert.transfer.download.adapter.DownloadAdapter;
import com.yuneec.android.flyingexpert.transfer.download.adapter.DownloadAdapter.OnPauseListener;
import com.yuneec.android.flyingexpert.transfer.download.adapter.DownloadAdapter.ViewHolder;
import com.yuneec.android.flyingexpert.transfer.download.db.bean.LoadInfo;
import com.yuneec.android.flyingexpert.util.LogX;
import com.yuneec.android.flyingexpert.util.NetUtils;
import com.yuneec.android.flyingexpert.util.SystemUtil;

/**
 * ****************************************************************
 * DownloadManageActivity
 * 
 * @Author yongdaimi
 * @Remark
 * @Date Mar 18, 2015 3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ******************************************************************** 
 */
public class DownloadManageActivity extends BaseActivity implements OnPauseListener, OnScrollListener{

	private TextView bt_finish;
	private ListView lv_download;
	private DownloadAdapter mAdapter;
	private List<LoadInfo> loadInfos;
	private DownloadHelper helper;
	private NumberFormat mProgressPercentFormat;
	
	
	// --------------------------------------------------------------------
	// 							Net status code
	// --------------------------------------------------------------------
	public static final int SUCCESS = 200;
	public static final int NETWORK_ERROR = 8002;
	public static final int SDCARD_ERROR = 8003;
	public static final int RESOURCE_ERROR = 8004;
	
	
	/* save downloader */
	private ConcurrentHashMap<String, Downloader> downloads = new ConcurrentHashMap<String, Downloader>();
	
	
	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_download_manage);
	}

	@Override
	protected void init() {
		bt_finish = getView(R.id.bt_finish);
		mAdapter = new DownloadAdapter(getApplicationContext(), new ArrayList<LoadInfo>());
		lv_download = getView(R.id.lv_download);
		lv_download.setAdapter(mAdapter);
	}

	@Override
	protected void setListener() {
		bt_finish.setOnClickListener(this);
		mAdapter.setOnPauseListener(this);
		// lv_download.setOnScrollListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ArrayList<String> urls = getIntent().getStringArrayListExtra("urls");
		mProgressPercentFormat = NumberFormat.getPercentInstance();
		helper = new DownloadHelper(getApplicationContext());
		if (urls != null && urls.size() > 0) {
			new InitTask().execute(urls);
		} else {
			new Thread(new GetResourceThread()).start();
		}

	}

	
	private class InitTask extends AsyncTask<List<String>, Void, Integer> {

		private ExecutorService mThreadPool; 
		int resultCode = 0;
		
		@Override
		protected void onPreExecute() {
			showProgressDialog(R.string.is_reading_download);
		}

		@Override
		protected Integer doInBackground(List<String>... arg0) {
			
			
			if (!NetUtils.isWIFIConnected(getApplicationContext())) {
				return NETWORK_ERROR;
			}

			if (!SystemUtil.hasSDCard()) {
				return SDCARD_ERROR;
			}
			
			List<String> urls = arg0[0];
			mThreadPool = Executors.newFixedThreadPool(urls.size());
			CyclicBarrier cyclicBarrier = new CyclicBarrier(urls.size(), new Runnable() {
				
				@Override
				public void run() {
					LogX.i(DownloadHelper.TAG, "All thread has complete.");
					mHandler.obtainMessage(0).sendToTarget();
					mThreadPool.shutdown();
				}
			});
			for (int i = 0; i < urls.size(); i++) {
				mThreadPool.execute(helper.new SaveTask(HttpRequest.PIC_DOWNLOAD_URL+urls.get(i),cyclicBarrier));
			}
			return resultCode;
		}

		
		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case NETWORK_ERROR:
				closeProgressDialog();
				showToast(R.string.is_error_network);
				break;
			case SDCARD_ERROR:
				closeProgressDialog();
				showToast(R.string.is_error_sdcard);
				break;
			default:
				break;
			}
		}
	}

	
	private class GetResourceThread implements Runnable {

		@Override
		public void run() {
			loadInfos = helper.getLoadInfos();
			if (loadInfos != null && loadInfos.size() > 0) {
				mHandler.obtainMessage(1).sendToTarget();
			} else {
				mHandler.obtainMessage(2).sendToTarget();
			}
		}

	}

	
	
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			closeProgressDialog();
			switch (msg.what) {
			case 0:
				new Thread(new GetResourceThread()).start();
				break;
			case 1:
				mAdapter.setList(loadInfos);
				mAdapter.notifyDataSetChanged();
				break;
			case 2:
				showToast(R.string.is_error_no_resource);
				break;
			default:
				break;
			}

		}

	};

	
	
	@Override
	public void onStart(View v, String url) {
		DownloadTask downloadTask = new DownloadTask(v);
		downloadTask.execute(url);
	}

	
	@Override
	public void onPause(View v, String url) {
		Downloader downloader = downloads.get(url);
		if (downloader != null) {
			downloader.pause();
		}
		Button bt_download_start = (Button) ((View)v.getParent()).findViewById(R.id.bt_download_start);
		Button bt_download_pause = (Button) ((View)v.getParent()).findViewById(R.id.bt_download_pause);
		bt_download_start.setVisibility(View.VISIBLE);
		bt_download_pause.setVisibility(View.GONE);
	}
	
	
	
	class DownloadTask extends AsyncTask<String, Integer, Integer> {

		View v = null;
		Downloader downloader = null;
		String urlstr = null;
		
		public DownloadTask(View v) {
			this.v = v;
		}
		
		@Override
		protected void onPreExecute() {
			Button bt_download_start = (Button) ((View)v.getParent()).findViewById(R.id.bt_download_start);
			Button bt_download_pause = (Button) ((View)v.getParent()).findViewById(R.id.bt_download_pause);
			bt_download_start.setVisibility(View.GONE);
			bt_download_pause.setVisibility(View.VISIBLE);
		}
		

		@Override
		protected Integer doInBackground(String... params) {
			urlstr = params[0];
			downloader = downloads.get(urlstr);
			if (downloader == null) {
				downloader = new Downloader(getApplicationContext(), updateProgressHandler);
				downloads.put(urlstr, downloader);
			}
			if (downloader.isdownloading()) {
				return -1;
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			switch (result) {
			case 0:
				downloader.download(helper.getDownloadInfos(urlstr));
				break;

			default:
				break;
			}
		}
		
		
	}
	
	
	
	
	private Handler updateProgressHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String url = (String)msg.obj;
				int length = msg.arg1;
				ViewHolder holder = mAdapter.getHolders().get(url);
				holder.pb_download_progress.incrementProgressBy(length);
				int max = holder.pb_download_progress.getMax();
				int progress = holder.pb_download_progress.getProgress();
				double percent = (double) progress / (double) max;
				holder.tv_download_percent.setText(mProgressPercentFormat.format(percent));
				holder.tv_download_percent.setTextColor(getResources().getColor(R.color.green));
				if (max == progress) {
					holder.tv_download_percent.setTextColor(getResources().getColor(R.color.orange));
					holder.tv_download_percent.setText(R.string.download_complete);
					// remove downloader for map
					if (downloads.size() > 0) {
						downloads.remove(url);
					}
				}
				break;
			default:
				break;
			}

		}

	};

	
	private void cancelAllTask() {
	
		if (downloads.size() > 0) {
			Collection<Downloader> values = downloads.values();
			for (Downloader downloader:values) {
				downloader.shutdown();
			}
			downloads.clear();
		}
	}
	
	
	private void startAllTask() {
		if (downloads.size() > 0) {
			Set<Entry<String,Downloader>> set = downloads.entrySet();
			for (Iterator<Entry<String, Downloader>> iterator = set.iterator(); iterator.hasNext();) {
				Map.Entry<String, Downloader> entry = (Map.Entry<String, Downloader>)iterator.next();
				entry.getValue().download(helper.getDownloadInfos(entry.getKey()));
			}
		}
	}
	
	
	@Override
	public void onClick(View v) {
		super.onClick(v);
		switch (v.getId()) {
		case R.id.bt_finish:
			finish();
			break;

		default:
			break;
		}

	}

	
	@Override
	protected void addActivity() {
		mApplication.addActivity(this);
	}

	@Override
	protected void initProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
	}

	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		cancelAllTask();
	}

	
	@Override
	public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void onScrollStateChanged(AbsListView absListView, int scrollState) {
		
		switch (scrollState) {
		case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
			cancelAllTask();
			break;
		case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:
			startAllTask();
			break;
		default:
			break;
		}
	}

}
