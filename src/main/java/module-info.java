module com.example.pathify {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;


    opens com.shortestpath.pathify to javafx.fxml;
    exports com.shortestpath.pathify;
}