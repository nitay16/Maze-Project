package View;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.scene.control.Button;

import java.awt.event.KeyListener;
import java.io.*;
import java.util.Calendar;
import java.util.Observable;
import java.util.Observer;

import static View.Main.*;
import static javafx.application.ConditionalFeature.CONTROLS;
import static javafx.application.ConditionalFeature.SWT;

public class PlayView implements IView, Observer {

    @FXML
    private Pane Pane1;
    @FXML
    private  MazeDisplayer mazeDisplayer;
    @FXML
    private AnchorPane Anchor;
    private static boolean DisplayTheSolu;
    private static boolean DoNew;

    //Start the maze again
    public void New(ActionEvent actionEvent) {
        Main.StartOver();
    }
    public void Save(ActionEvent actionEvent) {
        if(ViewModel.model.getMaze()==null){return;}
        try
        {
            // writing the soultion
            File dir = new File("resources/Saved");
            int count = dir.list().length;
            File FileToSave = new File("resources/Saved/Gamesave" + count);
            FileOutputStream myWriter = new FileOutputStream("resources/Saved/Gamesave" + count);
            ObjectOutputStream Save = new ObjectOutputStream(myWriter);
            Maze mazeToSave = ViewModel.model.getMaze();
            mazeToSave.SetNewStart(ViewModel.getCharPoRow(),ViewModel.getCharPoCol());
            Save.writeObject(mazeToSave);
            Save.close();
        }
        catch (Exception ex)

        {
            //TRY AGAIN
        }

    }

    public void Load(ActionEvent actionEvent) {
        try {
            File dir = new File("resources/Saved");
            int count = dir.list().length;
            int locationInPane = 0;
            boolean Oposet = false;
            Button[] saved = new Button[count];
            for(int i=0 ; i<count;i++) {
                saved[i] = new Button("Game Saved Number: " + String.valueOf(i));
                int now = i;
                saved[i].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        try {
                            FileInputStream myReader = new FileInputStream("resources/Saved/Gamesave"  + String.valueOf(now));
                            ObjectInputStream Read = new ObjectInputStream(myReader);
                            Maze MazeFromSave = (Maze) Read.readObject();
                            Read.close();
                            mazeDisplayer.Clear();
                            DisplayTheSolu=false;
                            DoNew=false;
                            ViewModel.setPos(MazeFromSave.getStartPosition().getRowIndex(),MazeFromSave.getStartPosition().getColumnIndex());
                            ViewModel.setMaze(MazeFromSave);
                            solution = ViewModel.getSolution();
                            maze = ViewModel.getMaze();
                            displayMaze(MazeFromSave);
                            ChooseLoadStage.hide();
                            BackGroundPlayer.stop();
                            BackGoundSong();
                            MainScreen.show();
                            CreateMaze(0,0,MazeFromSave);
                        } catch (Exception ex) {
                        }
                    }
                });
                saved[i].setStyle("-fx-background-color: #ffff66;");
                saved[i].setTranslateY(locationInPane);
                if(Oposet){locationInPane=-(locationInPane+30);Oposet=false;}
                else {locationInPane=(locationInPane+30);Oposet=true;}
            }
                ChooseLoadStakPane = FXMLLoader.load(getClass().getResource("ChooseLoad.fxml"));
                ChooseLoad =  new Scene(ChooseLoadStakPane,800,550);
                ChooseLoadStakPane.getChildren().addAll(saved);
                if(count==0){ChooseLoadStakPane.getChildren().add(new Label("There Isn't Any Saved Games"));}
                Button back = new Button("Back");
                back.setStyle("-fx-background-color: #ffff66;");
                back.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Main.ChooseLoadStage.hide();
                        Main.MainScreen.show();
                    }
                });
                back.setTranslateY(250);
                ChooseLoadStakPane.getChildren().add(back);
                ChooseLoadStage = new Stage();
                ChooseLoadStage.setScene(ChooseLoad);
                ChooseLoadStage.initStyle(StageStyle.UNDECORATED);
                MainScreen.hide();
                ChooseLoadStage.show();
            }
        catch (Exception ex){}
        }
    // Set Sence that the player can choose what type of maze to create
    public void Properties(ActionEvent actionEvent) {
        OptionsStage = new Stage();
        OptionsStage.initStyle(StageStyle.UNDECORATED);
        OptionsStage.setScene(Main.Options);
        Main.MainScreen.hide();
        OptionsStage.show();

    }
    //show the GamePad For Controling
    public void HelpButton(ActionEvent actionEvent) {
        Main.MainScreen.setScene(Main.Help);
        Main.MainScreen.show();
    }

    // show the about sence
    public void About(ActionEvent actionEvent) {
        Main.MainScreen.setScene(Main.About);
        Main.MainScreen.show();
    }
    //exit from the game
    public void Exit(ActionEvent actionEvent) {
        Main.ExitFromGame();
    }
    //func that draw the maze
    @Override
    public  void displayMaze(Maze maze)
    {
      mazeDisplayer.setCharacterPosition(Main.ViewModel.getCharPoRow(),Main.ViewModel.getCharPoCol());
      mazeDisplayer.setMaze(maze);
    }

    //Update when something is change in the model
    @Override
    public void update(Observable o, Object arg) {
        if(o == Main.ViewModel)
        {
            if(DoNew||maze==null){return;}
            //func that draw the maze
            displayMaze(ViewModel.getMaze());
            Main.solution = ViewModel.getSolution();
            //check if we find the treasure
            EndGame();
        }
        }



        //func that check if we arrive to the treasure
    public void EndGame()
    {
        if(mazeDisplayer.Mymaze.getGoalPosition().getColumnIndex()== ViewModel.getCharPoCol()&&mazeDisplayer.Mymaze.getGoalPosition().getRowIndex()==ViewModel.getCharPoRow()) {
            DoNew=true;
            TheWonShow();
        }
    }


    //solve button that activate the red dots for the treasure
    public void Solve(ActionEvent actionEvent) {
        if(maze==null){return;}
        DisplayTheSolu=true;
        //set the Start For the solution
        maze.SetNewStart(ViewModel.getCharPoRow(),ViewModel.getCharPoCol());
        ViewModel.setMaze(maze);
        //solve with the new start
        ViewModel.solve();
        solution=ViewModel.getSolution();
        //drawn the red dots
        mazeDisplayer.setSolu(solution);
        mazeDisplayer.requestFocus();
    }



    //Function That get rows and cols Or Maze and create maze .
    //Get maze for the Load Option
    public void CreateMaze(int row,int col,Maze mazeThatSend) {
        try {
            //Two boolean , one for the new and end of the maze
            //The anouther is for know if the solver button in has clicked
            DoNew=false;
            DisplayTheSolu=false;
            PlayView view = fxml.getController();
            ViewModel.addObserver(view);
            //check if we get maze
            if(mazeThatSend!=null){
                ViewModel.model.CreateByMaze(mazeThatSend);
            }
            else {
                ViewModel.generateMaze(row,col);
            }
            //For The resize of the maze Reduction binding
            Pane1.prefHeightProperty().bind(Anchor.heightProperty().subtract(50));
            Pane1.prefWidthProperty().bind(Anchor.widthProperty());
            mazeDisplayer.heightProperty().bind(Pane1.heightProperty());
            mazeDisplayer.widthProperty().bind(Pane1.widthProperty());
            // Set the maze that we get from the server
            maze = ViewModel.getMaze();
            Anchor.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if(DoNew){return;}
                    ViewModel.moveCharacter(keyEvent.getCode());
                }
            });
            //function that Call To drawn maze
            displayMaze(maze);
            mazeDisplayer.requestFocus();

        }catch (Exception ex ){}

    }


    public void Generate(ActionEvent actionEvent) {
        //Open Sence That the user Choose player Style
        ChoosePlayer();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    //KeyPressed
    public void Move(javafx.scene.input.KeyEvent keyEvent) {
        if(maze==null){return;}
        else {ViewModel.setCharPoCol(0);ViewModel.setCharPoRow(0);}
        if(DoNew){return;}
        if(mazeDisplayer==null){return;}
        Main.ViewModel.moveCharacter(keyEvent.getCode());
        keyEvent.consume();
        Main.ViewModel.getMaze().SetNewStart(Main.ViewModel.getCharPoRow(),Main.ViewModel.getCharPoCol());
        ViewModel.setPos(Main.ViewModel.getCharPoRow(),Main.ViewModel.getCharPoCol());
        displayMaze(ViewModel.getMaze());
        if(DisplayTheSolu&& (ViewModel.getCharPoRow()!=ViewModel.getMaze().getGoalPosition().getRowIndex()||ViewModel.getCharPoCol()!=ViewModel.getMaze().getGoalPosition().getColumnIndex()))
        {
            ViewModel.solve();
            solution =ViewModel.getSolution();
            mazeDisplayer.setSolu(solution);
        }
    }
}

