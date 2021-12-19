import implementations.Tree;
import implementations.TreeFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        int n = Integer.parseInt(scanner.nextLine());
        int sum = 0;
        String[] input = new String[n-1];
        for (int index = 0; index < n; index++) {
            if (index == n - 1) {
                sum = Integer.parseInt(scanner.nextLine());
            } else {
                input[index] = scanner.nextLine();
            }

        }

        TreeFactory treeFactory = new TreeFactory();

        Tree<Integer> tree = treeFactory.createTreeFromStrings(input);

        List<Tree<Integer>> trees = tree.subTreesWithGivenSum(sum);

        List<Tree<Integer>> trees2 = new ArrayList<>();

        for (Tree<Integer> integerTree : trees) {
            for (Tree<Integer> child : integerTree.getChildren()) {
                trees2.add(child);
            }
            trees2.add(0,integerTree);
        }
        StringBuilder sb = new StringBuilder();
        trees2.forEach(a -> sb.append(a.getKey() + " "));

        System.out.printf("Subtrees of sum %d:%n%s%n", sum,
                String.join(System.lineSeparator(), sb.toString()).trim());
    }
}
