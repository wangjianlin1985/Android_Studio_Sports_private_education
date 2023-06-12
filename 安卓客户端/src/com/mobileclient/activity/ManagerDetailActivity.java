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
	// �������ذ�ť
	private Button btnReturn;
	// ��������Ա�û����ؼ�
	private TextView TV_managerUserName;
	// ������¼����ؼ�
	private TextView TV_password;
	// ���������ؼ�
	private TextView TV_name;
	// �����Ա�ؼ�
	private TextView TV_sex;
	// �����������ڿؼ�
	private TextView TV_birthDate;
	// ������ϵ�绰�ؼ�
	private TextView TV_telephone;
	/* Ҫ�������ͨ����Ա��Ϣ */
	Manager manager = new Manager(); 
	/* ��ͨ����Ա����ҵ���߼��� */
	private ManagerService managerService = new ManagerService();
	private String managerUserName;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.manager_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��ͨ����Ա����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
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
	/* ��ʼ����ʾ������������ */
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
