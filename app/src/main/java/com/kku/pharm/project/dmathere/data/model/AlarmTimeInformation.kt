package com.kku.pharm.project.dmathere.data.model

class AlarmTimeInformation (
        var firstMed: String,
        var firstMedAmount: String,
        var secondMed: String ?= null,
        var secondMedAmount: String ?= null,
        var hour: Int,
        var minute: Int,
        var amPM: String,
        var timeDescription: String,
        var isRepeated: Boolean
)