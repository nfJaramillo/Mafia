/**
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * Universidad de los Andes (Bogot� - Colombia)
 * Departamento de Ingenier�a de Sistemas y Computaci�n 
 * Licenciado bajo el esquema Academic Free License version 2.1 
 *
 * Proyecto Cupi2 (http://cupi2.uniandes.edu.co)
 * Ejercicio: n12_juegoMafia
 * Autor: Equipo Cupi2 2018-1
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package com.example.nfjaramillo.mafia;

import android.content.Intent;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa una partida del juego mafia.
 */
public class Partida
{
    // -----------------------------------------------------------------
    // Enumeraciones
    // -----------------------------------------------------------------

    /**
     * Enumeraci�n con los tipos de elementos que pueden estar sobre la mesa.
     */
    enum Elemento
    {
        /**
         * Representa el cuchillo.
         */
        CUCHILLO,
        /**
         * Representa la pistola.
         */
        PISTOLA,
        /**
         * Representa la caja registradora.
         */
        CAJA_REGISTRADORA,
        /**
         * Representa el titulo de propiedad del casino.
         */
        CASINO,
        /**
         * Representa el titulo de propiedad del bar.
         */
        BAR
    }
    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Es la cadena de caracteres para separar el comando de los par�metros
     */
    public static final String SEPARADOR_COMANDO = ":::";

    /**
     * Es la cadena de caracteres para separar los par�metros
     */
    public static final String SEPARADOR_PARAMETROS = ";;;";
    /**
     * Constante que indica la cantidad de cartas que debe tener el jugador.
     */
    public static final int CANTIDAD_CARTAS = 5;

    /**
     * Constante que indica la cantidad de oponentes del jugador en la partida.
     */
    private static final int CANTIDAD_OPONENTES = 2;

    /**
     * Constante que indica la cantidad de puestos que hay en la mesa.
     */
    public static final int CANTIDAD_PUESTOS = 12;

    /**
     * Indica que se desea iniciar sesi�n en la aplicaci�n.
     */
    public static final String INGRESAR = "INGRESAR";

    /**
     * Indica que se desea registrar en la aplicaci�n.
     */
    public static final String REGISTRO = "REGISTRO";

    /**
     * Indica que se realizo exitosamente una operaci�n.
     */
    public static final String OK = "OK";

    /**
     * Indica que ocurri� un error al efectuar alguna acci�n.
     */
    public static final String ERROR = "ERROR";

    /**
     * Indica que se va a iniciar una partida.
     */
    public static final String INICIO_PARTIDA = "INICIO_PARTIDA";

    /**
     * Indica que se enviar�n un conjunto de cartas.
     */
    public static final String CARTAS = "CARTAS";

    /**
     * Indica el tipo de carta CUCHILLO.
     */
    public static final String CUCHILLO = "CUCHILLO";

    /**
     * Indica el tipo de carta PISTOLA.
     */
    public static final String PISTOLA = "PISTOLA";

    /**
     * Indica el tipo de carta PASTEL.
     */
    public static final String PASTEL = "PASTEL";

    /**
     * Indica el tipo de carta DETONADOR.
     */
    public static final String DETONADOR = "DETONADOR";

    /**
     * Indica el tipo de carta CAMBIAR_SILLA.
     */
    public static final String CAMBIAR_SILLA = "CAMBIAR_SILLA";

    /**
     * Indica que se est� recogiendo el dinero correspondiente en el turno.
     */
    public static final String RECOLECTAR_DINERO = "RECOLECTAR_DINERO";

    /**
     * Indica que se detono el pastel del jugador en turno.
     */
    public static final String DETONAR_PASTEL = "DETONAR_PASTEL";

    /**
     * Indica que se jugar� una carta.
     */
    public static final String CARTA = "CARTA";

    /**
     * Indica que se desea realizar un cambio de carta.
     */
    public static final String CAMBIAR_CARTA = "CAMBIAR_CARTA";

    /**
     * Mensaje para enviar una acci�n perteneciente a la jugada de un jugador.
     */
    public static final String JUGADA = "JUGADA";

    /**
     * Indica que se ha finalizado el turno.
     */
    public static final String FIN_TURNO = "FIN_TURNO";

    /**
     * Indica que ha finalizado el juego.
     */
    public static final String FIN_JUEGO = "FIN_JUEGO";

    /**
     * Constante para indicar el estado en espera de oponentes.
     */
    public static final int ESPERANDO_OPONENTES = 1;

    /**
     * Constante para indicar el estado de esperando turno.
     */
    public static final int ESPERANDO_TURNO = 2;

    /**
     * Constante para indicar el estado en turno.
     */
    public static final int EN_TURNO = 3;

    /**
     * Constante para indicar el estado realizando jugada.
     */
    public static final int REALIZANDO_JUGADA = 4;

    /**
     * Constante para indicar el estado juego finalizado.
     */
    public static final int FINALIZADO = 5;

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Jugador de la partida.
     */
    private Jugador jugador;

    /**
     * Oponentes del jugador en la partida.
     */
    private Jugador[] oponentes;

    /**
     * Cartas que tiene el jugador en su poder.
     */
    private String[] cartas;

    /**
     * Dinero recolectado por el jugador en la partida.
     */
    private double dineroRecolectado;

    /**
     * Familia que esta en turno.
     */
    private String turno;

    /**
     * Indica el estado actual de la partida.
     */
    private int estado;

    /**
     * Arreglo que contiene los elementos ubicados sobre cada puesto en la mesa.
     */
    private Elemento[] elementos;

    /**
     * La carta seleccionada por el jugador.
     */
    private String cartaSeleccionada;

    /**
     * La silla seleccionada sobre la cual se quiere usar la carta.
     */
    private int sillaSeleccionada;

    /**
     * La silla a la cual se quiere cambiar el mafioso.
     */
    private int sillaDestino;

    /**
     * Cantidad de dinero que hay sobre la mesa.
     */
    private int dineroMesa;

    /**
     * Canal usado para comunicarse con el servidor.
     */
    private Socket canal;

    /**
     * Flujo que env�a los datos al servidor.
     */
    private PrintWriter out;

    /**
     * Flujo de donde se leen los datos que llegan del servidor.
     */
    private BufferedReader in;

    private InputStream stream;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye una nueva partida en un servidor dado.<br>
     * <b>post: </b> Se cre� el canal con el servidor, se inici� el lector y el escritor del canal. Se inicio el arreglo de oponentes sin oponentes. Se inicializ� el arreglo
     * con los elementos con sus valores por defecto. Se inicializ� el dinero de la mesa con su valor por defecto. la silla seleccionada y la silla destino se inicializaron en
     * -1.
     *
     * @throws JuegoMafiaException Si ocurren problemas de conexi�n con el servidor.
     */
    public Partida( Socket socket ) throws JuegoMafiaException
    {
        try
        {
            System.out.printf( "[%s][INFO] Iniciando conexi�n...\n", new Date( ) );
            canal = socket;
            in = new BufferedReader( new InputStreamReader( canal.getInputStream( ) ) );
            out = new PrintWriter( canal.getOutputStream( ), true );
            oponentes = new Jugador[CANTIDAD_OPONENTES];
            System.out.printf( "[%s][INFO] Conexi�n establecida.\n", new Date( ) );
            cartas = new String[CANTIDAD_CARTAS];

            elementos = new Elemento[CANTIDAD_PUESTOS];
            elementos[ 0 ] = Elemento.CAJA_REGISTRADORA;
            elementos[ 2 ] = Elemento.CASINO;
            elementos[ 3 ] = Elemento.BAR;
            elementos[ 5 ] = Elemento.PISTOLA;
            elementos[ 6 ] = Elemento.CUCHILLO;
            elementos[ 7 ] = Elemento.BAR;
            elementos[ 8 ] = Elemento.CUCHILLO;
            elementos[ 10 ] = Elemento.PISTOLA;
            elementos[ 11 ] = Elemento.CASINO;

            dineroMesa = 50000;
            sillaSeleccionada = -1;
            sillaDestino = -1;
        }
        catch( Exception e )
        {
            System.out.printf( "[%s][ERROR] %s\n", new Date( ), e.getMessage( ) );
            throw new JuegoMafiaException( "No fue posible establecer una conexi�n con el servidor." );
        }
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el jugador de la partida.
     * @return Jugador de la partida.
     */
    public Jugador darJugador( )
    {
        return jugador;
    }

    /**
     * Retorna el arreglo que contiene los oponentes de la partida.
     * @return Oponentes de la partida.
     */
    public Jugador[] darOponentes( )
    {
        return oponentes;
    }

    /**
     * Retorna un arreglo con las cartas del jugador.
     * @return Arreglo con las cartas del jugador.
     */
    public String[] darCartas( )
    {
        return cartas;
    }

    /**
     * Retorna el dinero recolectado por el jugador en la partida.
     * @return Dinero recolectado.
     */
    public double darDineroRecolectado( )
    {
        return dineroRecolectado;
    }

    // /**
    // * Retorna el nombre de la familia que est� en turno.
    // * @return Nombre de la familia con el turno.
    // */
    // public String darTurno( )
    // {
    // return turno;
    // }

    /**
     * Retorna la carta seleccionada.
     * @return Carta seleccionada.
     */
    public String darCartaSeleccionada( )
    {
        return cartaSeleccionada;
    }

    /**
     * Retorna la silla seleccionada.
     * @return Silla seleccionada.
     */
    public int darSillaSeleccionada( )
    {
        return sillaSeleccionada;
    }

    /**
     * Retorna la silla destino.
     * @return Silla destino.
     */
    public int darSillaDestino( )
    {
        return sillaDestino;
    }

    /**
     * Retorna el dinero disponible en la mesa.
     * @return Dinero disponible en la mesa.
     */
    public double darDineroMesa( )
    {
        return dineroMesa;
    }

    /**
     * Selecciona la silla seleccionada.<br>
     * <b>post: </b> Se cambi� la silla seleccionada por el valor dado por par�metro.
     * @param pSilla Silla que se quiere seleccionar o -1 si se quiere quitar la selecci�n.
     */
    public void seleccionarSilla( int pSilla )
    {
        sillaSeleccionada = pSilla;
    }

    /**
     * Cambia la silla destino.<br>
     * <b>post:</b> Se cambi� la silla destino por el valor dado por par�metro.
     * @param pSilla Silla que se quiere asignar.
     */
    public void cambiarSillaDestino( int pSilla )
    {
        sillaDestino = pSilla;
    }

    /**
     * Selecciona la carta dada.<br>
     * <b>post: </b> Se cambi� la carta seleccionada por el par�metro dado.
     * @param pCarta Carta que se quiere seleccionar.
     */
    public void seleccionarCarta( String pCarta )
    {
        cartaSeleccionada = pCarta;
        sillaSeleccionada = -1;
        sillaDestino = -1;
    }
    /**
     * Retorna el estado de la partida.
     * @return estado de la partida.
     */
    public int darEstadoJuego( )
    {
        return estado;
    }

    /**
     * Indica si el jugador esta en turno.
     * @return True si el turno le corresponde al jugador, False en caso contrario.
     */
    public boolean enTurno( )
    {
        return turno.equals( jugador.darNombreFamilia( ) );
    }

    /**
     * Inicia sesi�n en la partida.<br>
     * <b>pre: </b> El canal de comunicaci�n con el servidor est� establecido.<br>
     * <b>post: </b> Se cre� el jugador de la partida con la informaci�n dada por par�metro y la recibida por el servidor.
     * @param pNombreFamilia Nombre de la familia que va a ingresar a la partida. pNombreFamilia != null && pNombreFamilia != "".
     * @param pFraseSecreta Frase secreta de la familia. pFraseSecreta != null && pFraseSecreta != "".
     * @throws JuegoMafiaException si ocurre alg�n problema al iniciar sesi�n en el servidor.
     */
    public void iniciarSesion( String pNombreFamilia, String pFraseSecreta ) throws Exception
    {

        enviarMensaje( INGRESAR, pNombreFamilia, pFraseSecreta );

        String respuesta[] = recibirMensaje( ).split( SEPARADOR_COMANDO );
        if( respuesta[ 0 ].equals( ERROR ) )
        {
            throw new JuegoMafiaException( respuesta[ 1 ] );
        }
        jugador = new Jugador( pNombreFamilia );

        String[] info = respuesta[ 1 ].split( SEPARADOR_PARAMETROS );
        jugador.cambiarDerrotas( Integer.parseInt( info[ 0 ] ) );
        jugador.cambiarVictorias( Integer.parseInt( info[ 1 ] ) );

        estado = ESPERANDO_OPONENTES;

    }

    /**
     * Registra un jugador en el servidor e inicia sesi�n en la partida.<br>
     * <b>pre: </b> El canal de comunicaci�n con el servidor est� establecido.<br>
     * <b>post: </b> Se cre� el jugador de la partida con la informaci�n dada por par�metro y la recibida por el servidor.
     * @param pNombreFamilia Nombre de la familia que va a ingresar a la partida. pNombreFamilia != null && pNombreFamilia != "".
     * @param pNombreCapo Nombre del capo que dirige la familia. pNombreCapo != null && pNombreCapo != "".
     * @param pFraseSecreta Frase secreta de la familia. pFraseSecreta != null && pFraseSecreta != "".
     * @throws JuegoMafiaException si ocurre alg�n problema al iniciar sesi�n en el servidor.
     */
    public void registrar( String pNombreFamilia, String pNombreCapo, String pFraseSecreta ) throws Exception
    {
        enviarMensaje( REGISTRO, pNombreFamilia, pNombreCapo, pFraseSecreta );
        String respuesta[] = recibirMensaje( ).split( SEPARADOR_COMANDO );
        if( respuesta[ 0 ].equals( ERROR ) )
        {
            throw new JuegoMafiaException( respuesta[ 1 ] );
        }

        jugador = new Jugador( pNombreFamilia );

        estado = ESPERANDO_OPONENTES;

    }

    /**
     * Retorna el jugador que est� en turno.
     * @return Jugador que est� en turno.
     */
    public Jugador darJugadorEnTurno( )
    {
        Jugador respuesta = jugador;


        if( oponentes[ 0 ].darNombreFamilia( ).equals( turno ) )
        {
            respuesta = oponentes[ 0 ];
        }
        else if( oponentes[ 1 ].darNombreFamilia( ).equals( turno ) )
        {
            respuesta = oponentes[ 1 ];
        }
        return respuesta;
    }

    /**
     * Retorna la entrada de la bit�cora de la jugada actual del jugador.
     * @param pJugador Jugador que realiza la jugada. pJugador != null.
     * @param pCarta Carta que fue jugada.
     * @param pSilla1 Silla donde se ejecuto la acci�n.
     * @param pSilla2 Silla destino en caso que fuera un cambio de silla. -1 si no fue cambio de carta.
     * @return Entrada de la bit�cora.
     */
    public String darEntradaJugada( Jugador pJugador, String pCarta, int pSilla1, int pSilla2 )
    {

        String entrada = pJugador.darNombreFamilia( ) + " ha ";
        Mafioso maf = darMafioso( pSilla1 );
        if( pCarta == null )
        {
            entrada += "realizado un cambio de carta.";
        }
        else
        {

            switch( pCarta )
            {
                case Partida.CUCHILLO:
                    entrada += "apu�alado al " + maf.darTipo( ) + " de la familia " + maf.darJugador( ) + " sentado en la silla #" + maf.darUbicacion( ) + ".";
                    break;
                case Partida.PISTOLA:
                    entrada += "disparado al " + maf.darTipo( ) + " de la familia " + maf.darJugador( ) + " sentado en la silla #" + maf.darUbicacion( ) + ".";
                    break;
                case Partida.PASTEL:
                    entrada += "puesto un pastel en la silla #" + pSilla1;
                    break;
                case Partida.CAMBIAR_SILLA:
                    entrada += "movido a la silla #" + pSilla2 + " su " + maf.darTipo( ) + " que estaba en la silla #" + maf.darUbicacion( ) + ".";
                    break;
                case Partida.DETONADOR:
                    entrada += "detonado el pastel ubicado en la silla #" + pSilla1;
                    break;
            }
        }
        return entrada;
    }

    /**
     * Retorna una lista con todos los mafiosos de la partida.
     * @return Lista de mafiosos
     */
    public List<Mafioso> darMafiosos( )
    {
        List<Mafioso> mafiosos = new ArrayList<>( );
        mafiosos.addAll( jugador.darMafiosos( ) );
        for( int i = 0; i < oponentes.length; i++ )
        {
            mafiosos.addAll( oponentes[ i ].darMafiosos( ) );
        }
        return mafiosos;
    }

    /**
     * Retorna los jugadores de la partida ordenados seg�n el color.
     * @return Lista con los jugadores.
     */
    public List<Jugador> darJugadores( )
    {
        List<Jugador> respuesta = new ArrayList<>( );
        for( int i = 0; i < 3; i++ )
        {
            respuesta.add( new Jugador( "null" ) );
        }
        respuesta.set( jugador.darColor( ) - 1, jugador );
        respuesta.set( oponentes[ 0 ].darColor( ) - 1, oponentes[ 0 ] );
        respuesta.set( oponentes[ 1 ].darColor( ) - 1, oponentes[ 1 ] );

        return respuesta;
    }

    /**
     * Retorna el mafioso que est� ubicado en la silla dada.
     * @param pSilla Silla de la cual se quiere obtener el mafioso. pSilla > 0.
     * @return El mafioso sentado en la silla o null si la silla esta vac�a.
     */
    public Mafioso darMafioso( int pSilla )
    {
        Mafioso respuesta = null;
        List<Mafioso> mafiosos = darMafiosos( );
        for( int i = 0; i < mafiosos.size( ) && respuesta == null; i++ )
        {
            Mafioso actual = mafiosos.get( i );
            if( actual.darUbicacion( ) == pSilla )
            {
                respuesta = actual;
            }
        }
        return respuesta;
    }

    /**
     * Retorna una lista con los indices de las sillas donde se puede realizar la jugada con la carta seleccionada.
     * @return lista con las posibles jugadas.
     */
    public List<Integer> darPosiblesJugadas( )
    {
        List<Integer> respuesta = new ArrayList<>( );
        if( enTurno( ) && cartaSeleccionada != null )
        {
            if( cartaSeleccionada.equals( CUCHILLO ) )
            {
                for( Mafioso maf : jugador.darMafiosos( ) )
                {
                    if( maf.darTipo( ).equals( Mafioso.PINCHORIZZO ) || elementos[ maf.darUbicacion( ) - 1 ] == Elemento.CUCHILLO )
                    {
                        int silla = maf.darUbicacion( ) - 1; // Silla izquierda
                        if( silla == 0 )
                        {
                            silla = CANTIDAD_PUESTOS;
                        }
                        if( darMafioso( silla ) != null )
                        {
                            respuesta.add( silla - 1 );
                        }

                        silla = maf.darUbicacion( ) + 1; // Silla derecha
                        if( silla > CANTIDAD_PUESTOS )
                        {
                            silla = 1;
                        }
                        if( darMafioso( silla ) != null )
                        {
                            respuesta.add( silla - 1 );
                        }
                    }
                }
            }
            else if( cartaSeleccionada.equals( PISTOLA ) )
            {
                int[] parejas = new int[]{ 7, 12, 11, 10, 9, 8, 1, 6, 5, 4, 3, 2 };
                for( Mafioso maf : jugador.darMafiosos( ) )
                {
                    if( maf.darTipo( ).equals( Mafioso.PISTOLON ) || elementos[ maf.darUbicacion( ) - 1 ] == Elemento.PISTOLA )
                    {
                        int pareja = parejas[ maf.darUbicacion( ) - 1 ];
                        if( darMafioso( pareja ) != null )
                        {
                            respuesta.add( pareja - 1 );
                        }
                    }
                }
            }
            else if( cartaSeleccionada.equalsIgnoreCase( PASTEL ) )
            {
                for( int i = 1; i <= CANTIDAD_PUESTOS; i++ )
                {
                    if( !hayPastel( i ) )
                    {
                        respuesta.add( i - 1 );
                    }
                }
            }
            else if( cartaSeleccionada.equals( DETONADOR ) )
            {
                for( int i = 1; i <= CANTIDAD_PUESTOS; i++ )
                {
                    if( hayPastel( i ) )
                    {
                        respuesta.add( i - 1 );
                    }
                }
            }
            else if( cartaSeleccionada.equals( CAMBIAR_SILLA ) && darMafiosos( ).size( ) != CANTIDAD_PUESTOS )
            {

                if( sillaSeleccionada == -1 )
                {
                    for( Mafioso maf : jugador.darMafiosos( ) )
                    {
                        respuesta.add( maf.darUbicacion( ) - 1 );
                    }
                }
                else
                {
                    for( int i = 1; i <= CANTIDAD_PUESTOS; i++ )
                    {
                        if( darMafioso( i ) == null )
                        {
                            respuesta.add( i - 1 );
                        }
                    }
                }
            }
        }
        return respuesta;
    }

    /**
     * Indica si hay un pastel puesto sobre la silla dada.
     * @param pSilla Silla de la cual se quiere conocer si hay pastel.
     * @return True si hay un pastel sobre la silla dada, false en caso contrario.
     */
    private boolean hayPastel( int pSilla )
    {
        return jugador.darUbicacionPastel( ) == pSilla || oponentes[ 0 ].darUbicacionPastel( ) == pSilla || oponentes[ 1 ].darUbicacionPastel( ) == pSilla;
    }

    /**
     * Indica si se el juego ya no puede continuar.<br>
     * @return Retorna true si hay dinero en la mesa y al menos 2 familias vivas, false en caso contrario.
     */
    public boolean puedeContinuar( )
    {
        boolean respuesta = dineroMesa > 0;
        if( respuesta )
        {
            int jugadoresVivos = 0;
            for( Jugador jug : darJugadores( ) )
            {
                if( !jug.darMafiosos( ).isEmpty( ) )
                {
                    jugadoresVivos++;
                }
            }
            respuesta = jugadoresVivos > 1;
        }
        return respuesta;
    }

    /**
     * Elimina la carta que esta seleccionada.<br>
     * <b>pre: </b>Hay una carta seleccionada.<br>
     * <b>post: </b> Se elimino la carta seleccionada del arreglo de cartas.
     */
    private void eliminarCartaSeleccionada( )
    {
        boolean termino = false;
        for( int i = 0; i < cartas.length && !termino; i++ )
        {
            String carta = cartas[ i ];
            if( carta != null && carta.equals( cartaSeleccionada ) )
            {
                cartas[ i ] = null;
                termino = true;
            }

        }

    }

    /**
     * Extrae los mafiosos del mensaje y los agrega al jugador.<br>
     * @param pJugador jugador al cual se agregaran los mafiosos. pJugador != null.
     * @param pMensaje Mensaje recibido del servidor con la informaci�n del jugador. pMensaje != null && pMensaje != "".
     */
    private void extraerMafiosos( Jugador pJugador, String[] pMensaje )
    {
        for( int i = 1; i < pMensaje.length; i++ )
        {
            String mafioso[] = pMensaje[ i ].split( ":" );
            pJugador.agregarMafioso( mafioso[ 0 ], Integer.parseInt( mafioso[ 1 ] ) );
        }
    }

    /**
     * Calcula la cantidad de cartas que hacen falta para completar las que se deben tener siempre.
     * @return Cantidad de cartas restantes
     */
    public int calcularCartasRestantes( )
    {
        int cartasRestantes = 0;
        for( int i = 0; i < cartas.length; i++ )
        {
            String carta = cartas[ i ];
            if( carta == null )
            {
                cartasRestantes++;
            }

        }
        return cartasRestantes;
    }

    /**
     * Recibe la informaci�n que contiene los datos de inicio de la partida.<br>
     * <b>pre: </b> Se ha establecido un canal de comunicaci�n con el servidor.<br>
     * <b>post: </b>Se inicializaron todos los datos de los jugadores, las cartas y el turno. El dinero recolectado del jugador se inicializ� en 0.
     * @throws JuegoMafiaException Si ocurre alg�n error leyendo los mensajes del servidor.
     */
    public void iniciar( ) throws JuegoMafiaException
    {
        // INICIAR_PARTIDA
        String mensaje[] = recibirMensaje( ).split( SEPARADOR_COMANDO );
        Log.i("I/TCP Client", "INICIANDO PARTIDA...");
        // CARTAS|carta1;...;carta5
        mensaje = recibirMensaje( ).split( SEPARADOR_COMANDO )[ 1 ].split( SEPARADOR_PARAMETROS );
        for( int i = 0; i < CANTIDAD_CARTAS; i++ )
        {
            String carta = mensaje[ i ];
            cartas[ i ] = carta;
            Log.i("I/TCP Client", "CARTAS");
        }

        for( int i = 0; i < CANTIDAD_OPONENTES + 1; i++ )

        {
            Log.i("I/TCP Client", "OPONENTES");
            // JUGADOR|nombreFamilia;;;CAPO:silla;;;PISTOLON:silla;;;PINCHORIZZO:silla;;;MATON:silla
            mensaje = recibirMensaje( ).split( SEPARADOR_COMANDO )[ 1 ].split( SEPARADOR_PARAMETROS );
            Log.i("I/TCP Client", "OPONENTES222222");
            String nombreFamilia = mensaje[ 0 ];

            if( nombreFamilia.equals( jugador.darNombreFamilia( ) ) )
            {
                extraerMafiosos( jugador, mensaje );
                jugador.asignarColor( i + 1 );
            }
            else
            {
                Jugador oponente = new Jugador( nombreFamilia );
                oponente.asignarColor( i + 1 );
                extraerMafiosos( oponente, mensaje );
                oponentes[ oponentes[ 0 ] == null ? 0 : 1 ] = oponente;
            }
        }

        recibirTurno( );

    }

    /**
     * Recibe el turno del servidor.
     * @throws JuegoMafiaException Si ocurren errores con la comunicaci�n
     */
    private void recibirTurno( ) throws JuegoMafiaException
    {
        // TURNO|familia
        String[] mensaje = recibirMensaje( ).split( SEPARADOR_COMANDO );
        turno = mensaje[ 1 ];

        estado = ESPERANDO_TURNO;
        if( enTurno( ) )
        {
            estado = EN_TURNO;
        }

    }

    /**
     * Detona el pastel del jugador.
     * @return Mensaje de la bit�cora.
     */
    public String detonarPastel( ) throws IOException {
        String respuesta = darEntradaJugada( jugador, DETONADOR, jugador.darUbicacionPastel( ), -1 );
        procesarJugada( DETONADOR, jugador.darUbicacionPastel( ) );
        enviarMensaje( JUGADA, DETONAR_PASTEL );
        return respuesta;
    }

    /**
     * Recolecta el dinero que le corresponde al jugador y actualiza el total.<br>
     * <b>pre: </b>El jugador est� en turno y el canal de comunicaci�n con el servidor est� abierto. <b>post: </b>El estado del juego cambio a realizando jugada.Se sum� la
     * cantidad de dinero recolectado en el turno a la cantidad de dinero recolectado del jugador. Se resto el dinero recolectado en el turno del dinero de la mesa.
     * @return Dinero recolectado en el turno
     */
    public double recoletarDinero( ) throws IOException {
        estado = REALIZANDO_JUGADA;
        double dinero = 0;
        int baresControlados = 0;
        int casinosControlados = 0;
        int multiplicador = 1;
        for( Mafioso mafioso : jugador.darMafiosos( ) )
        {
            Elemento elemento = elementos[ mafioso.darUbicacion( ) - 1 ];
            // 1000 si el capo esta vivo
            if( mafioso.darTipo( ).equals( Mafioso.CAPO ) )
            {
                dinero += 1000;
            }
            // 1000 por cada bar que controle
            if( elemento == Elemento.BAR )
            {
                baresControlados++;
                dinero += 1000;
            }
            // 1000 por cada casino que controle
            if( elemento == Elemento.CASINO )
            {
                casinosControlados++;
                dinero += 1000;
            }
            // 2X si controla la caja registradora
            if( elemento == Elemento.CAJA_REGISTRADORA )
            {
                multiplicador = 2;
            }
        }
        // El doble si hay monopolio de bares
        if( baresControlados == 2 )
        {
            dinero += 2000;
        }
        // El doble si hay monopolio de casinos
        if( casinosControlados == 2 )
        {
            dinero += 2000;
        }

        dinero *= multiplicador;

        if( dinero > dineroMesa )
        {
            dinero = dineroMesa;
        }
        dineroRecolectado += dinero;
        dineroMesa -= dinero;
        enviarMensaje( JUGADA, RECOLECTAR_DINERO, dinero + "" );
        return dinero;
    }

    /**
     * Realiza la jugada. Si hay silla seleccionada juega la carta seleccionada sobre la silla, de lo contrario hace un cambio de carta.<br>
     * <b>pre: </b> Hay una carta seleccionada.<br>
     * <b>post: </b> Se elimin� la carta jugada de mis cartas y se reiniciaron los valores de cartaSeleccionada, sillaSeleccionada y sillaDestino.
     */
    public void realizarJugada( ) throws IOException {
        // No es cambio de carta
        if( sillaSeleccionada != -1 )
        {
            procesarJugada( cartaSeleccionada, sillaSeleccionada, sillaDestino );
            // Es cambio de silla
            if( cartaSeleccionada.equals( CAMBIAR_SILLA ) )
            {
                enviarMensaje( JUGADA, CARTA, cartaSeleccionada, sillaSeleccionada + "", sillaDestino + "" );
            }
            // No es cambio de silla
            else
            {
                enviarMensaje( JUGADA, CARTA, cartaSeleccionada, sillaSeleccionada + "" );
            }
        }
        // Es cambio de carta
        else
        {
            enviarMensaje( JUGADA, CAMBIAR_CARTA );
        }
        eliminarCartaSeleccionada( );
        cartaSeleccionada = null;
        sillaSeleccionada = -1;
        sillaDestino = -1;
    }

    /**
     * Ejecuta la acci�n de cambiar de silla como segunda carta.
     */
    public void cambiarSilla( ) throws IOException {
        procesarJugada( cartaSeleccionada, sillaSeleccionada, sillaDestino );
        eliminarCartaSeleccionada( );
        enviarMensaje( JUGADA, CARTA, cartaSeleccionada, sillaSeleccionada + "", sillaDestino + "" );
    }

    /**
     * Recibe las jugadas de los otros jugadores.
     * @return Mensaje indicando que jugada se realiz�.
     * @throws JuegoMafiaException Si ocurren problemas con la comunicaci�n.
     */
    public String recibirJugada( ) throws JuegoMafiaException, IOException {
        String respuesta = null;
        DecimalFormat df = new DecimalFormat( "###,###.##" );
        String[] params = recibirMensaje( ).split( SEPARADOR_COMANDO )[ 1 ].split( SEPARADOR_PARAMETROS );
        if( params[ 0 ].equals( FIN_TURNO ) )
        {
            respuesta = turno + " ha finalizado su turno. ";
            recibirTurno( );
        }
        else if( params[ 0 ].equals( FIN_JUEGO ) )
        {
            estado = FINALIZADO;
            respuesta = finalizarJuego( );
        }
        else if( params[ 0 ].equals( DETONAR_PASTEL ) )
        {
            procesarJugada( DETONADOR, darJugadorEnTurno( ).darUbicacionPastel( ) );
            respuesta = "Ha detonado el pastel de la familia " + darJugadorEnTurno( ) + " que estaba en la silla #" + darJugadorEnTurno( ).darUbicacionPastel( );
        }
        else if( params[ 0 ].equals( RECOLECTAR_DINERO ) )
        {
            double dinero = Double.parseDouble( params[ 1 ] );
            respuesta = String.format( "%s ha recolectado $%s COP.", turno, df.format( dinero ) );
            dineroMesa -= dinero;
        }
        else if( params[ 0 ].equals( CARTA ) )
        {
            respuesta = darEntradaJugada( darJugadorEnTurno( ), params[ 1 ], Integer.parseInt( params[ 2 ] ), params.length == 4 ? Integer.parseInt( params[ 3 ] ) : -1 );
            if( params[ 1 ].equals( CAMBIAR_SILLA ) )
            {
                procesarJugada( params[ 1 ], Integer.parseInt( params[ 2 ] ), Integer.parseInt( params[ 3 ] ) );
            }
            else
            {
                procesarJugada( params[ 1 ], Integer.parseInt( params[ 2 ] ) );
            }
        }
        else if( params[ 0 ].equals( CAMBIAR_CARTA ) )
        {
            respuesta = darEntradaJugada( darJugadorEnTurno( ), null, -1, -1 );
        }

        return respuesta;

    }

    /**
     * Env�a los mensajes de final de turno y recibe las nuevas cartas.<br>
     * <b>pre: </b> Hay una conexi�n con el servidor establecida.
     * @throws JuegoMafiaException Si ocurren errores de comunicaci�n con el servidor.
     */
    public void finalizarTurno( ) throws JuegoMafiaException, IOException {
        // Enviar mensaje de finalizaci�n de turno.
        enviarMensaje( JUGADA, FIN_TURNO );

        // Calcular cartas restantes.
        int cartasRestantes = calcularCartasRestantes( );

        // Recibir cartas restantes.
        while( cartasRestantes-- > 0 )
        {
            String carta = recibirMensaje( ).split( SEPARADOR_COMANDO )[ 1 ];
            for( int i = 0; i < cartas.length; i++ )
            {
                if( cartas[ i ] == null )
                {
                    cartas[ i ] = carta;
                    break;
                }
            }
        }

        cartaSeleccionada = null;
        sillaDestino = -1;
        sillaSeleccionada = -1;

        // Recibir turno
        recibirTurno( );
    }

    /**
     * Env�a los mensajes de finalizaci�n de juego.
     * @return Mensaje con el resultado de la partida..
     * @throws JuegoMafiaException Si ocurren problemas con la comunicaci�n.
     */
    public String finalizarJuego( ) throws JuegoMafiaException, IOException {
        if( estado != FINALIZADO )
        {
            if( enTurno( ) )
            {
                // JUGADA:::FIN_JUEGO
                enviarMensaje( JUGADA, FIN_JUEGO );
            }
            estado = FINALIZADO;
        }

        if( jugador.darMafiosos( ).size( ) > 0 )
        {
            dineroRecolectado += dineroMesa;
        }
        if( !darMafiosos( ).isEmpty( ) )
        {
            dineroMesa = 0;
        }

        // FIN_JUEGO|dineroRecolectado
        enviarMensaje( FIN_JUEGO, "" + dineroRecolectado );

        // GANADOR:::familia;;;dinero
        String params[] = recibirMensaje( ).split( SEPARADOR_COMANDO )[ 1 ].split( SEPARADOR_PARAMETROS );
        return "La familia " + params[ 0 ] + " ha ganado el juego. Recolect� " + params[ 1 ];
    }

    /**
     * Procesa la jugada de una carta sobre unas sillas dadas.
     * @param pCarta Carta que fue jugada. pCarta != null
     * @param pSilla Sillas sobre las cuales se ejecuto la acci�n.
     */
    private void procesarJugada( String pCarta, int... pSilla )
    {
        if( pCarta.equals( CUCHILLO ) || pCarta.equals( PISTOLA ) )
        {
            darMafioso( pSilla[ 0 ] ).darJugador( ).eliminarMafioso( pSilla[ 0 ] );
        }
        else if( pCarta.equals( CAMBIAR_SILLA ) )
        {
            darMafioso( pSilla[ 0 ] ).cambiarUbicacion( pSilla[ 1 ] );
        }
        else if( pCarta.equals( PASTEL ) )
        {
            darJugadorEnTurno( ).cambiarUbicacionPastel( pSilla[ 0 ] );
        }
        else if( pCarta.equals( DETONADOR ) )
        {
            for( Jugador jugTmp : darJugadores( ) )
            {
                if( jugTmp.darUbicacionPastel( ) == pSilla[ 0 ] )
                {
                    jugTmp.cambiarUbicacionPastel( -1 );
                }
            }

            // Mafioso asesinado pastel encima
            Mafioso asesinado = darMafioso( pSilla[ 0 ] );
            if( asesinado != null )
            {
                asesinado.darJugador( ).eliminarMafioso( pSilla[ 0 ] );
            }

            // Mafioso asesinado izquierda del pastel
            int silla = pSilla[ 0 ] - 1;
            if( silla == 0 )
            {
                silla = CANTIDAD_PUESTOS;
            }
            asesinado = darMafioso( silla );
            if( asesinado != null )
            {
                asesinado.darJugador( ).eliminarMafioso( silla );
            }

            // Mafioso asesinado derecha del pastel
            silla = pSilla[ 0 ] + 1;
            if( silla > CANTIDAD_PUESTOS )
            {
                silla = 1;
            }
            asesinado = darMafioso( silla );
            if( asesinado != null )
            {
                asesinado.darJugador( ).eliminarMafioso( silla );
            }
        }

    }

    /**
     * Retorna el primer mensaje recibido desde el servidor.
     * @return Mensaje recibido.
     * @throws JuegoMafiaException Si ocurre un error al recibir el mensaje.
     */
    public String recibirMensaje( ) throws JuegoMafiaException
    {
        try
        {
            String mensaje = in.readLine( );
            return mensaje;
        }
        catch( IOException e )
        {
            throw new JuegoMafiaException( "Error al recibir mensaje. " + e.getMessage( ) );
        }



    }

    /**
     * Env�a un mensaje al servidor con un comando y par�metros dados.
     * @param pComando Comando que se enviar�. pComando != null && pComando != "".
     * @param pParametros Par�metros del comando.
     */
    private void enviarMensaje( String pComando, String... pParametros ) throws IOException {
        String mensaje = pComando;
        if( pParametros.length > 0 )
        {
            mensaje += SEPARADOR_COMANDO;
        }
        for( String parametro : pParametros )
        {
            mensaje += ( mensaje.endsWith( SEPARADOR_COMANDO ) ? "" : SEPARADOR_PARAMETROS ) + parametro;
        }

        Log.i("I/TCP Client", "Send data to server");
        PrintStream output = new PrintStream(canal.getOutputStream());
        output.println(mensaje);
    }

    /**
     * Finaliza la partida y cierra el canal con el servidor.<br>
     * <b>pre: </b> El canal de comunicaci�n con el cliente esta creado.<br>
     * <b>post: </b>Se cambio el estado de la partida a finalizada y se cerro la conexi�n con el servidor.
     */
    public void finalizar( )
    {
        try
        {
            in.close( );
            out.close( );
            canal.close( );
        }
        catch( Exception e )
        {
            e.printStackTrace( );
        }
    }

}
