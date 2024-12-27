module com.example.pathify {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pathify to javafx.fxml;
    exports com.example.pathify;
}