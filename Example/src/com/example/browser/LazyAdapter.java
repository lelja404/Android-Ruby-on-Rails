package com.example.browser;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyAdapter extends BaseAdapter {
	private Activity activity;
    private String[] data;
    private String[] comments;
    private Integer[] iddata;
    private Integer[] idcomments;
    private static LayoutInflater inflater=null;
    public ImageLoader imageLoader; 
    
    public LazyAdapter(Activity a, String[] d,String[] c,Integer[] id, Integer[] p_id) {
        activity = a;
        data=d;
        comments=c;
        iddata=id;
        idcomments=p_id;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        imageLoader=new ImageLoader(activity.getApplicationContext());
    }

    public int getCount() {
        return data.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
    
    public static class ViewHolder{
        public TextView text;
        public ImageView image;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        ViewHolder holder;
        if(convertView==null){
            vi = inflater.inflate(R.layout.item, null);
            holder=new ViewHolder();
            holder.text=(TextView)vi.findViewById(R.id.text);;
            holder.image=(ImageView)vi.findViewById(R.id.image);
            vi.setTag(holder);
        }
        else
          holder=(ViewHolder)vi.getTag();
        for(int k=0;k<comments.length;k++)
        {
        	if(idcomments[k]==iddata[position])
        		holder.text.append(comments[k]+"\n"); 
        }
        holder.image.setTag(data[position]);
        imageLoader.DisplayImage(data[position], activity, holder.image);
        
        return vi;
    }
}
