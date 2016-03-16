package cn.edu.sjtu.ijob.ijob;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private ListView mSeminarList;
    private SeminarListAdapter mSeminarListAdapter;
    private ArrayList<SeminarListItem> mSeminarData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initSlideMenu();
        initListView();
    }


    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setTitle("爱实习  爱工作");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white_36dp);
    }

    private void initSlideMenu() {
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("点击头像登陆").withIcon(R.drawable.face)
                )
                .build();

        PrimaryDrawerItem priItemHome = new PrimaryDrawerItem()
                .withName("首页").withIcon(R.mipmap.home);
        SecondaryDrawerItem subItemSeminar = new SecondaryDrawerItem()
                .withName("宣讲信息").withIcon(R.mipmap.seminar);
        SecondaryDrawerItem subItemIntern = new SecondaryDrawerItem()
                .withName("实习信息").withIcon(R.mipmap.intern);
        SecondaryDrawerItem subItemJob = new SecondaryDrawerItem()
                .withName("工作信息").withIcon(R.mipmap.job);



        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        priItemHome, new DividerDrawerItem(),
                        subItemSeminar, subItemIntern, subItemJob
                ).withAccountHeader(headerResult)
                .build();


    }

    private void initListView() {
        mSeminarList = (ListView) findViewById(R.id.listview_seminar);
        mSeminarData = new ArrayList<SeminarListItem>();
        getListData();
    }

    private void getListData() {
        Observable.create(new Observable.OnSubscribe<Elements>() {

            public void call(Subscriber<? super Elements> subscriber) {
                try {
                    String url = "http://xjh.haitou.cc/sh/uni-132";
                    Connection conn = Jsoup.connect(url);
                    conn.header("User-Agent", "Mozilla/5.0 (X11; Linux x86_64; rv:32.0) Gecko/20100101 Firefox/32.0");
                    Document doc = conn.get();
                    Elements elements = doc.select("tbody tr");
                    subscriber.onNext(elements);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    Log.d("Exception", e.toString());
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Elements>() {
            @Override
            public void onCompleted() {
                if (mSeminarListAdapter == null) {
                    mSeminarListAdapter = new SeminarListAdapter(MainActivity.this, R.layout.list_item, mSeminarData);
                }
                mSeminarList.setAdapter(mSeminarListAdapter);
                mSeminarListAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Elements elements) {
                for (Element element : elements) {
                    String company = element.getElementsByClass("company").text();
                    String link = element.select("a").attr("abs:href").toString();
                    String time = element.getElementsByClass("hold-ymd").text();
                    String address = element.getElementsByClass("text-ellipsis").text();

                    SeminarListItem item = new SeminarListItem();
                    item.setCompanyName(company);
                    item.setTime("举办时间: " + time);
                    item.setPlace("举办地点: " + address);
                    item.setInfoUrl(link);
                    mSeminarData.add(item);
                }
            }
        });
    }
}