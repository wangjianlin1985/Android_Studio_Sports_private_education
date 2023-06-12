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
	// �������ذ�ť
	private Button btnReturn;
	// �������б�ſؼ�
	private TextView TV_cityNo;
	// �����������ƿؼ�
	private TextView TV_cityName;
	/* Ҫ����ĳ�����Ϣ */
	City city = new City(); 
	/* ���й���ҵ���߼��� */
	private CityService cityService = new CityService();
	private String cityNo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.city_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    city = cityService.GetCity(cityNo); 
		this.TV_cityNo.setText(city.getCityNo());
		this.TV_cityName.setText(city.getCityName());
	} 
}
