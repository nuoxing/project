package com.jyzx.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;


public class Obj2JsonUtil 
{
	private static JsonConfig jsonConfig = null; 
	
	
	public static String getGridJson(List list)
	{
		return getGridJson(list, -1, -1);
	}
	
	public static String getGridJson(List list, int total, int page)
	{
		StringBuffer sb = new StringBuffer();

		if (total < 0 && list != null)
			total = list.size();
		else if (total < 0 && list == null)
			total = 0;
		
		sb.append("{\"rows\":");
		sb.append(list2json(list));
		sb.append(",\"total\":" + total);
		sb.append(",\"page\":" + page + "}");
		
		return sb.toString();
	}
	
	public static String set2json(Set<?> set)
	{
		return JSONArray.fromObject(set, getJsonConfig()).toString();
	}
	
	public static String list2json(List list)
	{
		return list2json(list, null);
	}
	
	public static String list2json(List list, JsonConfig jc)
	{
		return JSONArray.fromObject(list, (jc == null ? getJsonConfig() : jc)).toString();
	}
	
	public static String map2json(Map map)
	{
		return JSONObject.fromObject(map).toString();
	}
	
	
	
	
	
	private static JsonConfig getJsonConfig()
	{
		if (jsonConfig == null)
		{
			jsonConfig = new JsonConfig();
			jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
			jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
			jsonConfig.registerJsonValueProcessor(java.sql.Timestamp.class, new DateJsonValueProcessor("yyyy-MM-dd HH:mm:ss"));
		}
		
		return jsonConfig;
	}
	
	public static Date string2date(String str)
	{
		try 
		{
			if (str == null)
			{
				return null;
			}
			else if (str.matches("[0-9]{4}\\-[0-9]{1,2}\\-[0-9]{1,2}.*"))
			{
				int idx        = 0;
				String value   = str.trim();
				String pattern = "yyyy-MM-dd";	//"yyyy-MM-dd HH:mm:ss"
				
				if ((idx = value.indexOf(":", idx)) > 0) 
				{
					pattern += " HH:mm";
					
					if ((idx = value.indexOf(":", idx + 1)) > 0)
						pattern += ":ss";
				}
				
				SimpleDateFormat format = new SimpleDateFormat(pattern);
				return format.parse(value);
			}
			else if (str.matches("[a-zA-Z]{3} [a-zA-Z]{3} [0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2} .* [0-9]{4}"))
			{
				//Sat Feb 2 00:00:00 UTC+0800 2013
				String pattern = "EEE MMM dd HH:mm:ss zZ yyyy";
				SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
				format.setTimeZone(new java.util.SimpleTimeZone(28800000, "UTC"));
				return format.parse(str);
			}
			else if (str.matches("[a-zA-Z]{3} [0-9]{1,2} [0-9]{2}:[0-9]{2}:[0-9]{2} .* [0-9]{4}"))
			{
				//Feb 2 00:00:00 UTC+0800 2013
				String pattern = "MMM dd HH:mm:ss zZ yyyy";
				SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.ENGLISH);
				format.setTimeZone(new java.util.SimpleTimeZone(28800000, "UTC"));
				return format.parse(str);
			}
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		return null;
	}
	
	public static java.sql.Date string2sqldate(String str)
	{
		try 
		{
			return new java.sql.Date(Obj2JsonUtil.string2date(str).getTime());
		}
		catch (Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	
	public static String date2string(Date date, String pattern)
	{
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}
	
	public static boolean isNull(Object obj)
	{
		return obj == null || obj.toString().trim().equals("");
	}
	
	public static boolean isNull2(Object obj)
	{
		return obj == null || obj.toString().equals("");
	}
	
	public static String nvl(Object obj, String str)
	{
		return obj == null ? str : obj.toString();
	}
	
	public static boolean inString(Object find, String src)
	{
		return inString(find, src, ',');
	}
	
	public static boolean inString(Object find, String src, char c)
	{
		return find != null && src != null && (c + src + c).indexOf(c + find.toString() + c) >= 0;
	}
	
	public static String concatString(Object cat, String src, char c)
	{
		return inString(cat, src) ? src : concatString2(cat, src, c);
	}
	
	public static String concatString2(Object cat, String src, char c)
	{
		return isNull(cat) ? src : (isNull(src) ? "" : src + c) + cat;
	}
		
	@SuppressWarnings("unchecked")
	public static List<?> formatKeyToLowerCase(List<?> list) {
		if (list != null) {
			for (int i = 0, n = list.size(); i < n; i++) {
				if (list.get(i) instanceof Map) {
					Map<String,Object> map = (Map<String,Object>)list.get(i);
					int size = map.size();
					if (size <= 0)
						continue;
					
					String[] keys = new String[size];
					map.keySet().toArray(keys);
					for (int j = 0; j < size; j++) {
						if (keys[j].equals(keys[j].toLowerCase()))
							continue;
						
						map.put(keys[j].toLowerCase(), map.get(keys[j]));
						map.remove(keys[j]);
					}
				}
			}
		}
		return list;
	}
	
	

}
