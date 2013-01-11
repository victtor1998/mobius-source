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
package lineage2.gameserver.data.htm;

import java.io.File;
import java.io.IOException;

import lineage2.gameserver.Config;
import lineage2.gameserver.model.Player;
import lineage2.gameserver.utils.Language;
import lineage2.gameserver.utils.Strings;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmCache
{
	public static final int DISABLED = 0;
	public static final int LAZY = 1;
	public static final int ENABLED = 2;
	private static final Logger _log = LoggerFactory.getLogger(HtmCache.class);
	private final static HtmCache _instance = new HtmCache();
	
	public static HtmCache getInstance()
	{
		return _instance;
	}
	
	private final Cache[] _cache = new Cache[Language.VALUES.length];
	
	private HtmCache()
	{
		for (int i = 0; i < _cache.length; i++)
		{
			_cache[i] = CacheManager.getInstance().getCache(getClass().getName() + "." + Language.VALUES[i].name());
		}
	}
	
	public void reload()
	{
		clear();
		switch (Config.HTM_CACHE_MODE)
		{
			case ENABLED:
				for (Language lang : Language.VALUES)
				{
					File root;
					if (lang.getShortName() == "en")
					{
						root = new File(Config.DATAPACK_ROOT, "data/html");
					}
					else
					{
						root = new File(Config.DATAPACK_ROOT, "data/html-" + lang.getShortName());
					}
					
					if (!root.exists())
					{
						_log.info("HtmCache: Not find html dir for lang: " + lang);
						continue;
					}
					load(lang, root, root.getAbsolutePath() + "/");
				}
				for (int i = 0; i < _cache.length; i++)
				{
					Cache c = _cache[i];
					_log.info(String.format("HtmCache: parsing %d documents; lang: %s.", c.getSize(), Language.VALUES[i]));
				}
				break;
			case LAZY:
				_log.info("HtmCache: lazy cache mode.");
				break;
			case DISABLED:
				_log.info("HtmCache: disabled.");
				break;
		}
	}
	
	private void load(Language lang, File f, final String rootPath)
	{
		if (!f.exists())
		{
			_log.info("HtmCache: dir not exists: " + f);
			return;
		}
		File[] files = f.listFiles();
		for (File file : files)
		{
			if (file.isDirectory())
			{
				load(lang, file, rootPath);
			}
			else
			{
				if (file.getName().endsWith(".htm"))
				{
					try
					{
						putContent(lang, file, rootPath);
					}
					catch (IOException e)
					{
						_log.info("HtmCache: file error" + e, e);
					}
				}
			}
		}
	}
	
	public void putContent(Language lang, File f, final String rootPath) throws IOException
	{
		String content = FileUtils.readFileToString(f, "UTF-8");
		String path = f.getAbsolutePath().substring(rootPath.length()).replace("\\", "/");
		_cache[lang.ordinal()].put(new Element(path.toLowerCase(), Strings.bbParse(content)));
	}
	
	public String getNotNull(String fileName, Player player)
	{
		Language lang = player == null ? Language.ENGLISH : player.getLanguage();
		String cache = getCache(fileName, lang);
		if (StringUtils.isEmpty(cache))
		{
			cache = "Dialog not found: " + fileName + "; Lang: " + lang;
		}
		return cache;
	}
	
	public String getNullable(String fileName, Player player)
	{
		Language lang = player == null ? Language.ENGLISH : player.getLanguage();
		String cache = getCache(fileName, lang);
		if (StringUtils.isEmpty(cache))
		{
			return null;
		}
		return cache;
	}
	
	private String getCache(String file, Language lang)
	{
		if (file == null)
		{
			return null;
		}
		final String fileLower = file.toLowerCase();
		String cache = get(lang, fileLower);
		if (cache == null)
		{
			switch (Config.HTM_CACHE_MODE)
			{
				case ENABLED:
					break;
				case LAZY:
					cache = loadLazy(lang, file);
					if ((cache == null) && (lang != Language.ENGLISH))
					{
						cache = loadLazy(Language.ENGLISH, file);
					}
					break;
				case DISABLED:
					cache = loadDisabled(lang, file);
					if ((cache == null) && (lang != Language.ENGLISH))
					{
						cache = loadDisabled(Language.ENGLISH, file);
					}
					break;
			}
		}
		return cache;
	}
	
	private String loadDisabled(Language lang, String file)
	{
		String cache = null;
		File f;
		if (lang.getShortName() == "en")
		{
			f = new File(Config.DATAPACK_ROOT, "data/html/" + file);
		}
		else
		{
			f = new File(Config.DATAPACK_ROOT, "data/html-" + lang.getShortName() + "/" + file);
		}
		if (f.exists())
		{
			try
			{
				cache = FileUtils.readFileToString(f, "UTF-8");
				cache = Strings.bbParse(cache);
			}
			catch (IOException e)
			{
				_log.info("HtmCache: File error: " + file + " lang: " + lang);
			}
		}
		return cache;
	}
	
	private String loadLazy(Language lang, String file)
	{
		String cache = null;
		File f;
		if (lang.getShortName() == "en")
		{
			f = new File(Config.DATAPACK_ROOT, "data/html/" + file);
		}
		else
		{
			f = new File(Config.DATAPACK_ROOT, "data/html-" + lang.getShortName() + "/" + file);
		}
		if (f.exists())
		{
			try
			{
				cache = FileUtils.readFileToString(f, "UTF-8");
				cache = Strings.bbParse(cache);
				_cache[lang.ordinal()].put(new Element(file, cache));
			}
			catch (IOException e)
			{
				_log.info("HtmCache: File error: " + file + " lang: " + lang);
			}
		}
		return cache;
	}
	
	private String get(Language lang, String f)
	{
		Element element = _cache[lang.ordinal()].get(f);
		if (element == null)
		{
			element = _cache[Language.ENGLISH.ordinal()].get(f);
		}
		return element == null ? null : (String) element.getObjectValue();
	}
	
	public void clear()
	{
		for (Cache element : _cache)
		{
			element.removeAll();
		}
	}
}
