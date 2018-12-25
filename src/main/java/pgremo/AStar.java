package pgremo;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.stream.Stream.iterate;

public class AStar {
    public <T> Iterable<T> findPath(T start, Function<T, Float> estCost, Function<T, Map<T, Float>> getNeighbors, Predicate<T> isGoal) {
        Queue<Node<T>> open = new PriorityQueue<>(comparing(Node::getCost));
        open.add(new Node<>(start, null, 0));

        Set<T> closed = new HashSet<>();

        while (!open.isEmpty()) {
            Node<T> e = open.poll();

            if (isGoal.test(e.getValue())) {
                return iterate(e, Objects::nonNull, Node::getParent)
                        .map(Node::getValue)
                        .collect(LinkedList::new, Deque::addFirst, Collection::addAll);
            }

            for (Map.Entry<T, Float> n : getNeighbors.apply(e.getValue()).entrySet()) {
                if (closed.contains(n.getKey())) continue;
                open.add(new Node<>(n.getKey(), e, e.getCost() + n.getValue() + estCost.apply(n.getKey())));
            }

            closed.add(e.getValue());
        }
        return emptyList();
    }

    private class Node<T> {
        private T value;
        private Node<T> parent;
        private float cost;

        Node(T value, Node<T> parent, float cost) {
            this.value = value;
            this.parent = parent;
            this.cost = cost;
        }

        T getValue() {
            return value;
        }

        Node<T> getParent() {
            return parent;
        }

        float getCost() {
            return cost;
        }
    }
}
