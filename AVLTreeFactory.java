import java.util.*;

public class AVLTreeFactory <T extends Comparable<T>> extends BinaryTreeFactory<T> {

    public AVLTreeFactory(final AVLTreeNodeFactory<T> nodeFactory) {
        super(nodeFactory);
    }

    @Override
    public AVLTree<T> create(final Optional<? extends BinaryTreeNode<T>> root) {
        return new AVLTree<T>(root, this);
    }

    @Override
    public AVLTree<T> create(final T value) {
        return new AVLTree<T>(((AVLTreeNodeFactory<T>)this.nodeFactory).create(value), this);
    }

}
