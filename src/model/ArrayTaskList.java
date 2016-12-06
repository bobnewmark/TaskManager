package model;

import java.util.Iterator;

public class ArrayTaskList extends TaskList {
    private Task[] arrayTaskList = new Task[10];
    private int size;

    // метод, добавляющий задачу в список
    @Override
    public void add(Task task) {
        if (this.arrayTaskList.length != this.size()) {
            this.arrayTaskList[this.size()] = task;
        } else {
            int num = arrayTaskList.length;
            int newCapacity = (int) (num * 1.2);
            Task[] temp = new Task[newCapacity];
            System.arraycopy(this.arrayTaskList, 0, temp, 0, this.size());
            this.arrayTaskList = temp;
            this.arrayTaskList[this.size()] = task;
        }
        this.size++;
    }


     /*метод удаляет задачу и возвращает истину, если такая задача в списке
     была. Если задач несколько, удаляется любая*/

    @Override
    public boolean remove(Task task) {
        if (task == null) throw new NullPointerException();
        for (int i = 0; i < size; i++) {
            if (arrayTaskList[i] != null && task.equals(arrayTaskList[i])) {
                int num = size - i - 1;
                if (num > 0)
                    System.arraycopy(arrayTaskList, i + 1, arrayTaskList, i, num);
                arrayTaskList[--size] = null;
                return true;
            }
        }
        return false;
    }


    // метод возвращает кол-во задач в списке
    public int size() {
        return size;
    }

    // метод возвращает задачу с указанного места в списке
    @Override
    public Task getTask(int index) {
        return this.arrayTaskList[index];
    }

    // итератор
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {
            int marker = 0;

            @Override
            public boolean hasNext() { return size() > marker; }

            @Override
            public Task next() {
                if(!hasNext()) throw new IllegalStateException();
                return getTask(marker++);
            }

            @Override
            public void remove() {
                if(marker == 0) throw new IllegalStateException();
                ArrayTaskList.this.remove(arrayTaskList[--marker]);
            }
        };
    }

    // клонирование без конструктора класса
    @Override
    public ArrayTaskList clone(){
        ArrayTaskList out = new ArrayTaskList();
        if(size() > 0)
            for (Task task : this)
                out.add(task);
        return out;
    }
}