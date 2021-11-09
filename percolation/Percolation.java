package edu.princeton.cs.algs4.percolation;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int width;
    private boolean[][] grid;
    private int sizeOfGrid;
    private int topSite = 0;
    private int bottomSite;
    private WeightedQuickUnionUF wquf;
    private int count;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();

        width = n;
        grid = new boolean[width][width];
        sizeOfGrid = width * width;
        bottomSite = sizeOfGrid + 1;
        wquf = new WeightedQuickUnionUF(sizeOfGrid + 2);
    }

    private int getIndexOfSite(int row, int col) {
        return width * (row - 1) + col;
    }


    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        count++;
        grid[row - 1][col - 1] = true;

        if (row == 1) {
            wquf.union(getIndexOfSite(row, col), topSite);
        }

        if (row == width) {
            wquf.union(getIndexOfSite(row, col), bottomSite);
        }


        if (row > 1 && isOpen(row - 1, col)) {
            wquf.union(getIndexOfSite(row, col), getIndexOfSite(row - 1, col));
        }
        if (row < width && isOpen(row + 1, col)) {
            wquf.union(getIndexOfSite(row, col), getIndexOfSite(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            wquf.union(getIndexOfSite(row, col), getIndexOfSite(row, col - 1));
        }
        if (col < 1 && isOpen(row, col + 2)) {
            wquf.union(getIndexOfSite(row, col), getIndexOfSite(row, col + 1));
        }

    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > width || col <= 0 || col > width) {
            throw new IndexOutOfBoundsException();
        }
        return (wquf.find(getIndexOfSite(row, col)) == wquf.find(topSite));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.find(topSite) == wquf.find(bottomSite);

    }

    private void writeGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {

//                StdOut.print(j);
                if (grid[i][j] == true) {
                    StdOut.print("*");
                } else {
                    {
                        StdOut.print('/');
                    }
                }
            }
            StdOut.println();
        }

    }

    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(4);
        percolation.writeGrid();
        percolation.open(1, 1);
        percolation.writeGrid();
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.numberOfOpenSites());
        percolation.open(1, 2);
        percolation.open(1, 3);
        percolation.open(2, 3);
        percolation.open(3, 3);
        percolation.open(4, 3);
        percolation.writeGrid();
        StdOut.println(percolation.percolates());
        StdOut.println(percolation.numberOfOpenSites());
    }
}
