package com.ionos.scanbot.upload.use_case

interface Uploader {
    fun upload(uploadFolder: String, pageList: List<String>)
}