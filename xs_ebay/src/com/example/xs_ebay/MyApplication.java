package com.example.xs_ebay;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.lidroid.xutils.BitmapUtils;
import com.yfc_lib.activity.MainActivity;
import com.yfc_lib.util.Logger;
import com.yfc_lib.volley.toolbox.ImageLoader;

public class MyApplication extends Application {
	private MainActivity ma;

	@Override
	public void onCreate() {
		super.onCreate();
	}
	/*
	 * private BDLocation location; private LoginBean loginBean; private
	 * UserAgreementBean userAgreementBean; private ActivityRegistBean
	 * registBean;
	 * 
	 * private static BitmapUtils bitmapUtils; private static ImageLoader
	 * mImageLoader; private static Context context;
	 * 
	 * private LocationClient mLocationClient; private MyLocationListener
	 * myLocationListener;
	 * 
	 * @Override public void onCreate() { super.onCreate(); context =
	 * getApplicationContext();
	 * 
	 * if (bitmapUtils == null) bitmapUtils = new
	 * BitmapUtils(getApplicationContext());
	 * bitmapUtils.configDefaultLoadingImage(R.drawable.default_head_img);
	 * bitmapUtils.configDefaultLoadFailedImage(R.drawable.default_head_img);
	 * bitmapUtils.configDefaultBitmapConfig(Bitmap.Config.RGB_565);
	 * 
	 * initLocation(); }
	 * 
	 * public BDLocation getLocation() { if (location == null) { location = new
	 * BDLocation(); } return location; }
	 * 
	 * public void setLocation(BDLocation location) { this.location = location;
	 * }
	 * 
	 * public LoginBean getLoginBean() { return loginBean; }
	 * 
	 * public void setLoginBean(LoginBean loginBean) { this.loginBean =
	 * loginBean; }
	 * 
	 * public UserAgreementBean getUserAgreementBean() { return
	 * userAgreementBean; }
	 * 
	 * public void setUserAgreementBean(UserAgreementBean userAgreementBean) {
	 * this.userAgreementBean = userAgreementBean; }
	 * 
	 * public ActivityRegistBean getRegistBean() { return registBean; }
	 * 
	 * public void setRegistBean(ActivityRegistBean registBean) {
	 * this.registBean = registBean; }
	 * 
	 * public static BitmapUtils getBitmapUtils() { if (bitmapUtils == null)
	 * bitmapUtils = new BitmapUtils(context); return bitmapUtils; }
	 * 
	 * public MainActivity getMa() { return ma; }
	 * 
	 * public void setMa(MainActivity ma) { this.ma = ma; }
	 * 
	 * private void initLocation() { mLocationClient = new
	 * LocationClient(this.getApplicationContext()); myLocationListener = new
	 * MyLocationListener();
	 * mLocationClient.registerLocationListener(myLocationListener);
	 * LocationClientOption option = new LocationClientOption();
	 * option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式
	 * option.setCoorType("gcj02");//返回的定位结果是百度经纬度，默认值gcj02 int span = 60000;
	 * option.setScanSpan(span);//设置发起定位请求的间隔时间为5000ms
	 * option.setIsNeedAddress(true); mLocationClient.setLocOption(option);
	 * 
	 * mLocationClient.start(); }
	 *//**
	 * 实现实位回调监听
	 */
	/*
	 * public class MyLocationListener implements BDLocationListener {
	 * 
	 * @Override public void onReceiveLocation(BDLocation location) {
	 * setLocation(location);
	 * 
	 * StringBuffer sb = new StringBuffer(256); sb.append("time : ");
	 * sb.append(location.getTime()); sb.append("\nerror code : ");
	 * sb.append(location.getLocType()); sb.append("\nlatitude : ");
	 * sb.append(location.getLatitude()); sb.append("\nlontitude : ");
	 * sb.append(location.getLongitude()); sb.append("\nradius : ");
	 * sb.append(location.getRadius()); if (location.getLocType() ==
	 * BDLocation.TypeGpsLocation) { sb.append("\nspeed : ");
	 * sb.append(location.getSpeed()); sb.append("\nsatellite : ");
	 * sb.append(location.getSatelliteNumber()); sb.append("\ndirection : ");
	 * sb.append("\naddr : "); sb.append(location.getAddrStr());
	 * sb.append(location.getDirection()); } else if (location.getLocType() ==
	 * BDLocation.TypeNetWorkLocation) { sb.append("\naddr : ");
	 * sb.append(location.getAddrStr()); //运营商信息 sb.append("\noperationers : ");
	 * sb.append(location.getOperators()); } Logger.i(sb.toString());
	 * 
	 * // mLocationClient.stop(); } }
	 */
}
