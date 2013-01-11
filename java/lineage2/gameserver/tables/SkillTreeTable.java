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
package lineage2.gameserver.tables;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import lineage2.gameserver.data.xml.holder.SkillAcquireHolder;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.Skill;
import lineage2.gameserver.model.SkillLearn;
import lineage2.gameserver.model.base.AcquireType;
import lineage2.gameserver.model.base.EnchantSkillLearn;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SkillTreeTable
{
	public static final int NORMAL_ENCHANT_COST_MULTIPLIER = 1;
	public static final int SAFE_ENCHANT_COST_MULTIPLIER = 5;
	public static final int NORMAL_ENCHANT_BOOK = 6622;
	public static final int SAFE_ENCHANT_BOOK = 9627;
	public static final int CHANGE_ENCHANT_BOOK = 9626;
	public static final int UNTRAIN_ENCHANT_BOOK = 9625;
	public static final int NEW_ENCHANT_BOOK = 30297;
	public static final int NEW_SAFE_ENCHANT_BOOK = 30298;
	public static final int NEW_CHANGE_ENCHANT_BOOK = 30299;
	public static final int UNTRAIN_NEW_ENCHANT_BOOK = 30300;
	private static final Logger _log = LoggerFactory.getLogger(SkillTreeTable.class);
	private static SkillTreeTable _instance;
	public static Map<Integer, List<EnchantSkillLearn>> _enchant = new ConcurrentHashMap<>();
	
	public static SkillTreeTable getInstance()
	{
		if (_instance == null)
		{
			_instance = new SkillTreeTable();
		}
		return _instance;
	}
	
	private SkillTreeTable()
	{
		_log.info("SkillTreeTable: Loaded " + _enchant.size() + " enchanted skills.");
	}
	
	public static void checkSkill(Player player, Skill skill)
	{
		SkillLearn learn = SkillAcquireHolder.getInstance().getSkillLearn(player, skill.getId(), levelWithoutEnchant(skill), AcquireType.NORMAL);
		if (learn == null)
		{
			return;
		}
		if (player.isAwaking())
		{
			return;
		}
		if (learn.getMinLevel() > (player.getLevel() + 10))
		{
			player.removeSkill(skill, true);
			for (int i = skill.getBaseLevel(); i != 0; i--)
			{
				SkillLearn learn2 = SkillAcquireHolder.getInstance().getSkillLearn(player, skill.getId(), i, AcquireType.NORMAL);
				if (learn2 == null)
				{
					continue;
				}
				if (learn2.getMinLevel() > (player.getLevel() + 10))
				{
					continue;
				}
				Skill newSkill = SkillTable.getInstance().getInfo(skill.getId(), i);
				if (newSkill != null)
				{
					player.addSkill(newSkill, true);
					break;
				}
			}
		}
	}
	
	private static int levelWithoutEnchant(Skill skill)
	{
		return skill.getDisplayLevel() > 100 ? skill.getBaseLevel() : skill.getLevel();
	}
	
	public static List<EnchantSkillLearn> getFirstEnchantsForSkill(int skillid)
	{
		List<EnchantSkillLearn> result = new ArrayList<>();
		List<EnchantSkillLearn> enchants = _enchant.get(skillid);
		if (enchants == null)
		{
			return result;
		}
		for (EnchantSkillLearn e : enchants)
		{
			if ((e.getLevel() % 100) == 1)
			{
				result.add(e);
			}
		}
		return result;
	}
	
	public static int isEnchantable(Skill skill)
	{
		List<EnchantSkillLearn> enchants = _enchant.get(skill.getId());
		if (enchants == null)
		{
			return 0;
		}
		for (EnchantSkillLearn e : enchants)
		{
			if (e.getBaseLevel() <= skill.getLevel())
			{
				return 1;
			}
		}
		return 0;
	}
	
	public static List<EnchantSkillLearn> getEnchantsForChange(int skillid, int level)
	{
		List<EnchantSkillLearn> result = new ArrayList<>();
		List<EnchantSkillLearn> enchants = _enchant.get(skillid);
		if (enchants == null)
		{
			return result;
		}
		for (EnchantSkillLearn e : enchants)
		{
			if ((e.getLevel() % 100) == (level % 100))
			{
				result.add(e);
			}
		}
		return result;
	}
	
	public static EnchantSkillLearn getSkillEnchant(int skillid, int level)
	{
		List<EnchantSkillLearn> enchants = _enchant.get(skillid);
		if (enchants == null)
		{
			return null;
		}
		for (EnchantSkillLearn e : enchants)
		{
			if (e.getLevel() == level)
			{
				return e;
			}
		}
		return null;
	}
	
	public static int convertEnchantLevel(int baseLevel, int level, int enchantlevels)
	{
		if (level < 100)
		{
			return level;
		}
		return baseLevel + ((((level - (level % 100)) / 100) - 1) * enchantlevels) + (level % 100);
	}
	
	public static void unload()
	{
		if (_instance != null)
		{
			_instance = null;
		}
		_enchant.clear();
	}
}
