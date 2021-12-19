package implementations;

import interfaces.Solvable;

import java.util.ArrayDeque;

public class BalancedParentheses implements Solvable {
    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<String> deque = new ArrayDeque<>();
        String[] toCheck = parentheses.split("");

        if (parentheses.isEmpty()) {
            return false;
        }
        for (int index = 0; index < toCheck.length; index++) {
            deque.add(toCheck[index]);
        }

        boolean check = false;

        while (!deque.isEmpty()) {
            String first = deque.poll();
            String last = deque.removeLast();

            if (first == null || last == null){
                return false;
            }

            if (first.equals("{") && last.equals("}")) {
                check = true;
            } else if (first.equals("(") && last.equals(")")) {
                check = true;
            } else if (first.equals("[") && last.equals("]")) {
                check = true;
            } else {
                check = false;
            }
        }
        return check;
    }
}
