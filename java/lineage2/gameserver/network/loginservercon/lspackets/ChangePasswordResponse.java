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
package lineage2.gameserver.network.loginservercon.lspackets;

import lineage2.gameserver.model.Player;
import lineage2.gameserver.network.GameClient;
import lineage2.gameserver.network.loginservercon.LoginServerCommunication;
import lineage2.gameserver.network.loginservercon.ReceivablePacket;
import lineage2.gameserver.scripts.Functions;

/**
 * @author Mobius
 * @version $Revision: 1.0 $
 */
public class ChangePasswordResponse extends ReceivablePacket
{
	private String account;
	private boolean changed;
	
	/**
	 * Method readImpl.
	 */
	@Override
	public void readImpl()
	{
		account = readS();
		changed = readD() == 1;
	}
	
	/**
	 * Method runImpl.
	 */
	@Override
	protected void runImpl()
	{
		GameClient client = LoginServerCommunication.getInstance().removeWaitingClient(account);
		
		if (client == null)
		{
			return;
		}
		
		Player activeChar = client.getActiveChar();
		
		if (activeChar == null)
		{
			return;
		}
		
		if (changed)
		{
			Functions.show("Password changed.", activeChar);
		}
		else
		{
			Functions.show("Password not changed.", activeChar);
		}
	}
}
