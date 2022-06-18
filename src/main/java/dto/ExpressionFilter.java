package dto;

public record ExpressionFilter(Double aEquals,
                               Double min,
                               Double max,
                               int limit,
                               int offset) {

    @Override
    public String toString() {
        return "ExpressionFilter{" +
               "aEquals=" + aEquals +
               ", min=" + min +
               ", max=" + max +
               ", limit=" + limit +
               ", offset=" + offset +
               '}';
    }
}
