package com.jpp.myhealthassistant.model

enum class Location(val value:Int)
{
    DEFAULT(-1),
    HEAD(1),
    LEFT_ARM(2),
    RIGHT_ARM(3),
    CHEST(4),
    STOMACH(5),
    LEFT_LEG(6),
    RIGHT_LEG(7),
    LEFT_HAND(8),
    RIGHT_HAND(9),
    LEFT_FOOT(10),
    RIGHT_FOOT(11);


    companion object {
        fun fromInt(value: Int) = Location.values().first { it.value == value }
    }
}