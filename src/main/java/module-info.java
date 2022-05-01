module com.gregorgott.mathtrainer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.gregorgott.mathtrainer to javafx.fxml;
    exports com.gregorgott.mathtrainer;
}