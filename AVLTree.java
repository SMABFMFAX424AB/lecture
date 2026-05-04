import java.util.*;

public class AVLTree<T extends Comparable<T>> extends BinaryTree<T> {

    public AVLTree(final BinaryTreeNode<T> root, final AVLTreeFactory<T> treeFactory) {
        this(Optional.of(root), treeFactory);
    }

    public AVLTree(final Optional<? extends BinaryTreeNode<T>> root, final AVLTreeFactory<T> treeFactory) {
        super(root, treeFactory);
    }

}
