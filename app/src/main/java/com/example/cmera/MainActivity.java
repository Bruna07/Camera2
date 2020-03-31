package com.example.cmera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private ImageView imagem;
    private static final int IMAGE_VIEW_ACTIVITY_REQUEST_CODE   = 101;//para abrir a galeria
    private File arquivoFoto=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.verifcarPermissaoSd();
        this.armazenamentoDisp();


        imagem = (ImageView) findViewById(R.id.imagem1);
            final ImageButton galeria = (ImageButton) findViewById(R.id.btgaleria);
            ImageButton camera = (ImageButton) findViewById(R.id.btcamera);


        //*=*=*=*=*=*=*=*=*=*=*=*=*=*=*= BOTÃO TIRAR FOTO *=*=*=*=**=*=*=*=*=*=*=*=*=*=*=*=*/
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tirarFoto();
            }});
        //*=*=*=*=*=*=*=**=*=*=*=*=*=*BOTÃO ACESSO A GALERIA *=*=*=*=*=*=*=*=*=*=*=*=*=*=**/

        galeria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v2) {
                abrirGaleria();
            }});
        /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*/
    }

    public void tirarFoto(){
        /*Responsavel por abrir a câmera*/
        Log.i("Bruna","Entrou: tirarFoto();");
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,1);
    }

    public void abrirGaleria(){
        Log.i("Bruna","Entrou: AbrirGaleria();");
        Intent intentPegaFoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(intentPegaFoto,IMAGE_VIEW_ACTIVITY_REQUEST_CODE);
    }
      /**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=**=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*/
    @Override
    //*=*=*=*=*=*=*Retorna o resultado da intent*=*=*=*=*=*=*=*=*
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1 && resultCode == RESULT_OK){
            //CONVERTE RESULTADOS
            Bundle extras = data.getExtras();
            Bitmap formatImagem= (Bitmap) extras.get("data");
            imagem.setImageBitmap(formatImagem);
            Log.i("Bruna","Imagem convertida");

           this.salvarArquivo(formatImagem);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**=*=*=*=*=*=*=*=*=verifica permissão de acesso ao SD =*=*=*==*=*=*=*=*=*===**/
    private void verifcarPermissaoSd() {
        Log.i("Bruna","Entrou: verificarPermissaoSD();");

        if(ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            }
        }else{
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        }
    }

    /* Verifica se há armazenamento externo disponível para leitura e gravação */
    public boolean armazenamentoDisp() {
        Log.i("Bruna","Entrou: ArmazenamentoDis();");
        String state = Environment.getExternalStorageState();/* OBS*/
        if (Environment.MEDIA_MOUNTED.equals(state)) { /* Estado de armazenamento se a mídia estiver presente, mas não montada*/
            Log.i("Bruna","Armazenamento Disponível");
            return true;
        }
        Log.i("Bruna","Armazenamento não Disponível");
        return false;
    }

    /*SALVAR O ARQUIVO*/
        private void salvarArquivo(Bitmap Bitmap2) {
            Log.i("Bruna","Entrou: salvarArquivo();");
            String root = Environment.getExternalStorageDirectory().toString();
            File pastaArquivos = new File(root + "/Arquivos_Camera2\"");

            pastaArquivos.mkdirs();/*Cria o diretório nomeado por esse caminho abstrato, incluindo os diretórios pai necessários, mas inexistentes*/

                /*nome do arquivo (imagem)*/
            String sobreNome = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String nome = "Br_"+ sobreNome +".jpg";

            File file = new File (pastaArquivos, nome);
            if (file.exists()) file.delete ();/*ver se o arquivo existe se sim apaga*/
            try {
                FileOutputStream out = new FileOutputStream(file);
                Bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
                Log.i("Bruna","Salvou  arquivo");
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("Bruna","Erro");
            }
        }


    }
