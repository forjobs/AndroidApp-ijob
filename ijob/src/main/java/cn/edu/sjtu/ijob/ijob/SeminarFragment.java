package cn.edu.sjtu.ijob.ijob;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;
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

/**
 * Created by tian on 16/3/17.
 */
public class SeminarFragment extends ListFragment{

    private ArrayList<SeminarListItem> mListData;
    private SeminarListAdapter mAdapter;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListData = new ArrayList<SeminarListItem>();
        mListData.add(new SeminarListItem());  // 由于list首行显示图片,人工加入一个空的数据项
        getListData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seminar, container, false);
        return view;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        setFragmentToWebview(mListData.get(position).getInfoUrl());
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
                    e.printStackTrace();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Elements>() {
                    @Override
                    public void onCompleted() {
                        mAdapter = new SeminarListAdapter(getActivity(), R.layout.list_item, mListData);
                        setListAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
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
                            mListData.add(item);
                        }
                    }
                });
    }

    private void setFragmentToWebview(String url) {
        WebviewFragment webviewFragment = WebviewFragment.newInstance(url);
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.fragment_container, webviewFragment);
        transaction.commit();
    }

}
