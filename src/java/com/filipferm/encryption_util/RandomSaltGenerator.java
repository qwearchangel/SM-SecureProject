package com.filipferm.encryption_util;

import java.security.SecureRandom;
/**
 *
 * @author Filip
 */
public class RandomSaltGenerator {
    
    public static byte[] getRandomSalt(int size) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[size];
        random.nextBytes(salt); 
        return salt;
    }
}
