class Solution {
    public long countMajoritySubarrays(int[] nums, int target) {
        int n = nums.length;
        
        // The prefix sum can range from -n to +n. 
        // We add an offset of (n + 1) to keep tree indices positive (1 to 2n + 1).
        int offset = n + 1;
        FenwickTree bit = new FenwickTree(2 * n + 2);
        
        long count = 0;
        int currentPrefixSum = 0;
        
        // Base case: Before processing any element, the prefix sum is 0.
        bit.update(0 + offset, 1);
        
        for (int num : nums) {
            // Transform to +1 or -1
            currentPrefixSum += (num == target) ? 1 : -1;
            
            // We need to count all previous prefix sums that are strictly less than currentPrefixSum
            // In the Fenwick tree, this means querying from 1 up to (currentPrefixSum + offset - 1)
            count += bit.query(currentPrefixSum + offset - 1);
            
            // Add the current prefix sum to the Fenwick tree
            bit.update(currentPrefixSum + offset, 1);
        }
        
        return count;
    }
}

// Helper Binary Indexed Tree (Fenwick Tree) structure
class FenwickTree {
    private int[] tree;
    private int size;

    public FenwickTree(int size) {
        this.size = size;
        this.tree = new int[size];
    }

    public void update(int index, int delta) {
        while (index < size) {
            tree[index] += delta;
            index += index & (-index);
        }
    }

    public int query(int index) {
        int sum = 0;
        while (index > 0) {
            sum += tree[index];
            index -= index & (-index);
        }
        return sum;
    }
}