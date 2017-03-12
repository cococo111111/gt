package com.fu.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by Administrator on 15/02/2017.
 */
@Entity
@Table(name = "messagesuggestion")
public class MessageSuggestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "detail")
    @NotNull
    private String detail;

    @Column(name = "vehicleId")
    private int vehicleId;

    @Column(name = "fieldId")
    private int fieldId;

    @Column(name = "fieldName")
    private String fieldName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
