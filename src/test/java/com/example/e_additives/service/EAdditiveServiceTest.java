package com.example.e_additives.service;

import com.example.e_additives.entity.EAdditive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class EAdditiveServiceTest {

    @Autowired
    private EAdditiveService eAdditiveService;

    @Value("#{${eadditive.type.name}}")
    private String[] tableNames;

    @Test
    void getSelectedAdditivesByIndexesReturnEmptyMapIfNoIndexes(){
        List<String> emptyList = new ArrayList<>();
        Map<String, List<EAdditive>> resultMap = eAdditiveService.getSelectedAdditivesByIndexes(emptyList);
        Assertions.assertTrue(resultMap.isEmpty());
    }

    @Test
    void getSelectedAdditivesByIndexesReturnMapWithElementByType(){
        List<String> indexesList = new ArrayList<>();
        indexesList.add("E300");
        Map<String, List<EAdditive>> resultMap = eAdditiveService.getSelectedAdditivesByIndexes(indexesList);
        Assertions.assertFalse(resultMap.get(tableNames[2]).isEmpty());
    }
}
