package com.strato.hidrive.domain.background.jobs.pausable_job

interface PausableJob {
    val resumeType: ResumeType
    var pauseType: JobPauseType

    fun pause(type: JobPauseType)
}