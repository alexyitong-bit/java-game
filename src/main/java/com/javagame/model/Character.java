package com.javagame.model;

/**
 * 角色基类
 * 包含所有角色的基本属性和方法
 */
public abstract class Character {
    protected String name;           // 角色名称
    protected int maxHp;            // 最大HP
    protected int currentHp;        // 当前HP
    protected int maxMp;            // 最大魔力
    protected int currentMp;        // 当前魔力
    protected int physicalAttack;   // 物理攻击力
    protected int magicalAttack;    // 魔法攻击力
    protected int defense;          // 防御力
    protected int speed;            // 速度
    protected boolean isDefending;  // 是否在防守状态
    protected Skill[] skills;       // 技能列表

    public Character(String name, int maxHp, int maxMp, int physicalAttack,
                     int magicalAttack, int defense, int speed) {
        this.name = name;
        this.maxHp = maxHp;
        this.currentHp = maxHp;
        this.maxMp = maxMp;
        this.currentMp = maxMp;
        this.physicalAttack = physicalAttack;
        this.magicalAttack = magicalAttack;
        this.defense = defense;
        this.speed = speed;
        this.isDefending = false;
        this.skills = new Skill[4];
    }

    /**
     * 受到伤害
     * @param damage 伤害值
     */
    public void takeDamage(int damage) {
        int actualDamage = damage;
        
        // 如果在防守状态，伤害减半
        if (isDefending) {
            actualDamage = (int) (damage * 0.5);
            isDefending = false;
        }
        
        currentHp = Math.max(0, currentHp - actualDamage);
    }

    /**
     * 恢复HP
     * @param amount 恢复量
     */
    public void heal(int amount) {
        currentHp = Math.min(maxHp, currentHp + amount);
    }

    /**
     * 消耗魔力
     * @param amount 消耗量
     * @return 是否成功消耗
     */
    public boolean consumeMp(int amount) {
        if (currentMp >= amount) {
            currentMp -= amount;
            return true;
        }
        return false;
    }

    /**
     * 恢复魔力
     * @param amount 恢复量
     */
    public void restoreMp(int amount) {
        currentMp = Math.min(maxMp, currentMp + amount);
    }

    /**
     * 进入防守状态
     */
    public void defend() {
        this.isDefending = true;
    }

    /**
     * 检查是否死亡
     * @return 是否死亡
     */
    public boolean isDead() {
        return currentHp <= 0;
    }

    /**
     * 重置状态（每轮开始时调用）
     */
    public void resetStatus() {
        isDefending = false;
    }

    // Getters and Setters
    public String getName() { return name; }
    public int getMaxHp() { return maxHp; }
    public int getCurrentHp() { return currentHp; }
    public int getMaxMp() { return maxMp; }
    public int getCurrentMp() { return currentMp; }
    public int getPhysicalAttack() { return physicalAttack; }
    public int getMagicalAttack() { return magicalAttack; }
    public int getDefense() { return defense; }
    public int getSpeed() { return speed; }
    public boolean isDefending() { return isDefending; }
    public Skill[] getSkills() { return skills; }

    public void setCurrentHp(int hp) { this.currentHp = hp; }
    public void setCurrentMp(int mp) { this.currentMp = mp; }
    public void setSkills(Skill[] skills) { this.skills = skills; }
    public void setSkill(int index, Skill skill) { 
        if (index >= 0 && index < skills.length) {
            this.skills[index] = skill;
        }
    }
}
