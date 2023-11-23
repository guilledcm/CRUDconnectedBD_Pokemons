/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import conexion.Bdconnect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import vista.Vista;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import pokemonModel.Pokemon;

/**
 *
 * @author guilleDCM
 */
public class Controlador implements ActionListener {

    Bdconnect bdconnect;
    Vista vista;

    public Controlador(Bdconnect bdconnect, Vista vista) {
        this.bdconnect = bdconnect;
        this.vista = vista;
        asignarEvents();
    }

    private void asignarEvents() {
        vista.getBtnConectar().addActionListener(this);
        vista.getBtnAñadir().addActionListener(this);
        vista.getBtnEditar().addActionListener(this);
        vista.getBtnBorrar().addActionListener(this);
        vista.getTblPokemons().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    Pokemon pokemonSeleccionado = clickeadoEnTabla();
                    vista.getTxtNumPokedex().setText("" + pokemonSeleccionado.getNumPokedex());
                    vista.getTxtPokemon().setText(pokemonSeleccionado.getNombrePokemon());
                    vista.getTxtLevel().setText("" + pokemonSeleccionado.getLevel());
                    vista.getComboTipo().setSelectedItem(pokemonSeleccionado.getTipoPokemon());
                }
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.getBtnConectar()) {
            conectarBD();
        } else if (e.getSource() == vista.getBtnAñadir()) {
            añadirPokemon();
        } else if (e.getSource() == vista.getBtnEditar()) {
            editarPokemon();
        } else if (e.getSource() == vista.getBtnBorrar()) {
            borrarPokemon();
        }
    }

    private void conectarBD() {
        if (bdconnect.conectar()) {
            System.out.println("Todo guay");
            vista.getTblPokemons().setModel(pokemonJtable());
        } else {
            System.out.println("peto");
        }
    }

    private void añadirPokemon() {
        try {
            String nombre = vista.getTxtPokemon().getText();
            int level = Integer.parseInt(vista.getTxtLevel().getText());
            String tipo = (String) vista.getComboTipo().getSelectedItem();
            Connection conTemp = bdconnect.getConexion();

            String insertSQL = "INSERT INTO pokemon (nombrePokemon, level, tipo) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conTemp.prepareStatement(insertSQL)) {
                pstmt.setString(1, nombre);
                pstmt.setInt(2, level);
                pstmt.setString(3, tipo);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Pokémon añadido exitosamente");
            }
            vista.getTblPokemons().setModel(pokemonJtable());

        } catch (SQLException ex) {
            System.err.println("Error al añadir Pokémon: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al añadir Pokémon");
        }
    }

    private void editarPokemon() {
        try {
            int numPokedex = Integer.parseInt(vista.getTxtNumPokedex().getText());
            String nombre = vista.getTxtPokemon().getText();
            int level = Integer.parseInt(vista.getTxtLevel().getText());
            String tipo = (String) vista.getComboTipo().getSelectedItem();
            Connection conTemp = bdconnect.getConexion();

            String updateSQL = "UPDATE pokemon SET nombrePokemon=?, level=?, tipo=? WHERE numPokedex=?";
            try (PreparedStatement pstmt = conTemp.prepareStatement(updateSQL)) {
                pstmt.setString(1, nombre);
                pstmt.setInt(2, level);
                pstmt.setString(3, tipo);
                pstmt.setInt(4, numPokedex);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Pokémon editado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el Pokémon con el número de Pokédex especificado");
                }
            }
            vista.getTblPokemons().setModel(pokemonJtable());

        } catch (SQLException ex) {
            System.err.println("Error al editar Pokémon: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al editar Pokémon");
        }
    }

    private void borrarPokemon() {
        try {
            Pokemon pok = clickeadoEnTabla();
            int numPokedex = pok.getNumPokedex();
            numPokedex = Integer.parseInt(vista.getTxtNumPokedex().getText());

            Connection conTemp = bdconnect.getConexion();

            String deleteSQL = "DELETE FROM pokemon WHERE numPokedex=?";
            try (PreparedStatement pstmt = conTemp.prepareStatement(deleteSQL)) {
                pstmt.setInt(1, numPokedex);
                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Pokémon borrado exitosamente");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró el Pokémon con el número de Pokédex especificado");
                }
            }
            vista.getTblPokemons().setModel(pokemonJtable());

        } catch (SQLException ex) {
            System.err.println("Error al borrar Pokémon: " + ex.getMessage());
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al borrar Pokémon");
        }
    }

    public DefaultTableModel pokemonJtable() {
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("NumPokedex");
        tabla.addColumn("Pokemon");
        tabla.addColumn("Level");
        tabla.addColumn("Tipo");

        Connection con = bdconnect.getConexion();

        String[] data = new String[4];
        String sql = "Select * from pokemon";
        try {
            PreparedStatement instruccion = con.prepareStatement(sql);

            ResultSet rs = instruccion.executeQuery();
            while (rs.next()) {
                data[0] = "" + rs.getInt("numPokedex");
                data[1] = rs.getString("nombrePokemon");
                data[2] = "" + rs.getInt("level");
                data[3] = rs.getString("tipo");

                Pokemon pokemon = new Pokemon(rs.getInt("numPokedex"), rs.getString("nombrePokemon"),
                        rs.getInt("level"), rs.getString("tipo"));

                tabla.addRow(new Object[]{pokemon.getNumPokedex(), pokemon.getNombrePokemon(),
                    pokemon.getLevel(), pokemon.getTipoPokemon()});
            }
            instruccion.close();
            return tabla;

        } catch (SQLException ex) {
            System.out.println("Error in table data retrieval");
            ex.printStackTrace();
        }
        return null;
    }

    public Pokemon clickeadoEnTabla() {
        int row = vista.getTblPokemons().getSelectedRow();
        int numPokedex = Integer.parseInt(vista.getTblPokemons().getValueAt(row, 0).toString()); 
        String nombre = vista.getTblPokemons().getValueAt(row, 1).toString();
        int level = Integer.parseInt(vista.getTblPokemons().getValueAt(row, 2).toString());
        String tipo = vista.getTblPokemons().getValueAt(row, 3).toString();

        return new Pokemon(numPokedex, nombre, level, tipo);
    }

}
