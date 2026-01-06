package com.oliviermarteaux.a054_eventorias.di

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.oliviermarteaux.a054_eventorias.MainActivity
import io.cucumber.junit.WithJunitRule
import org.junit.Rule
import android.Manifest
import androidx.test.core.app.ApplicationProvider
import androidx.test.rule.GrantPermissionRule
import com.oliviermarteaux.a054_eventorias.EventoriasApplication
import io.cucumber.java.Before

@WithJunitRule
class ComposeRuleHolder {

    @Before
    fun setup() {
        val appContext = ApplicationProvider
            .getApplicationContext<EventoriasApplication>()

        appContext.eventoriasContainer = EventoriasTestContainer(appContext)
    }

    // ✅ 1️⃣ Grant permissions FIRST
    @get:Rule(order = 0)
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.POST_NOTIFICATIONS
        )

    // ✅ 2️⃣ Then start the Activity / Compose
    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()
}