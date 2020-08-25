/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

import modele.*;

import java.awt.*;
import java.util.Observable;
import java.util.Observer;

import modele.PkgMur.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 *
 * @author yisakorweldegebriel
 */
public class VueControleurFx extends Application{
    
    public final int PIXEL_SIZE_X = 20;
    public final int PIXEL_SIZE_Y = 20;

    public final int SCENE_WIDTH = 620;
    public final int SCENE_HEIGHT = 640;

    public final int SIZE_X = (SCENE_WIDTH / PIXEL_SIZE_X) + 1;
    public final int SIZE_Y = (SCENE_HEIGHT / PIXEL_SIZE_Y);

    @Override
    public void start(Stage primaryStage) {
        
        Jeu game = new Jeu();
        
        GridPane grid = new GridPane(); // création de la grille
        
        Image imPM = new Image("Images/ressource/Pacman.png"); // préparation des images
        Image imPMN = new Image("Images/ressource/PacmanNord.png");
        Image imPMS = new Image("Images/ressource/PacmanSud.png");
        Image imPMO = new Image("Images/ressource/PacmanOuest.png");
        Image imVide = new Image("Images/ressource/Vide.png");
        //Image imMur = new Image("ressource/Mur.png");
        Image imGomme = new Image("Images/ressource/Gomme.png");
        Image imSuperGomme = new Image("Images/ressource/SuperGomme.png");

        // Wall images plein
        Image imMur = new Image("Images/ressource/wall/MurPlein.png");
        // Wall image bord
        Image imMurBordUp = new Image("Images/ressource/wall/BordUp.png");
        Image imMurBordDown = new Image("Images/ressource/wall/BordDown.png");
        Image imMurBordLeft = new Image("Images/ressource/wall/BordLeft.png");
        Image imMurBordRight = new Image("Images/ressource/wall/BordRight.png");
        // Wall image Coin
        Image imMurCoinUpLeft = new Image("Images/ressource/wall/CoinUpLeft.png");
        Image imMurCoinUpRight = new Image("Images/ressource/wall/CoinUpRight.png");
        Image imMurCoinDownLeft = new Image("Images/ressource/wall/CoinDownLeft.png");
        Image imMurCoinDownRight = new Image("Images/ressource/wall/CoinDownRight.png");
        // Wall image Coin Interne
        Image imCaseInternalCornerUpLeft = new Image("Images/ressource/wall/CaseInternalCornerUpLeft.png");
        Image imCaseInternalCornerUpRight = new Image("Images/ressource/wall/CaseInternalCornerUpRight.png");
        Image imCaseInternalCornerDownLeft = new Image("Images/ressource/wall/CaseInternalCornerDownLeft.png");
        Image imCaseInternalCornerDownRight = new Image("Images/ressource/wall/CaseInternalCornerDownRight.png");
        // Wall image 1 case plein
        Image im1CasePleinHorizontal = new Image("Images/ressource/wall/Plein1CaseHorizontal.png");
        Image im1CasePleinVertical = new Image("Images/ressource/wall/Plein1CaseVertical.png");
        Image imCoin1CaseUpLeft = new Image("Images/ressource/wall/Coin1CaseUpLeft.png");
        Image imCoin1CaseUpRight = new Image("Images/ressource/wall/Coin1CaseUpRight.png");
        Image imCoin1CaseDownLeft = new Image("Images/ressource/wall/Coin1CaseDownLeft.png");
        Image imCoin1CaseDownRight = new Image("Images/ressource/wall/Coin1CaseDownRight.png");
        Image Case1ReversPleinLeft = new Image("Images/ressource/wall/Case1ReversPleinLeft.png");
        Image Case1ReversPleinRight = new Image("Images/ressource/wall/Case1ReversPleinRight.png");

        // Image pour Game Over
        Image imGameOver = new Image("Images/ressource/gameOver.jpg");
        Image imV = new Image("Images/ressource/Victoire.jpg");
        ImageView imGM = new ImageView();
        imGM.setFitWidth(560);
        imGM.setFitHeight(640);

        // Ghost images
        Image imGhostCyanUp = new Image("Images/ressource/cyanUp.png");
        Image imGhostCyanBottom = new Image("Images/ressource/cyanBottom.png");
        Image imGhostCyanLeft = new Image("Images/ressource/cyanLeft.png");
        Image imGhostCyanRight = new Image("Images/ressource/cyanRight.png");

        Image imGhostPinkUp = new Image("Images/ressource/pinkUp.png");
        Image imGhostPinkBottom = new Image("Images/ressource/pinkBottom.png");
        Image imGhostPinkLeft = new Image("Images/ressource/pinkLeft.png");
        Image imGhostPinkRight = new Image("Images/ressource/pinkRight.png");

        Image imGhostOrangeUp = new Image("Images/ressource/orangeUp.png");
        Image imGhostOrangeBottom = new Image("Images/ressource/orangeBottom.png");
        Image imGhostOrangeLeft = new Image("Images/ressource/orangeLeft.png");
        Image imGhostOrangeRight = new Image("Images/ressource/orangeRight.png");

        Image imGhostRougeUp = new Image("Images/ressource/rougeUp.png");
        Image imGhostRougeBottom = new Image("Images/ressource/rougeBottom.png");
        Image imGhostRougeLeft = new Image("Images/ressource/rougeLeft.png");
        Image imGhostRougeRight = new Image("Images/ressource/rougeRight.png");

        Image imGhostUp = new Image("Images/ressource/DEADGHOST1.png");
        Image imGhostBottom = new Image("Images/ressource/DEADGHOST2.png");
        Image imGhostLeft = new Image("Images/ressource/DEADGHOST3.png");
        Image imGhostRight = new Image("Images/ressource/DEADGHOST4.png");

        Image imHeart = new Image("Images/ressource/heart.png");
        Image imScore1 = new Image("Images/ressource/score1.png");
        Image imScore2 = new Image("Images/ressource/score2.png");
        Image imScore3 = new Image("Images/ressource/score3.png");
        
        String musicFile = "src/ressource/music.mp3";     // For example

        Image[] imNumbers = new Image[10];
        for(int i = 0; i < 10; i++)
        {
           imNumbers[i] = new Image("Images/ressource/numbers/" + i + ".png");
        }


        ImageView[][] tab = new ImageView[SIZE_X][SIZE_Y];


        for (int i = 0; i < SIZE_X; i++) {

            for (int j = 0; j < SIZE_Y; j++) {

                ImageView img = new ImageView();

                img.setFitWidth(PIXEL_SIZE_X);
                img.setFitHeight(PIXEL_SIZE_Y);

                tab[i][j] = img;

                if(i <= 27) {
                    grid.add(img, i, j);
                    tab[i][j].setImage(imVide);
                }
            }
        }

        for(int i = 0; i < game.getPacman().getNbVies(); i++) {

            tab[i][31].setImage(imHeart);
        }

        tab[22][31].setImage(imScore1);
        tab[23][31].setImage(imScore2);
        tab[24][31].setImage(imScore3);
        
        
        for (int i = 0; i < SIZE_X; i++) {

            for (int j = 0; j < SIZE_Y; j++) {

                if (game.getTabEntite()[i][j] instanceof CaseMur) {

                    tab[i][j].setImage(imMur);
                }
                if (game.getTabEntite()[i][j] instanceof CaseGomme) {

                    tab[i][j].setImage(imGomme);
                }
                if (game.getTabEntite()[i][j] instanceof CaseSuperGomme) {

                    tab[i][j].setImage(imSuperGomme);
                }
                if(game.getTabEntite()[i][j] instanceof CaseMurUp)
                {
                    tab[i][j].setImage(imMurBordUp);
                }
                if(game.getTabEntite()[i][j] instanceof CaseMurDown)
                {
                    tab[i][j].setImage(imMurBordDown);
                }
                if(game.getTabEntite()[i][j] instanceof CaseMurLeft)
                {
                    tab[i][j].setImage(imMurBordLeft);
                }
                if(game.getTabEntite()[i][j] instanceof CaseMurRight)
                {
                    tab[i][j].setImage(imMurBordRight);
                }
                if(game.getTabEntite()[i][j] instanceof CaseCoinUpLeft)
                {
                    tab[i][j].setImage(imMurCoinUpLeft);
                }
                if(game.getTabEntite()[i][j] instanceof CaseCoinUpRight)
                {
                    tab[i][j].setImage(imMurCoinUpRight);
                }
                if(game.getTabEntite()[i][j] instanceof CaseCoinDownLeft)
                {
                    tab[i][j].setImage(imMurCoinDownLeft);
                }
                if(game.getTabEntite()[i][j] instanceof CaseCoinDownRight)
                {
                    tab[i][j].setImage(imMurCoinDownRight);
                }
                if(game.getTabEntite()[i][j] instanceof CaseInternalCornerUpLeft)
                {
                    tab[i][j].setImage(imCaseInternalCornerUpLeft);
                }
                if(game.getTabEntite()[i][j] instanceof CaseInternalCornerUpRight)
                {
                    tab[i][j].setImage(imCaseInternalCornerUpRight);
                }
                if(game.getTabEntite()[i][j] instanceof CaseInternalCornerDownLeft)
                {
                    tab[i][j].setImage(imCaseInternalCornerDownLeft);
                }
                if(game.getTabEntite()[i][j] instanceof CaseInternalCornerDownRight)
                {
                    tab[i][j].setImage(imCaseInternalCornerDownRight);
                }
                if(game.getTabEntite()[i][j] instanceof Case1PleinHorizontal)
                {
                    tab[i][j].setImage(im1CasePleinHorizontal);
                }
                if(game.getTabEntite()[i][j] instanceof Case1PleinVertical)
                {
                    tab[i][j].setImage(im1CasePleinVertical);
                }
                if(game.getTabEntite()[i][j] instanceof Coin1CaseUpLeft)
                {
                    tab[i][j].setImage(imCoin1CaseUpLeft);
                }
                if(game.getTabEntite()[i][j] instanceof Coin1CaseUpRight)
                {
                    tab[i][j].setImage(imCoin1CaseUpRight);
                }
                if(game.getTabEntite()[i][j] instanceof Coin1CaseDownLeft)
                {
                    tab[i][j].setImage(imCoin1CaseDownLeft);
                }
                if(game.getTabEntite()[i][j] instanceof Coin1CaseDownRight)
                {
                    tab[i][j].setImage(imCoin1CaseDownRight);
                }
                if(game.getTabEntite()[i][j] instanceof modele.PkgMur.Case1ReversPleinLeft)
                {
                    tab[i][j].setImage(Case1ReversPleinLeft);
                }
                if(game.getTabEntite()[i][j] instanceof modele.PkgMur.Case1ReversPleinRight)
                {
                    tab[i][j].setImage(Case1ReversPleinRight);
                }
                if(game.getTabEntite()[i][j] instanceof Fantome)
                {
                    tab[i][j].setImage(imGhostCyanUp);
                }
                if(game.getTabEntite()[i][j] instanceof Pacman)
                {
                    tab[i][j].setImage(imPM);
                }
            }
        }
        
        game.addObserver(new Observer() {
            
            @Override
            public void update(Observable o, Object arg) {
                if (!game.isGameOver()) {
                    
                    for(int i = 0; i < game.getPacman().getNbVies(); i++) {

                        tab[i][31].setImage(imHeart);
                    }
                     for (int i = 0; i < SIZE_X; i++) {

                        for (int j = 0; j < SIZE_Y; j++) {
                            if(game.getTabEntite()[i][j] instanceof Fantome)
                            {
                                Fantome f = (Fantome) game.getTabEntite()[i][j];
                                if(!(f.getPeutManger())){
                                    switch(f.getDirection()){
                                                case haut: tab[i][j].setImage(imGhostUp);break;
                                                case bas: tab[i][j].setImage(imGhostBottom);break;
                                                case droite: tab[i][j].setImage(imGhostRight);break;
                                                case gauche: tab[i][j].setImage(imGhostLeft);break;
                                            }
                                }else{
                                    switch(f.getCouleur()){

                                        case cyan:
                                            switch(f.getDirection()){
                                                case haut: tab[i][j].setImage(imGhostCyanUp);break;
                                                case bas: tab[i][j].setImage(imGhostCyanBottom);break;
                                                case droite: tab[i][j].setImage(imGhostCyanRight);break;
                                                case gauche: tab[i][j].setImage(imGhostCyanLeft);break;
                                            }
                                           break;
                                         case orange:
                                            switch(f.getDirection()){
                                                case haut: tab[i][j].setImage(imGhostOrangeUp);break;
                                                case bas: tab[i][j].setImage(imGhostOrangeBottom);break;
                                                case droite: tab[i][j].setImage(imGhostOrangeRight);break;
                                                case gauche: tab[i][j].setImage(imGhostOrangeLeft);break;
                                            }
                                           break;
                                         case rouge:
                                            switch(f.getDirection()){
                                                case haut: tab[i][j].setImage(imGhostRougeUp);break;
                                                case bas: tab[i][j].setImage(imGhostRougeBottom);break;
                                                case droite: tab[i][j].setImage(imGhostRougeRight);break;
                                                case gauche: tab[i][j].setImage(imGhostRougeLeft);break;
                                            }
                                           break;
                                         case rose:
                                            switch(f.getDirection()){
                                                case haut: tab[i][j].setImage(imGhostPinkUp);break;
                                                case bas: tab[i][j].setImage(imGhostPinkBottom);break;
                                                case droite: tab[i][j].setImage(imGhostPinkRight);break;
                                                case gauche: tab[i][j].setImage(imGhostPinkLeft);break;
                                            }
                                           break;
                                    }
                                }
                            }else if(game.getTabEntite()[i][j] instanceof Pacman)
                            
                            {
                                Pacman p = (Pacman) game.getTabEntite()[i][j];
                                switch(p.getDirection()){
                                    case haut: tab[i][j].setImage(imPMN);break;
                                    case bas: tab[i][j].setImage(imPMS);break;
                                    case droite: tab[i][j].setImage(imPM);break;
                                    case gauche: tab[i][j].setImage(imPMO);break;
                                }
                            }else if (game.getTabEntite()[i][j] instanceof CaseGomme) {

                                tab[i][j].setImage(imGomme);
                            }else if (game.getTabEntite()[i][j] instanceof CaseSuperGomme) {

                                tab[i][j].setImage(imSuperGomme);
                            }else if (game.getTabEntite()[i][j] instanceof CaseVide) {

                                tab[i][j].setImage(imVide);
                            }
                        }
                     }
                     
                }
                
            }
        });
        
        
        StackPane root = new StackPane();
        root.getChildren().add(grid);
        game.start();
        

        Scene scene = new Scene(root, 560, 640, Color.BLACK);
        primaryStage.setOnCloseRequest( e -> {
            primaryStage.close();
            System.exit(0);
        });
        primaryStage.setTitle("Pac-Man  YW - MW");
        primaryStage.setScene(scene);
        
        primaryStage.show();
        
        root.setOnKeyPressed(new EventHandler<javafx.scene.input.KeyEvent>() { // on écoute le clavier
            
            
        

            @Override
            public void handle(javafx.scene.input.KeyEvent event) {

                switch (event.getCode()) {
                    case UP:
                        game.getPacman().setDirection(Direction.haut);
                        break;
                    case DOWN:
                        game.getPacman().setDirection(Direction.bas);
                        break;
                    case LEFT:
                        game.getPacman().setDirection(Direction.gauche);
                        break;
                    case RIGHT:
                        game.getPacman().setDirection(Direction.droite);
                        break;
                }
            }
        });
        
        grid.requestFocus();
        
    }
    
    
    
    
    public static void main(String[] args){
        launch(args);
    }
    
}
