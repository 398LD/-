package com.kexun.entity;

import javax.persistence.*;
import java.io.Serializable;

/*用户实体 计划使用spring data jpa操作数据的 环境以及搭建好可以直接使用*/
@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;

    @Column(name = "uname", length = 100)
    private String uname;

    @Column(name = "upassword", length = 100)
    private String upassword;


    public User(int uid, String uname, String upassword) {
        this.uid = uid;
        this.uname = uname;
        this.upassword = upassword;
    }


    public User() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }
}
