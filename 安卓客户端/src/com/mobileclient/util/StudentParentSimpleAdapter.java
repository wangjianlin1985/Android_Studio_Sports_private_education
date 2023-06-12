package com.mobileclient.util;

import java.util.List;  
import java.util.Map;

import com.mobileclient.service.CityService;
import com.mobileclient.service.AgeRangeService;
import com.mobileclient.service.NowStateService;
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

public class StudentParentSimpleAdapter extends SimpleAdapter { 
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

    public StudentParentSimpleAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to,ListView listView) { 
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
	  if (convertView == null) convertView = mInflater.inflate(R.layout.studentparent_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*绑定该view各个控件*/
	  holder.tv_spUserName = (TextView)convertView.findViewById(R.id.tv_spUserName);
	  holder.tv_parentName = (TextView)convertView.findViewById(R.id.tv_parentName);
	  holder.tv_cityObj = (TextView)convertView.findViewById(R.id.tv_cityObj);
	  holder.tv_studentSex = (TextView)convertView.findViewById(R.id.tv_studentSex);
	  holder.tv_ageRangeObj = (TextView)convertView.findViewById(R.id.tv_ageRangeObj);
	  holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
	  holder.tv_school = (TextView)convertView.findViewById(R.id.tv_school);
	  holder.tv_nowStateObj = (TextView)convertView.findViewById(R.id.tv_nowStateObj);
	  holder.iv_studentPhoto = (ImageView)convertView.findViewById(R.id.iv_studentPhoto);
	  /*设置各个控件的展示内容*/
	  holder.tv_spUserName.setText("用户名：" + mData.get(position).get("spUserName").toString());
	  holder.tv_parentName.setText("家长姓名：" + mData.get(position).get("parentName").toString());
	  holder.tv_cityObj.setText("城市：" + (new CityService()).GetCity(mData.get(position).get("cityObj").toString()).getCityName());
	  holder.tv_studentSex.setText("学生性别：" + mData.get(position).get("studentSex").toString());
	  holder.tv_ageRangeObj.setText("年龄范围：" + (new AgeRangeService()).GetAgeRange(Integer.parseInt(mData.get(position).get("ageRangeObj").toString())).getShowText());
	  holder.tv_age.setText("学生年龄：" + mData.get(position).get("age").toString());
	  holder.tv_school.setText("学生学校：" + mData.get(position).get("school").toString());
	  holder.tv_nowStateObj.setText("现状态：" + (new NowStateService()).GetNowState(Integer.parseInt(mData.get(position).get("nowStateObj").toString())).getStateName());
	  holder.iv_studentPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener studentPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_studentPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("studentPhoto"),studentPhotoLoadListener);  
	  /*返回修改好的view*/
	  return convertView; 
    } 

    static class ViewHolder{ 
    	TextView tv_spUserName;
    	TextView tv_parentName;
    	TextView tv_cityObj;
    	TextView tv_studentSex;
    	TextView tv_ageRangeObj;
    	TextView tv_age;
    	TextView tv_school;
    	TextView tv_nowStateObj;
    	ImageView iv_studentPhoto;
    }
} 
