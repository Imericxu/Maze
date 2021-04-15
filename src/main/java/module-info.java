module imericxu.zhiheng.mazegen {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	requires org.jetbrains.annotations;

	opens imericxu.mazegen to javafx.fxml;
	exports imericxu.mazegen;
}
