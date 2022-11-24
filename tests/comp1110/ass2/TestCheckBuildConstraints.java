package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestCheckBuildConstraints {

    private String errorPrefix(String board_state, String target) {
        return "CatanDice.checkBuildConstraints(" + target + ", " + board_state + ")";
    }

    private void test(String s, String b, boolean answer) {
        String errorPrefix = errorPrefix(b, s);
        boolean out = CatanDice.checkBuildConstraints(s, b);
	assertEquals(answer, out, errorPrefix);
    }

    int count_actions(String prefix, String[] plan) {
	int n = 0;
	for (int i = 0; i < plan.length; i++)
	    if (plan[i].startsWith(prefix))
		n += 1;
	return n;
    }

    int n_pos = 0;
    int n_neg = 0;

    public void makeTests(String[] board, String[] target, String[][][] plans) {
	for (int i = 0; i < board.length; i++) {
	    if (plans[i] != null) {
		int n_build = count_actions("build", plans[i][0]);
		if (n_build == 1)
		    n_pos += 1;
		else
		    n_neg += 1;
		test(target[i], board[i], (n_build == 1));
	    }
	}
    }

    @Test
    public void testSet1() {
	makeTests(TestBuildPlan.trivial_board,
		  TestBuildPlan.trivial_target,
		  TestBuildPlan.trivial_plans);
    }

    @Test
    public void testSet2() {
	makeTests(TestBuildPlan.simple_board,
		  TestBuildPlan.simple_target,
		  TestBuildPlan.simple_plans);
    }

    @Test
    public void testSet3() {
	makeTests(TestBuildPlan.noswap_board,
		  TestBuildPlan.noswap_target,
		  TestBuildPlan.noswap_plans);
    }

    @Test
    public void testSet4() {
	makeTests(TestBuildPlan.general_board,
		  TestBuildPlan.general_target,
		  TestBuildPlan.general_plans);
    }

    public static void main(String[] args) {
	TestCheckBuildConstraints tests = new TestCheckBuildConstraints();
	System.out.println("testing...");
	tests.testSet1();
	tests.testSet2();
	tests.testSet3();
	tests.testSet4();
	System.out.println("all done! (" + tests.n_pos + " pos, " + tests.n_neg + " neg)");
    }
}
