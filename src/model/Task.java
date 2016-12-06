package model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task implements Cloneable, Serializable {

    private String title;
    private Date time;
    private Date start;
    private Date end;
    private int interval;
    private boolean active;

    /* конструктор неактивной неповторяющейся задачи */
    public Task(String title, Date time) throws timeException {
        this.title = title;
        this.time = time;
        this.checkTime(time);
    }

    /* конструктор неактивной неповторяющейся задачи */
    public Task(String title, Date time, boolean active) throws Exception {
        this.title = title;
        this.time = time;
        this.checkTime(time);
        this.active = active;
    }

    /* конструктор неактивной повторяющейся задачи */
    public Task(String title, Date start, Date end, int interval) throws Exception {
        this.title = title;
        this.checkTime(start);
        this.start = start;
        this.checkTime(end);
        this.end = end;
        this.interval = interval;
        this.checkInterval(interval);
    }

    /* конструктор повторяющейся задачи  + boolean*/
    public Task(String title, Date start, Date end, int interval, boolean active) throws Exception {
        this.title = title;
        this.checkTime(start);
        this.start = start;
        this.checkTime(end);
        this.end = end;
        this.checkInterval(interval);
        this.interval = interval;
        this.active = active;
    }

    public Task(String title, Date time, Date start, Date end, int interval, boolean active) {
        this.title = title;
        this.time = time;
        this.start = start;
        this.end = end;
        this.interval = interval;
        this.active = active;
    }

    /* метод считывания названия задачи */
    public String getTitle() {
        return this.title;
    }

    /* метод изменения названия задачи */
    public void setTitle(String title) {
        this.title = title;
    }

    /* метод считывает состояние задачи */
    public boolean isActive() {
        return this.active;
    }

    /* метод устанавливает состояние задачи */
    public void setActive(boolean active) {
        this.active = active;
    }

    /* метод возвращает время повтора задачи, если она повторяется */
    public Date getTime() {
        if (this.interval != 0) {
            return this.start;
        } else {
            return this.time;
        }
    }

    /* метод отменяет повторяемость задачи, если она установлена */
    public void setTime(Date time) {
        if (this.interval != 0) {
            this.time = time;
            this.interval = 0;
            this.start = null;
            this.end = null;
        } else {
            this.time = time;
            this.start = null;
            this.end = null;
        }
    }

    /* метод возвращает время повторения задачи либо время выполнения */
    public Date getStartTime() {
        if (this.interval == 0) {
            return this.time;
        } else {
            return this.start;
        }
    }

    /* метод возвращает время завершения задачи либо время выполнения */
    public Date getEndTime() {
        if (this.interval == 0) {
            return this.time;
        } else {
            return this.end;
        }
    }
    public void setEndTime(Date time) {
        this.end = time;
    }
    public void setStartTime(Date time) {
        this.start = time;
    }
    public void setInterval(int time) {
        this.interval = time;
    }

    /* метод возвращает период выполнения задачи или 0 */
    public int getRepeatInterval() {
        if (this.interval != 0) {
            return this.interval;
        } else {
            return 0;
        }
    }

    /* метод делает задачу повторяющейся, если она таковой не была */
    public void setTime(Date start, Date end, int interval) {
        if (this.interval == 0) {
            this.start = start;
            this.end = end;
            this.interval = interval;
        }
    }

    /* метод проверяет, повторяется ли задача */
    public boolean isRepeated() {
        return this.interval != 0;
    }

    /* задание 2, метод возвращает время выполнения задачи после указанного */
    public Date nextTimeAfter(Date current) {
        if (current == null) throw new IllegalArgumentException("Cannot run nextTimeAfter with NULL argument");
        if (!active) return null;
        if (!isRepeated() && isActive() && getTime().after(current)) return getTime();
        if (isRepeated() && isActive() && getEndTime().after(current)) {
            Date temp = (Date) getStartTime().clone();
            while(temp.compareTo(current) <= 0) {
                temp.setTime(temp.getTime() + getRepeatInterval()*1000);
                if (temp.after(end)) return null;
            } return temp;
        } return null;
    }

    public class timeException extends Exception {
        public timeException(String message) {
            super(message);
        }
    }

    public void checkTime(Date someTime) throws timeException {
        if(someTime.before(new Date(0))) throw new timeException("Time cannot be of negative value");
    }

    public class intervalException extends Exception {
        public intervalException(String message) {
            super(message);
        }
    }

    public void checkInterval(int interval) throws intervalException {
        if((interval <= 0) && (start == null))
            throw new intervalException("Repeating interval cannot be of negative value or zero");
    }

    @Override
    protected Task clone() throws CloneNotSupportedException {
        Task copy = (Task) super.clone();
        return copy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (interval != task.interval) return false;
        if (active != task.active) return false;
        if (title != null ? !title.equals(task.title) : task.title != null) return false;
        if (time != null ? !time.equals(task.time) : task.time != null) return false;
        if (start != null ? !start.equals(task.start) : task.start != null) return false;
        return end != null ? end.equals(task.end) : task.end == null;
    }

    @Override
    public int hashCode() {
        int result = title != null ? title.hashCode() : 0;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (end != null ? end.hashCode() : 0);
        result = 31 * result + interval;
        result = 31 * result + (active ? 1 : 0);
        return result;
    }


    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("[y-MM-dd HH:mm:ss.S]");
        String ifNotActive = "";
        if (!isActive()) ifNotActive = " inactive";
        if(isRepeated()) {
            return "\"" + title + "\" from " + dateFormat.format(getStartTime()) + " to "
                    + dateFormat.format(getEndTime()) + " every [" + interString(getRepeatInterval()) + "]" + ifNotActive;
        } else {
            return "\"" + title + "\" at " + dateFormat.format(getTime()) + ifNotActive;
        }
    }

    public String interString(int inter) {
        Date date = new Date((long) inter * 1000);

        long milisecs = date.getTime();
        long days = milisecs / 86400000;
        long hours = (milisecs % 86400000) / 3600000;
        long mins = ((milisecs % 86400000) % 3600000) / 60000;
        long secs = (((milisecs % 86400000) % 3600000) % 60000) / 1000;

        String d = days == 1 ? " day " : " days ";
        String h = hours == 1 ? " hour " : " hours ";
        String m = mins == 1 ? " minute " : " minutes ";
        String s = secs == 1 ? " second" : " seconds";

        return days + d + hours + h + mins + m + secs + s;
    }
}
