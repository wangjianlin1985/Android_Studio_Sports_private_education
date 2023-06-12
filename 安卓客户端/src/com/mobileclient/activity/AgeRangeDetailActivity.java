package com.mobileclient.activity;

import java.util.Date;
import com.mobileclient.domain.AgeRange;
import com.mobileclient.service.AgeRangeService;
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
public class AgeRangeDetailActivity extends Activity {
	// 声明返回按钮
	private Button btnReturn;
	// 声明年龄范围id控件
	private TextView TV_arId;
	// 声明开始年龄控件
	private TextView TV_startAge;
	// 声明结束年龄控件
	private TextView TV_endAge;
	// 声明显示信息控件
	private TextView TV_showText;
	/* 要保存的年龄范围信息 */
	AgeRange ageRange = new AgeRange(); 
	/* 年龄范围管理业务逻辑层 */
	private AgeRangeService ageRangeService = new AgeRangeService();
	private int arId;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//去除title 
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//去掉Activity上面的状态栏
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN , WindowManager.LayoutParams. FLAG_FULLSCREEN); 
		// 设置当前Activity界面布局
		setContentView(R.layout.agerange_detail);
		ImageView search = (ImageView) this.findViewById(R.id.search);
		search.setVisibility(View.GONE);
		TextView title = (TextView) this.findViewById(R.id.title);
		title.setText("查看年龄范围详情");
		ImageView back = (ImageView) this.findViewById(R.id.back_btn);
		back.setOnClickListener(new OnClickListener(){ 
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		// 通过findViewById方法实例化组件
		btnReturn = (Button) findViewById(R.id.btnReturn);
		TV_arId = (TextView) findViewById(R.id.TV_arId);
		TV_startAge = (TextView) findViewById(R.id.TV_startAge);
		TV_endAge = (TextView) findViewById(R.id.TV_endAge);
		TV_showText = (TextView) findViewById(R.id.TV_showText);
		Bundle extras = this.getIntent().getExtras();
		arId = extras.getInt("arId");
		btnReturn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				AgeRangeDetailActivity.this.finish();
			}
		}); 
		initViewData();
	}
	/* 初始化显示详情界面的数据 */
	private void initViewData() {
	    ageRange = ageRangeService.GetAgeRange(arId); 
		this.TV_arId.setText(ageRange.getArId() + "");
		this.TV_startAge.setText(ageRange.getStartAge() + "");
		this.TV_endAge.setText(ageRange.getEndAge() + "");
		this.TV_showText.setText(ageRange.getShowText());
	} 
}
