package com.aimiko.task6.threads;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileSimpleNumbersThread extends Thread{
    private File fileName ;
    private List<Integer> simpleNumbersList;

    public FileSimpleNumbersThread(File fileName) {
        this.fileName = fileName;
        this.simpleNumbersList = new ArrayList<>();
    }

    @Override
    public void run(){
        try {
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(fileName)));

            for (String line : lines) {
                String[] numbers = line.split("\\s+");

                for (String numberStr : numbers) {
                    try {
                        int number = Integer.parseInt(numberStr);

                        if (isPrime(number)) {
                            simpleNumbersList.add(number);
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Помилка при перетворенні рядка в число: " + e.getMessage());
                    }
                }
            }
            System.out.println("Прості числа з файлу " + fileName + ": " + simpleNumbersList);

            writeSimpleNumbersToFile("simple-numbers.txt", simpleNumbersList);
        } catch (IOException e) {
            System.err.println("Виникла помилка при зчитуванні файлу: " + e.getMessage());
        }
    }

    private void writeSimpleNumbersToFile(String fileName, List<Integer> numbers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Integer number : numbers) {
                writer.write(number + " ");
            }
            writer.flush();
            System.out.println("Прості числа було успішно записано у файл: " + fileName);
        } catch (IOException e) {
            System.err.println("Виникла помилка при записі простих чисел до файлу: " + e.getMessage());
        }
    }

    private boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
