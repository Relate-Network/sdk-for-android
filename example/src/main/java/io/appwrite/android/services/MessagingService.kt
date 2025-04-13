package io.appwrite.android.services

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import io.appwrite.ID
import io.appwrite.services.Account
import kotlinx.coroutines.runBlocking

class MessagingService : FirebaseMessagingService() {

    companion object {
        var account: Account? = null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        val prefs = getSharedPreferences("example", MODE_PRIVATE)

        prefs.edit().putString("fcmToken", token).apply()

        if (account == null) {
            return
        }

        val targetId = prefs.getString("targetId", null)

        runBlocking {
            if (targetId == null) {
                val target = account!!.createPushTarget(ID.unique(), token)

                prefs.edit().putString("targetId", target.id).apply()
            } else {
                account!!.updatePushTarget(targetId, token)
            }
        }
    }
}