package com.example.nfjaramillo.mafia;

import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class Main2Activity extends AppCompatActivity {

    private static Partida main;
    //Primera seccion
    private TextView miFamilia;
    private TextView turno;
    private TextView familias;
    private TextView pasteles;


    //Segunda seccion
    private Button S1;
    private Button S2;
    private Button S3;
    private Button S4;
    private Button S5;
    private Button S6;
    private Button S7;
    private Button S8;
    private Button S9;
    private Button S10;
    private Button S11;
    private Button S12;
    private TextView dinero;
    private ArrayList <Button> botones;

    //Tercera seccion
    private Button dCarta;
    private Button C1;
    private Button C2;
    private Button C3;
    private Button C4;
    private Button C5;



    private Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);




        miFamilia = ((TextView) findViewById(R.id.miFamilia));
        turno = ((TextView) findViewById(R.id.turno));
        familias = ((TextView) findViewById(R.id.Familias));
        pasteles = ((TextView) findViewById(R.id.pasteles));

        S1 = ((Button) findViewById(R.id.S1));
        S2 = ((Button) findViewById(R.id.S2));
        S3 = ((Button) findViewById(R.id.S3));
        S4 = ((Button) findViewById(R.id.S4));
        S5 = ((Button) findViewById(R.id.S5));
        S6 = ((Button) findViewById(R.id.S6));
        S7 = ((Button) findViewById(R.id.S7));
        S8 = ((Button) findViewById(R.id.S8));
        S9 = ((Button) findViewById(R.id.S9));
        S10 = ((Button) findViewById(R.id.S10));
        S11 = ((Button) findViewById(R.id.S11));
        S12 = ((Button) findViewById(R.id.S12));
        dinero = ((TextView) findViewById(R.id.dinero));
        botones = new ArrayList<Button>();
        botones.add(S1);
        botones.add(S2);
        botones.add(S3);
        botones.add(S4);
        botones.add(S5);
        botones.add(S6);
        botones.add(S7);
        botones.add(S8);
        botones.add(S9);
        botones.add(S10);
        botones.add(S11);
        botones.add(S12);

        dCarta = ((Button) findViewById(R.id.dCarta));
        C1 = ((Button) findViewById(R.id.C1));
        C2 = ((Button) findViewById(R.id.C2));
        C3 = ((Button) findViewById(R.id.C3));
        C4 = ((Button) findViewById(R.id.C4));
        C5 = ((Button) findViewById(R.id.C5));

        new Thread(new iniciarPartidaThread(main,this)).start();



        C1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                seleccionarCarta(0);


            }
        });
        C2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                seleccionarCarta(1);


            }
        });
        C3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                seleccionarCarta(2);


            }
        });
        C4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                seleccionarCarta(3);


            }
        });
        C5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                seleccionarCarta(4);


            }
        });
        dCarta.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                main.eliminarCartaSeleccionada();
            }
        });
        S1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(2);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S4.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S5.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(4);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S6.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(5);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S7.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(6);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S8.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(7);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S9.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(8);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S10.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(9);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S11.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(10);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        S12.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    realizarJugada(11);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


    }

    public static class iniciarPartidaThread implements Runnable {
        private Partida main;
        private Main2Activity a;
        public iniciarPartidaThread(Partida pMain, Main2Activity pA)
        {
            main = pMain;
            a = pA;



        }


        @Override
        public void run() {
            try {

                main.iniciar();

                a.actualizarInterfaz(main);
                if(!main.enTurno())
                {
                  a.esperarTurno();
                }
            } catch (JuegoMafiaException e) {
                e.printStackTrace();
            }
        }


    }

    public void actualizarInterfaz(Partida main)
    {
        //Actualiza 1ra seccion

            miFamilia.setText(main.darJugador().darNombreFamilia() + ":" +'\n' + main.darDineroRecolectado());
            turno.setText("Turno actual:" + '\n' + main.darJugadorEnTurno().darNombreFamilia());
            familias.setText(main.darJugador().darNombreFamilia() +": Rojo" + '\n' + main.darOponentes()[0].darNombreFamilia()+": Azul" + '\n' + main.darJugadores().get(1).darNombreFamilia()+": Verde");

            //Actualiza 2da seccion
        for(int i = 0; i<botones.size();i++)
        {
            botones.get(i).setText("");
            botones.get(i).setBackgroundColor(Color.BLACK);
        }

            for(int i = 0;  i < main.darMafiosos().size(); i++)
            {


                Mafioso m =  main.darMafiosos().get(i);

                int silla = m.darUbicacion();
                String tipo = m.darTipo();
                if(tipo.equals("PISTOLON"))
                {
                    tipo = "PN";
                }
                else if (tipo.equals("PINCHORIZZO"))
                {
                    tipo = "PZ";
                }
                int color =  m.darJugador().darColor();
                botones.get(silla - 1).setBackgroundColor(colorear(color));
                botones.get(silla - 1).setText(tipo);

            }
            dinero.setText(String.valueOf(main.darDineroMesa()));

            // 3ra seccion
            C1.setText(main.darCartas()[0]);
            C2.setText(main.darCartas()[1]);
            C3.setText(main.darCartas()[2]);
            C4.setText(main.darCartas()[3]);
            C5.setText(main.darCartas()[4]);

            String pastel = "";
            for(int i = 0; i < botones.size();i++)
            {


                if(main.hayPastel(i)==true)
                {
                    pastel+= '\n'+"Hay un pastel en la silla: "+(i);
                }
            }
            pasteles.setText(pastel);






    }

    private Integer colorear(int color) {
        if(color == 1)
        {
            return parseColor("red");
        }
        else if (color==2)
        {
            return parseColor("blue");
        }
        else
        {
            return parseColor("green");
        }
    }

    public static void recibirPartida(Partida a)
    {
        main = a;
    }

    public void mostrarFinJuego()
    {
        startActivity(new Intent(Main2Activity.this, Main3Activity.class));
    }

    public void iniciarTurno( ) throws IOException {
        // 1. Detonar pastel
        if( main.darJugador( ).darUbicacionPastel( ) != -1 )
        {
            String entrada = main.detonarPastel( );

            actualizarInterfaz(main );
        }

        // No tiene mafiososos para jugar
        if( main.darJugador( ).darMafiosos( ).isEmpty( ) )
        {

            finalizarJugada( );
        }
        // Si no puede continuar
        if( !main.puedeContinuar( ) )
        {
            finalizarJugada( );
        }


    }

    /**
     * Ejecuta las acciones necesarias para finalizar la jugada.
     */
    public void finalizarJugada( ) throws IOException {
        // 3. Cambiar de silla (opcional)
        if( main.darSillaSeleccionada( ) != -1 && !main.darJugador( ).darMafiosos( ).isEmpty( ) )
        {
            String entrada = main.darEntradaJugada( main.darJugador( ), main.darCartaSeleccionada( ), main.darSillaSeleccionada( ), main.darSillaDestino( ) );
            main.cambiarSilla( );
        }

        // 4. Enviar fin
        boolean finJuego = !main.puedeContinuar( );
        if( finJuego )
        {
            try
            {
                String entrada = main.finalizarJuego( );
                actualizarInterfaz(main );
                mostrarFinJuego( );
            }
            catch( JuegoMafiaException e )
            {
                //Deberina mostrar un error
            }
        }
        else
        {
            try
            {
                String entrada = main.darJugadorEnTurno( ) + " ha finalizado su turno. ";
                main.finalizarTurno( );
                entrada = "La familia " + main.darJugadorEnTurno( ) + " tiene el turno.";
                actualizarInterfaz( main);
                esperarTurno( );
            }
            catch( JuegoMafiaException e )
            {
                //Deberina mostrar un error
            }
        }
    }

    public void esperarTurno( )
    {

        new Thread(new ThreadRecibirJugada(main,this)).start();
        actualizarInterfaz(main);

    }
    public void  realizarJugada(int pSilla) throws IOException {


        // No es cambio de carta
        if( pSilla != -1 )
        {

            if( main.darSillaSeleccionada( ) == -1 )
            {
                main.seleccionarSilla( pSilla + 1 );
                // No es cambiar silla
                if( !main.darCartaSeleccionada( ).equals( Partida.CAMBIAR_SILLA ) )
                {

                    new Thread(new ThreadRealizarJugada(main,this)).start();
                }
                else
                {
                    actualizarInterfaz( main);
                }
            }
            // Es cambiar silla
            else
            {
                if( main.darEstadoJuego( ) == Partida.REALIZANDO_JUGADA )
                {
                    cambiarSilla( pSilla );
                }
                else
                {
                    main.cambiarSillaDestino( pSilla + 1 );
                    actualizarInterfaz( main);
                    new Thread(new ThreadRealizarJugada(main,this)).start();
                }
            }
        }
        // Es cambio de carta
        else
        {
            new Thread(new ThreadRealizarJugada(main,this)).start();
        }

        actualizarInterfaz(main );
    }

    private void cambiarSilla( int pSilla ) throws IOException {
        if( main.darSillaSeleccionada( ) == -1 )
        {
            main.seleccionarSilla( pSilla + 1 );
            actualizarInterfaz(main );
        }
        else
        {
            main.cambiarSillaDestino( pSilla + 1 );
            actualizarInterfaz( main);
            finalizarJugada( );

        }
    }
    /**
     * Selecciona la carta del jugador que está en la posición dada.
     * @param pSeleccionada Índice de la carta seleccionada.
     */
    public void seleccionarCarta( int pSeleccionada )
    {
        main.seleccionarCarta( main.darCartas( )[ pSeleccionada ] );
        actualizarInterfaz( main);
    }
    public void cambiarSilla()
    {
        AlertDialog.Builder cambio = new AlertDialog.Builder(Main2Activity.this);
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

                        actualizarInterfaz( main);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                try {
                    finalizarJugada();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        Looper.prepare();
        Log.i("Cambio","intentara crear");
        AlertDialog alerta = cambio.create();
        Log.i("Cambio","Creo");
        alerta.setTitle("Cambio silla");
        alerta.show();


    }

}
