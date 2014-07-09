/**
 * Write a description of class PercolationStats here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

/*
 * public class PercolationStats {
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   public double mean()                     // sample mean of percolation threshold
   public double stddev()                   // sample standard deviation of percolation threshold
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   public static void main(String[] args)   // test client, described below
}
 */
public class PercolationStats
{
    private double mean;
    private double sigma;
    private double sqrtT;
    private static final String percent = "%";
    /**
     * Constructor for objects of class PercolationStats
     */
    public PercolationStats(int N, int T) throws java.lang.IllegalArgumentException {
      if((N < 1) || (T < 1))
        throw new IllegalArgumentException();
      Percolation p;
      double[] samples = new double[T];
      int sum = 0;
      sqrtT = Math.sqrt(T);
      for(int trials = 0; trials < T; trials++) {
        int openSites = 0;
        p = new Percolation(N);
        while(!p.percolates()) {
          int row = StdRandom.uniform(N)+1; 
          int col = StdRandom.uniform(N)+1;
          
          while(p.isOpen(row, col)) {
              row = StdRandom.uniform(N)+1;
              col = StdRandom.uniform(N)+1;
          }
          p.open(row, col);
          openSites++;
        }
        sum += openSites;
        samples[trials] = (double) openSites/(N*N);
      }
      mean = (double)sum/(T*N*N);
      double total = 0.0;
      for(int i = 0; i < T; i++) {
        double d = samples[i] - mean;
        total += d*d;
      }
      if(T == 1) {
          sigma = Double.NaN;
      } else {
          sigma = Math.sqrt((double)total/(T-1));
      }
    }
    
    public double mean() {
      return mean;
    }

    public double stddev() {
      return sigma;
    }
    
    public double confidenceLo() {
      return (mean - (1.96 * sigma)/sqrtT);
    }
    
    public double confidenceHi() {
      return (mean + (1.96 * sigma)/sqrtT);
    }
    
    public static void main(String args[]) {
      int N = Integer.parseInt(args[0]);
      int T = Integer.parseInt(args[1]);
      PercolationStats pStat = new PercolationStats(N, T);
      
      System.out.printf("mean                    = %6f%n", pStat.mean());
      System.out.printf("stddev                  = %6f%n", pStat.stddev());
      System.out.printf("95%s confidence interval = %6f, %f%n",percent, pStat.confidenceLo(), pStat.confidenceHi());                   
    }
}
