package com.softserveinc.ita.chat;

import java.util.Date;

public class Message {

	private String nickname;
	private String text;
    private long time;
    private boolean sent;

    public Message(){
        this("", "", 1, true);
    }

    public Message(String nickname, String text, long time, boolean sent){
        this.nickname = nickname;
        this.text = text;
        this.time = time;
        this.sent = sent;

    }
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return new Date().getTime();
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }
}
