package utils;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;

import constants.Constants;
import org.apache.poi.ss.usermodel.*;
import pojo.WriteBackInfo;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 通过easypoi解析excel，封装成list对象
 * @author 向阳
 * @date 2020/6/11-16:01
 */
public class ExcelUtils {
    //批量回写集合（定义为静态变量，可以将多个接口的回写对象放在集合中后，进行一次性回写）
   public static List<WriteBackInfo> list=new ArrayList<WriteBackInfo>();

    //通过easypoi解析excel，封装成list对象
    public static List readExcel(int startSheet,Class clazz){
        try {
            String path = Constants.EXCEL_PATH;
            FileInputStream fis = new FileInputStream(path);
            ImportParams params = new ImportParams();
            params.setStartSheetIndex(startSheet);
            List list = ExcelImportUtil.importExcel(fis, clazz, params);
            return list;
        }catch (Exception e){
            e.getStackTrace();
        }
        return null;
    }
    //将lis集合转换成一维数组，方便testng中数据驱动调用
    public static Object[] getDatas(int startSheet,Class clazz){
        List list = readExcel(startSheet, clazz);
        Object[] objects=new Object[list.size()];
        for (int i = 0; i <list.size() ; i++) {
            objects[i]=list.get(i);
        }
        return objects;
    }

    /**
     * 将测试实际结果回写到Excel表中的第8列中
     */
    public static void writeExcel(List<WriteBackInfo> list){
        FileInputStream fis=null;
        FileOutputStream fos=null;

        try {
            fis=new FileInputStream(Constants.EXCEL_PATH);
            Workbook excel = WorkbookFactory.create(fis);
            //循环批量回写集合
            for (WriteBackInfo writeBackInfo : list) {
                int sheetIndex = writeBackInfo.getSheetIndex();
                int rowNum = writeBackInfo.getRowNum();
                int cellNum = writeBackInfo.getCellNum();
                String content = writeBackInfo.getBody();

                Sheet sheet = excel.getSheetAt(sheetIndex);
                Row row = sheet.getRow(rowNum);
                Cell cell = row.getCell(cellNum,Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                //判断cell是否为空
                if(cell == null){
                    cell=row.createCell(cellNum);
                }
                 //设置单元格内容
                cell.setCellValue(content);
                }

            fos=new FileOutputStream(Constants.EXCEL_PATH);
            //执行回写
            excel.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //关流
            close(fis);
            close(fos);
        }

    }
    //关流
    public static void close(Closeable filestream){
        if(filestream!=null){
            try {
                filestream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
