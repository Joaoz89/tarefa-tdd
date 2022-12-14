package com.devsuperior.bds02.services;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

import com.devsuperior.bds02.dto.EventDTO;
import com.devsuperior.bds02.entities.Event;
import com.devsuperior.bds02.respositories.EventRepository;
import com.devsuperior.bds02.services.exceptions.ResourceNotFoundException;

@Service
public class EventService {

	@Autowired
	private EventRepository eventRepository;

	@Transactional
	public EventDTO update(Long id, EventDTO dto) {
		try {
			Event entity = eventRepository.getOne(id);
			entity.setName(dto.getName());
			entity = eventRepository.save(entity);
			return new EventDTO(entity);
		}

		catch (ResourceNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);
		}

		catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException("Id not found " + id);

		}
	}
}
