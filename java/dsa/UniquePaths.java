package com.github.dsa;

class UniquePaths {

    public int uniquePaths(int totalRows, int totalCols) {

        // Initialize a 1D DP array for the current row
        int[] previousRow = new int[totalCols];

        for (int row = 0; row < totalRows; row++) {
            int[] currentRow = new int[totalCols];
            currentRow[0] = 1; // First column always has 1 path (only from top)

            for (int col = 1; col < totalCols; col++) {
                int pathsFromAbove = previousRow[col];
                int pathsFromLeft = currentRow[col - 1];
                currentRow[col] = pathsFromAbove + pathsFromLeft;
            }

            // Move to the next row
            previousRow = currentRow;
        }

        return previousRow[totalCols - 1];
    }

    // Combinatorics solution
    public int uniquePaths2(int m, int n) {
        int N = m + n - 2; // Total steps
        int r = m - 1; // Choose r down moves
        double res = 1;

        for (int i = 1; i <= r; i++) {
            res = res * (N - r + i) / i;
        }

        return (int) res;
    }
}
