package solutions;

import java.util.ArrayList;
import java.util.List;

public class BinaryTree {
    private int value;

    private BinaryTree left;

    private BinaryTree right;

    private BinaryTree parent;

    public BinaryTree(int key, BinaryTree left, BinaryTree right) {
        this.value = key;
        this.left = left;
        this.right = right;
        this.setParent(null);
        if (this.left != null) this.left.setParent(this);
        if (this.right != null) this.right.setParent(this);
    }

    public int getValue() {
        return value;
    }

    public BinaryTree getLeft() {
        return left;
    }

    public BinaryTree getRight() {
        return right;
    }

    public BinaryTree getParent() {
        return parent;
    }

    public void setParent(BinaryTree parent) {
        this.parent = parent;
    }

    private BinaryTree findNode(BinaryTree current, int node) {
        if (current == null) return null;

        if (current.getValue() == node) {
            return current;
        } else {
            BinaryTree foundNode = this.findNode(current.getLeft(), node);

            if (foundNode == null) {
                foundNode = this.findNode(current.getRight(), node);
            }

            return foundNode;
        }
    }

    private List<BinaryTree> findAncestors(int node) {
        List<BinaryTree> result = new ArrayList<>();
        BinaryTree foundNode = this.findNode(this, node);

        while (foundNode.getParent() != null) {
            foundNode = foundNode.getParent();
            result.add(foundNode);
        }

        return result;
    }

    public Integer findLowestCommonAncestor(int first, int second) {
        List<BinaryTree> firstAncestors = this.findAncestors(first);
        List<BinaryTree> secondAncestors = this.findAncestors(second);

        for (int i = 0; i < firstAncestors.size(); i++) {
            if (secondAncestors.contains(firstAncestors.get(i))) {
                return firstAncestors.get(i).getValue();
            }
        }

        return null;
    }

    public List<Integer> topView() {
        List<Integer> list = new ArrayList<>();
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        topView(list1, this.left);
        topView2(list2, this.right);
        for (int i = 0; i < list1.size(); i++) {
            list.add(list1.get(i));
        }
        for (int i = 0; i < list2.size(); i++) {
            list.add(list2.get(i));
        }
        list.add(this.value);
        return list;
    }

    private void topView(List<Integer> list, BinaryTree binaryTree) {
        list.add(binaryTree.getValue());
        if (binaryTree.getLeft() == null) {
            return;
        }
        topView(list, binaryTree.left);

        return;

    }

    private void topView2(List<Integer> list, BinaryTree binaryTree) {
        list.add(binaryTree.getValue());
        if (binaryTree.getRight() == null) {
            return;
        }
        topView2(list, binaryTree.right);

        return;

    }
}
