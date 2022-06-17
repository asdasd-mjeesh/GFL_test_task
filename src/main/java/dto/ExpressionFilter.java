package dto;

public record ExpressionFilter(Double aEquals,
                               Double aLess,
                               Double aLarge,
                               Integer limit,
                               Integer offset) {
}
