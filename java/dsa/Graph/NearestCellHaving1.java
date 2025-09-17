public class NearestCellHaving1 {
    class Cell {
        int row;
        int col;
        int distance;
    
        Cell(int row, int col, int distance) {
            this.row = row;
            this.col = col;
            this.distance = distance;
        }
    }
    
    int[][] bfsOnGrid(int[][] grid) {
        int numRows = grid.length;
        int numCols = grid[0].length;
        int[][] distances = new int[numRows][numCols];
        boolean[][] visited = new boolean[numRows][numCols];
    
        Deque<Cell> queue = new ArrayDeque<>();
    
        // Initialize queue with all cells that have value 1
        for (int r = 0; r < numRows; r++) {
            for (int c = 0; c < numCols; c++) {
                if (grid[r][c] == 1) {
                    visited[r][c] = true;
                    queue.offer(new Cell(r, c, 0));
                }
            }
        }
    
        // Direction vectors for up, right, down, left
        int[] dRow = {-1, 0, 1, 0};
        int[] dCol = {0, 1, 0, -1};
    
        while (!queue.isEmpty()) {
            Cell current = queue.poll();
            distances[current.row][current.col] = current.distance;
    
            // Explore neighbors
            for (int i = 0; i < 4; i++) {
                int newRow = current.row + dRow[i];
                int newCol = current.col + dCol[i];
    
                if (newRow >= 0 && newRow < numRows && newCol >= 0 && newCol < numCols
                    && !visited[newRow][newCol]) {
    
                    visited[newRow][newCol] = true;
                    queue.offer(new Cell(newRow, newCol, current.distance + 1));
                }
            }
        }
    
        return distances;
    }
    
}
