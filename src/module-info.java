module Maze {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens imericxu.zhiheng.mazegen to javafx.fxml;
    exports imericxu.zhiheng.mazegen;
}