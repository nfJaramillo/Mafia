package com.example.nfjaramillo.mafia;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    /**
     * Controles
     * */
    private Button SingIn;
    private Button SingUp;
    private EditText servidor;
    private EditText puerto;
    private EditText nFamilia;
    private EditText nCapo;
    private EditText fSecreta;
    private static Partida main;
    private static Main2Activity main2;


    private Context context = this;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SingIn = ((Button) findViewById(R.id.Ingresar));
        SingUp = ((Button) findViewById(R.id.Registrarse));
        servidor = ((EditText) findViewById(R.id.servidor));
        puerto = ((EditText) findViewById(R.id.puerto));
        nFamilia = ((EditText) findViewById(R.id.nFamilia));
        nCapo = ((EditText) findViewById(R.id.nCapo));
        fSecreta = ((EditText) findViewById(R.id.fSecreta));


        SingIn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {

                        if(nFamilia.getText().toString().length()>0&&fSecreta.getText().toString().length()>0){
                            new Thread(new loginThread()).start();


                            //MyATaskCliente myATaskYW = new MyATaskCliente();
                            //myATaskYW.execute(nFamilia.getText().toString(),fSecreta.getText().toString());

                        }else{
                            Toast.makeText(context, "Escriba \"frase\" o \"libro\" ", Toast.LENGTH_LONG).show();

                        }

                    }
                });
        SingUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if(nFamilia.getText().toString().length()>0&&fSecreta.getText().toString().length()>0){
                    new Thread(new RegisterThread()).start();

                    //MyATaskCliente myATaskYW = new MyATaskCliente();
                    //myATaskYW.execute(nFamilia.getText().toString(),fSecreta.getText().toString());

                }else{
                    Toast.makeText(context, "Escriba \"frase\" o \"libro\" ", Toast.LENGTH_LONG).show();

                }

            }
        });
    }//end:onCreate


    /**
     * Clase para interactuar con el servidor
     * */
    class MyATaskCliente extends AsyncTask<String,Void,String>{

        /**
         * Ventana que bloqueara la pantalla del movil hasta recibir respuesta del servidor
         * */
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }

        /**
         * muestra una ventana emergente
         * */
        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setTitle("Connecting to server");
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
        }


        /**
         * Oculta ventana emergente y muestra resultado en pantalla
         * */
        @Override
        protected void onPostExecute(String value){
            progressDialog.dismiss();

        }
    }



    private class loginThread implements Runnable {
        @Override
        public void run() {
            try
            {
                String server = servidor.getText().toString();
                Integer port = Integer.parseInt(puerto.getText().toString());
                //Se conecta al servidor
                Log.i("I/TCP Client", server);
                Log.i("I/TCP Client", String.valueOf(port));
                InetAddress serverAddr = InetAddress.getByName(server);
                Log.i("I/TCP Client", "Connecting...");
                Socket socket = new Socket(serverAddr, port);

                Log.i("I/TCP Client", "Connected to server");

                Partida main = new Partida(socket);

                main.iniciarSesion(nFamilia.getText().toString(),fSecreta.getText().toString());
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                Main2Activity.recibirPartida(main);
                Main2Activity.recibirPrimera(MainActivity.this);
                MainActivity.recibirPartida(main);





                Thread.interrupted();

            }
            catch (Exception e)
            {

            }
        }
    }

    private class RegisterThread implements Runnable {
        @Override
        public void run() {
            try
            {
                String server = servidor.getText().toString();
                Integer port = Integer.parseInt(puerto.getText().toString());
                //Se conecta al servidor
                Log.i("I/TCP Client", server);
                Log.i("I/TCP Client", String.valueOf(port));
                InetAddress serverAddr = InetAddress.getByName(server);
                Log.i("I/TCP Client", "Connecting...");
                Socket socket = new Socket(serverAddr, port);

                Log.i("I/TCP Client", "Connected to server");

                Partida main = new Partida(socket);
                main.registrar(nFamilia.getText().toString(),nCapo.getText().toString(),fSecreta.getText().toString());
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                Main2Activity.recibirPartida(main);
                Main2Activity.recibirPrimera(MainActivity.this);
                MainActivity.recibirPartida(main);

                Thread.interrupted();
            }
            catch (Exception e)
            {

            }
        }
    }
    private boolean resp = false;
    public boolean cambiarSilla()
    {

        AlertDialog.Builder cambio = new AlertDialog.Builder(this);
        cambio.setMessage("Usted tiene una carta para cambiar silla, ¿Desea usarla?").setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for( int i = 0; i < main.darCartas( ).length; i++ )
                        {
                            String carta = main.darCartas( )[ i ];
                            if( carta != null && carta.equals( Partida.CAMBIAR_SILLA ) )
                            {
                                main.seleccionarCarta( main.darCartas( )[ i ] );
                                break;
                            }
                        }

                       resp = true;
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        Looper.prepare();
        Log.i("Cambio","intentara crear");
        AlertDialog alerta = cambio.create();
        Log.i("Cambio","Creo");
        alerta.setTitle("Cambio silla");
        alerta.show();
        return resp;

    }
    public static void recibirPartida(Partida a)
    {
        main = a;
    }
    public static void recibirMain2(Main2Activity a)
    {
        main2 = a;
    }
}