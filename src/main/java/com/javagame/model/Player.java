package com.javagame.model;

/**
 * 玩家角色类
 * 继承自Character，代表玩家控制的角色
 */
public class Player extends Character {
    public static final int WARRIOR = 1;    // 战士
    public static final int MAGE = 2;       // 法师
    public static final int ARCHER = 3;     // 弓箭手

    private int playerType;  // 角色类型
    private int experience; // 经验值
    private int level;      // 等级

    private Player(String name, int maxHp, int maxMp, int physicalAttack,
                   int magicalAttack, int defense, int speed, int type) {
        super(name, maxHp, maxMp, physicalAttack, magicalAttack, defense, speed);
        this.playerType = type;
        this.experience = 0;
        this.level = 1;
        initializeSkills();
    }

    /**
     * 初始化角色技能
     */
    private void initializeSkills() {
        if (playerType == WARRIOR) {
            skills[0] = new Skill("斩击", "进行一次强力的斩击", 10, 25, Skill.SkillType.PHYSICAL, 0);
            skills[1] = new Skill("盾挡", "消耗魔力增强防御", 15, 0, Skill.SkillType.SUPPORT, 0);
            skills[2] = new Skill("战吼", "激怒敌人并自我治疗", 20, 15, Skill.SkillType.MAGICAL, 2);
            skills[3] = new Skill("必杀斩", "必杀技能，强大的攻击", 30, 50, Skill.SkillType.PHYSICAL, 3);
        } else if (playerType == MAGE) {
            skills[0] = new Skill("火球术", "投掷火球进行魔法攻击", 15, 30, Skill.SkillType.MAGICAL, 0);
            skills[1] = new Skill("冰冻术", "冻结敌人", 20, 20, Skill.SkillType.MAGICAL, 1);
            skills[2] = new Skill("治疗术", "恢复自身HP", 25, 0, Skill.SkillType.HEAL, 0);
            skills[3] = new Skill("魔法风暴", "释放强大的魔法", 40, 60, Skill.SkillType.MAGICAL, 3);
        } else if (playerType == ARCHER) {
            skills[0] = new Skill("箭射", "快速射箭", 8, 20, Skill.SkillType.PHYSICAL, 0);
            skills[1] = new Skill("连续射击", "连续发射多箭", 15, 35, Skill.SkillType.PHYSICAL, 1);
            skills[2] = new Skill("闪避", "闪避下一次攻击", 10, 0, Skill.SkillType.SUPPORT, 0);
            skills[3] = new Skill("致命一箭", "威力巨大的一箭", 30, 55, Skill.SkillType.PHYSICAL, 2);
        }
    }

    /**
     * 创建战士角色
     */
    public static Player createWarrior() {
        return new Player("战士", 150, 50, 18, 8, 25, 8, WARRIOR);
    }

    /**
     * 创建法师角色
     */
    public static Player createMage() {
        return new Player("法师", 100, 120, 8, 25, 12, 12, MAGE);
    }

    /**
     * 创建弓箭手角色
     */
    public static Player createArcher() {
        return new Player("弓箭手", 120, 60, 20, 10, 15, 16, ARCHER);
    }

    /**
     * 获得经验值
     * @param exp 经验值
     */
    public void gainExperience(int exp) {
        this.experience += exp;
        while (experience >= 100 * level) {
            levelUp();
        }
    }

    /**
     * 升级
     */
    private void levelUp() {
        level++;
        experience -= 100 * (level - 1);
        maxHp = (int) (maxHp * 1.1);
        currentHp = maxHp;
        maxMp = (int) (maxMp * 1.1);
        currentMp = maxMp;
        physicalAttack = (int) (physicalAttack * 1.1);
        magicalAttack = (int) (magicalAttack * 1.1);
        defense = (int) (defense * 1.05);
    }

    public int getPlayerType() { return playerType; }
    public int getExperience() { return experience; }
    public int getLevel() { return level; }
    
    public String getPlayerTypeName() {
        return switch (playerType) {
            case WARRIOR -> "战士";
            case MAGE -> "法师";
            case ARCHER -> "弓箭手";
            default -> "未知";
        };
    }
}
