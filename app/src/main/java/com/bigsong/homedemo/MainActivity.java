package com.bigsong.homedemo;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigsong.homedemo.adapter.NewsFragmentPagerAdapter;
import com.bigsong.homedemo.bean.NewsClassify;
import com.bigsong.homedemo.fragment.NewsFragment;
import com.bigsong.homedemo.view.ColumnHorizontalScrollView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ColumnHorizontalScrollView mColumnHorizontalScrollView;
    private LinearLayout ll_content;
    private LinearLayout ll_more;
    private RelativeLayout rl_column;
    private ViewPager mViewPager;
    private ImageView button_more_columns;
    private ArrayList<NewsClassify> newsClassifies;
    private int columnSelectIndex;
    private ImageView shade_left;
    private ImageView shade_right;
    private int mScreenWidth;
    private int mItemWidth;
    private ArrayList<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        mScreenWidth = size.x;
        mItemWidth = mScreenWidth / 7;
        initView();
    }

    private void initView() {
        mColumnHorizontalScrollView = (ColumnHorizontalScrollView) findViewById(R.id.mColumnHorizontalScrollView);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);
        ll_more = (LinearLayout) findViewById(R.id.ll_more);
        rl_column = (RelativeLayout) findViewById(R.id.rl_column);
        shade_left = (ImageView) findViewById(R.id.shade_left);
        shade_right = (ImageView) findViewById(R.id.shade_right);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        setChangeView();
    }

    private void setChangeView() {
        initColumnData();
        initTabColumn();
        initFragment();
    }

    private void initColumnData() {
        newsClassifies = new ArrayList<NewsClassify>();
        for (int i = 0; i < 10; i++) {
            NewsClassify newsClassify = new NewsClassify();
            newsClassify.setId(i);
            newsClassify.setTitle("标题" + i);
            newsClassifies.add(newsClassify);
        }
    }

    private void initTabColumn() {
        ll_content.removeAllViews();
        mColumnHorizontalScrollView.setParam(this, mScreenWidth, ll_content, shade_left, shade_right, ll_more, rl_column);
        for (int i = 0; i < newsClassifies.size(); i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(mItemWidth, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 10;
            params.rightMargin = 10;
            TextView localTextView = new TextView(this);
            localTextView.setTextAppearance(this, R.style.top_category_scroll_view_item_text);
            localTextView.setBackgroundResource(R.drawable.radio_button_bg);
            localTextView.setGravity(Gravity.CENTER);
            localTextView.setPadding(5, 0, 5, 0);
            localTextView.setId(i);
            localTextView.setText(newsClassifies.get(i).getTitle());
            localTextView.setTextColor(getResources().getColor(android.R.color.darker_gray));
            if (columnSelectIndex == i){
                localTextView.setSelected(true);
            }
            localTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int i = 0; i < ll_content.getChildCount(); i++) {
                        View localView = ll_content.getChildAt(i);
                        if (localView != v) {
                            localView.setSelected(false);
                        } else {
                            localView.setSelected(true);
                            mViewPager.setCurrentItem(i);
                        }
                    }
                    Toast.makeText(getApplicationContext(), newsClassifies.get(v.getId()).getTitle(), Toast.LENGTH_SHORT).show();
                }
            });
            ll_content.addView(localTextView);
        }
    }

    private void initFragment() {
        for (int i=0;i<newsClassifies.size();i++){
            Bundle data = new Bundle();
            data.putString("text",newsClassifies.get(i).getTitle());
            NewsFragment newsFragment = new NewsFragment();
            newsFragment.setArguments(data);
            fragments.add(newsFragment);
        }
        NewsFragmentPagerAdapter mAdapter = new NewsFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mViewPager.setCurrentItem(position);
                selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void selectTab(int tabPosition) {
        columnSelectIndex = tabPosition;
        View selectedView = ll_content.getChildAt(tabPosition);
        mColumnHorizontalScrollView.smoothScrollTo(selectedView.getLeft()+selectedView.getMeasuredWidth()-mScreenWidth/2,0);
        for (int i=0;i<ll_content.getChildCount();i++){
            View localView = ll_content.getChildAt(i);
            boolean isSelect;
            if (i == tabPosition){
                isSelect = true;
            }else {
                isSelect = false;
            }
            localView.setSelected(isSelect);
        }
    }

}
