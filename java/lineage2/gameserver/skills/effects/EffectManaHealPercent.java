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
package lineage2.gameserver.skills.effects;

import lineage2.gameserver.model.Effect;
import lineage2.gameserver.network.serverpackets.SystemMessage;
import lineage2.gameserver.stats.Env;
import lineage2.gameserver.stats.Stats;

public class EffectManaHealPercent extends Effect
{
	private final boolean _ignoreMpEff;
	
	public EffectManaHealPercent(Env env, EffectTemplate template)
	{
		super(env, template);
		_ignoreMpEff = template.getParam().getBool("ignoreMpEff", true);
	}
	
	@Override
	public boolean checkCondition()
	{
		if (_effected.isHealBlocked())
		{
			return false;
		}
		return super.checkCondition();
	}
	
	@Override
	public void onStart()
	{
		super.onStart();
		if (_effected.isHealBlocked())
		{
			return;
		}
		double mp = (calc() * _effected.getMaxMp()) / 100.;
		double newMp = (mp * (!_ignoreMpEff ? _effected.calcStat(Stats.MANAHEAL_EFFECTIVNESS, 100., _effector, getSkill()) : 100.)) / 100.;
		double addToMp = Math.max(0, Math.min(newMp, ((_effected.calcStat(Stats.MP_LIMIT, null, null) * _effected.getMaxMp()) / 100.) - _effected.getCurrentMp()));
		_effected.sendPacket(new SystemMessage(SystemMessage.S1_MPS_HAVE_BEEN_RESTORED).addNumber(Math.round(addToMp)));
		if (addToMp > 0)
		{
			_effected.setCurrentMp(addToMp + _effected.getCurrentMp());
		}
	}
	
	@Override
	public boolean onActionTime()
	{
		return false;
	}
}
