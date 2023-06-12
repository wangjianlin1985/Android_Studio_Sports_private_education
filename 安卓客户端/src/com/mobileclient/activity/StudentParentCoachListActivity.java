package com.mobileclient.activity;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobileclient.app.Declare;
import com.mobileclient.domain.StudentParent;
import com.mobileclient.service.StudentParentService;
import com.mobileclient.util.ActivityUtils;import com.mobileclient.util.StudentParentSimpleAdapter;
import com.mobileclient.util.HttpUtil;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.Handler;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnCreateContextMenuListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class StudentParentCoachListActivity extends Activity {
	StudentParentSimpleAdapter adapter;
	ListView lv; 
	List<Map<String, Object>> list;
	String spUserName;
	/* ѧ���ҳ�����ҵ���߼������ */
	StudentParentService studentParentService = new StudentParentService();
	/*�����ѯ����������ѧ���ҳ�����*/
	private StudentParent queryConditionStudentParent;

	private MyProgressDialog dialog; //������	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN);
		setContentView(R.layout.studentparent_list);
		dialog = MyProgressDialog.getInstance(this);
		Declare declare = (Declare) getApplicationContext();
		String username = declare.getUserName();
		//�������ؼ�
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(StudentParentCoachListActivity.this, StudentParentQueryActivity.class);
				startActivityForResult(intent,ActivityUtils.QUERY_CODE);//�˴���requestCodeӦ�������������е��õ�requestCodeһ��
			}
		});
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("ѧ���ҳ���ѯ�б�");
		ImageView add_btn = (ImageView) this.findViewById(R.id.add_btn);
		add_btn.setOnClickListener(new android.view.View.OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(StudentParentCoachListActivity.this, StudentParentAddActivity.class);
				startActivityForResult(intent,ActivityUtils.ADD_CODE);
			}
		});
		add_btn.setVisibility(View.GONE);
		
		queryConditionStudentParent = new StudentParent();
		queryConditionStudentParent.setSpUserName("");
		queryConditionStudentParent.setParentName("");
		queryConditionStudentParent.setCityObj("");
		queryConditionStudentParent.setStudentSex("");
		queryConditionStudentParent.setAgeRangeObj(0);
		queryConditionStudentParent.setSchool("");
		queryConditionStudentParent.setNowStateObj(0);
		queryConditionStudentParent.setShzt("���ͨ��");
		  
		setViews();
	}

	//���������������secondActivity�з���ʱ���ô˺���
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==ActivityUtils.QUERY_CODE && resultCode==RESULT_OK){
        	Bundle extras = data.getExtras();
        	if(extras != null)
        		queryConditionStudentParent = (StudentParent)extras.getSerializable("queryConditionStudentParent");
        	setViews();
        }
        if(requestCode==ActivityUtils.EDIT_CODE && resultCode==RESULT_OK){
        	setViews();
        }
        if(requestCode == ActivityUtils.ADD_CODE && resultCode == RESULT_OK) {
        	queryConditionStudentParent = null;
        	setViews();
        }
    }

	private void setViews() {
		lv = (ListView) findViewById(R.id.h_list_view);
		dialog.show();
		final Handler handler = new Handler();
		new Thread(){
			@Override
			public void run() {
				//�����߳��н����������ݲ���
				list = getDatas();
				//������ʧ��handler��֪ͨ���߳��������
				handler.post(new Runnable() {
					@Override
					public void run() {
						dialog.cancel();
						adapter = new StudentParentSimpleAdapter(StudentParentCoachListActivity.this, list,
	        					R.layout.studentparent_list_item,
	        					new String[] { "spUserName","parentName","cityObj","studentSex","ageRangeObj","age","school","nowStateObj","studentPhoto" },
	        					new int[] { R.id.tv_spUserName,R.id.tv_parentName,R.id.tv_cityObj,R.id.tv_studentSex,R.id.tv_ageRangeObj,R.id.tv_age,R.id.tv_school,R.id.tv_nowStateObj,R.id.iv_studentPhoto,},lv);
	        			lv.setAdapter(adapter);
					}
				});
			}
		}.start(); 

		// ��ӳ������
		lv.setOnCreateContextMenuListener(studentParentListItemListener);
		lv.setOnItemClickListener(new OnItemClickListener(){
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) {
            	String spUserName = list.get(arg2).get("spUserName").toString();
            	Intent intent = new Intent();
            	intent.setClass(StudentParentCoachListActivity.this, StudentParentDetailActivity.class);
            	Bundle bundle = new Bundle();
            	bundle.putString("spUserName", spUserName);
            	intent.putExtras(bundle);
            	startActivity(intent);
            }
        });
	}
	private OnCreateContextMenuListener studentParentListItemListener = new OnCreateContextMenuListener() {
		public void onCreateContextMenu(ContextMenu menu, View v,ContextMenuInfo menuInfo) {
			//menu.add(0, 0, 0, "�༭ѧ���ҳ���Ϣ"); 
			//menu.add(0, 1, 0, "ɾ��ѧ���ҳ���Ϣ");
		}
	};

	// �����˵���Ӧ����
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		if (item.getItemId() == 0) {  //�༭ѧ���ҳ���Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�û���
			spUserName = list.get(position).get("spUserName").toString();
			Intent intent = new Intent();
			intent.setClass(StudentParentCoachListActivity.this, StudentParentEditActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("spUserName", spUserName);
			intent.putExtras(bundle);
			startActivityForResult(intent,ActivityUtils.EDIT_CODE);
		} else if (item.getItemId() == 1) {// ɾ��ѧ���ҳ���Ϣ
			ContextMenuInfo info = item.getMenuInfo();
			AdapterContextMenuInfo contextMenuInfo = (AdapterContextMenuInfo) info;
			// ��ȡѡ����λ��
			int position = contextMenuInfo.position;
			// ��ȡ�û���
			spUserName = list.get(position).get("spUserName").toString();
			dialog();
		}
		return super.onContextItemSelected(item);
	}

	// ɾ��
	protected void dialog() {
		Builder builder = new Builder(StudentParentCoachListActivity.this);
		builder.setMessage("ȷ��ɾ����");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				String result = studentParentService.DeleteStudentParent(spUserName);
				Toast.makeText(getApplicationContext(), result, 1).show();
				setViews();
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	private List<Map<String, Object>> getDatas() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			/* ��ѯѧ���ҳ���Ϣ */
			List<StudentParent> studentParentList = studentParentService.QueryStudentParent(queryConditionStudentParent);
			for (int i = 0; i < studentParentList.size(); i++) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("spUserName", studentParentList.get(i).getSpUserName());
				map.put("parentName", studentParentList.get(i).getParentName());
				map.put("cityObj", studentParentList.get(i).getCityObj());
				map.put("studentSex", studentParentList.get(i).getStudentSex());
				map.put("ageRangeObj", studentParentList.get(i).getAgeRangeObj());
				map.put("age", studentParentList.get(i).getAge());
				map.put("school", studentParentList.get(i).getSchool());
				map.put("nowStateObj", studentParentList.get(i).getNowStateObj());
				/*byte[] studentPhoto_data = ImageService.getImage(HttpUtil.BASE_URL+ studentParentList.get(i).getStudentPhoto());// ��ȡͼƬ����
				BitmapFactory.Options studentPhoto_opts = new BitmapFactory.Options();  
				studentPhoto_opts.inJustDecodeBounds = true;  
				BitmapFactory.decodeByteArray(studentPhoto_data, 0, studentPhoto_data.length, studentPhoto_opts); 
				studentPhoto_opts.inSampleSize = photoListActivity.computeSampleSize(studentPhoto_opts, -1, 100*100); 
				studentPhoto_opts.inJustDecodeBounds = false; 
				try {
					Bitmap studentPhoto = BitmapFactory.decodeByteArray(studentPhoto_data, 0, studentPhoto_data.length, studentPhoto_opts);
					map.put("studentPhoto", studentPhoto);
				} catch (OutOfMemoryError err) { }*/
				map.put("studentPhoto", HttpUtil.BASE_URL+ studentParentList.get(i).getStudentPhoto());
				list.add(map);
			}
		} catch (Exception e) { 
			Toast.makeText(getApplicationContext(), "", 1).show();
		}
		return list;
	}

}
