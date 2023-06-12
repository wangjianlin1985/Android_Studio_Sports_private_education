package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

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
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class CoachRegisterActivity extends Activity {
	// 声明确定添加按钮
	private Button btnAdd;
	// 声明用户名输入框
	private EditText ET_coachUserName;
	// 声明登录密码输入框
	private EditText ET_password;
	// 声明姓名输入框
	private EditText ET_name;
	// 声明性别输入框
	private EditText ET_sex;
	// 声明年龄范围下拉框
	private Spinner spinner_ageRangeObj;
	private ArrayAdapter<String> ageRangeObj_adapter;
	private static  String[] ageRangeObj_ShowText  = null;
	private List<AgeRange> ageRangeList = null;
	/*年龄范围管理业务逻辑层*/
	private AgeRangeService ageRangeService = new AgeRangeService();
	// 声明年龄输入框
	private EditText ET_age;
	// 声明手机号输入框
	private EditText ET_telephone;
	// 声明城市下拉框
	private Spinner spinner_cityObj;
	private ArrayAdapter<String> cityObj_adapter;
	private static  String[] cityObj_ShowText  = null;
	private List<City> cityList = null;
	/*城市管理业务逻辑层*/
	private CityService cityService = new CityService();
	// 声明现状态下拉框
	private Spinner spinner_nowStateObj;
	private ArrayAdapter<String> nowStateObj_adapter;
	private static  String[] nowStateObj_ShowText  = null;
	private List<NowState> nowStateList = null;
	/*现状态管理业务逻辑层*/
	private NowStateService nowStateService = new NowStateService();
	// 声明收费价格区间下拉框
	private Spinner spinner_priceRangeObj;
	private ArrayAdapter<String> priceRangeObj_adapter;
	private static  String[] priceRangeObj_ShowText  = null;
	private List<PriceRange> priceRangeList = null;
	/*收费价格区间管理业务逻辑层*/
	private PriceRangeService priceRangeService = new PriceRangeService();
	// 声明价格(元/小时)输入框
	private EditText ET_price;
	// 声明教练照片图片框控件
	private ImageView iv_coachPhoto;
	private Button btn_coachPhoto;
	protected int REQ_CODE_SELECT_IMAGE_coachPhoto = 1;
	private int REQ_CODE_CAMERA_coachPhoto = 2;
	// 声明教练简介输入框
	private EditText ET_coachDesc;
	 
	protected String carmera_path;
	/*要保存的私教信息*/
	Coach coach = new Coach();
	/*私教管理业务逻辑层*/
	private CoachService coachService = new CoachService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.coach_register); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("注册私教");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_coachUserName = (EditText) findViewById(R.id.ET_coachUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
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
				coach.setAgeRangeObj(ageRangeList.get(arg2).getArId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		ET_age = (EditText) findViewById(R.id.ET_age);
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
				coach.setCityObj(cityList.get(arg2).getCityNo()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_cityObj.setVisibility(View.VISIBLE);
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
				coach.setNowStateObj(nowStateList.get(arg2).getStateId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		spinner_priceRangeObj = (Spinner) findViewById(R.id.Spinner_priceRangeObj);
		// 获取所有的收费价格区间
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
		// 将可选内容与ArrayAdapter连接起来
		priceRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, priceRangeObj_ShowText);
		// 设置下拉列表的风格
		priceRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_priceRangeObj.setAdapter(priceRangeObj_adapter);
		// 添加事件Spinner事件监听
		spinner_priceRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				coach.setPriceRangeObj(priceRangeList.get(arg2).getPrId()); 
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// 设置默认值
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		ET_price = (EditText) findViewById(R.id.ET_price);
		iv_coachPhoto = (ImageView) findViewById(R.id.iv_coachPhoto);
		/*单击图片显示控件时进行图片的选择*/
		iv_coachPhoto.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(CoachRegisterActivity.this,photoListActivity.class);
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
		 
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*单击添加私教按钮*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取用户名*/
					if(ET_coachUserName.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "用户名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_coachUserName.setFocusable(true);
						ET_coachUserName.requestFocus();
						return;
					}
					coach.setCoachUserName(ET_coachUserName.getText().toString());
					/*验证获取登录密码*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "登录密码输入不能为空!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					coach.setPassword(ET_password.getText().toString());
					/*验证获取姓名*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "姓名输入不能为空!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					coach.setName(ET_name.getText().toString());
					/*验证获取性别*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "性别输入不能为空!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					coach.setSex(ET_sex.getText().toString());
					/*验证获取年龄*/ 
					if(ET_age.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "年龄输入不能为空!", Toast.LENGTH_LONG).show();
						ET_age.setFocusable(true);
						ET_age.requestFocus();
						return;	
					}
					coach.setAge(Integer.parseInt(ET_age.getText().toString()));
					/*验证获取手机号*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "手机号输入不能为空!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					coach.setTelephone(ET_telephone.getText().toString());
					/*验证获取价格(元/小时)*/ 
					if(ET_price.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "价格(元/小时)输入不能为空!", Toast.LENGTH_LONG).show();
						ET_price.setFocusable(true);
						ET_price.requestFocus();
						return;	
					}
					coach.setPrice(Integer.parseInt(ET_price.getText().toString()));
					if(coach.getCoachPhoto() != null) {
						//如果图片地址不为空，说明用户选择了图片，这时需要连接服务器上传图片
						CoachRegisterActivity.this.setTitle("正在上传图片，稍等...");
						String coachPhoto = HttpUtil.uploadFile(coach.getCoachPhoto());
						CoachRegisterActivity.this.setTitle("图片上传完毕！");
						coach.setCoachPhoto(coachPhoto);
					} else {
						coach.setCoachPhoto("upload/noimage.jpg");
					}
					/*验证获取教练简介*/ 
					if(ET_coachDesc.getText().toString().equals("")) {
						Toast.makeText(CoachRegisterActivity.this, "教练简介输入不能为空!", Toast.LENGTH_LONG).show();
						ET_coachDesc.setFocusable(true);
						ET_coachDesc.requestFocus();
						return;	
					}
					coach.setCoachDesc(ET_coachDesc.getText().toString());
					 
					coach.setShzt("待审核");
					/*获取注册时间*/  
					coach.setRegTime("");
					/*调用业务逻辑层上传私教信息*/
					CoachRegisterActivity.this.setTitle("正在上传私教信息，稍等...");
					String result = coachService.AddCoach(coach);
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
					booImageBm.compress(Bitmap.CompressFormat.JPEG, 30, jpgOutputStream);// 把数据写入文件 
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
