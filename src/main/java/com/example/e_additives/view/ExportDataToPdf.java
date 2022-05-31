package com.example.e_additives.view;

import com.example.e_additives.entity.EAdditive;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class ExportDataToPdf {

    private Font getTableTextFont(int fontSize){
        Font font = FontFactory.getFont("timesnewromanpsmt.ttf", "cp1251", BaseFont.EMBEDDED);
        font.setColor(BaseColor.BLACK);
        font.setSize(fontSize);
        return font;
    }

    private PdfPCell getTableCell(BaseColor color){
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(color);
        cell.setPaddingBottom(6);
        return cell;
    }

    private void writeTableHeader(PdfPTable table) {

        PdfPCell cell = getTableCell(new BaseColor(130,255,222,255));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        cell.setPhrase(new Phrase("Индекс", getTableTextFont(16)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Имя", getTableTextFont(16)));
        table.addCell(cell);

        cell.setPhrase(new Phrase("Информация", getTableTextFont(16)));
        table.addCell(cell);

    }

    private void writeTableData(PdfPTable table, EAdditive eAdditive) {

        PdfPCell cell = getTableCell(new BaseColor(211,255,233,255));
        cell.setPaddingLeft(3);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);

        cell.setPhrase(new Phrase(eAdditive.getIndex(), getTableTextFont(12)));
        table.addCell(cell);

        cell.setPhrase(new Phrase(eAdditive.getName(), getTableTextFont(12)));
        table.addCell(cell);

        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(eAdditive.getInformation(), getTableTextFont(12)));
        table.addCell(cell);

    }

    private PdfPTable createTable(List<EAdditive> eAdditiveList) throws DocumentException {

        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100f);
        table.setWidths(new float[]{1.0f, 1.5f, 5.0f});
        table.setSpacingBefore(10);
        writeTableHeader(table);
        for (EAdditive e: eAdditiveList){
            writeTableData(table, e);
        }
        return table;
    }

    private Paragraph createName(String name){
        Paragraph tableName = new Paragraph(name, getTableTextFont(18));
        tableName.setAlignment(Element.ALIGN_CENTER);
        return tableName;
    }

    public void export(HttpServletResponse response, Map<String, List<EAdditive>> tableNameAndAdditives) throws DocumentException, IOException {
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document,response.getOutputStream());
        document.open();
        for (String tableName: tableNameAndAdditives.keySet()){
            if (!tableNameAndAdditives.get(tableName).isEmpty()) {
                document.add(createName(tableName));
                document.add(createTable(tableNameAndAdditives.get(tableName)));
            }
        }
        document.close();
        response.getOutputStream().close();
    }
}
