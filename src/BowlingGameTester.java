import java.lang.StringBuilder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BowlingGameTester {

	public static void main(String[] args) {
		testFrames();
		testGameRolls();
		testFullGame();
	}
	
	public static void testFrames() {
		BowlingFrame tbf = new BowlingFrame();
		
		//No rolls happy case
		if(tbf.isCompleted() || tbf.getScore() != 0 || 
			tbf.isSpare() == true || tbf.isStrike() == true) {
			System.out.println("ERROR: No-Rolls");
		} 
		else {
			System.out.println("PASS: Too-Many-Rolls");
		}
		
		//One roll happy case
		tbf = new BowlingFrame();
		tbf.roll(7);
		if(tbf.getScore() != 7) {
			System.out.println("ERROR: OneRoll score calc");
		}
		else if(tbf.isCompleted()) {
			System.out.println("ERROR: OneRoll Completion");
		}
		else if(tbf.isStrike()) {
			System.out.println("ERROR: OneRoll Strike");
		}
		else if(tbf.isSpare()) {
			System.out.println("ERROR: OneRoll Spare");
		}
		else {
			System.out.println("PASS: OneRoll");
		}
		
		//Tworoll
		tbf.roll(3);
		if(tbf.getScore() != 10) {
			System.out.println("ERROR: TwoRoll score calc");
		}
		else if(!tbf.isCompleted()) {
			System.out.println("ERROR: TwoRoll Completion");
		}
		else if(tbf.isStrike()) {
			System.out.println("ERROR: TwoRoll Strike");
		}
		else if(!tbf.isSpare()) {
			System.out.println("ERROR: TwoRoll Spare");
		}
		else {
			System.out.println("PASS: TwoRoll");
		}
		
		tbf = new BowlingFrame();
		if(tbf.isStrike())
			System.out.println("ERROR: Strike-No-Roll");
		else
			System.out.println("PASS: Strike-No-Roll");
		
		tbf.roll(10);
		if(!tbf.isStrike())
			System.out.println("ERROR: Strike-Happy-Case");
		else
			System.out.println("PASS: Strike-Happy-Case");
		//Too many rolls
		tbf = new BowlingFrame();
		tbf.roll(0);
		tbf.roll(0);
		if(tbf.roll(0) != false){
			System.out.println("ERROR: Too-Many-Rolls");
		} 
		else {
			System.out.println("PASS: Too-Many-Rolls");
		}
		
		//Bad Roll Values
		tbf = new BowlingFrame();
		if(tbf.roll(-1) != false || tbf.roll(11) != false || tbf.roll(0) == false || tbf.roll(10) == false) {
			System.out.println("ERROR: Bad-Roll-Vals");
		} 
		else {
			System.out.println("PASS: Bad-Roll-Vals");
		}
	}
	
	public static void testGameRolls() {
		testGameTooManyRolls(new int[] {10,10,10,10,10,10,10,10,10,8,1,10}, "Bad-Game-Rolls");
		testGameTooManyRolls(new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,10,10}, "Bad-Game-Rolls-2");
		testGameTooManyRolls(new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,9,10,10}, "Bad-Game-Rolls-3");
		testGameTooManyRolls(new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,10,7}, "Spare-Extra-Rolls");
		testGameTooManyRolls(new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,10,10,9,10}, "Strike-Extra-Rolls");
		
		// get nth roll
		testNthRoll(
				new int[] {1,2,3,4,5},
				new int[] {0,0,0,0,1,1,2},
				new int[] {0,1,2,3,1,2,1},
				new int[] {1,3,4,5,5,0,0},
				"get-nth-roll-short"
		);
		
		testNthRoll(
				new int[] {1,1,2,2,3,3,4,4,5,5,6,4,7,3,8,2,9,1,10,9,8},
				new int[] {0,1,1,1,1,4},
				new int[] {0,0,1,2,3,0},
				new int[] {1,2,3,3,4,5},
				"get-nth-roll-long"
		);
		
	}

	public static void testFullGame() {
		expectError(new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
					new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
					"too-many-rolls-zeroCase");
		
		
		expectError(new int[]{10,10,10,10,10,10,10,10,10,10,10,10,1},
					new int[]{10,30,60,90,120,150,180,210,240,270,290,300,340},
					"too-many-rolls-allStrikes");
		
		expectError(new int[]{10,10,10,10,10,10,10,10,10,9,1,10, 1},
				new int[]{10,30,60,90,120,150,180,210,240,267,269,279,280},
				"too-many-rolls-all-strike-spare");
		
		testRolls(
				new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				"No-Hits"
		);
		
		testRolls(
				new int[]{10,10,10,10,10,10,10,10,10,10,10,10},
				new int[]{10,30,60,90,120,150,180,210,240,270,290,300},
				"All-Strikes"
		);
		
		testRolls(
				new int[]{10,10,10,10,10,10,10,10,10,9,1,10},
				new int[]{10,30,60,90,120,150,180,210,240,267,269,279},
				"All-Strike-Final-Spare"
		);
		
		testSetPinState(
				new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				"Pins-No-Hits"
		);
		
		testSetPinState(
				new int[]{10,10,10,10,10,10,10,10,10,10,10,10},
				new int[]{10,30,60,90,120,150,180,210,240,270,290,300},
				"Pins-All-Strikes"
		);
		
		testSetPinState(
				new int[]{10,10,10,10,10,10,10,10,10,9,10,10},
				new int[]{10,30,60,90,120,150,180,210,240,267,269,279},
				"Pins-All-Strike-Final-Spare"
		);
		
		testSetPinState(
				new int[]{3,7,10,4,6,10,10,10},
				new int[]{3,7,17,25,29,39,59,89},
				"Pins-Normal-Game"
		);
		
		
	}
	
	public static void testRolls(int[] rolls, int[] expectedScore, String testName) throws Error {
		BowlingGame game = new BowlingGame();
		for(int i = 0; i<rolls.length; ++i) {
			if(game.roll(rolls[i]) == false) {
				throw new Error("ERROR: " + testName + ": invalid roll: " + rolls[i]);
			}
				
			if(game.getScore() != expectedScore[i]) {
				throw new Error("ERROR: " + testName + ": score " + game.getScore() + " does not match expected score " + expectedScore[i]);
			}
		}
		System.out.println("PASS: " + testName);
	}
	
	public static void testSetPinState(int[] pinState, int[] expectedScore, String testName) throws Error {
		BowlingGame game = new BowlingGame();
		for(int i = 0; i<pinState.length; ++i) {
			if(game.setPinState(pinState[i]) == false) {
				throw new Error("ERROR: " + testName + ": invalid pinState: " + pinState[i]);
			}
				
			if(game.getScore() != expectedScore[i]) {
				throw new Error("ERROR: " + testName + ": score " + game.getScore() + " does not match expected score " + expectedScore[i]);
			}
		}
		System.out.println("PASS: " + testName);
	}
	
	public static void expectError(int[] rolls, int[] expectedScore, String testName) throws Error {
		try {
			testRolls(rolls, expectedScore, testName);
			System.out.println("ERROR: " + testName);
		} 
		catch(Error e) {
			System.out.println("PASS: " + testName + " caught " + e.getMessage());
		}
	}

	public static void testGameTooManyRolls(int[] testRolls, String testName) {
		BowlingGame tbg = new BowlingGame();
		for(int i = 0; i<testRolls.length-1; ++i) {
			tbg.roll(testRolls[i]);
		}
		if(tbg.roll(testRolls[testRolls.length-1]) != false)
			System.out.println("ERROR: " + testName);
		else {
			System.out.println("PASS: " + testName);
		}
	}

	public static void testNthRoll(int[] testRolls, int[] base, int[] offset, int[] expected, String testName) {
		BowlingGame tbg = new BowlingGame();
		HashMap<Integer, Integer> errors = new HashMap<Integer, Integer>();
		for(int i = 0; i<testRolls.length; ++i) {
			tbg.roll(testRolls[i]);
		}
		for(int i = 0; i<base.length; ++i) {
			if(tbg.getNthRoll(base[i], offset[i]) != expected[i]) {
				errors.put(base[i], offset[i]);
			}
		}
		
		if(errors.size() == 0)
			System.out.println("PASS: " + testName);
		else {
			System.out.println("ERROR: " + testName);
			for(Map.Entry<Integer, Integer> e: errors.entrySet()) {
				System.out.println("\t Base/Key (" + e.getKey() + "," + e.getValue() + ") does not match expected result");
			}
			
		}
	}
}
