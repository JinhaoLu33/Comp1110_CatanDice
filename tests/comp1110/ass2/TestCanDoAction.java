package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestCanDoAction {

    private String errorPrefix(String board_state, int[] rv, String action) {
	StringJoiner j1 = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j1.add(Integer.toString(rv[i]));
        return "CatanDice.canDoAction(" + action + ", " + board_state + ", {" + j1.toString() + "})";
    }

    private void test(String a, String b, int[] r, boolean answer) {
        String errorPrefix = errorPrefix(b, r, a);
        boolean out = CatanDice.canDoAction(a, b, r);
	assertEquals(answer, out, errorPrefix);
    }

    int n_pos = 0;
    int n_neg = 0;

    public void makeTests(String[] board, int[][] resources, String[] target, String[][][] plans) {
	for (int i = 0; i < board.length; i++) {
	    assert (plans[i] != null);
	    HashSet<String> all_actions = new HashSet<String>();
	    HashSet<String> first_actions = new HashSet<String>();
	    for (int k = 0; k < plans[i].length; k++) {
		for (int j = 0; j < plans[i][k].length; j++)
		    all_actions.add(plans[i][k][j]);
		first_actions.add(plans[i][k][0]);
	    }
	    for (String a : first_actions) {
		n_pos += 1;
		test(a, board[i], resources[i], true);
	    }
	    for (String a : all_actions)
		if (!first_actions.contains(a)) {
		    n_neg += 1;
		    test(a, board[i], resources[i], false);
		}
	}
    }

    @Test
    public void testSet1() {
	makeTests(TestBuildPlan.trivial_board,
		  TestBuildPlan.trivial_resources,
		  TestBuildPlan.trivial_target,
		  TestBuildPlan.trivial_plans);
    }

    @Test
    public void testSet2() {
	makeTests(TestBuildPlan.simple_board,
		  TestBuildPlan.simple_resources,
		  TestBuildPlan.simple_target,
		  TestBuildPlan.simple_plans);
    }

    @Test
    public void testSet3() {
	makeTests(TestBuildPlan.noswap_board,
		  TestBuildPlan.noswap_resources,
		  TestBuildPlan.noswap_target,
		  TestBuildPlan.noswap_plans);
    }

    @Test
    public void testSet4() {
	makeTests(TestBuildPlan.general_board,
		  TestBuildPlan.general_resources,
		  TestBuildPlan.general_target,
		  TestBuildPlan.general_plans);
    }

    /* Some edge cases not covered by the plan-based testing */
    @Test
    public void testSpecial() {
	test("trade 4", "R0", new int[]{2,1,1,1,1,0}, false);
	test("trade 3", "R0", new int[]{0,1,1,1,2,1}, false);
	test("swap 0 3", "J1,J2", new int[]{0,2,2,1,1,0}, false);
	test("swap 1 3", "J1,J2", new int[]{0,2,2,1,1,0}, false);
	test("swap 3 0", "K1,K2", new int[]{0,2,2,1,1,0}, false);
	test("swap 3 1", "J1,K2", new int[]{0,2,2,1,1,0}, false);
    }

	/* Test written specifically to test a false-positive occurring in canDoSequence */
	@Test
	public void testCanDoSequence(){
		test("build R7", "R0,S3,R1,C7,R2,S4,R3,R4,R5,R6,J1,J2,J3",new int[] {1,1,0,1,1,1}, true);
	}

    public static void main(String[] args) {
	TestCanDoAction tests = new TestCanDoAction();
	System.out.println("testing...");
	tests.testSet1();
	tests.testSet2();
	tests.testSet3();
	tests.testSet4();
	tests.testSpecial();
	tests.testCanDoSequence();
	System.out.println("all done! (" + tests.n_pos + " pos, " + tests.n_neg + " neg)");
    }
}
