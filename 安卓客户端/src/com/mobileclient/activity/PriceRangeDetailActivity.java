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
	// 声明返回按钮
	private Button btnReturn;
	// 声明价格范围id控件
	private TextView TV_prId;
	// 声明起始价控件
	private TextView TV_startPrice;
	// 声明结束价控件
	private TextView TV_endPrice;
	// 声明显示信息控件
	private TextView TV_showText;
	/* 要保存的价格范围信息 */
	PriceRange priceRange = new PriceRange(); 
	/* 价格范围管理业务逻辑层 */
	private PriceRangeService priceRangeService = new PriceRangeService();
	private int prId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.pricerange_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看价格范围详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
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
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    priceRange = priceRangeService.GetPriceRange(prId); 
		this.TV_prId.setText(priceRange.getPrId() + "");
		this.TV_startPrice.setText(priceRange.getStartPrice() + "");
		this.TV_endPrice.setText(priceRange.getEndPrice() + "");
		this.TV_showText.setText(priceRange.getShowText());
	} 
}
