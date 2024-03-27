package uao.movilizapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by home on 04/10/2016.
 */
public class seleccionPerfilUsuarioActivity extends Activity{

    RadioGroup rg;
    String rgEstado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_perfil_usuario);

        RadioGroup rg = (RadioGroup) findViewById(R.id.rg);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId){
                    case R.id.pasajero:
                        rgEstado = "pasajero";
                        break;

                    case R.id.conductor:
                        rgEstado = "conductor";
                        break;
                }
            }
        });

    }

    public void finalizar(View view){
        if(rgEstado!=null){
            if(rgEstado.equals("pasajero")){
                Intent perfilPublico = new Intent(this, perfilPublicoActivity.class);
                startActivity(perfilPublico);
            }else if(rgEstado.equals("conductor")){
                Intent registroVehiculo= new Intent(this, registroVehiculoActivity.class);
                startActivity(registroVehiculo);
            }
        }

    }

    public void clickImagen(View view){
        RadioButton rb_conductor = (RadioButton) findViewById(R.id.conductor);
        RadioButton rb_pasajero = (RadioButton) findViewById(R.id.pasajero);
        if (view.getId() == R.id.imagenConductor){
            rb_conductor.setChecked(true);
        }else if(view.getId() == R.id.imagenPasajero){
            rb_pasajero.setChecked(true);
        }
    }
}
