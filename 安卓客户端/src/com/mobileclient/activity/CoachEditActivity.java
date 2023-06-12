package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.Coach;
import com.mobileclient.service.CoachService;
import com.mobileclient.domain.AgeRange;
import com.mobileclient.service.AgeRangeService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.NowState;
import com.mobileclient.service.NowStateService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class CoachEditActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnUpdate;
	// �����û���TextView
	private TextView TV_coachUserName;
	// ������¼���������
	private EditText ET_password;
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// �������䷶Χ������
	private Spinner spinner_ageRangeObj;
	private ArrayAdapter<String> ageRangeObj_adapter;
	private static  String[] ageRangeObj_ShowText  = null;
	private List<AgeRange> ageRangeList = null;
	/*���䷶Χ����ҵ���߼���*/
	private AgeRangeService ageRangeService = new AgeRangeService();
	// �������������
	private EditText ET_age;
	// �����ֻ��������
	private EditText ET_telephone;
	// ��������������
	private Spinner spinner_cityObj;
	private ArrayAdapter<String> cityObj_adapter;
	private static  String[] cityObj_ShowText  = null;
	private List<City> cityList = null;
	/*���й���ҵ���߼���*/
	private CityService cityService = new CityService();
	// ������״̬������
	private Spinner spinner_nowStateObj;
	private ArrayAdapter<String> nowStateObj_adapter;
	private static  String[] nowStateObj_ShowText  = null;
	private List<NowState> nowStateList = null;
	/*��״̬����ҵ���߼���*/
	private NowStateService nowStateService = new NowStateService();
	// �����շѼ۸�����������
	private Spinner spinner_priceRangeObj;
	private ArrayAdapter<String> priceRangeObj_adapter;
	private static  String[] priceRangeObj_ShowText  = null;
	private List<PriceRange> priceRangeList = null;
	/*�շѼ۸��������ҵ���߼���*/
	private PriceRangeService priceRangeService = new PriceRangeService();
	// �����۸�(Ԫ/Сʱ)�����
	private EditText ET_price;
	// ����������ƬͼƬ��ؼ�
	private ImageView iv_coachPhoto;
	private Button btn_coachPhoto;
	protected int REQ_CODE_SELECT_IMAGE_coachPhoto = 1;
	private int REQ_CODE_CAMERA_coachPhoto = 2;
	// ����������������
	private EditText ET_coachDesc;
	// �������״̬�����
	private EditText ET_shzt;
	// ����ע��ʱ�������
	private EditText ET_regTime;
	protected String carmera_path;
	/*Ҫ�����˽����Ϣ*/
	Coach coach = new Coach();
	/*˽�̹���ҵ���߼���*/
	private CoachService coachService = new CoachService();

	private String coachUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coach_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�༭˽����Ϣ");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_coachUserName = (TextView) findViewById(R.id.TV_coachUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
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
		// ����ͼ����������б�ķ��
		ageRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_ageRangeObj.setAdapter(ageRangeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_ageRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coach.setAgeRangeObj(ageRangeList.get(arg2).getArId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		ET_age = (EditText) findViewById(R.id.ET_age);
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
		// ����ͼ����������б�ķ��
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_cityObj.setAdapter(cityObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_cityObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coach.setCityObj(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_cityObj.setVisibility(View.VISIBLE);
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
		// ����ͼ����������б�ķ��
		nowStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_nowStateObj.setAdapter(nowStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_nowStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coach.setNowStateObj(nowStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		spinner_priceRangeObj = (Spinner) findViewById(R.id.Spinner_priceRangeObj);
		// ��ȡ���е��շѼ۸�����
		try {
			priceRangeList = priceRangeService.QueryPriceRange(null);
		} catch (Exception e1) { 
			e1.printStackTrace(); 
		}
		int priceRangeCount = priceRangeList.size();
		priceRangeObj_ShowText = new String[priceRangeCount];
		for(int i=0;i<priceRangeCount;i++) { 
			priceRangeObj_ShowText[i] = priceRangeList.get(i).getShowText();
		}
		// ����ѡ������ArrayAdapter��������
		priceRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, priceRangeObj_ShowText);
		// ����ͼ����������б�ķ��
		priceRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_priceRangeObj.setAdapter(priceRangeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_priceRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coach.setPriceRangeObj(priceRangeList.get(arg2).getPrId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		iv_coachPhoto = (ImageView) findViewById(R.id.iv_coachPhoto);
		/*����ͼƬ��ʾ�ؼ�ʱ����ͼƬ��ѡ��*/
		iv_coachPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CoachEditActivity.this,photoListActivity.class);
				startActivityForResult(intent,REQ_CODE_SELECT_IMAGE_coachPhoto);
			}
		});
		btn_coachPhoto = (Button) findViewById(R.id.btn_coachPhoto);
		btn_coachPhoto.setOnClickListener(new OnClickListener() { 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
				carmera_path = HttpUtil.FILE_PATH + "/carmera_coachPhoto.bmp";
				File out = new File(carmera_path); 
				intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(out)); 
				startActivityForResult(intent, REQ_CODE_CAMERA_coachPhoto);  
			}
		});
		ET_coachDesc = (EditText) findViewById(R.id.ET_coachDesc);
		ET_shzt = (EditText) findViewById(R.id.ET_shzt);
		ET_regTime = (EditText) findViewById(R.id.ET_regTime);
		ET_shzt.setVisibility(View.GONE);
		ET_regTime.setVisibility(View.GONE);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		coachUserName = extras.getString("coachUserName");
		/*�����޸�˽�̰�ť*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					coach.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					coach.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					coach.setSex(ET_sex.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					coach.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*��֤��ȡ�ֻ���*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "�ֻ������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					coach.setTelephone(ET_telephone.getText().toString());
					/*��֤��ȡ�۸�(Ԫ/Сʱ)*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "�۸�(Ԫ/Сʱ)���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					coach.setPrice(Integer.parseInt(ET_price.getText().toString()));
					if (!coach.getCoachPhoto().startsWith("upload/")) {
						//���ͼƬ��ַ��Ϊ�գ�˵���û�ѡ����ͼƬ����ʱ��Ҫ���ӷ������ϴ�ͼƬ
						CoachEditActivity.this.setTitle("�����ϴ�ͼƬ���Ե�...");
						String coachPhoto = HttpUtil.uploadFile(coach.getCoachPhoto());
						CoachEditActivity.this.setTitle("ͼƬ�ϴ���ϣ�");
						coach.setCoachPhoto(coachPhoto);
					} 
					/*��֤��ȡ�������*/ 
					if(ET_coachDesc.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "����������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_coachDesc.setFocusable(true);
						ET_coachDesc.requestFocus();
						return;	
					}
					coach.setCoachDesc(ET_coachDesc.getText().toString());
					/*��֤��ȡ���״̬*/ 
					if(ET_shzt.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "���״̬���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_shzt.setFocusable(true);
						ET_shzt.requestFocus();
						return;	
					}
					coach.setShzt("�����");
					/*��֤��ȡע��ʱ��*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(CoachEditActivity.this, "ע��ʱ�����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					coach.setRegTime(ET_regTime.getText().toString());
					/*����ҵ���߼����ϴ�˽����Ϣ*/
					CoachEditActivity.this.setTitle("���ڸ���˽����Ϣ���Ե�...");
					String result = coachService.UpdateCoach(coach);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					//finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* ��ʼ����ʾ�༭��������� */
	private void initViewData() {
	    coach = coachService.GetCoach(coachUserName);
		this.TV_coachUserName.setText(coachUserName);
		this.ET_password.setText(coach.getPassword());
		this.ET_name.setText(coach.getName());
		this.ET_sex.setText(coach.getSex());
		for (int i = 0; i < ageRangeList.size(); i++) {
			if (coach.getAgeRangeObj() == ageRangeList.get(i).getArId()) {
				this.spinner_ageRangeObj.setSelection(i);
				break;
			}
		}
		this.ET_age.setText(coach.getAge() + "");
		this.ET_telephone.setText(coach.getTelephone());
		for (int i = 0; i < cityList.size(); i++) {
			if (coach.getCityObj().equals(cityList.get(i).getCityNo())) {
				this.spinner_cityObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < nowStateList.size(); i++) {
			if (coach.getNowStateObj() == nowStateList.get(i).getStateId()) {
				this.spinner_nowStateObj.setSelection(i);
				break;
			}
		}
		for (int i = 0; i < priceRangeList.size(); i++) {
			if (coach.getPriceRangeObj() == priceRangeList.get(i).getPrId()) {
				this.spinner_priceRangeObj.setSelection(i);
				break;
			}
		}
		this.ET_price.setText(coach.getPrice() + "");
		byte[] coachPhoto_data = null;
		try {
			// ��ȡͼƬ����
			coachPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + coach.getCoachPhoto());
			Bitmap coachPhoto = BitmapFactory.decodeByteArray(coachPhoto_data, 0, coachPhoto_data.length);
			this.iv_coachPhoto.setImageBitmap(coachPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.ET_coachDesc.setText(coach.getCoachDesc());
		this.ET_shzt.setText(coach.getShzt());
		this.ET_regTime.setText(coach.getRegTime());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQ_CODE_CAMERA_coachPhoto  && resultCode == Activity.RESULT_OK) {
			carmera_path = HttpUtil.FILE_PATH + "/carmera_coachPhoto.bmp"; 
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(carmera_path, opts); 
			opts.inSampleSize = photoListActivity.computeSampleSize(opts, -1, 300*300);
			opts.inJustDecodeBounds = false;
			try {
				Bitmap booImageBm = BitmapFactory.decodeFile(carmera_path, opts);
				String jpgFileName = "carmera_coachPhoto.jpg";
				String jpgFilePath =  HttpUtil.FILE_PATH + "/" + jpgFileName;
				try {
					FileOutputStream jpgOutputStream = new FileOutputStream(jpgFilePath);
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// ������д���ļ� 
					File bmpFile = new File(carmera_path);
					bmpFile.delete();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} 
				this.iv_coachPhoto.setImageBitmap(booImageBm);
				this.iv_coachPhoto.setScaleType(ScaleType.FIT_CENTER);
				this.coach.setCoachPhoto(jpgFileName);
			} catch (OutOfMemoryError err) {  }
		}

		if(requestCode == REQ_CODE_SELECT_IMAGE_coachPhoto && resultCode == Activity.RESULT_OK) {
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
				this.iv_coachPhoto.setImageBitmap(bm); 
				this.iv_coachPhoto.setScaleType(ScaleType.FIT_CENTER); 
			} catch (OutOfMemoryError err) {  } 
			coach.setCoachPhoto(filename); 
		}
	}
}
