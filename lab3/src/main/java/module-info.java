module ru.nsu.dmustakaev.lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.dmustakaev to javafx.fxml;
    opens ru.nsu.dmustakaev.controller to javafx.fxml;
    exports ru.nsu.dmustakaev;
    exports ru.nsu.dmustakaev.controller;
}