package com.ignitionone.datastorm.datorama.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ExcelReader {

    /**
     * Method
     *
     * @param filePath
     * @param sheetname
     * @return
     * @throws Exception
     */
    public HashMap getExcel(String filePath, String sheetname) throws Exception {
        HashMap sheetData = new HashMap();
        DataFormatter formatter = new DataFormatter();


        FileInputStream fis = null;
        try {
            if (filePath.endsWith(".xls")) {
                fis = new FileInputStream(filePath);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheet(sheetname);
                Iterator rows = sheet.rowIterator();
                List<String> keyrow = new ArrayList<>();
                HSSFRow row = (HSSFRow) rows.next();
                Iterator cells = row.cellIterator();

                while (cells.hasNext()) {
                    String cell = formatter.formatCellValue((Cell) cells.next());
                    keyrow.add(cell);
                }
                int rowAt = 0;
                while (rows.hasNext()) {
                    row = (HSSFRow) rows.next();
                    cells = row.cellIterator();
                    List data = new ArrayList();
                    while (cells.hasNext()) {
                        String cell = formatter.formatCellValue((Cell) cells.next());
                        data.add(cell);
                    }
                    sheetData.put(keyrow.get(rowAt), data);
                    rowAt++;
                }
                workbook.close();
            } else {
                if (filePath.endsWith(".xlsx")) {
                    fis = new FileInputStream(filePath);
                    XSSFWorkbook workbook = new XSSFWorkbook(fis);
                    XSSFSheet sheet = workbook.getSheet(sheetname);
                    Iterator rows = sheet.rowIterator();
                    List<String> keyrow = new ArrayList<>();
                    XSSFRow row = (XSSFRow) rows.next();
                    Iterator cells = row.cellIterator();

                    while (cells.hasNext()) {
                        String cell = formatter.formatCellValue((Cell) cells.next());
                        keyrow.add(cell);
                    }
                    int rowAt = 0;
                    while (rows.hasNext()) {
                        row = (XSSFRow) rows.next();
                        cells = row.cellIterator();
                        final List data = new ArrayList();
                        while (cells.hasNext()) {
                            String cell = formatter.formatCellValue((Cell) cells.next());
                            data.add(cell);
                        }
                        for (int i = 0; i < keyrow.size(); i++) {
                            if (sheetData.get(keyrow.get(i)) == null) {
                                final int finalI = i;
                                List templist = new ArrayList<String>() {{
                                    add((String) data.get(finalI));
                                }};
                                sheetData.put(keyrow.get(i), templist);
                            } else {
                                List templist = (List) sheetData.get(keyrow.get(i));
                                templist.add(data.get(i));
                                sheetData.put(keyrow.get(i), templist);
                            }
                        }


                        //sheetData.put(keyrow.get(rowAt), data);
                        rowAt++;
                    }
                    workbook.close();
                } else {
                    throw new IOException("Incorrect file type for Excel Reader");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
        return sheetData;
    }

    /**
     * This method Takes in cellValue read from the DB on Contract Submisson and writes into a common excel sheet. These
     * <br> cellValue can be than re-used for various other functionalities and classes in the end-to-end flow of a package
     *
     * @param filePath   File path for the Excel work book in which we have sheets.
     * @param sheetname  The sheetname to which cellValue has to be written into.
     * @param Row        The exact row number cellValue has to be written into.
     * @param cellValue  the contractSYSID obtained from DB.
     * @param columnName the columnName the columnName in the excel in which the ContractSysID is to be written into
     * @throws IOException
     */
    public void writeToExcelFile(String filePath, String sheetname, int Row, String cellValue, String columnName) throws IOException {

        FileInputStream fis = null;
        try {
            if (filePath.endsWith(".xls")) {
                fis = new FileInputStream(filePath);
                HSSFWorkbook workbook = new HSSFWorkbook(fis);
                HSSFSheet sheet = workbook.getSheet(sheetname);
                HSSFCell cell = null;
                HSSFRow row = sheet.getRow(0);
                Iterator cells = row.cellIterator();
                int i = 0;
                int flag = 0;
                cell = row.getCell(0);
                while (cells.hasNext()) {
                    if (cell == null) {
                        break;
                    }
                    if (cell.toString().matches(columnName)) {
                        cell = sheet.getRow(Row + 1).getCell(i);
                        if (cell == null) {
                            sheet.getRow(Row + 1).createCell(i);
                        }
                        cell = sheet.getRow(Row + 1).getCell(i);
                        cell.setCellValue(cellValue);
                        flag++;
                        break;
                    }
                    i++;
                    cell = row.getCell(i);
                }
                if (flag == 0) {
                    sheet.getRow(0).createCell(i);
                    cell = sheet.getRow(0).getCell(i);
                    System.out.println();
                    cell.setCellValue(columnName);
                    sheet.getRow(Row + 1).createCell(i);
                    cell = sheet.getRow(Row + 1).getCell(i);
                    cell.setCellValue(cellValue);
                }
                fis.close();

                FileOutputStream outFile = new FileOutputStream(new File(filePath));
                workbook.write(outFile);
                outFile.close();
            } else {
                if (filePath.endsWith(".xlsx")) {
                    fis = new FileInputStream(filePath);
                    XSSFWorkbook workbook = new XSSFWorkbook(fis);
                    XSSFSheet sheet = workbook.getSheet(sheetname);
                    XSSFCell cell = null;
                    XSSFRow row = sheet.getRow(0);
                    Iterator cells = row.cellIterator();
                    int i = 0;
                    int flag = 0;
                    cell = row.getCell(0);
                    while (cells.hasNext()) {
                        if (cell == null) {
                            break;
                        }
                        if (cell.toString().matches(columnName)) {
                            cell = sheet.getRow(Row + 1).getCell(i);
                            if (cell == null) {
                                sheet.getRow(Row + 1).createCell(i);
                            }
                            cell = sheet.getRow(Row + 1).getCell(i);
                            cell.setCellValue(cellValue);
                            flag++;
                            break;
                        }
                        i++;
                        cell = row.getCell(i);
                    }
                    if (flag == 0) {
                        sheet.getRow(0).createCell(i);
                        cell = sheet.getRow(0).getCell(i);
                        System.out.println();
                        cell.setCellValue(columnName);
                        sheet.getRow(Row + 1).createCell(i);
                        cell = sheet.getRow(Row + 1).getCell(i);
                        cell.setCellValue(cellValue);
                    }
                    fis.close();

                    FileOutputStream outFile = new FileOutputStream(new File(filePath));
                    workbook.write(outFile);
                    outFile.close();

                } else {
                    throw new IOException("Incorrect file type for Excel Reader");
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * <br>
     * Method will read particular coloum data(ex : If we pass ColoumName is Delaer ID it will ready delaerID coloumn
     * data) depends on ColumnName and filepath and returns the list*</br>
     *
     * @param FilePath   in value
     * @param ColumnName - Which coloumn name for data to be read
     * @return ColoumData as a String
     * @usage getColmnmDataFromExcel(filepath,"commonDataForThePackage","dealerID");
     */
    public String getColmnmDataFromExcel(String FilePath, String sheetName, String ColumnName) {

        List Columndata = new ArrayList<>();
        String columnValue = null;
        try {
            HashMap<String, List> exclData = getExcel(FilePath, sheetName);
            Columndata = exclData.get(ColumnName);
            for (int i = 0; i < Columndata.size(); i++) {
                columnValue = Columndata.get(0).toString();
            }
        } catch (Exception exc) {
            System.out.println("fail to read data from excel," + exc.getMessage());
        }
        return columnValue;

    }


}