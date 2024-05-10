package com.strato.hidrive.domain.entity

enum class FileCategory(val category: String) {
    ARCHIVE("archive"),
    AUDIO("audio"),
    CODE("code"),
    IMAGE("image"),
    PDF("pdf"),
    SLIDE("slide"),
    SOFTWARE("software"),
    SYSTEM("system"),
    SPREADSHEET("spreadsheet"),
    DOCUMENT("office"),
    VIDEO("video"),
    OTHER("other")
}