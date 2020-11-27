package model;

import java.util.ArrayList;

public class Node <T,E extends Comparable<E>>{
	
	private E element;
	private Integer parent;
	private T key;
	private ArrayList<Integer> edges;
	
	public Node(Integer parent,T key,E element) {
		this.setParent(parent);
		this.element = element;
		edges = new ArrayList<Integer>();
	}
	public boolean haveParent() {
		return parent!=null;
	}
	public void add(Integer edge) {
		edges.add(edge);
	}
	public E getElement() {
		return element;
	}

	public Integer getParent() {
		return parent;
	}
	public ArrayList<Integer> getEdges() {
		return edges;
	}
	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public T getKey() {
		return key;
	}

}
