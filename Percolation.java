import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {


   private int n;
   private boolean[][] grid;
   private WeightedQuickUnionUF uf;
   private WeightedQuickUnionUF full;
   private int[][] direction = {{1, 0}, {0, 1}, {0, -1}, {-1, 0}};
   public Percolation(int N)               // create N-by-N grid, with all sites blocked
   {
	   n = N;
	   if (N <=  0) {
		    throw new IllegalArgumentException();
	   }
       grid = new boolean[n][n];
       uf = new WeightedQuickUnionUF(N*N+2);
       full = new WeightedQuickUnionUF(N*N+1);
       for (int i = 0; i < n; i++)
           for (int j = 0; j < n; j++)
           grid[i][j] = false;
   }
   
   public void open(int i, int j)          // open site (row i, column j) if it is not open already
   {
	   if (isOutOfBounds(i-1, j-1)) {
		    throw new IndexOutOfBoundsException();
	   }
       if (i == 1) {
    	   uf.union(0, calculateUFid(i, j));
    	   full.union(0, calculateUFid(i, j));
    	   grid[i-1][j-1] = true;
    	   for(int k = 0; k < 3; k++) {
    		   
    		   if ( !isOutOfBounds(i-1+direction[k][0], j-1+direction[k][1]) ) {
    			   if (grid[i-1+direction[k][0]][j-1+direction[k][1]]) {
    				   uf.union(calculateUFid(i, j), calculateUFid(i+direction[k][0], j+direction[k][1]));
    			       full.union(calculateUFid(i, j), calculateUFid(i+direction[k][0], j+direction[k][1]));
    			   }
    		   }
    	   }
       }
       if (i  ==  n) {
    	   uf.union(n*n+1, calculateUFid(i, j));
    	   grid[i-1][j-1] = true;
    	   for(int k = 1; k < 4; k++) {
    		   
    		   if ( !isOutOfBounds(i-1+direction[k][0], j-1+direction[k][1]) ) {
    			   if (grid[i-1+direction[k][0]][j-1+direction[k][1]]) {
    				   uf.union(calculateUFid(i, j), calculateUFid(i+direction[k][0], j+direction[k][1]));
				       full.union(calculateUFid(i, j), calculateUFid(i+direction[k][0], j+direction[k][1]));
    			   }
    		   }
    	   }
       }
       else {
    	   grid[i-1][j-1] = true;
    	   for(int k = 0; k < 4; k++) {
    		   
    		   if ( !isOutOfBounds(i-1+direction[k][0], j-1+direction[k][1]) ) {
    			   if (grid[i-1+direction[k][0]][j-1+direction[k][1]]) {
    				   uf.union(calculateUFid(i, j), calculateUFid(i+direction[k][0], j+direction[k][1]));
				       full.union(calculateUFid(i, j), calculateUFid(i+direction[k][0], j+direction[k][1]));
    			   }
    		   }
    	   }
       }
       

   }
   
   public boolean isOpen(int i, int j)     // is site (row i, column j) open?
   {
	   if (isOutOfBounds(i-1, j-1)) {
		    throw new IndexOutOfBoundsException();
	   }
	   return grid[i-1][j-1];
   }
   
   public boolean isFull(int i, int j)     // is site (row i, column j) full?
   {  
	   if (isOutOfBounds(i-1, j-1)) {
		    throw new IndexOutOfBoundsException();
	   }
	   return (full.connected(0, calculateUFid(i, j)));
	   
   }
   
   public boolean percolates()             // does the system percolate?
   {   
	   return (uf.connected(0, n*n+1));
   }
   
   private boolean isOutOfBounds(int i, int j)
   {
	   return (i < 0 || j < 0 || i > n-1 || j > n-1);
   }
   private int calculateUFid(int i, int j) {
	   return (i-1)*n+j;
   }

   public static void main(String[] args)   // test client (optional)
   {

   }
}
