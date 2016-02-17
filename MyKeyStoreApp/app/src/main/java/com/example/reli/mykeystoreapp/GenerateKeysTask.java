package com.example.reli.mykeystoreapp;

import android.content.Context;
import android.os.AsyncTask;
import android.security.KeyPairGeneratorSpec;
import android.widget.Toast;

import java.io.File;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Calendar;

import javax.security.auth.x500.X500Principal;

/**
 * Created by reli on 14.3.2015.
 * copyright: Jenny Tyrväinen 
 */
public class GenerateKeysTask extends AsyncTask<Void, Void, KeyPair> {

    private MainActivity context = null;

    public GenerateKeysTask(MainActivity context) {
        this.context = context;
    }

    @Override
    /** Generate keys.*/
    protected KeyPair doInBackground(Void... params) {

        Calendar notBefore = Calendar.getInstance();
        Calendar notAfter = Calendar.getInstance();
        notAfter.add(Calendar.YEAR, 1);

        KeyPairGeneratorSpec spec = new KeyPairGeneratorSpec.Builder(context)
                .setAlias("myKey")
                .setSubject(
                        new X500Principal("CN=myKey"))
                .setSerialNumber(BigInteger.valueOf(1337)).setStartDate(notBefore.getTime())
                .setEndDate(notAfter.getTime()).build();

        KeyPairGenerator kpGenerator = null;

        try {
            kpGenerator = KeyPairGenerator.getInstance("RSA", "AndroidKeyStore");
        } catch (NoSuchAlgorithmException | NoSuchProviderException e) {
            e.printStackTrace();
        }
        try {
            assert kpGenerator != null;
            kpGenerator.initialize(spec);
        } catch (InvalidAlgorithmParameterException e) {
            e.printStackTrace();
        }

        return kpGenerator.generateKeyPair();
    }

    /** Hand over the keys from this thread to the MainActivity and signal that key generation is done.*/
    protected void onPostExecute(KeyPair kp) {
        context.keypair = kp;
        context.keypairDone();
    }
}
