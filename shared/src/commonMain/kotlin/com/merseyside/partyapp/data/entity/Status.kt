package com.merseyside.partyapp.data.entity

enum class Status(val status: String) {
    IN_PROCESS("In Process"), COMPLETE("Complete");

    override fun toString(): String {
        return status
    }

    companion object {
        fun getStatusByString(status: String): Status? {
            values().forEach {
                if (it.status == status) {
                    return it
                }
            }

            return null
        }
    }

}