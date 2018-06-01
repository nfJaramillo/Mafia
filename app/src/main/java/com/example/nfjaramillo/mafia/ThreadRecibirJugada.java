package com.example.nfjaramillo.mafia;

import java.io.IOException;

public class ThreadRecibirJugada implements Runnable {

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
    public ThreadRecibirJugada( Partida pPartida, Main2Activity pInterfaz )
    {
        principal = pInterfaz;
        partida = pPartida;
    }

    // -----------------------------------------------------------------
    // Métodos
    // -----------------------------------------------------------------

    /**
     * Hilo encargado de manejar el inicio de sesión.<br>
     * Cuando se tiene la conexión y la información de la partida entonces se actualiza la interfaz.
     */
    public void run( )
    {

        try
        {
            String ultimoTurno = partida.darJugadorEnTurno( ).darNombreFamilia( );
            while( !partida.enTurno( ) && partida.darEstadoJuego( ) != Partida.FINALIZADO )
            {
                String entrada;
                entrada = partida.recibirJugada( );
                if( partida.darEstadoJuego( ) == Partida.FINALIZADO )
                {
                    principal.mostrarFinJuego( );

                }
                principal.actualizarInterfaz(partida );

                if( partida.darEstadoJuego( ) != Partida.FINALIZADO && !ultimoTurno.equals( partida.darJugadorEnTurno( ).darNombreFamilia( ) ) )
                {
                    entrada = "La familia " + partida.darJugadorEnTurno( ) + " tiene el turno.";
                    ultimoTurno = partida.darJugadorEnTurno( ).darNombreFamilia( );
                }
            }

            if( partida.darEstadoJuego( ) != Partida.FINALIZADO )
            {
                principal.iniciarTurno( );
            }
        }
        catch( JuegoMafiaException e )
        {

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
