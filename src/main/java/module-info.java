module com.example.itc313a2t2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.itc313a2t2 to javafx.fxml;
    exports com.example.itc313a2t2;
}