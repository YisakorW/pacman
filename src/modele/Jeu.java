/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele;

import java.awt.Point;
import java.util.HashMap;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

import modele.*;
import modele.PkgMur.*;

/** La classe Jeu a deux fonctions 
 *  (1) Gérer les aspects du jeu : condition de défaite, victoire, nombre de vies
 *  (2) Gérer les coordonnées des entités du monde : déplacements, collisions, perception des entités, ... 
 *
 * @author freder
 */
public class Jeu extends Observable implements Runnable {

    public static final int SIZE_X = 32;
    public static final int SIZE_Y = 32;
    
    
    private int nbFantome, score, nbGommes, nbSuperGommes;
    private Pacman pm;
    private boolean gameOver;
    private Fantome[] tabFantome = new Fantome[4] ;
   
    

    private HashMap<Entite, Point> map = new  HashMap<Entite, Point>(); // permet de récupérer la position d'une entité à partir de sa référence
    private Element[][] grilleEntites = new Element[SIZE_X][SIZE_Y]; // permet de récupérer une entité à partir de ses coordonnées
    
    // TODO : ajouter les murs, couloir, PacGums, et adapter l'ensemble des fonctions (prévoir le raffraichissement également du côté de la vue)
    
    
      /*
     * 0 = Case Vide
     * 1 = Case Mur
     * 2 = Case Gomme
     * 3 = Case Super Gomme
     * 6 = Case Teleport
     * 10 = Mur bord Up
     * 11 = Mur bord Down
     * 12 = Mur bord Gauche
     * 13 = Mur bord Droite
     * 14 = Coin bord Haut Gauche
     * 15 = Coin bord Haut Droit
     * 16 = Coin bord Bas Gauche
     * 17 = Coin bord Bas Droit
     * 20 = Corner interne Haut Gauche
     * 21 = Corner interne Haut Droit
     * 22 = Corner interne Bas Gauche
     * 23 = Corner interne Bas Droit
     * 30 = 1 case plein horizontal
     * 31 = 1 case plein vertical
     * 32 = Coin 1 case Haut Gauche
     * 33 = Coin 1 case Haut Droit
     * 34 = Coin 1 case Bas Gauche
     * 35 = Coin 1 case Bas Droit
     * 36 = Revers 1 case plein gauche
     * 37 = Revers 1 case plein droit
     */
    private int[][] grid = new int[][]
            {
                    {23, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 22, 23, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 11, 22,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 14, 10, 10, 15, 2 , 14, 10, 10, 10, 15, 2 , 12, 13, 2 , 14, 10, 10, 10, 15, 2 , 14, 10, 10, 15, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 3 , 12, 1 , 1 , 13, 2 , 12, 1 , 1 , 1 , 13, 2 , 12, 13, 2 , 12, 1 , 1 , 1 , 13, 2 , 12, 1 , 1 , 13, 3 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 16, 11, 11, 17, 2 , 16, 11, 11, 11, 17, 2 , 16, 17, 2 , 16, 11, 11, 11, 17, 2 , 16, 11, 11, 17, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12,0, 0, 0, 0 },
                    {13, 2 , 14, 10, 10, 15, 2 , 14, 15, 2 , 14, 10, 10, 10, 10, 10, 10, 15, 2 , 14, 15, 2 , 14, 10, 10, 15, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 16, 11, 11, 17, 2 , 12, 13, 2 , 16, 11, 11, 22, 23, 11, 11, 17, 2 , 12, 13, 2 , 16, 11, 11, 17, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 2 , 2 , 12,0 ,0 ,0 ,0 },
                    {21, 10, 10, 10, 10, 15, 2 , 12, 21, 10, 10, 15, 2 , 12, 13, 2 , 14, 10, 10, 20, 13, 2 , 14, 10, 10, 10, 10, 20,0 ,0 ,0 ,0 },
                    {1 , 1 , 1 , 1 , 1 , 13, 2 , 12, 23, 11, 11, 17, 2 , 16, 17, 2 , 16, 11, 11, 22, 13, 2 , 12, 1 , 1 , 1 , 1 , 1 ,0 ,0 ,0 ,0 },
                    {1 , 1 , 1 , 1 , 1 , 13, 2 , 12, 13, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 12, 13, 2 , 12, 1 , 1 , 1 , 1 , 1 ,0 ,0 ,0 ,0 },
                    {1 , 1 , 1 , 1 , 1 , 13, 2 , 12, 13, 0 , 32, 30, 37, 0 , 0 , 36, 30, 33, 0 , 12, 13, 2 , 12, 1 , 1 , 1 , 1 , 1 ,0 ,0 ,0 ,0 },
                    {11, 11, 11, 11, 11, 17, 2 , 16, 17, 0 , 31, 0 , 0 , 0 , 0 , 0 , 0 , 31, 0 , 16, 17, 2 , 16, 11, 11, 11, 11, 11,0 ,0 ,0 ,0 },
                    {6 , 0 , 0 , 0 , 0 , 0 , 2 , 0 , 0 , 0 , 31, 0 , 0 , 0 , 0 , 0 , 0 , 31, 0 , 0 , 0 , 2 , 0 , 0 , 0 , 0 , 0 , 6 ,11 ,0 ,0 ,0 },
                    {10, 10, 10, 10, 10, 15, 2 , 14, 15, 0 , 31, 0 , 0 , 0 , 0 , 0 , 0 , 31, 0 , 14, 15, 2 , 14, 10, 10, 10, 10, 10,0 ,0 ,0 ,0 },
                    {1 , 1 , 1 , 1 , 1 , 13, 2 , 12, 13, 0 , 34, 30, 30, 30, 30, 30, 30, 35, 0 , 12, 13, 2 , 12, 1 , 1 , 1 , 1 , 1 ,0 ,0 ,0 ,0 },
                    {1 , 1 , 1 , 1 , 1 , 13, 2 , 12, 13, 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 12, 13, 2 , 12, 1 , 1 , 1 , 1 , 1 ,0 ,0 ,0 ,0 },
                    {1 , 1 , 1 , 1 , 1 , 13, 2 , 12, 13, 0 , 14, 10, 10, 10, 10, 10, 10, 15, 0 , 12, 13, 2 , 12, 1 , 1 , 1 , 1 , 1 ,0 ,0 ,0 ,0 },
                    {23, 11, 11, 11, 11, 17, 2 , 16, 17, 0 , 16, 11, 11, 22, 23, 11, 11, 17, 0 , 16, 17, 2 , 16, 11, 11, 11, 11, 22,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 14, 10, 10, 15, 2 , 14, 10, 10, 10, 15, 2 , 12, 13, 2 , 14, 10, 10, 10, 15, 2 , 14, 10, 10, 15, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 16, 11, 22, 13, 2 , 16, 11, 11, 11, 17, 2 , 16, 17, 2 , 16, 11, 11, 11, 17, 2 , 12, 23, 11, 17, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 12,0 ,0 ,0 ,0 },
                    {21, 10, 15, 2 , 12, 13, 2 , 14, 15, 2 , 14, 10, 10, 10, 10, 10, 10, 15, 2 , 14, 15, 2 , 12, 13, 2 , 14, 10, 20,0 ,0 ,0 ,0 },
                    {23, 11, 17, 2 , 16, 17, 2 , 12, 13, 2 , 16, 11, 11, 22, 23, 11, 11, 17, 2 , 12, 13, 2 , 16, 17, 2 , 16, 11, 22,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 12, 13, 2 , 2 , 2 , 2 , 2 , 2 , 12,0 ,0 ,0 ,0 },
                    {13, 3 , 14, 10, 10, 10, 10, 20, 21, 10, 10, 15, 2 , 12, 13, 2 , 14, 10, 10, 20, 21, 10, 10, 10, 10, 15, 3 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 16, 11, 11, 11, 11, 11, 11, 11, 11, 17, 2 , 16, 17, 2 , 16, 11, 11, 11, 11, 11, 11, 11, 11, 17, 2 , 12,0 ,0 ,0 ,0 },
                    {13, 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 2 , 12,0 ,0 ,0 ,0 },
                    {21, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 20,0 ,0 ,0 ,0 },
                    {0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 , 0 ,0 ,0 ,0 ,0 }
            };
    
    CaseVide caseV = new CaseVide();
    CaseGomme caseG = new CaseGomme();
    CaseSuperGomme caseSuperG = new CaseSuperGomme();
    
    public Pacman getPacman(){
        return pm;
    }
    
    public Element[][] getTabEntite(){
        return this.grilleEntites;
    }
    
    public Jeu() {
        
        
        initialisationDesEntites();
        
    }
    
    public boolean isGameOver(){
        
        if(pm.getNbVies() > 0) gameOver = false;
        else
            gameOver = true;
        return this.gameOver;
    }
    
    
    
    public Element[][] getGrille() {
        return grilleEntites;
    }
    
    
    public HashMap<Entite, Point> getMap(){
        return map;
    }
    
    public int getNbGommes(){
        return nbGommes;
    }
    public int getNbSuperGommes(){
        return nbSuperGommes;
    }
    
    private void initialisationDesEntites() {
        
        
       
        
        
        CaseMur caseM = new CaseMur();
        CaseTP caseT = new CaseTP();

        CaseMurUp caseMU = new CaseMurUp();
        CaseMurDown caseMD = new CaseMurDown();
        CaseMurLeft caseML = new CaseMurLeft();
        CaseMurRight caseMR = new CaseMurRight();

        CaseCoinDownLeft caseCDL = new CaseCoinDownLeft();
        CaseCoinDownRight caseCDR = new CaseCoinDownRight();
        CaseCoinUpLeft caseCUL = new CaseCoinUpLeft();
        CaseCoinUpRight caseCUR = new CaseCoinUpRight();

        CaseInternalCornerDownLeft caseICDL = new CaseInternalCornerDownLeft();
        CaseInternalCornerDownRight caseICDR = new CaseInternalCornerDownRight();
        CaseInternalCornerUpLeft caseICUL = new CaseInternalCornerUpLeft();
        CaseInternalCornerUpRight caseICUR = new CaseInternalCornerUpRight();


        Case1PleinHorizontal case1PH= new Case1PleinHorizontal();
        Case1PleinVertical case1PV= new Case1PleinVertical();
        Coin1CaseUpLeft caseCoin1UL = new Coin1CaseUpLeft();
        Coin1CaseUpRight caseCoin1UR = new Coin1CaseUpRight();
        Coin1CaseDownLeft caseCoin1DL = new Coin1CaseDownLeft();
        Coin1CaseDownRight caseCoin1DR = new Coin1CaseDownRight();
        Case1ReversPleinLeft caseReversLeft = new Case1ReversPleinLeft();
        Case1ReversPleinRight caseReversRight = new Case1ReversPleinRight();

        for (int i = 0; i < this.SIZE_X; i++) {

            for (int j = 0; j < this.SIZE_Y; j++) {

                switch (this.grid[i][j]) {

                    case 0:
                        grilleEntites[j][i] = caseV;
                        break;
                    case 1:
                        grilleEntites[j][i] = caseM;
                        break;
                    case 2:
                        grilleEntites[j][i] = caseG;
                        nbGommes++;
                        break;
                    case 3:
                        grilleEntites[j][i] = caseSuperG;
                        nbSuperGommes++;
                        break;
                    case 6:
                        grilleEntites[j][i] = caseT;
                        break;
                    case 10:
                        grilleEntites[j][i] = caseMU;
                        break;
                    case 11:
                        grilleEntites[j][i] = caseMD;
                        break;
                    case 12:
                        grilleEntites[j][i] = caseML;
                        break;
                    case 13:
                        grilleEntites[j][i] = caseMR;
                        break;
                    case 14:
                        grilleEntites[j][i] = caseCUL;
                        break;
                    case 15:
                        grilleEntites[j][i] = caseCUR;
                        break;
                    case 16:
                        grilleEntites[j][i] = caseCDL;
                        break;
                    case 17:
                        grilleEntites[j][i] = caseCDR;
                        break;
                    case 20:
                        grilleEntites[j][i] = caseICUL;
                        break;
                    case 21:
                        grilleEntites[j][i] = caseICUR;
                        break;
                    case 22:
                        grilleEntites[j][i] = caseICDL;
                        break;
                    case 23:
                        grilleEntites[j][i] = caseICDR;
                        break;
                    case 30:
                        grilleEntites[j][i] = case1PH;
                        break;
                    case 31:
                        grilleEntites[j][i] = case1PV;
                        break;
                    case 32:
                        grilleEntites[j][i] = caseCoin1UL;
                        break;
                    case 33:
                        grilleEntites[j][i] = caseCoin1UR;
                        break;
                    case 34:
                        grilleEntites[j][i] = caseCoin1DL;
                        break;
                    case 35:
                        grilleEntites[j][i] = caseCoin1DR;
                        break;
                    case 36:
                        grilleEntites[j][i] = caseReversLeft;
                        break;
                    case 37:
                        grilleEntites[j][i] = caseReversRight;
                        break;

                }
            }
        }
      
        
        pm = new Pacman(this);
        grilleEntites[14][17] = pm;
        map.put(pm, new Point(14, 17));
        
        
        this.nbFantome = 4;
        
        for(int i=0; i<this.nbFantome; i++){
            this.tabFantome[i] = new Fantome(this);
            grilleEntites[11+i][11] = tabFantome[i];
            map.put(tabFantome[i], new Point(11+i, 11));
        }
        
        tabFantome[0].setCouleur(CouleurFantome.orange);
        tabFantome[1].setCouleur(CouleurFantome.cyan);
        tabFantome[2].setCouleur(CouleurFantome.rouge);
        tabFantome[3].setCouleur(CouleurFantome.rose);
        
        
    }
    
    
    
    
    
    /** Permet a une entité  de percevoir sont environnement proche et de définir sa strétégie de déplacement 
     * (fonctionalité utilisée dans choixDirection() de Fantôme)
     */
    public Object regarderDansLaDirection(Entite e, Direction d) {
        Point positionEntite = map.get(e);
        return objetALaPosition(calculerPointCible(positionEntite, d));
    }
    
    long debutVulnerable=0;
    
    
    /** Si le déclacement de l'entité est autorisé (pas de mur ou autre entité), il est réalisé
     */
    public boolean deplacerEntite(Entite e, Direction d) {
        
        boolean retour;
        
        Point pCourant = map.get(e);
        
        Point pCible = calculerPointCible(pCourant, d);
        
        
        Case casePrec = null; 
        Object o = null;
        
        o = objetALaPosition(pCible);
        if(o instanceof CaseGomme) casePrec = caseG ;
        if(o instanceof CaseSuperGomme)casePrec = caseSuperG;
        if(o instanceof CaseVide)casePrec = caseV;
        
        
        
        if (contenuDansGrille(pCible) && !(objetALaPosition(pCible) instanceof CaseMur)) { // a adapter (collisions murs, etc.)
            if((System.currentTimeMillis() - debutVulnerable )>=20000){
                    fantomeNonVulnerable();
            }
            deplacerEntite(pCourant, pCible, e, casePrec);
            retour = true;
            if((e instanceof Pacman) && (o instanceof CaseSuperGomme)){
                debutVulnerable = System.currentTimeMillis();
                fantomeVulnerable();
            }
            
          
            
            
        } else {
            retour = false;
        }
        return retour;
    }
    
    
    private Point calculerPointCible(Point pCourant, Direction d) {
        Point pCible = null;
        
        switch(d) {
            case haut: pCible = new Point(pCourant.x, pCourant.y - 1); break;
            case bas : pCible = new Point(pCourant.x, pCourant.y + 1); break;
            case gauche : pCible = new Point(pCourant.x - 1, pCourant.y); break;
            case droite : pCible = new Point(pCourant.x + 1, pCourant.y); break;     
            
        }
        
        return pCible;
    }
    
    

    private void deplacerEntite(Point pCourant, Point pCible, Entite e, Case caseP) {
       
        if(e instanceof Pacman){
            if(caseP instanceof CaseGomme)nbGommes--; score +=1;
            if(caseP instanceof CaseSuperGomme)nbSuperGommes--; score+=5;
            
            if(!(pm.getPeutManger())){
                
                if(objetALaPosition(pCible) instanceof Fantome){
                        pm.mangerPM();
                        grilleEntites[14][17] = pm;
                        grilleEntites[pCourant.x][pCourant.y] = caseV;
                        map.put(pm,new Point(14,17));
                }else{
                    grilleEntites[pCourant.x][pCourant.y] = caseV;
                    grilleEntites[pCible.x][pCible.y] = e;
                    map.put(e, pCible);
                }
                
            }else{
                if(objetALaPosition(pCible) instanceof Fantome){
                    Fantome f = (Fantome) objetALaPosition(pCible);
                    grilleEntites[11][13] = f ;
                    map.put(f, new Point(11,13));
                    grilleEntites[pCourant.x][pCourant.y] = caseV;
                    grilleEntites[pCible.x][pCible.y] = e;
                    map.put(e, pCible);
                    
                    
                    
                }else{
                    grilleEntites[pCourant.x][pCourant.y] = caseV;
                    grilleEntites[pCible.x][pCible.y] = e;
                    map.put(e, pCible);
                }
                
            }
        }else{
              
              
              
              if(!(objetALaPosition(pCible) instanceof Fantome)){
                if(e.getPeutManger()){  
                    
                    if(objetALaPosition(pCible) instanceof Pacman){
                        pm.mangerPM();
                        grilleEntites[14][17] = pm;
                        grilleEntites[pCourant.x][pCourant.y] = caseV;
                        map.put(pm,new Point(14,17));
                    }
                
                    grilleEntites[pCourant.x][pCourant.y] = caseV;
                    grilleEntites[pCible.x][pCible.y] = e;
                    map.put(e, pCible);
                
                
                }else{
                    if(objetALaPosition(pCible) instanceof Pacman){
                        
                        grilleEntites[pCourant.x][pCourant.y] = caseV;
                        grilleEntites[11][13] = e;
                        map.put(e, new Point(11,13));
                    }else{
                        grilleEntites[pCourant.x][pCourant.y] = caseP;
                        grilleEntites[pCible.x][pCible.y] = e;
                        map.put(e, pCible);
                    }
                }
              }
        }
    }
   
    
    
    public void fantomeVulnerable(){ 
            pm.setPeutManger(true);
            for(int i=0; i<nbFantome; i++){
                tabFantome[i].setPeutManger(false); 
                //System.out.println(" fantome " + i + " " + tabFantome[i].getPeutManger());
            }
            //System.out.println(" pacman " + pm.getPeutManger());
            
    }        
      
            
    public void fantomeNonVulnerable(){
            
        pm.setPeutManger(false);
        for(int i=0; i<nbFantome; i++){
            tabFantome[i].setPeutManger(true);
        }
    }    
    
    
    /** Vérifie que p est contenu dans la grille
     */
    private boolean contenuDansGrille(Point p) {
        return p.x >= 0 && p.x < SIZE_X && p.y >= 0 && p.y < SIZE_Y;
    }
    
    private Object objetALaPosition(Point p) {
        Object retour = null;
        
        if (contenuDansGrille(p)) {
            retour = grilleEntites[p.x][p.y];
        }
        
        return retour;
    }
    
    /**
     * Un processus est créé et lancé, celui-ci execute la fonction run()
     */
    public void start() {

        new Thread(this).start();

    }

    @Override
    public void run() {

        while (!isGameOver()) {

            for (Entite e : map.keySet()) { // déclenchement de l'activité des entités, map.keySet() correspond à la liste des entités
                e.run(); 
            }

            setChanged();
            notifyObservers(); // notification de l'observer pour le raffraichisssement graphique

            try {
                Thread.sleep(300); // pause de 0.5s
            } catch (InterruptedException ex) {
                Logger.getLogger(Pacman.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

}
