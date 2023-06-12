package com.mobileclient.activity;

import java.util.Date;
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
public class NowStateDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// ����״̬id�ؼ�
	private TextView TV_stateId;
	// ����״̬���ƿؼ�
	private TextView TV_stateName;
	/* Ҫ�������״̬��Ϣ */
	NowState nowState = new NowState(); 
	/* ��״̬����ҵ���߼��� */
	private NowStateService nowStateService = new NowStateService();
	private int stateId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.nowstate_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴��״̬����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_stateId = (TextView) findViewById(R.id.TV_stateId);
		TV_stateName = (TextView) findViewById(R.id.TV_stateName);
		Bundle extras = this.getIntent().getExtras();
		stateId = extras.getInt("stateId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				NowStateDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    nowState = nowStateService.GetNowState(stateId); 
		this.TV_stateId.setText(nowState.getStateId() + "");
		this.TV_stateName.setText(nowState.getStateName());
	} 
}
