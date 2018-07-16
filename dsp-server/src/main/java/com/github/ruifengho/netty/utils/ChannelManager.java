package com.github.ruifengho.netty.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import io.netty.channel.ChannelHandlerContext;

public class ChannelManager {

	private static Map<String, Collection<ChannelHandlerContext>> map = new ConcurrentHashMap<>();

	private static class ChannelManagerHolder {
		static ChannelManager HOLDER = new ChannelManager();
	}

	private ChannelManager() {

	}

	public boolean hasGroup(String groupId) {
		return map.containsKey(groupId);
	}

	public void putGroup(String groupId, ChannelHandlerContext ctx) {
		if (map.containsKey(groupId)) {
			map.get(groupId).add(ctx);
		} else {
			Set<ChannelHandlerContext> list = new HashSet<>();
			list.add(ctx);
			map.put(groupId, list);
		}
	}

	public Collection<ChannelHandlerContext> getGroup(String groupId) {
		return map.get(groupId);
	}

	public boolean taskLose(String groupId) {
		Collection<ChannelHandlerContext> groups = getGroup(groupId);
		for (ChannelHandlerContext channelHandlerContext : groups) {
			if (!channelHandlerContext.channel().isOpen()) {
				return true;
			}
		}
		return false;
	}

	public static ChannelManager getInstance() {
		return ChannelManagerHolder.HOLDER;
	}
}
