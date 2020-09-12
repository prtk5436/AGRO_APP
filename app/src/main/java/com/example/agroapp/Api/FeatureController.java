package com.example.agroapp.Api;


import com.example.agroapp.Login.UserDetail;

import java.util.List;

public class FeatureController {

    public static FeatureController controller;
    public UserDetail userdetails;

    List<UserDetail> userDetail;
    String permission = "";
    public String Uid="";

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    String Model;

    public static FeatureController getController() {
        return controller;
    }

    public static void setController(FeatureController controller) {
        FeatureController.controller = controller;
    }

    public static FeatureController getInstance() {
        if (controller == null) {
            controller = new FeatureController();
        }
        return controller;
    }

    public UserDetail getUserdetails() {
        return userdetails;
    }

    public void setUserdetails(UserDetail userdetails) {
        this.userdetails = userdetails;
    }

    public List<UserDetail> getUserDetail() {
        return userDetail;
    }
    // public MrDetails mrDetails;

    public void setUserDetail(List<UserDetail> userDetail) {
        this.userDetail = userDetail;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }


}
