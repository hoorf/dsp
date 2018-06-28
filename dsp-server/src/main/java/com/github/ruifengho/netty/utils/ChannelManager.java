package com.github.ruifengho.netty.utils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

import io.netty.channel.ChannelHandlerContext;

public class ChannelManager {

	private static Map<String, ChannelHandlerContext> map = new ConcurrentHashMap<>();

	private static class ChannelManagerHolder {
		static ChannelManager HOLDER = new ChannelManager();
	}

	private ChannelManager() {

	}

	public boolean hasGroup(String groupId) {
		return map.containsKey(groupId);
	}

	public void group(String groupId, String taskId, ChannelHandlerContext ctx) {
		String key = String.format("%s-%s", groupId, taskId);
		if (StringUtils.isNotBlank(key)) {
			map.put(key, ctx);
		}
	}

	public List<ChannelHandlerContext> getGroup(String groupId) {
		List<String> collect = map.keySet().stream().filter(x -> x.startsWith(groupId)).collect(Collectors.toList());
		List<ChannelHandlerContext> result = map.entrySet().stream().filter(x -> collect.contains(x.getKey()))
				.map(x -> x.getValue()).collect(Collectors.toList());
		return result;
	}

	public boolean taskLose(String groupId) {
		List<ChannelHandlerContext> groups = getGroup(groupId);
		for (ChannelHandlerContext channelHandlerContext : groups) {
			if (!channelHandlerContext.channel().isOpen()) {
				return true;
			}
		}
		return false;
	}

	public ChannelHandlerContext getGroup(String groupId, String taskId) {
		String key = String.format("%s-%s", groupId, taskId);
		if (StringUtils.isNotBlank(key)) {
			return map.get(key);
		}
		return null;
	}

	public static ChannelManager getInstance() {
		return ChannelManagerHolder.HOLDER;
	}
}
