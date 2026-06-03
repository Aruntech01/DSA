class Solution {

    private static final int RANGE = 50000;
    private int[] maxSegment;

    private void modify(int node, int left, int right, int pos, int value) {
        if (left == right) {
            maxSegment[node] = value;
            return;
        }

        int middle = left + (right - left) / 2;

        if (pos <= middle) {
            modify(node * 2, left, middle, pos, value);
        } else {
            modify(node * 2 + 1, middle + 1, right, pos, value);
        }

        maxSegment[node] = Math.max(
            maxSegment[node * 2],
            maxSegment[node * 2 + 1]
        );
    }

    private int findMax(int node, int left, int right, int from, int to) {

        if (to < left || from > right) {
            return 0;
        }

        if (from <= left && right <= to) {
            return maxSegment[node];
        }

        int middle = left + (right - left) / 2;

        int bestLeft = findMax(node * 2, left, middle, from, to);
        int bestRight = findMax(node * 2 + 1, middle + 1, right, from, to);

        return Math.max(bestLeft, bestRight);
    }

    public List<Boolean> getResults(int[][] queries) {

        maxSegment = new int[4 * (RANGE + 1)];

        TreeSet<Integer> walls = new TreeSet<>();
        walls.add(0);

        for (int[] current : queries) {
            if (current[0] == 1) {
                walls.add(current[1]);
            }
        }

        List<Integer> sortedWalls = new ArrayList<>(walls);

        for (int i = 1; i < sortedWalls.size(); i++) {
            int currentWall = sortedWalls.get(i);
            int previousWall = sortedWalls.get(i - 1);

            modify(
                1,
                0,
                RANGE,
                currentWall,
                currentWall - previousWall
            );
        }

        List<Boolean> answer = new ArrayList<>();

        for (int i = queries.length - 1; i >= 0; i--) {

            int type = queries[i][0];

            if (type == 2) {

                int position = queries[i][1];
                int requiredLength = queries[i][2];

                int nearestWall = walls.floor(position);

                int longestGap =
                    findMax(1, 0, RANGE, 0, nearestWall);

                longestGap = Math.max(
                    longestGap,
                    position - nearestWall
                );

                answer.add(longestGap >= requiredLength);

            } else {

                int position = queries[i][1];

                Integer before = walls.lower(position);
                Integer after = walls.higher(position);

                modify(1, 0, RANGE, position, 0);

                if (after != null) {
                    modify(
                        1,
                        0,
                        RANGE,
                        after,
                        after - before
                    );
                }

                walls.remove(position);
            }
        }

        Collections.reverse(answer);
        return answer;
    }
}