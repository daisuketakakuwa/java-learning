package jp.ats.kotlinlearning.controller

import jp.ats.kotlinlearning.model.Event
import jp.ats.kotlinlearning.service.EventService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("events")
class EventController(
    private val eventService: EventService
) {

    @GetMapping
    fun findEvents(): List<Event> = eventService.findEvents()

    @GetMapping("{id}")
    fun findEventById(@PathVariable id: Int) = eventService.findEventById(id)

    @GetMapping("{id}/participants")
    fun findEventsWithOrganizerAndParticipants(@PathVariable id: Int) = eventService.findEventsWithOrganizerAndParticipants(id)

}