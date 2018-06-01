package com.example.nfjaramillo.mafia;

import android.app.AlertDialog;
import android.util.Log;

import java.io.IOException;
import java.util.MissingFormatArgumentException;

public class ThreadRealizarJugada implements Runnable{

    private Partida partida;
    private Main2Activity principal;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye el nuevo hilo y lo deja listo para conectarse al servidor.
     * @param pPartida Partida actual. pPartida != null.
     * @param pInterfaz Ventana principal de la aplicación. pInterfaz != null.
     */
    public ThreadRealizarJugada( Partida pPartida, Main2Activity pInterfaz )
    {
        partida = pPartida;
        principal = pInterfaz;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Espera la información de inicio de la partida y actualiza la partida y la interfaz una vez se de inicio a la partida.
     */
    public void run( ) {



        // 2. recolectar dinero.
        try {
            double dinero = partida.recoletarDinero( );
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean cambioCarta = false;

        if( partida.puedeContinuar( ) )
        {

            // 3. Jugar carta
            // No hay cambio de carta
            if( partida.darSillaSeleccionada( ) != -1 )
            {
                String entrada = partida.darEntradaJugada( partida.darJugador( ), partida.darCartaSeleccionada( ), partida.darSillaSeleccionada( ), partida.darSillaDestino( ) );
            }
            // Hay cambio de carta.
            else
            {
                cambioCarta = true;
                partida.seleccionarSilla( -1 );
                String entrada = partida.darEntradaJugada( partida.darJugador( ), null, -1, -1 );;
            }

            try {
                partida.realizarJugada( );
            } catch (IOException e) {
                e.printStackTrace();
            }

            principal.actualizarInterfaz(partida );

            // 4. Cambiar de silla (opcional)
            boolean puede = false;
            if( !cambioCarta )
            {
                for( String carta : partida.darCartas( ) )
                {
                    // Tiene carta para cambiar de silla y hay puestos vacíos
                    if( partida.puedeContinuar( ) && carta != null && carta.equals( Partida.CAMBIAR_SILLA ) && partida.darMafiosos( ).size( ) != Partida.CANTIDAD_PUESTOS && partida.calcularCartasRestantes( ) < 2 )
                    {
                        Log.i("Silla","Intenta cambiar");
                      //  principal.mostrarConfirmacionCambioSilla( );
                        principal.cambiarSilla();

                        puede = true;
                        break;

                    }
                }
            }

            if( !puede )
            {
                try {
                    principal.finalizarJugada( );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else
        {
            try {
                principal.finalizarJugada( );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
