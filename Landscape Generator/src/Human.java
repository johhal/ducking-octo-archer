
public class Human extends Humanoid {
	private long remainingSleepTime;
	private long lastEntered;
	public Human(int currentX, int currentY, Landscape landscape) {
		super(currentX, currentY, landscape);
		generateSleepTime();
		lastEntered = System.currentTimeMillis();
		landscape.getTile(currentX,currentY).inhabited(true);
	}

	private void generateSleepTime() {
		switch (landscape.getTile(currentX, currentY).getType()) {
		case FORREST:
			remainingSleepTime = (long) (Math.random() * 10000) + 10000;
			break;
		case PLAIN:
			remainingSleepTime = (long) (Math.random() * 10000) + 5000;
			break;
		case MOUNTAIN:
			remainingSleepTime = (long) (Math.random() * 10000) + 15000;
			break;
		}
	}

	@Override
	public void Initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Update() {
		if (remainingSleepTime <= 0) {
			//Has slept enough!
			
			//Genereate new sleep time
			generateSleepTime();

			//Randomize movement
			move();

		} else {
			//Need more sleep!
			remainingSleepTime = remainingSleepTime
					- (System.currentTimeMillis() - lastEntered);
			lastEntered = System.currentTimeMillis();
		}
		
	}

	@Override
	public void move() {
		if (landscape.getTile(currentX, currentY).isInhabited()) {
			landscape.spawnHuman(currentX, currentY);
		}
	}

	@Override
	public void Draw() {
		// TODO Auto-generated method stub
		
	}

}
