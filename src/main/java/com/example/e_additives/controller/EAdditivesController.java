package com.example.e_additives.controller;

import com.example.e_additives.services.EAdditivesService;
import com.example.e_additives.services.EmailSenderService;
import com.example.e_additives.view.ExportDataToPdf;
import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@Controller
public class EAdditivesController {

    @Autowired
    private EAdditivesService eAdditivesService;

    @Autowired
    private EmailSenderService senderService;

    @Autowired
    private ExportDataToPdf exportDataToPdf;

    @GetMapping()
    public String index(Model model){
        model.addAttribute("eAdditivesAndColumName", eAdditivesService.getAllAdditives());
        return "views/home/index";
    }

    @PostMapping("/decoding")
    public String decoding(@RequestParam(value = "index") List<String> indexes, Model model){
        model.addAttribute("eAdditivesAndTableName", eAdditivesService.getSelectedAdditivesByIndexes(indexes));
        return "views/home/decoding";
    }

    @PostMapping()
    public String sendSimpleEmail(@RequestParam(value = "message") String message) {
        senderService.sendEmail(message);
        return "redirect:/";
    }

    @PostMapping("/pdf")
    public void exportToPDF(@RequestParam(value = "index") List<String> indexes,
                            HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition";
        String headerValue = "inline; filename=EAdditives.pdf";
        response.setHeader(headerKey, headerValue);

        exportDataToPdf.export(response, eAdditivesService.getSelectedAdditivesByIndexes(indexes));
    }

}
