package com.example.cmera;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static android.graphics.BitmapFactory.decodeByteArray;


public class Main2Activity extends MainActivity {
    private Bitmap imagemT;
    private ImageView imagemRV;
    private ImageView imagemGV;
    private ImageView imagemBV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        imagemRV = (ImageView) findViewById(R.id.imagemRL);
        imagemGV = (ImageView) findViewById(R.id.imagemGL);
        imagemBV = (ImageView) findViewById(R.id.imagemBL);

        // setImagemView();
        testeimagem();
       // teste(testeimagemB());
    }


    public void testeimagem() {
        Log.i("Bruna", "ENTROU:TESTE IMAGEMB ");

        //  imagemT= BitmapFactory.decodeResource(this.getResources(),R.drawable.galeria_background);
        File f = new File("/storage/emulated/0/Camera2_Arquivos/imagemt.jpg");


        Options options = new Options();
        options.inSampleSize = 8;
        imagemT = BitmapFactory.decodeFile(f.getAbsolutePath(), options);

        /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*/
        /**TESTE MÉTODO 2 */
    /*
        int colour = imagemT.getPixel(0, 0);
        int red = Color.red(colour);
        int blue = Color.blue(colour);
        int green = Color.green(colour);
        Log.i("Bruna", "R:"+ red + "G: "+green+ "B: "+blue);*/

        /***TESTE MÉTODO 1 */

       /* int a = (colour>>24) & 0xff;
        int r = (colour>>16) & 0xff;
        int g = (colour>>8) & 0xff;
        int b = colour & 0xff;
        Log.i("Bruna", "R:"+ r + "G: "+ g + "B: "+b);
        */
        /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*/

        int larg = imagemT.getWidth();
        int alt = imagemT.getHeight();
        int totalPixels = larg * alt;
        int totalBytes = totalPixels * 4;
        Log.i("Bruna", "lar: " + imagemT.getWidth() + "alt: " + imagemT.getHeight());
        Log.i("Bruna", "TP: " + totalPixels);
        Log.i("Bruna", "TB: " + totalBytes);

        byte[] matrizBy = new byte[totalBytes];//

        int[] matrizPx = new int[totalPixels];//

        int[] matrizPxR= new int[totalPixels];
        int[] matrizPxG= new int[totalPixels];
        int[] matrizPxB= new int[totalPixels];

        // Log.i("Bruna","MatrizPixels: "+ matrizPx);
        //   Log.i("Bruna","MatrizBytes : "+ matrizBy);

           for (int i = 0; i < totalPixels; i++) {

            int pixel = matrizPx[i];

            int alpha = Color.red(pixel);
            int red2 = Color.red(pixel);
            int green2 = Color.green(pixel);
            int blue2 = Color.blue(pixel);
         //   Log.i("Bruna", "RGB: " + red2+", "+ green2+", "+blue2);

              byte[] anIntR = {(byte)alpha,(byte)red2,(byte)0,(byte)0};
              int result = (int) ( anIntR[0]<<24 | ( (anIntR[1]<<24)>>>8 ) | ( (anIntR[2]<<24)>>>16) | ( (anIntR[3]<<24)>>>24) );
              matrizPxR[i]=result;

               byte[] anIntG = {(byte)alpha,(byte)0,(byte)green2,(byte)0};
               int result1 = (int) ( anIntG[0]<<24 | ( (anIntG[1]<<24)>>>8 ) | ( (anIntG[2]<<24)>>>16) | ( (anIntG[3]<<24)>>>24) );
               matrizPxG[i]=result1;

               byte[] anIntB = {(byte)alpha,(byte)0,(byte)0,(byte)blue2};
               int result2 = (int) ( anIntB[0]<<24 | ( (anIntB[1]<<24)>>>8 ) | ( (anIntB[2]<<24)>>>16) | ( (anIntB[3]<<24)>>>24) );
               matrizPxB[i]=result2;
             //  Log.i("Bruna", "teste: " + anInt);
               }


        Bitmap imR = Bitmap.createBitmap(imagemT.getWidth(), imagemT.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(imR);
        c.drawBitmap(imagemT, 0, 0, new Paint());
        imR.setPixels(matrizPxR, 0, imR.getWidth(), 0, 0, larg, imR.getHeight());
      //  imagemRV.setImageBitmap(imR);

        Bitmap imG = Bitmap.createBitmap(imagemT.getWidth(), imagemT.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c1 = new Canvas(imG);
        c1.drawBitmap(imagemT, 0, 0, new Paint());
        imG.setPixels(matrizPxG, 0, imG.getWidth(), 0, 0, larg, imG.getHeight());
       // imagemGV.setImageBitmap(imB);

        Bitmap imB = Bitmap.createBitmap(imagemT.getWidth(), imagemT.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c2 = new Canvas(imB);
        c2.drawBitmap(imagemT, 0, 0, new Paint());
        imR.setPixels(matrizPxB, 0, imB.getWidth(), 0, 0, imB.getWidth(), imB.getHeight());
        imagemBV.setImageBitmap(imB);
        imagemGV.setImageBitmap(imB);
        imagemRV.setImageBitmap(imB);

         /* c.drawARGB(0,255,0,0);
        imagemRV.setImageBitmap(imt);
        c.drawARGB(0,0,255,0);
        imagemGV.setImageBitmap(imt);
        c.drawARGB(0,0,0,255);
        imagemBV.setImageBitmap(imt);*/
        //   Log.i("Bruna", "novaMatrizPx: " + novaMatrizPx);
        //  Log.i("Bruna","MatrizPixels: "+ matrizPx);



    }
    public void setaIm(int[] mp){


        Bitmap newBmp = Bitmap.createBitmap(imagemT.getWidth(), imagemT.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(newBmp);
        c.drawBitmap(imagemT, 0, 0, new Paint());
      //  newBmp.setPixels(mp, 0, newBmp.getWidth(), 0, 0, newBmp.getWidth(), newBmp.getHeight());
        newBmp.setPixels(mp, 0, 300, 0, 0, 300, imagemT.getHeight());
        imagemRV.setImageBitmap(imagemT);
        imagemBV.setImageBitmap(newBmp);
        Log.i("Bruna", "lar: " + newBmp.getWidth() + "alt: " + newBmp.getHeight());
        Log.i("Bruna", "lar: " + imagemT.getWidth() + "alt: " + imagemT.getHeight());
    }
}
        //Bitmap btn= imagemT.setPixels( novaMatrizPx, 0,imagemT.getWidth(), 0, 0, imagemT.getWidth(), imagemT.getHeight());
     //    return novaMatrizPx;
       // imagemT.setPixels( novaMatrizPx, 0,imagemT.getWidth(), 0, 0, imagemT.getWidth(), imagemT.getHeight());



       // imagemT.setPixels( novaMatrizPx, 0,imagemT.getWidth(), 0, 0, imagemT.getWidth(), imagemT.getHeight());
           /*

*/

        //imagemT.setPixels( p, imagemT.getWidth(), 0, 0, imagemT.getWidth(), imagemT.getHeight());
        // imagemRV.setImageBitmap(imagemT);

        // imagemT.setPixels(green2, 0, imagemT.getWidth(), 0, 0, imagemT.getWidth(), imagemT.getHeight());
        //  imagemGV.setImageBitmap(imagemT);

        //  imagemT.setPixels(blue2, 0, imagemT.getWidth(), 0, 0, imagemT.getWidth(), imagemT.getHeight());
        //  imagemBV.setImageBitmap(imagemT);


//c.drawARGB(0,red2,0,0);


                /* matrizPxR[i]= Color.alpha(pixel)+ Color.red(pixel)+Color.green(0)+Color.red(0);
                   matrizPxG[i]=Color.alpha(pixel)+ Color.red(0)+Color.green(pixel)+Color.red(0);
                   matrizPxB[i]=Color.alpha(pixel)+ Color.red(0)+Color.green(0)+Color.red(pixel);*/

             /*  matrizBy[i * (3) ] = (byte) red2;
               matrizBy[i * (3) + 1] = (byte) green2;
               matrizBy[i * (3) + 2] = (byte) blue2;*/






/**TESTE DOIS PARA ARMAZENAMENTO CARTÃO SD*/

 /*  imageT imagem;
    File fileImagem;
    private File uriImagem;*/
/* void createExternalStoragePrivateFile() {

        File pasta2 = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "/Camera2_Arquivos/");
        Log.i("Bruna","Criou PAsta privada; ");
    }
    /***=*=*=*=*=*==*=*= MODo 2 *=*=*=*=*=*=*=*=*=*=**/
  /*  public void criarUri(){
        Log.i("Bruna","Entrou: CriarUri();");
        File pastaArquivos = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES) + "/Arquivos_Camera2/");
       // fileImagem = pastaArquivos;
        uriImagem= pastaArquivos;
        Log.i("Bruna","Entrou: CriarUri();"+ uriImagem);

    }
    public void abrirCamera(){
        criarUri();
        Log.i("Bruna","Entrou: AbrirCamera();");
        Intent intentCamera= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT,uriImagem );
        startActivityForResult(intentCamera, 1);
        Log.i("Bruna","URI imagem: "+ uriImagem);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //CONVERTE RESULTADOS

            Bundle extras = data.getExtras();
            imageT formatImagem = (Bitmap) extras.get("data");
           // imagem.setImageBitmap(formatImagem);
            Log.i("Bruna", "Imagem convertida "+formatImagem);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }*/
/***=*=*=*=*=*=*==*=*=* FIM MODO 2*=*=*=*=*=*=*=*=*=*=*=*/