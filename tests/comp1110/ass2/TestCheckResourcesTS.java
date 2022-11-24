package comp1110.ass2;

import java.util.*;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// @org.junit.jupiter.api.Timeout(value = 1000, unit = MILLISECONDS)

public class TestCheckResourcesTS {

    private String errorPrefix(String target, String board_state, int[] resources) {
	StringJoiner j = new StringJoiner(",");
	for (int i = 0; i < 6; i++)
	    j.add(Integer.toString(resources[i]));
	return "CatanDice.checkResourcesWithTradeAndSwap(" + target + ", " + board_state + ", {" + j.toString() + "})";
    }

    private void test(String s, String b, int[] r, boolean answer) {
        String errorPrefix = errorPrefix(s, b, r);
        boolean out = CatanDice.checkResourcesWithTradeAndSwap(s, b, r);
	assertEquals(answer, out, errorPrefix);
    }

    int count_actions(String prefix, String[] plan) {
	int n = 0;
	for (int i = 0; i < plan.length; i++)
	    if (plan[i].startsWith(prefix))
		n += 1;
	return n;
    }

    int find_match(String b, String t, String[] bs, String[] ts, String[][][] ps) {
	for (int i = 0; i < bs.length; i++) {
	    if (bs[i].equals(b) && ts[i].equals(t)) {
		int nb = count_actions("build", ps[i][0]);
		if (nb == 1)
		    return i;
	    }
	}
	return -1;
    }

    int n_pos = 0;
    int n_neg = 0;

    public void makeTrueTests(String[] board, int[][] resources, String[] target, String[][][] plans) {
	for (int i = 0; i < board.length; i++) {
	    assert (plans[i] != null);
	    int nb = count_actions("build", plans[i][0]);
	    int nx = count_actions("trade", plans[i][0]) + count_actions("swap", plans[i][0]);
	    if (nx > 0 && nb == 1) {
		//System.out.println(errorPrefix(target[i], board[i], resources[i]) + " | " + PlanUtils.planToString(plans[i][0]));
		test(target[i], board[i], resources[i], true);
		n_pos += 1;
	    }
	}
    }

    public void makeFalseTests() {
	for (int i = 0; i < TestBuildPlan.unsolvable_board.length; i++) {
	    int f = find_match(TestBuildPlan.unsolvable_board[i],
			       TestBuildPlan.unsolvable_target[i],
			       TestBuildPlan.trivial_board,
			       TestBuildPlan.trivial_target,
			       TestBuildPlan.trivial_plans);
	    if (f < 0)
		f = find_match(TestBuildPlan.unsolvable_board[i],
			       TestBuildPlan.unsolvable_target[i],
			       TestBuildPlan.noswap_board,
			       TestBuildPlan.noswap_target,
			       TestBuildPlan.noswap_plans);
	    if (f < 0)
		f = find_match(TestBuildPlan.unsolvable_board[i],
			       TestBuildPlan.unsolvable_target[i],
			       TestBuildPlan.general_board,
			       TestBuildPlan.general_target,
			       TestBuildPlan.general_plans);
	    if (f >= 0) {
		test(TestBuildPlan.unsolvable_target[i],
		     TestBuildPlan.unsolvable_board[i],
		     TestBuildPlan.unsolvable_resources[i], false);
		n_neg += 1;
	    }
	}
    }

    @Test
    public void testSet3() {
	makeTrueTests(TestBuildPlan.noswap_board,
		      TestBuildPlan.noswap_resources,
		      TestBuildPlan.noswap_target,
		      TestBuildPlan.noswap_plans);
    }

    @Test
    public void testSet4() {
	makeTrueTests(TestBuildPlan.general_board,
		      TestBuildPlan.general_resources,
		      TestBuildPlan.general_target,
		      TestBuildPlan.general_plans);
    }

    @Test
    public void testSet5() {
	makeFalseTests();
    }


	public static void main(String[] args) {
	TestCheckResourcesTS tests = new TestCheckResourcesTS();
	System.out.println("testing...");
	tests.testSet3();
	tests.testSet4();
	tests.testSet5();
	System.out.println("all done! (" + tests.n_pos + " pos, " + tests.n_neg + " neg)");
    }


}
