import java.lang.StringBuilder;

public class BowlingGameRunner {

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
			System.out.println("ERROR: OneRoll Strike");
		}
		else {
			System.out.println("PASS: One-Roll");
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
		//Bad Rolls
		BowlingGame tbg = new BowlingGame();
		int[] testRolls = new int[] {10,10,10,10,10,10,10,10,10,8,1,10};
		for(int i = 0; i<testRolls.length-1; ++i) {
			tbg.roll(testRolls[i]);
		}
		if(tbg.roll(testRolls[testRolls.length-1]) != false)
			System.out.println("ERROR: BAD-GAME-ROLLS");
		else {
			System.out.println("PASS: BAD-GAME-ROLLS");
		}
		
		//Bad Rolls 2
	 	tbg = new BowlingGame();
		testRolls = new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,10,10};
		for(int i = 0; i<testRolls.length-1; ++i) {
			tbg.roll(testRolls[i]);
		}
		if(tbg.roll(testRolls[testRolls.length-1]) != false)
			System.out.println("ERROR: BAD-GAME-ROLLS2");
		else {
			System.out.println("PASS: BAD-GAME-ROLLS2");
		}
		
		//Bad Rolls 3
		tbg = new BowlingGame();
		testRolls = new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,9,10,10};
		for(int i = 0; i<testRolls.length-1; ++i) {
			tbg.roll(testRolls[i]);
		}
		if(tbg.roll(testRolls[testRolls.length-1]) != false)
			System.out.println("ERROR: BAD-GAME-ROLLS3");
		else {
			System.out.println("PASS: BAD-GAME-ROLLS3");
		}
		
		//spare extra rolls
		tbg = new BowlingGame();
		testRolls = new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,10};
		for(int i = 0; i<testRolls.length-1; ++i) {
			tbg.roll(testRolls[i]);
		}
		if(tbg.roll(testRolls[testRolls.length-1]) == false)
			System.out.println("ERROR: SPARE-EXTRA-ROLL");
		else {
			System.out.println("PASS: SPARE-EXTRA-ROLL");
		}
		
		//strike extra rolls
		tbg = new BowlingGame();
		testRolls = new int[] {10,10,10,3,4,5,1,0,10,8,2,7,2,4,5,8,2,9,10};
		for(int i = 0; i<testRolls.length-1; ++i) {
			tbg.roll(testRolls[i]);
		}
		if(tbg.roll(testRolls[testRolls.length-1]) == false)
			System.out.println("ERROR: STRIKE-EXTRA-ROLLS");
		else {
			System.out.println("PASS: STRIKE-EXTRA-ROLLS");
		}
	}

	public static void testFullGame() {
		BowlingGame game = new BowlingGame();
	}
}
