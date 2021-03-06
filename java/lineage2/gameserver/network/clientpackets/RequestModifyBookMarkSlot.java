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
package lineage2.gameserver.network.clientpackets;

import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.actor.instances.player.BookMark;
import lineage2.gameserver.network.serverpackets.ExGetBookMarkInfo;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public class RequestModifyBookMarkSlot extends L2GameClientPacket
{
	private String name, acronym;
	private int icon, slot;
	
	/**
	 * Method readImpl.
	 */
	@Override
	protected void readImpl()
	{
		slot = readD();
		name = readS(32);
		icon = readD();
		acronym = readS(4);
	}
	
	/**
	 * Method runImpl.
	 */
	@Override
	protected void runImpl()
	{
		final Player activeChar = getClient().getActiveChar();
		
		if (activeChar != null)
		{
			final BookMark mark = activeChar.bookmarks.get(slot);
			
			if (mark != null)
			{
				mark.setName(name);
				mark.setIcon(icon);
				mark.setAcronym(acronym);
				activeChar.sendPacket(new ExGetBookMarkInfo(activeChar));
			}
		}
	}
}
