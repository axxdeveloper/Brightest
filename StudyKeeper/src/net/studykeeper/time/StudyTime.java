package net.studykeeper.time;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StudyTime {

	private boolean isStarted = false;
	private Duration.Builder durationBuilder;
	private List<Duration> durations = new LinkedList<Duration>();
	
	private StudyTime() {
		this.durationBuilder = new Duration.Builder();
	}

	public static StudyTime newInstance() {
		return new StudyTime();
	}

	public synchronized void start() {
		if ( !isStarted ) {
			durationBuilder.startTimeInMillis(System.currentTimeMillis());
			isStarted = true;
		}
	}
	
	public synchronized void stop() {
		if ( isStarted ) {
			durations.add(durationBuilder.build());
			isStarted = false;
		}
	}
	
	public String totalDurationAsString() {
		long durationInMillis = isStarted ? durationBuilder.build().durationInMillis() : 0;
		for ( Duration d: durations ) {
			durationInMillis += d.durationInMillis();
		}
		return durationAsString(durationInMillis);
	}
	
	/**
	 * Convert duration to {MINUTES}:{SECONDS}
	 * */
	private String durationAsString(long durationInMillis) {
		long durationInMinutes = TimeUnit.MILLISECONDS.toMinutes(durationInMillis);
		long durationInSeconds = TimeUnit.MILLISECONDS.toSeconds(durationInMillis - TimeUnit.MINUTES.toMillis(durationInMinutes));
		return leftFillZero(durationInMinutes) + ":" + leftFillZero(durationInSeconds);
	}

	private String leftFillZero(long duration) {
		if ( duration < 10 ) {
			return "0" + duration;
		}
		return String.valueOf(duration);
	}

}
