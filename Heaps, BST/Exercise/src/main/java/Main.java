public class Main {
    public static void main(String[] args) {

        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree(4);
        binarySearchTree.insert(5);
        binarySearchTree.insert(6);
        binarySearchTree.insert(3);
        binarySearchTree.insert(2);
        boolean contains = binarySearchTree.contains(2);
        System.out.println(contains);
        System.out.println(binarySearchTree.count());
        System.out.println(binarySearchTree.rank(7));

    }
}
