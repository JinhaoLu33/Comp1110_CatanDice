package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestCanDoSequence {

    private String errorPrefix(String board_state, int[] rv, String[] plan) {
	StringJoiner j1 = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j1.add(Integer.toString(rv[i]));
	StringJoiner j2 = new StringJoiner(",");
	for (int i = 0; i < plan.length; i++)
	    j2.add(plan[i]);
        return "CatanDice.canDoSequence({" + j2.toString() + "}, " + board_state + ", {" + j1.toString() + "})";
    }

    private void test(String[] plan, String b, int[] rv, boolean answer) {
        String errorPrefix = errorPrefix(b, rv, plan);
        boolean out = CatanDice.canDoSequence(plan, b, rv);
	assertEquals(answer, out, errorPrefix);
    }

    String[] subsequence(String[] plan, int exclude) {
	String[] sub = new String[plan.length - 1];
	for (int i = 0; i + 1 < plan.length; i++)
	    sub[i] = plan[i < exclude ? i : i + 1];
	return sub;
    }

    int n_pos = 0;
    int n_neg = 0;

    public void makeTests(String[] board, int[][] resources, String[] target, String[][][] plans) {
	for (int i = 0; i < board.length; i++) {
	    if (plans[i] != null) {
		for (int k = 0; k < plans[i].length; k++) {
		    n_pos += 1;
		    test(plans[i][k], board[i], resources[i], true);
		    if (plans[i][k].length > 1) {
			for (int p = 0; p + 1 < plans[i][k].length; p++) {
			    String[] invalid_plan = subsequence(plans[i][k], p);
			    n_neg += 1;
			    test(invalid_plan, board[i], resources[i], false);
			}
		    }
		}
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
	test(new String[]{"trade 4", "trade 3"}, "R0", new int[]{0,1,1,1,1,2}, false);
	test(new String[]{"swap 3 0", "swap 4 0"}, "J1,J2", new int[]{0,2,2,1,1,0}, false);
	test(new String[]{"swap 3 0", "swap 3 1"}, "J1,J2", new int[]{1,1,2,1,1,0}, false);
	test(new String[]{"swap 3 0", "swap 4 0"}, "J1,J2,J3,J4,J5,J6", new int[]{0,2,2,1,1,0}, true);
	test(new String[]{"swap 3 0", "swap 3 1"}, "J1,J2,J3,J4,J5,J6", new int[]{1,1,2,1,1,0}, false);
    }

    public static void main(String[] args) {
	TestCanDoSequence tests = new TestCanDoSequence();
	System.out.println("testing...");
	tests.testSet1();
	tests.testSet2();
	tests.testSet3();
	tests.testSet4();
	tests.testSpecial();
	System.out.println("all done! (" + tests.n_pos + " pos, " + tests.n_neg + " neg)");
    }
}
