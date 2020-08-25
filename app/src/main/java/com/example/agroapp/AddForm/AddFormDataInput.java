package com.example.agroapp.AddForm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddFormDataInput {
    @SerializedName("operation")
    @Expose
private String operation;
    @SerializedName("api_key")
    @Expose
    private String apiKey;
    @SerializedName("Giolocation")
    @Expose
    private String giolocation;
    @SerializedName("Time")
    @Expose
    private String time;
    @SerializedName("Crop_image")
    @Expose
    private String cropImage;
    @SerializedName("soil_image")
    @Expose
    private String soilImage;
    @SerializedName("Sessional_condition")
    @Expose
    private String sessionalCondition;
    @SerializedName("Irrigation_type")
    @Expose
    private String irrigationType;
    @SerializedName("Soil_type")
    @Expose
    private String soilType;
    @SerializedName("Type_of_crop")
    @Expose
    private String typeOfCrop;
    @SerializedName("Grouth_duration")
    @Expose
    private String grouthDuration;
    @SerializedName("Area")
    @Expose
    private String area;
    @SerializedName("Village")
    @Expose
    private String village;
    @SerializedName("taluka")
    @Expose
    private String taluka;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getGiolocation() {
        return giolocation;
    }

    public void setGiolocation(String giolocation) {
        this.giolocation = giolocation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCropImage() {
        return cropImage;
    }

    public void setCropImage(String cropImage) {
        this.cropImage = cropImage;
    }

    public String getSoilImage() {
        return soilImage;
    }

    public void setSoilImage(String soilImage) {
        this.soilImage = soilImage;
    }

    public String getSessionalCondition() {
        return sessionalCondition;
    }

    public void setSessionalCondition(String sessionalCondition) {
        this.sessionalCondition = sessionalCondition;
    }

    public String getIrrigationType() {
        return irrigationType;
    }

    public void setIrrigationType(String irrigationType) {
        this.irrigationType = irrigationType;
    }

    public String getSoilType() {
        return soilType;
    }

    public void setSoilType(String soilType) {
        this.soilType = soilType;
    }

    public String getTypeOfCrop() {
        return typeOfCrop;
    }

    public void setTypeOfCrop(String typeOfCrop) {
        this.typeOfCrop = typeOfCrop;
    }

    public String getGrouthDuration() {
        return grouthDuration;
    }

    public void setGrouthDuration(String grouthDuration) {
        this.grouthDuration = grouthDuration;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getTaluka() {
        return taluka;
    }

    public void setTaluka(String taluka) {
        this.taluka = taluka;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
