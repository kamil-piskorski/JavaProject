module com.example.projektzaliczeniowy {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.example.projektzaliczeniowy to javafx.fxml;
    exports com.example.projektzaliczeniowy;
}