package com.devapp.aesencryptionjava.util;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.devapp.aesencryptionjava.model.AES;
import com.devapp.aesencryptionjava.model.Result;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

public class Encryption {
    private static AES aes;
    private static final DecimalFormat df = new DecimalFormat();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Result ECBEncryptionWithKey(String plainText, String keyInput) {
        df.setMaximumFractionDigits(8);
        Log.d("Fragment", "ECBEncryptionWithKey plain text before: " + Arrays.toString(plainText.getBytes()));
        byte[] inputText = pushDataToBlock(plainText).getBytes();
        Log.d("Fragment", "ECBEncryptionWithKey plain text after: " + Arrays.toString(inputText));
        byte[] key;
        key = keyInput.getBytes();
        aes = new AES(key);
        long startTime = System.nanoTime();
        byte[] cipherBytes = aes.ECB_encrypt(inputText);
        Log.d("Fragment", "ECBEncryptionWithKey cipher text encryption byte: " + Arrays.toString(cipherBytes));
        String a = Base64.getEncoder().encodeToString(cipherBytes);
        Log.d("Fragment", "ECBEncryptionWithKey cipher text encryption text: " + a);
        long endTime = System.nanoTime();
        return new Result(a, df.format((float) (endTime - startTime)/1000000f) + "ms");

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Result ECBDecryptionWithKey(String cipherText, String keyInput) {
        df.setMaximumFractionDigits(8);
        Log.d("Fragment", "ECBDecryptionWithKey cipher text: " + cipherText);
        byte[] key = keyInput.getBytes();
        Log.d("Fragment", "ECBDecryptionWithKey cipher text bytes: " + Arrays.toString(Base64.getDecoder().decode(cipherText)));
        aes = new AES(key);
        long startTime = System.nanoTime();
        byte[] textBytes = aes.ECB_decrypt(Base64.getDecoder().decode(cipherText));
        Log.d("Fragment", "ECBDecryptionWithKey plain text: " + Arrays.toString(textBytes));
        String a = new String(textBytes);
        long endTime = System.nanoTime();
        return new Result(a, df.format((float) (endTime - startTime)/1000000f) + "ms");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Result CBCEncryptionWithKey(String plainText, String keyInput, String ivInput) {
        df.setMaximumFractionDigits(8);
        byte[] inputText = pushDataToBlock(plainText).getBytes();
        Log.d("Fragment", "CBCEncryptionWithKey: " + inputText.length);
        byte[] key = keyInput.getBytes();
        byte[] iv = ivInput.getBytes();
        aes = new AES(key, iv);
        long startTime = System.nanoTime();
        byte[] cipherBytes = aes.CBC_encrypt(inputText);
        Log.d("Fragment", "CBCEncryptionWithKey: " + cipherBytes.length);
        String a = Base64.getEncoder().encodeToString(cipherBytes);
        long endTime = System.nanoTime();
        return new Result(a, df.format((float) (endTime - startTime)/1000000f) + "ms");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Result CBCDecryptionWithKey(String cipherText, String keyInput, String ivInput) {
        df.setMaximumFractionDigits(8);
        byte[] key;
        key = keyInput.getBytes();
        byte[] iv = ivInput.getBytes();
        aes = new AES(key, iv);
        long startTime = System.nanoTime();
        byte[] cipherBytes = aes.CBC_decrypt(Base64.getDecoder().decode(cipherText));
        String a = new String(cipherBytes);
        long endTime = System.nanoTime();
        return new Result(a, df.format((float) (endTime - startTime)/1000000f) + "ms");
    }

    public static String diffBit(String text1, String text2) {
        return String.valueOf(numBitDiff(text1.getBytes(), text2.getBytes()));
    }

    private static String pushDataToBlock(String text) {
        int spaceNum = text.getBytes().length % 16 == 0 ? 0 : 16 - text.getBytes().length % 16;
        StringBuilder textBuilder = new StringBuilder(text);
        for (int i = 0; i < spaceNum; i++) textBuilder.append(" ");
        text = textBuilder.toString();
        return text;
    }

    public static byte[] makeRandomKey() {
        SecureRandom random = new SecureRandom();
        int length = new Random().nextInt(2);
        switch (length) {
            case 1: {
                byte[] bytes = new byte[24];
                random.nextBytes(bytes);
                return bytes;
            }
            case 2: {
                byte[] bytes = new byte[32];
                random.nextBytes(bytes);
                return bytes;
            }
            default: {
                byte[] bytes = new byte[16];
                random.nextBytes(bytes);
                return bytes;
            }
        }
    }

    public static byte[] makeRandomIv() {
        String key = "";
        for (int i = 0; i < 1; i++) key += Long.toHexString(Double.doubleToLongBits(Math.random()));
        return key.getBytes();
    }

    public static int numBitDiff(byte[] a, byte[] b) {
        int num = 0;
        byte[] result = new byte[Math.min(a.length, b.length)];
        for (int j = 0; j < result.length; j++) {
            int xor = a[j] ^ b[j];
            while (xor > 0) {
                int temp = xor % 2;
                if (temp == 1) num++;
                xor /= 2;
            }
        }
        return num;
    }
}
