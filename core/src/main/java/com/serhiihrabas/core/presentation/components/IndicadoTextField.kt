package com.serhiihrabas.core.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.serhiihrabas.core.presentation.theme.IndicadoTheme
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndicadoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    modifier: Modifier = Modifier,
    supportText: String? = null,
    errorText: String? = null,
    isPassword: Boolean = false,
    digitsOnly: Boolean = false,
    maxLength: Int = Int.MAX_VALUE,
    isDateField: Boolean = false,
    dateFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
) {
    val focusRequester = remember { FocusRequester() }
    var passwordVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    val isError by remember(errorText, isFocused) {
        derivedStateOf {
            errorText?.isBlank() == false && !isFocused
        }
    }

    // Date picker dialog
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            val selectedDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneId.systemDefault())
                                .toLocalDate()
                            onValueChange(selectedDate.format(dateFormat))
                        }
                        showDatePicker = false
                    }
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .onFocusChanged { focusState ->
                    isFocused = focusState.hasFocus
                    // Show date picker when field gains focus and it's a date field
                    if (focusState.hasFocus && isDateField) {
                        showDatePicker = true
                    }
                }
                .focusRequester(focusRequester)
                .then(
                    if (isDateField) {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null // Remove ripple effect
                        ) {
                            showDatePicker = true
                        }
                    } else Modifier
                ),
            value = value,
            onValueChange = { newValue ->
                if (isDateField) {
                    // Prevent keyboard input for date fields
                    return@OutlinedTextField
                }
                val filtered = if (digitsOnly) newValue.filter(Char::isDigit) else newValue
                onValueChange(filtered.take(maxLength))
            },
            placeholder = {
                Text(
                    text = placeholder,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            supportingText = {
                when {
                    errorText?.isNotBlank() == true && !isFocused -> {
                        Text(
                            text = errorText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                    supportText?.isNotBlank() == true && (isFocused || isDateField) -> {
                        Text(
                            text = supportText,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            },
            isError = isError,
            singleLine = true,
            readOnly = isDateField,
            keyboardOptions = if (isDateField) {
                KeyboardOptions.Default.copy(keyboardType = KeyboardType.Unspecified)
            } else if (digitsOnly) {
                KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            } else {
                KeyboardOptions.Default
            },
            visualTransformation = if (isPassword && !passwordVisible) {
                PasswordVisualTransformation('*')
            } else {
                VisualTransformation.None
            },
            trailingIcon = {
                when {
                    isDateField -> {
                        IconButton(onClick = { showDatePicker = true }) {
                            Icon(
                                imageVector = Icons.Default.DateRange,
                                contentDescription = "Select date",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    isPassword -> {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            },
            textStyle = MaterialTheme.typography.bodyLarge.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.Transparent,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                focusedBorderColor = MaterialTheme.colorScheme.primary,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorBorderColor = MaterialTheme.colorScheme.error,
                cursorColor = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
            ),
            label = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IndicadoTextFieldPreview() {
    IndicadoTheme {
        IndicadoTextField(
            value = "Input",
            onValueChange = {},
            label = "Label",
            placeholder = "Placeholder",
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun IndicadoTextFieldErrorPreview() {
    IndicadoTheme {
        IndicadoTextField(
            value = "",
            onValueChange = {},
            label = "Label",
            placeholder = "Placeholder",
            supportText = "Support text",
            errorText = "Error text"
        )
    }
}