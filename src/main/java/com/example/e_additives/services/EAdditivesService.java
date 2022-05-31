package com.example.e_additives.services;

import com.example.e_additives.dao.EAdditivesRepository;
import com.example.e_additives.entity.EAdditive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EAdditivesService {

    @Autowired
    private EAdditivesRepository eAdditivesRepository;

    public Map<String, List<EAdditive>> getAllAdditives(){
        List<EAdditive> eAdditivesList = eAdditivesRepository.findAll();
        Collections.sort(eAdditivesList);
        return sortedAdditivesByType(eAdditivesList);
    }

    public Map<String, List<EAdditive>> getSelectedAdditivesByIndexes(List<String> indexes){
        List<EAdditive> eAdditiveList = new ArrayList<>();
        for (String s: indexes){
            eAdditiveList.add(eAdditivesRepository.findByIndex(s));
        }
        return sortedAdditivesByType(eAdditiveList);
    }

    @Autowired
    @Qualifier("names")
    private String[] names;

    private Map<String, List<EAdditive>> sortedAdditivesByType(List<EAdditive> eAdditiveList){

        Map<String, List<EAdditive>> typeWithListEAdditives = new LinkedHashMap<>();

        for (String name: names){
            Iterator<EAdditive> iterator = eAdditiveList.iterator();
            List<EAdditive> list = new ArrayList<>();
            while (iterator.hasNext()) {
                EAdditive e = iterator.next();
                if (e.getType().equals(name)) {
                    list.add(e);
                    iterator.remove();
                }
            }
            typeWithListEAdditives.put(name,list);
        }
        return typeWithListEAdditives;
    }


}
