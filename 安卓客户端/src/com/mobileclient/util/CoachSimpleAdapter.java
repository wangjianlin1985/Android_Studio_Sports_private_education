package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.AgeRangeService;
import com.mobileclient.service.CityService;
import com.mobileclient.service.NowStateService;
import com.mobileclient.service.PriceRangeService;
import com.mobileclient.activity.R;
import com.mobileclient.imgCache.ImageLoadListener;
import com.mobileclient.imgCache.ListViewOnScrollListener;
import com.mobileclient.imgCache.SyncImageLoader;
import android.content.Context;
import android.view.LayoutInflater; 
import android.view.View;
import android.view.ViewGroup;  
import android.widget.ImageView; 
import android.widget.ListView;
import android.widget.SimpleAdapter; 
import android.widget.TextView; 

public class CoachSimpleAdapter extends SimpleAdapter { 
	/*需要绑定的控件资源id*/
    private int[] mTo;
    /*map集合关键字数组*/
    private String[] mFrom;
/*需要绑定的数据*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //图片异步缓存加载类,带内存缓存和文件缓存
    private SyncImageLoader syncImageLoader;

    public CoachSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
        super(context, data, resource, from, to); 
        mTo = to; 
        mFrom = from; 
        mData = data;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context= context;
        mListView = listView; 
        syncImageLoader = SyncImageLoader.getInstance();
        ListViewOnScrollListener onScrollListener = new ListViewOnScrollListener(syncImageLoader,listView,getCount());
        mListView.setOnScrollListener(onScrollListener);
    } 

  public View getView(int position, View convertView, ViewGroup parent) { 
	  ViewHolder holder = null;
	  ///*第一次装载这个view时=null,就新建一个调用inflate渲染一个view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.coach_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_coachUserName = (TextView)convertView.findViewById(R.id.tv_coachUserName);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
	  holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
	  holder.tv_cityObj = (TextView)convertView.findViewById(R.id.tv_cityObj);
	  holder.tv_nowStateObj = (TextView)convertView.findViewById(R.id.tv_nowStateObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.iv_coachPhoto = (ImageView)convertView.findViewById(R.id.iv_coachPhoto);
	  /*设置各个控件的展示内容*/
	  holder.tv_coachUserName.setText("用户名：" + mData.get(position).get("coachUserName").toString());
	  holder.tv_name.setText("姓名：" + mData.get(position).get("name").toString());
	  holder.tv_sex.setText("性别：" + mData.get(position).get("sex").toString());
	  holder.tv_age.setText("年龄：" + mData.get(position).get("age").toString());
	  holder.tv_cityObj.setText("城市：" + (new CityService()).GetCity(mData.get(position).get("cityObj").toString()).getCityName());
	  holder.tv_nowStateObj.setText("现状态：" + (new NowStateService()).GetNowState(Integer.parseInt(mData.get(position).get("nowStateObj").toString())).getStateName());
	  holder.tv_price.setText("价格(元/小时)：" + mData.get(position).get("price").toString());
	  holder.iv_coachPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener coachPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_coachPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("coachPhoto"),coachPhotoLoadListener);  
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_coachUserName;
    	TextView tv_name;
    	TextView tv_sex;
    	TextView tv_age;
    	TextView tv_cityObj;
    	TextView tv_nowStateObj;
    	TextView tv_price;
    	ImageView iv_coachPhoto;
    }
} 
