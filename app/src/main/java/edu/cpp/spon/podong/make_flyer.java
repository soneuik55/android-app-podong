package edu.cpp.spon.podong;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class make_flyer extends AppCompatActivity {


    private EditText nameEditText;
    private EditText rewardEditText;
    private EditText breedEditText;
    private EditText ageEditText;
    private EditText contactEditText;
    private EditText dayEditText;

    final int PICK_IMAGE= 1;
    Uri imageUri;
    ImageView imageView;

    View main;
    File  imagePath ;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_flyer);



        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();


        nameEditText =(EditText)findViewById(R.id.name_edit);
        rewardEditText =(EditText)findViewById(R.id.reward_edit);;
        breedEditText =(EditText)findViewById(R.id.breed_edit);
        ageEditText =(EditText)findViewById(R.id.age_edit);
        contactEditText =(EditText)findViewById(R.id.contact_edit);
        dayEditText =(EditText)findViewById(R.id.day_edit);


        imageView = (ImageView)findViewById(R.id.image_Insert);
        Button LoadImage = (Button) findViewById(R.id.upload_image);
        Button share_Button = (Button)findViewById(R.id.share_Button);

        main = findViewById(R.id.content);
        LoadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
              openGallery();

            }
        });



        share_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                ActivityCompat.requestPermissions(make_flyer.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);

                savingUserInputs();

                Bitmap bitmap = takeScreenshot();
                sharePic(bitmap);
                onShareOnePhoto();

            }
        });

    }


    private void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

        }


        //when a picture is selected, the method will be called
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);
        }
    }



        // Methods to share an image
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }


    private void sharePic(Bitmap bitmap){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("image/jpeg");
        try{
            File cache = getApplicationContext().getExternalCacheDir();
            imagePath = new File(cache,"example.png");
            FileOutputStream out = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,out);
            out.flush();
            out.close();
            shareIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+imagePath));
            startActivity(Intent.createChooser(shareIntent,"Share"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private void onShareOnePhoto() {
        Uri path = FileProvider.getUriForFile( make_flyer.this, "edu.cpp.spon.podong", imagePath);

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "This is one image I'm sharing.");
        shareIntent.putExtra(Intent.EXTRA_STREAM, path);
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        shareIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent, "Share..."));
    }



    //store user inputs into edit box
    private void savingUserInputs(){
       String name  =nameEditText.getText().toString();
        nameEditText.setText(name);

        String reward =rewardEditText.getText().toString();
        rewardEditText.setText(reward);

        String breed  =breedEditText.getText().toString();
        breedEditText.setText(breed);

        String age = ageEditText.getText().toString();
        ageEditText.setText(age);

        String contact  = contactEditText.getText().toString();
        contactEditText.setText(contact);

        String day  =dayEditText.getText().toString();
        dayEditText.setText(day);








    }


}
