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
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明用户名输入框
	private EditText ET_spUserName;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明家长姓名输入框
	private EditText ET_parentName;
	// 声明手机号输入框
	private EditText ET_telephone;
	// 声明城市下拉框
	private Spinner spinner_cityObj;
	private ArrayAdapter<String> cityObj_adapter;
	private static  String[] cityObj_ShowText  = null;
	private List<City> cityList = null;
	/*城市管理业务逻辑层*/
	private CityService cityService = new CityService();
	// 声明学生性别输入框
	private EditText ET_studentSex;
	// 声明年龄范围下拉框
	private Spinner spinner_ageRangeObj;
	private ArrayAdapter<String> ageRangeObj_adapter;
	private static  String[] ageRangeObj_ShowText  = null;
	private List<AgeRange> ageRangeList = null;
	/*年龄范围管理业务逻辑层*/
	private AgeRangeService ageRangeService = new AgeRangeService();
	// 声明学生年龄输入框
	private EditText ET_age;
	// 声明学生学校输入框
	private EditText ET_school;
	// 声明现状态下拉框
	private Spinner spinner_nowStateObj;
	private ArrayAdapter<String> nowStateObj_adapter;
	private static  String[] nowStateObj_ShowText  = null;
	private List<NowState> nowStateList = null;
	/*现状态管理业务逻辑层*/
	private NowStateService nowStateService = new NowStateService();
	// 声明学生照片图片框控件
	private ImageView iv_studentPhoto;
	private Button btn_studentPhoto;
	protected int REQ_CODE_SELECT_IMAGE_studentPhoto = 1;
	private int REQ_CODE_CAMERA_studentPhoto = 2;
	// 声明学生介绍输入框
	private EditText ET_studentDesc;
	// 声明审核状态输入框
	private EditText ET_shzt;
	// 声明注册时间输入框
	private EditText ET_regTime;
	protected String carmera_path;
	/*要保存的学生家长信息*/
	StudentParent studentParent = new StudentParent();
	/*学生家长管理业务逻辑层*/
	private StudentParentService studentParentService = new StudentParentService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.studentparent_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("添加学生家长");
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
		// 获取所有的城市
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
		// 将可选内容与ArrayAdapter连接起来
		cityObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityObj_ShowText);
		// 设置下拉列表的风格
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_cityObj.setAdapter(cityObj_adapter);
		// 添加事件Spinner事件监听
		spinner_cityObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentParent.setCityObj(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_cityObj.setVisibility(View.VISIBLE);
		ET_studentSex = (EditText) findViewById(R.id.ET_studentSex);
		spinner_ageRangeObj = (Spinner) findViewById(R.id.Spinner_ageRangeObj);
		// 获取所有的年龄范围
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
		// 将可选内容与ArrayAdapter连接起来
		ageRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ageRangeObj_ShowText);
		// 设置下拉列表的风格
		ageRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_ageRangeObj.setAdapter(ageRangeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_ageRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentParent.setAgeRangeObj(ageRangeList.get(arg2).getArId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		ET_age = (EditText) findViewById(R.id.ET_age);
		ET_school = (EditText) findViewById(R.id.ET_school);
		spinner_nowStateObj = (Spinner) findViewById(R.id.Spinner_nowStateObj);
		// 获取所有的现状态
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
		// 将可选内容与ArrayAdapter连接起来
		nowStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nowStateObj_ShowText);
		// 设置下拉列表的风格
		nowStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_nowStateObj.setAdapter(nowStateObj_adapter);
		// 添加事件Spinner事件监听
		spinner_nowStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				studentParent.setNowStateObj(nowStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto);
		/*单击图片显示控件时进行图片的选择*/
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
		/*单击添加学生家长按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取用户名*/
					if(ET_spUserName.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "用户名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_spUserName.setFocusable(true);
						ET_spUserName.requestFocus();
						return;
					}
					studentParent.setSpUserName(ET_spUserName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					studentParent.setPassword(ET_password.getText().toString());
					/*验证获取家长姓名*/ 
					if(ET_parentName.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "家长姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_parentName.setFocusable(true);
						ET_parentName.requestFocus();
						return;	
					}
					studentParent.setParentName(ET_parentName.getText().toString());
					/*验证获取手机号*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "手机号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					studentParent.setTelephone(ET_telephone.getText().toString());
					/*验证获取学生性别*/ 
					if(ET_studentSex.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "学生性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentSex.setFocusable(true);
						ET_studentSex.requestFocus();
						return;	
					}
					studentParent.setStudentSex(ET_studentSex.getText().toString());
					/*验证获取学生年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "学生年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					studentParent.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*验证获取学生学校*/ 
					if(ET_school.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "学生学校输入不能为空!", Toast.LENGTH_LONG).show();
						ET_school.setFocusable(true);
						ET_school.requestFocus();
						return;	
					}
					studentParent.setSchool(ET_school.getText().toString());
					if(studentParent.getStudentPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						StudentParentAddActivity.this.setTitle("正在上传图片，稍等...");
						String studentPhoto = HttpUtil.uploadFile(studentParent.getStudentPhoto());
						StudentParentAddActivity.this.setTitle("图片上传完毕！");
						studentParent.setStudentPhoto(studentPhoto);
					} else {
						studentParent.setStudentPhoto("upload/noimage.jpg");
					}
					/*验证获取学生介绍*/ 
					if(ET_studentDesc.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "学生介绍输入不能为空!", Toast.LENGTH_LONG).show();
						ET_studentDesc.setFocusable(true);
						ET_studentDesc.requestFocus();
						return;	
					}
					studentParent.setStudentDesc(ET_studentDesc.getText().toString());
					/*验证获取审核状态*/ 
					if(ET_shzt.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "审核状态输入不能为空!", Toast.LENGTH_LONG).show();
						ET_shzt.setFocusable(true);
						ET_shzt.requestFocus();
						return;	
					}
					studentParent.setShzt(ET_shzt.getText().toString());
					/*验证获取注册时间*/ 
					if(ET_regTime.getText().toString().equals("")) {
						Toast.makeText(StudentParentAddActivity.this, "注册时间输入不能为空!", Toast.LENGTH_LONG).show();
						ET_regTime.setFocusable(true);
						ET_regTime.requestFocus();
						return;	
					}
					studentParent.setRegTime(ET_regTime.getText().toString());
					/*调用业务逻辑层上传学生家长信息*/
					StudentParentAddActivity.this.setTitle("正在上传学生家长信息，稍等...");
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
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
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
