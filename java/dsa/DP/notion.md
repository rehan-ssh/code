# Max sum of non adj elements in subsequence

You are given an array/list of 'N' integers. You are supposed to return the maximum sum of the subsequence with the constraint that no two elements are adjacent in the given array/list.

all subseq meaning pick or non pick

so we have to do recursion some how, use the 3 steps

1. define index 2. do stuff on index 3 return best 

```java
dp[n]

f(ind) - max sum you can get from 0 to ind
{
	if(ind == 0) return a[ind];
	if(ind<0) return 0;
	
	if(dp[i]!=-1) return dp[i]
	
2. you can do two things either take the elem in your subseq or not take

pick = a[ind] + f(ind-2)
not pick = 0 + f(ind-1)

return dp[ind] = max(pick, not pick)
```

```java
dp[n]
dp[0] = a[0]
dp[1] = max(a[0], a[1])

for(i = 2 to n-1)
	dp[i] = max(dp[i-1], dp[i-2]+a[i])
```

if negatives are involved

```java
change
dp[0] = Math.max(0, a[0)

```

# 2D DP:

ninja training

→ 10 50 11, 1 100 5

on two consec days we can’t take same task

→ so greedy fails, 50+5 = 55

→ 10+100 = 110 optimal

→ so, next try all possible ways i.e recursion

→ ind → days can be indexes because i hv to decide on certain day what to do.

→ try all possible stuff → select all the tasks and call the next day with the current called task number

→ take the max → take the max of three values

```java
recur(idx = n-1, lastTask){
	if(idx == -1){
		return 0;
	}
	int max = 0;
	for(int i = 0; i < 3; i++){
		if(lastTask!=i){
			max = Math.max(max, recur(idx-1, i) + input[idx][i])
		}
	}
	return max;
}
```

```java
// convert to dp
dp[day][taskNo] = new int[n][3]

recur(idx = n-1, lastTask){
	if(idx == -1){
		return 0;
	}
	if(lastTask!=-1 && dp[idx][lastTask]) return dp[idx][lastTask];
	
	int max = 0;
	for(int i = 0; i < 3; i++){
		if(lastTask!=i){
			max = Math.max(max, recur(idx-1, i) + input[idx][i])
		}
	}
	
	dp[idx][lastTask] = max
	return max;
}
```

```java
// bottom up
1. see in memo how much dp array we used
2. look for base case // because bottom up
3. for how many ind is the recursion func running, run a loop until then and write the condn in recursion
----
int[][] dp = new int[n][3];

// Base case: on day 0, pick any task
dp[0][0] = input[0][0];
dp[0][1] = input[0][1];
dp[0][2] = input[0][2];

for (int i = 1; i < n; i++) {
    for (int j = 0; j < 3; j++) {
        dp[i][j] = 0;
        for (int k = 0; k < 3; k++) {
            if (k != j) {
                dp[i][j] = Math.max(dp[i][j], dp[i - 1][k] + input[i][j]);
            }
        }
    }
}

return Math.max(dp[n-1][0], Math.max(dp[n-1][1], dp[n-1][2]));
```

// space optimize now we just need last rows values

TC→ O(n*3*3) 

SC→O(3)

---

## Unique Paths

https://leetcode.com/problems/unique-paths/

![image.png](attachment:267e69d4-7f01-4fe2-a23c-498e21062b63:image.png)

```java

recursive 
-> ind+base case
-> explore all
-> return what is asked for

-> IMP: for counting problems when you reach soln return 1, if you don't reach return 0

class Solution {
-> here we go from top ind backwards, to not send
more parameters
    int cntPaths(int currX, int currY){

        if(currX == 0 && currY==0){
            return 1;
        }
        if(currX<0 || currY<0){
            return 0;
        }

        int left = cntPaths(currX-1, currY);

        int up =cntPaths(currX, currY-1);

        return left+up;
    }

    public int uniquePaths(int m, int n) {
        return cntPaths(m-1, n-1);
    }
```

```java
dp

class Solution {
    int[][] memo;

    int count(int x, int y) {
        if (x == 0 && y == 0) return 1;
        if (x < 0 || y < 0) return 0;

        if (memo[x][y] != 0) return memo[x][y];

        int fromLeft = count(x - 1, y);
        int fromTop = count(x, y - 1);

        memo[x][y] = fromLeft + fromTop;
        return memo[x][y];
    }

    public int uniquePaths(int m, int n) {
        memo = new int[m][n];
        return count(m - 1, n - 1);
    }
}
```

```java
dp tabulation
1. declare similar array
2. base case
3. express all states in for loop, copy the recurrence

    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        
        for(int i = 0; i < m; i++)
	        dp[i][n-1] = 1;
	      for(int j = 0; j < n; j++)
		      dp[m-1][j] = 1;
        
        for(int i = m-2; i>=0; i--){
	        for(int j = n-2; j>=0; j--){
		        dp[i][j] = dp[i][j+1]+dp[i+1][j];
		        
	        }
	      }
       
        return dp[0][0];
    }
    
    
    or 
    
     public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        
        
        for(int i = 0; i < m; i++){
			    for(int j = 0; j < n; j++){
					     int up = 0; 
					     int left = 0; 
					     if(i>0) up = dp[i-1][j];
					     if(j>0) left = dp[i][j-1];
								dp[i][j] = up+left
					    }
					   }    
       
        return dp[m-1][n-1];
    }
```

now here SC is O(m*n) we can make it O(m+n) because we only need 2 rows

![image.png](attachment:f66c68d1-e842-451d-9826-29d3eba40c33:image.png)

```java
// we only need one row and temp var
     public int uniquePaths(int m, int n) {
   
        int[] dp = new int[n];
        
        for(int i = 0; i < m; i++){
	        int temp[] = new int[n];
	        temp[0] = 1;
          for(int j = 1; j < n; j++){
              int up = 0; 
              int left = 0; 
              up = dp[j];
              left = temp[j-1];
              temp[j] = up+left;
          }
					dp = temp;
				}    
       
        return dp[n-1];
    }
```

// this problem can also be done with combinatorics

→ total moves - m-1 + n-1 = m+n-2

→ in these moves we have to either select m-1 downs 

or n-1 rights 

![image.png](attachment:393a6e39-5a73-4912-b632-2c1085d85021:image.png)

```java
D D D R R R R  (for m=4, n=5 → 3 downs, 4 rights)
so its combinations because all D are same and we are finding places in m+n-2

int N = m + n - 2; // Total steps
int r = m - 1;     // Choose r down moves
double res = 1;

for (int i = 1; i <= r; i++) {
    res = res * (N - r + i) / i;
}
```

![image.png](attachment:8fb34718-21b7-43cb-b8ac-6f8b08d93548:image.png)

## Unique paths 2

with dead cells

```java
class Solution {

    int cntPaths(int currX, int currY){
				
				***if(i>=0 && j>=0 && a[i][j] == -1) 
					return 0;***
					
        if(currX == 0 && currY==0){
            return 1;
        }
        if(currX<0 || currY<0){
            return 0;
        }
        if(dp[i][j]!=0) return dp[i][j]

        int left = cntPaths(currX-1, currY);

        int up =cntPaths(currX, currY-1);

        return dp[i][j] = left+up;
    }

    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
         return cntPaths(m-1, n-1);
    }

```

```java
tabulation

     public int uniquePaths(int[][] obstacleGrid) {
     
	     int m = obstacleGrid.length;
	     int n = obstacleGrid[0].length;
   
        int[] dp = new int[n];
        
        for(int i = 0; i < m; i++){
	        int temp[] = new int[n];
	        temp[0] = 1;
          for(int j = 1; j < n; j++){
              int up = 0; 
              int left = 0; 
              if(obstacleGrid[i][j] == 1)
	            {
		            temp[j] = 0;
		            continue;
	            }
              up = dp[j];
              left = temp[j-1];
              temp[j] = up+left;
          }
					dp = temp;
				}    
       
        return dp[n-1];
    }
```

# Triangle [ we know start, end unknown]

- code
    
    ```java
    package com.github.dsa;
    
    import java.util.Arrays;
    import java.util.List;
    
    public class Triangle {
        class Solution {
    
            int recur(List<List<Integer>> triangle, int m, int n) {
                if (m == triangle.size() - 1) {
                    return triangle.get(m).get(n);
                }
    
                int down = recur(triangle, m + 1, n);
                int left = Integer.MAX_VALUE;
                if (n < triangle.get(m).size())
                    left = recur(triangle, m + 1, n + 1);
    
                return triangle.get(m).get(n) + Math.min(down, left);
            }
    
            public int minimumTotal(List<List<Integer>> triangle) {
                return recur(triangle, 0, 0);
            }
        }
    
        class SolutionRecurDp{
    
            int dp[][];
            int recur(List<List<Integer>> triangle, int m, int n) {
                if (m == triangle.size() - 1) {
                    return triangle.get(m).get(n);
                }
                if(dp[m][n]!=-1) return dp[m][n];
                int down = recur(triangle, m + 1, n);
                int left = Integer.MAX_VALUE;
                if (n < triangle.get(m).size())
                    left = recur(triangle, m + 1, n + 1);
    
                return dp[m][n] = triangle.get(m).get(n) + Math.min(down, left);
            }
    
            public int minimumTotal(List<List<Integer>> triangle) {
                int m = triangle.size();
                dp = new int[m][m];
                for(int[] row: dp){
                    Arrays.fill(row, -1);
                }
                return recur(triangle, 0, 0);
    
            }
        }
    
        class SolutionDP{
            public int minimumTotal(List<List<Integer>> triangle) {
                int m = triangle.size();
                int dp[][] = new int[m][m];
    
                dp[0][0] = triangle.get(0).get(0);
    
                for(int i = 1; i < m; i++){
                    for(int j = 0; j <= i; j++){
                        int top = Integer.MAX_VALUE;
                        int left = Integer.MAX_VALUE;
                        if(j!=i){
                            top = dp[i-1][j];
                        }
                        if(j!=0){
                            left = dp[i-1][j-1];
                        }
    
                        dp[i][j] = triangle.get(i).get(j) + Math.min(top, left);
                    }
                }
    
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < m; i++) {
                    min = Math.min(min, dp[m - 1][i]);
                }
                return min;
    
            }
        }
    
        class SolutionDPSpaceOpt{
            public int minimumTotal(List<List<Integer>> triangle) {
                int m = triangle.size();
                int dp[] = new int[m];
    
                dp[0] = triangle.get(0).get(0);
    
                for(int i = 1; i < m; i++){
                    int [] curr = new int[m];
                    for(int j = 0; j <= i; j++){
                        int top = Integer.MAX_VALUE;
                        int left = Integer.MAX_VALUE;
                        if(j!=i){
                            top = dp[j];
                        }
                        if(j!=0){
                            left = dp[j-1];
                        }
    
                        curr[j] = triangle.get(i).get(j) + Math.min(top, left);
                    }
                    dp = curr;
                }
    
                int min = Integer.MAX_VALUE;
                for (int i = 0; i < m; i++) {
                    min = Math.min(min, dp[i]);
                }
                return min;
    
            }
        }
    }
    
    ```
    

learnings:

→ here we had to go from 0 to m in recursion, just indexing change as we did not knew the end point

→ while converting to dp striver says if recursion you are doing from 0 → m then tabulation you should go from m→0, i did it the other way in above code 

but this is easier to reason for the base case and all stuff, so go in opp direction

![image.png](attachment:8ba64477-50b1-42a6-947f-feabecb52f17:image.png)

![image.png](attachment:a0c15626-0f33-408f-9544-9952a00c1f41:image.png)

we start filling from the end if we go from recur to dp solution its easier to follow

# Minimum path sum[variable start and variable end]

variable starting and variable ending

```java
-- put in the loop of startings or endings call the recursion and get the min out of all those recursions

recur(int i, int j, int[][] a){
	base case -> 
	if(i==0) return a[0][j];
	if(j<0 || j>=m) return Integer.MAX_VALUE;
	
	//explore paths
	int s = a[i][j] + f(i-1, j);
	int l = a[i][j] + f(i-1,j-1);
	int r = a[i][j] + f(i-1, j+1);
	
	return Stream.of(s,l,r).min(Integer::compareTo);
}

```

```java

```

- recur + memo
    
    ```java
    class Solution {
        int dp[][];
        int f(int i, int j, int[][] a){
            // base case -> 
    
            int n = a[0].length;
    
            if(j<0 || j>=n) return Integer.MAX_VALUE;
            if(i==0) return a[0][j];
            if(dp[i][j]!=-1) return dp[i][j];
            
            //explore paths
            int s = f(i-1, j, a);
            int l =  f(i-1,j-1, a);
            int r =  f(i-1, j+1, a);
    
            
            return  dp[i][j] = a[i][j] + Stream.of(s,l,r)
            .min(Integer::compareTo)
            .orElseThrow();
        }
    
        public int minFallingPathSum(int[][] matrix) {
            int min = Integer.MAX_VALUE;
            int n = matrix[0].length;
            int m = matrix.length;
            dp = new int[m][n];
    
            for(int row[]: dp){
                Arrays.fill(row, -1);
            }
    
            for(int i = 0; i < n; i++){
                min = Math.min(f(m-1, i, matrix), min);
            }
    
            return min;
        }
    }
    ```
    
- dp
    
    ```java
    
    int [][]dp = new int [m][n]
    //base case
    for(j = 0; j<n; j++) dp[0][j] = a[0][j];
    
    //observe i, j, convert to loops
    for(int i = 1; i < m; i++){
    	for(int j = 0; j < n; j++){
    		 
    		 int up = dp[i-1][j];
    		 int l = j>0? dp[i-1][j-1]: Integer.MAX_VALUE;
    		 int r = j<n-1? dp [i-1][j+1]:Integer.MAX_VALUE;
    		 dp[i][j] = a[i][j] + Math.min(up, l, r);
    	
    	}
    	
    	
    	int min = Integer.MAX_VALUE;
    	for(int i = 0; i < n; i++){
    		min = Math.min(min, dp[m-1][i]);
    	}
    
    }
    ```
    
- space optimized dp
    
    ```java
    class Solution {
    
        public int minFallingPathSum(int[][] a) {
            int min = Integer.MAX_VALUE;
            int n = a[0].length;
            int m = a.length;
            
            int []dp = new int [n];
            //base case
            for(int j = 0; j < n; j++) dp[j] = a[0][j];
    
            //observe i, j, convert to loops
            for(int i = 1; i < m; i++){
                int curr[] = new int[n];
                for(int j = 0; j < n; j++){
                    
                    int up = dp[j];
                    int l = j>0? dp[j-1]: Integer.MAX_VALUE;
                    int r = j<n-1? dp[j+1]:Integer.MAX_VALUE;
                    curr[j] = a[i][j] + Stream.of(up, l, r).min(Integer::compareTo).orElseThrow();
                
                }
                dp = curr;
            }
    	
            
            for(int i = 0; i < n; i++){
                min = Math.min(min, dp[i]);
            }
    
            return min;
    
        }
    }
    ```
    

# 3D dp ninja and his friends

→ 2 starting points fixed and variable end points

→ alice stars and bob starts, get the max sum path for both

→ common cells if any will be counted only once

```java
i1, j1 and i2, j2

starting pts 0,0 and 0,m-1
we can write single i, because they move simulatenously

i, j1 and i, j2 

f(0,0,m-1)
{ //2. define base cases 
 //i. out of bound base case [always write first]
	if(j1<0 || j1>=m || j2<0 ||j2>m){
	 return INTEGER.MIN_VALUE;
	}
	
	if(i == n-1){
		if(j1 == j2) return a[i][j1];
		else return a[i][j1] + a[i][j2];
		
	// 3. explore all paths
	// for one moment of alice, bob can have 3 movements
	// 9 combos
	dj[] = { -1, 0, 1}
	for(int k = -1; k <=1; k++){
			
		for(int l = -1; l <= 1; l++){
			 max = Maht.max(max, f(i+1, j1+k, j2+l))
		}
	
	}
	
	if(j1 == j2) return a[i][j1] + max;
	else return a[i][j1] + a[i][j2] + max;
}
	

TC: 3^n * 3^n
SC: O(M) auxillary stack place
```

- memoiation
    
    ```java
    memoize 3 values i, j1, j2
    
    dp[][][] = new dp[m][n][n]
    TC: M*N*N
    SC: O(N*M*M) + O(N) auxillary stack place
    ```
    
- Tabulation
    1. base case i.e m-1
    
    ![image.png](attachment:9b9349d2-6648-4016-9aff-ce44a0028f40:image.png)
    
    1. express every state in for loop
        1. i, j, j2
        
        i→ m-2 to 0
        
        j1→ 0 to n-1
        
        j2→ 0 to n-1
        
    
    ![image.png](attachment:8a1a5fc4-b02b-424b-b8a8-dbe1d1cc68a0:image.png)
    
- Space optimize
    
    ![image.png](attachment:68b35fad-87d5-4ed7-a932-2df4bea1e101:image.png)
    
    we only require the i+1th row
    

# Subsequences

## Subset sum k

- **all the sub seq problem you have to do not take and take in explore all paths**

because paths are taking or not taking only

- in grids paths were given in the question
- recursive approach
    
    ```java
        // find if subset sum is tarket
        class Solution {
    
            boolean f(int[] arr, int target, int index) {
                if(target == 0) return true;
                 if(index == 0) return a[0]==target;
    
                boolean notTake = f(arr, target, index-1);
                boolean take = false;
                if(arr[index] <= target) {
                    take = f(arr, target-arr[index], index-1);
                }
                return notTake || take;
            }
    
            public boolean isSubsetSum(int[] arr, int target) {
                return f(arr, target, arr.length-1);
            }
        }
    ```
    
- memoization
    
    ```java
        // find if subset sum is tarket
        class Solution {
    				int [][] memo;
            boolean f(int[] arr, int target, int index) {
                if(target == 0) return true;
                if(index == 0) return a[0]==target;
                
                if(memo[index][target] != -1) return memo[index][target];
    
                boolean notTake = f(arr, target, index-1);
                boolean take = false;
                if(arr[index] <= target) {
                    take = f(arr, target-arr[index], index-1);
                }
                return memo[index][target] = notTake || take;
            }
    
            public boolean isSubsetSum(int[] arr, int target) {
    		        memo = new int[arr.length][target];
    		        for(int [] arr: memo){
    			        Arrays.fill(arr, -1);
    			      }
                return f(arr, target, arr.length-1);
            }
        }
    ```
    
- dp
    
    ```java
        dp soln
        1. define arr of memo
        int[][] dp = new int[n][target+1];
        
        2. define base cases
        for(int i  to n )
    	    dp[i][0] = true;
    	  dp[0][a[0] = true;
    	 
    	  
    	  3. run loop for recur values
    	  for(int i 0 to n)
    		  for (j 1 to target)
    			  notTake = i>0?dp[i-1][j]:false;
    			  take = i>0&&j>=arr[i]?dp[i-1][j-arr[i]]:false;
    			  dp[i][j] = notTake || take;
    			
    		return dp[n-1][target];
    		
    		
    		//space optimize
    		 1. define arr of memo
        boolean[] dp = new boolean[target+1];
        dp[a[0]] = true;
        dp[0] = true;
    	  
    	  3. run loop for recur values
    	  for(int i 1 to n)
    		  int[] curr = new int[target+1]
    		  curr[0] = true;
    		  
    		  for (j 1 to target)
    			  notTake = dp[j];
    			  take = j>=arr[i]?dp[j-arr[i]]:false;
    			  curr[j] = notTake || take;
    			dp=curr;
    			
    		return dp[target];
        
        
        
    
    ```
    
- space optimized
    
    ```java
    public static boolean isSubsetSum(int[] arr, int target) {
        int n = arr.length;
        boolean[] dp = new boolean[target + 1];
        dp[0] = true;
    
        if (arr[0] <= target) {
            dp[arr[0]] = true;
        }
    
        for (int i = 1; i < n; i++) {
            boolean[] curr = new boolean[target + 1];
            curr[0] = true;
    
            for (int j = 1; j <= target; j++) {
                boolean notTake = dp[j];
                boolean take = (j >= arr[i]) ? dp[j - arr[i]] : false;
                curr[j] = notTake || take;
            }
    
            dp = curr;
        }
    
        return dp[target];
    }
    
    ```
    

## Partition Equal Subset Sum

- arr of n positive integers we have to partition them in two subsets, such that their sum is equal, tell them true or false

## Subset sum between two partition is minimal

**Partition Array Into Two Arrays to Minimize Sum Difference**

- we made a dp 2d array
- → target
- \|/ index
- The last row will tells us is some target achievable

![image.png](attachment:08175c5e-9192-41ce-8953-420f306ea1b6:image.png)

now we need |s1 - s2| is minimal

- now we will go in this array at the end we will see 0 is possible and last S is possible
- then we will find some n is possible and also S-n is possible as it will be the sum of the remaining elements which we don’t pick in n
- so we will find the number which is possible before ≤s/2
- and the ans will be that number, S-number i.e. if we want diff S-num-num
    - you can check the above by iterating until s/2 and keeping a min

---

- If negative numbers are allowed and we have to use a set instead of dp array for possible sums
- If negative allowed and we are not able to have 0 elements in any set, then meet in the middle technique is the only rescue. which i will learn later

## Count Subsets with sum K

- **IN ALL COUNT PROBLEMS, always in base case there will be 1 or 0**
- and we in general add and return the ways f()+f()+f()
- code
    
    ```java
    class Solution {
    	 // for greater than 0
    	 int countSubsets(int[] arr, int index, int K){
    	 
    		 if(index == 0){
    			 if(K==0 && arr[0] == 0) return 2;
    			 if(K==0 || arr[0] == K) return 1;
    			 return 0;
    		 }
    			
    		 
    		 int notPick = countSubsets(arr, index-1, K);
    
    		 int pick = K>=arr[index]? countSubsets(arr, index-1, K-arr[index]): 0;
    			
    		 return pick + notPick;
    	 
    	 }
    
        public int perfectSum(int[] arr, int K) {
    	    return countSubsets(arr, arr.length-1, K);
      
        }
    }
    ```
    
    ```java
    public int perfectSum(int[] arr, int K) {
        int n = arr.length;
        int[][] dp = new int[n][K + 1];
    
        // Initialization
        for (int i = 0; i < n; i++) {
            dp[i][0] = 1; // One way to make sum = 0 (empty subset)
        }
    
        if (arr[0] <= K)
            dp[0][arr[0]] += 1;
    
        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= K; j++) {
                dp[i][j] = dp[i - 1][j]; // not pick
                if (j >= arr[i])
                    dp[i][j] += dp[i - 1][j - arr[i]]; // pick
            }
        }
    
        return dp[n - 1][K];
    }
    
    ```
    

## DP18: count partitions with given difference

- s1-s2 = d [d is given]
- s1+s2 = total - we know
- s2= s1-d = total-s2-d
- s2 = (total - d)/2
- so the question is find the subset with given number (total-d)/2
- edge case is s2≥0 and total-d should be even>
- code
    
    ```java
    
    class Solution {
     public int countPartitions(int n, int diff, int[] arr) {
            
        }
    }
    
    ```
    

# 0-1 Knapsack

pick not pick only like subsequences

- Code
    
    ```java
    
    f(int index, int val[], int wt[], int w){
    	if(index == 0){
    		return w>=wt[0]? val[0]: 0;
    	}
    	
    	int notPick = f(index-1, val, wt, w);
    	int pick = w>=wt[index]?val[index]+f(index-1, val, wt, w-wt[index]):0;
    	return Math.max(pick, notPick);
    }
    
    knapsack(int n, int val[], int wt[], int w){
    	return f(n-1, val, wt, w);
    }
    ```
    
    ```java
    int[][] dp = new int[n][w + 1];
    
    for(int j = wt[0]; j<= w; j++){ dp[0][j] = val[0]; }
    
    for(int i = 1; i<n; i++){
    	for(int j = 0; j <= w; j++){
    		dp[i][j] = j>=wt[i]? Math.max(dp[i-1][j], val[i] + dp[i-1][j-wt[i]]): dp[i-1][j];
    	}
    }
    
    return dp[n-1][w];
    
    ```
    
    ```java
    //optimize this with one row only
    //what if we fill in reverse order the inner loop
    
    int[] dp = new int[w + 1];
    
    for(int j = wt[0]; j<= w; j++){ dp[j] = val[0]; }
    
    for(int i = 1; i<n; i++){
    	for(int j = w; j >= wt[i] ; j--){
    		dp[j] = Math.max(dp[j], val[i] + dp[j-wt[i]]);
    	}
    }
    
    return dp[w];
    ```
    

## Coin change

- infinite supply of coins
- code
    
    ```java
    int f(int index, int target, int[]coins){
    	if(index == 0){
    		if(target == 0) return 0;
    		if(target%coins[0]==0) return target/coins[0];
    		return Integer.MAX_VALUE;
    	}
    	
    	int notPick = f(index-1, target, coins);
    	int pick = Integer.MAX_VALUE;
    	if(target>=coins[index]){
    		 int subRes = f(index, target-coins[index], coins);
    		 if(subRes!=Integer.MAX_VALUE) pick = subRes+1;
    	}
    	
    	
    	return Math.min(pick, notPick);
    }
    
    int cc(int[] coins, int target){
      int n = coins.length;
    	return f(n-1, target, coins);
    }
    ```
    
    ```java
    int[][] dp = new int[n][target + 1];
    
    for(int i = 0; i <= target; i++) 
    	if(i%coins[0]==0) dp[0][i] = i/coins[0];
    	else dp[0][i] = (int)1e9;
    	
    for(int i = 1; i < n; i++){
    	for(int j = 0; j <= target; j++){
    		int notPick = dp[i-1][j];
    		int pick = 1e9;
    		if(j>=coins[i])
    			pick = dp[i][j-coins[i]]+1;
    		dp[i][j] = Math.min(pick, notPick);
    		
    	}
    	
    	return dp[n-1][target];
    
    ```
    
    ```java
    // space optimized one array
    
    int[] dp = new int[target + 1];
    
    for(int i = 0; i <= target; i++) 
    	if(i%coins[0]==0) dp[i] = i/coins[0];
    	else dp[i] = (int)1e9;
    	
    for(int i = 1; i < n; i++){
    	for(int j = 0; j <= target; j++){
    		int notPick = dp[j];
    		int pick = (int)1e9; // ie9 is a double value
    		if(j>=coins[i])
    			pick = dp[j-coins[i]]+1;
    		dp[j] = Math.min(pick, notPick);
    		
    	}
    }
    	
    return dp[target]>=1e9? -1: dp[target];
    ```
    

## Target sum

[1 2 3 1] 

how many ways can we assign + or - sign to get target 3

```java
// this can be done by recurusion f(ind, T) assign + and - sign and do recursion add them both to get the count
Map<String, Integer> memo = new HashMap<>();

int dfs(int i, int sum, int[] nums, int target) {
    String key = i + "," + sum;
    if (memo.containsKey(key)) return memo.get(key);

    if (i == nums.length) return sum == target ? 1 : 0;

    int count = dfs(i + 1, sum + nums[i], nums, target) +
                dfs(i + 1, sum - nums[i], nums, target);

    memo.put(key, count);
    return count;
}

// but we have done a problem where s1 - s2 = D 
// so we have done this problem where we wanted to put negative and positive signs
```

## Coin Change 2

Total number of ways to make some target

- code
    
    ```java
    f(ind, t){ - till index ind how many ways can you form t
    base case returns 1 or 0
    try all possibilities
    return sum of ways
    }
    
    f(i, t, coins[]){
    	if(i==0){
    	 if(t%coins[0]==0) return 1;
    		else return 0;
    	}
    	
    	int notPick = f(i-1,t, coins);
    	int pick = t>=coins[i] ? f(i, t-coins[i], coins): 0;
    	
    	
    	return pick + notPick;
    }
    
    dp[n][t+1]
    
    for(int i = 0; i <= t; i++){
    	dp[0][i] = t%coins[0]==0?1:0;
    }
    
    for(int i = 1; i < n; i++){
    	for(int j = 0; j < t+1; j++){
    		int notPick = dp[i-1][j]
    		int pick = j>=coins[i] ? dp[i][j-coins[i]]: 0;
    		dp[i][j] = pick+notPick;
    	}
    }
    
    return dp[n-1][t];
    
    dp[t+1] = {0}
    
    for(int i = 0; i <= t; i++){
    	dp[i] = i%coins[0]==0?1:0;
    }
    
    for(int i = 1; i < n; i++){
    	for(int j = 0; j < t+1; j++){
    		int notPick = dp[j]
    		int pick = j>=coins[i] ? dp[j-coins[i]]: 0;
    		dp[j] = pick+notPick;
    	}
    }
    
    return dp[t];
    ```
    

## Unbounded knapsack

→ unlimited supply

- code
    
    ```java
    f(int[] wt, int[] val, int w, int ind){
    if(ind == 0){
    	if(w<wt[0]) return 0;
    	if(w>=wt[0]) return w/wt[0] * val[0];
    }
    
    int notPick = f(wt, val, w, ind-1);
    int pick = w>=wt[ind]? val[ind] + f(wt, val, w-wt[ind], ind): 0;
    
    return Math.max(pick, notPick);
    }
    
    f(wt, val, w, n-1)
    ```
    
    ```java
    int dp[] = new int[t+1];
    
    for(int i = 0; i < t+1; i++){
     dp[i] = i>=wt[0]? i/wt[0] * val[0]: 0;
    }
    for(int i = 1; i < n; i++){
    	for(int j = 0; j < t+1; j++){
    		int notPick = dp[j];
    		int pick = j>=wt[i]? val[i] + dp[j-wt[i]]: 0;
    		dp[j] = Math.max(pick, notPick);
    	
    	}
    }
    return dp[t];
    ```
    

## Rod cutting [last problem on dp on subseq]

n=5 → rod length

price[] = 2, 5, 7, 8 ,10

for 1 length piece the price is 2

for 2 length piece the price we get is 5

so max we can get is 5+5+2 = 12

- code
    
    ```java
    f(ind, len, price)
    	if(ind==0){
    		if(len > 0) return len*price[0];
    		return 0;
    	}
    	
    	notPick = f(ind-1, len, price);
    	pick = len>ind? price[ind] + f(ind, len-ind-1, price): 0;
    	
    	return Math.max(pick, notPick);
    	
    	
    
    f(n-1,len, price)
    
    f(ind, len, price)
    	if(ind<0){
    		return 0;
    	}
    	
    	notPick = f(ind-1, len, price);
    	pick = len>ind? price[ind] + f(ind, len-ind-1, price): 0;
    	
    	return Math.max(pick, notPick);
    	
    	
    ```
    
    - Why use index 0 as base case here and not -ve
        
        Both work, but here's the blunt truth:
        
        ### ✅ Use `ind == 0` base case — it's cleaner and **more efficient**.
        
        ### Why?
        
        If you write:
        
        ```java
        if (ind == 0) return len * price[0];
        
        ```
        
        you're handling **all remaining rod length using only 1-length cuts**, which avoids deeper recursion. It’s the actual base logic.
        
        ### Avoid this:
        
        ```java
        if (ind < 0) return 0;
        
        ```
        
        It works, but it's lazy. You’ll still recurse down to -1 even when index 0 can be handled in one shot.
        
        ### Final Version (clean):
        
        ```java
        int f(int ind, int len, int[] price) {
            if (ind == 0) return len * price[0];
        
            int notPick = f(ind - 1, len, price);
            int pick = (len >= ind + 1) ? price[ind] + f(ind, len - (ind + 1), price) : 0;
        
            return Math.max(pick, notPick);
        }
        
        ```
        
        ### TL;DR:
        
        Use `ind == 0`. It avoids wasteful calls and expresses the real base case clearly.
        
    - Dp too becomes difficult
        
        Exactly — using `ind < 0` makes **memoization/DP implementation clumsy** and error-prone.
        
        Here’s **why `ind == 0` is better for DP**:
        
        ---
        
        ### 1. **Easier to index DP arrays**
        
        If your base case is `ind == 0`, your `dp[ind][len]` table stays within valid 0-based indexing.
        
        ```java
        java
        CopyEdit
        dp[0][len] = len * price[0]; // Clean and direct
        
        ```
        
        But if you use `ind < 0`, then:
        
        - You’d need to handle `dp[-1][len]`, which doesn’t exist.
        - You’re forced to add offset shifts (e.g. `ind + 1`) to avoid ArrayIndexOutOfBounds errors.
        
        ---
        
        ### 2. **No unnecessary recursion**
        
        With `ind == 0`, you terminate earlier:
        
        - It avoids going into meaningless recursive calls like `f(-1, len, ...)`.
        - This gives a performance edge and simplifies both memo and tabulation.
        
        ---
        
        ### 3. **Consistent tabulation**
        
        You can just write:
        
        ```java
        java
        CopyEdit
        for (int ind = 0; ind < n; ind++) {
            for (int len = 0; len <= N; len++) {
                // build dp[ind][len]
            }
        }
        
        ```
        
        No shifting, no offset hacks.
        
        ---
        
        ### ✅ Bottom Line:
        
        Use `ind == 0` as base case. It’s logical, avoids bugs, and directly maps to DP arrays.
        
    
    ```java
    int dp[] = new int[len+1];
    
    for(int l = 0; l <= len; l++){
    	dp[l] = l*price[0];
    }
    
    for(int ind = 1; ind<n; ind++){
    	for(int l = 0; l <= len; l++){
    			int notPick = dp[l];
    			int pick = l>ind? price[ind] + dp[l-ind-1]: 0;
    	
    	   dp[l] = Math.max(pick, notPick);
    	}
    }
    
    return dp[len];
    
    ```
    

# DP On Strings

## Longest Common Subsequence(LCS)

![image.png](attachment:50046da1-9a59-4510-bb66-568653c335e7:image.png)

- for brute force you have to generate all the subsequences and compare the largest
    - use the power set method to generate all for length 5 use 31 till 0 and whereever there are 1 in their binary replace with the char at that posn from the string
    - add all subseq in a set and check for the other in loop if it contains take a max of len and save.

```java
f(ind1, ind2, s1, s2)

// base case
if(ind1<0 || ind2<0) return 0

// explore all posibilities
if (s1[ind1] == s2[ind2]) // match case
	return 1 + f(ind1-1, ind2-1);
// not match case
return max(f(ind-1, ind2), f(ind1, ind2-1));

auxilary stack space N+M
depth of the binary tree is n+m so the tc is 2^n+m because at each stage we are breaking into 2

//memoize on ind1 and ind2
if we memoize the tc = n*m
```

- Here we took negative index as base case which makes tabulation a little difficult
- we could have done the same thing in all subsequence problems
- There it was easy to guess index 0, here its diff so we use -1
    
    ```java
    // if we want to write the base at 0 it will be clumpsy
    if (ind1 == 0 && ind2 == 0)
        return s1[0] == s2[0] ? 1 : 0;
    
    if (ind1 == 0) {
        // only s1[0] left — check if it matches anything in s2[0...ind2]
        for (int j = 0; j <= ind2; j++) {
            if (s1[0] == s2[j]) return 1;
        }
        return 0;
    }
    
    if (ind2 == 0) {
        // only s2[0] left — check if it matches anything in s1[0...ind1]
        for (int i = 0; i <= ind1; i++) {
            if (s2[0] == s1[i]) return 1;
        }
        return 0;
    }
    
    ```
    

```java
1. declare states, **copy base case**
the recursion is dependent on ind1-1 only so i will write
dp[m+1]

// here we can't write dp of -1, shift index
for(int i = 0; i <=m; i++)
	dp[i] = 0;
	

2. write changing param in opp fashion
for(int i = 1; i <= n; i++){
	int curr[] = new int[m+1];
	curr[0] = 0;
	for(int j = 1; j <= m; j++){
		// explore all posibilities
		//if (s1[i] == s2[j]) // match case // here is one problem that i missed
		//it should be s1[i-1] and s2[j-1] because we are running loop on shifted index
		if(s1[i-1] == s2[j-1])
			curr[j] = 1 + dp[j-1];
		// not match case
		else curr[j] = max(dp[j], curr[j-1]);
		// here we can't move to one arr sol becuase we need curr[j-1] and dp[j-1] both
	}
	dp = curr;
}
return dp[m];

3. copy the recurrence, **and account for shifting in loop**
```

## Printing LCS

- Code
    
    → use the dp array go from end and add char whenever they are same, if not same go backwards to the max of upper and left element
    
    ```java
        public String longestCommonSubsequence(String s1, String s2) {
            int n = s1.length();
            int m = s2.length();
    
            int[][] dp = new int[n + 1][m + 1];
    
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                    } else {
                        dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                    }
                }
            }
    
            int i = n;
            int j = m;
            StringBuilder ans = new StringBuilder();
    
            while (i > 0 && j > 0) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    ans.append(s1.charAt(i - 1));
                    i--;
                    j--;
                } else {
                    if (dp[i - 1][j] > dp[i][j - 1]) {
                        i--;
                    } else {
                        j--;
                    }
                }
            }
    
            return ans.reverse().toString();
        }
    ```
    

# Longest common substring

```java

// main logic
f(int m, int n, int cnt){
	if(m<0 or n<0) return cnt;
	
	int res = cnt;
	if(s1[m] == s2[n]) 
		res = f(m-1, n-1,cnt+1);
	
	 res= max(res, max(f(m-1,n,0), f(m,n-1,0)));
	 
	 return res;
}

```

- memoized- same as main logic only used a 3d dp array TC - O(n*m*max(m,n)) as dp has that many places to fill
    
    ```java
    class Solution {
        int[][][] dp;
    
        public int longestCommonSubstring(String s1, String s2) {
            int n = s1.length(), m = s2.length();
            dp = new int[n+1][m+1][Math.max(n, m)+1];
    
            for (int[][] arr2D : dp)
                for (int[] arr1D : arr2D)
                    Arrays.fill(arr1D, -1);
    
            return solve(s1, s2, n, m, 0);
        }
    
        private int solve(String s1, String s2, int i, int j, int count) {
            if (i == 0 || j == 0) return count;
    
            if (dp[i][j][count] != -1) return dp[i][j][count];
    
            int res = count;
    
            if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                res = solve(s1, s2, i - 1, j - 1, count + 1);
            }
    
            res = Math.max(res, Math.max(
                    solve(s1, s2, i - 1, j, 0),
                    solve(s1, s2, i, j - 1, 0)
            ));
    
            return dp[i][j][count] = res;
        }
    }
    
    ```
    
- dp
    
    ```java
    class Solution {
        public int longestCommonSubstring(String s1, String s2) {
            int n = s1.length(), m = s2.length();
            int[][] dp = new int[n+1][m+1];
            int maxLen = 0;
    
            for (int i = 1; i <= n; i++) {
                for (int j = 1; j <= m; j++) {
                    if (s1.charAt(i-1) == s2.charAt(j-1)) {
                        dp[i][j] = 1 + dp[i-1][j-1];
                        maxLen = Math.max(maxLen, dp[i][j]);
                    } else {
                        dp[i][j] = 0; // break substring
                    }
                }
            }
            return maxLen;
        }
    }
    
    ```
    
    👉 **Time & Space = O(n × m), space can be reduced to m**
    
- a better soln than dp [not for interviews]
    
    a binary search plus hashing 
    
    it uses [Rabin Karp](https://www.notion.so/Rabin-Karp-23e8d1088fb78099a7a5ded28f121155?pvs=21) which is also rarely asked in interviews
    
    O((n+m)*log(min(n,m)))
    
    → not required in the interviews
    

## Longest Palindromic subsequence

→ just take lcs(input, reverse(input))

## Min Insertions required to make a String Palindrome

- we can make any given string a palindrome by adding reverse(string) to it, and max operations will be len(input)
- but the question is find the min insertions

find the longest palindromic subsequence 

**the ans will be len(input)-len(longest palindromic subseq)**

because we will keep the longest palindromic subseq intact and we will add all the other elems in reverse order
→ same for min deletions remove all the other except longest palindromic subseq

## Min operations required to form string 1 to string 2

→ you can insert or delete

f(abcd, anc)

abcd → ac

ac → anc

lcs = longest common subsequence

deletions for s1 = len(s1) - len(lcs)

insertions for s2 = len(s2) - len(lcs)

total = len(s1) + len(s2) - 2*len(lcs)

## Shortest common supersequence

s1 = brute s2 = groot

both should be there

one sol for superseq = brutegrute

but we need shortest which is bgruoote

s1 = bleed, s2 = blue

ans = bleued

lcs = longest common subsequence

ans for len = s1+s2-lcs

len is simple

but how to do printing its bit complicated

- to print the shortest common subseq you will have to use the dp table like how we used in printing lcs
- like in printing lcs if it matched we can take it once
- also when we are going left or top add it, we will go in the direction of max, both equal go anywhere
- at the end if any one of i or j reaches 0, take all the char of remaining string
- reverse it that will be the `scs`
- code
    
    ```jsx
    dp[m+1][n+1]
    
    i = m+1;
    j = n+1;
     
    while(i>0 && j>0){
    	if(s1[i-1] == s2[j-1]){
    		ans.append(s1[i-1]);
    		i--;
    		j--;
    	}
    	else if(dp[i-1][j] > d[i][j-1]){
    		ans.append(s1[i-1]);
    		i--;
    	}
    	else{
    		ans.append(s2[j-1]);
    		j--;
    	}
    }
    while(i>0){
    	ans.append(s1[i-1])
    	i--;
    }
    while(j>0){
    	ans.append(s2[j-1])
    	j--;
    }
    
    return reverse(ans);
    ```
    

# String matching in DP

## Distinct subsequences

s = “babgbag”, s2 = “bag”

- given two strings how many times s2 comes in s1
- here it is 5 occurrences

```jsx
// whenever there is count generic code
f()
base 1 or 0 returning
return f()+f();

//how to write recurr
1. express everything in terms of i,j
2. explore all possiblilities
3. return summation of all
4. write base case

f(i, j){ no. of distinct subsequences of s2[0..j] in s1[0..i]

cnt = 0;
if(s1[i] == s2[j]){
	cnt+=f(i-1,j-1)
}
cnt += f(i-1,j) 
return cnt

base case: 
if(i<0) return 0;
if(j<0) return 1;

// memoize this dp[n][m]
```

- DP code
    
    ```jsx
    int[][] dp = new int[n+1][m+1];
    
    //1. base case
    // do 1 based indexing
    for(int i = 0; i <=n; i++) dp[i][0] = 1;
    
    //2. write changing params
    for(int i = 1; i < n+1; i++){
    	for(int j = 1; j < m+1; j++){
    		//3. copy the recurrence
    	
    		if(s1[i-1] == s2[j-1]){
    			dp[i][j]+=dp[i-1][j-1];
    		}
    		dp[i][j] += dp[i-1][j];
    
    	}
    }
    return dp[n][m];
    ```
    

## Edit Distance

min operations to convert s1 to s2

three ops allowed 1. insert, 2.remove, 3.replace

s1= “horse”, s2 = “ros” ⇒ 3 operations

- in prev question we had done insert and remove using lcs
- this question adds replace so it becomes more difficult

```jsx
lcs is not working directly like insert and remove
so next thing is try all possible ways i.e. recursion

f(i,j) // min operations required to convert s1[0...i] to s2[0...j]

	if(s[i] == s[j]){
		return 0 + f(i-1,j-1)		
	}
	
	// if not matched
	// delete the char, insert, replace
	return Math.min(1+f(i-1,j), 1+f(i,j-1), 1+f(i-1,j-1))
	
	
	base case:
	if(i<0) return j+1;
	if(j<0) return i+1;
```

- dp
    
    ```jsx
    int dp[][] = new int[n+1][m+1];
    
    // index shift
    for(int i = 0; i <= n; i++){
    	dp[i][0] = i;
    }
    for(int i = 0; i <= m; i++){
    	dp[0][i] = i;
    }
    
    for(int i = 1;  i <= n; i++){
    	for(int j = 1; j <= m; j++){
    		dp[i][j] = s1.charAt(i-1) == s2.charAt(j-1)? dp[i-1][j-1]:
    							Math.min(Math.min(1+dp[i-1][j], 1+ dp[i][j-1]), 1+dp[i-1][j-1]);
    							}
    	}
    
    return dp[n][m];
    ```
    

# Wildcard matching

- * matches with 0 or more chars
- ? matches with 1 char
- s1 contains wild cards, s2 is normal string

```jsx
f(i, j) => will s1[0...i] matches with s2[0..j]

if(s1[i] == s[j] || s1[i] == '?'){
	return f(i-1,j-1);
if(s1[i] == '*'){
	return f(i, j-1) || f(i-1,j);
return false;

base case:
if(i<0 && j<0) return true;
else if(i<0 && j>=0) return false;
else if(j<0 && i>=0){
	for(int a = 0; a<=i; a++){
		if(s1[i] != *) return false;
	}
	return true;
}

```

- dp code
    
    ```jsx
    
    int m = p.length(), n = s.length();
    boolean dp[][] = new boolean[m+1][n+1];
    
    dp[0][0] = true;
    for(int j = 1; j <=n; j++) dp[0][j] = false;
    for(int i = 1; i <=m; i++){
    	dp[i][0] = dp[i-1][0] && p.charAt(i-1) == '*';
    }
    
    for(int i = 1; i < m+1; i++){
    	for(int j = 1; j < n+1; j++){
    		if(p.charAt(i-1) == s.charAt(j-1) || p.charAt(i-1) == '?')
    			dp[i][j] =  dp[i-1][j-1];
    		else if(p.charAt(i-1) == '*')
    			dp[i][j] = dp[i][j-1] || dp[i-1][j];
    		else dp[i][j] = false;
    	}
    }
    
    return dp[m][n];
    
    ```
    

# DP on stocks (6 problems)

## Best time to buy & sell a stock 1

arr[] = 7,1,5,3,6,4

n =6

you have to decide a day when you buy and when you sell the stock

this is may be not dp, but basic constructive algo

but this is put under dp because the future problems builtup are dp

- code
    
    ```jsx
    class Solution {
        public int maxProfit(int[] prices) {
    	     int min = prices[0];
    	     int maxProfit = 0;
    	     
    	     for(int i = 0; i < prices.length; i++){
    		     maxProfit = Math.max(maxProfit, prices[i]-min);
    		     min = Math.min(min, prices[i]);
    	     }
    	     
    	     return maxProfit;
            
        }
    }
    ```
    

## Best time to buy and sell stock 2

We can buy and sell as many times as i want

only condition is before buying again you have to sell the old stock that you hold

arr[] = 7,1,5,3,6,4

n =6

ans is 5-1 + 6-3 = 7

```jsx
A lot of ways 
We try all ways
This means recursion

Q) How to write recurrence
1. express in terms of index
we have to know if at an ind something is already bought which is a state
f(ind, buy){
2. explore posibilities
3. take max of all profits

if(buy == false){
	return profit = Math.max(-prices[ind] + f(ind+1, true), 0 + f(ind+1, false));
}
else{
	return profit = Math.max( prices[ind]+f(ind+1, false), 0 + f(ind+1, true));
}
	
}
4. Base case:
if(ind == n){
	return 0;
}

f(0, false) -> starting with 0 and ability to buy or not buy
```

- DP code
    
    ```java
    
    int dp[][] = new int[n+1][2];
    
    dp[n][0] = 0; // 0 means can buy or not buy
    dp[n][1] = 0; // 1 means can sell or not sell
    
    for(int i = n-1; i >= 0 ; i--)
    {
    			dp[i][0] = Math.max(-prices[i] + dp[i+1][1] , 0 + dp[i+1][0]);
    
    			dp[i][1]  = Math.max(prices[i]+ dp[i+1][0], 0 + dp[i+1][1]);
    
    }
    
    return dp[0][0];
    
    ```
    
    ```java
    space optimized
    int dp[] = new int[2];
    dp[0] = 0;
    dp[1] = 0;
    
    for(int i = n-1; i >= 0 ; i--)
    {
    			int prevProfitWhenWeCanBuy = dp[0];
    			dp[0] = Math.max(-prices[i] + dp[1] , 0 + dp[0]);
    			dp[1]  = Math.max(prices[i]+ prevProfitWhenWeCanBuy, 0 + dp[1]);
    }
    
    return dp[0];
    ```
    
    ```java
    better names
    
    int[] profit = new int[2];
    profit[0] = 0; // profitWhenCanBuy: max profit starting from this day when we can buy
    profit[1] = 0; // profitWhenCanSell: max profit starting from this day when we already hold a stock
    
    for (int day = n - 1; day >= 0; day--) {
        int prevProfitWhenCanBuy = profit[0];
    
        // Option 1: Buy at current price and later sell for profit
        // Option 2: Skip buying today
        profit[0] = Math.max(-prices[day] + profit[1], profit[0]); 
    
        // Option 1: Sell the stock we have
        // Option 2: Skip selling today
        profit[1] = Math.max(prices[day] + prevProfitWhenCanBuy, profit[1]);
    }
    
    return profit[0]; // Final answer: max profit if we start with the option to buy
    
    ```
    
    ```java
    // here greedy also works 
    for (let i = 1; i < prices.length; i++) {
        if (prices[i] > prices[i - 1]) {
            ans += prices[i] - prices[i - 1];
        }
    }
    
    ```
    
    | Constraint | Use Greedy? | Use DP? |
    | --- | --- | --- |
    | Unlimited transactions | ✅ Yes | ✅ Yes |
    | With cooldown | ❌ No | ✅ Yes |
    | With fee | ❌ No | ✅ Yes |
    | Max k transactions | ❌ No | ✅ Yes |
    

## Best time to buy and sell stock 3

→ at max 2 transactions

```java
f(ind, buy, cap) - f(9,1,2)

if(cap == 0) return 0;
if(ind == n) return 0;

if(buy) 
	return max (-prices[ind] + f(ind+1, 0, cap),
												 0 + f(ind+1, 1, cap));
else{
	return max (prices[ind] + f(ind+1, 1, cap-1), 
								0 + f(ind+1, 0, cap);

}
TC: exponential
SC: O(n) - because of depth ASS
```

we can also take 2 variables as states and solve this

![image.png](attachment:4c8c7006-b10e-424e-a86e-9a778e6a53c0:image.png)

- DP
    
    ```java
    int dp[][][] = new int[n+1][2][3];
    
    // you can remove intializations as they are 0 by them selves
    for(int i = 0; i < n+1; i++){
    	dp[i][0][0] = 0;  // when you can sell
    	dp[i][1][0] = 0;  // when you can buy
    }
    
    for(int i = 0; i < 3; i++){
    	dp[n][0][i] = 0;
    	dp[n][1][i] = 0;
    }
    
    for(int i = n-1; i >=0; i--){
    	for(int j = 1; j < 3; j++){
    			dp[i][1][j] = Math.max(-prices[i] + dp[i+1][0][j],
    														 dp[i+1][1][j]);
    		
    			dp[i][0][j] = Math.max(prices[i] + dp[i+1][1][j-1], 
    										dp[i+1][0][j]);
    	
    	}
    }
    	
    	return dp[0][1][2];
    }
    
    ```
    
    ```java
    int dp[][] = new int[2][3];
    
    for(int i = 0; i < 3; i++){
    	dp[0][i] = 0;// when you can sell
    	dp[1][i] = 0;// when you can buy
    }
    
    for(int i = n-1; i >=0; i--){
    	int curr[][] = new int[2][3];
    	
    	for(int j = 1; j < 3; j++){
    			curr[1][j] = Math.max(-prices[i] + dp[0][j],
    														 dp[1][j]);
    		
    			curr[0][j] = Math.max(prices[i] + dp[1][j-1], 
    										dp[0][j]);
    	
    	}
    	dp = curr;
    }
    	
    	return dp[1][2];
    }
    ```
    

## Best time to buy and sell 5

→ buy and sell with a cool down

→ cannot buy right after sell

prices[] = {4,9,0,4,10}

9-4 + 10-4 = 11

```java
//normal buy and sell unlimited

f(ind, buy)

	 if(ind >= n) return 0;

	if(buy){
		return profit = min(-prices[i] + f(ind+1, 0), f(ind+1, 1))
	}
	else{
		return profit = min (prices[i]+f(ind+1,1), f(ind+1,0));
	}

// the only change here is in selling now
//you can't buy on the next day
	else{
		return profit = min(prices[i]+**f(ind+2,1)**, f(ind+1,0));
	}
```

- DP
    
    ```java
    int dp[][] = new int[n+2][2];
    
    dp[n][0] = 0; // 0 means can buy or not buy
    dp[n][1] = 0; // 1 means can sell or not sell
    
    for(int i = n-1; i >= 0 ; i--)
    {
    			dp[i][0] = Math.max(-prices[i] + dp[i+1][1] , 0 + dp[i+1][0]);
    
    			dp[i][1]  = Math.max(prices[i]+ dp[i+2][0], 0 + dp[i+1][1]);
    
    }
    
    return dp[0][0];
    ```
    

## Best time to buy and sell 6

- stock with fees

there is a fees fixed eg 2 for each transaction

```java
f(ind, buy)
	if(ind == 0) return 0;
	if(buy){
		return max(-price[ind] + f(ind+1, 0), f(ind+1,1);
	}
	else{
		return max(price[ind] + f(ind+1, 1), f(ind+1, 0);
	}
	
	// in the normal code above we will pay fee either while buying or selling
	
	else{
		return max(price[ind] - fee + f(ind+1, 1), f(ind+1, 0);
	}
```

- dp code modified
    
    ```java
    int dp[][] = new int[n+1][2];
    
    dp[n][0] = 0; // 0 means can buy or not buy
    dp[n][1] = 0; // 1 means can sell or not sell
    
    for(int i = n-1; i >= 0 ; i--)
    {
    			dp[i][0] = Math.max(-prices[i] + dp[i+1][1] , 0 + dp[i+1][0]);
    
    			dp[i][1]  = Math.max(prices[i] -fee + dp[i+1][0], 0 + dp[i+1][1]);
    
    }
    
    return dp[0][0];
    ```
    

# Longest Increasing subsequence

- take or not take pattern

arr[] = [10, 9 , 2, 5, 3, 7, 101, 18]

2, 3, 7, 101 - ans = 4

8, 8, 8 → ans  =1 

```java
f(ind, prev_ind) => f(0,-1) => give the the LIS from 0 who has no prev

if(prev_ind==-1 || arr[ind]>arr[prev_ind){
	return Math.max(1 + f(ind+1, ind), f(ind+1, prev_ind));
}
else{
	return f(ind+1,prev_ind);
}

base case:
if(ind == n) return 0;
```

- This can also be written from n-1 to 0 - but not very intuitive
    
    ```java
    int f(int ind, int prev_ind) {
        if (ind < 0) return 0;
    
        int notTake = f(ind - 1, prev_ind);
        int take = 0;
    
        if (prev_ind == -1 || arr[ind] < arr[prev_ind]) {
            take = 1 + f(ind - 1, ind);
        }
    
        return Math.max(take, notTake);
    }
    
    ```
    
- dp code
    
    ```java
    dp[n][n+1]
    
    f(ind, prev_ind)
    	if(ind == n) return 0;
    	if(dp[ind][prev_ind+1]!=-1) return dp[ind][prev_ind+1];
    	if(prev_ind==-1 || arr[ind]>arr[prev_ind){
    			return dp[ind][prev_ind+1] = Math.max(1 + f(ind+1, ind), f(ind+1, prev_ind));
    		}
    		else{
    			return dp[ind][prev_ind+1] = f(ind+1,prev_ind);
    		}
    
    f(0,-1);
    
    int dp[][] = new int[n+1][n+1];
    // base case is already 0
    
    for(int ind = n-1; ind >= 0; ind--){
    	for(int prev_ind = ind-1; prev_ind>=-1; prev_ind--){
    	
    		if(prev_ind==-1 || arr[ind]>arr[prev_ind]){
    		// we are storing the second param in +1 state
    			dp[ind][prev_ind+1] = Math.max(1 + dp[ind+1][ind+1], dp[ind+1][prev_ind+1]);
    		}
    		else{
    		  dp[ind][prev_ind+1] =  dp[ind+1][prev_ind+1];
    		}
    	
    	}
    }
    return dp[0][-1+1];
    
    int prev[] = new int[n+1];
    // base case is already 0
    
    for(int ind = n-1; ind >= 0; ind--){
    	int curr[] = new int[n+1];
    	for(int prev_ind = ind-1; prev_ind>=-1; prev_ind--){
    	
    		if(prev_ind==-1 || arr[ind]>arr[prev_ind]){
    		// we are storing the second param in +1 state
    			curr[prev_ind+1] = Math.max(1 + prev[ind+1], prev[prev_ind+1]);
    		}
    		else{
    		  curr[prev_ind+1] =  prev[prev_ind+1];
    		}
    		
    	
    	}
    	prev = curr;
    }
    return prev[-1+1];
    
    ```
    
- Better soln to explain in interviews **[memorise this approach for LIS]**
    
    ```java
    public int lengthOfLIS(int[] arr) {
        int n = arr.length;
        int[] dp = new int[n];
        Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
        int maxLen = 1;
    
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[prev] < arr[i]) {
                    dp[i] = Math.max(dp[i], 1 + dp[prev]);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
    
        return maxLen;
    }
    ```
    

## Print the LIS

this is eaiser to trace from the dp of better soln 

tracing from dp of recursive + dp, dp array is little difficult

```java
public int[] lengthOfLIS(int[] arr) {
    int n = arr.length;
    int[] dp = new int[n];
    int[] hash = new int[n];
    Arrays.fill(dp, 1);
    int maxLen = 1;
    int lastIndex = 0;

    for (int i = 0; i < n; i++) {
        hash[i] = i;
        for (int prev = 0; prev < i; prev++) {
            if (arr[prev] < arr[i] && 1 + dp[prev] > dp[i]) {
                dp[i] = 1 + dp[prev];
                hash[i] = prev;
            }
        }
        if (dp[i] > maxLen) {
            maxLen = dp[i];
            lastIndex = i;
        }
    }

    ArrayList<Integer> lis = new ArrayList<>();
    lis.add(arr[lastIndex]);
    while (hash[lastIndex] != lastIndex) {
        lastIndex = hash[lastIndex];
        lis.add(arr[lastIndex]);
    }

    Collections.reverse(lis);
    return lis.stream().mapToInt(i -> i).toArray();
}

```

[Dynamic Programming: Striever](https://www.notion.so/Dynamic-Programming-Striever-2438d1088fb7802ab611de365ebc65c1?pvs=21)

## LIS using binary search

above all approaches are still N^2

so now we have to make it NLogn for 10^5 sized input

- Binary search content
    
    ```java
    
    int binarySearch(int[] arr, int low, int high, int x){
    	if(high<low) return -1;
    
    	int mid = (high-low)/2 + low;
    	if(arr[mid] == x){
    		return mid;
    	}
    	else if (arr[mid]>x){
    		return binarySearch(arr, low, mid-1, x);
    	}
    	else if (arr[mid]<x){
    		return binarySearch(arr, mid+1, high, x);
    	}
    
    }
    
    // first element where we can insert x to maintain sorted order
    // lower bound is also a ceil function
    int lowerBound(int[] arr, int low, int high, int x) {
        if (low > high) {
            return low;  // This is the insertion point or first element >= x
        }
    
        int mid = low + (high - low) / 2;
    
        if (arr[mid] >= x) {
            // Potential lower bound found, search left half to find earlier one
            return lowerBound(arr, low, mid - 1, x);
        } else {
            // arr[mid] < x, search right half
            return lowerBound(arr, mid + 1, high, x);
        }
    }
    
    int lowerBound(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int ans = arr.length;  // Default: past end (if no greater element exists)
    
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] >= x) {
                ans = mid;         // This might be the upper bound
                high = mid - 1;    // Try to find an earlier one
            } else {
                low = mid + 1;     // Go right to find > x
            }
        }
    
        return ans;
    }
    
    // index whose element is just greater than x
    int upperBound(int[] arr, int low, int high, int x) {
        if (low > high) {
            return low;  // Insertion point for value > x
        }
    
        int mid = low + (high - low) / 2;
    
        if (arr[mid] <= x) {
            // arr[mid] is not strictly greater than x, go right
            return upperBound(arr, mid + 1, high, x);
        } else {
            // arr[mid] > x, might be the upper bound, go left
            return upperBound(arr, low, mid - 1, x);
        }
    }
    
    int upperBound(int[] arr, int x) {
        int low = 0, high = arr.length - 1;
        int ans = arr.length;  // Default: past end (if no greater element exists)
    
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (arr[mid] > x) {
                ans = mid;         // This might be the upper bound
                high = mid - 1;    // Try to find an earlier one
            } else {
                low = mid + 1;     // Go right to find > x
            }
        }
    
        return ans;
    }
    
    upper bound and lower bound are the same element if no x
    is present.
    lower bound arr[i] >=x
    upper bound arr[i] > x
    
    ```
    

![image.png](attachment:c2fda814-ae89-4918-adc6-f83b06adb23a:image.png)

→ we find the lis here by adding increasing elements to an lis array

→ we add the element if its greater than the last index 

→ if its less than we find the first element greater than or equal to it and replace with lesser value

→ if we don’t replace the equal value and replace the greater one there too we will get a longer subseq length which will not we strictly increasing 

→ replace the greater elem if you want longest non decreasing subseq then also add elem if its equal to the last elem

→ in the end we get the length of the lis, note: we do not get the lis by this approach

→ to find the next greater in the sorted array we use binary search

- code
    
    ```java
    
    // not the next greater, need a lower bound here not an upper bound
    int getNextGreaterOrEqualElemIndex(List<Integer> lis, int x){
    	int n = lis.size();
    	int low = 0;
    	int high = n-1;
    	int ans = n-1;
    	
    	while(low<=high){
    		int mid = low + (high-low)/2;
    		
    		if(lis.get(mid)>x)
    		{ 
    			ans = mid;
    			high = mid-1;
    		}
    		else if(lis.get(mid)<=x) low = mid+1;
    	
    	}
    
    	return ans;
    }
    
    int lis(int[] arr){
    
    	List<Integer> lis = new ArrayList<>();
    	
    	for(int x: arr){
    		int lisLen = lis.size();
    		if(lisLen==0) lis.add(x);
    		else if(lis.get(lisLen-1)<x) lis.add(x);
    		else {
    			int idx = getNextGreaterOrEqualElemIndex(lis,x);
    			lis.set(idx, x);
    		}
    	}
    	
    	return lis.size();
    
    }
    ```
    

## Largest Divisible Subset

arr[] = [1, 16, 7, 8 4]

arr[i]%arr[j] == 0 or arr[j]%arr[i] == 0 where i, j are in the ans subset

[16, 8, 4,1] ⇒ ans

print any order

thought process convert to lis

sort it 1, 4, 7, 8, 16 

![image.png](attachment:613b9d45-6db9-415d-b031-75e85202df06:image.png)

→ so this was the lis code if the value was greater than previous index we were storing 

→ here we will make **arr[i] % arr[j] ==0** we will do the same thing.

→ we can directly store the dp[j] because if 16 is divisible by 8 

it will be divisible by 4 and 1 

as the array is sorted

→ **if array is not sorted then it will not work**

- code
    
    ```java
        // length 
        public int lengthOfLDSSimple(int[] arr) {
            int n = arr.length;
            Arrays.sort(arr);
            int[] dp = new int[n];
            Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
            int maxLen = 1;
        
            for (int i = 0; i < n; i++) {
                for (int prev = 0; prev < i; prev++) {
                    if (arr[i] % arr[prev] == 0) {
                        dp[i] = Math.max(dp[i], 1 + dp[prev]);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        
            return maxLen;
        }
    
        // printing
        public List<Integer> largestDivisibleSubset(int[] arr) {
            int n = arr.length;
            int[] dp = new int[n];
            Arrays.sort(arr);
    
            // used for printing the lis
            // stores the previous element of this lis
            int[] hash = new int[n];
            Arrays.fill(dp, 1);
            int maxLen = 1;
            int lastIndex = 0;
        
            for (int i = 0; i < n; i++) {
                hash[i] = i;
                for (int prev = 0; prev < i; prev++) {
                    if (arr[i] % arr[prev] == 0 && 1 + dp[prev] > dp[i]) {
                        dp[i] = 1 + dp[prev];
                        hash[i] = prev;
                    }
                }
                if (dp[i] > maxLen) {
                    maxLen = dp[i];
                    lastIndex = i;
                }
            }
        
            ArrayList<Integer> lis = new ArrayList<>();
            lis.add(arr[lastIndex]);
            while (hash[lastIndex] != lastIndex) {
                lastIndex = hash[lastIndex];
                lis.add(arr[lastIndex]);
            }
        
            Collections.reverse(lis);
            return lis;
        }
    ```
    

## Longest String Chain

![image.png](attachment:0de3902c-b31a-477b-ab37-b4684b0f1761:image.png)

→ all the above shown are chains, one new character from old string

→ the longest chain is a, ba, bca, bdca

so the length is 4

→ 

`wordA` is a **predecessor** of `wordB` if and only if we can insert **exactly one** letter anywhere in `wordA` **without changing the order of the other characters** to make it equal to `wordB`.

- For example, `"abc"` is a **predecessor** of `"abac"`, while `"cba"` is not a **predecessor** of `"bcad"`.

![image.png](attachment:d628a6a0-edcb-4bcb-bb12-3713b2dbe55b:image.png)

→ only change in lis logic is change arr[j]<arr[i] to compare(arr[i], arr[j])
→ in the compare function we have to check if the diff is length one and the chars are matching except 1

→ compare function can also be named as isPredecessor

- code
    
    ```java
    class Solution {
    
        boolean isPredecessor(String parent, String child){
            if(parent.length()!=child.length()+1)return false;
    
            int i = 0; 
            int j = 0;
    
            while(i<parent.length()){
                if(j< child.length() && parent.charAt(i) == child.charAt(j)){
                    i++;
                    j++;
                }
                else{
                    i++;
                }
            }
    
            return j == child.length();
        }
    
        public int longestStrChain(String[] arr) {
            int n = arr.length;
            Arrays.sort(arr, (a, b)->(a.length()-b.length()));
            int[] dp = new int[n];
            Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
            int maxLen = 1;
        
            for (int i = 0; i < n; i++) {
                for (int prev = 0; prev < i; prev++) {
                    if (isPredecessor(arr[i], arr[prev])) {
                        dp[i] = Math.max(dp[i], 1 + dp[prev]);
                    }
                }
                maxLen = Math.max(maxLen, dp[i]);
            }
        
            return maxLen;
        }
    }
    
    ```
    

## Longest Bitonic Subsequence- based on lis

arr[] = 1, 11, 2, 10, 4, 5, 2, 1

anything increasing and decreasing 

or only increasing or only decreasing

1,2,10,4,2,1 

1,2,10,5,2,1 

1,11,10,4, 2,1 

all these are longest bitonic and length is 6

so LIS is also a bitonic and LDS too

![image.png](attachment:21b3ed72-0652-483a-839c-a3db633c52ce:image.png)

- this is lis code where dp[i] meant longest increasing sub if you consider the current element

![image.png](attachment:c3ae9bd4-b294-4a80-a6f1-fe2774676c91:image.png)

- so we figure out lis from front and lis from back
- then we add them at each element and -1 as one element at junction is common
- then from bitonic array we take the maximum

- Code
    
    ```java
     public int longestBitonicSubsequence(int[] arr) {
            int n = arr.length;
    
            // LIS from left to right
            int[] dp1 = new int[n];
            Arrays.fill(dp1, 1); // Each element is an LIS of length 1 by itself
        
            for (int i = 0; i < n; i++) {
                for (int prev = 0; prev < i; prev++) {
                    if (arr[prev] < arr[i]) {
                        dp1[i] = Math.max(dp1[i], 1 + dp1[prev]);
                    }
                }
            }
    
            // LIS from right to left
            int[] dp2 = new int[n];
            Arrays.fill(dp2, 1); // Each element is an LIS of length 1 by itself
    
            for (int i = n - 1; i >= 0; i--) {
                for (int prev = n - 1; prev > i; prev--) {
                    if (arr[prev] < arr[i]) {
                        dp2[i] = Math.max(dp2[i], 1 + dp2[prev]);
                    }
                }
            }
    
            int maxLen = 0;
            for (int i = 0; i < n; i++) {
                maxLen = Math.max(maxLen, dp1[i] + dp2[i] - 1);
            }
    
            return maxLen;
    
        }
    ```
    

## Number of longest increasing subsequences

just keep a count array and whenever we get max we want to add to the cnt as well

```java
        int[] dp = new int[n];
        int[] cnt = new int[n];
        Arrays.fill(dp, 1); // Each element is an LIS of length 1 by itself
		    Arrays.fill(cnt, 1);
		    int maxi=1;
        for (int i = 0; i < n; i++) {
            for (int prev = 0; prev < i; prev++) {
                if (arr[prev] < arr[i] && dp[i]<1+dp[prev]) {
                    dp[i] = 1 + dp[prev];
                    //inherit
                    cnt[i] = cnt[prev];
                }
                else if(arr[prev] < arr[i] && dp[i] == 1+dp[prev]){
                //increase
	                cnt[i]+=cnt[prev];
	              }
            }
            maxi = Math.max(dp[i], maxi);
        }
        int nos = 0;
        for(int i = 0; i < n; i++){
	        if(maxi == dp[i]) nos+= cnt[i];
        }
        
        return nos;

```

# Partition DP

mcm , boolean parthesis, and more

![image.png](attachment:544f93ae-f97e-4694-bdcc-9b588598d969:image.png)

we consider partitions to solve the problem like we move k and find answers

for i to k and k+1 to j

## Matrix chain multiplication

![image.png](attachment:fcac9934-7073-4205-b957-d1f2a73683c9:image.png)

basic of multiplying

4 operations

m*n and n*o

operations required will be m*n*o 

and the final result will be m*o

given the n matrix dimensions, tell the min cost to multiply

ABC ways to multiply A(BC) and (AB)C

ABCD ⇒ ways to multiply

A(B(CD)) or (AB)(CD) or A(BC)D…

given 

dimension arr = 10, 20, 30, 40, 50

so from above A = 10*20, B = 20*30, C…D = 40*50

so for any ith matrix  will be having dimension arr[i-1]*arr[i] eg A = 10*20 matrix [A being our 1st matrix i.e 1 indexed

Steps for partition dp

```java
1. start with entire array and have index i and j f(i, j) where i is start and j is end
2. try all partitions
3. return the best possible 2 partitions

```

![image.png](attachment:eac40f4f-94ef-41d7-8e47-947e1f6bc00e:image.png)

```java
f(i,j) f(1,n-1)

base case
if(i==j) return 0; // when we have single matrix
min = 1e9;
for(int k = i; k<=j-1, k++)
	min = Math.min(min, f(i,k)+f(k+1,j)+A[i-1]*A[k]*A[j])

return min
// we can memoize of dp[i][j] - dp[n-1][n-1]
O(N*N(states) * N(for each state we run loop))
S.C. O(N) auxilary space
```

```java
// convert to dp

int dp[][] = new int[n][n];
for(int i = 0; i <= n-1; i++) dp[i][i] = 0;

for(int i = n-1; i >= 1; i--){
	for(int j = i+1; j< n; j++){ // taking j from i+1 because j starts from i+1
		if(i!=j){
			int min = (int)1e9;
			for(int k = i; k<j; k++)
				min = Math.min(min, dp[i][k]+dp[k+1][j]+A[i-1]*A[k]*A[j]);
			dp[i][j] = min;
		}
	}
}

return dp[1][n-1]	

```

## Minimum cost to cut a stick

![image.png](attachment:b73fcb39-d51d-4f9b-8d91-8c4b4cddc81d:image.png)

given cuts array = [1, 3,  4, 5], n = 7

i can make a cut at any of the above values in any order

---

- we will sort the array and put 0 and n at start and end[pad the array]

0 1 3 4 5 7

```java
f(1, 4) f(1, c)

// f(i,j) represents the minimum cost to cut the stick between cuts[i-1] and cuts[j+1] considering only the cuts from index i to j
f(i, j) 
	if(i>j) return 0;
	min = int_max; 
	for(int k = i; k <= j; k++)
		cost = cuts[j+1] - cuts[i-1] + f(i, k-1) + f(k+1, j)
		min = min(cost, min);
	return min;
```

- code
    
    ```java
         public int minCost(int n, int[] cuts) {
            List<Integer> arrList = new ArrayList<>(
                    Arrays.stream(cuts).boxed().collect(Collectors.toList()));
    
            arrList.add(0);
            arrList.add(n);
            Collections.sort(arrList);
    
            int c = cuts.length;
    
            int[][] dp = new int[c + 2][c + 2]; // as we added two vals
    
            for (int i = c; i >= 1; i--) {
                for (int j = i; j <= c; j++) {
                    int min = Integer.MAX_VALUE;
                    for (int k = i; k <= j; k++) {
                        int cost = arrList.get(j + 1) - arrList.get(i - 1) + dp[i][k - 1] + dp[k + 1][j];
                        min = Math.min(cost, min);
                    }
                    dp[i][j] = min;
                }
            }
    
            return dp[1][c];
    
        }
    ```
    

## Burst balloons [ very unclear intuition- can’t be solved first time]

arr[] = 3, 1, 5, 8

if we burst a ballon we get left*i*right  

if i burst 5 i will get 1*5*8 = 40

for the left most balloon we take left as 1, similar for right

```java

pad the array for ease
1, 3, 1, 5, 8, 1

f(1, 4)
f(1, c)
f(i,j)
	if(i>j) return 0;
	max =int_min
	for(int k = i; k<=j; k++){
		cost = balloon[i-1]*balloon[k]*balloon[j+1] + f(i, k-1) + f(k+1,j)
		max = max(max,cost);
	}
	return max;

```

here we are saying balloon[k] is the last balloon i am bursting from range i to j, so it will depend on 1, 1 i.e the boundaries 

then the second last balloon i will burst will have the boundaries i-1 and k because we burst the kth balloon in the iteration

so f(i,k-1) gives me the best bursting max value of i to k-1 balloons and i know the boundaries as i-1 and k

if you think logically also the balloon which is last burst will only depend on the left and right boundary and will add the cost of itself only, similarly the second last will add the mult of itself and last, and so on

- more explaination if required
    
    The key to the “burst last” DP trick is that when you decide which balloon bursts last in a range, **everything else in that range has already been burst**.
    
    Here’s the intuition:
    
    ---
    
    ### Step 1 — What happens if I burst balloon *k* last in `[i..j]`?
    
    - At the very end, only three balloons remain relevant:
        - **`i-1`** (left boundary)
        - **`k`** (the balloon we’re bursting)
        - **`j+1`** (right boundary)
    - Why only those?
        
        Because every other balloon in `[i..j]` was burst earlier in the process, so they are gone when we finally burst *k*.
        
        That means when *k* bursts last, its **immediate neighbors** are the two balloons just outside the original interval:
        
        ```
        balloon[i-1]  k  balloon[j+1]
        
        ```
        
        and no one in between.
        
    
    ---
    
    ### Step 2 — Where do the “other” balloons go?
    
    We handle them in **subproblems**:
    
    - All balloons in `[i..k-1]` are burst *before* balloon *k*.
    - All balloons in `[k+1..j]` are also burst *before* balloon *k*.
    - But the exact order in those subranges doesn’t matter to *k’s* final coin count, because when *k* bursts, both sides are gone — only boundaries remain.
    
    So the total coins if *k* bursts last are:
    
    ```
    coins from bursting *k* last
    + coins from fully bursting [i..k-1]
    + coins from fully bursting [k+1..j]
    
    ```
    
    ---
    
    ### Step 3 — Why do the boundaries become `balloon[i-1]` and `balloon[j+1]`?
    
    Because **when *k* bursts last**, its left neighbor is the balloon just before the range (`i-1`) and its right neighbor is the balloon just after the range (`j+1`).
    
    Those are the only balloons left to multiply with `balloon[k]`.
    
    That’s why the formula is:
    
    ```
    balloon[i-1] * balloon[k] * balloon[j+1]
    + f(i, k-1)
    + f(k+1, j)
    
    ```
    
    ---
    
    If you want, I can draw a **visual timeline** showing how the surrounding balloons vanish and why only the immediate outer ones matter for the last burst. That usually makes it click instantly.
    
- code
    
    ```java
    class Solution {
         public int maxCoins(int[] balloons) {
            
            int[] arr = new int[balloons.length+2];
            int idx = 0;
            arr[idx] = 1;
            idx++;
            for(int j = 0; j < balloons.length; j++){
    	        arr[idx] = balloons[j];
    	        idx++;
            }
            arr[idx] = 1;
    
            int c = balloons.length;
    
            int[][] dp = new int[c + 2][c + 2]; // as we added two vals
    
            for (int i = c; i >= 1; i--) {
                for (int j = i; j <= c; j++) {
                    int max = Integer.MIN_VALUE;
                    for (int k = i; k <= j; k++) {
                        int cost = arr[i-1]*arr[k]*arr[j+1] + dp[i][k - 1] + dp[k + 1][j];
                        max = Math.max(cost, max);
                    }
                    dp[i][j] = max;
                }
            }
    
            return dp[1][c];
    
        }
    }
    ```
    

## Boolean Evaluation to true

expression = T|T&F

expressions | or, & and, ^ xor

in how many ways can we evaluate the expression to true

```java
f(0,n-1, 1) // n is the lenght of expression

f(i, j, 1) => we want the count of evaluations which makes the expression as true
	if(i>j) return 0;
	if(i==j) 
		if(isTrue == 1) return a[i] =='T'? 1:0;
		else return a[i] == 'F'?1:0;
	int ways = 0;
	for(int k = i+1; k <= j-1; k+=2){
		int lt  = f(i, k-1, 1); //leftTrue
		int lf = f(i, k-1, 0);
		int rt = f(k+1, j, 1);
		int rf = f(k+1,j, 0);
		if(a[k] == '&'){
			if(isTrue) ways = ways + rt * lt;
			else ways = ways + rt*lf + lf*rf + rf*lt;
		
		}
		
		else if(a[k] == '|'){
				if(isTrue) ways = ways + rt * lt + rt*lf + rf*lt;
				else ways = ways + lf*rf ;
		}
		
		else if(a[k] =='^'){
				if(isTrue) ways = ways  + rt*lf + rf*lt;
				else ways = ways + lf*rf + rt * lt;
		}
	
	}
	return ways
	
// this is memoized by 3 variable dp[n][n][2]

```

## Palindrome partition [Front partition] - usually simple pattern

String = bab abcba d c ede

how many minimal cuts to make all the substrings palindrome

in this case it is 4

![image.png](attachment:f213ff21-34b3-423c-b0e4-da7e0ffb27f3:image.png)

front partitioning 

→ check for partition then pass the remaining string to recursion

```java
f(i) - get min partitions from index i=0 -f(0)
	if(i==n) return 0;
	temp = "";
	for(int j = i; j < n; j++){ // go over string
		temp+=a[j];
		if(i to j is a palindrome- isPalindrome(temp)){
			min = Math.min(1+f(j+1), min);
		}
		
	}
	return min;
	
	// memoization dp[n]
	
	this code is returning min segments, so to get cuts subtract 1 from ans 
	or return 0 for n-1 too
```

- code
    
    ```java
    
    class Solution {
    boolean isPalindrome(String s){
    	    
    	    int i = 0; 
    	    int j = s.length()-1;
    	    while(i<j){
    		    if(s.charAt(i)!=s.charAt(j)) return false;
    		    i++;
    		    j--;
    	    }
    	    return true;
        
        }
        
        public int minCut(String s) {
    		int n = s.length();
            int[] dp = new int[n+1];
            
            
            dp[n] = 0;
            
            for(int i = n-1; i >=0; i--){
    	        StringBuilder sb = new StringBuilder();
    	        int min = Integer.MAX_VALUE;
    	        for(int j = i; j<n; j++){
    		        sb.append(s.charAt(j));
    		        if(isPalindrome(sb.toString())){
    			        min = Math.min( 1+dp[j+1], min);
    		        }
    	        
    	        }
    	        dp[i] = min;
            
            }
            
            return dp[0] - 1;
        }
    }
    
    O(N^2)
    ```
    

## Partition Array for Maximum Sum[Front partition]

arr[] = [ 1, 15, 7, 9, 2, 5, 10] - k = 3 → max partition will have 3 elements

three partitions

if we decide 1, 15 and 7 9 2 and 5 10

then elements will become 15 15 and 9 9 9 and 10 10 i.e the max

so we get the sum = 77

```java
try all ways
1. express in terms of index
2. try every partition possible from that index
3. take the best partition

f(i) => give me the max sum that you can make from 0 - f(0)
	if(i==n) return 0;
	
	len =0, max = intmin
	maxsum = intmin
	for(j = i, j< i+k && j < n; j++){
		len++;
		max = max(max, arr[j]);
		sum= len*max + f(j+1);
		maxsum = max(maxsum, sum);
	}
	return maxsum
	
	
	
```

- code
    
    ```java
        public int maxSumAfterPartitioning(int[] arr, int k) {
            int n = arr.length;
            int[] dp = new int[n + 1];
            dp[n] = 0;
    
            for (int i = n - 1; i >= 0; i--) {
                int len = 0;
                int max = Integer.MIN_VALUE;
                int maxsum = Integer.MIN_VALUE;
                for (int j = i; j < i + k && j < n; j++) {
                    len++;
                    max = Math.max(max, arr[j]);
                    int sum = len * max + dp[j + 1];
                    maxsum = Math.max(maxsum, sum);
                }
                dp[i] = maxsum;
            }
    
            return dp[0];
    
        }
    ```
    

# DP on squares

in general we try to write tabulation there are not many square problems only 2-3

## Maximum Rectangle Area with all 1’s

![image.png](attachment:6c44698a-bd3b-4a03-bf09-15b6c40c0a59:image.png)

- given a rectangle, find the max area with all 1’s continuous

O(n*(m+n))

- O(R × C) — optimal for this problem.
- Uses well-known **largest rectangle in histogram** solution as a subroutine.
- Scales well even for 200×200+ matrices.
- complexities
    - **Brute force (check every possible rectangle and scan its cells):**
        - Choose top-left `(r1,c1)` → O(R·C)
        - Choose bottom-right `(r2,c2)` → O(R·C)
        - Check all cells inside → up to O(R·C)
            
            → **Total = O(R²·C²·R·C) = O(R³·C³)**.
            
            If R ≈ C ≈ n → **O(n⁶)**. ✅
            
    - **Top-left-as-starting-point method (the one you suggested / I gave):**
        - Precompute `rightOnes` in O(R·C).
        - For each top-left `1` (≤ R·C), scan down up to R rows, O(1) per row → **O(R²·C)**.
            
            If R ≈ C ≈ n → **O(n³)**. ✅
            
    - **Histogram + stack (optimal common solution):**
        - Build heights per row O(R·C), compute largest-rectangle-in-histogram per row O(C) → **O(R·C)**.
            
            If R ≈ C ≈ n → **O(n²)**. ✅
            
    
    So the brute force is indeed **O(n⁶)** (when n = rows = cols). The top-left method is **O(n³)**, and the histogram+stack is **O(n²)**.
    
    Want the Java histogram+stack implementation now?
    
- code
    
    ```java
    class Solution {
        int getAreaOfRowStack(int[][] dp, int k){
        
    	    ArrayDeque<Integer> stack = new ArrayDeque();
    	    int n = dp[k].length;
    	    int[] heights = dp[k];
    	    
    	    int maxArea = 0;
    			
    			for(int j = 0; j <= n; j++){
    				int h = j==n? 0: heights[j];
    				
    				while(!stack.isEmpty() && h < heights[stack.peek()]){
    					int rectHeight = heights[stack.pop()];
    					int rectWidth = 0;
    					if(!stack.isEmpty()){
    						rectWidth = j-1 - stack.peek();
    					}
    					else{
    						rectWidth = j;
    					}
    					
    					int area = rectHeight * rectWidth;
    					maxArea = Math.max(area, maxArea);
    				
    				}
    				stack.push(j);
    			
    			}	  
    			// in the end stack will have all zeros  
        
    	    return maxArea;
        
        }
        
        
        
        public int maximalRectangle(char[][] matrix) {
    	       // we will go row wise and create a stack for each row if the prev row has a number we will add it with current if there is no zero
    	       // then we will find the area of stacks for each row and return the max one
    	       int m = matrix.length;
    	       int n = matrix[0].length;
    	       int[][] dp = new int[m][n];
    
               for(int i = 0; i < n; i++){
                    if(matrix[0][i] == '1') dp[0][i] = 1;
               }
    	       
    	       for(int i = 1; i < m; i++){
    		       for(int j = 0; j < n; j++){
    				       if(matrix[i][j] == '1'){
    					       dp[i][j] = dp[i-1][j] + 1;
    				       }
    				       else dp[i][j] = 0;
    		       }
    		     }
    		     int max = (int)-1e9;
    		     for(int i = 0; i < m; i++){
    			     max = Math.max(getAreaOfRowStack(dp, i), max);
    		     }
    		     
    		     return max;
    	     
        }
    }
    ```
    

## Count the no of square submatrixes with all 1’s

![image.png](attachment:3eed903c-d261-47e1-906f-89d38824797a:image.png)

- we want to know how much each one is forming how many squares if he is the bottom right

![image.png](attachment:9c0a0b77-09d5-4368-85e6-90082fba5f6a:image.png)

- we fill a dp table, first row and col, same as the given matrix and then we put the min of top, left and top-left add one to it, if the square is 1, else 0

if(arr[i][j] == 1 )dp[i][j] = min(dp[i-1][j-1], dp[i][j-1], dp[i-1][j]) + 1

else dp[i][j] = 0

- code
    
    ```java
    class Solution {
    int countSquares(int[][] matrix){
    	int m = matrix.length;
    	int n = matrix[0].length;
    	int[][] dp = new int[m][n];
    	int sum = 0;
    	
    	for(int i = 0; i < m; i++){
    		dp[i][0] = matrix[i][0];
            sum+=dp[i][0];
    	}
    	
    	for(int j = 1; j < n; j++){
    		dp[0][j] = matrix[0][j];
            sum+=dp[0][j];
    	}
    	
    	for(int i = 1; i < m; i++){
    		for(int j = 1; j < n; j++){
    			dp[i][j] = matrix[i][j]==0? 0: 1+Stream.of(dp[i-1][j-1], dp[i-1][j], dp[i][j-1]).min(Integer::compare).get();
    			sum+=dp[i][j];
    		}
    	}
    	
    	
    	return sum;
    
    }
    }
    ```