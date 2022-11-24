package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestCheckResources {

    private String errorPrefix(String target, int[] resources) {
	StringJoiner j = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j.add(Integer.toString(resources[i]));
	return "CatanDice.checkResources(" + target + ", {" + j.toString() + "})";
    }

    private void test(String s, int[] r, boolean answer) {
        String errorPrefix = errorPrefix(s, r);
        boolean out = CatanDice.checkResources(s, r);
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

    public void makeTests(String[] board, int[][] resources, String[] target, String[][][] plans) {
	for (int i = 0; i < board.length; i++) {
	    if (plans[i] != null) {
		int nb = count_actions("build", plans[i][0]);
		int nx = count_actions("trade", plans[i][0]) + count_actions("swap", plans[i][0]);
		if (nx == 0) {
		    //System.out.println("pos: " + target[i]);
		    test(target[i], resources[i], true);
		    n_pos += 1;
		}
		else if (nb == 1) {
		    //System.out.println("neg: " + target[i]);
		    test(target[i], resources[i], false);
		    n_neg += 1;
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

    public static void main(String[] args) {
	TestCheckResources tests = new TestCheckResources();
	System.out.println("testing...");
	tests.testSet1();
	tests.testSet2();
	tests.testSet3();
	tests.testSet4();
	System.out.println("all done! (" + tests.n_pos + " pos, " + tests.n_neg + " neg)");
    }
}
