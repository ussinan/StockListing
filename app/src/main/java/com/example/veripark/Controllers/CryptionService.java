package com.example.veripark.Controllers;

import android.util.Base64;

import java.nio.charset.StandardCharsets;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public abstract class CryptionService {

    private static String aesKey = "";
    private static String aesIV = "";

    public static String encrypt(String message){
        try {

            byte[] valueDecoded = Base64.decode(aesKey,Base64.NO_WRAP);
            byte[] ivDecoded = Base64.decode(aesIV,Base64.NO_WRAP);

            IvParameterSpec iv = new IvParameterSpec(ivDecoded);

            SecretKeySpec skeySpec = new SecretKeySpec(valueDecoded, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);

            byte[] encValue = cipher.doFinal(message.getBytes());
            String encryptedValue = Base64.encodeToString(encValue,Base64.NO_WRAP);

            return encryptedValue;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static String decrypt(String message){
        try {

            byte[] valueDecoded = Base64.decode(aesKey,Base64.NO_WRAP);
            byte[] ivDecoded = Base64.decode(aesIV,Base64.NO_WRAP);
            byte[] encValue = Base64.decode(message,Base64.NO_WRAP);

            IvParameterSpec iv = new IvParameterSpec(ivDecoded);

            SecretKeySpec skeySpec = new SecretKeySpec(valueDecoded, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec,iv);

            String str =  new String(cipher.doFinal(encValue), StandardCharsets.UTF_8);

            String decryptedValue = Base64.encodeToString(encValue,Base64.NO_WRAP);

            return str;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static void setAesKey(String key){
        aesKey = key;
    }

    public static void setAesIV(String iv){
        aesIV = iv;
    }

}
