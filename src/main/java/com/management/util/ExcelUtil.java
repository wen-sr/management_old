package com.management.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

public class ExcelUtil
{
    private static final String xls = "xls";
    private static final String xlsx = "xlsx";

    public static List<String[]> readExcel(MultipartFile file)
            throws IOException
    {
        checkFile(file);

        Workbook workbook = getWorkBook(file);

        List<String[]> list = new ArrayList();
        if (workbook != null)
        {
            for (int sheetNum = 0; sheetNum < workbook.getNumberOfSheets(); sheetNum++)
            {
                Sheet sheet = workbook.getSheetAt(sheetNum);
                if (sheet != null)
                {
                    int firstRowNum = sheet.getFirstRowNum();

                    int lastRowNum = sheet.getLastRowNum();

                    int firstColumn = 0;
                    for (int rowNum = firstRowNum; rowNum <= lastRowNum; rowNum++)
                    {
                        Row row = sheet.getRow(rowNum);
                        if (row != null)
                        {
                            int firstCellNum = 0;

                            int lastCellNum = row.getPhysicalNumberOfCells();
                            if (rowNum == firstRowNum) {
                                firstColumn = lastCellNum;
                            }
                            String[] cells = new String[firstColumn];
                            for (int cellNum = firstCellNum; cellNum < firstColumn; cellNum++)
                            {
                                Cell cell = row.getCell(cellNum);
                                cells[cellNum] = getCellValue(cell);
                            }
                            list.add(cells);
                        }
                    }
                }
            }
            workbook.close();
        }
        return list;
    }

    public static void checkFile(MultipartFile file)
            throws IOException
    {
        if (null == file) {
            throw new FileNotFoundException("������������");
        }
        String fileName = file.getOriginalFilename();
        if ((!fileName.endsWith("xls")) && (!fileName.endsWith("xlsx"))) {
            throw new IOException(fileName + "����excel����");
        }
    }

    public static Workbook getWorkBook(MultipartFile file)
    {
        String fileName = file.getOriginalFilename();

        Workbook workbook = null;
        try
        {
            InputStream is = file.getInputStream();
            if (fileName.endsWith("xls")) {
                workbook = new HSSFWorkbook(is);
            } else if (fileName.endsWith("xlsx")) {
                workbook = new XSSFWorkbook(is);
            }
        }
        catch (IOException localIOException) {}
        return workbook;
    }

    public static String getCellValue(Cell cell)
    {
        DecimalFormat df = new DecimalFormat("#");
        String cellValue = "";
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == 0) {
            cell.setCellType(1);
        }
        switch (cell.getCellType())
        {
            case 0:
                double c = cell.getNumericCellValue();
                cellValue = df.format(c);
                break;
            case 1:
                cellValue = cell.getStringCellValue().toString();
                break;
            case 4:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case 2:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case 3:
                cellValue = "";
                break;
            case 5:
                cellValue = "非法字符";
                break;
            default:
                cellValue = "未知类型";
        }
        return cellValue;
    }
}
