package com.daughter.qinghan.comment;

public interface RedisKey {
    String EOP_TOKEN_PREFIX = "Eop_Token_";
    String EOP_USER_PREFIX = "Eop_";
    String EOP_USERINFO_PREFIX = "Eop_UserInfo_";


    String EOP_MANAGER_TOKEN_PREFIX = "Eop_Manager_Token_";
    String EOP_MANAGER_USER_PREFIX = "Eop_Manager_";
    String EOP_MANAGER_USERINFO_PREFIX = "Eop_Manager_UserInfo_";

    String WX_JS_CODE_SESSION_KEY="Wx_jsCode_sessionKey_";


    String ADD_ETC_LIMIT_LOCK_KEY = "eop:addEtc:lock%s";
}
