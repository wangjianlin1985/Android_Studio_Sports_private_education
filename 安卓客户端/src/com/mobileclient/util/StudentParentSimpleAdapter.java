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
	/*��Ҫ�󶨵Ŀؼ���Դid*/
    private int[] mTo;
    /*map���Ϲؼ�������*/
    private String[] mFrom;
/*��Ҫ�󶨵�����*/
    private List<? extends Map<String, ?>> mData; 

    private LayoutInflater mInflater;
    Context context = null;

    private ListView mListView;
    //ͼƬ�첽���������,���ڴ滺����ļ�����
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
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.studentparent_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_spUserName = (TextView)convertView.findViewById(R.id.tv_spUserName);
	  holder.tv_parentName = (TextView)convertView.findViewById(R.id.tv_parentName);
	  holder.tv_cityObj = (TextView)convertView.findViewById(R.id.tv_cityObj);
	  holder.tv_studentSex = (TextView)convertView.findViewById(R.id.tv_studentSex);
	  holder.tv_ageRangeObj = (TextView)convertView.findViewById(R.id.tv_ageRangeObj);
	  holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
	  holder.tv_school = (TextView)convertView.findViewById(R.id.tv_school);
	  holder.tv_nowStateObj = (TextView)convertView.findViewById(R.id.tv_nowStateObj);
	  holder.iv_studentPhoto = (ImageView)convertView.findViewById(R.id.iv_studentPhoto);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_spUserName.setText("�û�����" + mData.get(position).get("spUserName").toString());
	  holder.tv_parentName.setText("�ҳ�������" + mData.get(position).get("parentName").toString());
	  holder.tv_cityObj.setText("���У�" + (new CityService()).GetCity(mData.get(position).get("cityObj").toString()).getCityName());
	  holder.tv_studentSex.setText("ѧ���Ա�" + mData.get(position).get("studentSex").toString());
	  holder.tv_ageRangeObj.setText("���䷶Χ��" + (new AgeRangeService()).GetAgeRange(Integer.parseInt(mData.get(position).get("ageRangeObj").toString())).getShowText());
	  holder.tv_age.setText("ѧ�����䣺" + mData.get(position).get("age").toString());
	  holder.tv_school.setText("ѧ��ѧУ��" + mData.get(position).get("school").toString());
	  holder.tv_nowStateObj.setText("��״̬��" + (new NowStateService()).GetNowState(Integer.parseInt(mData.get(position).get("nowStateObj").toString())).getStateName());
	  holder.iv_studentPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener studentPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_studentPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("studentPhoto"),studentPhotoLoadListener);  
	  /*�����޸ĺõ�view*/
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
