package com.kku.pharm.project.dmathere.data.model

class AlarmTimeInformation (
        var id: String,
        var firstMed: String,
        var firstMedAmount: String,
        var secondMed: String ?= null,
        var secondMedAmount: String ?= null,
        var hour: Int,
        var minute: Int,
        var timeDescription: String,
        var isRepeated: Boolean,
        var status: String
)