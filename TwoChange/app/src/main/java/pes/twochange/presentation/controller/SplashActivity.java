package pes.twochange.presentation.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import pes.twochange.R;
import pes.twochange.domain.themes.AuthTheme;
import pes.twochange.presentation.Config;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener, AuthTheme.Response {

    private TranslateAnimation translateAnimation;
    private CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView logo = (ImageView) findViewById(R.id.logo_image);
        cardView = (CardView) findViewById(R.id.card_splash);

        translateAnimation = new TranslateAnimation(0.0f, 0.0f, 1000.0f, 0.0f);
        translateAnimation.setDuration(2000);
        translateAnimation.setAnimationListener(this);
        logo.startAnimation(translateAnimation);
    }



    @Override
    public void onAnimationStart(Animation animation) {
        AuthTheme authTheme = AuthTheme.getInstance();
        authTheme.setSharedPreferences(getSharedPreferences(Config.SP_NAME, MODE_PRIVATE));
        authTheme.setResponse(this);
        authTheme.startListeningFirebaseAuth();
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(3000);
        alphaAnimation.setAnimationListener(
                new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        translateAnimation.setAnimationListener(null);
                        Intent i = new Intent(getApplicationContext(), AuthActivity.class);
                        i.putExtra("startPoint", "LOGIN");
                        startActivity(i);
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                }
        );
        cardView.startAnimation(alphaAnimation);
        cardView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void main() {
        startActivity(new Intent(getApplicationContext(), ExploreActivity.class));
        finish();
    }

    @Override
    public void profile() {
        translateAnimation.setAnimationListener(null);
        Intent i = new Intent(getApplicationContext(), AuthActivity.class);
        i.putExtra("startPoint", "PROFILE");
        startActivity(i);
        finish();
    }

    @Override
    public void noConnection() {
        // TODO Control d'errors
    }

    @Override
    public void startActivity(Intent intent) {
        translateAnimation.setAnimationListener(null);
        super.startActivity(intent);
        finish();
    }
}
