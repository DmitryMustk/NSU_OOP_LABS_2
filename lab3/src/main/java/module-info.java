module ru.nsu.dmustakaev.lab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.nsu.dmustakaev to javafx.fxml;
    exports ru.nsu.dmustakaev;
}