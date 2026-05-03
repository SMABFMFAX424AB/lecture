import java.util.*;

public class ImmutableSet<T> implements Iterable<T> {

    public static class ImmutableSetIterator<T> implements Iterator<T> {
        ImmutableSetNode<T> current;
        ImmutableSet<T> used;

        public ImmutableSetIterator(final ImmutableSetNode<T> current) {
            this.current = current;
            this.used = new ImmutableSet<>();
            this.forward();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public T next() {
            final T result = this.current.element;
            this.used = this.used.add(this.current.element);
            this.current = this.current.next;
            this.forward();
            return result;
        }

        private void forward() {
            while(this.current != null && (!this.current.added || this.used.contains(this.current.element))){
                this.used = this.used.add(this.current.element);
                this.current = this.current.next;
            }
        }
    }

    private static class ImmutableSetNode<T>{
        final boolean added;
        final T element;
        final ImmutableSetNode<T> next;

        ImmutableSetNode(final T element, final ImmutableSetNode<T> next, final boolean added) {
            this.element = element;
            this.next = next;
            this.added = added;
        }
    }

    public static void main(final String[] args) {
        final ImmutableSet<Integer> set =
            new ImmutableSet<Integer>().add(3).add(7).add(-4).add(7).remove(3).remove(7).add(3);
        for (final Integer i : set) {
            System.out.println(i);
        }
        System.out.println(set.size());
    }

    final ImmutableSetNode<T> root;

    public ImmutableSet() {
        this.root = null;
    }

    public ImmutableSet(final Collection<T> c) {
        if (c == null || c.isEmpty()) {
            this.root = null;
        } else {
            final Iterator<T> iterator = c.iterator();
            ImmutableSetNode<T> current = new ImmutableSetNode<T>(iterator.next(), null, true);
            while (iterator.hasNext()) {
                current = new ImmutableSetNode<T>(iterator.next(), current, true);
            }
            this.root = current;
        }
    }

    private ImmutableSet(final ImmutableSetNode<T> root) {
        this.root = root;
    }

    public ImmutableSet<T> add(final T e) {
        return new ImmutableSet<T>(new ImmutableSetNode<T>(e, this.root, true));
    }

    public ImmutableSet<T> addAll(final Collection<? extends T> c) {
        ImmutableSet<T> result = this;
        for (final T element : c) {
            result = result.add(element);
        }
        return result;
    }

    public ImmutableSet<T> clear() {
        return new ImmutableSet<T>();
    }

    public boolean contains(final Object o) {
        for(final T t : this) {
            if(t.equals(o)){
                return true;
            }
        }
        /*
            Iterator<T> iterator = this.iterator();
            while(iterator.hasNext()) {
                T t = iterator.next();
                Gleicher Schleifenkörper wie oben
            }
        */
        return false;
        /*ImmutableSetNode<T> current = root;
        while(current != null) {
            if (current.element.equals(o)){
                return current.added;
            }
            current = current.next;
        }
        return false;*/
    }

    public boolean containsAll(final Collection<?> c) {
        for (final Object o : c) {
            if (!this.contains(o)) {
                return false;
            }
        }
        return true;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    @Override
    public Iterator<T> iterator() {
        return new ImmutableSetIterator<T>(this.root);
    }

    public ImmutableSet<T> remove(final T e) {
        return new ImmutableSet<T>(new ImmutableSetNode<T>(e, this.root, false));
    }

    public ImmutableSet<T> removeAll(final Collection<T> c) {
        ImmutableSet<T> result = this;
        for (final T element : c) {
            result = result.remove(element);
        }
        return result;
    }

    public ImmutableSet<T> retainAll(final Collection<?> c) {
        ImmutableSet<T> result = this;
        for (final T element : this) {
            if (!c.contains(element)) {
                result = result.remove(element);
            }
        }
        return result;
    }

    public int size() {
        int result = 0;
        final Iterator<T> iterator = this.iterator();
        while (iterator.hasNext()) {
            iterator.next();
            result++;
        }
        return result;
    }


}
