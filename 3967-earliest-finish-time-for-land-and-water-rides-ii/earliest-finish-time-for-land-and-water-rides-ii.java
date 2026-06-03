class Solution {
    public int earliestFinishTime(int[] landStart, int[] landTime,
                                  int[] waterStart, int[] waterTime) {

        int fastestLandRide = Integer.MAX_VALUE;
        int fastestWaterRide = Integer.MAX_VALUE;

        for (int i = 0; i < landStart.length; i++) {
            int finish = landStart[i] + landTime[i];
            if (finish < fastestLandRide) {
                fastestLandRide = finish;
            }
        }

        for (int i = 0; i < waterStart.length; i++) {
            int finish = waterStart[i] + waterTime[i];
            if (finish < fastestWaterRide) {
                fastestWaterRide = finish;
            }
        }

        int answer = Integer.MAX_VALUE;

        for (int i = 0; i < landStart.length; i++) {
            int startTime = Math.max(landStart[i], fastestWaterRide);
            answer = Math.min(answer, startTime + landTime[i]);
        }

        for (int i = 0; i < waterStart.length; i++) {
            int startTime = Math.max(waterStart[i], fastestLandRide);
            answer = Math.min(answer, startTime + waterTime[i]);
        }

        return answer;
    }
}