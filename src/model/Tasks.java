package model;

import java.util.*;

public class Tasks {


    // метод возвращает подмножество задач хотя бы раз в промежутке от from to
    public static Iterable<Task> incoming(Iterable<Task> taskList, Date from, Date to) throws CloneNotSupportedException {
        ArrayTaskList temp = new ArrayTaskList();

        for (Task task : taskList) {
            Date date = task.nextTimeAfter(from);
            if (date != null && date.compareTo(to) <= 0) temp.add(task);
        }
        return temp;
    }

    // календар задач на заданий період
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) throws CloneNotSupportedException {
        TreeMap<Date, Set<Task>> treeMap = new TreeMap<>();
        ArrayTaskList tempList = (ArrayTaskList) incoming(tasks, start, end);

        for (Task task : tempList) {
            Date date = task.nextTimeAfter(start);
            while (!date.after(end)) {
                if (treeMap.containsKey(date)) {
                    treeMap.get(date).add(task);
                } else {
                    HashSet<Task> innerSet = new HashSet<>();
                    innerSet.add(task);
                    treeMap.put(date, innerSet);
                }
                if (!task.isRepeated()) {
                    break;
                }
                if (task.nextTimeAfter(date) != null) {
                    date = task.nextTimeAfter(date);
                } else {
                    break;
                }
            }
        }
        return treeMap;
    }
}
