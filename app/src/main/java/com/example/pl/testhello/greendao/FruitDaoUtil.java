package com.example.pl.testhello.greendao;

import android.content.Context;
import android.util.Log;


import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * desc:
 * created by pl at 2018/12/21
 */
public class FruitDaoUtil {
    private DaoManager mManager;
    private String TAG="flag";
    //操作Fruit的构造函数
    public FruitDaoUtil(Context context) {
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }
    /**
     * 完成fruit记录的插入，如果表未创建，先创建fruit表
     *
     * @param fruit
     * @return  返回long值 -1为失败
     */
    public boolean insertFruit(Fruit fruit) {
        boolean flag = false;
        flag = mManager.getDaoSession().getFruitDao().insertOrReplace(fruit) == -1 ? false : true;
        Log.i(TAG, "-------插入一条的结果为"+flag);
        return flag;
    }

    /**
     * 插入多条数据，在子线程操作
     * @return
     */
    public boolean insertListFruit(final List<Fruit> mList) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Fruit fruit : mList) {
                        mManager.getDaoSession().insertOrReplace(fruit);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }

    /**
     *   刷新指定数据
     */
    public boolean reFreshFruit(Fruit fruit){
        boolean flag = false;
        mManager.getDaoSession().refresh(fruit);
        return flag;
    }
    /**
     * 修改一条数据
     * @return
     */
    public boolean updateFruit(Fruit fruit){
        boolean flag = false;
        try {
            mManager.getDaoSession().update(fruit);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除单条记录
     * @return
     */
    public boolean deleteFruit(Fruit fruit){
        boolean flag = false;
        try {
            mManager.getDaoSession().delete(fruit);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 删除所有记录
     * @return
     */
    public boolean deleteAll(){
        boolean flag = false;
        try {
            mManager.getDaoSession().deleteAll(Fruit.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return flag;
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<Fruit> queryAllFruit(){
        return mManager.getDaoSession().loadAll(Fruit.class);
    }

    /**
     * 根据主键id查询记录
     * @param key
     * @return
     */
    public Fruit queryFruitById(long key){
        return mManager.getDaoSession().load(Fruit.class, key);
    }

    /**
     * 使用 sql语句进行查询操作
     *  参数一sql语句  参数二查询条件限定
     */
    public List<Fruit> queryFruitBySql(String sql, String[] conditions){
        return mManager.getDaoSession().queryRaw(Fruit.class, sql, conditions);
    }

    /**
     * 使用queryBuilder进行查询
     * @return
     */
    public List<Fruit> queryFruitByQueryBuilder(long id){
        QueryBuilder<Fruit> queryBuilder = mManager.getDaoSession().queryBuilder(Fruit.class);
        return queryBuilder.where(FruitDao.Properties.Id.eq(id)).list();
    }
}
