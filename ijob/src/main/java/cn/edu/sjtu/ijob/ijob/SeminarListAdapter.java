package cn.edu.sjtu.ijob.ijob;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by tian on 16/3/15.
 */
public class SeminarListAdapter extends ArrayAdapter<SeminarListItem> {

    Context mContext;
    int listLayout;
    ArrayList<SeminarListItem> mSeminarListData;
    private int mWidth;

    public SeminarListAdapter(Context context, int resource, ArrayList<SeminarListItem> object) {
        super(context, resource,object);
        this.mContext = context;
        this.listLayout = resource;
        this.mSeminarListData = object;

        DisplayMetrics metrics = Resources.getSystem().getDisplayMetrics();
        this.mWidth = metrics.widthPixels;
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
    public int getViewTypeCount() {return 2;}

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (getItemViewType(position) == 1) {  // 对应显示信息的item
            ViewHolder holder;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                convertView = inflater.inflate(listLayout, parent, false);
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

        } else {  // 对应list中的第一行,显示图片
            ViewHolderTop holderTop;
            if (convertView == null) {
                LayoutInflater inflater = ((Activity)mContext).getLayoutInflater();
                convertView = inflater.inflate(R.layout.list_item_top, parent, false);
                holderTop = new ViewHolderTop();
                holderTop.post = (ImageView) convertView.findViewById(R.id.list_item_top);
                convertView.setTag(holderTop);
            } else {
                holderTop = (ViewHolderTop) convertView.getTag();
            }
            ViewGroup.LayoutParams lp = holderTop.post.getLayoutParams();
            lp.width = mWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holderTop.post.setLayoutParams(lp);
            holderTop.post.setMaxWidth(mWidth);
            holderTop.post.setMaxWidth(mWidth*5);
            Glide.with(holderTop.post.getContext())
                    .load("https://raw.githubusercontent.com/onlytjt/MarkdownSource/master/pic/vip_post.jpg")
                    .into(holderTop.post);
        }
        return convertView;

    }

    class ViewHolder {
        public TextView companyName;
        public TextView time;
        public TextView place;
    }

    class ViewHolderTop {
        public ImageView post;
    }
}
