package com.example.onexero.customertimesproject.presenter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.onexero.customertimesproject.CustomApplicaton;
import com.example.onexero.customertimesproject.api.Api;
import com.example.onexero.customertimesproject.db.DataBaseHelper;
import com.example.onexero.customertimesproject.model.DescribeModel;
import com.example.onexero.customertimesproject.model.NameModel;
import com.example.onexero.customertimesproject.utils.NetworkUtils;
import com.example.onexero.customertimesproject.view.IView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Presenter implements IPresenter {

    private Api mApi;
    private IView mIView;
    private CompositeDisposable mCompositeDisposable;
    private Context mContext;
    private ArrayList<NameModel> names;
    private DataBaseHelper mDataBaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    HashMap<Integer, HashMap<String, String>> queries = new HashMap<>();

    public Presenter(Api api, IView view, CompositeDisposable compositeDisposable, CustomApplicaton applicaton) {
        this.mApi = api;
        this.mIView = view;
        this.mCompositeDisposable = compositeDisposable;
        this.mContext = applicaton.getApplicationContext();
    }

    @Override
    public void getDescribe() {
        if (NetworkUtils.isNetworkConnected(mContext)) {
            mCompositeDisposable.add(mApi.getDescribe()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(getDescribeJson()));
        } else {
            mIView.showMessage("no internet");
        }
    }

    @Override
    public void getQuery() {
        mCompositeDisposable.add(mApi.getQuery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(getQueryJson()));
    }

    @Override
    public void onDestroy() {
        mCompositeDisposable.dispose();
    }

    private DisposableObserver<DescribeModel> getDescribeJson() {
        return new DisposableObserver<DescribeModel>() {
            @Override
            public void onNext(DescribeModel describeModel) {
                if (describeModel != null) {
                    names = describeModel.getNames();
                    mIView.showMessage("ok");
                }
            }

            @Override
            public void onError(Throwable e) {
                mIView.showMessage("error");
            }

            @Override
            public void onComplete() {
                getQuery();
            }
        };
    }

    private DisposableObserver<ResponseBody> getQueryJson() {
        return new DisposableObserver<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                if (responseBody != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(responseBody.string());
                        JSONArray jsonArray = jsonObject.getJSONArray("records");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                            JSONArray jsonArray2 = jsonObject2.names();
                            HashMap<String, String> hashMap2 = new HashMap<>();
                            for (int j = 0; j < jsonArray2.length(); j++) {
                                hashMap2.put((String) jsonArray2.get(j), jsonObject2.getString((String) jsonArray2.get(j)));
                            }
                            queries.put(i, hashMap2);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    mIView.showMessage("ok 2");
                }
            }

            @Override
            public void onError(Throwable e) {
                mIView.showMessage("error 2");
            }

            @Override
            public void onComplete() {
                createAndInsertDb();
                mIView.setAdapterCursor(getDbData());
            }
        };
    }

    public void createAndInsertDb() {
        mDataBaseHelper = new DataBaseHelper(mContext, names);
        mSqLiteDatabase = mDataBaseHelper.getReadableDatabase();
        mSqLiteDatabase.getPath();

        Log.e("tester1", "tester1");
        for (HashMap<String, String> map : queries.values()) {
            ContentValues values = new ContentValues();
            for (Map.Entry entry : map.entrySet()) {
                if (!((String) entry.getKey()).equalsIgnoreCase("IsApproved__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("IsLocked__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("Description__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("AccountId__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("IsDone__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("EndDate__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("Subject__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("attributes")
                        && !((String) entry.getKey()).equalsIgnoreCase("StartDate__c")
                        && !((String) entry.getKey()).equalsIgnoreCase("Status__c") )
                    values.put((String) entry.getKey(), (String) entry.getValue());
            }
            long id = mSqLiteDatabase.insert("account", null, values);
        }
        Log.e("tester2", "tester2");
    }

    public Cursor getDbData(){
        String sql = " select ConnectionReceivedId, ConnectionSentId, CreatedById, CreatedDate, CurrencyIsoCode" +
                " Id, IsDeleted, LastActivityDate, LastModifiedById, LastModifiedDate, LastReferencedDate, LastViewedDate "+
                " Name, OwnerId, RecordTypeId, SystemModstamp from account";
        Cursor cursor = null;
        try {
            cursor = mSqLiteDatabase.rawQuery(sql, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cursor;
    }

}
