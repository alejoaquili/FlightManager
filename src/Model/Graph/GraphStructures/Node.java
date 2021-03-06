package Model.Graph.GraphStructures;


import Model.SortedList;

import java.util.*;

public class Node<T,V> {

    private T element;
    private Map<Node<T,V>, List<SortedList<Arc<T,V>>>> outArcsMap;
    private List<Arc<T,V>> outArcsList;
    private List<Arc<T,V>> inArcs;
    private List<Comparator<Arc<T,V>>> comparators;
    private boolean visited;
    private int tag;

    public Node(T element, List<Comparator<Arc<T,V>>> comparators) {
        this.element = element;
        this.inArcs = new ArrayList<>();
        this.outArcsList = new ArrayList<>();
        this.outArcsMap = new HashMap<>();
        this.comparators = comparators;
        this.visited = false;
        this.tag = 0;
    }

    public List<Arc<T,V>> getInArcs(){
        return inArcs;
    }

    public List<Arc<T, V>> getOutArcs(){
        return outArcsList;
    }

    public boolean getVisited(){
        return this.visited;
    }

    public int getTag(){
        return  this.tag;
    }

    public T getElement(){
        return  this.element;
    }

    public void addInArc(Arc<T,V> arc) {
        inArcs.add(arc);
    }

    public void removeInArc(Arc<T,V> arc) {
        inArcs.remove(arc);
    }

    public void addOutArc(Arc<T,V> arc) {
        outArcsList.add(arc);
        if(!outArcsMap.containsKey(arc.getTarget())) {
            List<SortedList<Arc<T,V>>> list = new ArrayList<>();
            outArcsMap.put(arc.getTarget(), list);

            for (Comparator<Arc<T,V>> cmp: comparators) {
                list.add(new SortedList<>(cmp));
            }
        }
        List<SortedList<Arc<T,V>>> nodeArcs = outArcsMap.get(arc.getTarget());

        for (SortedList<Arc<T,V>> set: nodeArcs) {
            set.add(arc);

        }
    }

    public void removeOutArc(Arc<T,V> arc) {
        outArcsList.remove(arc);
        if(outArcsMap.containsKey(arc.getTarget())) {
            List<SortedList<Arc<T,V>>> nodeArcs = outArcsMap.get(arc.getTarget());
            for (SortedList<Arc<T,V>> set: nodeArcs) {
                set.remove(arc);
            }
            if(outArcsMap.get(arc.getTarget()).get(0).isEmpty()) {
                outArcsMap.remove(arc.getTarget());
            }
        }
    }

    public SortedList<Arc<T,V>> getList(Node<T,V> node, Comparator<Arc<T,V>> cmp) {
        return outArcsMap.get(node).get(comparators.indexOf(cmp));
    }

    public void clearArcs() {
        inArcs.clear();
        outArcsMap.clear();
    }

    public List<Node<T,V>> getInElements() {
        List<Node<T,V>> elements = new ArrayList<>();
        for (Arc<T,V> arc: inArcs) {
            elements.add(arc.getOrigin());
        }
        return elements;
    }
    public Set<Node<T,V>> getAdjacents(){
        return outArcsMap.keySet();
    }

    public void deleteArcsTo(Node<T,V> node) {
        outArcsList.removeIf(tvArc -> tvArc.getTarget().equals(node));
        outArcsMap.remove(node);
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null) return  false;
        if(obj == this) return  true;
        if(!getClass().equals(obj.getClass())) return false;
        Node node = (Node) obj;
        return this.element.equals(node.getElement());
    }

    @Override
    public int hashCode(){
        return this.element.hashCode();
    }

    @Override
    public String toString(){
        return this.element.toString();
    }

}
