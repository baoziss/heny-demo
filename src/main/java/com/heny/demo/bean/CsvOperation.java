package com.heny.demo.bean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CsvOperation implements Operation {
    @Override
    public void execute(String url) {
        List<String[]> points = readCSV(url);
        for (String[] point : points) {

        }
    }

    public static List<String[]> readCSV(String filePath) {
        List<String[]> dataList;
        try {
            Path path = Paths.get(filePath);
            dataList = Files.lines(path)
                    .map(line -> line.split(","))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
            dataList = null;
        }
        return dataList;
    }

    public static void main(String[] args) {
        List<String[]> csvData = readCSV("I:\\code\\1\\heny\\heny-demo\\file\\J1-J8.CSV");
        for (String[] data : csvData) {
            // 处理CSV数据
            System.out.println(data[1]);
        }
    }
}
