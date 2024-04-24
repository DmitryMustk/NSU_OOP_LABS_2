module ru.nsu.dmustakaev.lab3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens ru.nsu.dmustakaev to javafx.fxml;
    opens ru.nsu.dmustakaev.controller to javafx.fxml;
    exports ru.nsu.dmustakaev;
    exports ru.nsu.dmustakaev.controller;
    exports ru.nsu.dmustakaev.game_object_factory;
    opens ru.nsu.dmustakaev.game_object_factory to javafx.fxml;
    exports ru.nsu.dmustakaev.utils;
    opens ru.nsu.dmustakaev.utils to javafx.fxml;
}
