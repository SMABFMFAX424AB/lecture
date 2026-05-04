import java.util.*;
import java.util.stream.*;

public class BinaryTree<T extends Comparable<T>> {

    private final Optional<? extends BinaryTreeNode<T>> root;

    private final BinaryTreeFactory<T> treeFactory;

    public BinaryTree(final BinaryTreeNode<T> root, final BinaryTreeFactory<T> treeFactory) {
        this(Optional.of(root), treeFactory);
    }

    public BinaryTree(final Optional<? extends BinaryTreeNode<T>> root, final BinaryTreeFactory<T> treeFactory) {
        this.root = root;
        this.treeFactory = treeFactory;
    }

    public BinaryTree<T> add(final T value) {
        if (this.isEmpty()) {
            return this.treeFactory.create(value);
        }
        return this.treeFactory.create(Optional.of(this.root.get().add(value)));
    }

    public boolean contains(final T value) {
        return this.containsAll(Collections.singleton(value));
    }

    public boolean containsAll(final Collection<? extends T> values) {
        if (this.isEmpty()) {
            return values.isEmpty();
        }
        return this.root.get().containsAll(values);
    }

    @Override
    public boolean equals(final Object o) {
        if (!(o instanceof BinaryTree)) {
            return false;
        }
        return this.root.equals(((BinaryTree<?>)o).root);
    }

    public int getHeight() {
        return this.root.isEmpty() ? 0 : this.root.get().getHeight();
    }

    public Optional<T> getMax() {
        return this.stream().reduce((x,y) -> y);
    }

    public Optional<T> getMin() {
        return this.stream().findFirst();
    }

    public List<T> getValues() {
        return this.stream().toList();
    }

    @Override
    public int hashCode() {
        return 2 * this.root.hashCode();
    }

    public boolean isEmpty() {
        return this.root.isEmpty();
    }

    public Iterator<T> iterator() {
        return this.stream().iterator();
    }

    public BinaryTree<T> remove(final T value) {
        if (this.isEmpty()) {
            return this;
        }
        return this.treeFactory.create(this.root.get().remove(value));
    }

    public int size() {
        if (this.isEmpty()) {
            return 0;
        }
        return this.root.get().size();
    }

    public Stream<T> stream() {
        if (this.isEmpty()) {
            return Stream.empty();
        }
        return this.root.get().stream();
    }

    @Override
    public String toString() {
        return this.root.isEmpty() ? "" : this.root.get().toString();
    }

}
