package com.fu.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 09/02/2017.
 */
@Entity
@Table(name = "law")
public class Law {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "detail")
    @NotNull
    private String detail;

    @Column(name = "money")
    @NotNull
    private String money;

    @Column(name = "fieldId")
    @NotNull
    private int fieldId;

    @Column(name = "fieldName")
    @NotNull
    private String fieldName;

    @Column(name = "imgUrl")
    @NotNull
    private String imgUrl;

    @Column(name = "vehicleId")
    @NotNull
    private int vehicleId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
