/* *****************************************************************************
 *  Name:              Hoang Nguyen
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    final private double[] thresholdData;

    final private int trials;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials){
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException();

        thresholdData = new double[trials];
        this.trials = trials;

        for (int i = 0; i < trials; i++){
            Percolation percolation = new Percolation(n);
            while(!percolation.percolates()){
                int row = StdRandom.uniform(1, n+1);
                int col = StdRandom.uniform(1, n+1);


                if (!percolation.isOpen(row,col)){
                    percolation.open(row,col);
                }
            }

            double threshold = percolation.numberOfOpenSites()/(double)(n*n);
            thresholdData[i] = threshold;
        }




    }

    // sample mean of percolation threshold
    public double mean(){
        return StdStats.mean(this.thresholdData);
    }

    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(this.thresholdData);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo(){
        return mean() - 1.96*stddev()/Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi(){
        return mean() + 1.96*stddev()/Math.sqrt(trials);
    }

    // test client (see below)
    public static void main(String[] args){
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, T);
        System.out.println(stats.mean());
        System.out.println(stats.stddev());
        System.out.println("[" + stats.confidenceLo() +", "+ stats.confidenceHi() +"]");
    }
}
