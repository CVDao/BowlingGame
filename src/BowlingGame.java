/**
 * Rules are taken from: https://www.playerssports.net/page/bowling-rules
 */
public class BowlingGame {
	BowlingFrame[] rolls;
	int curRoll;
	
	public BowlingGame() {
		curRoll = 0;
		rolls = new BowlingFrame[12];
		for(int i = 0; i<rolls.length; ++i) {
			rolls[i] = new BowlingFrame();
		}
	}
	
	public int getScore() {
		int scoreSum = 0;
		for(int i = 0; i<10; ++i) {
			if(rolls[i].isStrike()) {
				scoreSum += 10 + getNthRoll(i, 1) + getNthRoll(i,2);
			}
			else if(rolls[i].isSpare()) {
				scoreSum += 10 + getNthRoll(i, 1);
			}
			else {
				scoreSum += rolls[i].getScore();
			}
		}
		scoreSum += rolls[10].getScore() + rolls[11].getScore();
		return scoreSum;
	}
	
	/**
	 * Gets score of roll located offset after base
	 * @param base starting position
	 * @param offset roll offset from base
	 * @return 0 if the roll does not exist, else score of Nth offset roll.
	 */
	public int getNthRoll(int base, int offset) {
		if(base + offset >= rolls.length) {
			System.out.println("Invalid getNthRoll called; offset impossible.");
			return 0;
		}
		
		int cOffset = 1;
		for(int i = base+1; i<rolls.length; ++i) {
			BowlingFrame frame = rolls[i];
			if(!frame.started())
				break;
			
			if(offset == cOffset + 1) {
				return frame.getRoll(0);
			}
			else if(offset == cOffset + 2 && frame.isCompleted() && !frame.isStrike()) {
				return frame.getRoll(1);
			}
			else {
				if(frame.isCompleted() && !frame.isStrike()) {
					cOffset += 2;
				}
				++cOffset;
			}
		}
		return 0;
	}
	
	public boolean roll(int num) {
		if(curRoll > 9 + bonusRolls())
			return false;
		
		rolls[curRoll].roll(num);
		if(rolls[curRoll].isCompleted())
			++curRoll;
		return true;
	}
	
	public int bonusRolls() {
		if(rolls[9].isStrike())
			return 2;
		else if(rolls[9].isSpare())
			return 1;
		return 0;
	}

}