package com.javagame;

import javafx.application.Application;
import javafx.stage.Stage;
import com.javagame.ui.MainMenuScene;

/**
 * 主程序入口
 * 启动JavaFX应用程序
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 设置窗口标题
        primaryStage.setTitle("JavaFX 回合制RPG战斗游戏");
        
        // 设置窗口大小
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        
        // 创建主菜单场景
        MainMenuScene mainMenu = new MainMenuScene(primaryStage);
        
        // 显示主菜单
        primaryStage.setScene(mainMenu.getScene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
