package com.strato.hidrive.api.connection.converter

import okhttp3.ResponseBody
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

class ConverterFactory : Converter.Factory() {

    private val converterFactoryJSON = GsonConverterFactory.create()
    private val converterFactoryXML = SimpleXmlConverterFactory.createNonStrict(
        Persister(AnnotationStrategy())
    )

    @XmlResponse
    override fun responseBodyConverter(
        type: java.lang.reflect.Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val factory = annotations.firstOrNull { it.annotationClass == XmlResponse::class }
            ?.let { converterFactoryXML } ?: converterFactoryJSON

        return factory.responseBodyConverter(type, annotations, retrofit)
    }
}