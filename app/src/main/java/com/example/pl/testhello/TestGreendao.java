package com.example.pl.testhello;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


import com.example.pl.testhello.base.BaseActivity;
import com.example.pl.testhello.greendao.Fruit;
import com.example.pl.testhello.greendao.FruitDaoUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TestGreendao extends BaseActivity {
    FruitDaoUtil mFruitDaoUtil;
    @BindView(R.id.insertsingle)
    Button btnSingle;

    @Override
    protected void initData(Bundle savedInstanceState) {
        super.initData(savedInstanceState);
        mFruitDaoUtil = new FruitDaoUtil(this);
        btnSingle.setText("shdfjkshfkjsd");
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_test_greendao;
    }

    @OnClick({R.id.insertsingle, R.id.multinsert, R.id.refresh,
            R.id.update, R.id.deletesingle, R.id.deleteMult,
            R.id.checksingle, R.id.querybuilder})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.insertsingle:  //插入单个的
                mFruitDaoUtil.insertFruit(new Fruit(1L, "苹果1", 1));
                break;
            case R.id.multinsert:  //插入多个的
                List<Fruit> list = new ArrayList<Fruit>();
                for (int i = 0; i < 10; i++) {
                    list.add(new Fruit(Long.parseLong(i + ""), "桃子" + i, i));
                }
                mFruitDaoUtil.insertListFruit(list);
                break;
            case R.id.refresh:  //刷新单个的
                mFruitDaoUtil.reFreshFruit(new Fruit(1L, "苹果1", 10));
                break;
            case R.id.update:  //更新单个的
                mFruitDaoUtil.reFreshFruit(new Fruit(1L, "苹果1", 20));
                break;
            case R.id.deletesingle:  //删除单个的
                mFruitDaoUtil.deleteFruit(new Fruit(4L, "苹果1", 20));
                break;
            case R.id.deleteMult:  //删除全部
                mFruitDaoUtil.deleteAll();
                break;
            case R.id.checksingle:  //按主键查询一个
                Fruit f = mFruitDaoUtil.queryFruitById(1L);
                Log.d("flag", "---------按条件查询的结果为" + f.getName() + "-----" + f.getId());
                break;
            case R.id.querybuilder:  //querybuilder查询
                List<Fruit> List = mFruitDaoUtil.queryFruitByQueryBuilder(1L);
                for (int i = 0; i < List.size(); i++) {
                    Log.d("flag", "---------按条件查询的结果为" + List.get(i).getName() + "-----" + List.get(i).getId());
                }
                break;

        }
    }


}
