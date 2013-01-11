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
package ai.hellbound;

import lineage2.gameserver.ai.CtrlEvent;
import lineage2.gameserver.ai.DefaultAI;
import lineage2.gameserver.model.Creature;
import lineage2.gameserver.model.entity.Reflection;
import lineage2.gameserver.model.instances.NpcInstance;

public class RankuScapegoat extends DefaultAI
{
	private static final int Eidolon_ID = 25543;
	
	public RankuScapegoat(NpcInstance actor)
	{
		super(actor);
	}
	
	@Override
	protected void onEvtDead(Creature killer)
	{
		NpcInstance actor = getActor();
		NpcInstance mob = actor.getReflection().addSpawnWithoutRespawn(Eidolon_ID, actor.getLoc(), 0);
		NpcInstance boss = getBoss();
		if ((mob != null) && (boss != null))
		{
			Creature cha = boss.getAggroList().getTopDamager();
			if (cha != null)
			{
				mob.getAI().notifyEvent(CtrlEvent.EVT_AGGRESSION, cha, 100000);
			}
		}
		super.onEvtDead(killer);
	}
	
	private NpcInstance getBoss()
	{
		Reflection r = getActor().getReflection();
		if (!r.isDefault())
		{
			for (NpcInstance n : r.getNpcs())
			{
				if ((n.getNpcId() == 25542) && !n.isDead())
				{
					return n;
				}
			}
		}
		return null;
	}
}
