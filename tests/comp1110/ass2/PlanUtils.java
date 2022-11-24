package comp1110.ass2;

import java.util.*;

public class PlanUtils {

    public static String planToString(String[] plan) {
	if (plan == null) return "null";
	StringJoiner j = new StringJoiner(",");
	for (var action : plan)
	    j.add(action);
	return j.toString();
    }

    public static String planToJava(String[] plan) {
	if (plan == null) return "null";
	StringJoiner j = new StringJoiner(",");
	for (var action : plan)
	    j.add("\"" + action + "\"");
	return "{" + j.toString() + "}";
    }

    public static String planSetToString(String[][] plans) {
	StringJoiner j = new StringJoiner("; or ");
	for (var plan : plans)
	    j.add(planToString(plan));
	return j.toString();
    }

    public static String planSetToJava(String[][] plans) {
	StringJoiner j = new StringJoiner(", ");
	for (var plan : plans)
	    j.add(planToJava(plan));
	return "{" + j.toString() + "}";
    }

    public static boolean planEquals(String[] planA, String[] planB) {
	if (planA.length != planB.length)
	    return false;
	boolean all_equal = true;
	for (int j = 0; j < planA.length; j++)
	    if (!planA[j].equals(planB[j]))
		all_equal = false;
	return all_equal;
    }

    public static boolean equalsOneOf(String[] plan, String[][] planset) {
	for (int i = 0; i < planset.length; i++) {
	    if (planEquals(plan, planset[i]))
		return true;
	}
	return false;
    }

    public static boolean equalsOneOf(String[] plan, Vector<String[]> planset) {
	for (int i = 0; i < planset.size(); i++) {
	    if (planEquals(plan, planset.get(i)))
		return true;
	}
	return false;
    }

}
