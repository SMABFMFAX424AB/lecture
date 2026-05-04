import java.util.*;

public class BinaryTreeFactory <T extends Comparable<T>> {

    protected final BinaryTreeNodeFactory<T> nodeFactory;

    public BinaryTreeFactory(final BinaryTreeNodeFactory<T> nodeFactory) {
        this.nodeFactory = nodeFactory;
    }

    public BinaryTree<T> create(final Optional<? extends BinaryTreeNode<T>> root) {
        return new BinaryTree<T>(root, this);
    }

    public BinaryTree<T> create(final T value) {
        return new BinaryTree<T>(this.nodeFactory.create(value), this);
    }

}
