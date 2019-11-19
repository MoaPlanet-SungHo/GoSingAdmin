package com.moaplanet.gosingadmin.common.manager;

import android.content.Context;
import android.os.Build;
import android.security.KeyPairGeneratorSpec;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;

import com.moaplanet.gosingadmin.manager.SharedPreferencesManager;

import java.math.BigInteger;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.ECGenParameterSpec;
import java.util.Calendar;
import java.util.Enumeration;

import javax.security.auth.x500.X500Principal;

/**
 * 인증 관련 매니저
 */
public class AuthManager {

    // 모바일 핀 관련 ALIAS
    public final String KEY_ALIAS_MOBILE_PIN = "TYPE_MOBILE_PIN";

    // 키스토어
    private KeyStore keyStore;
    // 콜백
    private onAuthCallback onAuthCallback;

    public void setOnAuthCallback(AuthManager.onAuthCallback onAuthCallback) {
        this.onAuthCallback = onAuthCallback;
    }

    /**
     * 핀 패스워드 초기화
     */
    public void onInitPin(Context context, String keyAlias, String pin) {

        if (pin == null || pin.length() != 6) {
            onFail();
        }

        Boolean existKey = onExistKey(keyAlias);
        if (existKey != null && !existKey) {

            if (onCreateKey(keyAlias, context)) {
                onCreatePin(keyAlias, pin);
            } else {
                onFail();
            }
        } else if (existKey == null) {
            onFail();
        } else {
            onCreatePin(keyAlias, pin);
        }
    }

    /**
     * 핀 패스워드 생성
     */
    private void onCreatePin(String keyAlias, String pin) {
        try {
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(keyAlias, null);
            Signature signature = Signature.getInstance("SHA512withECDSA");
            signature.initSign(privateKey);
            signature.update(pin.getBytes());
            byte[] signPin = signature.sign();

            SharedPreferencesManager
                    .getInstance()
                    .setPin(Base64.encodeToString(signPin, Base64.DEFAULT));

            onSuccess();

        } catch (Exception e) {
            e.printStackTrace();
            onFail();
        }
    }

    /**
     * 키가 존재하는지 체크
     */
    private Boolean onExistKey(String keyAlias) {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            Enumeration<String> aliases = keyStore.aliases();
            boolean existKey = false;
            while (aliases.hasMoreElements()) {
                String ali = aliases.nextElement();
                if (ali.equals(keyAlias)) {
                    existKey = true;
                    break;
                }
            }

            if (existKey) {
                // 키가 이미 존재할경우
                return true;
            } else {
                // 키가 없을 경우
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 키 생성
     *
     * @param keyAlias
     * @param context
     */
    private Boolean onCreateKey(String keyAlias, Context context) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
                kpg.initialize(new KeyGenParameterSpec.Builder(keyAlias,
                        KeyProperties.PURPOSE_SIGN)
                        .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                        .setDigests(KeyProperties.DIGEST_SHA256,
                                KeyProperties.DIGEST_SHA512)
//                        .setUserAuthenticationRequired(true)
//                        .setUserAuthenticationValidityDurationSeconds(5 * 60)
                        .build());

                kpg.generateKeyPair();
                return true;
            } else {
                Calendar start = Calendar.getInstance();
                Calendar end = Calendar.getInstance();
                end.add(Calendar.YEAR, 1);

                KeyPairGenerator kpg =
                        KeyPairGenerator.getInstance(
                                "EC",
                                "AndroidKeyStore");

                kpg.initialize(new KeyPairGeneratorSpec.Builder(context)
                        .setKeySize(256)
                        .setAlias(keyAlias)
//                        .setKeyType(KeyProperties.KEY_ALGORITHM_EC)
                        .setSubject(new X500Principal("CN=Dodgy Stuff"))
                        .setSerialNumber(BigInteger.valueOf(123456789L))
                        .setStartDate(start.getTime())
                        .setEndDate(end.getTime())
                        .build());

                kpg.generateKeyPair();
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 실패
     */
    private void onFail() {
        if (onAuthCallback != null) {
            onAuthCallback.onFail();
        }
    }

    /**
     * 성공
     */
    private void onSuccess() {
        if (onAuthCallback != null) {
            onAuthCallback.onSuccess();
        }
    }

    /**
     * 키스토어 관련 인증 콜백
     */
    public interface onAuthCallback {
        void onSuccess();

        void onFail();
    }
}
