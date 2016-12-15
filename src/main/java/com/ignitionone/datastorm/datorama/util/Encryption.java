package com.ignitionone.datastorm.datorama.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

/**
 * Contains methods for basic AES encryption and decryption.
 */
public class Encryption {

    //~ Static fields/initializers -------------------------------------------------------------------------------------

    public static final String AES = "AES";

    //~ Methods --------------------------------------------------------------------------------------------------------

    /**
     * Internal AES Encryption method for generation of secret key.
     *
     * @param keyFile Text file that contains the encryption key.
     *
     * @return The secret key spec.
     *
     * @throws NoSuchAlgorithmException on error
     * @throws IOException on error
     */
    private static SecretKeySpec getSecretKeySpec(File keyFile) throws NoSuchAlgorithmException, IOException {
        byte[] key = readKeyFile(keyFile);
        SecretKeySpec sks = new SecretKeySpec(key, Encryption.AES);

        return sks;
    }

    /**
     * Decrypts string with key file.
     *
     * @param message String to be decrypted.
     *
     * @return Decrypted value in plain text.
     *
     * @throws GeneralSecurityException on error
     * @throws IOException on error
     */
    public static String decrypt(String message) throws GeneralSecurityException, IOException {
        File keyFile = new File(System.getProperty("user.dir") + "//src//test//resources//Assets//enc.key");
        SecretKeySpec sks = getSecretKeySpec(keyFile);
        Cipher cipher = Cipher.getInstance(Encryption.AES);
        cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] decrypted = cipher.doFinal(hexStringToByteArray(message));

        return new String(decrypted);
    }


    /**
     * Encrypts a string based on the key in the db.key file. If no key file exists it creates one and generates a new key.
     *
     * @param value String to be encrypted.
     * @param keyFile Text file containing encryption key.
     *
     * @return Encrypted string in plain text.
     *
     * @throws GeneralSecurityException on error
     * @throws IOException on error
     */
    public static String encrypt(String value, File keyFile) throws GeneralSecurityException, IOException {
        if (!keyFile.exists()) {
            KeyGenerator keyGen = KeyGenerator.getInstance(Encryption.AES);
            keyGen.init(128);
            SecretKey sk = keyGen.generateKey();
            FileWriter fw = new FileWriter(keyFile);
            fw.write(byteArrayToHexString(sk.getEncoded()));
            fw.flush();
            fw.close();
        }
        SecretKeySpec sks = getSecretKeySpec(keyFile);
        Cipher cipher = Cipher.getInstance(Encryption.AES);
        cipher.init(Cipher.ENCRYPT_MODE, sks, cipher.getParameters());
        byte[] encrypted = cipher.doFinal(value.getBytes());

        return byteArrayToHexString(encrypted);
    }

    /**
     * Internal AES Encryption helper method for converting byte array back to string.
     *
     * @param b Byte array to be converted.
     *
     * @return Input value in String format.
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuilder sb = new StringBuilder(b.length * 2);
        for (int i = 0; i < b.length; i++) {
            int v = b[i] & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase();
    }

    /**
     * Internal AES Encryption helper method for converting key to byte array.
     *
     * @param s String to be converted to byte array
     *
     * @return Input value in byte array format.
     */
    private static byte[] hexStringToByteArray(String s) {
        byte[] b = new byte[s.length() / 2];
        for (int i = 0; i < b.length; i++) {
            int index = i * 2;
            int v = Integer.parseInt(s.substring(index, index + 2), 16);
            b[i] = (byte) v;
        }
        return b;
    }

    /**
     * Internal AES Encryption method for reading the key and converting into bytes.
     *
     * @param keyFile Text file containing encryption key.
     *
     * @return Key in byte array format.
     *
     * @throws FileNotFoundException on error
     */
    private static byte[] readKeyFile(File keyFile) throws FileNotFoundException {
        Scanner scanner = new Scanner(keyFile).useDelimiter("\\Z");
        String keyValue = scanner.next();
        scanner.close();

        return hexStringToByteArray(keyValue);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //////////Methods below this line are for troubleshooting and validation only////////////////////////////////
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////

    //<editor-fold desc="Use this method to encrypt a password">
    // public static void main(String[] args) throws Exception{
    //
    // String keyFile = System.getProperty("user.dir") + "\\src\\test\\resources\\" + "db.key";
    // String inputPassword = "test";
    // String encryptedPwd = Encryption.encrypt(inputPassword, new File(keyFile));
    // String decryptedPwd = Encryption.decrypt(encryptedPwd, new File(keyFile));
    //
    // System.out.println("Input PW: " + inputPassword);
    // System.out.println("Encrypted PW: " + encryptedPwd);
    // System.out.println("Decrypted PW: " + decryptedPwd);

    // }
}

