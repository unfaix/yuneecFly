#yuneecFly

This project is used to control the `UAV`. It has these functions, control of the `exposure`, adjust the `ISO`, `white balance` adjustment, adjust the `shutter time`, and so on。<br />

[click me to access our website.](http://www.yuneec.com) <br />

![](https://github.com/yongdaimi/yuneecFly/blob/yongdaimi/res/drawable-hdpi/logo.png)

###Developer Email
    yongdaimi@163.com


## Project structure
* assets
* jni
* libs
* src
  * com\yuneec\android\flyingexpert\activity
  * com\yuneec\android\flyingexpert\adapter
  * com\yuneec\android\flyingexpert\base
  * com\yuneec\android\flyingexpert\base64
  * com\yuneec\android\flyingexpert\core
  * com\yuneec\android\flyingexpert\db
  * src\com\yuneec\android\flyingexpert\entity
  * com\yuneec\android\flyingexpert\library
  * com\yuneec\android\flyingexpert\logic
    * http
    * rtsp
  * com\yuneec\android\flyingexpert\manager
  * com\yuneec\android\flyingexpert\service
  * com\yuneec\android\flyingexpert\settings
  * com\yuneec\android\flyingexpert\settings
  * com\yuneec\android\flyingexpert\transfer
  * com\yuneec\android\flyingexpert\util
* res
* AndroidManifest.xml
* proguard-project.txt
* project.properties

## Push Notes
1. You must explain your identity, for example: <br />
    * Camera UI
    * @Author yongdaimi
    * @Remark Chronometer format "H:MM:SS" schedual 10 seconds
    * @Date Mar 18, 2015  3:00:51 PM
    * @Company Copyright (C) Yuneec.Inc. All Rights Reserved.
2. You must follow our rules to write your code
  ```java
  public class PhotoFormatAdaper extends BaseListAdapter<String> {

	private int selectedId = 0;
	
	public PhotoFormatAdaper(Context context, List<String> list) {
		super(context, list);
	}

	
	
	
	@Override
	protected View bindView(int position, View convertView, ViewGroup parent) {
		String string = mList.get(position);
		View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_settings_option, null);
		TextView tv_resolution_option = (TextView) view.findViewById(R.id.tv_settings_option);
		ImageView iv_settings_selected = (ImageView) view.findViewById(R.id.iv_settings_selected);
		tv_resolution_option.setText(string);
		if (selectedId == position) {
			iv_settings_selected.setVisibility(View.VISIBLE);
		} else {
			iv_settings_selected.setVisibility(View.GONE);
		}
		return view;
	}

	
	public void setSelectedId(int selectedId) {
		this.selectedId = selectedId;
	}
	
}
	
	```
3. Please try to use some new features if possible, for example: JPA, Annotation, SoftReference, concurrentHashMap, etc.


## Project screenshot
![](https://github.com/yongdaimi/yuneecFly/blob/yongdaimi/screenshot001.png)<br />
![](https://github.com/yongdaimi/yuneecFly/blob/yongdaimi/screenshot002.png)<br />
![](https://github.com/yongdaimi/yuneecFly/blob/yongdaimi/screenshot003.png)<br />
![](https://github.com/yongdaimi/yuneecFly/blob/yongdaimi/screenshot004.png)<br />

## Copyright
Copyright(C) 2015-2015 Yuneec.Inc.<br />
![](https://github.com/yongdaimi/yuneecFly/blob/yongdaimi/res/drawable-hdpi/company.png)
