package com.peplink.ecommerce.workflow.reactive.api.controller.cellularmodule;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.peplink.ecommerce.workflow.reactive.api.repository.CellularModuleRepository;
import com.peplink.ecommerce.workflow.reactive.api.util.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = Constants.API_PATH_CELLULAR_MODULES)
public class CellularModuleV1Controller {

//    @Autowired
//    private CellularModuleRepository m_cellularModuleRepository;

    @Autowired
    @Qualifier("messageSource")
    private ResourceBundleMessageSource m_messageSource;

    @GetMapping(produces = "application/json")
    public Flux<CellularModuleApi> readAll() {
//         m_cellularModuleRepository.findAll().doOnNext(resultEntity -> {
//            ObjectUtility.convertTo(resultEntity, CellularModuleApi.class);
//        }).log().subscribe();
         return null;
//        return ObjectUtility.listConvertTo(m_cellularModuleRepository.findAll(), CellularModuleApi.class);
    }

    @GetMapping(path = "/search", produces = "application/json")
    public Flux<CellularModuleApi> readBy(@RequestParam Map<String, Object> queryParams) {
        return null;
//        return ObjectUtility.listConvertTo(
//                m_cellularModuleRepository.findAll(SpecificationBuilder.build(queryParams)),
//                CellularModuleApi.class);
    }

    @PutMapping(path = "/{id}", produces = "application/json")
    public Mono<CellularModuleApi> update(@PathVariable String id, @RequestBody Map<String, Object> payload) {
        return null;
//        Optional<CellularModuleEntity> existingEntity = m_cellularModuleRepository.findById(id);
//        if (existingEntity.isPresent()) {
//            CellularModuleEntity resultEntity = m_cellularModuleRepository
//                    .save(ObjectUtility.updateObject(payload, existingEntity.get(), CellularModuleApi.class));
//            return ObjectUtility.convertTo(resultEntity, CellularModuleApi.class);
//        }
//        throw new ObjectNotFoundException(
//                m_messageSource.getMessage("cellularmodule.notfound", new Object[] { id }, Locale.getDefault()));
    }

    @PostMapping(produces = "application/json")
    public Mono<CellularModuleApi> persist(@RequestBody CellularModuleApi payload) {
        return null;
//        CellularModuleEntity entity = ObjectUtility.convertTo(payload, CellularModuleEntity.class);
//        return ObjectUtility.convertTo(m_cellularModuleRepository.save(entity), CellularModuleApi.class);
    }

}
