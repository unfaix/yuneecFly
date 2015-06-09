package com.yuneec.android.flyingexpert.activity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.yuneec.android.flyingexpert.R;
import com.yuneec.android.flyingexpert.adapter.ImagePreviewListAdapter;
import com.yuneec.android.flyingexpert.adapter.ImagePreviewPicAdapter;
import com.yuneec.android.flyingexpert.base.BaseActivity;
import com.yuneec.android.flyingexpert.entity.ResourceInfo;
import com.yuneec.android.flyingexpert.library.AlertDialog;
import com.yuneec.android.flyingexpert.library.ImageExportDialog;
import com.yuneec.android.flyingexpert.library.ProgressDialog;
import com.yuneec.android.flyingexpert.logic.comn.http.HttpRequest;
import com.yuneec.android.flyingexpert.transfer.download.DownloadHelper;
import com.yuneec.android.flyingexpert.transfer.download.Downloader;
import com.yuneec.android.flyingexpert.transfer.download.SimpleImageDownloader;
import com.yuneec.android.flyingexpert.transfer.download.DownloadHelper.SaveTask;
import com.yuneec.android.flyingexpert.transfer.download.adapter.DownloadAdapter.ViewHolder;
import com.yuneec.android.flyingexpert.transfer.download.db.bean.LoadInfo;
import com.yuneec.android.flyingexpert.transfer.download.db.dao.Dao;
import com.yuneec.android.flyingexpert.util.LogX;
import com.yuneec.android.flyingexpert.util.NetUtils;
import com.yuneec.android.flyingexpert.util.SystemUtil;



/**
 * ****************************************************************
 * ImagePreviewActivity
 * @Author yongdaimi
 * @Remark 
 * @Date Mar 18, 2015  3:00:51 PM
 * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
 ********************************************************************
 */
public class ImagePreviewActivity extends BaseActivity {

	
	
	private static final String N = "n";
	private static final String M = "m";
	private static final String S = "s";
	private static final String T = "t";
	private static final String TR = "tr";
	private static final String TD = "td";
	private static final String A = "a";
	private static final String HREF = "href";
	
	private static final String NAME = "name";
	private static final String LINK = "link";
	private static final String DATE = "date";
	private static final String SIZE = "size";
	
	private static final String TYPE_MEDIA = "application/octet-stream";
	private static final String TYPE_IMG = "image/jpeg";
	
	
	protected boolean pauseOnScroll = true;
	protected boolean pauseOnFling = true;
	
	private GridView gv_image_preview; 
	private ListView lv_image_preview; 
	private ImagePreviewPicAdapter mImagePreviewPicAdapter;
	private ImagePreviewListAdapter mImagePreviewListAdapter = null;
	
	private List<ResourceInfo> mThumbUris = null;
	private List<ResourceInfo> mMediaUris = null;
	private int itemType = 0; // 0 image 1 media;
	
	private TextView bt_finish;
	private Button bt_image;
	private Button bt_media;
	private Button bt_export;
	
	/* Download Manage*/
	private TextView tv_download;
	
	
	@Override
	protected void setContainer() {
		setContentView(R.layout.activity_image_preview_list);
	}

	
	@Override
	protected void init() {
		
		/*TextView emptyView = new TextView(this);
		emptyView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.cgo3_null, 0, 0, 0);
		emptyView.setTextColor(Color.WHITE);
		emptyView.setText(R.string.is_error_no_resource);
		LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
		emptyView.setLayoutParams(layoutParams);*/
		
		// gv_image_preview = getView(R.id.gv_image_preview);
		lv_image_preview = getView(R.id.lv_image_preview);
		// lv_image_preview.setEmptyView(emptyView);
		
		bt_finish = getView(R.id.bt_finish);
		bt_image = getView(R.id.bt_image);
		bt_export = getView(R.id.bt_export);
		bt_media = getView(R.id.bt_media);
		tv_download = getView(R.id.tv_download);
		
	}

	
	
	
	@Override
	protected void setListener() {

		bt_finish.setOnClickListener(this);
		bt_image.setOnClickListener(this);
		bt_media.setOnClickListener(this);
		bt_export.setOnClickListener(this);
		tv_download.setOnClickListener(this);
		//gv_image_preview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
		lv_image_preview.setOnItemClickListener(this);
		lv_image_preview.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), pauseOnScroll, pauseOnFling));
		
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new GetImageResourceTask().execute(new String[]{HttpRequest.PIC_DOWNLOAD_URL});
	}
	
	
	
	private class GetImageResourceTask extends AsyncTask<String, Void, List<List<ResourceInfo>>> {

		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showProgressDialog(R.string.is_getting);
		}
		
		
		@Override
		protected List<List<ResourceInfo>> doInBackground(String... arg0) {
			List<List<ResourceInfo>> list = null;
			try {
				Document document = Jsoup.parse(new URL(arg0[0]), 5000);
				Elements es = document.getElementsByTag(TR);
				if (es.size() > 2) {
					// clear the dirty data
					es.remove(0); es.remove(0);
					list = new ArrayList<List<ResourceInfo>>();
					List<ResourceInfo> imageList = new ArrayList<ResourceInfo>();
					List<ResourceInfo> mediaList = new ArrayList<ResourceInfo>();
					for (Element e:es) {
						ResourceInfo resource = new ResourceInfo();
						// get node where type is N
						Elements nElements = e.getElementsByClass(N);
						for(Element ne:nElements) {
							resource.setName(ne.getElementsByTag(A).text());
							resource.setLink(ne.getElementsByTag(A).attr(HREF));
						}
						
						// get node where type is M
						Elements mElements = e.getElementsByClass(M);
						for(Element me:mElements) {
							resource.setCreateDate(me.getElementsByTag(TD).text());
						}
						
						// get node where type is S
						Elements sElements = e.getElementsByClass(S);
						for(Element se:sElements) {
							resource.setSize(se.getElementsByTag(TD).text());
						}
						
						// get node where type is T
						Elements tElements = e.getElementsByClass(T);
						for(Element te:tElements) {
							String temp = te.getElementsByTag(TD).text();
							if (temp.equals(TYPE_IMG)) {
								resource.setType(0);
							} else if (temp.equals(TYPE_MEDIA)){
								resource.setType(1);
							}
							resource.setStreamType(temp);
							/************************************************************/
						}
						
						if (resource.getType() == 0) {
							 imageList.add(resource);
						} else if (resource.getType() == 1) {
							mediaList.add(resource);
						}
						
					}
					list.add(imageList);
					list.add(mediaList);
					return list;
				}
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return list;
		}
		

		
		
		@Override
		protected void onPostExecute(List<List<ResourceInfo>> result) {
			super.onPostExecute(result);
			closeProgressDialog();
			if (result != null) {
				/*mImagePreviewPicAdapter = new ImagePreviewPicAdapter(getApplicationContext(), result);
				gv_image_preview.setAdapter(mImagePreviewPicAdapter);*/
				// return the image thumbUrls .
				mThumbUris = result.get(0);
				mMediaUris = result.get(1);
				mImagePreviewListAdapter = new ImagePreviewListAdapter(getApplicationContext(), result);
				lv_image_preview.setAdapter(mImagePreviewListAdapter);
			} else {
				showToast(R.string.is_error_no_resource);
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
		// IMAGE
		case R.id.bt_image:
			bt_image.setTextColor(Color.RED);
			bt_media.setTextColor(Color.WHITE);
			itemType = 0;
			if (mImagePreviewListAdapter != null) {
				mImagePreviewListAdapter.setType(itemType);
				mImagePreviewListAdapter.notifyDataSetChanged();
			}
			break;
		// MEDIA
		case R.id.bt_media:
			bt_media.setTextColor(Color.RED);
			bt_image.setTextColor(Color.WHITE);
			itemType = 1;
			if (mImagePreviewListAdapter != null) {
				mImagePreviewListAdapter.setType(itemType);
				mImagePreviewListAdapter.notifyDataSetChanged();
			}
			break;
		// Export
		case R.id.bt_export:
			// to export
			if (!SystemUtil.hasSDCard()) {
				showToast(R.string.is_error_sdcard);
				return;
			}
			
			if (!NetUtils.isWIFIConnected(getApplicationContext())) {
				showToast(R.string.is_error_no_network);
				return;
			}
			
			if (mThumbUris == null || mThumbUris.size() <= 0) {
				showToast(R.string.is_error_to_export);
				return;
			}
			
			exportImage();
			break;
		// Download Manage
		case R.id.tv_download:
			Intent intent = new Intent(getApplicationContext(),DownloadManageActivity.class);
			intent.putStringArrayListExtra("urls", downloadList);
			startActivity(intent);
			break;
		default:
			break;
		}
	}


	private void exportImage() {
		new ExportTask().execute("");
	}
	
	
	private Handler updateHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				int length = msg.arg1;
				mProgressBarDialog.incrementProgressBy(length);
				break;
			default:
				break;
			}
			
		}
		
	};
	
	
	private ImageExportDialog mProgressBarDialog;
	
	
	class ExportTask extends AsyncTask<String, Void, Integer> {

		@Override
		protected void onPreExecute() {
			mProgressBarDialog = new ImageExportDialog(ImagePreviewActivity.this);
			mProgressBarDialog.setCanceledOnTouchOutside(false);
			mProgressBarDialog.setCancelable(false);
			mProgressBarDialog.show();
		}
		
		
		@Override
		protected Integer doInBackground(String... arg0) {
			final DownloadHelper helper = new DownloadHelper(getApplicationContext());
			final ExecutorService threadPool = Executors.newFixedThreadPool(mThumbUris.size());
			CyclicBarrier cyclicBarrier = new CyclicBarrier(mThumbUris.size(), new Runnable() {
				
				@Override
				public void run() {
					LogX.i(DownloadHelper.TAG, "All pics infos save complete.");
					threadPool.shutdown();
					int max = 0;
					for (ResourceInfo info:mThumbUris) {
						LoadInfo loadinfo = helper.getLoadinfo(HttpRequest.PIC_DOWNLOAD_URL+info.getLink());
						max += loadinfo.getFileSize();
					}
					mProgressBarDialog.setMax(max);
					LogX.i(DownloadHelper.TAG, "File total size read complete.");
					for (ResourceInfo info:mThumbUris) {
						new Downloader(getApplicationContext(), updateHandler).download(
								helper.getDownloadInfos(HttpRequest.PIC_DOWNLOAD_URL+info.getLink())); 
					}
				}
			});
			
			for (ResourceInfo info:mThumbUris) {
				Dao.getInstance(getApplicationContext()).delete(HttpRequest.PIC_DOWNLOAD_URL+info.getLink());
				threadPool.execute(helper.new SaveTask(HttpRequest.PIC_DOWNLOAD_URL+info.getLink(),cyclicBarrier));
			}
			return null;
		}
		
	}
	
	
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		super.onItemClick(arg0, arg1, arg2, arg3);
		if (itemType == 0) {
			if (mThumbUris != null && mThumbUris.size() > 0) {
				Intent intent = new Intent(getApplicationContext(),ImageDetailActivity.class);
				Bundle bundle = new Bundle();
				bundle.putSerializable("thumbUris", (Serializable) mThumbUris);
				bundle.putInt("position", arg2);
				intent.putExtra("bundle", bundle);
				startActivity(intent);
			}
		} else if (itemType == 1) {
			if (mMediaUris != null && mMediaUris.size() > 0) {
				ResourceInfo info = mMediaUris.get(arg2);
				if (!downloadList.contains(info)) {
					downloadList.add(info.getLink());
				}
			}
			// showDownloadTips();
		}
	}
	
	
	
	// to save download url collections.
	private ArrayList<String> downloadList = new ArrayList<String>();
	
	@Override
	protected void onResume() {
		super.onResume();
		downloadList.clear();
	}
	
	
	private void showDownloadTips() {
		final AlertDialog alertDialog = new AlertDialog(ImagePreviewActivity.this);
		alertDialog.setMessage(R.string.download_tips);
		alertDialog.setCancelable(true);
		alertDialog.setPositiveButton(getString(R.string.confirm), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
		
		alertDialog.setNegativeButton(getString(R.string.cancel), new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				alertDialog.dismiss();
			}
		});
	}



	@Override
	protected void addActivity() {
		mApplication.addActivity(this);
	}

	@Override
	protected void initProgressDialog() {
		mProgressDialog = new ProgressDialog(this);
	}

}
