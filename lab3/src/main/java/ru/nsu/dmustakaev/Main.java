package ru.nsu.dmustakaev;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import ru.nsu.dmustakaev.controller.GameMenuController;

public class Main extends Application {
    public static final int SCREEN_WIDTH = 1024;
    public static final int SCREEN_HEIGHT = 600;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/menu/GameMenu.fxml"));
        Parent mainPage = loader.load();

        GameMenuController menuController = loader.getController();
        menuController.setPrimaryStage(primaryStage);

        Scene scene = new Scene(mainPage, SCREEN_WIDTH, SCREEN_HEIGHT);
        primaryStage.setTitle("HeadBall");

        primaryStage.setResizable(false);
        primaryStage.setMinWidth(SCREEN_WIDTH);
        primaryStage.setMinHeight(SCREEN_HEIGHT);
        primaryStage.setMaxWidth(SCREEN_WIDTH);
        primaryStage.setMaxHeight(SCREEN_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

//TODO: add pvp mode
    //TODO: add cvc mode
    //TODO: add pause
    //TODO: add setting with difficulty changer
    //TODO: timer
    //TODO: win screen
    //TODO: loose screen`
    //TODO: back to gameObject factory idea

//TODO:(modes)
//1. big goals     DONE
//2. big ball      DONE
//3. big players   DONE
//4. small goals   DONE
//5. small ball    DONE
//6. small players DONE
//7. light ball    DONE
//8. heavy ball    DONE
//9. moon gravity  DONE
//10. high speed
//11. high jumps
//12. на вулицi склизько
//13. 2 balls
//14. without jump
//15. 3 balls1
//


//TODO: true collision system

