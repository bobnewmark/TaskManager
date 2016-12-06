package model;

import java.util.Iterator;

public class LinkedTaskList extends TaskList {

    private class LinkedCell {
        private LinkedCell prevCell;
        private LinkedCell nextCell;
        private Task task;

        public LinkedCell(LinkedCell prevCell, LinkedCell nextCell, Task task) {
            this.prevCell = prevCell;
            this.nextCell = nextCell;
            this.task = task;
        }

        public LinkedCell() {
        }
    }

    private LinkedCell firstCell;
    private LinkedCell lastCell;
    private int size = 0;

    // метод, добавляющий задачу в список
    @Override
    public void add(Task task) {
        if (task == null) return;
        if (firstCell == null) {
            firstCell = new LinkedCell();
            firstCell.task = task;
            lastCell = firstCell;
        } else {
            lastCell.nextCell = new LinkedCell();
            lastCell.nextCell.prevCell = lastCell;
            lastCell = lastCell.nextCell;
            lastCell.task = task;
        }
        size++;
    }

    // метод возвращает кол-во задач в списке
    @Override
    public int size() {
        return size;
    }

    /*метод удаляет задачу и возвращает истину, если такая задача в списке
     была. Если задач несколько, удаляется любая*/
    @Override
    public boolean remove(Task task) {
        if (task == null) {
            throw new NullPointerException("Task should be defined to delete.");
        }
        LinkedCell iter = firstCell;
        for (int i = 0; i < size; i++) {
            if (iter.task.equals(task)) {
                if (!LinkedTaskList.this.iterator().hasNext()) throw new IllegalStateException();
                if (iter.prevCell == null) {
                    firstCell = iter.nextCell;
                } else {
                    iter.prevCell.nextCell = iter.nextCell;
                }
                if (iter.nextCell == null) {
                    lastCell = iter.prevCell;
                } else {
                    iter.nextCell.prevCell = iter.prevCell;
                }
                size--;
                return true;
            }
            iter = iter.nextCell;
        }
        return false;
    }

    // метод возвращает задачу с указанного места в списке
    public Task getTask(int index) {
        if (index < 0 || index > size) throw new IndexOutOfBoundsException();
        int i = 0;
        LinkedCell currCell = firstCell;
        while (i < index) {
            currCell = currCell.nextCell;
            i++;
        }
        return currCell.task;
    }

    // итератор
    @Override
    public Iterator<Task> iterator() {
        return new Iterator<Task>() {

            private LinkedCell marker = firstCell;

            public boolean hasNext() {
                return marker != null;
            }

            public Task next() {
                Task task = marker.task;
                marker = marker.nextCell;
                return task;
            }

            public void remove() {
                if (marker.prevCell == null) throw new IllegalStateException();
                LinkedTaskList.this.remove(marker.prevCell.task);
            }
        };
    }

    // клонирование без конструктора класса
    @Override
    public LinkedTaskList clone(){
        LinkedTaskList out = new LinkedTaskList();
        if(size() > 0)
            for (Task task : this)
                out.add(task);
        return out;
    }
}