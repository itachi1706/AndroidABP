package me.jfenn.gitrest.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Custom kotlin.String serializer that returns empty/blank strings as null.
 */
open class OptionalStringSerializer : KSerializer<String?> {

    final override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("kotlin.OptionalString", PrimitiveKind.STRING)
    private val valueSerializer = String.serializer()

    final override fun deserialize(decoder: Decoder): String? {
        val str = valueSerializer.deserialize(decoder)
        return if (str.isNotBlank()) str else null
    }

    final override fun serialize(encoder: Encoder, value: String?) {
        value?.let { valueSerializer.serialize(encoder, it) }
    }

}