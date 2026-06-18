package com.javagame.model;

/**
 * 敌人角色类
 * 继承自Character，代表敌人
 */
public class Enemy extends Character {
    public static final int GOBLIN = 1;        // 哥布林
    public static final int ORC = 2;           // 兽人
    public static final int DRAGON = 3;        // 龙
    public static final int DARK_KNIGHT = 4;   // 黑骑士

    private int enemyType;    // 敌人类型
    private int goldReward;   // 金币奖励
    private int expReward;    // 经验奖励

    private Enemy(String name, int maxHp, int maxMp, int physicalAttack,
                  int magicalAttack, int defense, int speed, int type,
                  int goldReward, int expReward) {
        super(name, maxHp, maxMp, physicalAttack, magicalAttack, defense, speed);
        this.enemyType = type;
        this.goldReward = goldReward;
        this.expReward = expReward;
        initializeSkills();
    }

    /**
     * 初始化敌人的技能
     */
    private void initializeSkills() {
        if (enemyType == GOBLIN) {
            skills[0] = new Skill("匕首刺击", "快速的匕首攻击", 5, 15, Skill.SkillType.PHYSICAL, 0);
            skills[1] = new Skill("逃窜", "试图逃离", 0, 0, Skill.SkillType.SUPPORT, 0);
        } else if (enemyType == ORC) {
            skills[0] = new Skill("战锤砸击", "用战锤进行猛烈攻击", 10, 30, Skill.SkillType.PHYSICAL, 0);
            skills[1] = new Skill("怒吼", "增强攻击力", 15, 0, Skill.SkillType.SUPPORT, 1);
            skills[2] = new Skill("碾压", "踩踏敌人", 20, 35, Skill.SkillType.PHYSICAL, 2);
        } else if (enemyType == DRAGON) {
            skills[0] = new Skill("龙息", "喷出烈火", 25, 45, Skill.SkillType.MAGICAL, 0);
            skills[1] = new Skill("龙爪", "用龙爪进行攻击", 15, 40, Skill.SkillType.PHYSICAL, 0);
            skills[2] = new Skill("飞行躲避", "飞向空中躲避攻击", 20, 0, Skill.SkillType.SUPPORT, 1);
            skills[3] = new Skill("龙之力量", "释放龙的全部力量", 50, 80, Skill.SkillType.MAGICAL, 3);
        } else if (enemyType == DARK_KNIGHT) {
            skills[0] = new Skill("黑剑斩", "用黑剑进行攻击", 15, 35, Skill.SkillType.PHYSICAL, 0);
            skills[1] = new Skill("黑暗魔法", "使用黑暗魔法攻击", 20, 40, Skill.SkillType.MAGICAL, 0);
            skills[2] = new Skill("生命汲取", "吸取敌人生命", 25, 30, Skill.SkillType.MAGICAL, 1);
            skills[3] = new Skill("黑暗降临", "召唤黑暗之力", 40, 70, Skill.SkillType.MAGICAL, 3);
        }
    }

    /**
     * 创建哥布林敌人
     */
    public static Enemy createGoblin() {
        return new Enemy("哥布林", 40, 20, 10, 5, 5, 12, GOBLIN, 50, 30);
    }

    /**
     * 创建兽人敌人
     */
    public static Enemy createOrc() {
        return new Enemy("兽人", 80, 30, 18, 10, 12, 10, ORC, 100, 60);
    }

    /**
     * 创建龙敌人
     */
    public static Enemy createDragon() {
        return new Enemy("龙", 150, 100, 20, 30, 20, 14, DRAGON, 300, 200);
    }

    /**
     * 创建黑骑士敌人
     */
    public static Enemy createDarkKnight() {
        return new Enemy("黑骑士", 120, 80, 22, 25, 18, 12, DARK_KNIGHT, 250, 150);
    }

    /**
     * AI决定下一步行动
     * @return 选择的技能索引，-1表示普通攻击
     */
    public int makeAIDecision() {
        double healthPercentage = (double) currentHp / maxHp;
        
        if (healthPercentage < 0.5 && skills[1] != null && !skills[1].isOnCooldown()) {
            return 1;
        }
        
        if (currentMp > 50 && Math.random() < 0.3) {
            for (int i = skills.length - 1; i >= 0; i--) {
                if (skills[i] != null && !skills[i].isOnCooldown() && 
                    currentMp >= skills[i].getMpCost()) {
                    return i;
                }
            }
        }
        
        return -1;
    }

    public int getEnemyType() { return enemyType; }
    public int getGoldReward() { return goldReward; }
    public int getExpReward() { return expReward; }
    
    public String getEnemyTypeName() {
        return switch (enemyType) {
            case GOBLIN -> "哥布林";
            case ORC -> "兽人";
            case DRAGON -> "龙";
            case DARK_KNIGHT -> "黑骑士";
            default -> "未知怪物";
        };
    }
}
