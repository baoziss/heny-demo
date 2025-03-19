package com.heny.demo.bean;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class OperationFactory {
    private static Map<String, Operation> map=new HashMap<>();
    static {
        map.put("txt",new TxtOperation());
        map.put("csv",new CsvOperation());
        map.put("xlsx",new ExcelOperation());
    }
    public static Optional<Operation> getOperation(String operator){
        return Optional.ofNullable(map.get(operator));
    }
}
