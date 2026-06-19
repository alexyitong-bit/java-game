package com.javagame.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Alignment;
import javafx.geometry.Insets;
import javafx.stage.Stage;

/**
 * 主菜单场景
 * 游戏开始界面
 */
public class MainMenuScene {
    private Scene scene;
    private Stage stage;
    
    public MainMenuScene(Stage stage) {
        this.stage = stage;
        this.scene = createScene();
    }
    
    /**
     * 创建主菜单场景
     */
    private Scene createScene() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 20;");
        root.setAlignment(Alignment.CENTER);
        root.setSpacing(30);
        
        // 标题
        Text title = new Text("JavaFX 回合制RPG战斗游戏");
        title.setStyle("-fx-fill: #00ff00; -fx-font-size: 36; -fx-font-weight: bold;");
        
        // 子标题
        Text subtitle = new Text("欢迎来到游戏世界");
        subtitle.setStyle("-fx-fill: #ffff00; -fx-font-size: 18;");
        
        // 按钮容器
        VBox buttonBox = new VBox();
        buttonBox.setSpacing(15);
        buttonBox.setAlignment(Alignment.CENTER);
        
        // 开始游戏按钮
        Button startButton = new Button("开始游戏");
        startButton.setPrefSize(200, 50);
        startButton.setStyle("-fx-font-size: 16; -fx-padding: 10;");
        startButton.setOnAction(e -> startGame());
        
        // 游戏说明按钮
        Button instructionButton = new Button("游戏说明");
        instructionButton.setPrefSize(200, 50);
        instructionButton.setStyle("-fx-font-size: 16; -fx-padding: 10;");
        instructionButton.setOnAction(e -> showInstructions());
        
        // 退出按钮
        Button exitButton = new Button("退出游戏");
        exitButton.setPrefSize(200, 50);
        exitButton.setStyle("-fx-font-size: 16; -fx-padding: 10;");
        exitButton.setOnAction(e -> stage.close());
        
        buttonBox.getChildren().addAll(startButton, instructionButton, exitButton);
        
        root.getChildren().addAll(title, subtitle, buttonBox);
        
        return new Scene(root, 1000, 700);
    }
    
    /**
     * 开始游戏
     */
    private void startGame() {
        CharacterSelectScene characterSelect = new CharacterSelectScene(stage);
        stage.setScene(characterSelect.getScene());
    }
    
    /**
     * 显示游戏说明
     */
    private void showInstructions() {
        // 这里可以实现一个弹窗或新场景显示游戏说明
        System.out.println("游戏说明功能待实现");
    }
    
    public Scene getScene() {
        return scene;
    }
}
