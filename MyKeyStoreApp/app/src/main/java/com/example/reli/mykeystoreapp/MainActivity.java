package com.example.reli.mykeystoreapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.security.KeyPair;
import java.security.KeyStore;
import java.security.Signature;

/**
 * Copyright: Jenny Tyrväinen 2015
 **/


public class MainActivity extends ActionBarActivity {

    private static final String TAG = "Täällä.";
    private GenerateKeysTask genKeysTask = new GenerateKeysTask(this);
    public KeyPair keypair = null;
    private String alias = "myKey";
    private byte[] signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(keypair == null) {
            genKeysTask.execute();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /** Show a toast-message, when keypair are done in a separate thread.*/
    public void keypairDone() {
        CharSequence text = "Keypair ready.";
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(this, text, duration);
        toast.show();


    }

    /** Sign a message using Key Store and a signature created with generated keys.*/
    public byte[] signMessage(String data) {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                Log.w(TAG, "not an instance of private key entry.");
                return null;
            }
            Signature s = Signature.getInstance("SHA256withRSA");
            s.initSign(((KeyStore.PrivateKeyEntry) entry).getPrivateKey());
            s.update(data.getBytes());
            this.signature = s.sign();
            return signature;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /** Verify message using KeyStore and generated keys and private field signature.*/
    public boolean verifyMessage(String data) {
        try {
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            KeyStore.Entry entry = ks.getEntry(alias, null);
            if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
                Log.w(TAG, "Not an instance of a PrivateKeyEntry");
                return false;
            }
            Signature s = Signature.getInstance("SHA256withRSA");
            s.initVerify(((KeyStore.PrivateKeyEntry) entry).getCertificate());
            s.update(data.getBytes());
            return s.verify(signature);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /** Called when the user clicks the Sign button */
    public void signMsg(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_signMsg);
        String message = editText.getText().toString();
        TextView textView = (TextView) findViewById(R.id.sign_result);

        textView.setTextSize(40);
        textView.setText(signMessage(message).toString());
    }

    /** Called when the user clicks the Verify button */
    public void verifyMsg(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_verify);
        String message = editText.getText().toString();
        TextView textView = (TextView) findViewById(R.id.verify_result);

        textView.setTextSize(40);
        textView.setText("" + verifyMessage(message));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
