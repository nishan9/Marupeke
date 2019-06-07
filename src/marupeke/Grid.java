/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package marupeke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

/**
 *
 * @author 181326
 */
public class Grid {
    Button[][] btn;
    boolean[][] editable;
    /**
     * Creates a new Grid with 2 empty arrays 
     * @param Height 
     * @param Width
     */
    public Grid(int n,int m){
        Button [][] btn = new Button[n][m];
        boolean [][] editable = new boolean[n][m];
    }
    
    /**
     * Creates a random grid with the number of symbols specified 
     * @param Size of the Grid 
     * @param Number of Solids 
     * @param Number of Xs
     * @param Number of Os
     * @return Randomised grid 
     */
    public Button [][] randomiser(int size,int numFill,int numX, int numO){
        Random rand = new Random();

        for(int i=0;i<numFill;i++){
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            btn[x][y].setText("#");
            editable[x][y] =false;
        }

        for(int i=0;i<numX;i++){
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (btn[x][y].getText() == ""){
                btn[x][y].setText("X");
                editable[x][y] = false;
            }else
                --i;
        }

        for(int i=0;i<numO;i++){
            int x = rand.nextInt(size);
            int y = rand.nextInt(size);
            if (btn[x][y].getText() == ""){
                btn[x][y].setText("O");
                editable[x][y] = false;
            }else
                --i;
        }
        
        if (!(isLegal()))
        {
            for (int i = 0; i < btn.length ; i++) {
                for (int j = 0; j < btn.length; j++) {
                    editable[i][j] = true;
                    btn[i][j] = null;
                    btn[i][j] = new Button();
                    btn[i][j].setPrefSize(50, 50);
                    Main.play(i,j);
                }
            }
            btn = randomiser(size,numFill,numX,numO);
        }
        return btn;

    }
    /**
     * Records illegalities in a List
     */
    public String[] illegalities() {
        List<String> problems = new ArrayList();        
        
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[i].length; j++) {
                if (btn[i][j].getText() != "X"
                        && btn[i][j].getText() != "O") {
                    continue;
                }
                if (i > 0 && i < btn.length - 1) {
                    // do horizontal tests
                    if (btn[i - 1][j].getText() == btn[i][j].getText()
                            && btn[i][j].getText() == btn[i + 1][j].getText()) {
                        problems.add("More than two " + btn[i][j].getText() + "  in a vertical row at " + j + "," + i);
                    }
                }
                if (j > 0 && j < btn.length - 1) {
                    // do vertical tests
                    if (btn[i][j - 1].getText() == btn[i][j].getText()
                            && btn[i][j].getText() == btn[i][j + 1].getText()) {
                        problems.add("More than two " + btn[i][j].getText() + "  in a horizontal row at " + j + "," + i);
                     
                    }
                    if (i > 0 && i < btn.length - 1) {
                        if (btn[i - 1][j - 1].getText() == btn[i][j].getText()
                                && btn[i][j].getText() == btn[i + 1][j + 1].getText()) {
                            problems.add("More than two " + btn[i][j].getText() + " in a diagonal row at " + j + "," + i);
                        }
                        if (btn[i + 1][j - 1].getText() == btn[i][j].getText()
                                && btn[i][j].getText() == btn[i - 1][j + 1].getText()) {
                            problems.add("More than two " + btn[i][j].getText() + " in a diagonal row at " + j + "," + i);

                        }
                    }
                }
            }
        }
        Main.getlblillegal().setVisible(true);
        for (int i=0;i < problems.size();i++){
            Main.getlblillegal().setText(problems.get(i));
        }
        String[] s = new String[problems.size()];
        return problems.toArray(s);
    }
    /**
     * Checks if the puzzle is legal or not 
     * @return Is the puzzle Legal 
     */
    public boolean isLegal() {
        return illegalities().length == 0;
    }
    /**
     * Returns the number of remaining spaces. 
     * @return 
     */
    public int remainingSpaces() {
        int count = 0;
        for (int i = 0; i < btn.length; i++) {
            for (int j = 0; j < btn[i].length; j++) {
                if (btn[i][j].getText() == "") {
                    count++;
                }
            }
        }

        Main.getlblspaces().setText("Remaining "+ String.valueOf(count));
        return count;
    }
    /**
     * Checks if the puzzle is complete.
     */
    public void isPuzzleComplete() {
        if (isLegal() && remainingSpaces() == 0){
            Alert ds= new Alert(Alert.AlertType.INFORMATION);
            ds.setTitle("Winner");
            ds.setHeaderText("Congratulations you have won!");
            ds.showAndWait();
            System.exit(0);
        }        
    }
    

}
