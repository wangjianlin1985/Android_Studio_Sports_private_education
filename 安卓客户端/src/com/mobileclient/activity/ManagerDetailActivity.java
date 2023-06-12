package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.Manager;
import com.mobileclient.service.ManagerService;
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
public class ManagerDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明管理员用户名控件
	private TextView TV_managerUserName;
	// 声明登录密码控件
	private TextView TV_password;
	// 声明姓名控件
	private TextView TV_name;
	// 声明性别控件
	private TextView TV_sex;
	// 声明出生日期控件
	private TextView TV_birthDate;
	// 声明联系电话控件
	private TextView TV_telephone;
	/* 要保存的普通管理员信息 */
	Manager manager = new Manager(); 
	/* 普通管理员管理业务逻辑层 */
	private ManagerService managerService = new ManagerService();
	private String managerUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.manager_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看普通管理员详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_managerUserName = (TextView) findViewById(R.id.TV_managerUserName);
		TV_password = (TextView) findViewById(R.id.TV_password);
		TV_name = (TextView) findViewById(R.id.TV_name);
		TV_sex = (TextView) findViewById(R.id.TV_sex);
		TV_birthDate = (TextView) findViewById(R.id.TV_birthDate);
		TV_telephone = (TextView) findViewById(R.id.TV_telephone);
		Bundle extras = this.getIntent().getExtras();
		managerUserName = extras.getString("managerUserName");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				ManagerDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    manager = managerService.GetManager(managerUserName); 
		this.TV_managerUserName.setText(manager.getManagerUserName());
		this.TV_password.setText(manager.getPassword());
		this.TV_name.setText(manager.getName());
		this.TV_sex.setText(manager.getSex());
		Date birthDate = new Date(manager.getBirthDate().getTime());
		String birthDateStr = (birthDate.getYear() + 1900) + "-" + (birthDate.getMonth()+1) + "-" + birthDate.getDate();
		this.TV_birthDate.setText(birthDateStr);
		this.TV_telephone.setText(manager.getTelephone());
	} 
}
