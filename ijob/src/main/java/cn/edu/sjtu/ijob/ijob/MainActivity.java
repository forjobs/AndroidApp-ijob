package cn.edu.sjtu.ijob.ijob;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;


public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ActionBar mActionbar;
    private SeminarFragment mSeminarFragment;
    private InternFragment mInternFragment;
    private JobFragment mJobFragment;
    private FragmentTransaction mFragmentTransaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initToolbar();
        initSlideMenu();
        initFragments();
        setFragmentToSeminar();
    }

    @Override
    public void onBackPressed() {
        Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment instanceof WebviewFragment) {
            getFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
        }
    }

    private void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mActionbar = getSupportActionBar();
        mActionbar.setTitle("爱实习  爱工作");
        mToolbar.setNavigationIcon(R.mipmap.ic_menu_white_36dp);
    }

    private void initSlideMenu() {
        // 为SlideMenu创建Header
        AccountHeader headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(
                        new ProfileDrawerItem().withName("点击头像登陆").withIcon(R.drawable.face)
                )
                .build();
        // 为SlideMenu创建所有的item
        PrimaryDrawerItem priItemHome = new PrimaryDrawerItem()
                .withName("首页").withIcon(R.mipmap.home);
        SecondaryDrawerItem subItemSeminar = new SecondaryDrawerItem()
                .withName("宣讲信息").withIcon(R.mipmap.seminar);
        SecondaryDrawerItem subItemIntern = new SecondaryDrawerItem()
                .withName("实习信息").withIcon(R.mipmap.intern);
        SecondaryDrawerItem subItemJob = new SecondaryDrawerItem()
                .withName("工作信息").withIcon(R.mipmap.job);
        // 创建SlideMenu
        Drawer result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(mToolbar)
                .addDrawerItems(
                        priItemHome, new DividerDrawerItem(),
                        subItemSeminar, subItemIntern, subItemJob
                ).withAccountHeader(headerResult)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int i, IDrawerItem iDrawerItem) {
                        switch(i) {
                            case 1 :
                                setFragmentToSeminar();
                                break;
                            case 3:
                                setFragmentToSeminar();
                                break;
                            case 4:
                                setFragmentToIntern();
                                break;
                            case 5:
                                setFragmentToJob();
                                break;
                        }
                        return false;
                    }
                })
                .build();
    }

    private void initFragments() {
        mSeminarFragment = new SeminarFragment();
        mInternFragment = new InternFragment();
        mJobFragment = new JobFragment();
    }


    private void setFragmentToSeminar() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, mSeminarFragment).commit();
    }

    private void setFragmentToIntern() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, mInternFragment).commit();
    }

    private void setFragmentToJob() {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, mJobFragment).commit();
    }
}