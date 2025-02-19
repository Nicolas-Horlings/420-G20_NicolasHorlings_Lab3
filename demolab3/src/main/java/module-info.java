module com.example.demolab3 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.demolab3 to javafx.fxml;
    exports com.example.demolab3;
}