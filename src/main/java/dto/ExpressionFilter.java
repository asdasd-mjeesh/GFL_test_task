package dto;

public record ExpressionFilter(Double aEquals,
                               Double aLess,
                               Double aLarge,
                               int limit,
                               int offset) {
}
