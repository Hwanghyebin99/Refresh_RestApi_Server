package com.example.webapi.core.domain.model

import java.io.ByteArrayOutputStream

interface UpdatesStrategy {
    fun processUpdates(): ByteArrayOutputStream
}