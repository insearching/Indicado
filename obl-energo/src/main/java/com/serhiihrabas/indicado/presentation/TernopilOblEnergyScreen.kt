package com.serhiihrabas.indicado.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiihrabas.core.presentation.components.IndicadoButton
import com.serhiihrabas.core.presentation.components.IndicadoTextField
import com.serhiihrabas.core.presentation.theme.IndicadoTheme
import com.serhiihrabas.core.util.DeviceConfiguration
import com.serhiihrabas.indicado.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.mm.yyyy")

@Composable
fun TernopilOblEnergyScreenRoot() {
    TernopilOblEnergyScreen()
}

@Composable
fun TernopilOblEnergyScreen() {
    var personalAccountNumber by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.statusBars
    ) { innerPadding ->
        val rootModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .clip(
                RoundedCornerShape(
                    topStart = 15.dp,
                    topEnd = 15.dp
                )
            )
            .background(MaterialTheme.colorScheme.surfaceContainerLowest)
            .padding(
                horizontal = 16.dp,
                vertical = 24.dp
            )
            .consumeWindowInsets(WindowInsets.navigationBars)

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)
        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT -> {
                Column(
                    modifier = rootModifier,
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    HeaderSection()
                    FormSection(
                        personalAccountNumber = personalAccountNumber,
                        onPersonalAccountNumberChange = { personalAccountNumber = it },
                        lastName = lastName,
                        onLastNameChange = { lastName = it },
                        date = date,
                        onDateChange = { date = it },
                        onIndicatorsSend = { /* TODO: Send indicators */ },
                        modifier = rootModifier
                    )
                }
            }

            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                Row(
                    modifier = rootModifier
                        .windowInsetsPadding(WindowInsets.displayCutout)
                        .padding(
                            horizontal = 32.dp
                        ),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    HeaderSection()
                    FormSection(
                        personalAccountNumber = personalAccountNumber,
                        onPersonalAccountNumberChange = { personalAccountNumber = it },
                        lastName = lastName,
                        onLastNameChange = { lastName = it },
                        date = date,
                        onDateChange = { date = it },
                        onIndicatorsSend = { /* TODO: Send indicators */ },
                        modifier = rootModifier
                    )
                }
            }

            DeviceConfiguration.TABLET_PORTRAIT,
            DeviceConfiguration.TABLET_LANDSCAPE,
            DeviceConfiguration.DESKTOP,
                -> {
                Column(
                    modifier = rootModifier
                        .verticalScroll(rememberScrollState())
                        .padding(top = 48.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    HeaderSection(
                        modifier = Modifier
                            .widthIn(max = 540.dp),
                        alignment = Alignment.CenterHorizontally
                    )
                    FormSection(
                        personalAccountNumber = personalAccountNumber,
                        onPersonalAccountNumberChange = { personalAccountNumber = it },
                        lastName = lastName,
                        onLastNameChange = { lastName = it },
                        date = date,
                        onDateChange = { date = it },
                        onIndicatorsSend = { /* TODO: Send indicators */ },
                        modifier = Modifier
                            .widthIn(max = 540.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HeaderSection(
    modifier: Modifier = Modifier,
    alignment: Alignment.Horizontal = Alignment.Start,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = alignment
    ) {
        Text(
            text = stringResource(R.string.send_electricity_indicators_title),
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = stringResource(R.string.enter_your_data_here_description),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun FormSection(
    personalAccountNumber: String,
    onPersonalAccountNumberChange: (String) -> Unit,
    lastName: String,
    onLastNameChange: (String) -> Unit,
    date: String,
    onDateChange: (String) -> Unit,
    onIndicatorsSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        IndicadoTextField(
            value = personalAccountNumber,
            onValueChange = onPersonalAccountNumberChange,
            label = stringResource(R.string.personal_account_number_label),
            placeholder = "2*********",
            digitsOnly = true,
            maxLength = 10
        )
        IndicadoTextField(
            value = lastName,
            onValueChange = onLastNameChange,
            label = stringResource(R.string.last_name_cyrillic),
            placeholder = stringResource(R.string.last_name)
        )
        IndicadoTextField(
            value = date,
            onValueChange = onDateChange,
            label = stringResource(R.string.date_indicator_receipt),
            placeholder = ZonedDateTime.now().format(dateTimeFormatter),
            isDateField = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        IndicadoButton(
            text = stringResource(R.string.send_indicators),
            onClick = onIndicatorsSend,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TernopilOblEnergyScreenPreview() {
    IndicadoTheme {
        TernopilOblEnergyScreen()
    }
}