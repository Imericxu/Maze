module imericxu.mazegen {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires kotlin.stdlib;

	opens imericxu.mazegen to javafx.fxml;
	exports imericxu.mazegen;
}
