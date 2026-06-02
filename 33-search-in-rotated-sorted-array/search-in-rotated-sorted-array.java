class Solution {
    public int search(int[] nums, int target) {
        int n = nums.length;
        int low = 0, high = n - 1;

        while (low < high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] <= nums[high]) {
                high = mid;
            } else {
                low = mid + 1;
            }
        }

        int pivot = low;
        low = 0;
        high = n - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;
            int idx = (pivot + mid) % n;

            if (nums[idx] == target) {
                return idx;
            }

            if (nums[idx] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return -1;
    }
}