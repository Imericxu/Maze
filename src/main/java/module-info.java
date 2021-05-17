module imericxu.mazegen {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires kotlin.stdlib;

	opens imericxu.mazefx to javafx.fxml;
	opens imericxu.mazefx.controller to javafx.fxml;

	exports imericxu.mazefx;
}
