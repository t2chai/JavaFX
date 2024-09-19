package main;

import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;

public class Time extends AnimationTimer{
	
	long startTime = 0;
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getSeconds() {
		return seconds;
	}
	public void setSeconds(long seconds) {
		this.seconds = seconds;
	}
	long seconds = 0;
	@Override
	public void handle(long now) {
		if (startTime == 0) {
			startTime= now;
		}
		long elapsedTime = now-startTime;
		this.seconds = TimeUnit.NANOSECONDS.toSeconds(elapsedTime);
		
    }		
	
	
}
