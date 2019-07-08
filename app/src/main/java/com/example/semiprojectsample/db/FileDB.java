package com.example.semiprojectsample.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.example.semiprojectsample.bean.MemberBean;
import com.example.semiprojectsample.bean.MemoBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;

public class FileDB {

    private static final String FILE_DB = "FileDB";
    private static Gson mGson = new Gson();

    private static SharedPreferences getSP(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_DB, Context.MODE_PRIVATE);
        return sp;
    }

    /** 기존 리스트에 새로운 멤버추가 */
    public static void addMember(Context context, MemberBean memberBean){
        //1.기존의 멤버 리스트를 불러온다.
        List<MemberBean> memberList = getMemberList(context);
        //2.기존의 멤버 리스트에 추가한다.
        memberList.add(memberBean);
        //3.멤버 리스트를 저장한다.
        String listStr = mGson.toJson(memberList);
        //4.저장한다.
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putString("memberList", listStr);
        editor.commit();
    }

    //기존 멤버 교체 내용 수정 (메모수정할 때 이용)
    public static void setMember(Context context, MemberBean memberBean){
        //전체 멤버 리스트를 취득한다.
        List<MemberBean> memberList = getMemberList(context);
        // MemberBean findMember = getFindMember(context, memberBean.memId);
        if(memberList.size() == 0) return;

        for(int i=0; i<memberList.size();i++){
            MemberBean bean= memberList.get(i);
            if(TextUtils.equals(bean.memId, memberBean.memId)){
                //같은 멤버 id를 찾았다.
                memberList.set(i, memberBean);
                break;
            }
        }

        //새롭게 업데이트 된 리스트 저장
        String jsonStr = mGson.toJson(memberList);
        //4.멤버 저장한다.
        SharedPreferences.Editor editor = getSP(context).edit();
        editor.putString("memberList", jsonStr);
        editor.commit();

    }

    public static List<MemberBean> getMemberList(Context context) {
        String listStr = getSP(context).getString("memberList", null);
        //저장된 리스트가 없을 경우에 새로운 리스트를 리턴한다.
        if(listStr == null) {
            return new ArrayList<MemberBean>();
        }
        //Gson 으로 변환한다.
        List<MemberBean> memberList =
            mGson.fromJson(listStr, new TypeToken<List<MemberBean>>(){}.getType() );
        return memberList;
    }

    public static MemberBean getFindMember(Context context, String memId) {
        //1.멤버리스트를 가져온다
        List<MemberBean> memberList = getMemberList(context);
        //2.for 문 돌면서 해당 아이디를 찾는다.
        for(MemberBean bean : memberList) {
            if(TextUtils.equals(bean.memId, memId)) { //아이디가 같다.
                //3.찾았을 경우는 해당 MemberBean 을 리턴한다.
                return bean;
            }
        }
        //3-2.못찾았을 경우는??? null 리턴
        return null;
    }

    //로그인한 MemberBean 을 저장한다.
    public static void setLoginMember(Context context, MemberBean bean) {
        if(bean != null) {
            String str = mGson.toJson(bean);
            SharedPreferences.Editor editor = getSP(context).edit();
            editor.putString("loginMemberBean", str);
            editor.commit();
        }
    }
    //로그인한 MemberBean 을 취득한다.
    public static MemberBean getLoginMember(Context context) {
        String str = getSP(context).getString("loginMemberBean", null);
        if(str == null) return null;
        MemberBean memberBean = mGson.fromJson(str, MemberBean.class);
        return memberBean;
    }

    //메모 저장하는 거 필요. 추가 메서드
    public static void addMemo(Context context, String memId, MemoBean memoBean){
        MemberBean findMember = getFindMember(context, memId);//메모 가지고 오는 멤버 찾기
        if(findMember == null) return;

        List<MemoBean> memoList = findMember.memoList;
        if(memoList == null){
            memoList = new ArrayList<>();
        }
        //고유 메모 아이디 생성해야한다.
        memoBean.memoId = System.currentTimeMillis();
        memoList.add(memoBean);

        findMember.memoList = memoList;
        //저장
        setMember(context, findMember);
    }

    //메모 수정시 setMemo
    public static void setMemo(Context context, String memId, MemoBean memoBean){

    }

    //delMemo
    public static void delMemo(Context context, String memId, int memoId){
        //MemoBean findMemo = getFindMember(context, memoId);//메모 찾기
        //return findMemo.

    }

    //메모  찾기
    public static void findMemo(Context context, String memId, MemberBean memberBean){
    }

    //메모 리스트 취득
    public static List<MemoBean> getMemoList(Context context, String memId){
        MemberBean memberBean = getFindMember(context, memId);
        if(memberBean == null) return null;

        if(memberBean.memoList == null){
            return new ArrayList<>();
        }else{
            return memberBean.memoList;
        }
    }

}
