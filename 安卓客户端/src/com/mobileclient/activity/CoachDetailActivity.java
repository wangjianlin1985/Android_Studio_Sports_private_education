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
	// �������ذ�ť
	private Button btnReturn;
	// �����û����ؼ�
	private TextView TV_coachUserName;
	// ������¼����ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �������䷶Χ�ؼ�
	private TextView TV_ageRangeObj;
	// ��������ؼ�
	private TextView TV_age;
	// �����ֻ��ſؼ�
	private TextView TV_telephone;
	// �������пؼ�
	private TextView TV_cityObj;
	// ������״̬�ؼ�
	private TextView TV_nowStateObj;
	// �����շѼ۸�����ؼ�
	private TextView TV_priceRangeObj;
	// �����۸�(Ԫ/Сʱ)�ؼ�
	private TextView TV_price;
	// ����������ƬͼƬ��
	private ImageView iv_coachPhoto;
	// �����������ؼ�
	private TextView TV_coachDesc;
	// �������״̬�ؼ�
	private TextView TV_shzt;
	// ����ע��ʱ��ؼ�
	private TextView TV_regTime;
	/* Ҫ�����˽����Ϣ */
	Coach coach = new Coach(); 
	/* ˽�̹���ҵ���߼��� */
	private CoachService coachService = new CoachService();
	private AgeRangeService ageRangeService = new AgeRangeService();
	private CityService cityService = new CityService();
	private NowStateService nowStateService = new NowStateService();
	private PriceRangeService priceRangeService = new PriceRangeService();
	private String coachUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coach_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴˽������");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
			// ��ȡͼƬ����
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
