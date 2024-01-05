package com.deep.basket.service;

import com.deep.basket.vo.KakaoVo;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class KaKaoService {

    @Value("$(kako.client.id)")
    private String KAKAO_CLIENT_ID;

    @Value("${kakao.client.secret}")
    private String KAKAO_CLIENT_SECRET;

    @Value("${kakao.redirect.url}")
    private String KAKAO_REDIRECT_URL;

    private final static String KAKAO_AUTH_URI = "https://kauth.kakao.com";
    private final static String KAKAO_API_URI = "https://kapi.kakao.com";

    public String getKakaoLogin(){
        return KAKAO_AUTH_URI+"/oauth/authorize"
                +"?client_id="+KAKAO_CLIENT_ID
                +"&redirect_uri="+KAKAO_REDIRECT_URL
                +"&response_type=code";
    }
    //컨트롤러에서 리턴받은 인증코드값을 통해 카카오 인증 서버에 액세스 토큰 요청
    //1. 액세스토큰 2.리프레쉬토큰(액세스토큰갱신할때사용) 두가지가 있다.
    public KakaoVo getkaKaoInfo(String code) throws Exception{
        if(code==null)throw new Exception("Failed get authorization code");

        String accessToken ="";
        String refreshToken ="";

        try{
            HttpHeaders headers = new HttpHeaders();    //header 기본정보
            headers.add("Content-type","application/x-www-form-urlencoded");

            MultiValueMap<String,String> params = new LinkedMultiValueMap<>();  //hashMap에 정보넣기
            params.add("grant_type", "authorization_code");
            params.add("client_id"    , KAKAO_CLIENT_ID);
            params.add("client_secret", KAKAO_CLIENT_SECRET);
            params.add("code"         , code);
            params.add("redirect_uri" , KAKAO_REDIRECT_URL);

            RestTemplate restTemplate = new RestTemplate();     //만들어둔 params, headers넣기
            HttpEntity<MultiValueMap<String,String>> httpEntity = new HttpEntity<>(params, headers);

            ResponseEntity<String> response = restTemplate.exchange(    //주소로 토큰 요청
                    KAKAO_AUTH_URI +"/outh/token",
                    HttpMethod.POST,
                    httpEntity,
                    String.class
            );

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObj = (JSONObject)jsonParser.parse(response.getBody());

            accessToken = (String) jsonObj.get("access_token");
            refreshToken = (String) jsonObj.get("refresh_token");

        }catch (Exception e){
            throw new Exception("API call failed");
        }
        return getUserInfoWithToken(accessToken);
    }

    //전달받은 엑세스토큰을 통해 사용자 정보를 가져온다. 사용자 정보는 사용자가 동의한 내역한에서 가져온다.
    private KakaoVo getUserInfoWithToken(String accessToken) throws Exception {
        //HttpHeader생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","Bearer"+accessToken);
        headers.add("Content-type","application/x-www-form-urlencoded;charset=utf-8");

        //httpHeader담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = rt.exchange(
                KAKAO_API_URI + "/v2/user/me", //oauth 프로퍼티 user info uri
                HttpMethod.POST,
                httpEntity,
                String.class
        );

        //Response 데이터 파싱
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObj = (JSONObject) jsonParser.parse(response.getBody());
        JSONObject account = (JSONObject) jsonObj.get("kakako_account");
        JSONObject profile = (JSONObject) account.get("profile");

        long id = (long) jsonObj.get("id");
        String email = String.valueOf(account.get("email"));
        String nickname = String.valueOf(profile.get("nickname"));

        return KakaoVo.builder()
                .id(id)
                .email(email)
                .nickname(nickname).build();
    }

}