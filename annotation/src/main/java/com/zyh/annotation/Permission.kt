package com.zyh.annotation

@Retention(value = AnnotationRetention.SOURCE)
annotation class Permission(
    val value: Int,
    val type: Int
)
