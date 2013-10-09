package Server;

public class DayManager {
	private double currentTime;
	private long minuteLength;
	
	private boolean updated;
	
	private long remainingSleepTime;
	private long lastEntered;

	private double LENGTH_OF_DAY = 100; //24*60;
	private double MORNING_THRESHOLD = 0.25;
	private double DAY_THRESHOLD = 0.5;
	private double EVENING_THRESHOLD = 0.75;

	public DayManager(int minuteLength) {
		this.minuteLength = minuteLength;
		remainingSleepTime = minuteLength;
		updated = false;
	}
	
	public boolean isUpdated(){
		return updated;
	}
	
	public void update(){
		if (remainingSleepTime <= 0) {
			currentTime++;
			currentTime = currentTime%LENGTH_OF_DAY;
			
			remainingSleepTime = minuteLength;
			updated= true;
		} else {
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}
	}

	public void setUpdated(boolean updated){
		this.updated = updated;
	}
	
	public double getCurrentTime() {
		return (currentTime / LENGTH_OF_DAY);
	}

	public TIME_ENUM getTimeOfDay() {
		double cycle = getCurrentTime();
		if (cycle <= MORNING_THRESHOLD) {
			return TIME_ENUM.MORNING;
		} else if (cycle > MORNING_THRESHOLD && cycle <= DAY_THRESHOLD) {
			return TIME_ENUM.DAY;
		} else if (cycle > DAY_THRESHOLD && cycle <= EVENING_THRESHOLD) {
			return TIME_ENUM.EVENING;
		} else {
			return TIME_ENUM.NIGHT;
		}
	}

	public enum TIME_ENUM {
		MORNING, DAY, EVENING, NIGHT
	}
}
