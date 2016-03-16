package cn.edu.sjtu.ijob.ijob;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by tian on 16/3/15.
 */
public class SeminarListAdapter extends ArrayAdapter<SeminarListItem> {

    Context mContext;
    int mResId;
    ArrayList<SeminarListItem> mSeminarListData;

    public SeminarListAdapter(Context context, int resource, ArrayList<SeminarListItem> object) {
        super(context, resource, object);
        this.mContext = context;
        this.mResId = resource;
        this.mSeminarListData = object;
    }

    public void setSeminarListData(ArrayList<SeminarListItem> data) {
        this.mSeminarListData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mSeminarListData == null) {
            return 0;
        } else {
            return mSeminarListData.size();
        }
    }

    @Override
    public SeminarListItem getItem(int position) {
        if (mSeminarListData == null) {
            return null;
        } else {
            return mSeminarListData.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
            convertView = inflater.inflate(mResId, parent, false);
            holder = new ViewHolder();
            holder.companyName = (TextView) convertView.findViewById(R.id.textview_company_name);
            holder.time = (TextView) convertView.findViewById(R.id.textview_time);
            holder.place = (TextView) convertView.findViewById(R.id.textview_place);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SeminarListItem item = mSeminarListData.get(position);
        holder.companyName.setText(item.getCompanyName());
        holder.time.setText(item.getTime());
        holder.place.setText(item.getPlace());

        return convertView;

    }

    class ViewHolder {
        public TextView companyName;
        public TextView time;
        public TextView place;
    }
}
