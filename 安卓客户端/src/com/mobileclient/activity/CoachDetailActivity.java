package com.mobileclient.activity;

import java.util.Date;
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
public class CoachDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明用户名控件
	private TextView TV_coachUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明年龄范围控件
	private TextView TV_ageRangeObj;
	// 声明年龄控件
	private TextView TV_age;
	// 声明手机号控件
	private TextView TV_telephone;
	// 声明城市控件
	private TextView TV_cityObj;
	// 声明现状态控件
	private TextView TV_nowStateObj;
	// 声明收费价格区间控件
	private TextView TV_priceRangeObj;
	// 声明价格(元/小时)控件
	private TextView TV_price;
	// 声明教练照片图片框
	private ImageView iv_coachPhoto;
	// 声明教练简介控件
	private TextView TV_coachDesc;
	// 声明审核状态控件
	private TextView TV_shzt;
	// 声明注册时间控件
	private TextView TV_regTime;
	/* 要保存的私教信息 */
	Coach coach = new Coach(); 
	/* 私教管理业务逻辑层 */
	private CoachService coachService = new CoachService();
	private AgeRangeService ageRangeService = new AgeRangeService();
	private CityService cityService = new CityService();
	private NowStateService nowStateService = new NowStateService();
	private PriceRangeService priceRangeService = new PriceRangeService();
	private String coachUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.coach_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看私教详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_coachUserName = (TextView) findViewById(R.id.TV_coachUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_ageRangeObj = (TextView) findViewById(R.id.TV_ageRangeObj);
		TV_age = (TextView) findViewById(R.id.TV_age);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		TV_cityObj = (TextView) findViewById(R.id.TV_cityObj);
		TV_nowStateObj = (TextView) findViewById(R.id.TV_nowStateObj);
		TV_priceRangeObj = (TextView) findViewById(R.id.TV_priceRangeObj);
		TV_price = (TextView) findViewById(R.id.TV_price);
		iv_coachPhoto = (ImageView) findViewById(R.id.iv_coachPhoto); 
		TV_coachDesc = (TextView) findViewById(R.id.TV_coachDesc);
		TV_shzt = (TextView) findViewById(R.id.TV_shzt);
		TV_regTime = (TextView) findViewById(R.id.TV_regTime);
		Bundle extras = this.getIntent().getExtras();
		coachUserName = extras.getString("coachUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				CoachDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    coach = coachService.GetCoach(coachUserName); 
		this.TV_coachUserName.setText(coach.getCoachUserName());
		this.TV_password.setText(coach.getPassword());
		this.TV_name.setText(coach.getName());
		this.TV_sex.setText(coach.getSex());
		AgeRange ageRangeObj = ageRangeService.GetAgeRange(coach.getAgeRangeObj());
		this.TV_ageRangeObj.setText(ageRangeObj.getShowText());
		this.TV_age.setText(coach.getAge() + "");
		this.TV_telephone.setText(coach.getTelephone());
		City cityObj = cityService.GetCity(coach.getCityObj());
		this.TV_cityObj.setText(cityObj.getCityName());
		NowState nowStateObj = nowStateService.GetNowState(coach.getNowStateObj());
		this.TV_nowStateObj.setText(nowStateObj.getStateName());
		PriceRange priceRangeObj = priceRangeService.GetPriceRange(coach.getPriceRangeObj());
		this.TV_priceRangeObj.setText(priceRangeObj.getShowText());
		this.TV_price.setText(coach.getPrice() + "");
		byte[] coachPhoto_data = null;
		try {
			// 获取图片数据
			coachPhoto_data = ImageService.getImage(HttpUtil.BASE_URL + coach.getCoachPhoto());
			Bitmap coachPhoto = BitmapFactory.decodeByteArray(coachPhoto_data, 0,coachPhoto_data.length);
			this.iv_coachPhoto.setImageBitmap(coachPhoto);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.TV_coachDesc.setText(coach.getCoachDesc());
		this.TV_shzt.setText(coach.getShzt());
		this.TV_regTime.setText(coach.getRegTime());
	} 
}
