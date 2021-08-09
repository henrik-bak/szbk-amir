module com.szbk.amir {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires javafx.graphics;
    requires java.net.http;
    requires java.desktop;

    opens com.szbk.amir to javafx.fxml;
    exports com.szbk.amir;
}