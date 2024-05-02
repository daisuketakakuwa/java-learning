package jp.ats.kotlinlearning.service

import jp.ats.kotlinlearning.model.Event
import jp.ats.kotlinlearning.model.EventWithParticipants
import jp.ats.kotlinlearning.repository.EventRepository
import org.springframework.stereotype.Service

@Service
class EventService(private val eventRepository: EventRepository) {

    fun findEvents(): List<Event> = eventRepository.findEvents()

    fun findEventById(id: Int): Event = eventRepository.findEventById(id)

    fun findEventsWithOrganizerAndParticipants(id: Int): EventWithParticipants = eventRepository.findEventsWithOrganizerAndParticipants(id)

}