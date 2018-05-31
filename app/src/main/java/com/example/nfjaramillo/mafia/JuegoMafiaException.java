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
 * Excepci�n que se lanza para indicar un problema en la partida.
 */
@SuppressWarnings("serial")
public class JuegoMafiaException extends Exception
{

    // -----------------------------------------------------------------
    // Constructores
    // -----------------------------------------------------------------

    /**
     * Construye una nueva excepci�n de este tipo con el mensaje indicado.
     * @param pMensaje Mensaje que describe la causa de la excepci�n. pMensaje != null && pMensaje != "".
     */
    public JuegoMafiaException( String pMensaje )
    {
        super( pMensaje );
    }
}
