package com.example.api.payment.mqservice.mqconfig;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class SSLContextBuilder {

    private SSLContext sslContext;

    @Value("${mq.keystore.password:#{null}}")
    private String keyStorePassword;

    @Value("${mq.keystore.path:#{null}}")
    private String keyStorePath;

    public SSLSocketFactory getSslSocketFactory() {

        if (Objects.isNull(sslContext)) {
            synchronized (this) {
                if (Objects.isNull(sslContext)) {
                    Optional<SSLContext> context = initSSLContext();
                    context.ifPresent(value -> sslContext = value);
                }
            }
        }
        return sslContext.getSocketFactory();
    }

    private Optional<SSLContext> initSSLContext() {

        try {
            final org.apache.http.ssl.SSLContextBuilder sslContextBuilder =
                    new org.apache.http.ssl.SSLContextBuilder();

            final Optional<KeyStore> keyStore = initKeyStore();
            if (!keyStore.isPresent()) {
                log.info("No keystore is configured for sslContext");
            } else {
                sslContextBuilder.loadKeyMaterial(keyStore.get(), keyStorePassword.toCharArray());
            }
            return Optional.ofNullable(sslContextBuilder.build());
        } catch (Exception e) {
            log.error("Failed to initialize SSLContext", e);
        }
        return Optional.empty();
    }

    private Optional<KeyStore> initKeyStore() {

        if (StringUtils.hasText(keyStorePath))  {
            final FileSystemResource keyStoreResource = new FileSystemResource(keyStorePath.trim());
            try {
                final KeyStore keyStore = KeyStore.getInstance("JKS");
                keyStore.load(keyStoreResource.getInputStream(), keyStorePassword.toCharArray());
                return Optional.of(keyStore);
            } catch (NoSuchAlgorithmException
                     | CertificateException
                     | IOException
                     | KeyStoreException e) {
                log.error("Failed to initialize keyStore", e);
            }
        }
        return Optional.empty();
    }
}
