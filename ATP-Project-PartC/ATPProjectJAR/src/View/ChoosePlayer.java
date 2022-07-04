package View;

import javafx.event.ActionEvent;

public class ChoosePlayer {
    //static for the use of the maze displayer
    public static int PlayerType=1;

    // Each Button is Change the Style of the player, and active the Generated Sense
    public void ButtonPlayer1(ActionEvent actionEvent) {
        PlayerType=3;
        Main.ChoosePlayerStage.hide();
        Main.GenInput();
    }

    public void ButtonPlayer2(ActionEvent actionEvent) {
        PlayerType=2;
        Main.ChoosePlayerStage.hide();
        Main.GenInput();
    }

    public void ButtonPlayer3(ActionEvent actionEvent) {
        PlayerType=1;
        Main.ChoosePlayerStage.hide();
        Main.GenInput();
    }
}
