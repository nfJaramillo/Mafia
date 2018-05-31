package com.example.nfjaramillo.mafia;

import android.content.Context;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static android.graphics.Color.parseColor;

public class Main2Activity extends AppCompatActivity {

    private static Partida main;
    //Primera seccion
    private TextView miFamilia;
    private TextView turno;
    private TextView familias;


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


         new iniciarPartidaThread(main,this).run();

    }

    public static class iniciarPartidaThread implements Runnable {
        private Partida main;
        public iniciarPartidaThread(Partida pMain, Main2Activity a)
        {
            main = pMain;

            a.actualizarInterfaz(main);


        }


        @Override
        public void run() {
            try {

                main.iniciar();

            } catch (JuegoMafiaException e) {
                e.printStackTrace();
            }
        }

    }

    public void actualizarInterfaz(Partida main)
    {
        //Actualiza 1ra seccion
        if (main.darOponentes()[0]!= null && main.darOponentes()[1]!=null && main.darJugadorEnTurno()!=null )
        {
            miFamilia.setText(main.darJugador().darNombreFamilia() + ":" +'\n' + main.darDineroRecolectado());
            turno.setText("Turno actual:" + '\n' + main.darJugadorEnTurno().darNombreFamilia());
            familias.setText(main.darJugador().darNombreFamilia() +": Azul" + '\n' + main.darOponentes()[0].darNombreFamilia()+": Rojo" + '\n' + main.darJugadores().get(1).darNombreFamilia()+": Amarillo");

            //Actualiza 2da seccion

            for(int i = 0; i < main.darMafiosos().size(); i++)
            {

                Mafioso m =  main.darMafiosos().get(i);
                int silla = m.darUbicacion();
                String tipo = m.darTipo();
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
        }

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
}
