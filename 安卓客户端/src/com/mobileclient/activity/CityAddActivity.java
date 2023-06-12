package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CityAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �������б�������
	private EditText ET_cityNo;
	// �����������������
	private EditText ET_cityName;
	protected String carmera_path;
	/*Ҫ����ĳ�����Ϣ*/
	City city = new City();
	/*���й���ҵ���߼���*/
	private CityService cityService = new CityService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.city_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��ӳ���");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_cityNo = (EditText) findViewById(R.id.ET_cityNo);
		ET_cityName = (EditText) findViewById(R.id.ET_cityName);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������ӳ��а�ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ���б��*/
					if(ET_cityNo.getText().toString().equals("")) {
						Toast.makeText(CityAddActivity.this, "���б�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_cityNo.setFocusable(true);
						ET_cityNo.requestFocus();
						return;
					}
					city.setCityNo(ET_cityNo.getText().toString());
					/*��֤��ȡ��������*/ 
					if(ET_cityName.getText().toString().equals("")) {
						Toast.makeText(CityAddActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_cityName.setFocusable(true);
						ET_cityName.requestFocus();
						return;	
					}
					city.setCityName(ET_cityName.getText().toString());
					/*����ҵ���߼����ϴ�������Ϣ*/
					CityAddActivity.this.setTitle("�����ϴ�������Ϣ���Ե�...");
					String result = cityService.AddCity(city);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
