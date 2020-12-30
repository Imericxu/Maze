module imericxu.zhiheng.mazegen {
	requires javafx.controls;
	requires javafx.fxml;
	requires org.controlsfx.controls;
	
	opens imericxu.zhiheng.mazegen to javafx.fxml;
	exports imericxu.zhiheng.mazegen;
}