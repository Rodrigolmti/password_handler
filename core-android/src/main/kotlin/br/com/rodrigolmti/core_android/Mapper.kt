package br.com.rodrigolmti.core_android

interface Mapper<in E, out T> {
    fun mapFrom(from: E): T
}