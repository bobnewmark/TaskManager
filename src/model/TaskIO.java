package model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskIO {

    //  записує задачі із списку у потік у бінарному форматі, описаному нижче
    public static void write(TaskList tasks, OutputStream out) throws IOException {
        try (DataOutputStream dataOut = new DataOutputStream(out)) {
            dataOut.writeInt(tasks.size());
            for (Task t : tasks) {
                dataOut.writeInt(t.getTitle().length());
                dataOut.writeUTF(t.getTitle());
                dataOut.writeBoolean(t.isActive());
                dataOut.writeInt(t.getRepeatInterval());
                if (t.isRepeated()) {
                    dataOut.writeLong(t.getStartTime().getTime());
                    dataOut.writeLong(t.getEndTime().getTime());
                } else {
                    dataOut.writeLong(t.getTime().getTime());
                }
            }
        }
    }


    //  зчитує задачі із потоку у даний список задач
    public static void read(TaskList tasks, InputStream in) throws Exception {
        try (DataInputStream dataIn = new DataInputStream(in)) {
            int size = dataIn.readInt();
            for (int i = 0; i < size; i++) {
                int length = dataIn.readInt();
                String title = dataIn.readUTF();
                boolean active = dataIn.readBoolean();
                int interval = dataIn.readInt();
                long timeOrStart = dataIn.readLong();
                if (interval > 0) {
                    long end = dataIn.readLong();
                    Task task = new Task(title, new Date(timeOrStart), new Date(end), interval);
                    task.setActive(active);
                    tasks.add(task);
                } else {
                    Task task = new Task(title, new Date(timeOrStart));
                    task.setActive(active);
                    tasks.add(task);
                }
            }
        }
    }

    //  записує задачі із списку у файл
    public static void writeBinary(TaskList tasks, File file) throws IOException {
        try (BufferedOutputStream dataOut = new BufferedOutputStream(new FileOutputStream(file))) {
            write(tasks, dataOut);
        }
    }

    //  зчитує задачі із файлу у список задач
    public static void readBinary(TaskList tasks, File file) throws Exception {
        try (BufferedInputStream dataIn = new BufferedInputStream(new FileInputStream(file))) {
            read(tasks, dataIn);
        }
    }


    //    записує задачі зі списку у потік в текстовому форматі, описаному нижче
    public static void write(TaskList tasks, Writer out) throws IOException {
        try (BufferedWriter dataOut = new BufferedWriter(out)) {
            int count = 0;
            for (Task task : tasks) {
                dataOut.append(task.toString());
                dataOut.append(count < tasks.size()-1 ? ";" : ".");
                dataOut.newLine();
                count++;
            }
        }
    }

    //  зчитує задачі із поток у список
    public static void read(TaskList tasks, Reader in) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("[y-MM-dd HH:mm:ss.S]");
        BufferedReader dataIn = new BufferedReader(in);
        String line = "";
        while ((line = dataIn.readLine()) != null) {
            int start = line.indexOf('\"');
            int finish = line.lastIndexOf('\"');
            String title = line.substring(start + 1, finish);
            boolean active = !line.contains("inactive");

            if (line.contains(" at [")) {
                String stringDate = line.substring(line.indexOf("["), line.indexOf("]") + 1);
                Date dateStart = dateFormat.parse(stringDate);
                Task task = new Task(title, new Date(dateStart.getTime()));
                task.setActive(active);
                tasks.add(task);
            } else {
                String stringStart = line.substring(line.indexOf("["), line.indexOf("]") + 1);
                Date dateStart = dateFormat.parse(stringStart);
                line = line.substring(line.indexOf("]") + 1);
                String stringEnd = line.substring(line.indexOf("["), line.indexOf("]") + 1);
                Date dateEnd = dateFormat.parse(stringEnd);
                line = line.substring(line.indexOf("]") + 1);
                String interval = line.substring(line.lastIndexOf("[", line.indexOf("[") + 1) + 1,
                        line.lastIndexOf("]", line.indexOf("]") + 1));
                String[] forInterval = interval.split(" ");
                long days;
                long hours;
                long minutes;
                long seconds;
                int theInterval = 0;

                switch (forInterval.length) {
                    case 4:
                        minutes = Long.parseLong(forInterval[0]);
                        seconds = Long.parseLong(forInterval[2]);
                        theInterval = (int) (seconds * 1000 + minutes * 60 * 1000) / 1000;
                        break;
                    case 6:
                        hours = Long.parseLong(forInterval[0]);
                        minutes = Long.parseLong(forInterval[2]);
                        seconds = Long.parseLong(forInterval[4]);
                        theInterval = (int) (seconds * 1000 + minutes * 60 * 1000 + hours * 3600 * 1000) / 1000;
                        break;
                    case 8:
                        days = Long.parseLong(forInterval[0]);
                        hours = Long.parseLong(forInterval[2]);
                        minutes = Long.parseLong(forInterval[4]);
                        seconds = Long.parseLong(forInterval[6]);
                        theInterval = (int) (seconds * 1000 + minutes * 60 * 1000 + hours * 3600 * 1000 + days * 8640 * 1000) / 1000;
                        break;
                }
                Task task = new Task(title, new Date(dateStart.getTime()), new Date(dateEnd.getTime()), theInterval);
                task.setActive(active);
                tasks.add(task);
            }
        }
    }

    //  записує задачі у файл у текстовому форматі
    public static void writeText(TaskList tasks, File file) throws IOException {
        try (PrintWriter dataOut = new PrintWriter(new BufferedWriter(new FileWriter(file)))) {
            write(tasks, dataOut);
        }
    }

    //    зчитує задачі із файлу у текстовому вигляді
    public static void readText(TaskList tasks, File file) throws Exception {
        try (BufferedReader dataIn = new BufferedReader(new FileReader(file))) {
            read(tasks, dataIn);
        }
    }
}