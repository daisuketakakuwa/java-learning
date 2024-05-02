package jp.ats.kotlinlearning.model

import java.time.LocalDateTime

data class Event(
    val id: Long, // BigSerialだからLong
    val organizerId: Int, // SerialだからInt
    val eventName: String,
    val startsAt: LocalDateTime,
    val endsAt: LocalDateTime
)

data class EventWithParticipants(
    val id: Long, // BigSerialだからLong
    val eventName: String,
    val startsAt: LocalDateTime?,
    val endsAt: LocalDateTime?,
    val organizer: Organizer?,
    val participants: List<Participant>
) {
    // MyBatisは空のコンストラクタを利用してインスタンス生成するので定義必須
    constructor() : this(
        1L,
        "",
        null,null,null,
        // mutableなListでないとエラーとなる
        mutableListOf()
    )
}