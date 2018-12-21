package com.example.pl.testhello.greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * desc:
 * created by pl at 2018/12/21
 */
@Entity
public class Fruit {
    @Id(autoincrement = true)   //设置主键自增长
    private Long id;   //自增id必须为long类型的
    private String Name;  //名字
    @NotNull
    private int Count; //个数
    @Generated(hash = 2141989882)
    public Fruit(Long id, String Name, int Count) {
        this.id = id;
        this.Name = Name;
        this.Count = Count;
    }
    @Generated(hash = 2030614587)
    public Fruit() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.Name;
    }
    public void setName(String Name) {
        this.Name = Name;
    }
    public int getCount() {
        return this.Count;
    }
    public void setCount(int Count) {
        this.Count = Count;
    }
}
