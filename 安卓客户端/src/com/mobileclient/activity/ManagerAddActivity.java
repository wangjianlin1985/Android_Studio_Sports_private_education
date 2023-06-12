package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import com.mobileclient.util.HttpUtil;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.domain.Manager;
import com.mobileclient.service.ManagerService;
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
public class ManagerAddActivity extends Activity {
	// ����ȷ����Ӱ�ť
	private Button btnAdd;
	// ��������Ա�û��������
	private EditText ET_managerUserName;
	// ������¼���������
	private EditText ET_password;
	// �������������
	private EditText ET_name;
	// �����Ա������
	private EditText ET_sex;
	// ����������ڿؼ�
	private DatePicker dp_birthDate;
	// ������ϵ�绰�����
	private EditText ET_telephone;
	protected String carmera_path;
	/*Ҫ�������ͨ����Ա��Ϣ*/
	Manager manager = new Manager();
	/*��ͨ����Ա����ҵ���߼���*/
	private ManagerService managerService = new ManagerService();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.manager_add); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�����ͨ����Ա");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		ET_managerUserName = (EditText) findViewById(R.id.ET_managerUserName);
		ET_password = (EditText) findViewById(R.id.ET_password);
		ET_name = (EditText) findViewById(R.id.ET_name);
		ET_sex = (EditText) findViewById(R.id.ET_sex);
		dp_birthDate = (DatePicker)this.findViewById(R.id.dp_birthDate);
		ET_telephone = (EditText) findViewById(R.id.ET_telephone);
		btnAdd = (Button) findViewById(R.id.BtnAdd);
		/*���������ͨ����Ա��ť*/
		btnAdd.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*��֤��ȡ����Ա�û���*/
					if(ET_managerUserName.getText().toString().equals("")) {
						Toast.makeText(ManagerAddActivity.this, "����Ա�û������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_managerUserName.setFocusable(true);
						ET_managerUserName.requestFocus();
						return;
					}
					manager.setManagerUserName(ET_managerUserName.getText().toString());
					/*��֤��ȡ��¼����*/ 
					if(ET_password.getText().toString().equals("")) {
						Toast.makeText(ManagerAddActivity.this, "��¼�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_password.setFocusable(true);
						ET_password.requestFocus();
						return;	
					}
					manager.setPassword(ET_password.getText().toString());
					/*��֤��ȡ����*/ 
					if(ET_name.getText().toString().equals("")) {
						Toast.makeText(ManagerAddActivity.this, "�������벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_name.setFocusable(true);
						ET_name.requestFocus();
						return;	
					}
					manager.setName(ET_name.getText().toString());
					/*��֤��ȡ�Ա�*/ 
					if(ET_sex.getText().toString().equals("")) {
						Toast.makeText(ManagerAddActivity.this, "�Ա����벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_sex.setFocusable(true);
						ET_sex.requestFocus();
						return;	
					}
					manager.setSex(ET_sex.getText().toString());
					/*��ȡ��������*/
					Date birthDate = new Date(dp_birthDate.getYear()-1900,dp_birthDate.getMonth(),dp_birthDate.getDayOfMonth());
					manager.setBirthDate(new Timestamp(birthDate.getTime()));
					/*��֤��ȡ��ϵ�绰*/ 
					if(ET_telephone.getText().toString().equals("")) {
						Toast.makeText(ManagerAddActivity.this, "��ϵ�绰���벻��Ϊ��!", Toast.LENGTH_LONG).show();
						ET_telephone.setFocusable(true);
						ET_telephone.requestFocus();
						return;	
					}
					manager.setTelephone(ET_telephone.getText().toString());
					/*����ҵ���߼����ϴ���ͨ����Ա��Ϣ*/
					ManagerAddActivity.this.setTitle("�����ϴ���ͨ����Ա��Ϣ���Ե�...");
					String result = managerService.AddManager(manager);
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
	}
}
