package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class CityEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明城市编号TextView
	private TextView TV_cityNo;
	// 声明城市名称输入框
	private EditText ET_cityName;
	protected String carmera_path;
	/*要保存的城市信息*/
	City city = new City();
	/*城市管理业务逻辑层*/
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
		setContentView(R.layout.city_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑城市信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_cityNo = (TextView) findViewById(R.id.TV_cityNo);
		ET_cityName = (EditText) findViewById(R.id.ET_cityName);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		cityNo = extras.getString("cityNo");
		/*单击修改城市按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取城市名称*/ 
					if(ET_cityName.getText().toString().equals("")) {
						Toast.makeText(CityEditActivity.this, "城市名称输入不能为空!", Toast.LENGTH_LONG).show();
						ET_cityName.setFocusable(true);
						ET_cityName.requestFocus();
						return;	
					}
					city.setCityName(ET_cityName.getText().toString());
					/*调用业务逻辑层上传城市信息*/
					CityEditActivity.this.setTitle("正在更新城市信息，稍等...");
					String result = cityService.UpdateCity(city);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    city = cityService.GetCity(cityNo);
		this.TV_cityNo.setText(cityNo);
		this.ET_cityName.setText(city.getCityName());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
