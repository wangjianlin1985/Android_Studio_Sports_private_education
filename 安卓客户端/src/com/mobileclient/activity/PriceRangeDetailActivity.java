package com.mobileclient.activity;

import java.util.Date;
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
public class PriceRangeDetailActivity extends Activity {
	// �������ذ�ť
	private Button btnReturn;
	// �����۸�Χid�ؼ�
	private TextView TV_prId;
	// ������ʼ�ۿؼ�
	private TextView TV_startPrice;
	// ���������ۿؼ�
	private TextView TV_endPrice;
	// ������ʾ��Ϣ�ؼ�
	private TextView TV_showText;
	/* Ҫ����ļ۸�Χ��Ϣ */
	PriceRange priceRange = new PriceRange(); 
	/* �۸�Χ����ҵ���߼��� */
	private PriceRangeService priceRangeService = new PriceRangeService();
	private int prId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//ȥ��title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//ȥ��Activity�����״̬��
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// ���õ�ǰActivity���沼��
		setContentView(R.layout.pricerange_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("�鿴�۸�Χ����");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// ͨ��findViewById����ʵ�������
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_prId = (TextView) findViewById(R.id.TV_prId);
		TV_startPrice = (TextView) findViewById(R.id.TV_startPrice);
		TV_endPrice = (TextView) findViewById(R.id.TV_endPrice);
		TV_showText = (TextView) findViewById(R.id.TV_showText);
		Bundle extras = this.getIntent().getExtras();
		prId = extras.getInt("prId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				PriceRangeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* ��ʼ����ʾ������������ */
	private void initViewData() {
	    priceRange = priceRangeService.GetPriceRange(prId); 
		this.TV_prId.setText(priceRange.getPrId() + "");
		this.TV_startPrice.setText(priceRange.getStartPrice() + "");
		this.TV_endPrice.setText(priceRange.getEndPrice() + "");
		this.TV_showText.setText(priceRange.getShowText());
	} 
}
