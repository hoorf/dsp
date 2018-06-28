package com.github.ruifengho.tx;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

public class TxManagerPool {

	private static Map<String, Integer> map = new ConcurrentHashMap<>();

	public static Integer getState(String groupId) {
		return map.get(groupId);
	}

	public static void setState(String groupId, Integer state) {
		if (StringUtils.isNotBlank(groupId)) {
			map.put(groupId, state);
		}
	}
}
