package com.example.surveyapp1;

import android.content.Context;
import android.util.Log;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;


//this class is extremely useful. It generates a BKS keystore which the program
//can use to connect with the https server. Any time the server's certificate
//expires, you'll have to use this class to generated a new one. The way it works
//Is you go to the server, export the certificate (which should be a .crt)
//and then move that certificate into res/raw in this project, naming it
//certificate.crt. Then, run this class's method in main activity or something
//and the new certificate will show up in the SASSEMA directory. Then, download it
//and move it into res/raw and name it certificate.bks

//Make sure not to let PostDatam run while an incorrect certificate is there or
//It will error out

//You'll know the certificate is outdated if you start getting errors like this
//OkHttp Request Failed: Unacceptable certificate
public class BKSGenerator {

    private static final String KEYSTORE_TYPE = "BKS";
    private static final String KEYSTORE_PASSWORD = "password"; // we should change this and make it more secure in the future
    private static final String ALIAS = "myalias";
    private static final String TAG = "TestingTesting123";

    public static void generateBKS(Context context) {
        try {
            // Load the crt certificate from res/raw/certificate.crt
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509", BouncyCastleProvider.PROVIDER_NAME);
            InputStream certInputStream = context.getResources().openRawResource(R.raw.certificate);
            Certificate certificate = certFactory.generateCertificate(certInputStream);
            certInputStream.close();

            // Generate a KeyPair (public and private key)
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA", BouncyCastleProvider.PROVIDER_NAME);
            keyPairGen.initialize(2048); // Key size
            KeyPair keyPair = keyPairGen.generateKeyPair();

            // Create a KeyStore and load the KeyPair and Certificate into it
            KeyStore keyStore = KeyStore.getInstance(KEYSTORE_TYPE);
            keyStore.load(null, null);
            keyStore.setKeyEntry(ALIAS, keyPair.getPrivate(), KEYSTORE_PASSWORD.toCharArray(), new Certificate[]{certificate});

            // Save the KeyStore to a file in the SASSEMA directory
            File keyStoreFile = new File(context.getExternalFilesDir(null), "SASSEMA/keystore.bks");
            FileOutputStream keyStoreOutputStream = new FileOutputStream(keyStoreFile);
            keyStore.store(keyStoreOutputStream, KEYSTORE_PASSWORD.toCharArray());
            keyStoreOutputStream.close();

            Log.d(TAG, "BKS file saved to: " + keyStoreFile.getAbsolutePath());

        } catch (Exception e) {
            Log.e(TAG, "Error generating BKS file: " + e.getMessage(), e);
        }
    }
}