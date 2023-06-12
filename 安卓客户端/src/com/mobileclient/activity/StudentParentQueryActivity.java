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
	// 声明查询按钮
	private Button btnQuery;
	// 声明用户名输入框
	private EditText ET_spUserName;
	// 声明家长姓名输入框
	private EditText ET_parentName;
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
	// 声明学生学校输入框
	private EditText ET_school;
	// 声明现状态下拉框
	private Spinner spinner_nowStateObj;
	private ArrayAdapter<String> nowStateObj_adapter;
	private static  String[] nowStateObj_ShowText  = null;
	private List<NowState> nowStateList = null; 
	/*现状态管理业务逻辑层*/
	private NowStateService nowStateService = new NowStateService();
	// 声明审核状态输入框
	private EditText ET_shzt;
	/*查询过滤条件保存到这个对象中*/
	private StudentParent queryConditionStudentParent = new StudentParent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		// 设置当前Activity界面布局
		setContentView(R.layout.studentparent_query);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("设置学生家长查询条件");
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
		// 获取所有的城市
		try {
			cityList = cityService.QueryCity(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int cityCount = cityList.size();
		cityObj_ShowText = new String[cityCount+1];
		cityObj_ShowText[0] = "不限制";
		for(int i=1;i<=cityCount;i++) { 
			cityObj_ShowText[i] = cityList.get(i-1).getCityName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		cityObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, cityObj_ShowText);
		// 设置城市下拉列表的风格
		cityObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_cityObj.setAdapter(cityObj_adapter);
		// 添加事件Spinner事件监听
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
		ageRangeObj_ShowText = new String[ageRangeCount+1];
		ageRangeObj_ShowText[0] = "不限制";
		for(int i=1;i<=ageRangeCount;i++) { 
			ageRangeObj_ShowText[i] = ageRangeList.get(i-1).getShowText();
		} 
		// 将可选内容与ArrayAdapter连接起来
		ageRangeObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, ageRangeObj_ShowText);
		// 设置年龄范围下拉列表的风格
		ageRangeObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_ageRangeObj.setAdapter(ageRangeObj_adapter);
		// 添加事件Spinner事件监听
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
		// 设置默认值
		spinner_ageRangeObj.setVisibility(View.VISIBLE);
		ET_school = (EditText) findViewById(R.id.ET_school);
		spinner_nowStateObj = (Spinner) findViewById(R.id.Spinner_nowStateObj);
		// 获取所有的现状态
		try {
			nowStateList = nowStateService.QueryNowState(null);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		int nowStateCount = nowStateList.size();
		nowStateObj_ShowText = new String[nowStateCount+1];
		nowStateObj_ShowText[0] = "不限制";
		for(int i=1;i<=nowStateCount;i++) { 
			nowStateObj_ShowText[i] = nowStateList.get(i-1).getStateName();
		} 
		// 将可选内容与ArrayAdapter连接起来
		nowStateObj_adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, nowStateObj_ShowText);
		// 设置现状态下拉列表的风格
		nowStateObj_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// 将adapter 添加到spinner中
		spinner_nowStateObj.setAdapter(nowStateObj_adapter);
		// 添加事件Spinner事件监听
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
		// 设置默认值
		spinner_nowStateObj.setVisibility(View.VISIBLE);
		ET_shzt = (EditText) findViewById(R.id.ET_shzt);
		/*单击查询按钮*/
		btnQuery.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*获取查询参数*/
					queryConditionStudentParent.setSpUserName(ET_spUserName.getText().toString());
					queryConditionStudentParent.setParentName(ET_parentName.getText().toString());
					queryConditionStudentParent.setStudentSex(ET_studentSex.getText().toString());
					queryConditionStudentParent.setSchool(ET_school.getText().toString());
					//queryConditionStudentParent.setShzt(ET_shzt.getText().toString());
					queryConditionStudentParent.setShzt("审核通过");
					Intent intent = getIntent();
					//这里使用bundle绷带来传输数据
					Bundle bundle =new Bundle();
					//传输的内容仍然是键值对的形式
					bundle.putSerializable("queryConditionStudentParent", queryConditionStudentParent);
					intent.putExtras(bundle);
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
			});
	}
}
