
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.Stack;

/*
 * Copyright (C) 2016 Michael <GrubenM@GMail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/**
 * This mutable data type represents a set of points in the "Unit Square".
 * 
 * Its representation depends on a Red-Black (balanced) Binary Search Tree,
 * hence O(log n) is expected for Searching, Inserting, and Deleting.
 * 
 * This particular implementation uses algs4.SET, although notably the same
 * implementation could be achieved through java.util.TreeSet.
 * 
 * More specifically, algs4.SET is a wrapper around java.util.TreeSet.
 * 
 * @author Michael <GrubenM@GMail.com>
 */
public class KdTree {
    private SET<Node> rb;
    
    /**
     * Construct an empty set of points.
     */
    public KdTree() {
       rb = new SET<>();
    }
    
    /**
     * Is the set empty?
     * @return {@code true} if this SET is empty;
     *         {@code false} otherwise
     */
    public boolean isEmpty() {
        return rb.isEmpty();
    }
    
    /**
     * @return the number of points in the set.
     */
    public int size() {
        return rb.size();
    }
    
    /**
     * Add the point to the set (if it is not already in the set).
     * 
     * At the root (and every second level thereafter), the x-coordinate is
     * used as the key.
     * 
     * This means that if (0.7, 0.2) is the root, then (0.5, 0.9) will be
     * added to the left, since its x-coordinate is smaller than the
     * x-coordinate of the root node.  Similarly, if the next point to be
     * added is (0.8, 0.1), that point will be added to the right of root,
     * since its x-coordinate is larger than the x-coordinate of the root node.
     * 
     * So, visually, we would have:
     *       (0.7, 0.2)
     *      /          \
     * (0.5, 0.9)   (0.8, 0.1)
     * 
     * 
     * At one level below the root (and every second level thereafter), the
     * y-coordinate is used as the key.
     * 
     * This means that if we next add (0.6, 0.8), it will be added to the left
     * of (0.5, 0.9).  Similarly, if we next add (0.4, 0.95), it will be added
     * to the right of (0.5, 0.9).
     * 
     * So, visually, we would have:
     *              (0.7, 0.2)
     *             /          \
     *        (0.5, 0.9)   (0.8, 0.1)
     *       /          \
     * (0.6, 0.8)   (0.4, 0.95)
     * 
     * 
     * Note that no check for presence is made here before attempting to add,
     * since the documentation of algs4.SET explicitly states:
     * "Adds the key to this set (if it is not already present)"
     * 
     * Note however that this implementation explicitly checks for a null
     * argument,even though algs4.SET also performs this check.  This is
     * because the assignment API requires this check to be made.
     * 
     * In the worst case, this implementation takes time proportional to the
     * logarithm of the number of points in the set.
     * This is because, in the worst case, algs4.SET.add() takes logarithmic
     * time.
     * That is because, in the worst case, java.util.TreeSet.add() takes
     * logarithmic time.
     * 
     * @param p the point to add
     * @throws NullPointerException if {@code p} is {@code null}
     */
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException
            ("called insert() with a null Point2D");
        root = insert(root, p, 0);
    }
    
    /**
     * Does the set contain point p?
     * 
     * In the worst case, this implementation takes time proportional to the
     * logarithm of the number of points in the set.
     * This is because, in the worst case, algs4.SET.contains() takes
     * logarithmic time.
     * That is because, in the worst case, java.util.TreeSet.contains() takes
     * logarithmic time.
     * 
     * @param p the point to look for
     * @return {@code true} if the SET contains point p;
     *         {@code false} otherwise
     * @throws NullPointerException if {@code p} is {@code null}
     */
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException
            ("called contains() with a null Point2D");
        return rb.contains(new Node(p));
    }
    
    /**
     * Draw all points to standard draw.
     */
    public void draw() {
        for (Node n: rb) n.p.draw();
    }
    
    /**
     * All points that are inside the rectangle.
     * 
     * In the worst case, this implementation takes time
     * proportional to the number of points in the set.
     * 
     * However, unlike PointSET.range(), in the best case, this implementation
     * takes time proportional to the logarithm of the number of
     * points in the set.
     * 
     * @param rect the RectHV within which to look for points
     * @return an iterator to all of the points within the given RectHV
     * @throws NullPointerException if {@code rect} is {@code null}
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.NullPointerException
            ("called range() with a null RectHV");

    }
    
    /**
     * A nearest neighbor in the set to point p; null if the set is empty.
     * 
     * In the worst case, this implementation takes time
     * proportional to the number of points in the set.
     * 
     * However, unlike PointSET.nearest(), in the best case, this
     * implementation takes time proportional to the logarithm of
     * the number of points in the set.
     * 
     * @param p the point from which to search for a neighbor
     * @return the nearest neighbor to the given point p,
     *         {@code null} otherwise.
     * @throws NullPointerException if {@code p} is {@code null}
     */
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.NullPointerException
            ("called contains() with a null Point2D");
        
        if (rb.isEmpty()) return null;
    }
    
    private static class Node implements Comparable<Node> {
        
        // the point
        private Point2D p;
        
        // the axis-aligned rectangle corresponding to this node
        private RectHV rect;
        
        // the left/bottom subtree
        private Node lb;
        
        // the right/top subtree
        private Node rt;
        
        private Node(Point2D p) {
            this.p = p;
        }
        
        
    }
    
    /**
     * Unit testing of the methods (optional).
     * @param args
     */
    public static void main(String[] args) {
        
    }
}
