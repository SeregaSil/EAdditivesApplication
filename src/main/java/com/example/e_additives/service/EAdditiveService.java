package com.example.e_additives.service;

import com.example.e_additives.repository.EAdditivesRepository;
import com.example.e_additives.entity.EAdditive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Класс-сервис для обработки информации из базы данных.
 * {@code findAll()} - метод, возвращающий все пищевые добавки {@link EAdditive} из базы данных;
 * {@code findBuIndex()} - метод, возвращающий определенную пищевую добавку {@link EAdditive} из базы данных.
 */
@Service
public class EAdditiveService {

    private final EAdditivesRepository eAdditivesRepository;

    /**
     * Конструктор.
     * @param eAdditivesRepository репозиторий {@link EAdditivesRepository} для доступа к информации из базы данных.
     */
    @Autowired
    public EAdditiveService(EAdditivesRepository eAdditivesRepository) {
        this.eAdditivesRepository = eAdditivesRepository;
    }

    /**
     * Метод, возвращающий весь список пищевых добавок.
     * @return {@link Map}, где ключ - название класса пищевых добавок,
     * значение - список пищевых добавок, которые относятся к данному классу.
     */
    public Map<String, List<EAdditive>> getAllAdditives(){
        return sortedAdditivesByType(eAdditivesRepository.findAll());
    }

    /**
     * Возвращает список выбранных пищевых добавок, отсортированный по каждому классу добавок.
     * @param indexes индексы выбранных пищевых добавок.
     * @return {@link Map}, где ключ - название класса пищевых добавок,
     * значение - список пищевых добавок, которые относятся к данному классу.
     */
    public Map<String, List<EAdditive>> getSelectedAdditivesByIndexes(List<String> indexes){
        if(!indexes.isEmpty()){
            List<EAdditive> eAdditiveList = new ArrayList<>();
            for (String s: indexes){
                Optional<EAdditive> optional = Optional.ofNullable(eAdditivesRepository.findByIndex(s));
                if (optional.isPresent()){
                    eAdditiveList.add(optional.get());
                }
            }
            if (!eAdditiveList.isEmpty()){
                return sortedAdditivesByType(eAdditiveList);
            }
            else return new HashMap<>();
        }
        else return new HashMap<>();
    }

    @Value("#{${eadditive.type.name}}")
    private String[] names;

    /**
     * Сортирует список пищевых добавок по их классам.
     * @param eAdditiveList список пищевых добавок, подлежащий сортировке.
     * @return {@link Map}, где ключ - название класса пищевых добавок,
     * значение - список пищевых добавок, которые относятся к данному классу.
     */
    private Map<String, List<EAdditive>> sortedAdditivesByType(List<EAdditive> eAdditiveList){

        Map<String, List<EAdditive>> typeWithListEAdditives = new LinkedHashMap<>();
        Collections.sort(eAdditiveList);
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
