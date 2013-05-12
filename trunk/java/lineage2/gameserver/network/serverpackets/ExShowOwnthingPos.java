package lineage2.gameserver.network.serverpackets;

import lineage2.gameserver.data.xml.holder.ResidenceHolder;
import lineage2.gameserver.model.entity.events.objects.TerritoryWardObject;
import lineage2.gameserver.model.entity.residence.Dominion;
import lineage2.gameserver.utils.Location;

import java.util.ArrayList;
import java.util.List;

/**
 * @author VISTALL
 */
public class ExShowOwnthingPos extends L2GameServerPacket
{
	private List<WardInfo> _wardList = new ArrayList<WardInfo>(9);

	public ExShowOwnthingPos()
	{
		for (Dominion dominion : ResidenceHolder.getInstance().getResidenceList(Dominion.class))
		{
			if (dominion.getSiegeDate().getTimeInMillis() == 0)
				continue;

			int[] flags = dominion.getFlags();
			for (int dominionId : flags)
			{
				TerritoryWardObject wardObject = dominion.getSiegeEvent().getFirstObject("ward_" + dominionId);
				Location loc = wardObject.getWardLocation();
				if (loc != null)
					_wardList.add(new WardInfo(dominionId, loc.x, loc.y, loc.z));
			}
		}
	}

	@Override
	protected void writeImpl()
	{
		writeEx(0x94);
		writeD(_wardList.size());
		for (WardInfo wardInfo : _wardList)
		{
			writeD(wardInfo.dominionId);
			writeD(wardInfo._x);
			writeD(wardInfo._y);
			writeD(wardInfo._z);
		}
	}

	private static class WardInfo
	{
		private int dominionId, _x, _y, _z;

		public WardInfo(int territoryId, int x, int y, int z)
		{
			dominionId = territoryId;
			_x = x;
			_y = y;
			_z = z;
		}
	}
}