package jp.ats.kotlinlearning.model

import java.time.LocalDateTime

data class Event(
    val id: Long?, // BigSerialだからLong
    val organizerId: Int, // SerialだからInt
    val eventName: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime,
    val eventDetails: EventDetails?
)

data class EventWithParticipants(
    val id: Long?, // BigSerialだからLong
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

data class EventCreateRequest(
    val organizerId: Int, // SerialだからInt
    val eventName: String,
    val startsAt: String,
    val endsAt: String,
    val eventDetails: String?
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor() : this(
        0,
        "",
        "",
        "",
        ""
    )
}

data class EventDetails(
    val description: String,
    val address1: String,
    val address2: String
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor(): this("","","")
}

