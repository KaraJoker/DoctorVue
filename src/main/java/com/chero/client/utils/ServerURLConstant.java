package com.chero.client.utils;

public interface ServerURLConstant {

    String DEBUG_USER_SERVER = "http://47.98.154.107:8003";
    String TEST_USER_SERVER = "http://47.98.154.107:8003";
    String PRODUCT_USER_SERVER = "http://47.98.154.107:8003";

    String USER_SERVER = TEST_USER_SERVER;

    String RESTFUL_API_SMSSENDEREGISTER = USER_SERVER + "/code/sms";

    String RESTFUL_API_LOGIN = USER_SERVER + "/authentication/form";

    String RESTFUL_API_REGISTRY = USER_SERVER + "/users";

    String RESTFUL_API_CHANGEPWD = USER_SERVER + "/users/me/resetpassword";

    String RESTFUL_API_FINDPWD = USER_SERVER + "/users/me/updatepassword";

    String RESTFUL_API_FINDUSER = USER_SERVER + "/users/me";

    String RESTFUL_API_UsernameLike = USER_SERVER + "/users/findUserByIdInAndUsernameLike";

    String RESTFUL_API_FINDBYMOBILE = USER_SERVER + "/users/findByMobile";


    String RESTFUL_API_FINDUSERDETAIL = USER_SERVER + "/users/me/normal";

    String RESTFUL_API_VALIDATEPDATEMOBILE = USER_SERVER + "/users/me/updatemobile";
}
