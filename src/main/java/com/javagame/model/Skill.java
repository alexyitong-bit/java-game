package com.javagame.model;

/**
 * 技能类
 * 代表角色可以使用的技能
 */
public class Skill {
    private String name;              // 技能名称
    private String description;       // 技能描述
    private int mpCost;               // 魔力消耗
    private int baseDamage;           // 基础伤害
    private SkillType type;           // 技能类型
    private int cooldown;             // 冷却时间
    private int currentCooldown;      // 当前冷却

    public enum SkillType {
        PHYSICAL,    // 物理技能
        MAGICAL,     // 魔法技能
        HEAL,        // 治疗技能
        SUPPORT      // 辅助技能
    }

    public Skill(String name, String description, int mpCost, int baseDamage, 
                 SkillType type, int cooldown) {
        this.name = name;
        this.description = description;
        this.mpCost = mpCost;
        this.baseDamage = baseDamage;
        this.type = type;
        this.cooldown = cooldown;
        this.currentCooldown = 0;
    }

    /**
     * 使用技能
     * @return 是否成功使用
     */
    public boolean use(Character character) {
        if (currentCooldown > 0) {
            return false;
        }
        
        if (!character.consumeMp(mpCost)) {
            return false;
        }
        
        currentCooldown = cooldown;
        return true;
    }

    /**
     * 减少冷却时间
     */
    public void reduceCooldown() {
        if (currentCooldown > 0) {
            currentCooldown--;
        }
    }

    /**
     * 重置冷却时间
     */
    public void resetCooldown() {
        currentCooldown = 0;
    }

    /**
     * 计算伤害
     * @param attacker 攻击者
     * @param defender 防守者
     * @return 实际伤害
     */
    public int calculateDamage(Character attacker, Character defender) {
        int damage = baseDamage;
        
        // 根据技能类型计算伤害
        if (type == SkillType.PHYSICAL) {
            damage += attacker.getPhysicalAttack() / 2;
        } else if (type == SkillType.MAGICAL) {
            damage += attacker.getMagicalAttack() / 2;
        }
        
        // 加入随机因素（±10%）
        double variance = 0.9 + Math.random() * 0.2;
        damage = (int) (damage * variance);
        
        // 防御减伤
        int defensionReduction = (int) (damage * defender.getDefense() / 100.0);
        damage = Math.max(1, damage - defensionReduction);
        
        return damage;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getMpCost() { return mpCost; }
    public int getBaseDamage() { return baseDamage; }
    public SkillType getType() { return type; }
    public int getCooldown() { return cooldown; }
    public int getCurrentCooldown() { return currentCooldown; }
    public boolean isOnCooldown() { return currentCooldown > 0; }
}
