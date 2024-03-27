package uao.movilizapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uao.movilizapp.Utilidades.GestorDialogos;
import uao.movilizapp.Utilidades.Utilidades;

/**
 * Created by home on 04/10/2016.
 */
public class registroVehiculoActivity extends Activity {

    private ImageView fotoCarro;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private String userChoosenTask;
    private EditText placa,marca,referencia,color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_vehiculo);
        fotoCarro = (ImageView)findViewById(R.id.fotoBotton);
        placa = (EditText) findViewById(R.id.in_put_placa);
        marca = (EditText) findViewById(R.id.in_put_marca);
        referencia = (EditText) findViewById(R.id.in_put_ref);
        color = (EditText) findViewById(R.id.in_put_color);

    }



    public void finalizar(View view){
        final String txt_placa = placa.getText().toString();
        final String txt_marca = marca.getText().toString();
        final String txt_ref = referencia.getText().toString();
        final String txt_color = color.getText().toString();

        if(estanCamposCorrectos(txt_placa,txt_marca, txt_ref,txt_color) ){
            System.out.println("datos buenos");
            Intent perfilConductor = new Intent(this, perfilConductorActivity.class);
            startActivity(perfilConductor);
        }





    }

    public boolean estanCamposCorrectos(String placa, String marca, String ref, String color){
        int tmp = 0;
        Pattern pattern;
        Matcher matcher;
        final String PLACA_MOLDE = "[a-zA-Z]{3}+-[0-9]{3}";
        pattern = Pattern.compile(PLACA_MOLDE);
        matcher = pattern.matcher(placa);
        if(matcher.matches()){
            tmp++;
        }else {
            this.placa.setError("Placa invalida, ejemplo: gjd-584");
        }
        if(TextUtils.isEmpty(marca)) {
            this.marca.setError("Marca invalida, ejemplo: ford");
        }else{
            tmp++;
        }
        if(TextUtils.isEmpty(ref)) {
            this.referencia.setError("Referencia invalida, ejemplo: fiesta");
        }else{
            tmp++;
        }
        if(TextUtils.isEmpty(color)) {
            this.color.setError("Campo invalido, ejemplo: gris ");
        }else{
            tmp++;
        }
        if(tmp==4){
            return true;
        }else {
            return false;
        }

    }

    private boolean esPlacaValida(String string){
        Pattern pattern;
        Matcher matcher;
        final String PLACA_MOLDE = "[a-zA-Z]{3}+-[0-9]{3}";
        pattern = Pattern.compile(PLACA_MOLDE);
        matcher = pattern.matcher(string);
        if(matcher.matches()){
            return true;
        }else {
            placa.setError("Placa incorrecta, ejemplo: gjd-584");
            return false;
        }

    }

    public void seleccionarImagen(View view) {
        final CharSequence[] items = { "Tomar foto", "Seleccionar desde la galeria",
                "Cancelar" };

        AlertDialog.Builder builder = new AlertDialog.Builder(registroVehiculoActivity.this);
        builder.setTitle("Agregar foto del vehiculo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result= GestorDialogos.checkPermission(registroVehiculoActivity.this);

                if (items[item].equals("Tomar foto")) {
                    userChoosenTask ="Tomar foto";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Seleccionar desde la galeria")) {
                    userChoosenTask ="Seleccionar desde la galeria";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancelar")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fotoCarro.setImageBitmap(Utilidades.getFotoCircular(thumbnail));
    }

    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        fotoCarro.setImageBitmap(Utilidades.getFotoCircular(bm));
    }





}
