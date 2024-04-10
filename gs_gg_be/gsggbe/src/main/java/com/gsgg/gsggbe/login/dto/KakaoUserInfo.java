package com.gsgg.gsggbe.login.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KakaoUserInfo {
//    @JsonProperty("id")
    private String id;

    @JsonProperty("connected_at")
    private String connectedAt;

//    @JsonProperty("properties")
    private KakaoProperty properties;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;
}
@Data
class KakaoProperty{
    private String nickname;
    @JsonProperty("profile_image")
    private String profileImage;
    @JsonProperty("thumbnail_image")
    private String thumbnailImage;
}
@Data
class KakaoAccount{
    @JsonProperty("profile_nickname_needs_agreement")
    private String profileNicknameNeedsAgreement;

    @JsonProperty("profile_image_needs_agreement")
    private String profileImageNeedsAgreement;

    private Profile profile;
}

@Data
class Profile{
    private String nickname;
    @JsonProperty("thumbnail_image_url")
    private String thumbnailImageUrl;

    @JsonProperty("profile_image_url")
    private String profileImageUrl;

    @JsonProperty("is_default_image")
    private String isDefaultImage;

    @JsonProperty("is_default_nickname")
    private String is_default_nickname;
}