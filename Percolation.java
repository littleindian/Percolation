

/**
 * Write a description of class Percolation here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

/*
 *  // create N-by-N grid, with all sites blocked
 *  public Percolation(int N)
 * 
 *  // open site (row i, column j) if it is not already
 *  public void open(int i, int j)
 * 
 * // is site (row i, column j) open?
 *  public boolean isOpen(int i, int j)
 * 
 *   // is site (row i, column j) full
 *  public boolean isFull(int i, int j) 
 *  
 *  // does the system percolate?
 *  public boolean percolates()
 */

public class Percolation
{
  /**
     * Constructor for objects of class Percolation
     */
    
  private WeightedQuickUnionUF uf;
  private int[] sites;
  private int gridSize;
  
  private static final int BLOCKED = 0;
  private static final int OPEN = 1;
    
  private void scanNeighbours(int i) {
      int left = (i - 1)%gridSize; // should not be 0
      int up = (i - gridSize); // should be > 0
      int right = (i + 1) % gridSize; // should not be 1
      int down = (i + gridSize); // should not be > gridSize
      if((left != 0) && (sites[i-1] == OPEN)) uf.union(i, i-1);
      if((up > 0) && (sites[up] == OPEN)) uf.union(i, up);
      if((right != 1) && (sites[i+1] == OPEN)) uf.union(i, i+1);
      if((down <= gridSize * gridSize) && (sites[down] == OPEN)) uf.union(i, down);
  }
  
  public Percolation(int N) throws IllegalArgumentException {
      if(N < 1) throw new IllegalArgumentException();
      gridSize = N;
      uf = new WeightedQuickUnionUF(N*N + 2);
      sites = new int[N*N + 1];
      for (int i = 1; i < sites.length; i++) {
          sites[i] = BLOCKED;
      }          
  }
    
  public void open(int i, int j) throws IndexOutOfBoundsException {
      int index = gridSize * (i-1) + j;
      int n = gridSize * gridSize;
      if((i > gridSize) || (i < 1) || (j > gridSize) || (j<1)) throw new IndexOutOfBoundsException(); // TODO: do something, don't just return
      sites[index] = OPEN;
      if(i == 1) uf.union(0, index);
      if(i == gridSize) uf.union(n+1, index);
      scanNeighbours(index);
  }
  
  public boolean isOpen(int i, int j) throws IndexOutOfBoundsException {
      if((i > gridSize) || (i < 1) || (j > gridSize) || (j<1)) throw new IndexOutOfBoundsException();
      return (sites[(i-1)*gridSize+j] == OPEN);
  }
  
  public boolean isFull(int i, int j) throws IndexOutOfBoundsException {
      if((i > gridSize) || (i < 1) || (j > gridSize) || (j<1)) throw new IndexOutOfBoundsException();
      return (isOpen(i, j) && uf.connected(0, gridSize * (i - 1) + j));
  }
  
  public boolean percolates() {
      return uf.connected(0, gridSize*gridSize + 1);
  } 
}
