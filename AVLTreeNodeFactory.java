import java.util.*;

public class AVLTreeNodeFactory<T extends Comparable<T>> extends BinaryTreeNodeFactory<T> {

    @Override
    public AVLTreeNode<T> create(final T value) {
        return new AVLTreeNode<T>(value, Optional.empty(), Optional.empty(), this);
    }

    @Override
    public AVLTreeNode<T> create(final T value, final Optional<?extends BinaryTreeNode<T>> leftChild, final Optional<?extends BinaryTreeNode<T>> rightChild) {
        return new AVLTreeNode<T>(value, leftChild, rightChild, this);
    }

}
