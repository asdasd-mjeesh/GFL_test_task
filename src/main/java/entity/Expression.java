package entity;

public class Expression {

    private Long id;
    private String value;
    private Double result;

    public Expression(String value, Double result) {
        this.value = value;
        this.result = result;
    }

    public Expression(Long id, String value, Double result) {
        this.id = id;
        this.value = value;
        this.result = result;
    }

    @Override
    public String toString() {
        return "Expression{" +
               "id=" + id +
               ", value='" + value + '\'' +
               ", result=" + result +
               '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
