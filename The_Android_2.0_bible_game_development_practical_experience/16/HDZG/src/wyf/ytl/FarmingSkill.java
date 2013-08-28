package wyf.ytl;

import static wyf.ytl.ConstantUtil.PROFICIENCY_INCREMENT;
import static wyf.ytl.ConstantUtil.PROFICIENCY_UPGRADE_SPAN;
import static wyf.ytl.ConstantUtil.SKILL_LEVEL_MAX;
import static wyf.ytl.ConstantUtil.STRENGTH_COST_DECREMENT;

import java.io.Serializable;

public class FarmingSkill extends Skill implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3127196633134119562L;

	public FarmingSkill(){}
	
	public FarmingSkill(int id, String name, int basicEarning, int skillType,
			Hero hero) {
		super(id, name, basicEarning, skillType, hero);
		this.strengthCost = 18;
	}
	@Override
	public int calculateResult() {
		if(hero.strength < strengthCost){//如果英雄的体力不够使用本次技能
			return -1;
		}
		else{//计算英雄收益
			int result = GameFormula.getSkillEearning(proficiencyLevel, basicEarning);
			return result;
		}
	}

	@Override
	public void useSkill(int skillEarning) {
		hero.setFood(hero.getFood() + skillEarning);//英雄粮草增加
		hero.setStrength(hero.getStrength() - strengthCost);//英雄体力减少
		tempProficiency += PROFICIENCY_INCREMENT;//英雄临时熟练度增加
		if(tempProficiency == proficiencyToUpgrade && proficiencyLevel<SKILL_LEVEL_MAX){//可以熟练度升级了
			proficiencyLevel += 1;//熟练度等级加1
			tempProficiency = 0;//临时熟练度置零
			strengthCost -= STRENGTH_COST_DECREMENT;//消耗的体力值减少
			proficiencyToUpgrade+=PROFICIENCY_UPGRADE_SPAN;//下一级升级上限增加
		}
	}
	
}