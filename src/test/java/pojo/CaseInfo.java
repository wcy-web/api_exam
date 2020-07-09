package pojo;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * @author 向阳
 * @date 2020/6/11-16:02
 */
public class CaseInfo {
    //用例编号	用例描述	接口名称	请求方式	url	参数	参数类型
    @Excel(name="用例编号")
    private int id;
    @Excel(name="接口名称")
    private String name;
    @Excel(name="请求方式")
    private String type;
    @Excel(name="url")
    private String url;
    @Excel(name="参数")
    private String params;
    @Excel(name="参数类型")
    private String contentType;
    @Excel(name="预期结果")
    private String expectResult;
    @Excel(name="sql")
    private String sql;

    public CaseInfo() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getExpectResult() {
        return expectResult;
    }

    public void setExpectResult(String expectResult) {
        this.expectResult = expectResult;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @Override
    public String toString() {
        return "CaseInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", params='" + params + '\'' +
                ", contentType='" + contentType + '\'' +
                ", expectResult='" + expectResult + '\'' +
                ", sql='" + sql + '\'' +
                '}';
    }
}
