package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.Coach;
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
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.ImageView;
import android.widget.TextView;
public class CoachQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����û��������
	private EditText ET_coachUserName;
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
	/*�۸�Χ����ҵ���߼���*/
	private PriceRangeService priceRangeService = new PriceRangeService();
	// �������״̬�����
	private EditText ET_shzt;
	/*��ѯ�����������浽���������*/
	private Coach queryConditionCoach = new Coach();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.coach_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("����˽�̲�ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_coachUserName = (EditText) findViewById(R.id.ET_coachUserName);
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
		ageRangeObj_ShowText = new String[ageRangeCount+1];
		ageRangeObj_ShowText[0] = "������";
		for(int i=1;i<=ageRangeCount;i++) { 
			ageRangeObj_ShowText[i] = ageRangeList.get(i-1).getShowText();
		} 
		// ����ѡ������ArrayAdapter��������
		ageRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ageRangeObj_ShowText);
		// �������䷶Χ�����б�ķ��
		ageRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_ageRangeObj.setAdapter(ageRangeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_ageRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCoach.setAgeRangeObj(ageRangeList.get(arg2-1).getArId()); 
				else
					queryConditionCoach.setAgeRangeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		spinner_cityObj = (Spinner) findViewById(R.id.Spinner_cityObj);
		// ��ȡ���еĳ���
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int cityCount = cityList.size();
		cityObj_ShowText = new String[cityCount+1];
		cityObj_ShowText[0] = "������";
		for(int i=1;i<=cityCount;i++) { 
			cityObj_ShowText[i] = cityList.get(i-1).getCityName();
		} 
		// ����ѡ������ArrayAdapter��������
		cityObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityObj_ShowText);
		// ���ó��������б�ķ��
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_cityObj.setAdapter(cityObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_cityObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCoach.setCityObj(cityList.get(arg2-1).getCityNo()); 
				else
					queryConditionCoach.setCityObj("");
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
		nowStateObj_ShowText = new String[nowStateCount+1];
		nowStateObj_ShowText[0] = "������";
		for(int i=1;i<=nowStateCount;i++) { 
			nowStateObj_ShowText[i] = nowStateList.get(i-1).getStateName();
		} 
		// ����ѡ������ArrayAdapter��������
		nowStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nowStateObj_ShowText);
		// ������״̬�����б�ķ��
		nowStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_nowStateObj.setAdapter(nowStateObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_nowStateObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCoach.setNowStateObj(nowStateList.get(arg2-1).getStateId()); 
				else
					queryConditionCoach.setNowStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		spinner_priceRangeObj = (Spinner) findViewById(R.id.Spinner_priceRangeObj);
		// ��ȡ���еļ۸�Χ
		try {
			priceRangeList = priceRangeService.QueryPriceRange(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int priceRangeCount = priceRangeList.size();
		priceRangeObj_ShowText = new String[priceRangeCount+1];
		priceRangeObj_ShowText[0] = "������";
		for(int i=1;i<=priceRangeCount;i++) { 
			priceRangeObj_ShowText[i] = priceRangeList.get(i-1).getShowText();
		} 
		// ����ѡ������ArrayAdapter��������
		priceRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, priceRangeObj_ShowText);
		// �����շѼ۸����������б�ķ��
		priceRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// ��adapter ��ӵ�spinner��
		spinner_priceRangeObj.setAdapter(priceRangeObj_adapter);
		// ����¼�Spinner�¼�����
		spinner_priceRangeObj.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
				if(arg2 != 0)
					queryConditionCoach.setPriceRangeObj(priceRangeList.get(arg2-1).getPrId()); 
				else
					queryConditionCoach.setPriceRangeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_priceRangeObj.setVisibility(View.VISIBLE);
		ET_shzt = (EditText) findViewById(R.id.ET_shzt);
		 
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionCoach.setCoachUserName(ET_coachUserName.getText().toString());
					queryConditionCoach.setName(ET_name.getText().toString());
					queryConditionCoach.setSex(ET_sex.getText().toString());
					//queryConditionCoach.setShzt(ET_shzt.getText().toString());
					queryConditionCoach.setShzt("���ͨ��");
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionCoach", queryConditionCoach);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
