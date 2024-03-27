package uao.movilizapp.Utilidades;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import uao.movilizapp.R;

/**
 * Created by home on 21/09/2016.
 */
public class Splash extends Activity {

    boolean spActive;      //Bandera de Splash activo
    boolean spPaused;    //Bandera de Splash pausado
    long SPLASH_TIME_TO_END = 2000;  //Tiempo duración de Splash en milisegundos

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            spPaused = false;
            spActive = true;

                Thread splashTimer = new Thread() {
                    public void run() {
                        try{ //Bucla de espera
                            long ms = 0;
                            while(spActive && ms < SPLASH_TIME_TO_END){
                                sleep(100);                //El temporizador avanza si no se ha pausado
                                 if(!spPaused)
                                     ms += 100;
                            } //Avanza a la siguiente pantalla

                            startActivity(new Intent("init.splash.CLEARSPLASH")); //No vuelve a llamarse esta Actividad al pulsar back
                            finish();
                        }
                        catch(Exception e){                     //Thread exception
                             System.out.println(e.toString());
                        }
                    }
                };
            splashTimer.start();
            setContentView(R.layout.activity_splash);
            return; }

        protected void onStop() {
            super.onStop();
        }

        protected void onPause() {
            super.onPause();
            spPaused = true;
        }

        protected void onResume() {
            super.onResume();
            spPaused = false;
        }

        protected void onDestroy() {
            super.onDestroy();
        }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
            //Quitar Splash si se pulsa cualquier tecla
            super.onKeyDown(keyCode, event);
            spActive = false;
            return true;

        }
}

