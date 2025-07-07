package com.serhiihrabas.indicado.presentation

import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.serhiihrabas.core.presentation.components.IndicadoButton
import com.serhiihrabas.core.presentation.components.IndicadoTextField
import com.serhiihrabas.core.presentation.theme.IndicadoTheme
import com.serhiihrabas.core.util.DeviceConfiguration
import com.serhiihrabas.indicado.R
import com.serhiihrabas.indicado.utils.dateDisplayFormatter
import com.serhiihrabas.indicado.utils.parseToZonedDateTime
import org.koin.androidx.compose.koinViewModel
import java.time.ZonedDateTime

@Composable
fun TernopilOblEnergyScreenRoot(
    viewModel: OblEnergyViewModel = koinViewModel<OblEnergyViewModel>(),
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is OblEnergyEvent.ShowMessage -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    TernopilOblEnergyScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun TernopilOblEnergyScreen(
    state: OblEnergyState,
    onAction: (OblEnergyAction) -> Unit,
) {
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
                        state = state,
                        onAction = onAction,
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
                        state = state,
                        onAction = onAction,
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
                        state = state,
                        onAction = onAction,
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
    state: OblEnergyState,
    onAction: (OblEnergyAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        IndicadoTextField(
            value = state.accountNumber,
            onValueChange = {
                onAction(OblEnergyAction.OnAccountNumberChange(it))
            },
            label = stringResource(R.string.personal_account_number_label),
            placeholder = "2*********",
            digitsOnly = true,
            maxLength = 10
        )
        IndicadoTextField(
            value = state.lastName,
            onValueChange = {
                onAction(OblEnergyAction.OnLastNameChange(it))
            },
            label = stringResource(R.string.last_name_cyrillic),
            placeholder = stringResource(R.string.last_name)
        )
        IndicadoTextField(
            value = state.indicatorValue,
            onValueChange = {
                onAction(OblEnergyAction.OnIndicatorValueChange(it))
            },
            label = stringResource(R.string.indicator_value),
            placeholder = "0",
            digitsOnly = true,
            maxLength = 6
        )
        IndicadoTextField(
            value = state.date.format(dateDisplayFormatter),
            onValueChange = { onAction(OblEnergyAction.OnDateChange(it.parseToZonedDateTime())) },
            label = stringResource(R.string.date_indicator_receipt),
            placeholder = ZonedDateTime.now().format(dateDisplayFormatter),
            isDateField = true,
            dateFormat = dateDisplayFormatter
        )
        Spacer(modifier = Modifier.height(24.dp))
        IndicadoButton(
            text = stringResource(R.string.send_indicators),
            onClick = { onAction(OblEnergyAction.OnSendIndicator) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TernopilOblEnergyScreenPreview() {
    IndicadoTheme {
        TernopilOblEnergyScreen(
            state = OblEnergyState(),
            onAction = {}
        )
    }
}