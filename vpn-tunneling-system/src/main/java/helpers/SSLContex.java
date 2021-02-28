package helpers;

import java.io.*;
import javax.net.ssl.*;
import java.security.KeyStore;


public class SSLContex {

    private SSLContext ctx;
    private KeyStore keystore, truststore;
    private final String keystorePath;
    private final String truststorePath;

    public SSLContex(String keystorePath , String truststorePath , char[] storepass, char[] keypass) {
        this.keystorePath = keystorePath;
        this.truststorePath = truststorePath;
        initSSLContext(storepass, keypass);
    }

    public void initKeystores(String keyPath , String trustPath , char[] storepass) {
        try {
            keystore = KeyStore.getInstance("JKS" , "SUN");
            truststore = KeyStore.getInstance("JKS", "SUN");

            keystore.load(new FileInputStream(keyPath), storepass);
            truststore.load(new FileInputStream(trustPath), storepass );
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void initSSLContext(char[] storepass , char[] keypass) {
        try {
            ctx = SSLContext.getInstance("TLSv1.2", "SunJSSE");
            initKeystores(keystorePath, truststorePath, storepass);

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509", "SunJSSE");
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509","SunJSSE");

            kmf.init(keystore, keypass);
            tmf.init(truststore);
            ctx.init(kmf.getKeyManagers(), tmf.getTrustManagers(),null);
        } catch(Exception e) {
                System.err.println(e.getMessage());
        }
    }

    public SSLContext getSSLContext() {
        return ctx;
    }
}
