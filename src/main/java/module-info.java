module com.gregorgott.mathtrainer {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.gregorgott.mdialogwindows;
    requires java.desktop;


    opens com.gregorgott.mathtrainer to javafx.fxml;
    exports com.gregorgott.mathtrainer;
}