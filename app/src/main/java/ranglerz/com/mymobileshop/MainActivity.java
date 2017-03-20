package ranglerz.com.mymobileshop;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.support.annotation.MainThread;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.UUID;

import ranglerz.com.mymobileshop.util.IabBroadcastReceiver;
import ranglerz.com.mymobileshop.util.IabHelper;
import ranglerz.com.mymobileshop.util.IabResult;
import ranglerz.com.mymobileshop.util.Inventory;
import ranglerz.com.mymobileshop.util.Purchase;

public class MainActivity extends AppCompatActivity implements IabBroadcastReceiver.IabBroadcastListener{

    //inApp purchase ID for Beard
    static final String SKU_SGCOREPRIME = "sgcoreprime";
    static final String SKU_SGGRANDNEO = "sggrandneo";
    static final String SKU_SGGRANDPRIMEPLUS = "sggrandprimeplus";
    static final String SKU_SGJ1 = "sgj1";
    static final String SKU_SGJ1ACE = "sgj1ace";
    static final String SKU_SGJ1MINIPRIME = "sgj1miniprime";
    static final String SKU_SGJ2 = "sgj2";
    static final String SKU_SGJ2PRIMEPLUS = "sgj2primeplus";
    static final String SKU_STAR2 = "sgstar2";
    static final String SKU_SGSTARS5282 = "sgstars5282";
    static final String SKU_SGA3 = "sgs6edge";
    static final String TAG = "MAINACTIVITY";


    static final String BILLING_CHARGES = "+ (30% Google Biling Service Charges)";

    ImageView leftArrow, rightArrow;
    HorizontalScrollView scrollView;


    IabHelper mHelper;
    String devPayLoad = "";
    private static final int IAPCODE = 10001;
    static final int RC_REQUEST = 10001;

    TextView contact_us_call;
    ImageView galaxy_grand_prime_plus, galaxy_j2_prime_plus, galaxy_j2, galaxy_grand_neo, galaxy_j1, galaxy_j1_ace, galaxy_j1_mini, galaxy_core_prime, galaxy_star2, galaxy_star_s5282, galaxy_a3;

    String cutomer_phn, cutomer_adrs;

    int mobileNumber = 0;

    IabHelper m_Helper;
    boolean mIsPremium = false;
    boolean mSubscribedToInfiniteGas = false;
    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFotView();
        getVisibiliteyForMobiles();

        keyInitialization();
        startUpLab();
        buy();
        loadData();
        contactUsPhoneCall();

    }//end of onCreate

    public void initFotView(){
        galaxy_grand_prime_plus = (ImageView)findViewById(R.id.for_sell_grand_prime_plus);
        galaxy_j2_prime_plus = (ImageView)findViewById(R.id.for_sell_samsung_galaxy_grand_prime_j2);
        galaxy_j2 = (ImageView)findViewById(R.id.for_sell_samsung_galxy_j2);
        galaxy_grand_neo = (ImageView)findViewById(R.id.for_sell_samsung_galaxy_grand_neo);
        galaxy_j1 = (ImageView)findViewById(R.id.for_sall_samsung_galaxy_j1);
        galaxy_j1_ace = (ImageView)findViewById(R.id.for_sell_samsung_galaxy_j1_ace);
        galaxy_j1_mini = (ImageView)findViewById(R.id.for_sell_sg_j1_mini_prime);
        galaxy_core_prime = (ImageView)findViewById(R.id.for_sell_core_prime);
        galaxy_star2 = (ImageView)findViewById(R.id.for_sell_star_2);
        galaxy_star_s5282 = (ImageView)findViewById(R.id.for_sell_star_s5282);
        galaxy_a3 = (ImageView)findViewById(R.id.for_sell_samsung_galaxy_a3);
        leftArrow = (ImageView)findViewById(R.id.arrow_left);
        rightArrow = (ImageView)findViewById(R.id.arrow_right);
        contact_us_call = (TextView) findViewById(R.id.contact_us);
        scrollView = (HorizontalScrollView)findViewById(R.id.h_scrol_view);
    }


    public void buy(){
        //onClick for galaxy mobile 1
        galaxy_core_prime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 1;
                showSingleImageDialog();
                //buyMobile1();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 1

        //onClick for galaxy mobile 2
        galaxy_grand_neo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 2;
                showSingleImageDialog();
                //buyMobile2();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 2
        //onClick for galaxy mobile 3
        galaxy_grand_prime_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 3;
                showSingleImageDialog();
                // /buyMobile3();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 1

//onClick for galaxy mobile 4
        galaxy_j1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 4;
                showSingleImageDialog();
                //buyMobile4();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 4

        //onClick for galaxy mobile 5
        galaxy_j1_ace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 5;
                showSingleImageDialog();
                // buyMobile5();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 5

//onClick for galaxy mobile 6
        galaxy_j1_mini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 6;
                showSingleImageDialog();
                //buyMobile6();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 6

        //onClick for galaxy mobile 7
        galaxy_j2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 7;
                showSingleImageDialog();
                //buyMobile7();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 7

        //onClick for galaxy mobile 8
        galaxy_j2_prime_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 8;
                showSingleImageDialog();
                //buyMobile8();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 8
//onClick for galaxy mobile 9
        galaxy_star2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 9;
                showSingleImageDialog();
                //buyMobile9();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 9
//onClick for galaxy mobile 10
        galaxy_star_s5282.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 10;
                showSingleImageDialog();
                //buyMobile10();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 10

        //onClick for galaxy mobile 11
        galaxy_a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileNumber = 11;
                showSingleImageDialog();
                //buyMobile10();
                // forSaleBeardDilaog();
            }
        });//end of onClick for mobile 11


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      if(requestCode == IAPCODE )
        {
            android.util.Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
            if (mHelper == null) return;

            // Pass on the activity result to the helper for handling
            if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {




                super.onActivityResult(requestCode, resultCode, data);
            }
            else {
                android.util.Log.d(TAG, "onActivityResult handled by IABUtil.");
            }

        }



    }


    //making a phone call on contact us
    public void contactUsPhoneCall(){
        contact_us_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder callDialog = new AlertDialog.Builder(MainActivity.this);
                callDialog.setTitle("Contact Us For Detail");
                callDialog.setMessage("Make a Call To 00923350388888");
                callDialog.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent call = new Intent(Intent.ACTION_CALL);
                        call.setData(Uri.parse("tel:" + "00923350388888"));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        dialog.dismiss();
                        startActivity(call);


                    }
                });

                callDialog.setNegativeButton("Later", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                callDialog.show();


            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        inAppBilling_onDestroy();

    }


    private static final char[] payloadSymbols = new char[36];

    static {
        for (int idx = 0; idx < 10; ++idx)
            payloadSymbols[idx] = (char) ('0' + idx);
        for (int idx = 10; idx < 36; ++idx)
            payloadSymbols[idx] = (char) ('a' + idx - 10);
    }


//
//inappbilling start
//




    public void inAppBilling_onDestroy()
    {

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }
        if (mHelper != null) try {
            mHelper.dispose();
            mHelper.disposeWhenFinished();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }





    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            android.util.Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error purchasing: " + result);
                setWaitScreen(false);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error purchasing. Authenticity verification failed.");
                setWaitScreen(false);
                return;
            }

            android.util.Log.d(TAG, "Purchase successful.");

            //*********************************** Method for mobils *******************
            //***************mobile 1*****************
           if (mobileNumber == 1) {
               if (purchase.getSku().equals(SKU_SGCOREPRIME)) {
                   // bought 1/4 tank of gas. So consume it.
                   android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                   try {
                       mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                   } catch (IabHelper.IabAsyncInProgressException e) {
                       complain("Error consuming Goggle. Another async operation in progress.");
                       setWaitScreen(false);
                       return;
                   }
               }
           }//end of mobile 1

            //***************mobile 2*****************
            if (mobileNumber == 2) {
                if (purchase.getSku().equals(SKU_SGGRANDNEO)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 2

            //***************mobile 3*****************
            if (mobileNumber == 3) {
                if (purchase.getSku().equals(SKU_SGGRANDPRIMEPLUS)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 3

            //***************mobile 4*****************
            if (mobileNumber == 4) {
                if (purchase.getSku().equals(SKU_SGJ1)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 4

            //***************mobile 5*****************
            if (mobileNumber == 5) {
                if (purchase.getSku().equals(SKU_SGJ1ACE)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 5

            //***************mobile 6*****************
            if (mobileNumber == 6) {
                if (purchase.getSku().equals(SKU_SGJ1MINIPRIME)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 6

            //***************mobile 7*****************
            if (mobileNumber == 7) {
                if (purchase.getSku().equals(SKU_SGJ2)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 7

            //***************mobile 8*****************
            if (mobileNumber == 8) {
                if (purchase.getSku().equals(SKU_SGJ2PRIMEPLUS)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 8

            //***************mobile 9*****************
            if (mobileNumber == 9) {
                if (purchase.getSku().equals(SKU_STAR2)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 9

            //***************mobile 10*****************
            if (mobileNumber == 10) {
                if (purchase.getSku().equals(SKU_SGSTARS5282)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 10

            //***************mobile 11*****************
            if (mobileNumber == 11) {

                if (purchase.getSku().equals(SKU_SGA3)) {
                    // bought 1/4 tank of gas. So consume it.
                    android.util.Log.d(TAG, "Purchase is Star Goggle. Starting Goggle consumption.");
                    try {
                        mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming Goggle. Another async operation in progress.");
                        setWaitScreen(false);
                        return;
                    }
                }
            }//end of mobile 11

            //********************************************* Ranglerz Team **********************************

        }
    };



    @Override
    public void receivedBroadcast() {
// Received a broadcast notification that the inventory of items has changed
        android.util.Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            android.util.Log.e(TAG, "Error querying inventory. Another async operation in progress.");
        }
    }




    public class RandomString {

       /*
        * static { for (int idx = 0; idx < 10; ++idx) symbols[idx] = (char)
        * ('0' + idx); for (int idx = 10; idx < 36; ++idx) symbols[idx] =
        * (char) ('a' + idx - 10); }
        */


        private final Random random = new Random();

        private final char[] buf;

        public RandomString(int length) {
            if (length < 1)
                throw new IllegalArgumentException("length < 1: " + length);
            buf = new char[length];
        }

        public String nextString() {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = payloadSymbols[random.nextInt(payloadSymbols.length)];
            return new String(buf);
        }

    }

    public final class SessionIdentifierGenerator {

        private SecureRandom random = new SecureRandom();

        public String nextSessionId() {
            return new BigInteger(130, random).toString(32);
        }

    }


    public void keyInitialization(){
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAsqtYVpd7wE9wuSI3oM/qhhYb3fhK4/6ZgBim3ZHMw2sjmnGvtiGc1zIk/+ocQeUyi6/mO9J/Td+pnPv1x66JmlvHUGQozH5/xtipMl4uXMGdG1Jp2nbKaRTHXccFKiJePwf5b/B3RgemG1EWN/H5fqwCSxCxKA2ROIbh5hZg9/HQxZWfmUQuCR4ZC4fQA9oF9ZT5TrqR70oKWqBAZxzktxkt8W0AuIrJWyJDHnZ4iaiHfedSaAjksyVV6rvdhRlc9KJ5i4ksQmFmfjbwVJkf0+n/R5tSul4D5SLZVcnmqqEXl7kAKVClg4ieK+KCrpjUituv42NHf/ZOZph1eVVAGQIDAQAB";

        // Some sanity checks to see if the developer (that's you!) really followed the
        // instructions to run this sample (don't put these checks on your app!)
        if (base64EncodedPublicKey.contains("CONSTRUCT_YOUR")) {
            throw new RuntimeException("Please put your app's public key in MainActivity.java. See README.");
        }
        if (getPackageName().startsWith("com.example")) {
            throw new RuntimeException("Please change the sample's package name! See README.");
        }

        // Create the helper, passing it our context and the public key to verify signatures with
        android.util.Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);



    }//end of keyInitialization

    public void startUpLab(){
        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        android.util.Log.d(TAG, "Starting setup.");

        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                android.util.Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Problem setting up in-app billing: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                mBroadcastReceiver = new IabBroadcastReceiver(MainActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                android.util.Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error querying inventory. Another async operation in progress.");
                }
            }
        });


    }//end of startup lab


    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            android.util.Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                complain("Failed to query inventory: " + result);
                return;
            }

            android.util.Log.d(TAG, "Query inventory was successful.");

            // Check for gas delivery -- if we own gas, we should fill up the tank immediately

            //************************************** for Mobile Perchase ********************************

            //************for Mobile 1 perchase*********************
            if (mobileNumber==1){
            Purchase gasPurchase = inventory.getPurchase(SKU_SGCOREPRIME);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGCOREPRIME), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
                }//end for Mobile 1 perchase

            //************for Mobile 2 perchase*********************
            if (mobileNumber==2){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGGRANDNEO);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGGRANDNEO), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 2 perchase

            //************for Mobile 3 perchase*********************
            if (mobileNumber==3){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGGRANDPRIMEPLUS);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGGRANDPRIMEPLUS), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 3 perchase

//************for Mobile 4 perchase*********************
            if (mobileNumber==4){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGJ1);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGJ1), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 4 perchase

            //************for Mobile 5 perchase*********************
            if (mobileNumber==5){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGJ1ACE);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGJ1ACE), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 5 perchase

            //************for Mobile 6 perchase*********************
            if (mobileNumber==6){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGJ1MINIPRIME);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGJ1MINIPRIME), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 6 perchase

            //************for Mobile 7 perchase*********************
            if (mobileNumber==7){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGJ2);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGJ2), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 7 perchase

            //************for Mobile 8 perchase*********************
            if (mobileNumber==8){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGJ2PRIMEPLUS);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGJ2PRIMEPLUS), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 8 perchase

            //************for Mobile 9 perchase*********************
            if (mobileNumber==9){
                Purchase gasPurchase = inventory.getPurchase(SKU_STAR2);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_STAR2), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 9 perchase

            //************for Mobile 10 perchase*********************
            if (mobileNumber==10){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGSTARS5282);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGSTARS5282), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 10 perchase

//************for Mobile 11 perchase*********************
            if (mobileNumber==11){
                Purchase gasPurchase = inventory.getPurchase(SKU_SGA3);
                android.util.Log.d(TAG, "Inventory Purchase " + gasPurchase);

                if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
                    android.util.Log.d(TAG, "We have gas. Consuming it.");
                    try {
                        mHelper.consumeAsync(inventory.getPurchase(SKU_SGA3), mConsumeFinishedListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        complain("Error consuming gas. Another async operation in progress.");
                    }
                    return;
                }
            }//end for Mobile 11 perchase



            //************************************** end for Mobile Perchase ********************************

            setWaitScreen(false);
            android.util.Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };

    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            android.util.Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                android.util.Log.d(TAG, "Consumption successful. Provisioning.");
                //mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
                //saveData();
                alert("You have successfully bought Mobile Phone");

                onSuccessMobileBought();
                DialogForCustomerInfo();



                //alert("You filled 1/4 tank. Your tank is now " + String.valueOf(mTank) + "/4 full!");
            }
            else {
                complain("Error while consuming: " + result);
            }
            //updateUi();

            setWaitScreen(false);
            android.util.Log.d(TAG, "End consumption flow.");
        }
    };


    void complain(String message) {
        android.util.Log.e(TAG, "**** TrivialDrive Error: " + message);
        alert("Error: " + message);
    }


    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        android.util.Log.d(TAG, "Showing alert dialog: " + message);
        bld.setCancelable(false);
        bld.create().show();
    }


    //************************************ Starts for methods for buy Mobile x ************************************

   //buy mobile 1
    public void buyMobile1(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGCOREPRIME, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile1


    //buy mobile 2
    public void buyMobile2(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGGRANDNEO, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile2

    //buy mobile 3
    public void buyMobile3(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGGRANDPRIMEPLUS, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile3

    //buy mobile 4
    public void buyMobile4(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGJ1, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile4

    //buy mobile 5
    public void buyMobile5(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGJ1ACE, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile5

    //buy mobile 6
    public void buyMobile6(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGJ1MINIPRIME, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile6

    //buy mobile 7
    public void buyMobile7(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGJ2, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile7

    //buy mobile 8
    public void buyMobile8(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGJ2PRIMEPLUS, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile8

    //buy mobile 9
    public void buyMobile9(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_STAR2, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile9

    //buy mobile 10
    public void buyMobile10(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGSTARS5282, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile10

    //buy mobile 11
    public void buyMobile11(){
        if (mSubscribedToInfiniteGas) {
            complain("No need! You're subscribed to infinite gas. Isn't that awesome?");
            return;
        }


        // launch the gas purchase UI flow.
        // We will be notified of completion via mPurchaseFinishedListener
        setWaitScreen(true);
        android.util.Log.d(TAG, "Launching purchase flow for gas.");

        /* TODO: for security, generate your payload here for verification. See the comments on
         *        verifyDeveloperPayload() for more info. Since this is a SAMPLE, we just use
         *        an empty string, but on a production app you should carefully generate this. */
        String payload = "";
        android.util.Log.e("testTag", "Buy Call");
        RandomString randomString = new RandomString(36);
        devPayLoad = randomString.nextString();
        payload = devPayLoad;

        try {
            mHelper.launchPurchaseFlow(this, SKU_SGA3, RC_REQUEST,
                    mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error launching purchase flow. Another async operation in progress.");
            setWaitScreen(false);
        }
    }//end of buyMobile11

//********************************** End for methods Buy mobiles *********************************


    // Enables or disables the "please wait" screen.
    void setWaitScreen(boolean set) {

        Toast.makeText(getApplicationContext(), "Please Wait...", Toast.LENGTH_SHORT).show();

		/*findViewById(R.id.screen_main).setVisibility(set ? View.GONE : View.VISIBLE);
		findViewById(R.id.screen_wait).setVisibility(set ? View.VISIBLE : View.GONE);*/
    }

    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }




    @Override
    protected void onResume() {
        super.onResume();

    }

    //hinding views when bought invertory successfull
    public void onSuccessMobileBought(){
        sharedPreferences = getSharedPreferences("FORSELLMOBILES", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //start
        if (mobileNumber==1){
            //additing values to sharepreferences
            editor.putInt("MOBILE1", 1);//int value 1 is for stargoggle

            galaxy_core_prime.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==2){
            //additing values to sharepreferences
            editor.putInt("MOBILE2", 2);//int value 1 is for stargoggle

            galaxy_grand_neo.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==3){
            //additing values to sharepreferences
            editor.putInt("MOBILE3", 3);//int value 1 is for stargoggle
            galaxy_grand_prime_plus.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==4){
            //additing values to sharepreferences
            editor.putInt("MOBILE4", 4);//int value 1 is for stargoggle
            galaxy_j1.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==5){
            //additing values to sharepreferences
            editor.putInt("MOBILE5", 5);//int value 1 is for stargoggle
            galaxy_j1_ace.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==6){
            //additing values to sharepreferences
            editor.putInt("MOBILE6", 6);//int value 1 is for stargoggle
            galaxy_j1_mini.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==7){
            //additing values to sharepreferences
            editor.putInt("MOBILE7", 7);//int value 1 is for stargoggle
            galaxy_j2.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==8){
            //additing values to sharepreferences
            editor.putInt("MOBILE8", 8);//int value 1 is for stargoggle
            galaxy_j2_prime_plus.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==9){
            //additing values to sharepreferences
            editor.putInt("MOBILE9", 9);//int value 1 is for stargoggle
            galaxy_star2.setVisibility(View.GONE);
        }//end
//start
        if (mobileNumber==10){
            //additing values to sharepreferences
            editor.putInt("MOBILE10", 10);//int value 1 is for stargoggle
            galaxy_star_s5282.setVisibility(View.GONE);
        }//end

        //start
        if (mobileNumber==11){
            //additing values to sharepreferences
            editor.putInt("MOBILE11", 11);//int value 1 is for stargoggle
            galaxy_a3.setVisibility(View.GONE);
        }//end


        editor.commit();

        ifAllMobileBought();


    }//end of on Successfully mobile bought

    public void loadData(){
        sharedPreferences = getSharedPreferences("FORSELLMOBILES", 0);

        if(sharedPreferences!=null) {
            int boughtValue1 = sharedPreferences.getInt("MOBILE1", 0);//defualt values is zero for all
            int boughtValue2 = sharedPreferences.getInt("MOBILE2", 0);
            int boughtValue3 = sharedPreferences.getInt("MOBILE3", 0);
            int boughtValue4 = sharedPreferences.getInt("MOBILE4", 0);
            int boughtValue5 = sharedPreferences.getInt("MOBILE5", 0);
            int boughtValue6 = sharedPreferences.getInt("MOBILE6", 0);
            int boughtValue7 = sharedPreferences.getInt("MOBILE7", 0);
            int boughtValue8 = sharedPreferences.getInt("MOBILE8", 0);
            int boughtValue9 = sharedPreferences.getInt("MOBILE9", 0);
            int boughtValue10 = sharedPreferences.getInt("MOBILE10", 0);
            int boughtValue11 = sharedPreferences.getInt("MOBILE11", 0);


            if (boughtValue1==1){
                galaxy_core_prime.setVisibility(View.GONE);
            }
            if (boughtValue2==2){
                galaxy_grand_neo.setVisibility(View.GONE);
            }
            if (boughtValue3==3){
                galaxy_grand_prime_plus.setVisibility(View.GONE);
            }
            if (boughtValue4==4){
                galaxy_j1.setVisibility(View.GONE);
            }
            if (boughtValue5==5){
                galaxy_j1_ace.setVisibility(View.GONE);
            }
            if (boughtValue6==6){
                galaxy_j1_mini.setVisibility(View.GONE);
            }
            if (boughtValue7==7){
                galaxy_j2.setVisibility(View.GONE);
            }
            if (boughtValue8==8){
                galaxy_j2_prime_plus.setVisibility(View.GONE);
            }
            if (boughtValue9==9){
                galaxy_star2.setVisibility(View.GONE);
            }
            if (boughtValue10==10){
                galaxy_star_s5282.setVisibility(View.GONE);
            }

            if (boughtValue11==11){
                galaxy_a3.setVisibility(View.GONE);
            }

        }

        ifAllMobileBought();
    }

    public void ifAllMobileBought(){


        if (galaxy_grand_prime_plus.getVisibility()==View.GONE
                && galaxy_j2_prime_plus.getVisibility()==View.GONE
                && galaxy_j2.getVisibility()==View.GONE
                && galaxy_grand_neo.getVisibility()==View.GONE
                && galaxy_j1.getVisibility()==View.GONE
                && galaxy_j1_ace.getVisibility()==View.GONE
                && galaxy_j1_mini.getVisibility()==View.GONE
                && galaxy_core_prime.getVisibility()==View.GONE
                && galaxy_star2.getVisibility()==View.GONE
                && galaxy_star_s5282.getVisibility()==View.GONE
                && galaxy_a3.getVisibility()==View.GONE)
        {


            Toast.makeText(this, "You Have Bought All Mobiles", Toast.LENGTH_SHORT).show();
            TextView allMobileBought = (TextView)findViewById(R.id.tv_all_bought);
            allMobileBought.setVisibility(View.VISIBLE);

        }
    }

    public void sendMailOnPurchaseSucess(){
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"aratel1979@gmail.com"});
        if (mobileNumber==1) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy Core Prime"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==2) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy Grand Neo"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==3) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy Grand Prime Plus"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==4) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy J1"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==5) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy J1 ACE"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==6) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy J1 Mini Prime");
        }

        if (mobileNumber==7) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy J2"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==8) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy J2 Prime Plus"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==9) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy Star 2"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==10) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy Star S5282"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

        if (mobileNumber==11) {
            i.putExtra(Intent.EXTRA_SUBJECT, "Order From User");
            i.putExtra(Intent.EXTRA_TEXT, "Samsung Galaxy A3"
                    + "\n" + "Customer Mobile Number = "
                    + cutomer_phn + "\n" + "Customer Address = " + cutomer_adrs);
        }

            try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void DialogForCustomerInfo(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.customer_info_layout);
        dialog.setTitle("Delivery Information");
       final EditText customer_mobile_number = (EditText)dialog.findViewById(R.id.ed_customer_phon);
        final EditText customer_address = (EditText)dialog.findViewById(R.id.cutomer_address);
        Button bt_ok = (Button)dialog.findViewById(R.id.dialog_ok);



        bt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (customer_mobile_number.getText().length()==0){
                    Toast.makeText(MainActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else if(customer_address.getText().length()<9){
                    Toast.makeText(MainActivity.this, "Please Enter Valid Phone Number", Toast.LENGTH_SHORT).show();
                }
                else if (customer_address.getText().length()==0){
                    Toast.makeText(MainActivity.this, "Pleas Enter Delivery Address", Toast.LENGTH_SHORT).show();
                }else {

                    cutomer_phn = customer_mobile_number.getText().toString().trim();
                    cutomer_adrs = customer_address.getText().toString();
                    dialog.dismiss();
                    sendMailOnPurchaseSucess();
                }

            }
        });

        dialog.setCancelable(false);
        dialog.show();
    }

    public void showSingleImageDialog(){

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setContentView(R.layout.single_image_view_dialog);


        ImageView singleImageView = (ImageView) dialog.findViewById(R.id.single_image_view);
        TextView mobileColor = (TextView)dialog.findViewById(R.id.mobile_color);
        TextView mobilePrice = (TextView)dialog.findViewById(R.id.mobile_price);
        Button buyNow = (Button) dialog.findViewById(R.id.buy_this_mobile);
        Button buyLater = (Button) dialog.findViewById(R.id.buy_later);


        //start if for mobile1
        if (mobileNumber==1){
            dialog.setTitle("Samung Galaxy Core Prime");
            mobileColor.setText("Mobile Color: Silver Gray");
            mobilePrice.setText("Mobile Price: 124.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.core_prime);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile1();

                }
            });
        }//end of if for mobile1


        //start if for mobile2
        if (mobileNumber==2){
            dialog.setTitle("Samung Galaxy Grand Neo");
            mobileColor.setText("Mobile Color: White");
            mobilePrice.setText("Mobile Price: 144.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.samsung_galaxy_grand_neo);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile2();

                }
            });
        }//end of if for mobile2

//start if for mobile3
        if (mobileNumber==3){
            dialog.setTitle("Samung Galaxy Grand Prime Plus");
            mobileColor.setText("Mobile Color: Golden");
            mobilePrice.setText("Mobile Price: 154.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.grand_prime_plus);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile3();

                }
            });
        }//end of if for mobile3

//start if for mobile4
        if (mobileNumber==4){
            dialog.setTitle("Samung Galaxy J1");
            mobileColor.setText("Mobile Color: Black And Golden");
            mobilePrice.setText("Mobile Price: 144.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.samsung_galaxy_j1);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile4();

                }
            });
        }//end of if for mobile4

//start if for mobile5
        if (mobileNumber==5){
            dialog.setTitle("Samung Galaxy J1 ACE");
            mobileColor.setText("Mobile Color: Sky Blue");
            mobilePrice.setText("Mobile Price: 109.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.samsung_galaxy_j1_ace);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile5();

                }
            });
        }//end of if for mobile5

//start if for mobile6
        if (mobileNumber==6){
            dialog.setTitle("Samung Galaxy J1 Mini Prime");
            mobileColor.setText("Mobile Color: White");
            mobilePrice.setText("Mobile Price: 94.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.sg_j1_mini_prime);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile6();

                }
            });
        }//end of if for mobile6

//start if for mobile7
        if (mobileNumber==7){
            dialog.setTitle("Samung Galaxy J2");
            mobileColor.setText("Mobile Color: Black");
            mobilePrice.setText("Mobile Price: 144.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.samsung_galxy_j2);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile7();

                }
            });
        }//end of if for mobile7

//start if for mobile8
        if (mobileNumber==8){
            dialog.setTitle("Samung Galaxy J2 Prime Plus");
            mobileColor.setText("Mobile Color: Shining Golden");
            mobilePrice.setText("Mobile Price: 154.99$"  + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.samsung_galaxy_grand_prime_j2);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile8();

                }
            });
        }//end of if for mobile8

//start if for mobile8
        if (mobileNumber==9){
            dialog.setTitle("Samung Galaxy Star 2");
            mobileColor.setText("Mobile Color: White");
            mobilePrice.setText("Mobile Price: 64.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.star_2);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile9();

                }
            });
        }//end of if for mobile9

//start if for mobile10
        if (mobileNumber==10){
            dialog.setTitle("Samung Galaxy Star S5282");
            mobileColor.setText("Mobile Color: White");
            mobilePrice.setText("Mobile Price: 54.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.star_s5282);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile10();

                }
            });
        }//end of if for mobile10

//start if for mobile11
        if (mobileNumber==11){
            dialog.setTitle("Samung Galaxy A3");
            mobileColor.setText("Mobile Color: Silver White");
            mobilePrice.setText("Mobile Price: 280.99$ " + BILLING_CHARGES);
            singleImageView.setImageResource(R.drawable.sga3);

            buyLater.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            buyNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    buyMobile11();

                }
            });
        }//end of if for mobile11



        dialog.setCancelable(true);
        dialog.show();

    }

    public void getVisibiliteyForMobiles(){
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                int x = v.getScrollX();
                int y = v.getScrollY();

                scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
                    @Override
                    public void onScrollChanged() {
                        if (scrollView != null) {
                            View view = scrollView.getChildAt(scrollView.getChildCount()-1);
                            int startDiff = ((view.getLeft()+scrollView.getPaddingLeft())-(scrollView.getWidth()+scrollView.getScrollX()));
                            int diff = ((view.getRight()+scrollView.getPaddingRight())-(scrollView.getWidth()+scrollView.getScrollX()));

                            // if diff is zero, then the bottom has been reached
                            Log.v("TAG", "SSSSSSSS: "  + diff);
                            if (diff == 0) {
                                rightArrow.setVisibility(View.GONE);
                            }
                            if (diff!=0){
                                rightArrow.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });


                if (x==0){
                    leftArrow.setVisibility(View.GONE);
                }
                if (x!=0){
                    leftArrow.setVisibility(View.VISIBLE);
                }




                Log.v("TAG", "THE VALUES x IS: "  + x + " AND  for y : " + y);
                return false;
            }
        });

    }

}