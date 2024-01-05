package com.deep.basket.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public final class GenerateCodeUtil {

    private GenerateCodeUtil() {}

    public static String generatedCode() {
        String code;

        try{
            SecureRandom random = SecureRandom.getInstanceStrong(); //랜덤수 생성하는 SecureRandom의 인스턴스 생성

            int c = random.nextInt(9000) + 1000;    //0~8999사이값 생성 후 1000 더해서 1000~9999 사이의 4자리 임의 값 생성

            code = String.valueOf(c);   //int값 String으로 변환 후 반환
        }catch (NoSuchAlgorithmException e){
            throw new RuntimeException("Problem when generating the random code...");
        }
        return code;
    }
}
