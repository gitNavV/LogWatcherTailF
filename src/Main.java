import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        List<String> logs = fetchLastLogs(10);
        for (String log : logs) System.out.println(log);
        String lastLog = logs.get(logs.size() - 1);
        while (true) {
            int numLines = 1;
            int flag = 1;
            while (flag == 1) {
                List<String> logLines = fetchLastLogs(numLines);
                if (logLines.size() > 0 && !Objects.equals(logLines.get(0), lastLog)) numLines++;
                else {
                    if (logLines.size() > 0) {
                        flag = 0;
                        for (int i = 1; i < logLines.size(); i++) System.out.println(logLines.get(i));
                        lastLog = logLines.get(logLines.size() - 1);
                    }
                }
            }
        }
    }

    public static List<String> fetchLastLogs(int noOfLines) {
        try {
            RandomAccessFile file = new RandomAccessFile(new File("/Users/navneet.das/log.txt"), "r");
            long size = file.length();
            int bytes = 1024 * 32;
            List<String> logLines = new ArrayList<>();
            int flag = 1;
            while (flag == 1) {
                byte[] part = new byte[bytes];
                long start = size - bytes;
                long length = bytes;
                if (start < 0) {
                    length = bytes + start;
                    start = 0;
                }
                file.seek(start);
                long readLength = file.read(part, 0, (int)length);
                if (readLength <= 0) break;
                int rangeEnd = (int)readLength;
                for (int j = rangeEnd - 1; j >= 0; j--) {
                    if (part[j] == '\n') {
                        int startLine = j + 1;
                        int span = rangeEnd - startLine;
                        if (span > 0) logLines.add(new String(part, startLine, span));
                        rangeEnd = startLine;
                    }
                }
                size -= bytes + readLength;
                flag = logLines.size() < noOfLines && start != 0 ? 1 : 0;
            }
            if (logLines.size() < noOfLines) noOfLines = logLines.size();
            List<String> finalLogs = new ArrayList<>();
            for (int i = noOfLines - 1; i >= 0; i--) finalLogs.add(logLines.get(i));
            return finalLogs;
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}