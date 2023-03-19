module de.thluebeck.webinar {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens de.thluebeck.webinar to javafx.fxml, javafx.graphics;
    opens de.thluebeck.webinar.view to javafx.fxml;
    exports de.thluebeck.webinar.view;
    exports de.thluebeck.webinar.model;


}