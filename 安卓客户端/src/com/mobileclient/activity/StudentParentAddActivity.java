package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.StudentParent;
import com.mobileclient.service.StudentParentService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.AgeRange;
import com.mobileclient.service.AgeRangeService;
import com.mobileclient.domain.NowState;
import com.mobileclient.service.NowStateService;
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
public class StudentParentAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// �����û��������
	private EditText ET_spUserName;
	// ������¼���������
	private EditText ET_password;
	// �����ҳ����������
	private EditText ET_parentName;
	// �����ֻ��������
	private EditText ET_telephone;
	// ��������������
	private Spinner spinner_cityObj;
	private ArrayAdapter<String> cityObj_adapter;
	private static  String[] cityObj_ShowText  = null;
	private List<City> cityList = null;
	/*���й���ҵ���߼���*/
	private CityService cityService = new CityService();
	// ����ѧ���Ա������
	private EditText ET_studentSex;
	// �������䷶Χ������
	private Spinner spinner_ageRangeObj;
	private ArrayAdapter<String> ageRangeObj_adapter;
	private static  String[] ageRangeObj_ShowText  = null;
	private List<AgeRange> ageRangeList = null;
	/*���䷶Χ����ҵ���߼���*/
	private AgeRangeService ageRangeService = new AgeRangeService();
	// ����ѧ�����������
	private EditText ET_age;
	// ����ѧ��ѧУ�����
	private EditText ET_school;
	// ������״̬������
	private Spinner spinner_nowStateObj;
	private ArrayAdapter<String> nowStateObj_adapter;
	private static  String[] nowStateObj_ShowText  = null;
	private List<NowState> nowStateList = null;
	/*��״̬����ҵ���߼���*/
	private NowStateService nowStateService = new NowStateService();
	// ����ѧ����ƬͼƬ��ؼ�
	private ImageView iv_studentPhoto;
	private Button btn_studentPhoto;
	protected int REQ_CODE_SELECT_IMAGE_studentPhoto = 1;
	private int REQ_CODE_CAMERA_studentPhoto = 2;
	// ����ѧ�����������
	private EditText ET_studentDesc;
	// �������״̬�����
	private EditText ET_shzt;
	// ����ע��ʱ�������
	private EditText ET_regTime;
	protected String carmera_path;
	/*Ҫ�����ѧ���ҳ���Ϣ*/
	StudentParent studentParent = new StudentParent();
	/*ѧ���ҳ�����ҵ���߼���*/
	private StudentParentService studentParentService = new StudentParentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.studentparent_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("���ѧ���ҳ�");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_spUserName = (EditText) findViewById(R.id.ET_spUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_parentName = (EditText) findViewById(R.id.ET_parentName);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		spinner_cityObj = (Spinner) findViewById(R.id.Spinner_cityObj);
		// ��ȡ���еĳ���
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int cityCount = cityList.size();
		cityObj_ShowText = new String[cityCount];
		for(int i=0;i<cityCount;i++) { 
			cityObj_ShowText[i] = cityList.get(i).getCityName();
		}
		// ����ѡ������ArrayAdapter��������
		cityObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityObj_ShowText);
		// ���������б�ķ��
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_cityObj.setAdapter(cityObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_cityObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentParent.setCityObj(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_cityObj.setVisibility(View.VISIBLE);
		ET_studentSex = (EditText) findViewById(R.id.ET_studentSex);
		spinner_ageRangeObj = (Spinner) findViewById(R.id.Spinner_ageRangeObj);
		// ��ȡ���е����䷶Χ
		try {
			ageRangeList = ageRangeService.QueryAgeRange(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int ageRangeCount = ageRangeList.size();
		ageRangeObj_ShowText = new String[ageRangeCount];
		for(int i=0;i<ageRangeCount;i++) { 
			ageRangeObj_ShowText[i] = ageRangeList.get(i).getShowText();
		}
		// ����ѡ������ArrayAdapter��������
		ageRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ageRangeObj_ShowText);
		// ���������б�ķ��
		ageRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_ageRangeObj.setAdapter(ageRangeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_ageRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentParent.setAgeRangeObj(ageRangeList.get(arg2).getArId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		ET_age = (EditText) findViewById(R.id.ET_age);
		ET_school = (EditText) findViewById(R.id.ET_school);
		spinner_nowStateObj = (Spinner) findViewById(R.id.Spinner_nowStateObj);
		// ��ȡ���е���״̬
		try {
			nowStateList = nowStateService.QueryNowState(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int nowStateCount = nowStateList.size();
		nowStateObj_ShowText = new String[nowStateCount];
		for(int i=0;i<nowStateCount;i++) { 
			nowStateObj_ShowText[i] = nowStateList.get(i).getStateName();
		}
		// ����ѡ������ArrayAdapter��������
		nowStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nowStateObj_ShowText);
		// ���������б�ķ��
		nowStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_nowStateObj.setAdapter(nowStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_nowStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentParent.setNowStateObj(nowStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_studentPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(StudentParentAddActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_studentPhoto);
			}
		});
		btn_studentPhoto = (Button) findViewById(R.id.btn_studentPhoto);
		btn_studentPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_studentPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_studentPhoto);  
			}
		});
		ET_studentDesc = (EditText) findViewById(R.id.ET_studentDesc);
		ET_shzt = (EditText) findViewById(R.id.ET_shzt);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*�������ѧ���ҳ���ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ�û���*/
					if(ET_spUserName.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "�û������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_spUserName.setFocusable(true);
						ET_spUserName.requestFocus();
						return;
					}
					studentParent.setSpUserName(ET_spUserName.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					studentParent.setPassword(ET_password.getText().toString());
					/*��֤��ȡ�ҳ�����*/ 
					if(ET_parentName.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "�ҳ��������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_parentName.setFocusable(true);
						ET_parentName.requestFocus();
						return;	
					}
					studentParent.setParentName(ET_parentName.getText().toString());
					/*��֤��ȡ�ֻ���*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "�ֻ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					studentParent.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡѧ���Ա�*/ 
					if(ET_studentSex.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "ѧ���Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_studentSex.setFocusable(true);
						ET_studentSex.requestFocus();
						return;	
					}
					studentParent.setStudentSex(ET_studentSex.getText().toString());
					/*��֤��ȡѧ������*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "ѧ���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					studentParent.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*��֤��ȡѧ��ѧУ*/ 
					if(ET_school.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "ѧ��ѧУ���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_school.setFocusable(true);
						ET_school.requestFocus();
						return;	
					}
					studentParent.setSchool(ET_school.getText().toString());
					if(studentParent.getStudentPhoto() != null) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						StudentParentAddActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String studentPhoto = HttpUtil.uploadFile(studentParent.getStudentPhoto());
						StudentParentAddActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						studentParent.setStudentPhoto(studentPhoto);
					} else {
						studentParent.setStudentPhoto("upload/noimage.jpg");
					}
					/*��֤��ȡѧ������*/ 
					if(ET_studentDesc.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "ѧ���������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_studentDesc.setFocusable(true);
						ET_studentDesc.requestFocus();
						return;	
					}
					studentParent.setStudentDesc(ET_studentDesc.getText().toString());
					/*��֤��ȡ���״̬*/ 
					if(ET_shzt.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "���״̬���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_shzt.setFocusable(true);
						ET_shzt.requestFocus();
						return;	
					}
					studentParent.setShzt(ET_shzt.getText().toString());
					/*��֤��ȡע��ʱ��*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "ע��ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					studentParent.setRegTime(ET_regTime.getText().toString());
					/*����ҵ���߼����ϴ�ѧ���ҳ���Ϣ*/
					StudentParentAddActivity.this.setTitle("�����ϴ�ѧ���ҳ���Ϣ���Ե�...");
					String result = studentParentService.AddStudentParent(studentParent);
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
		if (requestCode == REQ_CODE_CAMERA_studentPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_studentPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_studentPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_studentPhoto.setImageBitmap(booImageBm);
				this.iv_studentPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.studentParent.setStudentPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_studentPhoto && resultCode == Activity.RESULT_OK) {
			Bundle bundle = data.getExtras();
			String filename =  bundle.getString("fileName");
			String filepath = HttpUtil.FILE_PATH + "/" + filename;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true; 
			BitmapFactory.decodeFile(filepath, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 128*128);
			opts.inJustDecodeBounds = false; 
			try { 
				Bitmap bm = BitmapFactory.decodeFile(filepath, opts);
				this.iv_studentPhoto.setImageBitmap(bm); 
				this.iv_studentPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			studentParent.setStudentPhoto(filename); 
		}
	}
}
