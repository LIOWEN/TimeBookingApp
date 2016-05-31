package cap.dtx;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by LIOWEN on 04/04/2016.
 */
public class ListAdapter extends BaseAdapter {
    private final ArrayList mData;

    public ListAdapter(Map<String, String> map) {
        mData = new ArrayList();
        mData.addAll(map.entrySet());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Map.Entry<String, String> getItem(int position) {
        return (Map.Entry) mData.get(position);
    }

    @Override
    public int getViewTypeCount() {

        if (getCount() != 0)
            return getCount();

        return 1;
    }

    @Override
    public long getItemId(int position) {
        // TODO implement you own logic with ID
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View result;

        if (convertView == null) {
            result = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter, parent, false);
        } else {
            result = convertView;
        }

        Map.Entry<String, String> item = getItem(position);

        // TODO replace findViewById by ViewHolder
        ((TextView) result.findViewById(android.R.id.text1)).setText(item.getKey());
        ((TextView) result.findViewById(android.R.id.text1)).setTextColor(Color.parseColor("#070829"));
        ((TextView) result.findViewById(android.R.id.text2)).setText(item.getValue());
        ((TextView) result.findViewById(android.R.id.text2)).setTextColor(Color.parseColor("#FF0D0F4B"));

        for (int i = 0; i < Approvals.approvedItems.size(); i++) {
            if(item.getValue().equals("approved")){
                result.setBackgroundColor(Color.parseColor("#7ABA71"));
            }
            if(item.getValue().equals("pending")){
                result.setBackgroundColor(Color.parseColor("#5f65a3"));
            }
            if(item.getValue().equals("rejected")){
                result.setBackgroundColor(Color.parseColor("#960018"));
            }
        }
        return result;
    }

    /**
    public String getAr(){
        String s = "";
        for(int i = 0;i<MainActivity.list.size();i++){
            s += MainActivity.list.get(i);
        }
        return s;
    }*/
}


