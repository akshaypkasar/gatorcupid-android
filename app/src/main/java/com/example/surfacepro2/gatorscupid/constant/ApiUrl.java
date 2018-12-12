package com.example.surfacepro2.gatorscupid.constant;

public class ApiUrl {

    public static final String API = "http://10.0.0.110:8080/gatorscupid";
    public static final String SIGNUP = "/user/signup";
    public static final String SIGNIN = "/user/signin";
    public static final String SIGNOUT = "/user/signout";
    public static final String UPDATE_USER_PROFILE = "/user/<id>";
    public static final String GET_USER_PROFILE = "/user/<id>";
    public static final String GET_BROWSE_PROFILE = "/user/<id>/browseList?pageNo=<id1>&pageSize=<id2>";
    public static final String LIKE_PROFILE = "/user/<id>/browseList/<id1>/like";
    public static final String UNLIKE_PROFILE = "/user/<id>/browseList/<id1>/unlike";

}