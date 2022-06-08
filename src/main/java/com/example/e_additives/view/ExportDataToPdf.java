package com.example.e_additives.view;

import com.example.e_additives.entity.EAdditive;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Класс, преобразующий информацию о пищевых добавках в pdf-файл.
 */
@Component
public class ExportDataToPdf {

    /**
     * Метод, создающий новый pdf-документ и выводящий его в выходной поток {@link HttpServletResponse#getOutputStream()}.
     * @param response HTTP-ответ на запрос о создании pdf-файла.
     * @param tableNameAndAdditives {@link Map} с названиями таблиц в качестве ключей
     *                                         и списками пищевых добавок в качестве значений.
     * @throws DocumentException сигнализирует о том, что произошла ошибка при создании документа.
     * @throws IOException сигнализирует о том, что произошло какое-либо исключение ввода-вывода.
     */
    public void export(HttpServletResponse response, Map<String, List<EAdditive>> tableNameAndAdditives) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        for (Map.Entry<String, List<EAdditive>> entry: tableNameAndAdditives.entrySet()){
            if (!entry.getValue().isEmpty()) {
                document.add(createName(entry.getKey()));
                document.add(createTable(entry.getValue()));
            }
        }
        document.close();
        response.getOutputStream().close();
    }

    /**
     * Метод, создающий шрифт со стандартными настройками.
     * @param fontSize размер шрифта.
     * @return возвращает {@link Font} (шрифт) с настройками.
     */
    private Font getTableTextFont(int fontSize){
        Font font = FontFactory.getFont("timesnewromanpsmt.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        font.setColor(BaseColor.BLACK);
        font.setSize(fontSize);
        return font;
    }

    /**
     * Метод, создающий ячейку таблицы со стандартными настройками.
     * @param color цвет заднего фона ячейки.
     * @return возвращает {@link PdfPCell} (ячейку таблицы) с настройками.
     */
    private PdfPCell getTableCell(BaseColor color){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(color);
        cell.setPaddingBottom(6);
        return cell;
    }

    /**
     * Метод, создающий шапку таблицы.
     * @param table таблица для заполнения.
     */
    private void writeTableHeader(PdfPTable table) {
        Font font = getTableTextFont(16);

        PdfPCell cell = getTableCell(new BaseColor(130,255,222,255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Индекс", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Имя", font));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Информация", font));
        table.addCell(cell);

    }

    /**
     * Метод, заполняющий строку таблицы информацией о пищевой добавке.
     * @param table таблица для заполнения.
     * @param eAdditive пищевая добавка, информация о которой заносится в таблицу.
     * @param font шрифт.
     */
    private void writeTableData(PdfPTable table, EAdditive eAdditive, Font font) {

        PdfPCell cell = getTableCell(new BaseColor(211,255,233,255));
        cell.setPaddingLeft(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setPhrase(new Phrase(eAdditive.getIndex(), font));
        table.addCell(cell);

        cell.setPhrase(new Phrase(eAdditive.getName(), font));
        table.addCell(cell);

        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(eAdditive.getInformation(), font));
        table.addCell(cell);

    }

    /**
     * Метод, создающий таблицу и заполняющий ее данными.
     * с помощью методов {@link ExportDataToPdf#writeTableHeader(PdfPTable)} и {@link ExportDataToPdf#writeTableData(PdfPTable, EAdditive,Font)}.
     * @param eAdditiveList список пищевых добавок.
     * @throws DocumentException сигнализирует о том, что произошла ошибка при редактировании документа.
     * @return возвращает созданную таблицу {@link PdfPTable}.
     */
    private PdfPTable createTable(List<EAdditive> eAdditiveList) throws DocumentException {

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 1.5f, 5.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);

        Font font = getTableTextFont(12);

        for (EAdditive e: eAdditiveList){
            writeTableData(table, e, font);
        }
        return table;
    }

    /**
     * Метод, создающий название таблицы.
     * @param name название таблицы.
     * @return возвращает созданное название.
     */
    private Paragraph createName(String name){
        Paragraph tableName = new Paragraph(name, getTableTextFont(18));
        tableName.setAlignment(Element.ALIGN_CENTER);
        return tableName;
    }
}
