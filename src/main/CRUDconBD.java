/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import conexion.Bdconnect;
import controlador.Controlador;
import vista.Vista;

/**
 *
 * @author Vespertino
 */
public class CRUDconBD {
    public static void main(String[] args) {
        Vista vista = new Vista();
        vista.setVisible(true);
        Bdconnect bdcon = new Bdconnect();
        Controlador controlador = new Controlador(bdcon,vista);
    }
}
