package com.example.e_additives.service;

import com.example.e_additives.entity.EAdditive;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
class EAdditiveServiceTest {

    @Autowired
    private EAdditiveService eAdditiveService;

    @Test
    void getSelectedAdditivesByIndexesReturnEmptyMapIfNoIndexes(){
        List<String> emptyList = new ArrayList<>();
        Map<String, List<EAdditive>> resultMap = eAdditiveService.getSelectedAdditivesByIndexes(emptyList);
        Assertions.assertTrue(resultMap.isEmpty());
    }

    @Test
    void getSelectedAdditivesByIndexesReturnMapWithElementByType(){
        List<String> indexesList = new ArrayList<>();
        indexesList.add("E100");
        Map<String, List<EAdditive>> resultMap = eAdditiveService.getSelectedAdditivesByIndexes(indexesList);
        Assertions.assertFalse(resultMap.get("Красители (E100-E199)").isEmpty());
    }
}
