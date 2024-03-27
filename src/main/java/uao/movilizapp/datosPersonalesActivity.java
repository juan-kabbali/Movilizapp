package uao.movilizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.regex.Pattern;

/**
 * Created by home on 04/10/2016.
 */
public class datosPersonalesActivity extends Activity{

    private EditText numeroTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_personales);
        numeroTelefono=((EditText) findViewById(R.id.numero_telefono));
    }


    public void signIn(View view){

        final String numero_telefono = numeroTelefono.getText().toString();
        if (isValidMobile(numero_telefono)) {
            Intent seleccionPerfilUsuario = new Intent(this, seleccionPerfilUsuarioActivity.class);
            startActivity(seleccionPerfilUsuario);
        }
    }

    private boolean isValidMobile(String phone2)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone2))
        {
            if(phone2.length()!=10)
            {
                check = false;
                numeroTelefono.setError("Número incorrecto");
            }else
            {
                check = true;
            }
        } else
        {
            check=false;
        }
        return check;
    }
}
