package com.example.api_ver5;


import androidx.appcompat.app.AppCompatActivity;

import android.icu.util.Freezable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.api_ver5.databinding.ActivityMainBinding;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends base_ACt<ActivityMainBinding,CommVM> {

        private String data ;
        private String base64Encryption;

    @Override
    protected void initViews() {
            binding.btEncryption.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data = binding.edtStringData.getText().toString() ;
                    base64Encryption = binding.edtBase64PrivateKey.getText().toString() ;
                    try {
                        String code = Base64.getEncoder().encodeToString(encrypt(data, base64Encryption));
                        binding.edtResult.setText(code) ;
                    }catch (Exception e)
                    {
                        e.printStackTrace()  ;
                    }
                }
            });

    }


    public static PublicKey getPublicKey(String base64PublicKey){
        PublicKey publicKey = null;
        try{
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public static PrivateKey getPrivateKey(String base64PrivateKey){
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            assert keyFactory != null;
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public static byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public static String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public static String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }


//    public static PublicKey getPublicKey(String base64PublicKey){
//        PublicKey publicKey = null;
//        try{
//            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
//            KeyFactory keyFactory = KeyFactory.getInstance("RSA") ;
//            publicKey = keyFactory.generatePublic(keySpec) ;
//            Log.e(MainActivity.class.getName(),"Public key : " + publicKey);
//            return publicKey ;
//        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
//            e.printStackTrace() ;
//        }
//        return publicKey ;
//    }
//    private static String encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
//        Cipher cipher = Cipher.getInstance("RSA");
//        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
//        byte[] az =  cipher.doFinal(data.getBytes());
//        Log.e(MainActivity.class.getName(),"Encypt -1 : " + Arrays.toString(az));
//        Log.e(MainActivity.class.getName(),"Encypt -2 : " + az.toString());
//        String encode = encode(az) ;
//        Log.e(MainActivity.class.getName(),"Encypt: -3" + encode);
//        return encode ;
//    }
//
//
//    private static String encode(byte[] data)
//    {
//        return Base64.getEncoder().encodeToString(data);
//    }
//


    @Override
    protected Class<CommVM> ClassVM() {
        return CommVM.class;
    }

    @Override
    protected ActivityMainBinding initViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }
}