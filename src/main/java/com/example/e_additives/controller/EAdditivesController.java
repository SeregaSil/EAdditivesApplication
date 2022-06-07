package com.example.e_additives.controller;

import com.example.e_additives.entity.EAdditive;
import com.example.e_additives.service.EAdditiveService;
import com.example.e_additives.service.EmailSenderService;
import com.example.e_additives.service.FileReadingService;
import com.example.e_additives.view.ExportDataToPdf;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Класс-контроллер.
 * Используется для обработки входящих запросов.
 */
@Controller
public class EAdditivesController {

    private final EAdditiveService eAdditiveService;

    private final EmailSenderService emailSenderService;

    private final ExportDataToPdf exportDataToPdf;

    private final FileReadingService fileReadingService;

    /**
     * Конструктор.
     * @param eAdditiveService сервис для вывода информации о пищевых добавках {@link EAdditiveService}.
     * @param emailSenderService сервис для отправки сообщения {@link EmailSenderService}.
     * @param exportDataToPdf класс для представления информации в виде файла формата pdf {@link ExportDataToPdf}.
     * @param fileReadingService сервис для считывания информации из загруженного файла {@link FileReadingService}.
     */
    @Autowired
    public EAdditivesController(EAdditiveService eAdditiveService, EmailSenderService emailSenderService, ExportDataToPdf exportDataToPdf, FileReadingService fileReadingService) {
        this.eAdditiveService = eAdditiveService;
        this.emailSenderService = emailSenderService;
        this.exportDataToPdf = exportDataToPdf;
        this.fileReadingService = fileReadingService;
    }

    /**
     * Метод для вывода начальной страницы.
     * @param model модель для вывода информации.
     * @return начальная html-страница.
     */
    @GetMapping()
    public String index(Model model){
        model.addAttribute("eAdditivesAndColumName", eAdditiveService.getAllAdditives());
        return "views/home/index";
    }
    /**
     * Метод вывода страницы с расшифровкой пищевых добавок, выбранных с помощью checkbox.
     * @param indexes индексы выбранных пищевых добавок.
     * @param model модель для вывода информации.
     * @return html-страница с информацией о пищевых добавках.
     */
    @PostMapping("/decoding")
    public String decoding(@RequestParam(value = "index") List<String> indexes, Model model){
        model.addAttribute("eAdditivesAndTableName", eAdditiveService.getSelectedAdditivesByIndexes(indexes));
        return "views/home/decoding";
    }

    /**
     * Метод для обработки запроса на отправку сообщения об ошибке.
     * @param message текст сообщения.
     * @return переадресация на начальную html-страницу.
     */
    @PostMapping()
    public String sendSimpleEmail(@RequestParam(value = "message") String message) {
        emailSenderService.sendEmail(message);
        return "redirect:/";
    }

    /**
     * Метод для обработки запроса преобразования информации о пищевых добавках в файл формата pdf.
     * @param indexes индексы выбранных пищевых добавок.
     * @param response HTTP-ответ сервера.
     * @throws DocumentException сигнализирует о том, что произошла ошибка в Document, создающийся в методе {@link ExportDataToPdf#export(HttpServletResponse, Map)}.
     * @throws IOException сигнализирует о том, что произошло какое-либо исключение ввода-вывода.
     */
    @PostMapping("/pdf")
    public void exportToPDF(@RequestParam(value = "index") List<String> indexes,
                            HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=EAdditives.pdf";
        response.setHeader(headerKey, headerValue);

        exportDataToPdf.export(response, eAdditiveService.getSelectedAdditivesByIndexes(indexes));
    }

    /**
     * Метод для обработки запроса считывания информации из файла с последующей расшифровкой найденных пищевых добавок.
     * @param file файл, загруженный пользователем.
     * @param model модель для вывода информации.
     * @throws IOException сигнализирует о том, что произошло какое-либо исключение ввода-вывода.
     * @return html-страница с информацией о найденных в файле пищевых добавок
     * или html-страница с сообщением об ошибке.
     */
    @PostMapping("/file_decoding")
    public String uploadFile(@RequestParam("buttonFile")MultipartFile file, Model model) throws IOException {
        Map<String,List<EAdditive>> map = eAdditiveService.getSelectedAdditivesByIndexes(fileReadingService.readFile(file));
        if (map.isEmpty()){
            return "views/home/error";
        }
        else {
            model.addAttribute("eAdditivesAndTableName", map);
            return "views/home/decoding";
        }
    }
}
