package dto;

public record ExpressionDto(Long id,
                            String value,
                            Double result) {

    @Override
    public String toString() {
        return "id:\t" + id + "\n" +
               "value:\t" + value +  "\n" +
               "result:\t" + result;
    }
}
