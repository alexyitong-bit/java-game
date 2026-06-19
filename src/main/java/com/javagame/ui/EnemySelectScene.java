package com.javagame.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.Alignment;
import javafx.stage.Stage;
import com.javagame.model.Enemy;
import com.javagame.game.GameData;

/**
 * 敌人选择场景
 * 让玩家选择战斗的敌人
 */
public class EnemySelectScene {
    private Scene scene;
    private Stage stage;
    
    public EnemySelectScene(Stage stage) {
        this.stage = stage;
        this.scene = createScene();
    }
    
    /**
     * 创建敌人选择场景
     */
    private Scene createScene() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 20;");
        root.setAlignment(Alignment.TOP_CENTER);
        root.setSpacing(20);
        
        // 标题
        Text title = new Text("选择敌人");
        title.setStyle("-fx-fill: #ff0000; -fx-font-size: 32; -fx-font-weight: bold;");
        
        // 敌人选择容器
        HBox enemyBox = new HBox();
        enemyBox.setSpacing(30);
        enemyBox.setAlignment(Alignment.CENTER);
        enemyBox.setPadding(new javafx.geometry.Insets(20));
        
        // 哥布林敌人卡牌
        VBox goblinCard = createEnemyCard(
            "哥布林\n",
            "HP: 40\n难度: ★\n金币: 50",
            () -> selectEnemy(Enemy.createGoblin())
        );
        
        // 兽人敌人卡牌
        VBox orcCard = createEnemyCard(
            "兽人\n",
            "HP: 80\n难度: ★★\n金币: 100",
            () -> selectEnemy(Enemy.createOrc())
        );
        
        // 龙敌人卡牌
        VBox dragonCard = createEnemyCard(
            "龙\n",
            "HP: 150\n难度: ★★★\n金币: 300",
            () -> selectEnemy(Enemy.createDragon())
        );
        
        // 黑骑士敌人卡牌
        VBox darkKnightCard = createEnemyCard(
            "黑骑士\n",
            "HP: 120\n难度: ★★★\n金币: 250",
            () -> selectEnemy(Enemy.createDarkKnight())
        );
        
        enemyBox.getChildren().addAll(goblinCard, orcCard, dragonCard, darkKnightCard);
        
        // 返回按钮
        Button backButton = new Button("返回角色选择");
        backButton.setPrefSize(150, 40);
        backButton.setStyle("-fx-font-size: 14;");
        backButton.setOnAction(e -> goBackToCharacterSelect());
        
        root.getChildren().addAll(title, enemyBox, backButton);
        
        return new Scene(root, 1000, 700);
    }
    
    /**
     * 创建敌人卡牌
     */
    private VBox createEnemyCard(String name, String stats, Runnable onSelect) {
        VBox card = new VBox();
        card.setStyle("-fx-border-color: #ff0000; -fx-border-width: 2; -fx-padding: 15; " +
                      "-fx-background-color: #2d1b1b;");
        card.setPrefSize(180, 240);
        card.setAlignment(Alignment.CENTER);
        card.setSpacing(15);
        
        // 敌人名称
        Text nameText = new Text(name);
        nameText.setStyle("-fx-fill: #ff6666; -fx-font-size: 18; -fx-font-weight: bold;");
        
        // 属性信息
        Text statsText = new Text(stats);
        statsText.setStyle("-fx-fill: #ff0000; -fx-font-size: 12;");
        
        // 选择按钮
        Button selectButton = new Button("选择");
        selectButton.setPrefSize(130, 35);
        selectButton.setStyle("-fx-font-size: 14;");
        selectButton.setOnAction(e -> onSelect.run());
        
        card.getChildren().addAll(nameText, statsText, selectButton);
        
        return card;
    }
    
    /**
     * 选择敌人
     */
    private void selectEnemy(Enemy enemy) {
        GameData.getInstance().setCurrentEnemy(enemy);
        BattleScene battle = new BattleScene(stage);
        stage.setScene(battle.getScene());
    }
    
    /**
     * 返回角色选择
     */
    private void goBackToCharacterSelect() {
        CharacterSelectScene characterSelect = new CharacterSelectScene(stage);
        stage.setScene(characterSelect.getScene());
    }
    
    public Scene getScene() {
        return scene;
    }
}
