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
package ai.SuspiciousMerchant;

import lineage2.commons.util.Rnd;
import lineage2.gameserver.ai.DefaultAI;
import lineage2.gameserver.model.Creature;
import lineage2.gameserver.model.instances.NpcInstance;
import lineage2.gameserver.utils.Location;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public final class SuspiciousMerchantArchaic extends DefaultAI
{
	static final Location[] points =
	{
		new Location(105447, -139845, -3120),
		new Location(104918, -140382, -3256),
		new Location(105507, -142515, -3648),
		new Location(106533, -143107, -3656),
		new Location(106714, -143825, -3656),
		new Location(107510, -144024, -3656),
		new Location(108092, -144888, -3656),
		new Location(109499, -145168, -3664),
		new Location(110064, -146169, -3456),
		new Location(110186, -147427, -3096),
		new Location(112389, -147779, -3256),
		new Location(110186, -147427, -3096),
		new Location(110064, -146169, -3456),
		new Location(109499, -145168, -3664),
		new Location(108092, -144888, -3656),
		new Location(107510, -144024, -3656),
		new Location(106714, -143825, -3656),
		new Location(106533, -143107, -3656),
		new Location(105507, -142515, -3648),
		new Location(104918, -140382, -3256),
		new Location(105447, -139845, -3120)
	};
	private int current_point = -1;
	private long wait_timeout = 0;
	private boolean wait = false;
	
	/**
	 * Constructor for SuspiciousMerchantArchaic.
	 * @param actor NpcInstance
	 */
	public SuspiciousMerchantArchaic(NpcInstance actor)
	{
		super(actor);
	}
	
	/**
	 * Method isGlobalAI.
	 * @return boolean
	 */
	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
	
	/**
	 * Method thinkActive.
	 * @return boolean
	 */
	@Override
	protected boolean thinkActive()
	{
		final NpcInstance actor = getActor();
		
		if (actor.isDead())
		{
			return true;
		}
		
		if (_def_think)
		{
			doTask();
			return true;
		}
		
		if (actor.isMoving)
		{
			return true;
		}
		
		if ((System.currentTimeMillis() > wait_timeout) && ((current_point > -1) || Rnd.chance(5)))
		{
			if (!wait)
			{
				switch (current_point)
				{
					case 0:
						wait_timeout = System.currentTimeMillis() + 30000;
						wait = true;
						return true;
						
					case 6:
						wait_timeout = System.currentTimeMillis() + 30000;
						wait = true;
						return true;
						
					case 10:
						wait_timeout = System.currentTimeMillis() + 60000;
						wait = true;
						return true;
						
					case 14:
						wait_timeout = System.currentTimeMillis() + 30000;
						wait = true;
						return true;
						
					case 20:
						wait_timeout = System.currentTimeMillis() + 30000;
						wait = true;
						return true;
				}
			}
			
			wait_timeout = 0;
			wait = false;
			current_point++;
			
			if (current_point >= points.length)
			{
				current_point = 0;
			}
			
			addTaskMove(points[current_point], false);
			doTask();
			return true;
		}
		
		if (randomAnimation())
		{
			return true;
		}
		
		return false;
	}
	
	/**
	 * Method onEvtAttacked.
	 * @param attacker Creature
	 * @param damage int
	 */
	@Override
	protected void onEvtAttacked(Creature attacker, int damage)
	{
		// empty method
	}
	
	/**
	 * Method onEvtAggression.
	 * @param target Creature
	 * @param aggro int
	 */
	@Override
	protected void onEvtAggression(Creature target, int aggro)
	{
		// empty method
	}
}
