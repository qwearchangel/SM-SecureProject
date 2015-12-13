/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.filipferm.encryption_util;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Filip
 */
public class EncryptionModule {
    
    public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSha256";
    
    public static final int SALT_BYTE_SIZE = 32;
    public static final int HASH_BYTE_SIZE = 32;
    public static final int PBKDF2_ITERATIONS = 1000;
    
    
//    public static String createHash(String password, byte[] salt) {
//        return createHash(password.toCharArray(), salt); 
//    }
//    
//    public static String createHash(char[] pass, byte[] salt) {
//    }
    
    public static boolean validatePassword(String testPass, String correctPass, int iterations, String salt){
        byte[] correctPassHash = fromHex(correctPass);
        byte[] testPassHash = null;
        try {
            testPassHash = pbkdf2(testPass.toCharArray(), fromHex(salt), iterations, correctPassHash.length);
        } catch (InvalidKeySpecException | NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        if (testPassHash == null) {
            return false;
        }
        return slowEquals(correctPassHash, testPassHash);
    }
    
    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }

    public static byte[] pbkdf2(char[] password, byte[] salt, int iterations, int bytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
        PBEKeySpec spec = new PBEKeySpec(password, salt, iterations, bytes * 8);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        return skf.generateSecret(spec).getEncoded();
    }

    public static byte[] fromHex(String hex) {
        byte[] binary = new byte[hex.length() / 2];
        for (int i = 0; i < binary.length; i++) {
            binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return binary;
    }

    public static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }
        return hex;
    }
    
}
