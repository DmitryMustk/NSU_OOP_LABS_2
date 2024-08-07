module ru.nsu.dmustakaev {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires javafx.media;

    opens ru.nsu.dmustakaev to javafx.fxml;
    opens ru.nsu.dmustakaev.controller to javafx.fxml;
    exports ru.nsu.dmustakaev.modes;
    exports ru.nsu.dmustakaev;
}