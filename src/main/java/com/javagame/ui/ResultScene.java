package com.javagame.ui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.Alignment;
import javafx.stage.Stage;
import com.javagame.game.GameData;
import com.javagame.model.Player;

/**
 * 结果场景
 * 显示战斗结果
 */
public class ResultScene {
    private Scene scene;
    private Stage stage;
    private boolean playerWon;
    
    public ResultScene(Stage stage, boolean playerWon) {
        this.stage = stage;
        this.playerWon = playerWon;
        this.scene = createScene();
    }
    
    /**
     * 创建结果场景
     */
    private Scene createScene() {
        VBox root = new VBox();
        root.setStyle("-fx-background-color: #1a1a2e; -fx-padding: 20;");
        root.setAlignment(Alignment.CENTER);
        root.setSpacing(30);
        
        // 结果标题
        Text resultTitle;
        String resultColor;
        if (playerWon) {
            resultTitle = new Text("恭喜！战斗胜利！");
            resultColor = "#00ff00";
        } else {
            resultTitle = new Text("很遗憾！战斗失败！");
            resultColor = "#ff0000";
        }
        resultTitle.setStyle("-fx-fill: " + resultColor + "; -fx-font-size: 40; -fx-font-weight: bold;");
        
        // 游戏统计信息
        VBox statsBox = createStatsBox();
        
        // 按钮容器
        HBox buttonBox = new HBox();
        buttonBox.setSpacing(20);
        buttonBox.setAlignment(Alignment.CENTER);
        
        // 再来一局
        Button playAgainButton = new Button("再来一局");
        playAgainButton.setPrefSize(150, 50);
        playAgainButton.setStyle("-fx-font-size: 16;");
        playAgainButton.setOnAction(e -> playAgain());
        
        // 返回主菜单
        Button mainMenuButton = new Button("返回主菜单");
        mainMenuButton.setPrefSize(150, 50);
        mainMenuButton.setStyle("-fx-font-size: 16;");
        mainMenuButton.setOnAction(e -> goBackToMainMenu());
        
        // 退出游戏
        Button exitButton = new Button("退出游戏");
        exitButton.setPrefSize(150, 50);
        exitButton.setStyle("-fx-font-size: 16;");
        exitButton.setOnAction(e -> stage.close());
        
        buttonBox.getChildren().addAll(playAgainButton, mainMenuButton, exitButton);
        
        root.getChildren().addAll(resultTitle, statsBox, buttonBox);
        
        return new Scene(root, 1000, 700);
    }
    
    /**
     * 创建统计信息框
     */
    private VBox createStatsBox() {
        VBox statsBox = new VBox();
        statsBox.setStyle("-fx-border-color: #00ff00; -fx-border-width: 2; -fx-padding: 20;");
        statsBox.setSpacing(15);
        statsBox.setAlignment(Alignment.CENTER);
        statsBox.setPrefWidth(500);
        
        GameData gameData = GameData.getInstance();
        Player player = gameData.getCurrentPlayer();
        
        // 玩家信息
        Text playerInfo = new Text("角色：" + player.getName() + " (等级: " + player.getLevel() + ")");
        playerInfo.setStyle("-fx-fill: #ffff00; -fx-font-size: 16;");
        
        // 统计信息
        Text totalGold = new Text("累计金币：" + gameData.getTotalGold());
        totalGold.setStyle("-fx-fill: #00ff00; -fx-font-size: 14;");
        
        Text totalExp = new Text("累计经验：" + gameData.getTotalExperience());
        totalExp.setStyle("-fx-fill: #00ff00; -fx-font-size: 14;");
        
        Text victories = new Text("胜利次数：" + gameData.getVictoriesCount());
        victories.setStyle("-fx-fill: #00ff00; -fx-font-size: 14;");
        
        Text playerStats = new Text(
            "HP：" + player.getCurrentHp() + "/" + player.getMaxHp() + 
            " | MP：" + player.getCurrentMp() + "/" + player.getMaxMp()
        );
        playerStats.setStyle("-fx-fill: #00ff00; -fx-font-size: 14;");
        
        statsBox.getChildren().addAll(playerInfo, totalGold, totalExp, victories, playerStats);
        
        return statsBox;
    }
    
    /**
     * 再来一局
     */
    private void playAgain() {
        // 重置玩家状态
        Player player = GameData.getInstance().getCurrentPlayer();
        player.setCurrentHp(player.getMaxHp());
        player.setCurrentMp(player.getMaxMp());
        
        EnemySelectScene enemySelect = new EnemySelectScene(stage);
        stage.setScene(enemySelect.getScene());
    }
    
    /**
     * 返回主菜单
     */
    private void goBackToMainMenu() {
        GameData.getInstance().reset();
        MainMenuScene mainMenu = new MainMenuScene(stage);
        stage.setScene(mainMenu.getScene());
    }
    
    public Scene getScene() {
        return scene;
    }
}
