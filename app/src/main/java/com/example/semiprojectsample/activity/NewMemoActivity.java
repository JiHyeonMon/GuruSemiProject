package com.example.semiprojectsample.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.bean.MemoBean;
import com.example.semiprojectsample.db.FileDB;
import com.example.semiprojectsample.fragment.FragmentCamera;
import com.example.semiprojectsample.fragment.FragmentMember;
import com.example.semiprojectsample.fragment.FragmentMemo;
import com.example.semiprojectsample.fragment.FragmentMemoWrite;
import com.google.android.material.tabs.TabLayout;

public class NewMemoActivity extends AppCompatActivity {


    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private  ViewPagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_memo);
        mTabLayout = findViewById(R.id.tabLayout);
        mViewPager = findViewById(R.id.viewPager);

        mTabLayout.addTab(mTabLayout.newTab().setText("글쓰기"));
        mTabLayout.addTab(mTabLayout.newTab().setText("사진찍기"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        findViewById(R.id.btnCancel).setOnClickListener(mBtnClick);
        findViewById(R.id.btnSave).setOnClickListener(mBtnClick);

        //ViewPager 생성
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(),
                mTabLayout.getTabCount());

        //tab 이랑 viewpager 랑 연결
        mViewPager.setAdapter(mViewPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }
            @Override
            public void onTabReselected(TabLayout.Tab tab) { }
        });
    }//end onCreate()

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private int tabCount;

        public ViewPagerAdapter(FragmentManager fm, int count) {
            super(fm);
            this.tabCount = count;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new FragmentMemoWrite();
                case 1:
                    return new FragmentCamera();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tabCount;
        }
    }



    private View.OnClickListener mBtnClick = new View.OnClickListener() {


        @Override
        public void onClick(View view) {
            //어떤 버튼이 클릭됐는지 구분
            switch (view.getId()){
                case R.id.btnCancel:
                    //처리
                    finish();
                    break;
                case R.id.btnSave:
                    //처리
                    saveProc();
                    break;
            }
        }
    };

    //저장버튼 저장 처리
    private void saveProc(){
        MemberBean memberBean = FileDB.getFindMember(this, memId);

        //1. 첫번째 fragment의 EditText값을 받아온다.
        FragmentMemoWrite f0 = (FragmentMemoWrite)mViewPagerAdapter.instantiateItem(mViewPager, 0);
        //2.두번째 fragment mphotopath가져온다.
        FragmentCamera f1 = (FragmentCamera)mViewPagerAdapter.instantiateItem(mViewPager, 1);

        EditText edtWriteMemo = f0.getView().findViewById(R.id.edtWriteMemo);
        String memoStr = edtWriteMemo.getText().toString();

        ImageView imgCamera = f1.getView().findViewById(R.id.imgCamera);

        Log.e("aa", "photopath:  "+f1.mPhotoPath);

        String photoPath = f1.mPhotoPath;

       // FileDB.addMemo(this, memId,  memoBean);
        //필요한 인자 Context context, String memId, MemoBean memoBean

        Log.e("SEMI", "memoStr: "+ memoStr + ".photoPath: " + photoPath);
        Toast.makeText(this, "memoStr: " + memoStr + ".photoPath: " + photoPath, Toast.LENGTH_LONG).show();




    }

}
