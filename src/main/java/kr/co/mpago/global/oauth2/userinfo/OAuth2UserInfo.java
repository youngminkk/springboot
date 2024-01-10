package kr.co.mpago.global.oauth2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {
    //추상클래스를 상속받는 클래스에서만 사용할 수 있도록 protected 제어자를 사용
    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId();

    public abstract String getNickname();

    public abstract String getProfileImg();

    public abstract String getEmail();
}