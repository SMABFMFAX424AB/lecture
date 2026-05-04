import java.util.*;

public class AVLTreeNode<T extends Comparable<T>> extends BinaryTreeNode<T> {

    private final int height;

    public AVLTreeNode(final T value, final AVLTreeNodeFactory<T> nodeFactory) {
        this(value, Optional.empty(), Optional.empty(), nodeFactory);
    }

    public AVLTreeNode(
        final T value,
        final Optional<? extends BinaryTreeNode<T>> leftChild,
        final Optional<? extends BinaryTreeNode<T>> rightChild,
        final AVLTreeNodeFactory<T> nodeFactory
    ) {
        super(value, leftChild, rightChild, nodeFactory);
        this.height = Math.max(BinaryTreeNode.height(leftChild), BinaryTreeNode.height(rightChild)) + 1;
    }

    @Override
    public BinaryTreeNode<T> add(final T value) {
        if (this.value.compareTo(value) < 0) {
            if (this.rightChild.isEmpty()) {
                return this.setRightChild(this.nodeFactory.create(value));
            }
            return ((AVLTreeNode<T>)this.setRightChild(((AVLTreeNode<T>) this.rightChild.get().add(value)).balance())).balance();
        }
        if (this.leftChild.isEmpty()) {
            return this.setLeftChild(this.nodeFactory.create(value));
        }
        return ((AVLTreeNode<T>)this.setLeftChild(((AVLTreeNode<T>)this.leftChild.get().add(value)).balance())).balance();
    }

    @Override
    public int getHeight() {
        return this.height;
    }


    @SuppressWarnings("unchecked")
    @Override
    public Optional<? extends BinaryTreeNode<T>> remove(final T value) {
        final int comparison = this.value.compareTo(value);
        if (comparison == 0) {
            if (this.leftChild.isEmpty()) {
                if (this.rightChild.isEmpty()) {
                    return Optional.empty();
                }
                return Optional.of(this.rightChild.get());
            }
            if (this.rightChild.isEmpty()) {
                return Optional.of(this.leftChild.get());
            }
            final T min = this.rightChild.get().getMin();
            final Optional<? extends BinaryTreeNode<T>> newRight = this.rightChild.get().remove(min);
            return Optional.of(
                ((AVLTreeNode<T>)this.nodeFactory.create(
                    min,
                    this.leftChild,
                    newRight.isEmpty() ? newRight : Optional.of(((AVLTreeNode<T>)newRight.get()).balance())
                )).balance()
            );
        }
        if (comparison < 0) {
            if (this.rightChild.isEmpty()) {
                return Optional.of(this);
            }
            final Optional<? extends BinaryTreeNode<T>> newRight = this.rightChild.get().remove(value);
            return Optional.of(
                ((AVLTreeNode<T>)this.setRightChild(newRight.isEmpty() ? newRight : Optional.of(((AVLTreeNode<T>)newRight.get()).balance()))).balance()
            );
        }
        if (this.leftChild.isEmpty()) {
            return Optional.of(this);
        }
        final Optional<? extends BinaryTreeNode<T>> newLeft = this.leftChild.get().remove(value);
        return Optional.of(
            ((AVLTreeNode<T>)this.setLeftChild(newLeft.isEmpty() ? newLeft : Optional.of(((AVLTreeNode<T>)newLeft.get()).balance()))).balance()
        );
    }

    private AVLTreeNode<T> balance() {
        final int diff = this.leftHeight() - this.rightHeight();
        if (diff < -1) {
            return this.balanceRightToLeft();
        } else if (diff > 1) {
            return this.balanceLeftToRight();
        } else {
            return this;
        }
    }

    @SuppressWarnings("unchecked")
    private AVLTreeNode<T> balanceLeftToRight() {
        final AVLTreeNode<T> left = (AVLTreeNode<T>)this.leftChild.get();
        if (
            left.rightChild.isEmpty() ||
            (left.leftChild.isPresent() && left.rightChild.get().getHeight() <= left.leftChild.get().getHeight())
        ) {
            return this.rotateRight();
        }
        return ((AVLTreeNode<T>)this.setLeftChild(left.rotateLeft())).rotateRight();
    }

    @SuppressWarnings("unchecked")
    private AVLTreeNode<T> balanceRightToLeft() {
        final AVLTreeNode<T> right = (AVLTreeNode<T>)this.rightChild.get();
        if (
            right.leftChild.isEmpty() ||
            (right.rightChild.isPresent() && right.leftChild.get().getHeight() <= right.rightChild.get().getHeight())
        ) {
            return this.rotateLeft();
        }
        return ((AVLTreeNode<T>)this.setRightChild(right.rotateRight())).rotateLeft();
    }

    private int leftHeight() {
        return BinaryTreeNode.height(this.leftChild);
    }

    private int rightHeight() {
        return BinaryTreeNode.height(this.rightChild);
    }

    private AVLTreeNode<T> rotateLeft() {
        return (AVLTreeNode<T>)this.rightChild.get().setLeftChild(this.setRightChild(this.rightChild.get().leftChild));
    }

    private AVLTreeNode<T> rotateRight() {
        return (AVLTreeNode<T>)this.leftChild.get().setRightChild(this.setLeftChild(this.leftChild.get().rightChild));
    }

}
