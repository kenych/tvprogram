package exercises;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Solution {
    public int solution(String S) {
        String[] logs = S.split("\n");

        LongestCall longestCall = null;

        Map<String, List<Time>> logMap = new HashMap<>();


        for (String log : logs) {
            String[] parts = log.split(",");
            String timePart = parts[0];
            String number = parts[1];

            String[] timeParts = timePart.split(":");
            Time time = new Time(Integer.parseInt(timeParts[0]), Integer.parseInt(timeParts[1]), Integer.parseInt(timeParts[2]));

            longestCall = setLongestCall(longestCall, number, time);

            List<Time> times = logMap.get(number);
            if (times == null) {
                ArrayList<Time> timeForNumber = new ArrayList<>();
                timeForNumber.add(time);
                logMap.put(number, timeForNumber);
            } else {
                times.add(time);
            }


        }

        int total = 0;
        for (String number : logMap.keySet()) {
            if (number != longestCall.number) {

                List<Time> times = logMap.get(number);
                for (Time time : times) {
                    if (time.toMinutes() < 5) {
                        int calculated = time.toSeconds() * 3;

                        total += calculated;
                    } else {
                        int calculated = time.toMinutes() * 150;
                        total += calculated;
                    }
                }
            }
        }


        return total;
    }

    private LongestCall setLongestCall(LongestCall longestCall, String number, Time time) {
        if (longestCall == null) {
            longestCall = new LongestCall(number, time.toSeconds());
        } else if (number != longestCall.number) {
            if (time.toSeconds() > longestCall.callDuration) {
                longestCall.callDuration = time.toSeconds();
                longestCall.number = number;
            } else if (time.toSeconds() == longestCall.callDuration) {
                if (asNumber(number) < asNumber(longestCall.number)) {
                    longestCall.callDuration = time.toSeconds();
                    longestCall.number = number;
                }
            }
        }
        return longestCall;
    }

    int asNumber(String phoneNumber) {
        return Integer.parseInt(phoneNumber.replaceAll("-", ""));

    }

    public static void main(String[] args) {
        String s = "00:01:07,400-234-090\n00:05:01,701-080-080\n00:05:00,400-234-090";

        int solution = new Solution().solution(s);

        System.out.println(solution);

    }

    class LongestCall {
        String number;
        int callDuration;

        LongestCall(String number, int callDuration) {
            this.number = number;
            this.callDuration = callDuration;
        }

        @Override
        public String toString() {
            return "LongestCall{" +
                    "number='" + number + '\'' +
                    ", callDuration=" + callDuration +
                    '}';
        }
    }

    class Time {
        int hour;
        int min;
        int sec;

        Time(int hour, int min, int sec) {
            this.hour = hour;
            this.min = min;
            this.sec = sec;
        }

        int toSeconds() {
            return sec + min * 60 + hour * 60 * 60;
        }

        int toMinutes() {
            return hour * 60 + min + (sec > 0 ? 1 : 0);
        }

        @Override
        public String toString() {
            return "Time{" +
                    "hour=" + hour +
                    ", min=" + min +
                    ", sec=" + sec +
                    '}';
        }

    }

}
