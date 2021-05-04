module imericxu.mazegen {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;

	opens imericxu.mazegen to javafx.fxml;
	exports imericxu.mazegen;
}
