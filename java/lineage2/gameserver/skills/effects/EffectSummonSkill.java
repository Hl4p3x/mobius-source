/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package lineage2.gameserver.skills.effects;

import lineage2.gameserver.model.Effect;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.network.serverpackets.SystemMessage;
import lineage2.gameserver.stats.Env;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public class EffectSummonSkill extends Effect
{
	private final int _skillToCast, _skillToCastLevel;
	
	/**
	 * Constructor for EffectSummonSkill.
	 * @param env Env
	 * @param template EffectTemplate
	 */
	public EffectSummonSkill(Env env, EffectTemplate template)
	{
		super(env, template);
		_skillToCast = env.skill.getSkillToCast();
		_skillToCastLevel = env.skill.getSkillToCastLevel();
	}
	
	/**
	 * Method checkCondition.
	 * @return boolean
	 */
	@Override
	public boolean checkCondition()
	{
		Player player = _effected.getPlayer();
		
		if (player == null)
		{
			return false;
		}
		
		if (player.isMounted())
		{
			player.sendPacket(new SystemMessage(SystemMessage.S1_CANNOT_BE_USED_DUE_TO_UNSUITABLE_TERMS).addSkillName(_skill.getId(), _skill.getLevel()));
			return false;
		}
		
		return super.checkCondition();
	}
	
	/**
	 * Method onStart.
	 */
	@Override
	public void onStart()
	{
		super.onStart();
		Player player = _effected.getPlayer();
		player.startSkillCastingTask(_skillToCast, _skillToCastLevel);
	}
	
	/**
	 * Method onExit.
	 */
	@Override
	public void onExit()
	{
		super.onExit();
		Player player = _effected.getPlayer();
		player.stopSkillCastingTask();
	}
	
	/**
	 * Method onActionTime.
	 * @return boolean
	 */
	@Override
	public boolean onActionTime()
	{
		Player player = _effected.getPlayer();
		
		if (player == null)
		{
			return false;
		}
		
		if (player.isAlikeDead())
		{
			return false;
		}
		
		double manaDam = calc();
		
		if (manaDam > _effected.getCurrentMp())
		{
			if (getSkill().isToggle())
			{
				player.sendPacket(new SystemMessage(SystemMessage.NOT_ENOUGH_MP), new SystemMessage(SystemMessage.THE_EFFECT_OF_S1_HAS_BEEN_REMOVED).addSkillName(getSkill().getId(), getSkill().getDisplayLevel()));
				return false;
			}
		}
		
		_effected.reduceCurrentMp(manaDam, null);
		return true;
	}
}
