package com.swingfrog.summer.server;

import com.google.common.collect.Queues;
import io.netty.channel.Channel;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

public class SessionContext {

	private final String sessionId;
	private String directAddress;
	private String realAddress;
	private int port;
	
	private volatile long currentMsgId;
	private volatile long lastRecvTime;
	private final ConcurrentLinkedQueue<Object> waitWriteQueue = Queues.newConcurrentLinkedQueue();

	private Object token;

	private Channel channel;
	private final ConcurrentMap<Object, Object> data = new ConcurrentHashMap<>();

	public SessionContext(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getSessionId() {
		return sessionId;
	}
	public String getDirectAddress() {
		return directAddress;
	}
	public void setDirectAddress(String directAddress) {
		this.directAddress = directAddress;
	}
	public String getRealAddress() {
		return realAddress;
	}
	public void setRealAddress(String realAddress) {
		this.realAddress = realAddress;
	}
	public String getAddress() {
		return realAddress != null ? realAddress : directAddress;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public long getCurrentMsgId() {
		return currentMsgId;
	}
	public void setCurrentMsgId(long currentMsgId) {
		this.currentMsgId = currentMsgId;
	}
	public long getLastRecvTime() {
		return lastRecvTime;
	}
	public void setLastRecvTime(long lastRecvTime) {
		this.lastRecvTime = lastRecvTime;
	}
	ConcurrentLinkedQueue<Object> getWaitWriteQueue() {
		return waitWriteQueue;
	}
	public int getWaitWriteQueueSize() {
		return waitWriteQueue.size();
	}
	public Object getToken() {
		return token;
	}
	public void setToken(Object token) {
		this.token = token;
	}
	public void clearToken() {
		token = null;
	}
	void setChannel(Channel channel) {
		this.channel = channel;
	}
	Channel getChannel() {
		return channel;
	}

	@Override
	public String toString() {
		return String.format("IP[%s:%s]", getAddress(), port);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SessionContext that = (SessionContext) o;
		return sessionId.equals(that.sessionId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(sessionId);
	}

	public ConcurrentMap<Object, Object> getData() {
		return data;
	}

	public void put(Object key, Object value) {
		data.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T get(Object key) {
		return (T) data.get(key);
	}

	@SuppressWarnings("unchecked")
	public <T> T remove(Object key) {
		return (T) data.remove(key);
	}

	public void removeAll() {
		data.clear();
	}

}
