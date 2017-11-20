package com.nicapp.EphemerisNA;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.analytics.FirebaseAnalytics;
public class MainActivity extends AppCompatActivity {

    private WebView mWebView;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//  Firebase Analytics
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//  Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
        MobileAds.initialize(this, "ca-app-pub-8256835306478664~8780942222");
//  AdMob Banner
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
//  AdMob Interstitial
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-8256835306478664/7919109080");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
//  Prepare an Interstitial Ad Listener
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
//  Call displayInterstitial() function
                displayInterstitial();
            }
        });
//  WebView
        mWebView = findViewById(R.id.activity_main_webview);
//  Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//
        mWebView.loadUrl("https://ephemeris.mx/");
//  Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());
//
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "MY_ITEM_ID-NA");
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "MY_ITEM_NAME-NA");
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "MY_CONTENT_TYPE-NA");
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }
//
    public void displayInterstitial() {
//  If Ads are loaded, show Interstitial else show nothing.
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }
//
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}

