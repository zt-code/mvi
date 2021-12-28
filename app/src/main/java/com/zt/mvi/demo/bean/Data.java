/**
 * Copyright 2021 json.cn
 */
package com.zt.mvi.demo.bean;

import java.util.Date;

/**
 * Auto-generated: 2021-11-25 10:35:43
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
public class Data {

    private Date updatedTime;
    private int orderNo;
    private String name = "";
    private Date createdTime;
    private int id;
    private int status;

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Data{" +
                "updatedTime=" + updatedTime +
                ", orderNo=" + orderNo +
                ", name='" + name + '\'' +
                ", createdTime=" + createdTime +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}