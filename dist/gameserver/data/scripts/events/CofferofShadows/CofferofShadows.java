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
package events.CofferofShadows;

import java.util.ArrayList;
import java.util.List;

import lineage2.gameserver.Announcements;
import lineage2.gameserver.Config;
import lineage2.gameserver.cache.Msg;
import lineage2.gameserver.listener.actor.player.OnPlayerEnterListener;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.model.SimpleSpawner;
import lineage2.gameserver.model.actor.listener.CharListenerList;
import lineage2.gameserver.model.instances.NpcInstance;
import lineage2.gameserver.network.serverpackets.components.CustomMessage;
import lineage2.gameserver.scripts.Functions;
import lineage2.gameserver.scripts.ScriptFile;
import lineage2.gameserver.utils.Util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CofferofShadows extends Functions implements ScriptFile, OnPlayerEnterListener
{
	private static int COFFER_PRICE = 50000;
	private static int COFFER_ID = 8659;
	private static int EVENT_MANAGER_ID = 32091;
	private static List<SimpleSpawner> _spawns = new ArrayList<>();
	private static final Logger _log = LoggerFactory.getLogger(CofferofShadows.class);
	private static boolean _active = false;
	
	private void spawnEventManagers()
	{
		final int EVENT_MANAGERS[][] =
		{
			{
				-14823,
				123567,
				-3143,
				8192
			},
			{
				-83159,
				150914,
				-3155,
				49152
			},
			{
				18600,
				145971,
				-3095,
				40960
			},
			{
				82158,
				148609,
				-3493,
				60
			},
			{
				110992,
				218753,
				-3568,
				0
			},
			{
				116339,
				75424,
				-2738,
				0
			},
			{
				81140,
				55218,
				-1551,
				32768
			},
			{
				147148,
				27401,
				-2231,
				2300
			},
			{
				43532,
				-46807,
				-823,
				31471
			},
			{
				87765,
				-141947,
				-1367,
				6500
			},
			{
				147154,
				-55527,
				-2807,
				61300
			}
		};
		SpawnNPCs(EVENT_MANAGER_ID, EVENT_MANAGERS, _spawns);
	}
	
	private void unSpawnEventManagers()
	{
		deSpawnNPCs(_spawns);
	}
	
	private static boolean isActive()
	{
		return IsActive("CofferofShadows");
	}
	
	public void startEvent()
	{
		Player player = getSelf();
		if (!player.getPlayerAccess().IsEventGm)
		{
			return;
		}
		if (SetActive("CofferofShadows", true))
		{
			spawnEventManagers();
			System.out.println("Event: Coffer of Shadows started.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStarted", null);
		}
		else
		{
			player.sendMessage("Event 'Coffer of Shadows' already started.");
		}
		_active = true;
		show("admin/events.htm", player);
	}
	
	public void stopEvent()
	{
		Player player = getSelf();
		if (!player.getPlayerAccess().IsEventGm)
		{
			return;
		}
		if (SetActive("CofferofShadows", false))
		{
			unSpawnEventManagers();
			System.out.println("Event: Coffer of Shadows stopped.");
			Announcements.getInstance().announceByCustomMessage("scripts.events.CofferofShadows.AnnounceEventStoped", null);
		}
		else
		{
			player.sendMessage("Event 'Coffer of Shadows' not started.");
		}
		_active = false;
		show("admin/events.htm", player);
	}
	
	public void buycoffer(String[] var)
	{
		Player player = getSelf();
		if (!player.isQuestContinuationPossible(true))
		{
			return;
		}
		if (!NpcInstance.canBypassCheck(player, player.getLastNpc()))
		{
			return;
		}
		int coffer_count = 1;
		try
		{
			coffer_count = Integer.valueOf(var[0]);
		}
		catch (Exception E)
		{
		}
		long need_adena = (long) (COFFER_PRICE * Config.EVENT_CofferOfShadowsPriceRate * coffer_count);
		if (player.getAdena() < need_adena)
		{
			player.sendPacket(Msg.YOU_DO_NOT_HAVE_ENOUGH_ADENA);
			return;
		}
		player.reduceAdena(need_adena, true);
		Functions.addItem(player, COFFER_ID, coffer_count);
	}
	
	private static int[] buycoffer_counts =
	{
		1,
		5,
		10,
		50
	};
	
	public String DialogAppend_32091(Integer val)
	{
		if (val != 0)
		{
			return "";
		}
		String price;
		String append = "";
		for (int cnt : buycoffer_counts)
		{
			price = Util.formatAdena((long) (COFFER_PRICE * Config.EVENT_CofferOfShadowsPriceRate * cnt));
			append += "<a action=\"bypass -h scripts_events.CofferofShadows.CofferofShadows:buycoffer " + cnt + "\">";
			if (cnt == 1)
			{
				append += new CustomMessage("scripts.events.CofferofShadows.buycoffer", getSelf()).addString(price);
			}
			else
			{
				append += new CustomMessage("scripts.events.CofferofShadows.buycoffers", getSelf()).addNumber(cnt).addString(price);
			}
			append += "</a><br>";
		}
		return append;
	}
	
	@Override
	public void onLoad()
	{
		CharListenerList.addGlobal(this);
		if (isActive())
		{
			_active = true;
			spawnEventManagers();
			_log.info("Loaded Event: Coffer of Shadows [state: activated]");
		}
		else
		{
			_log.info("Loaded Event: Coffer of Shadows [state: deactivated]");
		}
	}
	
	@Override
	public void onReload()
	{
		unSpawnEventManagers();
	}
	
	@Override
	public void onShutdown()
	{
		unSpawnEventManagers();
	}
	
	@Override
	public void onPlayerEnter(Player player)
	{
		if (_active)
		{
			Announcements.getInstance().announceToPlayerByCustomMessage(player, "scripts.events.CofferofShadows.AnnounceEventStarted", null);
		}
	}
}
