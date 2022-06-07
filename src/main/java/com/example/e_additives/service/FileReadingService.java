package com.example.e_additives.service;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Класс-сервис для расшифровки файла с пищевыми добавками.
 */
@Service
public class FileReadingService {

    /**
     * Метод, определяющий как файл будет считываться в зависимости от расширения.
     * @param file файл, отправленный на расшифровку.
     * @throws IOException сигнализирует о возможном исключении ввода-вывода.
     * @return возвращает список индексов, найденных в файле.
     */
    public List<String> readFile(MultipartFile file) throws IOException {
        List<String> indexesList = new ArrayList<>();
        Optional<String> optionalFileName = Optional.ofNullable(file.getOriginalFilename());
        if (optionalFileName.isPresent()){
            switch (optionalFileName.get().substring(optionalFileName.get().lastIndexOf(".") + 1)){
                case ("txt"):
                    indexesList.addAll(readTxt(file.getInputStream()));
                    break;
                case ("pdf"):
                    indexesList.addAll(readPdf(file.getInputStream()));
                    break;
                case ("docx"):
                    indexesList.addAll(readDocx(file.getInputStream()));
                    break;
                default:
                    break;
            }
        }
        file.getInputStream().close();
        return indexesList;
    }

    /**
     * Метод, считывающий файлы txt-формата.
     * @param inputStream входной поток байтов.
     * @throws IOException сигнализирует о возможном исключении ввода-вывода.
     * @return возвращает список индексов, найденных в файле txt.
     */
    private Set<String> readTxt(InputStream inputStream) throws IOException {
        Set<String> list = new HashSet<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        while ((line = bufferedReader.readLine()) != null){
            list.addAll(readIndexes(line));
        }
        bufferedReader.close();
        return list;
    }

    /**
     * Метод, считывающий файлы pdf-формата.
     * @param inputStream входной поток байтов.
     * @throws IOException сигнализирует о возможном исключении ввода-вывода.
     * @return возвращает список индексов, найденных в файле pdf.
     */
    private Set<String> readPdf(InputStream inputStream) throws IOException {
        Set<String> list = new HashSet<>();
        PdfReader pdfReader = new PdfReader(inputStream);
        for (int i = 1; i <= pdfReader.getNumberOfPages(); i++){
            list.addAll(readIndexes(PdfTextExtractor.getTextFromPage(pdfReader,i)));
        }
        pdfReader.close();
        return list;
    }

    /**
     * Метод, считывающий файлы docx-формата.
     * @param inputStream входной поток байтов.
     * @throws IOException сигнализирует о возможном исключении ввода-вывода.
     * @return возвращает список индексов, найденных в файле docx.
     */
    private Set<String> readDocx(InputStream inputStream) throws IOException {
        XWPFWordExtractor extractor = new XWPFWordExtractor(new XWPFDocument(inputStream));
        Set<String> list = new HashSet<>(readIndexes(extractor.getText()));
        extractor.close();
        return list;
    }

    /**
     * Метод, считывающий индексы из текста файла.
     * @param line строка с информацией из файла.
     * @return возвращает список индексов, найденных в строке.
     */
    private Set<String> readIndexes(String line){

        StringBuilder stringBuilder = new StringBuilder();
        Set<String> list = new HashSet<>();
        boolean isIndexStart = false;

        for (char c: line.toCharArray()){
            if( (c == 'E' || c == 'Е') && !isIndexStart){
                isIndexStart = true;
                stringBuilder.append('E');
            }
            else if(isSeparatingSymbol(c) && isIndexStart){
                isIndexStart = false;
                list.add(stringBuilder.toString());
                stringBuilder.setLength(0);
            }
            else if(isIndexStart){
                stringBuilder.append(c);
            }
        }
        if (isIndexStart){
            list.add(stringBuilder.toString());
            stringBuilder.setLength(0);
        }
        return list;
    }

    /**
     * Метод, который проверяет: является ли символ "разделяющим".
     * @param symbol проверяемый символ.
     * @return возвращает true - если символ "разделяющий", false - символ "не разделяющий".
     */
    private boolean isSeparatingSymbol(char symbol){
        return symbol == ' '
                || symbol == ','
                || symbol == ';'
                || symbol == '\n'
                || symbol == '\r'
                || symbol == '.'
                || symbol == '!'
                || symbol == '?'
                || symbol == ':'
                || symbol == '('
                || symbol == ')';
    }
}
