package com.demo.authdemo.service;
import java.util.List;
import com.demo.authdemo.entity.FoundMaterial;
import com.demo.authdemo.repository.FoundMaterialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class FoundMaterialService {

    @Autowired
    private FoundMaterialRepository foundMaterialRepository;

    public FoundMaterial saveFoundMaterial(FoundMaterial foundMaterial) {
        // Eğer malzeme zaten tabloya eklenmişse hata fırlatma
        boolean exists = foundMaterialRepository.existsByMaterialMatId(foundMaterial.getMaterial().getMatId());
        if (exists) {
            return null; // Hata fırlatma, null döndür
        }
        return foundMaterialRepository.save(foundMaterial);
    }

    public List<FoundMaterial> saveAllFoundMaterials(List<FoundMaterial> foundMaterials) {
        List<FoundMaterial> materialsToSave = foundMaterials.stream()
                .filter(material -> !foundMaterialRepository.existsByMaterialMatId(material.getMaterial().getMatId()))
                .collect(Collectors.toList());

        if (materialsToSave.isEmpty()) {
            throw new RuntimeException("Tüm malzemeler zaten mevcut.");
        }

        return foundMaterialRepository.saveAll(materialsToSave);
    }
}
