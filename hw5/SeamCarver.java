import edu.princeton.cs.algs4.Picture;

import java.awt.Color;

public class SeamCarver {
    private Picture picture;
    private boolean horizontal = false;
    
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }
    
    // current picture
    public Picture picture() {
        return new Picture(picture);
    }
    
    // width of current picture
    public int width() {
        return picture.width();
    }
    
    // height of current picture
    public int height() {
        return picture.height();
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if (x < 0 || x > width() - 1 || y < 0 || y > height() - 1) {
            throw new java.lang.IndexOutOfBoundsException("index is outside its prescribed range");
        }
        //horizontal
        Color c1 = null;
        Color c2 = null;
        if (x - 1 < 0) {
            c1 = picture.get(width() - 1, y);
        } else {
            c1 = picture.get(x - 1, y);
        }
        if (x + 1 > width() - 1) {
            c2 = picture.get(0, y);
        } else {
            c2 = picture.get(x + 1, y);
        }
        double dualX = getDualGradient(c1, c2);
        //vertical
        if (y - 1 < 0) {
            c1 = picture.get(x, height() - 1);
        } else {
            c1 = picture.get(x, y - 1);
        }
        if (y + 1 > height() - 1) {
            c2 = picture.get(x, 0);
        } else {
            c2 = picture.get(x, y + 1);
        }
        
        double dualY = getDualGradient(c1, c2);
        return dualX + dualY;
    }
    
    private double getDualGradient(Color c1, Color c2) {
        double dualG = Math.pow(c1.getRed() - c2.getRed(), 2)
                + Math.pow(c1.getBlue() - c2.getBlue(), 2)
                + Math.pow(c1.getGreen() - c2.getGreen(), 2);
        return dualG;
    }
    
    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        horizontal = true;
        return findVerticalSeam();
    }
    
    private double[][] getEnergyTable() {
        int x = width();
        int y = height();
        if (horizontal) {
            x = height();
            y = width();
        }
        double[][] energyTable = new double[y][x];
        for (int i = 0; i < energyTable.length; i += 1) {
            for (int j = 0; j < energyTable[0].length; j += 1) {
                if (horizontal) {
                    energyTable[i][j] = energy(i, j);
                } else {
                    energyTable[i][j] = energy(j, i);
                }
            }
        }
        return energyTable;
    }
    
    private int getSmallestIndex(double[] mi) {
        double min = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < mi.length; i += 1) {
            if (mi[i] < min) {
                min = mi[i];
                minIndex = i;
            }
        }
        return minIndex;
    }
    
    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        double[][] energyTable = getEnergyTable();
        int col = energyTable[0].length;
        int row = energyTable.length;
        int[] res = new int[row];
        double[][] m = new double[row][col];
        int[][] route = new int[row - 1][col];
        for (int i = 0; i < col; i += 1) {
            m[0][i] = energyTable[0][i];
        }
        if (row == 1) {
            res[0] = getSmallestIndex(m[0]);
            return res;
        }
        for (int i = 1; i < row; i += 1) {
            for (int j = 0; j < col; j += 1) {
                double left = Double.MAX_VALUE;
                double middle = m[i - 1][j];
                double right = Double.MAX_VALUE;
                if (j > 0) {
                    left = m[i - 1][j - 1];
                }
                if (j < col - 1) {
                    right = m[i - 1][j + 1];
                }
                double min;
                if (left < middle) {
                    if (left < right) {
                        min = left;
                        route[i - 1][j] = j - 1;
                    } else {
                        min = right;
                        route[i - 1][j] = j + 1;
                    }
                } else if (middle < right) {
                    min = middle;
                    route[i - 1][j] = j;
                } else {
                    min = right;
                    route[i - 1][j] = j + 1;
                }
                m[i][j] = min + energyTable[i][j];
            }
        }
        int index = getSmallestIndex(m[m.length - 1]);
        for (int i = row - 1; i >= 0; i -= 1) {
            res[i] = index;
            if (i > 0) {
                index = route[i - 1][index];
            }
        }
        return res;
    }
    
    // remove horizontal seam from picture
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(picture, seam);
    }
    
    // remove vertical seam from picture
    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(picture, seam);
    }
}
