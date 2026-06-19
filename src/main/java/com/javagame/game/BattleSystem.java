package com.javagame.game;

import com.javagame.model.Character;
import com.javagame.model.Player;
import com.javagame.model.Enemy;
import com.javagame.model.Skill;
import java.util.ArrayList;
import java.util.List;

/**
 * 战斗系统类
 * 管理战斗逻辑和规则
 */
public class BattleSystem {
    private Player player;
    private Enemy enemy;
    private int currentRound;
    private List<String> battleLog;
    private boolean battleEnded;
    private boolean playerWon;
    
    public BattleSystem(Player player, Enemy enemy) {
        this.player = player;
        this.enemy = enemy;
        this.currentRound = 0;
        this.battleLog = new ArrayList<>();
        this.battleEnded = false;
        this.playerWon = false;
        this.addLog("战斗开始！");
        this.addLog(player.getName() + " vs " + enemy.getName());
    }
    
    /**
     * 添加战斗日志
     */
    public void addLog(String message) {
        battleLog.add(message);
    }
    
    /**
     * 玩家执行动作
     * @param actionType 动作类型: 0=普通攻击, 1-4=技能编号
     */
    public void playerAction(int actionType) {
        if (battleEnded) return;
        
        currentRound++;
        addLog("========== 第 " + currentRound + " 回合 ==========");
        addLog(player.getName() + "的回合");
        
        // 玩家行动
        if (actionType == 0) {
            // 普通攻击
            performNormalAttack(player, enemy);
        } else if (actionType >= 1 && actionType <= 4) {
            // 技能攻击
            Skill skill = player.getSkills()[actionType - 1];
            if (skill != null) {
                performSkillAttack(player, enemy, skill);
            }
        }
        
        // 检查敌人是否死亡
        if (enemy.isDead()) {
            endBattle(true);
            return;
        }
        
        // 敌人AI行动
        performEnemyAction();
        
        // 检查玩家是否死亡
        if (player.isDead()) {
            endBattle(false);
        }
    }
    
    /**
     * 玩家防守
     */
    public void playerDefend() {
        if (battleEnded) return;
        
        currentRound++;
        addLog("========== 第 " + currentRound + " 回合 ==========");
        addLog(player.getName() + "进入防守状态");
        player.defend();
        
        // 敌人AI行动
        performEnemyAction();
        
        // 检查玩家是否死亡
        if (player.isDead()) {
            endBattle(false);
        }
    }
    
    /**
     * 执行普通攻击
     */
    private void performNormalAttack(Character attacker, Character defender) {
        int damage = (int)(attacker.getPhysicalAttack() * (0.8 + Math.random() * 0.4));
        
        // 防御减伤
        if (defender.isDefending()) {
            damage = (int)(damage * 0.5);
        }
        
        defender.takeDamage(damage);
        addLog(attacker.getName() + "进行了普通攻击，造成 " + damage + " 点伤害");
        addLog(defender.getName() + "的HP: " + Math.max(0, defender.getCurrentHp()));
    }
    
    /**
     * 执行技能攻击
     */
    private void performSkillAttack(Character attacker, Character defender, Skill skill) {
        // 检查技能是否可用
        if (skill.isOnCooldown()) {
            addLog(attacker.getName() + "的 " + skill.getName() + " 还在冷却中");
            return;
        }
        
        // 检查魔力是否充足
        if (!attacker.consumeMp(skill.getMpCost())) {
            addLog(attacker.getName() + "的魔力不足，无法使用 " + skill.getName());
            return;
        }
        
        // 根据技能类型执行
        switch (skill.getType()) {
            case PHYSICAL, MAGICAL -> {
                int damage = skill.calculateDamage(attacker, defender);
                if (defender.isDefending()) {
                    damage = (int)(damage * 0.5);
                }
                defender.takeDamage(damage);
                addLog(attacker.getName() + "使用了 " + skill.getName() + "，造成 " + damage + " 点伤害");
            }
            case HEAL -> {
                int healAmount = skill.getBaseDamage();
                attacker.heal(healAmount);
                addLog(attacker.getName() + "使用了 " + skill.getName() + "，恢复了 " + healAmount + " HP");
            }
            case SUPPORT -> {
                attacker.defend();
                addLog(attacker.getName() + "使用了 " + skill.getName() + "，防御增强！");
            }
        }
        
        skill.resetCooldown();
        addLog(defender.getName() + "的HP: " + Math.max(0, defender.getCurrentHp()));
    }
    
    /**
     * 执行敌人AI行动
     */
    private void performEnemyAction() {
        addLog(enemy.getName() + "的回合");
        
        // 获取敌人的AI决策
        int action = enemy.makeAIDecision();
        
        if (action == -1) {
            // 普通攻击
            performNormalAttack(enemy, player);
        } else if (action >= 0 && action < enemy.getSkills().length) {
            // 技能攻击
            Skill skill = enemy.getSkills()[action];
            if (skill != null) {
                performSkillAttack(enemy, player, skill);
            }
        }
    }
    
    /**
     * 结束战斗
     */
    private void endBattle(boolean playerWon) {
        this.battleEnded = true;
        this.playerWon = playerWon;
        
        if (playerWon) {
            addLog("========== 战斗结束 ==========");
            addLog(player.getName() + "胜利！");
            int gold = enemy.getGoldReward();
            int exp = enemy.getExpReward();
            player.gainExperience(exp);
            GameData.getInstance().addGold(gold);
            GameData.getInstance().addExperience(exp);
            GameData.getInstance().addVictory();
            addLog("获得金币: " + gold);
            addLog("获得经验: " + exp);
        } else {
            addLog("========== 战斗结束 ==========");
            addLog(enemy.getName() + "胜利！");
            addLog(player.getName() + "被击败了...");
        }
    }
    
    // Getters
    public Player getPlayer() { return player; }
    public Enemy getEnemy() { return enemy; }
    public int getCurrentRound() { return currentRound; }
    public List<String> getBattleLog() { return battleLog; }
    public boolean isBattleEnded() { return battleEnded; }
    public boolean isPlayerWon() { return playerWon; }
}
