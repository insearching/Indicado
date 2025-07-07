package com.serhiihrabas.indicado.data.mapper

import com.serhiihrabas.indicado.data.dto.EnergyReadingBody
import com.serhiihrabas.indicado.domain.PowerIndicator
import com.serhiihrabas.indicado.utils.dateSendFormatter

fun PowerIndicator.toDto(): EnergyReadingBody {
    return EnergyReadingBody(
        accountNumber = personalNumber,
        meterNumber = "08118161",
        energyKindCode = "A-",
        date = date.format(dateSendFormatter),
        originDetailId = 107,
        value = indicatorValue,
        note = lastName
    )
}
