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
package lineage2.gameserver.model.worldstatistics;

import static lineage2.gameserver.dao.WorldStatisticDAO.UPDATE_STATISTIC_MODE_ADD;
import static lineage2.gameserver.dao.WorldStatisticDAO.UPDATE_STATISTIC_MODE_INSERT_MAX;
import static lineage2.gameserver.dao.WorldStatisticDAO.UPDATE_STATISTIC_MODE_REPLACE;

public enum CategoryType
{
	// General section
	EXP_ADDED(0),
	ADENA_ADDED(1),
	TIME_PLAYED(2),
	TIME_IN_BATTLE(3), // TODO
	TIME_IN_PARTY(4),
	TIME_IN_FULLPARTY(5),
	
	WEAPON_ENCHANT_MAX(21),
	WEAPON_ENCHANT_MAX_D(21, 1, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_C(21, 2, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_B(21, 3, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_A(21, 4, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_S(21, 5, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_S80(21, 6, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_S84(21, 7, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_R(21, 8, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_R95(21, 10, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_MAX_R99(21, 11, UPDATE_STATISTIC_MODE_REPLACE),
	
	ARMOR_ENCHANT_MAX(23),
	ARMOR_ENCHANT_MAX_D(23, 1, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_C(23, 2, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_B(23, 3, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_A(23, 4, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_S(23, 5, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_S80(23, 6, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_S84(23, 7, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_R(23, 8, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_R95(23, 9, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_MAX_R99(23, 10, UPDATE_STATISTIC_MODE_REPLACE),
	
	WEAPON_ENCHANT_TRY(22),
	WEAPON_ENCHANT_TRY_D(22, 1, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_C(22, 2, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_B(22, 3, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_A(22, 4, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_S(22, 5, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_S80(22, 6, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_S84(22, 7, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_R(22, 8, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_R95(22, 9, UPDATE_STATISTIC_MODE_REPLACE),
	WEAPON_ENCHANT_TRY_R99(22, 10, UPDATE_STATISTIC_MODE_REPLACE),
	
	ARMOR_ENCHANT_TRY(24),
	ARMOR_ENCHANT_TRY_D(24, 1, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_C(24, 2, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_B(24, 3, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_A(24, 4, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_S(24, 5, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_S80(24, 6, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_S84(24, 7, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_R(24, 8, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_R95(24, 9, UPDATE_STATISTIC_MODE_REPLACE),
	ARMOR_ENCHANT_TRY_R99(24, 10, UPDATE_STATISTIC_MODE_REPLACE),
	
	PRIVATE_SELL_COUNT(11),
	QUESTS_COMPLETED(12),
	
	SS_CONSUMED(13),
	SS_CONSUMED_D(13, 1),
	SS_CONSUMED_C(13, 2),
	SS_CONSUMED_B(13, 3),
	SS_CONSUMED_A(13, 4),
	SS_CONSUMED_S(13, 5),
	SS_CONSUMED_R(13, 8),
	
	// SS & BSS
	SPS_CONSUMED(14),
	SPS_CONSUMED_D(14, 1),
	SPS_CONSUMED_C(14, 2),
	SPS_CONSUMED_B(14, 3),
	SPS_CONSUMED_A(14, 4),
	SPS_CONSUMED_S(14, 5),
	SPS_CONSUMED_R(14, 8),
	
	RESURRECTED_CHAR_COUNT(18),
	RESURRECTED_BY_OTHER_COUNT(19),
	
	DIE_COUNT(20),
	// Hunting field section
	MONSTERS_KILLED(1000),
	EXP_FROM_MONSTERS(1001), // TODO
	DAMAGE_TO_MONSTERS_MAX(1003, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_SIGEL_KNIGHT(1003, 139, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_TYRR_WARRIOR(1003, 140, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_YUL_ARCHER(1003, 141, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_OTHELL_ROGUE(1003, 142, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_ISS_ENCHANTER(1003, 143, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_FEOH_WIZARD(1003, 144, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_WYNN_SUMMONER(1003, 145, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS_MAX_AEORE_HEALER(1003, 146, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_MONSTERS(1004),
	DAMAGE_TO_MONSTERS_SIGEL_KNIGHT(1004, 139),
	DAMAGE_TO_MONSTERS_TYRR_WARRIOR(1004, 140),
	DAMAGE_TO_MONSTERS_YUL_ARCHER(1004, 141),
	DAMAGE_TO_MONSTERS_OTHELL_ROGUE(1004, 142),
	DAMAGE_TO_MONSTERS_ISS_ENCHANTER(1004, 143),
	DAMAGE_TO_MONSTERS_FEOH_WIZARD(1004, 144),
	DAMAGE_TO_MONSTERS_WYNN_SUMMONER(1004, 145),
	DAMAGE_TO_MONSTERS_AEORE_HEALER(1004, 146),
	DAMAGE_FROM_MONSTERS(1005),
	DAMAGE_FROM_MONSTERS_SIGEL_KNIGHT(1005, 139),
	DAMAGE_FROM_MONSTERS_TYRR_WARRIOR(1005, 140),
	DAMAGE_FROM_MONSTERS_YUL_ARCHER(1005, 141),
	DAMAGE_FROM_MONSTERS_OTHELL_ROGUE(1005, 142),
	DAMAGE_FROM_MONSTERS_ISS_ENCHANTER(1005, 143),
	DAMAGE_FROM_MONSTERS_FEOH_WIZARD(1005, 144),
	DAMAGE_FROM_MONSTERS_WYNN_SUMMONER(1005, 145),
	DAMAGE_FROM_MONSTERS_AEORE_HEALER(1005, 146),
	KILLED_BY_MONSTER_COUNT(1002),
	
	// Raid section
	EPIC_BOSS_KILLS(1006),
	EPIC_BOSS_KILLS_25774(1006, 1025774),
	EPIC_BOSS_KILLS_25785(1006, 1025785),
	EPIC_BOSS_KILLS_29195(1006, 1029195),
	EPIC_BOSS_KILLS_25779(1006, 1025779),
	EPIC_BOSS_KILLS_25866(1006, 1025866),
	EPIC_BOSS_KILLS_29194(1006, 1029194),
	EPIC_BOSS_KILLS_29218(1006, 1029218),
	EPIC_BOSS_KILLS_29213(1006, 1029213),
	EPIC_BOSS_KILLS_29196(1006, 1029196),
	EPIC_BOSS_KILLS_25867(1006, 1025867),
	EPIC_BOSS_KILLS_29212(1006, 1029212),
	EPIC_BOSS_KILLS_29197(1006, 1029197),
	
	// PVP section
	PK_COUNT(2004),
	PVP_COUNT(2005),
	KILLED_BY_PK_COUNT(2001),
	KILLED_IN_PVP_COUNT(2002),
	DAMAGE_TO_PC_MAX(2006, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_SIGEL_KNIGHT(2006, 139, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_TYRR_WARRIOR(2006, 140, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_YUL_ARCHER(2006, 141, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_OTHELL_ROGUE(2006, 142, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_ISS_ENCHANTER(2006, 143, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_FEOH_WIZARD(2006, 144, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_WYNN_SUMMONER(2006, 145, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC_MAX_AEORE_HEALER(2006, 146, UPDATE_STATISTIC_MODE_INSERT_MAX),
	DAMAGE_TO_PC(2007),
	DAMAGE_TO_PC_SIGEL_KNIGHT(2007, 139),
	DAMAGE_TO_PC_TYRR_WARRIOR(2007, 140),
	DAMAGE_TO_PC_YUL_ARCHER(2007, 141),
	DAMAGE_TO_PC_OTHELL_ROGUE(2007, 142),
	DAMAGE_TO_PC_ISS_ENCHANTER(2007, 143),
	DAMAGE_TO_PC_FEOH_WIZARD(2007, 144),
	DAMAGE_TO_PC_WYNN_SUMMONER(2007, 145),
	DAMAGE_TO_PC_AEORE_HEALER(2007, 146),
	DAMAGE_FROM_PC(2008),
	DAMAGE_FROM_PC_SIGEL_KNIGHT(2008, 139),
	DAMAGE_FROM_PC_TYRR_WARRIOR(2008, 140),
	DAMAGE_FROM_PC_YUL_ARCHER(2008, 141),
	DAMAGE_FROM_PC_OTHELL_ROGUE(2008, 142),
	DAMAGE_FROM_PC_ISS_ENCHANTER(2008, 143),
	DAMAGE_FROM_PC_FEOH_WIZARD(2008, 144),
	DAMAGE_FROM_PC_WYNN_SUMMONER(2008, 145),
	DAMAGE_FROM_PC_AEORE_HEALER(2008, 146),
	
	// Clan section
	MEMBERS_COUNT(3000, UPDATE_STATISTIC_MODE_REPLACE), // TODO[K] - released
														// this category in
														// WSManager
	INVITED_COUNT(3001), // TODO[K] - released this category in WSManager
	LEAVED_COUNT(3002), // TODO[K] - released this category in WSManager
	REPUTATION_COUNT(3003), // TODO[K] - released this category in WSManager
	ADENA_COUNT_IN_WH(3004), // TODO[K] - released this category in WSManager
	ALL_CLAN_PVP_COUNT(3005), // TODO[K] - released this category in WSManager
	CLAN_WAR_WIN_COUNT(3006); // TODO[K] - released this category in WSManager
	private final int _id;
	private final int _subcat;
	private final int _saveMode;
	
	private CategoryType(int id)
	{
		_id = id;
		_subcat = 0;
		_saveMode = UPDATE_STATISTIC_MODE_ADD;
		
	}
	
	private CategoryType(int id, int subcat)
	{
		_id = id;
		_subcat = subcat;
		_saveMode = UPDATE_STATISTIC_MODE_ADD;
	}
	
	private CategoryType(int id, int subcat, int saveMode)
	{
		_id = id;
		_subcat = subcat;
		_saveMode = saveMode;
	}
	
	public static CategoryType getCategoryById(int catId, int subcatId)
	{
		for (CategoryType category : values())
		{
			if ((category.getClientId() == catId) && (category.getSubcat() == subcatId))
			{
				return category;
			}
		}
		return null;
	}
	
	public final int getClientId()
	{
		return _id;
	}
	
	public final int getSubcat()
	{
		return _subcat;
	}
	
	public int getSaveMode()
	{
		return _saveMode;
	}
}
