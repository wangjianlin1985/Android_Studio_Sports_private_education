package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.StudentParent;
import com.mobileclient.service.StudentParentService;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.AgeRange;
import com.mobileclient.service.AgeRangeService;
import com.mobileclient.domain.NowState;
import com.mobileclient.service.NowStateService;
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
public class StudentParentDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明用户名控件
	private TextView TV_spUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明家长姓名控件
	private TextView TV_parentName;
	// 声明手机号控件
	private TextView TV_telephone;
	// 声明城市控件
	private TextView TV_cityObj;
	// 声明学生性别控件
	private TextView TV_studentSex;
	// 声明年龄范围控件
	private TextView TV_ageRangeObj;
	// 声明学生年龄控件
	private TextView TV_age;
	// 声明学生学校控件
	private TextView TV_school;
	// 声明现状态控件
	private TextView TV_nowStateObj;
	// 声明学生照片图片框
	private ImageView iv_studentPhoto;
	// 声明学生介绍控件
	private TextView TV_studentDesc;
	// 声明审核状态控件
	private TextView TV_shzt;
	// 声明注册时间控件
	private TextView TV_regTime;
	/* 要保存的学生家长信息 */
	StudentParent studentParent = new StudentParent(); 
	/* 学生家长管理业务逻辑层 */
	private StudentParentService studentParentService = new StudentParentService();
	private CityService cityService = new CityService();
	private AgeRangeService ageRangeService = new AgeRangeService();
	private NowStateService nowStateService = new NowStateService();
	private String spUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.studentparent_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看学生家长详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_spUserName = (TextView) findViewById(R.id.TV_spUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_parentName = (TextView) findViewById(R.id.TV_parentName);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_cityObj = (TextView) findViewById(R.id.TV_cityObj);
		TV_studentSex = (TextView) findViewById(R.id.TV_studentSex);
		TV_ageRangeObj = (TextView) findViewById(R.id.TV_ageRangeObj);
		TV_age = (TextView) findViewById(R.id.TV_age);
		TV_school = (TextView) findViewById(R.id.TV_school);
		TV_nowStateObj = (TextView) findViewById(R.id.TV_nowStateObj);
		iv_studentPhoto = (ImageView) findViewById(R.id.iv_studentPhoto); 
		TV_studentDesc = (TextView) findViewById(R.id.TV_studentDesc);
		TV_shzt = (TextView) findViewById(R.id.TV_shzt);
		TV_regTime = (TextView) findViewById(R.id.TV_regTime);
		Bundle extras = this.getIntent().getExtras();
		spUserName = extras.getString("spUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				StudentParentDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    studentParent = studentParentService.GetStudentParent(spUserName); 
		this.TV_spUserName.setText(studentParent.getSpUserName());
		this.TV_password.setText(studentParent.getPassword());
		this.TV_parentName.setText(studentParent.getParentName());
		this.TV_telephone.setText(studentParent.getTelephone());
		City cityObj = cityService.GetCity(studentParent.getCityObj());
		this.TV_cityObj.setText(cityObj.getCityName());
		this.TV_studentSex.setText(studentParent.getStudentSex());
		AgeRange ageRangeObj = ageRangeService.GetAgeRange(studentParent.getAgeRangeObj());
		this.TV_ageRangeObj.setText(ageRangeObj.getShowText());
		this.TV_age.setText(studentParent.getAge() + "");
		this.TV_school.setText(studentParent.getSchool());
		NowState nowStateObj = nowStateService.GetNowState(studentParent.getNowStateObj());
		this.TV_nowStateObj.setText(nowStateObj.getStateName());
		byte[] studentPhoto_data = null;
		try {
			// 获取图片数据
			studentPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + studentParent.getStudentPhoto());
			Bitmap studentPhoto = BitmapFactory.decodeByteArray(studentPhoto_data, 0,studentPhoto_data.length);
			this.iv_studentPhoto.setImageBitmap(studentPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_studentDesc.setText(studentParent.getStudentDesc());
		this.TV_shzt.setText(studentParent.getShzt());
		this.TV_regTime.setText(studentParent.getRegTime());
	} 
}
