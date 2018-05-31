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

/**
 * Clase que representa un mafioso de una familia.
 */
public class Mafioso
{

    // -----------------------------------------------------------------
    // Constantes
    // -----------------------------------------------------------------

    /**
     * Indica el tipo de mafioso CAPO;
     */
    public static final String CAPO = "CAPO";

    /**
     * Indica el tipo de mafioso PISTOLON;
     */
    public static final String PISTOLON = "PISTOLON";

    /**
     * Indica el tipo de mafioso PINCHORIZZO;
     */
    public static final String PINCHORIZZO = "PINCHORIZZO";

    /**
     * Indica el tipo de mafioso MATON;
     */
    public static final String MATON = "MATON";

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Jugador a la cual pertenece el mafioso.
     */
    private Jugador jugador;

    /**
     * Tipo del mafioso
     */
    private String tipo;

    /**
     * N�mero de silla en el que esta ubicado el mafioso.
     */
    private int ubicacion;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye un mafioso con los valores dados por par�metro.<br>
     * <b>post:</b> El nombre de la familia, el tipo y la ubicaci�n fueron inicializados con los valores dados por par�metro.<br>
     * @param pJugador Jugador al cual pertenece el mafioso. pNombreFamilia != null && pNombreFamilia != "".
     * @param pTipo Tipo del mafioso. pTipo pertenece a {CAPO, PISTOLON, PINCHORIZZO, MATON}.
     * @param pUbicacion Silla donde esta ubicado el mafioso. pUbicacion > 0.
     */
    public Mafioso( Jugador pJugador, String pTipo, int pUbicacion )
    {
        jugador = pJugador;
        tipo = pTipo;
        ubicacion = pUbicacion;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el jugador del mafioso.
     * @return Jugador del mafioso.
     */
    public Jugador darJugador( )
    {
        return jugador;
    }

    /**
     * Retorna el tipo del mafioso.
     * @return tipo del mafioso.
     */
    public String darTipo( )
    {
        return tipo;
    }

    /**
     * Retorna la ubicaci�n del mafioso.
     * @return ubicaci�n del mafioso.
     */
    public int darUbicacion( )
    {
        return ubicacion;
    }

    /**
     * Cambia la ubicaci�n del mafioso.<br>
     * <b>post:</b> Se cambi� la ubicaci�n del mafioso por el valor dado por par�metro.
     * @param pUbicacion Silla donde esta ubicado el mafioso. pUbicacion > 0.
     */
    public void cambiarUbicacion( int pUbicacion )
    {
        ubicacion = pUbicacion;
    }

    /**
     * Construye y retorna la ruta de la imagen del mafioso.
     * @return ruta de la imagen del mafioso.
     */
    public String darRutaImagen( )
    {
        String rot = "";
        if( ubicacion > 7 )
        {
            rot = "-180";
        }
        else if( ubicacion == 1 )
        {
            rot = "-90";
        }
        else if( ubicacion == 7 )
        {
            rot = "-90-";
        }
        return String.format( "./data/imagenes/mafiosos/%s%d%s.png", tipo.toLowerCase( ), jugador.darColor( ), rot );
    }

    /**
     * Retorna el nombre de la familia, el tipo de mafioso y la ubicaci�n separado por dos puntos (:).
     */
    @Override
    public String toString( )
    {
        return String.format( "%s:%s:%d", jugador.darNombreFamilia( ), tipo, ubicacion );
    }

}
