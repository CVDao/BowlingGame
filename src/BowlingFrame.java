
/**
 * Represents a single round of a bowling game.
 * Contains two rolls. Rolls set to -1 have not been rolled.
 */
public class BowlingFrame {
	
	private int firstRoll;
	private int secondRoll;
	private int rolls;
	private boolean complete;
	
	
	public BowlingFrame() {
		firstRoll = -1;
		secondRoll = -1;
		rolls = 0;
		complete = false;
	}
	
	public boolean isStrike() {
		return firstRoll == 10 ? true:false;
	}
	
	public boolean isSpare() {
		return (firstRoll + secondRoll) == 10 ? true: false;
	}
	
	public boolean isCompleted() {
		return complete;
	}
	
	public boolean started() {
		return (firstRoll != -1);
	}
	
	// returns true if roll is successfully logged
	public boolean roll(int rollVal) {
		if(rollVal < 0 || rollVal > 10 || complete) {
			return false;
		}
		else if(rolls == 0) {
			firstRoll = rollVal;
			if(rollVal == 10)
				complete = true;
			
		}
		else if (rolls == 1) {
			secondRoll = rollVal;
			complete = true;
		}
		++rolls;
		return true;
	}
	
	
	/**
	 * Get the total score of the frame
	 * @return score of frame, or 0 if no rolls have been made
	 */
	public int getScore() {
		if(rolls == 0)
			return 0;
		else if(rolls == 1)
			return firstRoll;
		else
			return firstRoll + secondRoll;
	}
	
	public int getRoll(int num) {
		switch(num) {
			case 0:
				return firstRoll == -1? 0:firstRoll;
			case 1:
				return secondRoll == -1? 0:secondRoll;
			default:
				return 0;
		}
	}

}
