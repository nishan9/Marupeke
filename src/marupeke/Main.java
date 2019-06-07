package marupeke;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
/**
 *
 * @author 181326
 */
public class Main extends Application{

    private static ComboBox<String> CbDifficulty,CbGridSize;
    private static Stage primaryStage;
    private static Grid grid = new Grid(1,1);
    private static Label lblillegal = new Label();
    private static Label lblspaces = new Label();

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setHeight(350);
        primaryStage.setWidth(200);
        this.primaryStage = primaryStage;
        Label tilt = new Label("Marupeke Puzzle");
        BorderPane mainmenu = new BorderPane();
        
        
        Label lblDifficulty = new Label("Select the Difficulty Level");
        CbDifficulty = new ComboBox <>();
        CbDifficulty.getItems().addAll( "Easy","Medium","Hard","Extreme");
        CbDifficulty.getSelectionModel().selectFirst();
        
        Label lblGridSize = new Label("Select the Grid Size");
        CbGridSize = new ComboBox <>();
        CbGridSize.getItems().addAll( "4","5","6","7","8","9");
        CbGridSize.setPromptText("Grid Size");
        CbGridSize.getSelectionModel().selectFirst();
        
        Button b1 = new Button("Submit");
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(20,20,20,20));
        layout.getChildren().addAll(tilt,lblDifficulty,CbDifficulty,lblGridSize,CbGridSize,b1);
        mainmenu.setCenter(layout);
        
         MenuBar menuBar = new MenuBar();
         Menu playMenu = new Menu("Play");
         MenuItem playItem = new MenuItem("New Game");
         MenuItem exitItem = new MenuItem("Exit");
            playMenu.getItems().addAll(playItem,exitItem);

         Menu helpMenu = new Menu("Help"); 
         MenuItem howToPlayItem = new MenuItem("How to Play");
            helpMenu.getItems().add(howToPlayItem);

         Menu creditsMenu = new Menu ("Options");
         MenuItem author = new MenuItem("Author");
         CheckMenuItem darkMode = new CheckMenuItem("Dark Mode");
            creditsMenu.getItems().addAll(darkMode,author);
         
            
       darkMode.setOnAction(e -> {
           if (layout.getStyle() == "-fx-background-color: #000000"){
                layout.setStyle("");
           }
           else 
               layout.setStyle("-fx-background-color: #000000");
       });
       
            
         playItem.setOnAction(e ->{
            primaryStage.close();
            this.start(primaryStage);
         });
         
         exitItem.setOnAction(e ->{
            System.exit(0);
         });
         
        author.setOnAction(e -> {
            Alert ds= new Alert(Alert.AlertType.INFORMATION);
            ds.setTitle("Credits");
            ds.setHeaderText("This game was implemented and designed by 181326");
            ds.show();            
        });
        
        howToPlayItem.setOnAction(e -> {
            Label title = new Label("******Instructions******");
            Label objective = new Label("The objective of the game it to complete the puzzle");
            Label space = new Label("");
            Label rules = new Label("******The Rules******");
            Label space2 = new Label("");
            Label rules1= new Label("You cannot insert a set of three Xs or Os in a horizontal/vertical row or column and diagonally from left to right or righ to left");
            rules1.setWrapText(true);
            Label rules2 = new Label("The game will notify the user of the illegal move made");        
            
           VBox secondaryLayout = new VBox();
           secondaryLayout.getChildren().addAll(title,space,objective,space2,rules,rules1,rules2);

           Scene secondScene = new Scene(secondaryLayout, 800, 200);
           secondaryLayout.setAlignment(Pos.CENTER);
           Stage newWindow = new Stage();
           newWindow.setTitle("Help");
           newWindow.setScene(secondScene);

           newWindow.setX(primaryStage.getX() + 200);
           newWindow.setY(primaryStage.getY() + 100);

           newWindow.show();
        });
        

         menuBar.getMenus().addAll(playMenu,helpMenu,creditsMenu);
         mainmenu.setTop(menuBar);
        
        Scene MainMenu = new Scene(mainmenu,300,200);

        b1.setOnAction(e -> {
            layout.setPadding(new Insets(0,0,0,0));
            layout.getChildren().clear();
            layout.getChildren().add(gridimp());
        });

        primaryStage.setScene(MainMenu);
        primaryStage.show();
    }

    
    private Pane gridimp (){
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);
        
        int n = Integer.parseInt(CbGridSize.getValue());
        BorderPane borderpane = new BorderPane();
        GridPane layout2 = new GridPane();
        layout2.setAlignment(Pos.CENTER);
        
        layout2.setPadding(new Insets(20,0,0,0));
        layout2.setHgap(8); layout2.setVgap(8);

        borderpane.setCenter(layout2);
          
        grid = new Grid(n,n);
        
        grid.btn = new Button[n][n];
        grid.editable = new boolean[n][n];

        for(int i=0; i< grid.btn.length; i++){
            for(int j=0; j< grid.btn.length;j++){
                grid.editable[i][j] = true;
                grid.btn [i][j] = new Button("");
                grid.btn[i][j].setPrefSize(50, 50);
            }
        }

    if(CbDifficulty.getValue() == "Easy"){
            grid.randomiser(n,4,4,4);
    }
    
    if ("Medium".equals(CbDifficulty.getValue())){
        grid.randomiser(n,3,3,3);
    }
    if ("Hard".equals(CbDifficulty.getValue())){
        grid.randomiser(n,2,2,2);
    }
    if ("Extreme".equals(CbDifficulty.getValue())){
        grid.randomiser(n,1,1,1);
    }
        
        for (int i = 0; i < grid.btn.length; i++) {
            for (int j = 0; j < grid.btn.length; j++) {
                if(!grid.editable[i][j])
                    grid.btn[i][j].setStyle("-fx-background-color: #ff66ff");
            }
        }

        for(int i=0; i<grid.btn.length; i++){
            for(int j=0; j<grid.btn.length;j++){
                play(i,j);
                layout2.add(grid.btn[i][j], i, j);
            }
        }

        lblillegal.setPadding(new Insets(25));
        HBox e = new HBox();
        e.getChildren().addAll(lblillegal,lblspaces);
        e.setStyle("-fx-alignment:center");
        borderpane.setBottom(e);
        return borderpane;
    }
    /**
     * ActionListener, executes all relevant methods 
     * @param i Grid Location 1
     * @param j Grid Location 2
     */   
    public static void play(int i, int j){
        grid.btn[i][j].setOnAction(e ->{
                actionPerformed(i,j);
                grid.isLegal();
            if (!(grid.isLegal())){
                grid.illegalities();
            }
            grid.isPuzzleComplete();
                    if(grid.isLegal())
            lblillegal.setVisible(false);
        else
            lblillegal.setVisible(true);
            
        });
    }
    /**
     * Marks the grid with either X on click 1, O on Click 2 and resets on the third click.
     * @param i Coordinate X
     * @param j  Coordinate Y
     */
    public static void actionPerformed(int i, int j) {
        if(grid.editable[i][j]) {
            if (grid.btn[i][j].getText() == "")
                grid.btn[i][j].setText("X");
            else if (grid.btn[i][j].getText() == "X")
                grid.btn[i][j].setText("O");
            else
                grid.btn[i][j].setText("");
        }else
            {
                Alert ds= new Alert(Alert.AlertType.ERROR);
                ds.setTitle("ERROR!");
                ds.setHeaderText("This tile cannot be edited.");
                ds.showAndWait();
            }

    }  
    /**
     * Returns label 
     * @return 
     */
    public static Label getlblillegal() {
        return lblillegal;
    }
    /**
     * Returns Label Remaining
     * @return 
     */
    public static Label getlblspaces() {
        return lblspaces;
    }
        
    /**
     * Checks if the grid is solvable or not 
     */
    public void isSolvable(){
    /*
    This builds are traverses the tree successfully but i did not have time to implement the checks if it is empty it would try both symbols.And would go through the isLegal 
    isLegal method and generate it accordinly. 
    */        
    Tree tree = new Tree();
        int count = 0;
        String shit ="";
        for(int i=0; i<grid.btn.length; i++){
            for(int j=0; j<grid.btn.length;j++){
                shit = grid.btn[i][j].getText();
                    tree.add(count,shit);                            
                shit ="";
                count++;
            }
        }
        tree.traverse(tree.start);
    }
   
}