package com.example.frpinkl.appmanejocamara;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private final String ruta_fotos = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/misfotos/";
    private File file = new File(ruta_fotos);
    private Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //boton para hacer la foto
        boton = (Button)findViewById(R.id.btnTomaFoto);

        //crea la carpeta contenedor de las fotos si no existe
        file.mkdirs();

        //damos accion al boton
        //cuando se pulsa el boton se abre la camara para hacer la foto y
        //despues de hacerla se vuelve a la aplicacion
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String file = "sdcard/pictures/misfotos/" + getCode() + ".jpg";
                File mi_foto = new File(file);
                try {
                    mi_foto.createNewFile();
                }catch (IOException ex){
                    Log.e("ERROR ", "ERROR: "+ex);
                }

                //manejo de Uri
                Uri uri = Uri.fromFile( mi_foto);

                //abrir la camara para hacer la foto
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                //guardar la imagen
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                //vuelve a la activity
                startActivityForResult(cameraIntent, 0);
            }
        });

    }


    //metodo para generar un nombre único para cada foto con la fecha del sistema: photoCode
    //tendra el formato  “pic_yyyymmddhhmmss.jpg
    //@SuppressLint("SimpleDateFormat")
    private String getCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
          String date = dateFormat.format(new Date() );
         String photoCode = "pic_" + date;
          return photoCode;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
