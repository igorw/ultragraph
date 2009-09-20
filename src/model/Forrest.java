package model;

import java.util.HashSet;

//a forrest contains a list of separate (disconnected) trees
public class Forrest {
	private HashSet<Tree> trees = new HashSet<Tree>();
	
	public void add(Tree t) {
		trees.add(t);
	}
	
	public void remove(Tree t) {
		trees.remove(t);
	}
	
	// merge two trees
	public void merge(Tree t1, Tree t2) {
		for (Edge e : t1.getEdges()) {
			t2.add(e);
		}
		
		// remove t1
		remove(t1);
		
		// update reference
		t1 = t2;
	}
	
	public int size() {
		return trees.size();
	}
	
	public HashSet<Tree> getTrees() {
		return trees;
	}
	
	// assume we have only one tree left
	// then we have a spanning tree
	public Tree getSpanningTree() {
		for (Tree t : trees) {
			return t;
		}
		return null;
	}
	
	// add edge to forrest
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
	
	// contains edge
	public boolean contains(Edge e) {
		for (Tree t : trees) {
			if (t.contains(e)) return true;
		}
		return false;
	}
	
	// does edge connect to tree
	public boolean connectsBoth(Edge e) {
		for (Tree t : trees) {
			if (t.connectsBoth(e)) {
				return true;
			}
		}
		return false;
	}
	
	// count all edges
	public int countEdges() {
		int count = 0;
		for (Tree t : trees) {
			count += t.size();
		}
		return count;
	}
}
