package model;

import java.io.Serializable;
import java.util.Iterator;

public abstract class TaskList implements Iterable<Task>, Cloneable, Serializable {

    int size;

    // метод, добавляющий задачу в список
    public abstract void add(Task task);

    /*метод удаляет задачу и возвращает истину, если такая задача в списке
    была. Если задач несколько, удаляется любая*/
    public abstract boolean remove(Task task);

    // метод возвращает кол-во задач в списке
    public int size() { return size; }

    // метод возвращает задачу с указанного места в списке
    public abstract Task getTask(int index);

    // переопределенный метод toString
    @Override
    public String toString() {
        String string = "List: " + this.getClass().getSimpleName() + ", size: " + size() + ".\n";
        int count = 0;
        while (size() > count) {
            string += "Task " + count + ": " + getTask(count).getTitle() + "; status: " + getTask(count).isActive() + ".\n";
            count++;
        }
        return string;
    }

    // переопределенный метод сравнения двух списков
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;

        TaskList other = (TaskList) obj;

        if (this.size() != other.size()) return false;

        Iterator<Task> it = this.iterator();

        for (Task otherTask :other) {
            if (! (it.next().equals(otherTask))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = 1;
        for (int i = 0; i < size(); i++) {
            result = 31 * result + getTask(i).hashCode();
        }
        return result;
    }
}