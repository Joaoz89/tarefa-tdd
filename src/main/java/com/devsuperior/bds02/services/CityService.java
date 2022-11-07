package com.devsuperior.bds02.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.devsuperior.bds02.dto.CityDTO;

import com.devsuperior.bds02.entities.City;

import com.devsuperior.bds02.respositories.CityRepository;
import com.devsuperior.bds02.respositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.DataBaseException;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;



@Service
public class CityService {
	
	
	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private EventRepository eventRepository;
	
	@Transactional(readOnly = true)
	public Page<CityDTO> findAllPaged(Pageable pageable) {
		
		Page<City> list = cityRepository.findAll(pageable); 

		
		return list.map(x -> new CityDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CityDTO findById(Long id) {
		Optional<City> list = cityRepository.findById(id);
		City city = list.orElseThrow(() -> new ResourceNotFoundException("Entyti not found"));
				
		return new CityDTO(city); 
	}
	
	@Transactional
	public CityDTO insert(@RequestBody CityDTO dto) {
		City entity = new City();
		copyDtoToEntity(dto, entity);
		entity = cityRepository.save(entity);
		return new CityDTO(entity);
	
	}
	@Transactional
	private void copyDtoToEntity(CityDTO dto, City entity) {
		entity.setName(dto.getName());
		//entity.getEvents().clear();

	}
	
	public void delete(Long id) {
		try {
			cityRepository.deleteById(id);
		
		}
		catch(EmptyResultDataAccessException e){
			throw new ResourceNotFoundException("Id not found " + id);
			
		}
		catch(DataIntegrityViolationException e) {
			throw new DataBaseException("Integrity violation");
		}
	}}


