package com.fu.database.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Created by manlm on 9/17/2016.
 */
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    @NotNull
    private String code;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "imgUrl")
    @NotNull
    private String imgUrl;

    @Column(name = "imgPromotionUrl")
    private String imgPromotionUrl;

    @Column(name = "price")
    @NotNull
    private long price;

    @Column(name = "details")
    @NotNull
    private String details;

    @Column(name = "areaId")
    private int areaId;

    @Column(name = "areaName")
    private String areaName;

    @Column(name = "floorName")
    private String floorName;

    @Column(name = "areaWeight")
    private int areaWeight;

    @Column(name = "status")
    private boolean status;

    @Column(name = "lastUpdate")
    private long lastUpdate;

    public long getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Product() {
        // Default Constructor
    }

    /**
     * Constructor
     *
     * @param code
     * @param name
     * @param imgUrl
     * @param imgPromotionUrl
     * @param price
     * @param details
     * @param areaId
     * @param areaName
     * @param floorName
     * @param areaWeight
     */
    public Product(String code, String name, String imgUrl, String imgPromotionUrl, long price, String details, int areaId, String areaName, String floorName, int areaWeight) {
        this.code = code;
        this.name = name;
        this.imgUrl = imgUrl;
        this.imgPromotionUrl = imgPromotionUrl;
        this.price = price;
        this.details = details;
        this.areaId = areaId;
        this.areaName = areaName;
        this.floorName = floorName;
        this.areaWeight = areaWeight;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgPromotionUrl() {
        return imgPromotionUrl;
    }

    public void setImgPromotionUrl(String imgPromotionUrl) {
        this.imgPromotionUrl = imgPromotionUrl;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaLocation() {
        return floorName;
    }

    public void setAreaLocation(String floorName) {
        this.floorName = floorName;
    }

    public int getAreaWeight() {
        return areaWeight;
    }

    public void setAreaWeight(int areaWeight) {
        this.areaWeight = areaWeight;
    }
}