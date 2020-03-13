package com.example.cmera;

import androidx.annotation.MainThread;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ImageView imagem;
    private static final int IMAGE_VIEW_ACTIVITY_REQUEST_CODE   = 101;//para abrir a galeria

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         imagem = (ImageView) findViewById(R.id.imagem1);

         final ImageButton galeria = (ImageButton) findViewById(R.id.btgaleria);

        ImageButton camera = (ImageButton) findViewById(R.id.btcamera);

        //*=*=*=*=*=*=*=**=*=*=*=*=*=*=*=*=ACESSO A GALERIA *=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {

                //Intent intent2 = new Intent( Intent.ACTION_VIEW );
              //  startActivityForResult( intent2, IMAGE_VIEW_ACTIVITY_REQUEST_CODE );
                Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(intentPegaFoto,IMAGE_VIEW_ACTIVITY_REQUEST_CODE);

            }
        });


        /*protected void onActivityResult( int requestCode, int resultCode, @Nullable Intent data1){
        if(resultCode != Activity.RESULT_CANCELED){
                if(requestCode == IMAGE_VIEW_ACTIVITY_REQUEST_CODE){
                    Uri selectedImage = data1.getData();
                    Toast.makeText(getApplicationContext(), selectedImage.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        }*/


//*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= PARTE PARA CAMERA *=*=*=*=**=*=*=*=*=*=*=*=*=*=*=*=*=*=**=*=*=*=*=
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},0);
            // VERIFICA SE O APP POSSUI A PERMISSÃO DE ACESSO A CÂMERA
        }

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });
    }

    public void tirarFoto(){
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }
    @Override
    //*=*=*=*=*=*=*Retorna o resultado da intent*=*=*=*=*=*=*=*=*
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode == RESULT_OK){
            //CONVERTE RESULTADOS
            Bundle extras = data.getExtras();
            Bitmap formatImagem= (Bitmap) extras.get("data");
            imagem.setImageBitmap(formatImagem);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

}
