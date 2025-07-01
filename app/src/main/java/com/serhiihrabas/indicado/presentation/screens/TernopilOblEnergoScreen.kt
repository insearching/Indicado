package com.serhiihrabas.indicado.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiihrabas.indicado.R
import com.serhiihrabas.indicado.presentation.components.IndicadoTextField
import com.serhiihrabas.indicado.ui.theme.IndicadoTheme
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.mm.yyyy")

@Composable
fun TernopilOblEnergoScreenRoot(modifier: Modifier = Modifier) {
    TernopilOblEnergoScreen(modifier = modifier)
}

@Composable
fun TernopilOblEnergoScreen(modifier: Modifier = Modifier) {
    var personalAccountNumber by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }


    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        IndicadoTextField(
            value = personalAccountNumber,
            onValueChange = { personalAccountNumber = it },
            label = "Особовий рахунок: 10 символів 2 (код району) (особовий рахунок)",
            placeholder = "2*********"
        )
        IndicadoTextField(
            value = lastName,
            onValueChange = { lastName = it },
            label = "Прізвище (кирилицею)",
            placeholder = stringResource(R.string.last_name)
        )
        IndicadoTextField(
            value = date,
            onValueChange = { date = it },
            label = stringResource(R.string.date_indicator_receipt),
            placeholder = ZonedDateTime.now().format(dateTimeFormatter)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TernopilOblEnergoScreenPreview() {
    IndicadoTheme {
        TernopilOblEnergoScreen()
    }
}