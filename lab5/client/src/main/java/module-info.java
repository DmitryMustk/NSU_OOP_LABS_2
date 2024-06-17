module ru.nsu.dmustakaev.client {
    requires javafx.controls;
    requires javafx.fxml;
//    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
    requires java.logging;

    opens ru.nsu.dmustakaev.client to javafx.fxml;
    exports ru.nsu.dmustakaev.client;
}