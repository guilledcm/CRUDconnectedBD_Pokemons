/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemonModel;

/**
 *
 * @author Vespertino
 */
public class Pokemon {
    int numPokedex;
    String nombrePokemon;
    int level;
    String TipoPokemon;

    public Pokemon() {
    }

    public Pokemon(int numPokedex, String nombrePokemon, int level, String TipoPokemon) {
        this.numPokedex = numPokedex;
        this.nombrePokemon = nombrePokemon;
        this.level = level;
        this.TipoPokemon = TipoPokemon;
    }
    

    public Pokemon(String nombrePokemon, int level, String TipoPokemon) {
        this.nombrePokemon = nombrePokemon;
        this.level = level;
        this.TipoPokemon = TipoPokemon;
    }

    public String getNombrePokemon() {
        return nombrePokemon;
    }

    public void setNombrePokemon(String nombrePokemon) {
        this.nombrePokemon = nombrePokemon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTipoPokemon() {
        return TipoPokemon;
    }

    public void setTipoPokemon(String TipoPokemon) {
        this.TipoPokemon = TipoPokemon;
    }

    public int getNumPokedex() {
        return numPokedex;
    }

    public void setNumPokedex(int numPokedex) {
        this.numPokedex = numPokedex;
    }
    
}
