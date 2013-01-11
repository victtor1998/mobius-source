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
package lineage2.gameserver.skills.skillclasses;

import java.util.List;

import lineage2.gameserver.cache.Msg;
import lineage2.gameserver.model.Creature;
import lineage2.gameserver.model.Skill;
import lineage2.gameserver.model.instances.TrapInstance;
import lineage2.gameserver.templates.StatsSet;

public class DefuseTrap extends Skill
{
	public DefuseTrap(StatsSet set)
	{
		super(set);
	}
	
	@Override
	public boolean checkCondition(Creature activeChar, Creature target, boolean forceUse, boolean dontMove, boolean first)
	{
		if ((target == null) || !target.isTrap())
		{
			activeChar.sendPacket(Msg.INVALID_TARGET);
			return false;
		}
		return super.checkCondition(activeChar, target, forceUse, dontMove, first);
	}
	
	@Override
	public void useSkill(Creature activeChar, List<Creature> targets)
	{
		for (Creature target : targets)
		{
			if ((target != null) && target.isTrap())
			{
				TrapInstance trap = (TrapInstance) target;
				if (trap.getLevel() <= getPower())
				{
					trap.deleteMe();
				}
			}
		}
		if (isSSPossible())
		{
			activeChar.unChargeShots(isMagic());
		}
	}
}
