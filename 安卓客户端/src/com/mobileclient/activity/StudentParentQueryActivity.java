package com.mobileclient.activity;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.mobileclient.domain.StudentParent;
import com.mobileclient.domain.City;
import com.mobileclient.service.CityService;
import com.mobileclient.domain.AgeRange;
import com.mobileclient.service.AgeRangeService;
import com.mobileclient.domain.NowState;
import com.mobileclient.service.NowStateService;

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
public class StudentParentQueryActivity extends Activity {
	// ������ѯ��ť
	private Button btnQuery;
	// �����û��������
	private EditText ET_spUserName;
	// �����ҳ����������
	private EditText ET_parentName;
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
	// ����ѧ��ѧУ�����
	private EditText ET_school;
	// ������״̬������
	private Spinner spinner_nowStateObj;
	private ArrayAdapter<String> nowStateObj_adapter;
	private static  String[] nowStateObj_ShowText  = null;
	private List<NowState> nowStateList = null; 
	/*��״̬����ҵ���߼���*/
	private NowStateService nowStateService = new NowStateService();
	// �������״̬�����
	private EditText ET_shzt;
	/*��ѯ�����������浽���������*/
	private StudentParent queryConditionStudentParent = new StudentParent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.studentparent_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("����ѧ���ҳ���ѯ����");
		ImageView back_btn = (ImageView) this.findViewById(R.id.back_btn);
		back_btn.setOnClickListener(new android.view.View.OnClickListener(){
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		btnQuery = (Button) findViewById(R.id.btnQuery);
		ET_spUserName = (EditText) findViewById(R.id.ET_spUserName);
		ET_parentName = (EditText) findViewById(R.id.ET_parentName);
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
					queryConditionStudentParent.setCityObj(cityList.get(arg2-1).getCityNo()); 
				else
					queryConditionStudentParent.setCityObj("");
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
					queryConditionStudentParent.setAgeRangeObj(ageRangeList.get(arg2-1).getArId()); 
				else
					queryConditionStudentParent.setAgeRangeObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		ET_school = (EditText) findViewById(R.id.ET_school);
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
					queryConditionStudentParent.setNowStateObj(nowStateList.get(arg2-1).getStateId()); 
				else
					queryConditionStudentParent.setNowStateObj(0);
			}
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		// ����Ĭ��ֵ
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		ET_shzt = (EditText) findViewById(R.id.ET_shzt);
		/*������ѯ��ť*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��ȡ��ѯ����*/
					queryConditionStudentParent.setSpUserName(ET_spUserName.getText().toString());
					queryConditionStudentParent.setParentName(ET_parentName.getText().toString());
					queryConditionStudentParent.setStudentSex(ET_studentSex.getText().toString());
					queryConditionStudentParent.setSchool(ET_school.getText().toString());
					//queryConditionStudentParent.setShzt(ET_shzt.getText().toString());
					queryConditionStudentParent.setShzt("���ͨ��");
					Intent intent = getIntent();
					//����ʹ��bundle��������������
					Bundle bundle =new Bundle();
					//�����������Ȼ�Ǽ�ֵ�Ե���ʽ
					bundle.putSerializable("queryConditionStudentParent", queryConditionStudentParent);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
