package com.example.semiprojectsample.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.semiprojectsample.R;
import com.example.semiprojectsample.activity.LoginActivity;
import com.example.semiprojectsample.activity.MainActivity;
import com.example.semiprojectsample.activity.ModifyMemoActivity;
import com.example.semiprojectsample.activity.NewMemoActivity;
import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.bean.MemoBean;
import com.example.semiprojectsample.db.FileDB;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FragmentMemo extends Fragment {

    //원본 데이터
    List<MemoBean> memos = new ArrayList<>();
    ListAdapter adapter;

    public ListView mLstMemo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_memo, container,false);

        mLstMemo = view.findViewById(R.id.lstMemo);

        view.findViewById(R.id.btnNewMemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //새메모 화면으로 이동
                Intent i = new Intent(getActivity(), NewMemoActivity.class);
                startActivity(i);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //저장된 memos 리스트 획득
        MemberBean memberBean = FileDB.getLoginMember(getActivity());
        memos = FileDB.getMemoList(getActivity(), memberBean.memId);

        //Adapter 생성 및적용: Adapter가 데이터를 갖고 있다가 리스트에 반복해서 찍어내는 것
        adapter = new ListAdapter(memos, getActivity());

        //리스트 뷰에 Adapter 설정
        mLstMemo.setAdapter(adapter);
    }

    class ListAdapter extends BaseAdapter{

        List<MemoBean> memos;       //원본데이터
        Context mcontext;
        LayoutInflater inflater;

        public ListAdapter(List<MemoBean> memos, Context context){
            this.memos =memos;
            this.mcontext = context;
            this.inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);     //개별적으로 UI매핑하기 위해서. 반드시 필요.
        }

        public void setMemos(List<MemoBean> memos){
            this.memos = memos;
        }

        @Override
        public int getCount() {
            return memos.size();
        }

        @Override
        public Object getItem(int i) {
            return memos.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            //view_memo 획득
            view = inflater.inflate(R.layout.list_memo, null);

            //객체 획득
            ImageView imgv = view.findViewById(R.id.imgMemo);
            TextView txtDesc = view.findViewById(R.id.txtMemo);
            Button btnEdit = view.findViewById(R.id.btnMemoEdit);
            Button btnDelete = view.findViewById(R.id.btnMemoDelete);
            Button btnDetail = view.findViewById(R.id.btnMemoDetail);

            //원본에서 i번째 memo 획득
            final MemoBean memo = memos.get(i);

            //원본데이터를 UI(객체)에 적용
            imgv.setImageURI(Uri.fromFile(new File(memo.memoPicPath)) );
            txtDesc.setText(memo.memo);

            //수정 버튼 클릭 시 메모 수정 화면으로 이동
            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mcontext, ModifyMemoActivity.class);     //메시지를 만들어서 띄움(context, 어디에 띄울건지)
                    intent.putExtra("memoId", memo.memoId);
                    startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MemberBean memberBean = FileDB.getLoginMember(getActivity());

                    FileDB.delMemo(getActivity(), memo.memoId);

                    adapter.notifyDataSetInvalidated();

                    new AlertDialog.Builder(getActivity()).setTitle("삭제").setMessage("삭제 하시겠습니까?").setPositiveButton("삭제", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            Intent i = new Intent(getActivity(), MainActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(i);
                       }
                    })
                            .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {

                                }
                            })
                            .show();
                }




            });

            //상세보기 버튼 클릭 시 메모 수정 화면으로 이동
            btnDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intentdetail = new Intent(mcontext, FragmentModifyWrite.class);     //메시지를 만들어서 띄움(context, 어디에 띄울건지)
                    intentdetail.putExtra("memoId", memo.memoId);
                    intentdetail.putExtra("memoPicPath", memo.memoPicPath);
                    intentdetail.putExtra("memo", memo.memo);
                    intentdetail.putExtra("memoDate", memo.memoDate); // 상세표시할 원본 데이터
                    startActivity(intentdetail);
                }
            });

            return view;
        }
    }



}
