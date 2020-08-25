/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.util.Random;



/**
 *
 * @author fred
 */
public class Pacman extends Entite {
    private int nbVies = 5;

    
    

    public Pacman(Jeu _jeu) {
        super(_jeu);
        peutManger = false;
        d = Direction.droite;

    }
    
    public void setDirection(Direction _d) {
        d = _d;
    }
    
    public boolean estEnVie(){
        return(nbVies>0);
    }
    
    public int getNbVies(){
        return nbVies;
    }
    
    public void mangerPM(){
        this.nbVies--;
        System.out.println("nb vies: " + nbVies);
    }

    @Override
    public void choixDirection() {
        
    }

}
