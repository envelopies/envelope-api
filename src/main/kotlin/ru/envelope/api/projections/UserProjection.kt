package ru.envelope.api.projections

interface UserProjection {
    fun getId(): Long
    fun getUsername(): String
    fun isVerified(): Boolean
    fun getAuthority(): String
}
