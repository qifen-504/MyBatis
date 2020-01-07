package cn.xdl.bean;

public class SqlBean {
    private String id;
    private String resultType ;
    private String sql;
    private String parameterType;
    private  String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public String toString() {
        return "SqlBean{" +
                "id='" + id + '\'' +
                ", resultType='" + resultType + '\'' +
                ", sql='" + sql + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getParameterType() {
        return parameterType;
    }

    public void setParameterType(String parameterType) {
        this.parameterType = parameterType;
    }

    public SqlBean(String id, String resultType, String sql, String parameterType, String type) {
        this.id = id;
        this.resultType = resultType;
        this.sql = sql;
        this.parameterType = parameterType;
        this.type = type;
    }

    public SqlBean() {
    }
}
