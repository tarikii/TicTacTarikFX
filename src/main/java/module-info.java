module com.example.tresenratlla {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.tresenratlla to javafx.fxml;
    exports com.example.tresenratlla;
}