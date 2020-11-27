package model;

import java.util.ArrayList;

public class N_AryanTree<T,E extends Comparable<E>>{
	private Node<?, ?>[] tree;
	public N_AryanTree(int n) {
		tree =new Node<?,?>[n+1];
	}
	public void insert(T node1, T node2, E element1, E element2) {
		//Node<T,E> newNode = new Node<T,E>(parent,key,element);
		if(node2==null) {
			tree[1] = new Node<T,E>(1,node1,element1);
		}else {
			int pos1 = node1.hashCode();
			int pos2 = node2.hashCode();
			if(tree[pos1]!=null && tree[pos2]!=null) {
				tree[pos1].add(pos2);
				tree[pos2].add(pos1);
				if(tree[pos1].haveParent()) {
					tree[pos2].setParent(pos1);
					updateParent(pos2);
				}else if(tree[pos2].haveParent()) {
					tree[pos1].setParent(pos2);
					updateParent(pos1);
				}
				
			}else if(tree[pos1]!=null) {
				Node<T,E> newNode2 = new Node<T,E>(null,node2,element2);
				tree[pos1].add(pos2);
				tree[pos2] = newNode2;
				tree[pos2].add(pos1);
				if(tree[pos1].haveParent()) {
					tree[pos2].setParent(pos1);
				}
			}else if(tree[pos2]!=null) {
				Node<T,E> newNode1 = new Node<T,E>(null,node1,element1);
				tree[pos2].add(pos1);
				tree[pos1] = newNode1;
				tree[pos1].add(pos2);
				if(tree[pos2].haveParent()) {
					tree[pos1].setParent(pos2);
				}
			}else {
				Node<T,E> newNode2 = new Node<T,E>(null,node2,element2);
				Node<T,E> newNode1 = new Node<T,E>(null,node1,element1);
				newNode1.add(pos2);
				newNode2.add(pos1);
				tree[pos1] = newNode1;
				tree[pos2] = newNode2;
			}
		}
	}
	private void updateParent(int pos){
		ArrayList<Integer> edges = tree[pos].getEdges();
		for (int i = 0; i < edges.size(); i++) {
			if(!edges.get(i).equals(tree[pos].getParent())){
				if(tree[edges.get(i)].haveParent()) {
					break;
				}else {
					tree[edges.get(i)].setParent(pos);
					updateParent(edges.get(i));
				}
			}
		}
	}

	public int countNodes(Integer position, E element) {
		int result =0;
		if(tree[position].getElement().equals(element)) {
			result++;
		}
		ArrayList<Integer> edges = tree[position].getEdges();
		for (int i = 0; i < edges.size(); i++) {
			if(!edges.get(i).equals(tree[position].getParent())) {
				result += countNodes(edges.get(i),element);
			}
		}
		return result;
	}
	
	
}
