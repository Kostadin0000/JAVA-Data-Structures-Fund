import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.function.Consumer;

public class BinarySearchTree<E extends Comparable<E>> {
    private Node<E> root;

    public static class Node<E> {
        private E value;
        private Node<E> leftChild;
        private Node<E> rightChild;

        public Node(E value) {
            this.value = value;
        }

        public Node<E> getLeft() {
            return this.leftChild;
        }

        public void setLeft(Node<E> leftChild) {
            this.leftChild = leftChild;
        }

        public Node<E> getRight() {
            return this.rightChild;
        }

        public void setRight(Node<E> rightChild) {
            this.rightChild = rightChild;
        }

        public E getValue() {
            return this.value;
        }
    }

    public BinarySearchTree() {
    }

    public BinarySearchTree(E value) {
        this.root = new Node<>(value);
    }

    public void eachInOrder(Consumer<E> consumer) {
        this.internalEachInOrder(this.root, consumer);
    }

    private void internalEachInOrder(Node<E> node, Consumer<E> consumer) {
        if (node == null) {
            return;
        }

        this.internalEachInOrder(node.getLeft(), consumer);
        consumer.accept(node.getValue());
        this.internalEachInOrder(node.getRight(), consumer);
    }

    public Node<E> getRoot() {
        return this.root;
    }

    public void insert(E element) {
        if (this.root == null) {
            this.root = new Node<>(element);
        } else {
            this.internalInsert(this.root, element);
        }
    }

    private void internalInsert(Node<E> node, E element) {
        if (this.isGreaterThanNode(node, element)) {
            if (node.getRight() == null) {
                node.setRight(new Node<>(element));
            } else {
                this.internalInsert(node.getRight(), element);
            }
        } else if (this.isLessThanNode(node, element)) {
            if (node.getLeft() == null) {
                node.setLeft(new Node<>(element));
            } else {
                this.internalInsert(node.getLeft(), element);
            }
        }
    }

    public boolean contains(E element) {
        return this.internalSearch(this.root, element) != null;
    }

    private Node<E> internalSearch(Node<E> node, E element) {
        if (node == null) {
            return null;
        }

        if (this.isGreaterThanNode(node, element)) {
            return this.internalSearch(node.getRight(), element);
        } else if (this.isLessThanNode(node, element)) {
            return this.internalSearch(node.getLeft(), element);
        }

        return node;
    }

    public BinarySearchTree<E> search(E element) {
        Node<E> node = this.internalSearch(this.root, element);

        return node == null ? null : new BinarySearchTree<>(node.getValue());
    }

    public List<E> range(E first, E second) {
        List<E> result = new ArrayList<>();

        this.internalEachInOrder(this.root, (element) -> {
            if (element.compareTo(first) >= 0 && element.compareTo(second) <= 0) {
                result.add(element);
            }
        });

        return result;
    }

    public void deleteMin() {
        if (this.root == null) {
            throw new IllegalArgumentException();
        }

        if (this.root.getLeft() == null) {
            this.root = this.root.getRight();
        } else {
            Node<E> current = this.root;

            while (current.getLeft().getLeft() != null) {
                current = current.getLeft();
            }

            current.setLeft(current.getLeft().getRight());
        }
    }

    public void deleteMax() {
        if (this.root == null) {
            throw new IllegalArgumentException();
        }

        if (this.root.getRight() == null) {
            this.root = this.root.getLeft();
        } else {
            Node<E> current = this.root;

            while (current.getRight().getRight() != null) {
                current = current.getRight();
            }

            current.setRight(current.getRight().getLeft());
        }
    }

    public int count() {
        return this.internalCount(this.root);
    }

    private Integer internalCount(Node<E> node) {
        if (node == null) {
            return 0;
        } else {
            return 1
                    + (node.getLeft() == null ? 0 : this.internalCount(node.getLeft()))
                    + (node.getRight() == null ? 0 : this.internalCount(node.getRight()));
        }
    }

    public int rank(E element) {
        if(this.root == null) {
            return 0;
        }

        Node<E> current = this.root;
        Queue<Node<E>> queue = new ArrayDeque<>();
        queue.offer(current);

        Integer result = 0;

        while (!queue.isEmpty()) {
            current = queue.poll();

            if (this.isGreaterThanNode(current, element)) {
                result++;
            }

            if (current.getLeft() != null) queue.offer(current.getLeft());
            if (current.getRight() != null) queue.offer(current.getRight());
        }

        return result;
    }

    public E ceil(E element) {
        if (this.root == null) {
            return null;
        } else {
            Node<E> current = this.root;
            Node<E> highestNode = null;

            while (current != null) {
                if (this.isGreaterThanNode(current, element)) {
                    current = current.getRight();
                } else if (this.isLessThanNode(current, element)) {
                    highestNode = current;
                    current = current.getLeft();
                } else {
                    if (current.getRight() != null) {
                        highestNode = current.getRight();
                        current = current.getRight().getLeft();
                    } else {
                        break;
                    }
                }
            }

            return highestNode == null ? null : highestNode.getValue();
        }
    }

    public E floor(E element) {
        if (this.root == null) {
            return null;
        } else {
            Node<E> current = this.root;
            Node<E> lowestNode = null;

            while (current != null) {
                if (this.isLessThanNode(current, element)) {
                    current = current.getLeft();
                } else if(this.isGreaterThanNode(current, element)) {
                    lowestNode = current;
                    current = current.getRight();
                } else {
                    if (current.getLeft() != null) {
                        lowestNode = current.getLeft();
                        current = current.getLeft().getRight();
                    } else {
                        break;
                    }
                }
            }

            return lowestNode == null ? null : lowestNode.getValue();
        }
    }

    private Boolean isGreaterThanNode(Node<E> node, E element) {
        return node.getValue().compareTo(element) < 0;
    }

    private Boolean isLessThanNode(Node<E> node, E element) {
        return node.getValue().compareTo(element) > 0;
    }

    private Boolean isEqualToNode(Node<E> node, E element) {
        return node.getValue().equals(element);
    }
}