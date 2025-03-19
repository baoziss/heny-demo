package com.heny.demo.service.impl;

import com.heny.demo.bean.Operation;
import com.heny.demo.bean.OperationFactory;
import com.heny.demo.service.CoordinateService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CoordinateServiceImpl implements CoordinateService {

    @Override
    public void insertDb() {
        Operation csv = OperationFactory.getOperation("csv").orElseThrow(() -> new IllegalArgumentException("无效操作"));
        csv.execute("I:\\code\\1\\heny\\heny-demo\\file\\J1-J8.CSV");
        Operation txt = OperationFactory.getOperation("txt").orElseThrow(() -> new IllegalArgumentException("无效操作"));
        txt.execute("I:\\code\\1\\heny\\heny-demo\\file\\J1-J8.txt");
        Operation xlsx = OperationFactory.getOperation("xlsx").orElseThrow(() -> new IllegalArgumentException("无效操作"));
        xlsx.execute("I:\\code\\1\\heny\\heny-demo\\file\\J1-J8.xlsx");
    }
}
