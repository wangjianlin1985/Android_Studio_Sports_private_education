package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.PriceRange;
import com.mobileclient.service.PriceRangeService;
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
public class PriceRangeAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ������ʼ�������
	private EditText ET_startPrice;
	// ���������������
	private EditText ET_endPrice;
	// ������ʾ��Ϣ�����
	private EditText ET_showText;
	protected String carmera_path;
	/*Ҫ����ļ۸�Χ��Ϣ*/
	PriceRange priceRange = new PriceRange();
	/*�۸�Χ����ҵ���߼���*/
	private PriceRangeService priceRangeService = new PriceRangeService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.pricerange_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("��Ӽ۸�Χ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_startPrice = (EditText) findViewById(R.id.ET_startPrice);
		ET_endPrice = (EditText) findViewById(R.id.ET_endPrice);
		ET_showText = (EditText) findViewById(R.id.ET_showText);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*������Ӽ۸�Χ��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��ʼ��*/ 
					if(ET_startPrice.getText().toString().equals("")) {
						Toast.makeText(PriceRangeAddActivity.this, "��ʼ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_startPrice.setFocusable(true);
						ET_startPrice.requestFocus();
						return;	
					}
					priceRange.setStartPrice(Integer.parseInt(ET_startPrice.getText().toString()));
					/*��֤��ȡ������*/ 
					if(ET_endPrice.getText().toString().equals("")) {
						Toast.makeText(PriceRangeAddActivity.this, "���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_endPrice.setFocusable(true);
						ET_endPrice.requestFocus();
						return;	
					}
					priceRange.setEndPrice(Integer.parseInt(ET_endPrice.getText().toString()));
					/*��֤��ȡ��ʾ��Ϣ*/ 
					if(ET_showText.getText().toString().equals("")) {
						Toast.makeText(PriceRangeAddActivity.this, "��ʾ��Ϣ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_showText.setFocusable(true);
						ET_showText.requestFocus();
						return;	
					}
					priceRange.setShowText(ET_showText.getText().toString());
					/*����ҵ���߼����ϴ��۸�Χ��Ϣ*/
					PriceRangeAddActivity.this.setTitle("�����ϴ��۸�Χ��Ϣ���Ե�...");
					String result = priceRangeService.AddPriceRange(priceRange);
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
