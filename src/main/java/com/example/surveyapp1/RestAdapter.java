package com.example.surveyapp1;

import android.content.Context;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;

public class RestAdapter {

    private static final String BKS_TYPE = "BKS";
    private static final String SSL_PROTOCOL = "TLS";
    private static final String PASSWORD = "password";

    public static OkHttpClient createOkHttpClient(Context context) throws Exception {
        // Load the BKS keystore from resources
        KeyStore keyStore = KeyStore.getInstance(BKS_TYPE);
        // Adjust resource name

        try (InputStream bksInputStream = context.getResources().openRawResource(R.raw.certificate)) {
            keyStore.load(bksInputStream, PASSWORD.toCharArray()); // Use your BKS password
        }

        // Initialize TrustManager with the KeyStore
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);

        // Initialize SSLContext with the TrustManager
        SSLContext sslContext = SSLContext.getInstance(SSL_PROTOCOL);
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null);

        // Create OkHttpClient with custom SSL configuration
        return new OkHttpClient.Builder()
                .sslSocketFactory(sslContext.getSocketFactory(), (X509TrustManager) trustManagerFactory.getTrustManagers()[0])
                .build();
    }
}
