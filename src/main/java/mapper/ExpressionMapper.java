package mapper;

import dto.ExpressionDto;
import entity.Expression;

import java.util.List;
import java.util.stream.Collectors;

public class ExpressionMapper implements Mapper<ExpressionDto, Expression> {

    @Override
    public ExpressionDto map(Expression from) {
        return new ExpressionDto(
                from.getId(),
                from.getValue(),
                from.getResult()
                );
    }

    @Override
    public List<ExpressionDto> map(List<Expression> from) {
        return from.stream()
                .map( item -> new ExpressionDto(
                        item.getId(),
                        item.getValue(),
                        item.getResult()
                ))
                .collect(Collectors.toList());
    }
}
