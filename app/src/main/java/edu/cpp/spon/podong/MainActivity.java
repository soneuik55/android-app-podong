package edu.cpp.spon.podong;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;


public class MainActivity extends  AppCompatActivity {


    private static final String TAG = "googlemap";
    private static final int  ERROR_DIALOG_RQUEST =9001;



    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureNextButton();
        configureNextButton2();
        configureNextButton3();
        configureNextButton4();
        configureNextButton5();
        if(isServiceOk()){
            init();
        }
    }



    private void init(){
        Button btnMap = (Button) findViewById(R.id.map_Button);
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);

            }

        });
    }


    public boolean isServiceOk(){
        Log.d(TAG, "isService ok: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){

            Log.d(TAG, "isSErvice ok: google play service is working");

        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isserviceok: error, but we can solve");
        }
        else{
            Toast.makeText(this, "you can't make map request ", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    private void configureNextButton(){
        Button nextButton = (Button) findViewById(R.id.chipButton);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

             goToUrl("https://petchip.info/");
            }


        });

    }


    private void configureNextButton2(){
        Button nextButton = (Button) findViewById(R.id.flyer_Button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, make_flyer.class));
            }


        });

    }


    private void configureNextButton3(){
        Button nextButton = (Button) findViewById(R.id.map_Button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, googlemap.class));
            }


        });

    }


    private void configureNextButton4(){
        Button nextButton = (Button) findViewById(R.id.petbreed_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, petbreed.class));
            }


        });

    }

    private void configureNextButton5(){
        Button nextButton = (Button) findViewById(R.id.findShelter_button);
        nextButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                startActivity(new Intent(MainActivity.this, find_shelters.class));
            }


        });

    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

}
