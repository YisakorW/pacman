/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

/*import java.util.Observable;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;*/


/**
 *
 * @author freder
 */
public abstract class Entite extends Element implements Runnable {

    protected Jeu jeu;
    protected Direction d;
    protected boolean peutManger;
    
    public abstract void choixDirection(); // stratégie de déclassement définie dans les sous-classes, concernant Pacman, ce sont les évènements clavier qui définissent la direction
    
    public void avancerDirectionChoisie() {
        jeu.deplacerEntite(this, d);
    }
    
    
    public Entite(Jeu _jeu) {
        
        jeu = _jeu;

    }
    
    
    public Direction getDirection(){
        return this.d;
    }
    
        
    public void setPeutManger(boolean b){
        peutManger = b;
    }
    
    public boolean getPeutManger(){
        return peutManger;
    }

    
    @Override
    public void run() {

        choixDirection();
        avancerDirectionChoisie();
    }
    
}
