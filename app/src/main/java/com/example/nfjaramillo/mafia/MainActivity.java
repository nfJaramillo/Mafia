package com.example.nfjaramillo.mafia;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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


        SingIn.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        if(nFamilia.getText().toString().length()>0&&fSecreta.getText().toString().length()>0){
                            MyATaskCliente myATaskYW = new MyATaskCliente();
                            myATaskYW.execute(nFamilia.getText().toString(),fSecreta.getText().toString());
                            System.exit(0);
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
         * Se conecta al servidor y trata resultado
         * */
        @Override
        protected String doInBackground(String... values){

            try {
                String server = servidor.getText().toString();
                Integer port = Integer.parseInt(puerto.getText().toString());
                //Se conecta al servidor
                InetAddress serverAddr = InetAddress.getByName(server);
                Log.i("I/TCP Client", "Connecting...");
                Socket socket = new Socket(serverAddr, port);
                Log.i("I/TCP Client", "Connected to server");

                /*
                //envia peticion de cliente
                Log.i("I/TCP Client", "Send data to server");
                PrintStream output = new PrintStream(socket.getOutputStream());
                String request = values[0];
                output.println(request);

                //recibe respuesta del servidor y formatea a String
                Log.i("I/TCP Client", "Received data to server");
                InputStream stream = socket.getInputStream();
                byte[] lenBytes = new byte[256];
                stream.read(lenBytes,0,256);
                String received = new String(lenBytes,"UTF-8").trim();
                Log.i("I/TCP Client", "Received " + received);
                Log.i("I/TCP Client", "");
                //cierra conexion
                socket.close();
                return received;
            }catch (UnknownHostException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();
            } catch (IOException ex) {
                Log.e("E/TCP Client", "" + ex.getMessage());
                return ex.getMessage();

            */

                Partida main = new Partida(socket);
                main.iniciarSesion(values[0],values[1]);
            }


            catch (Exception e)
            {

            }
            return null;
        }


        /**
         * Oculta ventana emergente y muestra resultado en pantalla
         * */
        @Override
        protected void onPostExecute(String value){
            progressDialog.dismiss();

        }
    }
}