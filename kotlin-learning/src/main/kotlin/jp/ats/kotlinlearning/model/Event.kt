package jp.ats.kotlinlearning.model

import java.time.LocalDateTime

data class EventDetails constructor(
    val description: String,
    val address1: String,
    val address2: String
) {
    constructor(): this("","","")
}

data class Event(
    val id: Long, // BigSerialだからLong
    val organizerId: Int, // SerialだからInt
    val eventName: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val eventDetails: EventDetails
)

data class EventWithParticipants(
    val id: Long, // BigSerialだからLong
    val eventName: String,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val eventDetails: EventDetails?,
    val organizer: Organizer?,
    val participants: List<Participant>
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor() : this(
        1L,
        "",
        null,null,null,null,
        // mutableなListでないとエラーとなる
        mutableListOf()
    )
}