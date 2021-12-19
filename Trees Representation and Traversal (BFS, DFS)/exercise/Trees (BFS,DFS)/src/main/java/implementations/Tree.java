package implementations;

import interfaces.AbstractTree;

import java.util.*;
import java.util.stream.Collectors;

public class Tree<E> implements AbstractTree<E> {
    private E value;
    private Tree<E> parent;
    private List<Tree<E>> children;

    public Tree(E value, Tree<E>... children) {
        this.value = value;
        this.children = this.initChildren(children);
    }

    private List<Tree<E>> initChildren(Tree<E>[] children) {
        List<Tree<E>> treeChildren = new ArrayList<>();

        for (Tree<E> child : children) {
            child.setParent(this);
            treeChildren.add(child);
        }

        return treeChildren;
    }

    private String getAsStringDFS(Tree<E> node, String indent) {
        StringBuilder result = new StringBuilder(indent + node.getKey());

        for (Tree<E> child : node.getChildren()) {
            result
                    .append(System.lineSeparator())
                    .append(getAsStringDFS(child, indent + "  "));
        }

        return result.toString();
    }

    private void getLongestPathDFS(Tree<E> node, Stack<Tree<E>> longestPath, Stack<Tree<E>> currentPath) {
        if (node.getChildren().isEmpty()) {
            if (longestPath.size() < currentPath.size()) {
                longestPath.clear();

                for (Tree<E> currentPathNode : currentPath) {
                    longestPath.push(currentPathNode);
                }
            }
        } else {
            for (Tree<E> childNode : node.getChildren()) {
                currentPath.push(childNode);
                getLongestPathDFS(childNode, longestPath, currentPath);
                currentPath.pop();
            }
        }
    }

    private void getAllPathsWithGivenSum(Tree<E> node, List<List<E>> paths, Stack<Tree<E>> currentPath, int targetSum) {
        if (node.getChildren().isEmpty()) {
            if (currentPath.stream().mapToInt(x -> (int) x.getKey()).sum() == targetSum) {
                paths.add(new ArrayList<E>(currentPath.stream().map(x -> x.getKey()).collect(Collectors.toList())));
            }
        } else {
            for (Tree<E> childNode : node.getChildren()) {
                currentPath.push(childNode);
                getAllPathsWithGivenSum(childNode, paths, currentPath, targetSum);
                currentPath.pop();
            }
        }
    }

    public List<Tree<E>> getChildren() {
        return this.children;
    }

    @Override
    public void setParent(Tree<E> parent) {
        this.parent = parent;
    }

    @Override
    public void addChild(Tree<E> child) {
        this.children.add(child);
    }

    @Override
    public Tree<E> getParent() {
        return this.parent;
    }

    @Override
    public E getKey() {
        return this.value;
    }

    @Override
    public String getAsString() {
        return this.getAsStringDFS(this, "");
    }

    @Override
    public List<E> getLeafKeys() {
        if (this.value == null) {
            return null;
        }
        Queue<Tree<E>> treeQueue = new ArrayDeque<>();
        treeQueue.offer(this);

        List<E> leafs = new ArrayList<>();

        while (!treeQueue.isEmpty()) {
            Tree<E> currentNode = treeQueue.poll();

            if (currentNode.getChildren().isEmpty()) {
                leafs.add(currentNode.getKey());
            } else {
                for (Tree<E> childNode : currentNode.getChildren()) {
                    treeQueue.offer(childNode);
                }
            }
        }


        return leafs;
    }

    @Override
    public List<E> getMiddleKeys() {
        Queue<Tree<E>> treeQueue = new ArrayDeque<>();
        treeQueue.offer(this);

        List<Tree<E>> trees = new ArrayList<>();
        List<E> middles = new ArrayList<>();

        while (!treeQueue.isEmpty()) {
            Tree<E> currentNode = treeQueue.poll();
            //trees.add(currentNode);
            for (Tree<E> childNode : currentNode.getChildren()) {
                if (!trees.contains(childNode)) {
                    trees.add(childNode);
                }
                treeQueue.offer(childNode);
            }
        }
        for (Tree<E> tree : trees) {
            if (!tree.children.isEmpty() && tree.parent != null) {
                middles.add(tree.value);
            }
        }

        return middles;
    }

    @Override
    public Tree<E> getDeepestLeftmostNode() {
        Stack<Tree<E>> longestPath = new Stack<>();
        Stack<Tree<E>> currentPath = new Stack<>();

        currentPath.push(this);

        getLongestPathDFS(this, longestPath, currentPath);

        List<E> resultPath = new ArrayList<>();

        for (Tree<E> longestPathNode : longestPath) {
            resultPath.add(longestPathNode.getKey());
        }
        return longestPath.get(longestPath.size() - 1);
    }

    @Override
    public List<E> getLongestPath() {
        Stack<Tree<E>> longestPath = new Stack<>();
        Stack<Tree<E>> currentPath = new Stack<>();

        currentPath.push(this);

        getLongestPathDFS(this, longestPath, currentPath);

        List<E> resultPath = new ArrayList<>();

        for (Tree<E> longestPathNode : longestPath) {
            resultPath.add(longestPathNode.getKey());
        }

        return resultPath;
    }

    @Override
    public List<List<E>> pathsWithGivenSum(int sum) {
        List<List<E>> paths = new ArrayList<>();
        Stack<Tree<E>> currentPath = new Stack<>();

        currentPath.push(this);

        getAllPathsWithGivenSum(this, paths, currentPath, sum);

        return paths;
    }

    private void getAllTreesDFS(Tree<E> currentTree, List<Tree<E>> allTrees) {
        allTrees.add(currentTree);

        for (Tree<E> child : currentTree.children) {
            getAllTreesDFS(child, allTrees);
        }
    }

    private List<Tree<E>> getAllTreesDFS() {
        List<Tree<E>> result = new ArrayList<>();
        getAllTreesDFS(this, result);
        return result;
    }

    //@Override
    public List<Tree<E>> subTreesWithGivenSum2(int sum) {
        List<Tree<E>> allTrees = getAllTreesDFS();


        return allTrees.stream()
                .filter(t -> t.getSum() == sum)
                .collect(Collectors.toList());
    }

    private int getSum() {
        return getAllTreesDFS().stream()
                .map(t -> (Integer) t.value)
                .reduce(0, (accumulator, element) -> accumulator += element);
    }
   @Override
    public List<Tree<E>> subTreesWithGivenSum(int sum) {

        Stack<Tree<E>> treeQueue = new Stack<>();
        treeQueue.push(this);
        List<Tree<E>> check = new ArrayList<>();

        int sumToEqual = 0;

        List<Tree<E>> trees = new ArrayList<>();

        while (!treeQueue.isEmpty()) {
            Tree<E> currentNode = treeQueue.pop();
            check.add(currentNode);
            sumToEqual = 0;
            sumToEqual += (int) currentNode.value;
            for (int i = currentNode.getChildren().size() - 1; i >= 0; i--) {
                treeQueue.push(currentNode.getChildren().get(i));
                sumToEqual += (int) currentNode.getChildren().get(i).value;
            }
            if (sumToEqual == sum && currentNode.parent != null) {
                trees.add(currentNode);
            }
        }

       for (int index = 0; index < trees.size() - 1; index++) {
           if (trees.get(index).getChildren().contains(trees.get(index+1))){
               trees.remove(index);
               index--;
           }
       }


        return trees;
    }

}
