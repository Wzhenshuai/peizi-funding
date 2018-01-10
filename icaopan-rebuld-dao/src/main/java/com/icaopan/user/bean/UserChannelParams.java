package com.icaopan.user.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6 0006.
 */
public class UserChannelParams {

    private List<Integer> channelId;

    private int userId;

    private int priorityLever;

    public List<Integer> getChannelId() {
        return channelId;
    }

    public void setChannelId(List<Integer> channelId) {
        this.channelId = channelId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPriorityLever() {
        return priorityLever;
    }

    public void setPriorityLever(int priorityLever) {
        this.priorityLever = priorityLever;
    }
}
