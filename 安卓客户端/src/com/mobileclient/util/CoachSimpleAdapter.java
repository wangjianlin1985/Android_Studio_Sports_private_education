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
	  ///*��һ��װ�����viewʱ=null,���½�һ������inflate��Ⱦһ��view*/
	  if (convertView == null) convertView = mInflater.inflate(R.layout.coach_list_item, null);
	  convertView.setTag("listViewTAG" + position);
	  holder = new ViewHolder(); 
	  /*�󶨸�view�����ؼ�*/
	  holder.tv_coachUserName = (TextView)convertView.findViewById(R.id.tv_coachUserName);
	  holder.tv_name = (TextView)convertView.findViewById(R.id.tv_name);
	  holder.tv_sex = (TextView)convertView.findViewById(R.id.tv_sex);
	  holder.tv_age = (TextView)convertView.findViewById(R.id.tv_age);
	  holder.tv_cityObj = (TextView)convertView.findViewById(R.id.tv_cityObj);
	  holder.tv_nowStateObj = (TextView)convertView.findViewById(R.id.tv_nowStateObj);
	  holder.tv_price = (TextView)convertView.findViewById(R.id.tv_price);
	  holder.iv_coachPhoto = (ImageView)convertView.findViewById(R.id.iv_coachPhoto);
	  /*���ø����ؼ���չʾ����*/
	  holder.tv_coachUserName.setText("�û�����" + mData.get(position).get("coachUserName").toString());
	  holder.tv_name.setText("������" + mData.get(position).get("name").toString());
	  holder.tv_sex.setText("�Ա�" + mData.get(position).get("sex").toString());
	  holder.tv_age.setText("���䣺" + mData.get(position).get("age").toString());
	  holder.tv_cityObj.setText("���У�" + (new CityService()).GetCity(mData.get(position).get("cityObj").toString()).getCityName());
	  holder.tv_nowStateObj.setText("��״̬��" + (new NowStateService()).GetNowState(Integer.parseInt(mData.get(position).get("nowStateObj").toString())).getStateName());
	  holder.tv_price.setText("�۸�(Ԫ/Сʱ)��" + mData.get(position).get("price").toString());
	  holder.iv_coachPhoto.setImageResource(R.drawable.default_photo);
	  ImageLoadListener coachPhotoLoadListener = new ImageLoadListener(mListView,R.id.iv_coachPhoto);
	  syncImageLoader.loadImage(position,(String)mData.get(position).get("coachPhoto"),coachPhotoLoadListener);  
	  /*�����޸ĺõ�view*/
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
