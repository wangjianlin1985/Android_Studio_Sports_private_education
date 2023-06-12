package com.mobileclient.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.mobileclient.util.HttpUtil;
import com.mobileclient.util.ImageService;
import com.mobileclient.domain.PriceRange;
import com.mobileclient.service.PriceRangeService;
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
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.Spinner;
import android.widget.Toast;

public class PriceRangeEditActivity extends Activity {
	// 声明确定添加按钮
	private Button btnUpdate;
	// 声明价格范围idTextView
	private TextView TV_prId;
	// 声明起始价输入框
	private EditText ET_startPrice;
	// 声明结束价输入框
	private EditText ET_endPrice;
	// 声明显示信息输入框
	private EditText ET_showText;
	protected String carmera_path;
	/*要保存的价格范围信息*/
	PriceRange priceRange = new PriceRange();
	/*价格范围管理业务逻辑层*/
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
		setContentView(R.layout.pricerange_edit); 
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("编辑价格范围信息");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		TV_prId = (TextView) findViewById(R.id.TV_prId);
		ET_startPrice = (EditText) findViewById(R.id.ET_startPrice);
		ET_endPrice = (EditText) findViewById(R.id.ET_endPrice);
		ET_showText = (EditText) findViewById(R.id.ET_showText);
		btnUpdate = (Button) findViewById(R.id.BtnUpdate);
		Bundle extras = this.getIntent().getExtras();
		prId = extras.getInt("prId");
		/*单击修改价格范围按钮*/
		btnUpdate.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				try {
					/*验证获取起始价*/ 
					if(ET_startPrice.getText().toString().equals("")) {
						Toast.makeText(PriceRangeEditActivity.this, "起始价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_startPrice.setFocusable(true);
						ET_startPrice.requestFocus();
						return;	
					}
					priceRange.setStartPrice(Integer.parseInt(ET_startPrice.getText().toString()));
					/*验证获取结束价*/ 
					if(ET_endPrice.getText().toString().equals("")) {
						Toast.makeText(PriceRangeEditActivity.this, "结束价输入不能为空!", Toast.LENGTH_LONG).show();
						ET_endPrice.setFocusable(true);
						ET_endPrice.requestFocus();
						return;	
					}
					priceRange.setEndPrice(Integer.parseInt(ET_endPrice.getText().toString()));
					/*验证获取显示信息*/ 
					if(ET_showText.getText().toString().equals("")) {
						Toast.makeText(PriceRangeEditActivity.this, "显示信息输入不能为空!", Toast.LENGTH_LONG).show();
						ET_showText.setFocusable(true);
						ET_showText.requestFocus();
						return;	
					}
					priceRange.setShowText(ET_showText.getText().toString());
					/*调用业务逻辑层上传价格范围信息*/
					PriceRangeEditActivity.this.setTitle("正在更新价格范围信息，稍等...");
					String result = priceRangeService.UpdatePriceRange(priceRange);
					Toast.makeText(getApplicationContext(), result, 1).show(); 
					Intent intent = getIntent();
					setResult(RESULT_OK,intent);
					finish();
				} catch (Exception e) {}
			}
		});
		initViewData();
	}

	/* 初始化显示编辑界面的数据 */
	private void initViewData() {
	    priceRange = priceRangeService.GetPriceRange(prId);
		this.TV_prId.setText(prId+"");
		this.ET_startPrice.setText(priceRange.getStartPrice() + "");
		this.ET_endPrice.setText(priceRange.getEndPrice() + "");
		this.ET_showText.setText(priceRange.getShowText());
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
}
