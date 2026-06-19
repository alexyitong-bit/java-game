package com.javagame.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.geometry.Alignment;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import com.javagame.model.Player;
import com.javagame.model.Enemy;
import com.javagame.model.Skill;
import com.javagame.game.BattleSystem;
import com.javagame.game.GameData;

/**
 * 战斗场景
 * 进行实际的战斗交互
 */
public class BattleScene {
    private Scene scene;
    private Stage stage;
    private BattleSystem battleSystem;
    private TextFlow battleLogFlow;
    private Text playerHpText;
    private Text enemyHpText;
    private VBox skillButtonBox;
    private HBox actionButtonBox;
    
    public BattleScene(Stage stage) {
        this.stage = stage;
        this.battleSystem = new BattleSystem(
            GameData.getInstance().getCurrentPlayer(),
            GameData.getInstance().getCurrentEnemy()
        );
        this.scene = createScene();
    }
    
    /**
     * 创建战斗场景
     */
    private Scene createScene() {
        HBox root = new HBox();
        root.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 15;");
        root.setSpacing(20);
        
        // 左侧：角色信息和战斗日志
        VBox leftPanel = createLeftPanel();
        
        // 右侧：操作按钮
        VBox rightPanel = createRightPanel();
        
        root.getChildren().addAll(leftPanel, rightPanel);
        HBox.setHgrow(leftPanel, javafx.scene.layout.Priority.ALWAYS);
        
        return new Scene(root, 1000, 700);
    }
    
    /**
     * 创建左侧面板
     */
    private VBox createLeftPanel() {
        VBox leftPanel = new VBox();
        leftPanel.setSpacing(15);
        
        // 角色信息容器
        HBox infoBox = createCharacterInfoBox();
        
        // 战斗日志
        battleLogFlow = new TextFlow();
        battleLogFlow.setStyle("-fx-fill: #00ff00; -fx-font-size: 12;");
        updateBattleLog();
        
        ScrollPane scrollPane = new ScrollPane(battleLogFlow);
        scrollPane.setStyle("-fx-control-inner-background: #0f3460; -fx-border-color: #00ff00;");
        scrollPane.setPrefHeight(500);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
        // 操作按钮
        actionButtonBox = new HBox();
        actionButtonBox.setSpacing(10);
        actionButtonBox.setAlignment(Alignment.CENTER);
        updateActionButtons();
        
        leftPanel.getChildren().addAll(infoBox, scrollPane, actionButtonBox);
        VBox.setVgrow(scrollPane, javafx.scene.layout.Priority.ALWAYS);
        
        return leftPanel;
    }
    
    /**
     * 创建角色信息框
     */
    private HBox createCharacterInfoBox() {
        HBox infoBox = new HBox();
        infoBox.setSpacing(40);
        infoBox.setAlignment(Alignment.CENTER);
        infoBox.setStyle("-fx-padding: 10;");
        
        Player player = battleSystem.getPlayer();
        Enemy enemy = battleSystem.getEnemy();
        
        // 玩家信息
        VBox playerInfo = new VBox();
        playerInfo.setSpacing(5);
        Text playerName = new Text(player.getName());
        playerName.setStyle("-fx-fill: #00ff00; -fx-font-size: 16; -fx-font-weight: bold;");
        playerHpText = new Text("HP: " + player.getCurrentHp() + "/" + player.getMaxHp());
        playerHpText.setStyle("-fx-fill: #00ff00; -fx-font-size: 14;");
        Text playerMp = new Text("MP: " + player.getCurrentMp() + "/" + player.getMaxMp());
        playerMp.setStyle("-fx-fill: #00ff00; -fx-font-size: 14;");
        playerInfo.getChildren().addAll(playerName, playerHpText, playerMp);
        
        // VS文字
        Text vs = new Text("VS");
        vs.setStyle("-fx-fill: #ffff00; -fx-font-size: 24; -fx-font-weight: bold;");
        
        // 敌人信息
        VBox enemyInfo = new VBox();
        enemyInfo.setSpacing(5);
        Text enemyName = new Text(enemy.getName());
        enemyName.setStyle("-fx-fill: #ff0000; -fx-font-size: 16; -fx-font-weight: bold;");
        enemyHpText = new Text("HP: " + enemy.getCurrentHp() + "/" + enemy.getMaxHp());
        enemyHpText.setStyle("-fx-fill: #ff0000; -fx-font-size: 14;");
        Text enemyMp = new Text("MP: " + enemy.getCurrentMp() + "/" + enemy.getMaxMp());
        enemyMp.setStyle("-fx-fill: #ff0000; -fx-font-size: 14;");
        enemyInfo.getChildren().addAll(enemyName, enemyHpText, enemyMp);
        
        infoBox.getChildren().addAll(playerInfo, vs, enemyInfo);
        
        return infoBox;
    }
    
    /**
     * 创建右侧面板
     */
    private VBox createRightPanel() {
        VBox rightPanel = new VBox();
        rightPanel.setSpacing(15);
        rightPanel.setStyle("-fx-border-color: #00ff00; -fx-border-width: 2; -fx-padding: 15;");
        rightPanel.setPrefWidth(250);
        
        // 标题
        Text title = new Text("选择动作");
        title.setStyle("-fx-fill: #ffff00; -fx-font-size: 16; -fx-font-weight: bold;");
        
        // 技能按钮
        skillButtonBox = new VBox();
        skillButtonBox.setSpacing(10);
        updateSkillButtons();
        
        // 防守和返回按钮
        VBox bottomBox = new VBox();
        bottomBox.setSpacing(10);
        
        Button defendButton = new Button("防守");
        defendButton.setPrefSize(200, 40);
        defendButton.setStyle("-fx-font-size: 14;");
        defendButton.setOnAction(e -> playerDefend());
        
        Button backButton = new Button("返回主菜单");
        backButton.setPrefSize(200, 40);
        backButton.setStyle("-fx-font-size: 12;");
        backButton.setOnAction(e -> goBackToMenu());
        
        bottomBox.getChildren().addAll(defendButton, backButton);
        
        rightPanel.getChildren().addAll(title, skillButtonBox, bottomBox);
        VBox.setVgrow(skillButtonBox, javafx.scene.layout.Priority.ALWAYS);
        
        return rightPanel;
    }
    
    /**
     * 更新技能按钮
     */
    private void updateSkillButtons() {
        skillButtonBox.getChildren().clear();
        
        Player player = battleSystem.getPlayer();
        Skill[] skills = player.getSkills();
        
        // 普通攻击按钮
        Button attackButton = new Button("普通攻击");
        attackButton.setPrefSize(200, 40);
        attackButton.setStyle("-fx-font-size: 12;");
        attackButton.setOnAction(e -> playerAction(0));
        skillButtonBox.getChildren().add(attackButton);
        
        // 技能按钮
        for (int i = 0; i < skills.length; i++) {
            if (skills[i] != null) {
                Skill skill = skills[i];
                int skillIndex = i;
                Button skillButton = new Button(skill.getName() + "\n(MP: " + skill.getMpCost() + ")");
                skillButton.setPrefSize(200, 40);
                skillButton.setStyle("-fx-font-size: 11;");
                skillButton.setWrapText(true);
                
                // 检查冷却和魔力
                if (skill.isOnCooldown() || player.getCurrentMp() < skill.getMpCost()) {
                    skillButton.setDisable(true);
                }
                
                skillButton.setOnAction(e -> playerAction(skillIndex + 1));
                skillButtonBox.getChildren().add(skillButton);
            }
        }
    }
    
    /**
     * 玩家执行动作
     */
    private void playerAction(int actionType) {
        battleSystem.playerAction(actionType);
        updateUI();
        
        if (battleSystem.isBattleEnded()) {
            handleBattleEnd();
        }
    }
    
    /**
     * 玩家防守
     */
    private void playerDefend() {
        battleSystem.playerDefend();
        updateUI();
        
        if (battleSystem.isBattleEnded()) {
            handleBattleEnd();
        }
    }
    
    /**
     * 更新UI
     */
    private void updateUI() {
        Player player = battleSystem.getPlayer();
        Enemy enemy = battleSystem.getEnemy();
        
        playerHpText.setText("HP: " + Math.max(0, player.getCurrentHp()) + "/" + player.getMaxHp());
        enemyHpText.setText("HP: " + Math.max(0, enemy.getCurrentHp()) + "/" + enemy.getMaxHp());
        
        updateBattleLog();
        updateSkillButtons();
    }
    
    /**
     * 更新战斗日志
     */
    private void updateBattleLog() {
        battleLogFlow.getChildren().clear();
        
        for (String log : battleSystem.getBattleLog()) {
            Text text = new Text(log + "\n");
            
            if (log.contains("胜利") || log.contains("获得")) {
                text.setStyle("-fx-fill: #00ff00;");
            } else if (log.contains("失败") || log.contains("被击败")) {
                text.setStyle("-fx-fill: #ff0000;");
            } else if (log.contains("战斗")) {
                text.setStyle("-fx-fill: #ffff00;");
            } else {
                text.setStyle("-fx-fill: #00ff00;");
            }
            
            battleLogFlow.getChildren().add(text);
        }
        
        // 自动滚动到底部
        battleLogFlow.layout();
    }
    
    /**
     * 处理战斗结束
     */
    private void handleBattleEnd() {
        ResultScene result = new ResultScene(stage, battleSystem.isPlayerWon());
        stage.setScene(result.getScene());
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
