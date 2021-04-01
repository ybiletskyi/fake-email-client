package io.ybiletskyi.fec.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import io.ybiletskyi.domain.Email
import io.ybiletskyi.fec.App
import io.ybiletskyi.fec.MainActivity
import java.util.concurrent.ThreadLocalRandom

class EmailCreator(private val context: Context) {

    /**
     * for display
     */
    private val id = "Fake Email Client Channel"
    private val name = "Fake Email Client Channel"
    private val description = "Channel will notify about new email receiving"
    private val vibrationSettings = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
    private val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * for generation
     */
    private val min = 100 // local generated id will be start from this value
    private val max = 1000 + 1
    private val random = ThreadLocalRandom.current()

    fun generateRandomEmail(): Email {
        val randomId = random.nextInt(min, max)
        val unixTime = System.currentTimeMillis() / 1000L
        val emailText = List(randomId) { index -> "user$randomId pushed commit -- c1b1d59$index" }.joinToString(separator = "\n")
        return Email(
            id = randomId,
            time = unixTime,
            from = "GitHub Fork #$randomId",
            subject = "New PR #$randomId was raised by user$randomId",
            text = emailText,
            isRead = false,
            isDeleted = false
        )
    }

    fun createNotification(email: Email) {
        val notifyId = email.id
        val title = "New email from: ${email.from}"
        val body = "Email subject: ${email.subject}"

        createNotification(notifyId, title, body)
    }

    private fun createNotification(notifyId: Int, title: String, body: String) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(App.context, 0, intent, 0)

        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var mChannel = manager.getNotificationChannel(id)
            if (mChannel == null) {
                mChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH)
                mChannel.description = description
                mChannel.enableVibration(true)
                mChannel.lightColor = Color.GREEN
                mChannel.vibrationPattern = vibrationSettings
                manager.createNotificationChannel(mChannel)
            }

            NotificationCompat.Builder(context, id)
        } else {
            NotificationCompat.Builder(context)
                .setPriority(Notification.PRIORITY_HIGH)
        }

        builder.setContentTitle(title)
            .setSmallIcon(android.R.drawable.ic_popup_reminder)
            .setContentText(body)
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setTicker(title)
            .setVibrate(vibrationSettings)

        val notification = builder.build()
        manager.notify(notifyId, notification)
    }
}