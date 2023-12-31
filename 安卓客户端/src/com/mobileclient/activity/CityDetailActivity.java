package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import android.widget.Toast;
public class CityDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明城市编号控件
	private TextView TV_cityNo;
	// 声明城市名称控件
	private TextView TV_cityName;
	/* 要保存的城市信息 */
	City city = new City(); 
	/* 城市管理业务逻辑层 */
	private CityService cityService = new CityService();
	private String cityNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.city_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看城市详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_cityNo = (TextView) findViewById(R.id.TV_cityNo);
		TV_cityName = (TextView) findViewById(R.id.TV_cityName);
		Bundle extras = this.getIntent().getExtras();
		cityNo = extras.getString("cityNo");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CityDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    city = cityService.GetCity(cityNo); 
		this.TV_cityNo.setText(city.getCityNo());
		this.TV_cityName.setText(city.getCityName());
	} 
}
