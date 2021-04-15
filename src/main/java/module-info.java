module imericxu.zhiheng.mazegen {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires annotations;

	opens imericxu.mazegen to javafx.fxml;
	exports imericxu.mazegen;
}
