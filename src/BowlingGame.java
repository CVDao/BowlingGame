/**
 * Rules are taken from: https://www.playerssports.net/page/bowling-rules
 */
public class BowlingGame {
	BowlingFrame[] rolls;
	int curFrame;
	
	public BowlingGame() {
		curFrame = 0;
		rolls = new BowlingFrame[12];
		for(int i = 0; i<rolls.length; ++i) {
			rolls[i] = new BowlingFrame();
		}
	}
	
	public int getScore() {
		int scoreSum = 0;
		for(int i = 0; i<9; ++i) {
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
		scoreSum += rolls[9].getScore() + rolls[10].getScore() + rolls[11].getScore();
		return scoreSum;
	}
	
	/**
	 * Gets score of roll located offset after base, excluding current frame
	 * @param base starting position
	 * @param offset roll offset from base
	 * @return 0 if the roll does not exist, else score of Nth offset roll.
	 */
	public int getNthRoll(int base, int offset) {
		if(base + offset >= rolls.length) {
			System.out.println("Invalid getNthRoll called; offset impossible.");
			return 0;
		}

		if(offset == 0) {
			return rolls[base].getRoll(0);
		}
		
		int cOffset = 1;
		for(int i = base+1; i<rolls.length; ++i) {
			BowlingFrame frame = rolls[i];
			if(!frame.started())
				break;
			
			if(offset == cOffset) {
				return frame.getRoll(0);
			}
			else if(offset == cOffset + 1 && frame.isCompleted() && !frame.isStrike()) {
				return frame.getRoll(1);
			}
			else {
				if(frame.isCompleted() && !frame.isStrike()) {
					cOffset += 1;
				}
				++cOffset;
			}
			if(cOffset > offset) {
				return 0;
			}
		}
		return 0;
	}
	
	public boolean roll(int num) {
		if(curFrame > 9 + bonusRolls()) {
			return false;
		}
		else if(curFrame > 9) {
			rolls[curFrame].roll(num);
			++curFrame;
			return true;
		}
		else {
			rolls[curFrame].roll(num);
			if(rolls[curFrame].isCompleted())
				++curFrame;
			return true;
		}
	}
	
	/**
	 * 
	 * @param num number of pins knocked down in total
	 * @return true if roll is possible, false if its not
	 */
	public boolean setPinState(int num) {
		BowlingFrame cFrame = rolls[curFrame];
		int pointsScored = cFrame.started()? num - cFrame.getScore(): num;
		
		if(pointsScored < 0)
			return false;
		
		return roll(pointsScored);
	}
	
	public int bonusRolls() {
		if(rolls[9].isStrike()) {
			return 2;
		}
		else if(rolls[9].isSpare()) {
			return 1;
		}
		return 0;
	}

}