package mk.ukim.finki.wp.eshop.service;

import mk.ukim.finki.wp.eshop.model.Manufacturer;

import java.util.List;
import java.util.Optional;

public interface ManufacturerService {

    Optional<Manufacturer> findById(Long id);
    List<Manufacturer> findAll();
}