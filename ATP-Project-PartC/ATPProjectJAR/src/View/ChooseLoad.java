package View;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ChooseLoad {
    //only create when clicking on the "Load" button on the manu

    public Button but;

    public void BackButtonHelp(ActionEvent actionEvent) {
        Main.ChooseLoadStage.hide();
        Main.MainScreen.show();
    }
}
