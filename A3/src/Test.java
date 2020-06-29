import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

class Test<T> {
    Set<Pair> items = new HashSet<>(); 

    public static void main(String[] args) {
        Test<Integer> test = new Test<>();
        test.addPair(1, 2);
        test.addPair(2, 3);

        test.printContains(1, 2);
    }

    void addPair(T obj1, T obj2) {
        items.add(new Pair(obj1, obj2));
    }

    void printContains(T obj1, T obj2) {
        System.out.println(items.contains(new Pair(obj1, obj2)));
    }


    class Vertex {
        T element;
        boolean visited;
    
        Vertex(T _element) {
            element = _element;
        }
    
        @Override
        public boolean equals(Object o) {
            try {
                @SuppressWarnings("unchecked")
                Vertex other = (Vertex) o;
                return element.equals(other.element);
            }
            catch(ClassCastException e) {
                return false;
            }
        }
    }


    class Pair {
        T from;
        T to;
    
        Pair(T _from, T _to) {
            from = _from;
            to = _to;
        }
    
        @Override
        public String toString() {
            return from + "-" + to;
        }
    
        @Override
        public int hashCode() {
            return from.hashCode() + to.hashCode();
        }

        @Override
        public boolean equals(Object o) {
            System.out.println("running equals()");
            try {
                @SuppressWarnings("unchecked")
                Pair other = (Pair) o;
                return (from.equals(other.from) && to.equals(other.to)) || (from.equals(other.to) && to.equals(other.from));
            }
            catch(ClassCastException e) {
                return false;
            }
        }
    }
} 