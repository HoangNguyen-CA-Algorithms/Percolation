/* *****************************************************************************
 *  Name:              Hoang Nguyen
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    final private WeightedQuickUnionUF UF;
    final private int START_SITE;
    final private int END_SITE;
    final private int N;

    private boolean[] openSites; // keeps track of which sites are open.
    private int openCount;


    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n){
        if (n <= 0){
            throw new IllegalArgumentException();
        }

        this.START_SITE = n*n;
        this.END_SITE = n*n+1;
        this.N = n;

        this.UF = new WeightedQuickUnionUF(n*n + 2); // last 2 are virtual sites.
        this.openSites = new boolean[n*n];


        //connect all starting sites
        for (int i = 0; i < n; i++){
            UF.union(START_SITE, i);
        }

        //connect all ending sites
        for (int i = n*(n-1); i < n*n; i++){
            UF.union(END_SITE, i);
        }
    }

    private int getNode(int row, int col){
        if (row < 1 ||  row > this.N) throw new IllegalArgumentException();
        if (col < 1 ||  col > this.N) throw new IllegalArgumentException();


        return (row-1) * N + (col-1);
    }

    private int getRightNode(int row, int col){
        if (col +1 > this.N){
            return -1;
        }else{
            return getNode(row, col+1);
        }
    }

    private int getLeftNode(int row, int col){
        if (col -1 < 1){
            return -1;
        }else{
            return getNode(row, col-1);
        }
    }

    private int getTopNode(int row, int col){
        if (row -1 < 1){
            return -1;
        }else{
            return getNode(row-1, col);
        }
    }

    private int getBottomNode(int row, int col){
        if (row +1 > this.N){
            return -1;
        }else{
            return getNode(row+1, col);
        }
    }

    private boolean isOpen(int node){
        return openSites[node];
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col){
        if (isOpen(row, col)){
            return;
        }

        int node = getNode(row, col);
        int topNode = getTopNode(row,col);
        int rightNode = getRightNode(row,col);
        int bottomNode = getBottomNode(row,col);
        int leftNode = getLeftNode(row,col);

        if (topNode >=0){
            if (isOpen(topNode)){
                UF.union(node, topNode);
            }
        }if (rightNode>= 0){
            if (isOpen(rightNode)){
                UF.union(node, rightNode);
            }
        }
        if (bottomNode >= 0){
            if (isOpen(bottomNode)){
                UF.union(node, bottomNode);
            }
        }if (leftNode >= 0){
            if (isOpen(leftNode)){
                UF.union(node, leftNode);
            }
        }

        this.openSites[node] = true;
        this.openCount++;
    }



    // is the site (row, col) open?
    public boolean isOpen(int row, int col){
        return openSites[this.getNode(row, col)];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col){
        return UF.find(this.START_SITE) == UF.find(this.getNode(row, col)) && isOpen(row, col);
    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return this.openCount;
    }

    // does the system percolate?
    public boolean percolates(){
        return UF.find(this.START_SITE) == UF.find(this.END_SITE);
    }

}
