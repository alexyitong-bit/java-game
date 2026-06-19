package com.javagame.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.geometry.Alignment;
import javafx.stage.Stage;
import com.javagame.model.Player;
import com.javagame.game.GameData;

/**
 * 角色选择场景
 * 让玩家选择游戏角色
 */
public class CharacterSelectScene {
    private Scene scene;
    private Stage stage;
    
    public CharacterSelectScene(Stage stage) {
        this.stage = stage;
        this.scene = createScene();
    }
    
    /**
     * 创建角色选择场景
     */
    private Scene createScene() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 20;");
        root.setAlignment(Alignment.TOP_CENTER);
        root.setSpacing(20);
        
        // 标题
        Text title = new Text("选择你的角色");
        title.setStyle("-fx-fill: #00ff00; -fx-font-size: 32; -fx-font-weight: bold;");
        
        // 角色选择容器
        HBox characterBox = new HBox();
        characterBox.setSpacing(40);
        characterBox.setAlignment(Alignment.CENTER);
        characterBox.setPadding(new javafx.geometry.Insets(20));
        
        // 战士角色卡牌
        VBox warriorCard = createCharacterCard(
            "战士\n",
            "HP: 150\n防御: 25\n物攻: 18\n速度: 8",
            () -> selectCharacter(Player.createWarrior())
        );
        
        // 法师角色卡牌
        VBox mageCard = createCharacterCard(
            "法师\n",
            "HP: 100\n魔攻: 25\n魔防: 12\n速度: 12",
            () -> selectCharacter(Player.createMage())
        );
        
        // 弓箭手角色卡牌
        VBox archerCard = createCharacterCard(
            "弓箭手\n",
            "HP: 120\n物攻: 20\n速度: 16\n防御: 15",
            () -> selectCharacter(Player.createArcher())
        );
        
        characterBox.getChildren().addAll(warriorCard, mageCard, archerCard);
        
        // 返回按钮
        Button backButton = new Button("返回主菜单");
        backButton.setPrefSize(150, 40);
        backButton.setStyle("-fx-font-size: 14;");
        backButton.setOnAction(e -> goBackToMenu());
        
        root.getChildren().addAll(title, characterBox, backButton);
        
        return new Scene(root, 1000, 700);
    }
    
    /**
     * 创建角色卡牌
     */
    private VBox createCharacterCard(String name, String stats, Runnable onSelect) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: #00ff00; -fx-border-width: 2; -fx-padding: 15; " +
                      "-fx-background-color: #16213e;");
        card.setPrefSize(200, 280);
        card.setAlignment(Alignment.CENTER);
        card.setSpacing(15);
        
        // 角色名称
        Text nameText = new Text(name);
        nameText.setStyle("-fx-fill: #ffff00; -fx-font-size: 20; -fx-font-weight: bold;");
        
        // 属性信息
        Text statsText = new Text(stats);
        statsText.setStyle("-fx-fill: #00ff00; -fx-font-size: 12;");
        
        // 选择按钮
        Button selectButton = new Button("选择");
        selectButton.setPrefSize(150, 40);
        selectButton.setStyle("-fx-font-size: 14;");
        selectButton.setOnAction(e -> onSelect.run());
        
        card.getChildren().addAll(nameText, statsText, selectButton);
        
        return card;
    }
    
    /**
     * 选择角色
     */
    private void selectCharacter(Player player) {
        GameData.getInstance().setCurrentPlayer(player);
        EnemySelectScene enemySelect = new EnemySelectScene(stage);
        stage.setScene(enemySelect.getScene());
    }
    
    /**
     * 返回主菜单
     */
    private void goBackToMenu() {
        MainMenuScene mainMenu = new MainMenuScene(stage);
        stage.setScene(mainMenu.getScene());
    }
    
    public Scene getScene() {
        return scene;
    }
}
