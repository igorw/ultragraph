package model;

import java.util.HashSet;

/**
 * forest
 * a forest contains a list of separate (disconnected) trees
 */
public class Forest {
	/**
	 * unique list of trees
	 */
	private HashSet<Tree> trees = new HashSet<Tree>();
	
	/**
	 * add a tree to the forest
	 * 
	 * @param t tree
	 */
	public void add(Tree t) {
		trees.add(t);
	}
	
	/**
	 * remove a tree from the forest
	 * 
	 * @param t tree
	 */
	public void remove(Tree t) {
		trees.remove(t);
	}
	
	/**
	 * merge two trees into one
	 * 
	 * @param t1 first tree
	 * @param t2 second tree
	 */
	public void merge(Tree t1, Tree t2) {
		for (Edge e : t1.getEdges()) {
			t2.add(e);
		}
		
		// remove t1
		remove(t1);
		
		// update reference
		t1 = t2;
	}
	
	/**
	 * amount of trees in forest
	 * 
	 * @return amount of trees
	 */
	public int size() {
		return trees.size();
	}
	
	/**
	 * getter for trees
	 * 
	 * @return hashset of trees
	 */
	public HashSet<Tree> getTrees() {
		return trees;
	}
	
	/**
	 * assume we have only one tree left
	 * then we have a spanning tree
	 */
	public Tree getSpanningTree() {
		for (Tree t : trees) {
			return t;
		}
		return null;
	}
	
	/**
	 * add edge to forest
	 * 
	 * @param e edge to be added
	 */
	public void add(Edge e) {
		Tree tree1 = null;
		Tree tree2 = null;
		for (Tree t : trees) {
			if (t.connects(e)) {
				tree1 = t;
				break;
			}
		}
		if (tree1 != null) {
			for (Tree t : trees) {
				if (t != tree1 && t.connects(e)) {
					tree2 = t;
					break;
				}
			}
		}
		
		if (tree1 != null && tree2 != null) {
			// if edge connects to two trees, merge
			tree1.add(e);
			tree2.add(e);
			merge(tree1, tree2);
		} else if (tree1 != null) {
			// connects to one tree, add
			tree1.add(e);
		} else {
			// new tree, create
			Tree t = new Tree();
			t.add(e);
			add(t);
		}
	}
	
	/**
	 * contains edge
	 * 
	 * @param e edge
	 * @return edge is present
	 */
	public boolean contains(Edge e) {
		for (Tree t : trees) {
			if (t.contains(e)) return true;
		}
		return false;
	}
	
	/**
	 * does edge connect to tree
	 * 
	 * @param e edge
	 * @return edge connects
	 */
	public boolean connectsBoth(Edge e) {
		for (Tree t : trees) {
			if (t.connectsBoth(e)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * count all edges
	 * 
	 * @return edge count
	 */
	public int countEdges() {
		int count = 0;
		for (Tree t : trees) {
			count += t.size();
		}
		return count;
	}
	
	/**
	 * remove all trees
	 */
	public void clear() {
		trees.clear();
	}
}
