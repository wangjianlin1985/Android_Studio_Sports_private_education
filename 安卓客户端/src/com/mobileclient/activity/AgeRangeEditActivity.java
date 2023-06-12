package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.AgeRange;
import com.mobileclient.service.AgeRangeService;
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

public class AgeRangeEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �������䷶ΧidTextView
	private TextView TV_arId;
	// ������ʼ���������
	private EditText ET_startAge;
	// �����������������
	private EditText ET_endAge;
	// ������ʾ��Ϣ�����
	private EditText ET_showText;
	protected String carmera_path;
	/*Ҫ��������䷶Χ��Ϣ*/
	AgeRange ageRange = new AgeRange();
	/*���䷶Χ����ҵ���߼���*/
	private AgeRangeService ageRangeService = new AgeRangeService();

	private int arId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.agerange_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭���䷶Χ��Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_arId = (TextView) findViewById(R.id.TV_arId);
		ET_startAge = (EditText) findViewById(R.id.ET_startAge);
		ET_endAge = (EditText) findViewById(R.id.ET_endAge);
		ET_showText = (EditText) findViewById(R.id.ET_showText);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		arId = extras.getInt("arId");
		/*�����޸����䷶Χ��ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��ʼ����*/ 
					if(ET_startAge.getText().toString().equals("")) {
						Toast.makeText(AgeRangeEditActivity.this, "��ʼ�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_startAge.setFocusable(true);
						ET_startAge.requestFocus();
						return;	
					}
					ageRange.setStartAge(Integer.parseInt(ET_startAge.getText().toString()));
					/*��֤��ȡ��������*/ 
					if(ET_endAge.getText().toString().equals("")) {
						Toast.makeText(AgeRangeEditActivity.this, "�����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_endAge.setFocusable(true);
						ET_endAge.requestFocus();
						return;	
					}
					ageRange.setEndAge(Integer.parseInt(ET_endAge.getText().toString()));
					/*��֤��ȡ��ʾ��Ϣ*/ 
					if(ET_showText.getText().toString().equals("")) {
						Toast.makeText(AgeRangeEditActivity.this, "��ʾ��Ϣ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_showText.setFocusable(true);
						ET_showText.requestFocus();
						return;	
					}
					ageRange.setShowText(ET_showText.getText().toString());
					/*����ҵ���߼����ϴ����䷶Χ��Ϣ*/
					AgeRangeEditActivity.this.setTitle("���ڸ������䷶Χ��Ϣ���Ե�...");
					String result = ageRangeService.UpdateAgeRange(ageRange);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    ageRange = ageRangeService.GetAgeRange(arId);
		this.TV_arId.setText(arId+"");
		this.ET_startAge.setText(ageRange.getStartAge() + "");
		this.ET_endAge.setText(ageRange.getEndAge() + "");
		this.ET_showText.setText(ageRange.getShowText());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
