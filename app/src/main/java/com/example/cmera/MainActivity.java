package com.example.cmera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.os.Environment.getExternalStorageState;
import static android.os.Environment.isExternalStorageEmulated;
import static android.os.Environment.isExternalStorageRemovable;

public class MainActivity extends AppCompatActivity {

    private ImageView imagem;
    private static final int IMAGE_VIEW_ACTIVITY_REQUEST_CODE = 101;//para abrir a galeria
    private File arquivoFoto = null;
    private Uri fileUri;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //imagemB = (ImageView) findViewById(R.id.imagemB);

        this.verifcarPermissaoSd();
        this.armazenamentoDisp();
        Log.i("Bruna", "");

        imagem = (ImageView) findViewById(R.id.imagem1);
        final ImageButton galeria = (ImageButton) findViewById(R.id.btgaleria);
        ImageButton camera = (ImageButton) findViewById(R.id.btcamera);


        //*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= BOTÃO TIRAR FOTO *=*=*=*=**=*=*=*=*=*=*=*=*=*=*=*=*/
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }
        });
        //*=*=*=*=*=*=*=**=*=*=*=*=*=*BOTÃO ACESSO A GALERIA *=*=*=*=*=*=*=*=*=*=*=*=*=*=**/

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                abrirGaleria();
            }
        });
        /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*/
    }

    public void tirarFoto() {
        /*Responsavel por abrir a câmera*/
            String a= MediaStore.EXTRA_OUTPUT;

        Log.i("Bruna", "Entrou: tirarFoto();");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, 1);

        Log.i("Bruna", "Intent: ;" + a);
    }

    public void abrirGaleria() {
       // Log.i("Bruna", "Entrou: AbrirGaleria();");
       // Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
       // startActivityForResult(intentPegaFoto, IMAGE_VIEW_ACTIVITY_REQUEST_CODE);
     Intent intentProxTela= new Intent(this,Main2Activity.class);
      startActivity(intentProxTela);
    }

    /**
     * =*=*=*=*=*=*=*=*=*=*=*=*=*=*=**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=
     */
    @Override
    //*=*=*=*=*=*=*Retorna o resultado da intent*=*=*=*=*=*=*=*=*
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //CONVERTE RESULTADOS

            Bundle extras = data.getExtras();
            Bitmap formatImagem = (Bitmap) extras.get("data");
            imagem.setImageBitmap(formatImagem);
            Log.i("Bruna", "Imagem convertida");
            // Log.i("Bruna","Root1: "+ teste);
            this.salvarArquivo(formatImagem);
            Log.i("Bruna", "Intent: ;" + fileUri);
            // this.salvar2();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * =*=*=*=*=*=*=*=*=verifica permissão de acesso ao SD =*=*=*==*=*=*=*=*=*===
     **/
    private void verifcarPermissaoSd() {
        Log.i("Bruna", "Entrou: verificarPermissaoSD();");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //    Log.i("Bruna", "Situação:"));
            }
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }

    /* Verifica se há armazenamento externo disponível para leitura e gravação */
    public boolean armazenamentoDisp() {
        Log.i("Bruna", "Entrou: ArmazenamentoDis();");
        String state = getExternalStorageState();/* OBS*/
        if (Environment.MEDIA_MOUNTED.equals(state)) { /* Estado de armazenamento se a mídia estiver presente, mas não montada*/
            Log.i("Bruna", "Situação:" + getExternalStorageState());
            return true;
        }
        Log.i("Bruna", "Situação:" + getExternalStorageState());
        return false;

    }

    /*SALVAR O ARQUIVO*/
    private void salvarArquivo(Bitmap Bitmap2) {
        Log.i("Bruna", "Entrou: salvarArquivo();");
      /**=*=*=*=*=*=*==*=*=*=*==*TESTES *=*=*=*=*=*=*=*=*=*=*==*=*=*/
        //  final String rawExternalStorage = System.getenv("EXTERNAL_STORAGE");
        //  final String rawEmulatedStorageTarget = System.getenv("EMULATED_STORAGE_TARGET");
        // File diretorio = this.getExternalFilesDir(null);
        // File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), pasta);
        //  String root1 = Environment.DIRECTORY_MOVIES;
        // String rn= MediaStore.;


        //File pastaArquivos = new File(getExternalFilesDir(null) + "/Arquivos_Camera2/");
       //  File pastaArquivos = new File(getExternalFilesDir(null) + "/Camera2_Arquivos/");
          //Log.i("Bruna", "Salvou  arquivo " + pastaArquivos);
      /***=*=*=*=*=*=*=*=*=*=*=*=*==*=*=*=*=*=*=*=*=*=*==*=*=*=*=*=*=*=*=*=*=*/


        String root = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
        File pastaArquivos = new File(root + "/Camera2_Arquivos/");
         pastaArquivos.mkdirs();/*Cria o diretório nomeado por esse caminho abstrato, incluindo os diretórios pai necessários, mas inexistentes*/

        /*nome do arquivo (imagem)*/
        String sobreNome = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nome = "Br_" + sobreNome + ".jpg";

        File file = new File(pastaArquivos, nome);
        if (file.exists()) file.delete();/*ver se o arquivo existe */
        try {
            //  Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

            FileOutputStream salvar = new FileOutputStream(file, true);
            Bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, salvar);
            salvar.flush();
            salvar.close();

            Log.i("Bruna", "Salvou  arquivo " + file);
        //    Log.i("Bruna","1 "+ isExternalStorageEmulated()  + pastaArquivos);
            //Log.i("Bruna","2 "+ isExternalStorageRemovable();
           // Log.i("Bruna","2 "+ );
             //Log.i("Bruna","3 "+ rawExternalStorage);
                  //  Log.i("Bruna","3 "+ Environment.getExternalStoragePublicDirectory(root));

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("Bruna", "Erro");
        }
    }



    }















