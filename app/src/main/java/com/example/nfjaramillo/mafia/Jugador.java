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

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un jugador en el juego.
 */
public class Jugador
{

    // -----------------------------------------------------------------
    // Atributos
    // -----------------------------------------------------------------

    /**
     * Color asignado al jugador.
     */
    private int color;

    /**
     * Nombre de la familia del jugador.
     */
    private String nombreFamilia;

    /**
     * Cantidad de victorias del jugador.
     */
    private int victorias;

    /**
     * Cantidad de derrotas del jugador.
     */
    private int derrotas;

    /**
     * Mafiosos de la familia del jugador.
     */
    private List<Mafioso> mafiosos;

    /**
     * Ubicaci�n del pastel del jugador.
     */
    private int ubicacionPastel;

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Crea un nuevo jugador.<br>
     * <b>post: </b> Se asignaron los atributos color, nombreFamilia y nombreCapo con los valores dados por par�metro. Los mafiosos del jugador se inicializaron con una lista.
     * La ubicaci�n del pastel se inicializ� en -1. vac�a.
     * @param pNombreFamilia Nombre de la familia del jugador. pNombreFamilia != null && pNombreFamilia != "".
     */
    public Jugador( String pNombreFamilia )
    {
        nombreFamilia = pNombreFamilia;
        victorias = 0;
        derrotas = 0;
        mafiosos = new ArrayList<>( );
        ubicacionPastel = -1;
    }

    /**
     * Asigna el color dado por par�metro al jugador.<br>
     * <b>post:</b> Se cambi� el color por el valor del par�metro.
     * @param pColor Color del jugador. pColor = {1,2,3}
     */
    public void asignarColor( int pColor )
    {
        color = pColor;
    }

    // -----------------------------------------------------------------
    // M�todos
    // -----------------------------------------------------------------

    /**
     * Retorna el color asignado al jugador.
     * @return color asignado.
     */
    public int darColor( )
    {
        return color;
    }

    /**
     * Retorna el nombre de la familia del jugador.
     * @return nombre de la familia.
     */
    public String darNombreFamilia( )
    {
        return nombreFamilia;
    }

    /**
     * Retorna los mafiosos del jugador.
     * @return Lista con los mafiosos del jugador.
     */
    public List<Mafioso> darMafiosos( )
    {
        return mafiosos;
    }

    /**
     * Retorna la ubicaci�n del pastel del jugador.
     * @return Ubicaci�n del pastel del jugador.
     */
    public int darUbicacionPastel( )
    {
        return ubicacionPastel;
    }

    /**
     * Se asigna la ubicaci�n del pastel por el valor dado.<br>
     * <b>post: </b> La ubicaci�n del pastel se cambi� por el valor dado por par�metro.
     * @param pUbicacion Ubicaci�n el pastel.
     */
    public void cambiarUbicacionPastel( int pUbicacion )
    {
        ubicacionPastel = pUbicacion;
    }

    /**
     * Cambia la cantidad de victorias.<br>
     * <b>post: </b>Se cambi� la cantidad de victorias por el valor dado por par�metro.
     * @param pVictorias Cantidad de victorias del jugador. pVictorias > 0.
     */
    public void cambiarVictorias( int pVictorias )
    {
        victorias = pVictorias;
    }

    /**
     * Cambia la cantidad de derrotas.<br>
     * <b>post: </b>Se cambi� la cantidad de derrotas por el valor dado por par�metro.
     * @param pDerrotas Cantidad de derrotas del jugador. pDerrotas > 0.
     */
    public void cambiarDerrotas( int pDerrotas )
    {
        derrotas = pDerrotas;
    }

    /**
     * Agrega un mafioso a la lista de mafiosos del jugador.<br>
     * <b>pre: </b>La lista de mafiosos est� inicializada.<br>
     * <b>post: </b>Se agreg� un nuevo mafioso con los valores dados a la lista de mafiosos.
     * @param pTipo Tipo del mafioso. pTipo pertenece a {CAPO, PISTOLON, PINCHORIZZO, MATON}.
     * @param pUbicacion Silla donde esta ubicado el mafioso. pUbicacion > 0.
     */
    public void agregarMafioso( String pTipo, int pUbicacion )
    {
        mafiosos.add( new Mafioso( this, pTipo, pUbicacion ) );
    }

    /**
     * Elimina al mafioso que est� sentado en la silla dada.<br>
     * <b>pre: </b>La lista de mafiosos est� inicializada y contiene un mafioso sentado en la silla dada.<br>
     * <b>post: </b>Se elimin� de la lista de mafiosos al mafioso sentado en la silla dada.
     * @param pUbicacion Silla donde esta ubicado el mafioso. pUbicacion > 0.
     * @return Mafioso eliminado.
     */
    public Mafioso eliminarMafioso( int pUbicacion )
    {
        Mafioso respuesta = null;
        for( int i = 0; i < mafiosos.size( ) && respuesta == null; i++ )
        {
            if( mafiosos.get( i ).darUbicacion( ) == pUbicacion )
            {
                respuesta = mafiosos.remove( i );
            }
        }
        return respuesta;
    }

    /**
     * Calcula el porcentaje de efectividad del jugador.
     * @return Porcentaje de efectividad del jugador.
     */
    public double calcularPorcentajeEfectividad( )
    {
        double total = victorias + derrotas;
        return total == 0 ? 0 : victorias * 100.0 / total;
    }

    /**
     * Retorna el nombre de la familia.
     */
    @Override
    public String toString( )
    {
        return nombreFamilia;
    }
}
