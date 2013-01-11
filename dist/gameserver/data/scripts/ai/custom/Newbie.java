/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package ai.custom;

import lineage2.gameserver.ai.DefaultAI;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.World;
import lineage2.gameserver.model.instances.NpcInstance;
import lineage2.gameserver.network.serverpackets.ExShowScreenMessage;
import lineage2.gameserver.network.serverpackets.components.NpcString;
import services.SupportMagic;

public class Newbie extends DefaultAI
{
	private long lastebuff = 0;
	
	public Newbie(NpcInstance actor)
	{
		super(actor);
		AI_TASK_ACTIVE_DELAY = 1000;
	}
	
	@Override
	protected boolean thinkActive()
	{
		NpcInstance actor = getActor();
		if ((actor.getDistance(actor.getTarget()) < 100) && ((System.currentTimeMillis() - lastebuff) > 30000))
		{
			lastebuff = System.currentTimeMillis();
			for (Player player : World.getAroundPlayers(actor, 300, 200))
			{
				SupportMagic.doSupportMagic(actor, player, false);
				player.sendPacket(new ExShowScreenMessage(NpcString.NEWBIE_GUIDE_GIVE_YOU_THE_MAGIC_OF_SATTELITE_S1, 4000, ExShowScreenMessage.ScreenMessageAlign.TOP_CENTER, player.getName()));
			}
		}
		return true;
	}
	
	@Override
	public boolean isGlobalAI()
	{
		return true;
	}
}
