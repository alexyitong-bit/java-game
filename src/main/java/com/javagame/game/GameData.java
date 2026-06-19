package com.javagame.game;

import com.javagame.model.Player;
import com.javagame.model.Enemy;

/**
 * 游戏数据管理类
 * 保存当前游戏的状态信息
 */
public class GameData {
    private static GameData instance;
    
    private Player currentPlayer;
    private Enemy currentEnemy;
    private int totalGold;      // 总金币
    private int totalExperience; // 总经验
    private int victoriesCount;  // 胜利次数
    
    private GameData() {
        this.totalGold = 0;
        this.totalExperience = 0;
        this.victoriesCount = 0;
    }
    
    /**
     * 获取单例实例
     */
    public static GameData getInstance() {
        if (instance == null) {
            instance = new GameData();
        }
        return instance;
    }
    
    /**
     * 重置游戏数据
     */
    public void reset() {
        this.currentPlayer = null;
        this.currentEnemy = null;
        this.totalGold = 0;
        this.totalExperience = 0;
        this.victoriesCount = 0;
    }
    
    /**
     * 增加金币
     */
    public void addGold(int gold) {
        this.totalGold += gold;
    }
    
    /**
     * 增加经验值
     */
    public void addExperience(int exp) {
        this.totalExperience += exp;
    }
    
    /**
     * 增加胜利次数
     */
    public void addVictory() {
        this.victoriesCount++;
    }
    
    // Getters and Setters
    public Player getCurrentPlayer() { return currentPlayer; }
    public void setCurrentPlayer(Player player) { this.currentPlayer = player; }
    
    public Enemy getCurrentEnemy() { return currentEnemy; }
    public void setCurrentEnemy(Enemy enemy) { this.currentEnemy = enemy; }
    
    public int getTotalGold() { return totalGold; }
    public void setTotalGold(int gold) { this.totalGold = gold; }
    
    public int getTotalExperience() { return totalExperience; }
    public void setTotalExperience(int exp) { this.totalExperience = exp; }
    
    public int getVictoriesCount() { return victoriesCount; }
    public void setVictoriesCount(int count) { this.victoriesCount = count; }
}
