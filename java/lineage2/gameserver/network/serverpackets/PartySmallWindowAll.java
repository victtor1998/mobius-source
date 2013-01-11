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
package lineage2.gameserver.network.serverpackets;

import java.util.ArrayList;
import java.util.List;

import lineage2.gameserver.model.Party;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.Summon;

public class PartySmallWindowAll extends L2GameServerPacket
{
	private final int leaderId, loot;
	private final List<PartySmallWindowMemberInfo> members = new ArrayList<>();
	
	public PartySmallWindowAll(Party party, Player exclude)
	{
		leaderId = party.getPartyLeader().getObjectId();
		loot = party.getLootDistribution();
		for (Player member : party.getPartyMembers())
		{
			if (member != exclude)
			{
				members.add(new PartySmallWindowMemberInfo(member));
			}
		}
	}
	
	@Override
	protected final void writeImpl()
	{
		writeC(0x4E);
		writeD(leaderId);
		writeD(loot);
		writeD(members.size());
		for (PartySmallWindowMemberInfo member : members)
		{
			writeD(member._id);
			writeS(member._name);
			writeD(member.curCp);
			writeD(member.maxCp);
			writeD(member.curHp);
			writeD(member.maxHp);
			writeD(member.curMp);
			writeD(member.maxMp);
			writeD(member.vitality);
			writeD(member.level);
			writeD(member.class_id);
			writeD(0);
			writeD(member.race_id);
			writeD(0x00);
			writeD(0x00);
			writeD(0x00);
			writeD(member.summonInfos.size());
			for (PartySmallWindowMemberInfo.PartySmallWindowSummonInfo summon : member.summonInfos)
			{
				writeD(summon.summon_type);
				writeD(summon.summon_id);
				writeD(summon.summon_NpcId);
				writeS(summon.summon_Name);
				writeD(summon.summon_curHp);
				writeD(summon.summon_maxHp);
				writeD(summon.summon_curMp);
				writeD(summon.summon_maxMp);
				writeD(summon.summon_level);
			}
		}
	}
	
	public static class PartySmallWindowMemberInfo
	{
		public String _name;
		public int _id, curCp, maxCp, curHp, maxHp, curMp, maxMp, level, class_id, race_id, vitality;
		public List<PartySmallWindowSummonInfo> summonInfos;
		
		public PartySmallWindowMemberInfo(Player member)
		{
			_name = member.getName();
			_id = member.getObjectId();
			curCp = (int) member.getCurrentCp();
			maxCp = member.getMaxCp();
			vitality = member.getVitality();
			curHp = (int) member.getCurrentHp();
			maxHp = member.getMaxHp();
			curMp = (int) member.getCurrentMp();
			maxMp = member.getMaxMp();
			level = member.getLevel();
			class_id = member.getClassId().getId();
			race_id = member.getRace().ordinal();
			summonInfos = new ArrayList<>(member.getSummonList().size());
			for (Summon summon : member.getSummonList())
			{
				summonInfos.add(new PartySmallWindowSummonInfo(summon));
			}
		}
		
		public static class PartySmallWindowSummonInfo
		{
			public int summon_type, summon_id, summon_NpcId, summon_level;
			public int summon_curHp, summon_maxHp, summon_curMp, summon_maxMp;
			public String summon_Name;
			
			public PartySmallWindowSummonInfo(Summon summon)
			{
				summon_type = summon.getSummonType();
				summon_id = summon.getObjectId();
				summon_NpcId = summon.getNpcId() + 1000000;
				summon_Name = summon.getName();
				summon_curHp = (int) summon.getCurrentHp();
				summon_maxHp = summon.getMaxHp();
				summon_curMp = (int) summon.getCurrentMp();
				summon_maxMp = summon.getMaxMp();
				summon_level = summon.getLevel();
			}
		}
	}
}
