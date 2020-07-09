package pojo;

/**
 * @author 向阳
 * @date 2020/6/22-15:28
 */
public class WriteBackInfo {
    private int sheetIndex;
    private int rowNum;
    private int cellNum;
    private String body;

    public WriteBackInfo() {
    }

    public WriteBackInfo(int sheetIndex, int rowNum, int cellNum, String body) {
        this.sheetIndex = sheetIndex;
        this.rowNum = rowNum;
        this.cellNum = cellNum;
        this.body = body;
    }

    public int getSheetIndex() {
        return sheetIndex;
    }

    public void setSheetIndex(int sheetIndex) {
        this.sheetIndex = sheetIndex;
    }

    public int getRowNum() {
        return rowNum;
    }

    public void setRowNum(int rowNum) {
        this.rowNum = rowNum;
    }

    public int getCellNum() {
        return cellNum;
    }

    public void setCellNum(int cellNum) {
        this.cellNum = cellNum;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "WriteInfo{" +
                "sheetIndex=" + sheetIndex +
                ", rowNum=" + rowNum +
                ", cellNum=" + cellNum +
                ", body='" + body + '\'' +
                '}';
    }
}
