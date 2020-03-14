package com.example.mydatamanagement.ListUtit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mydatamanagement.Fun.Users;
import com.example.mydatamanagement.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyUserAdapter extends ArrayAdapter {
    public MyUserAdapter(Context context, int resource, List<Users> objects) {
        super(context, resource, objects);
    }
    private  Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
         try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date date = null;
      //  date =new Date();
        Users user = (Users) getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.linkmain_item, null);
        date=StrToDate(user.getDataTime());
        
        ImageView headPortrait = (ImageView)view.findViewById(R.id.headPortrait);
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView time_year = (TextView)view.findViewById(R.id.time_year);
        TextView time_hour = (TextView)view.findViewById(R.id.time_hour);
        headPortrait.setImageResource(R.drawable.wlwduihui);
        TextView value_temp = (TextView)view.findViewById(R.id.value_temp);
        TextView value_hum = (TextView)view.findViewById(R.id.value_hum);
        TextView value_loca = (TextView)view.findViewById(R.id.value_loca);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date nowdate = new Date(System.currentTimeMillis());
        String year_date=simpleDateFormat.format(date).equals(simpleDateFormat.format(nowdate))?"今天":simpleDateFormat.format(date);

        name.setText("ID:"+user.getUserID());
        value_temp.setText(String.valueOf("温度："+user.getTemperature()));
        value_hum.setText(String.valueOf("湿度："+user.getHumidity()));
        time_year.setText(year_date);
        time_hour.setText(new SimpleDateFormat("HH:mm:ss").format(date));
        value_loca.setText(user.getLocation());
        return view;
    }

}
