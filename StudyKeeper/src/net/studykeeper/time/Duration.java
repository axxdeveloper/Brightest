package net.studykeeper.time;


class Duration {
	private final long startTimeInMillis;
	private final long stopTimeInMillis;

	public Duration(Builder builder) {
		this.startTimeInMillis = builder.startTimeInMillis;
		this.stopTimeInMillis = System.currentTimeMillis();
	}
	
	public Duration(long startTimeInMillis, long stopTimeInMillis) {
		this.startTimeInMillis = startTimeInMillis;
		this.stopTimeInMillis = stopTimeInMillis;
	}

	public long getStartTimeInMillis() {
		return startTimeInMillis;
	}

	public long getStopTimeInMillis() {
		return stopTimeInMillis;
	}

	public long durationInMillis() {
		return stopTimeInMillis - startTimeInMillis;
	}

	static class Builder {
		private long startTimeInMillis;
		
		public Builder startTimeInMillis(long startTimeInMillis) {
			this.startTimeInMillis = startTimeInMillis;
			return this;
		}

		public Duration build() {
			return new Duration(this);
		}
	}
	
}
