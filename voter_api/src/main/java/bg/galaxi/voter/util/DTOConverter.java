package bg.galaxi.voter.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class DTOConverter {

    private DTOConverter() {
    }

    public static <S, D> D convert(S source, Class<D> destinationClass) {
        org.modelmapper.ModelMapper mapper = new org.modelmapper.ModelMapper();
        return mapper.map(source, destinationClass);
    }

    public static <S, D> List<D> convert(Iterable<S> sourceCollection, Class<D> destinationClass) {
        CustomModelMapper mapper = new CustomModelMapper();
        List<D> resultList = new ArrayList<>();

        for (S sourceObj : sourceCollection) {
            D destObj = convert(sourceObj, destinationClass);
            resultList.add(destObj);
        }

        return resultList;
    }

    public static <S, D> Set<D> convertToSet(Iterable<S> sourceCollection, Class<D> destinationClass) {
        CustomModelMapper mapper = new CustomModelMapper();
        Set<D> resultSet = new HashSet<>();

        for (S sourceObj : sourceCollection) {
            D destObj = convert(sourceObj, destinationClass);
            resultSet.add(destObj);
        }

        return resultSet;
    }
}