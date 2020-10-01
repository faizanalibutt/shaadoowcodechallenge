package com.test.shaadoow.util

object Utils {

    val verticalListData = arrayOf("mujahid", "akbar", "lahore", "marwa", "urwa")

    val horizontalListData = arrayOf(
        android.R.drawable.ic_btn_speak_now,
        android.R.drawable.ic_delete, android.R.drawable.ic_media_next,
        android.R.drawable.ic_menu_camera, android.R.drawable.ic_dialog_alert,
        android.R.drawable.ic_media_ff
    )

    fun toShortCutNumber(j: Long): String {
        val j2: Long
        val j3: Long
        val j4: Long
        val j5: Long
        val j6 = 1000.toLong()
        if (j > j6) {
            j3 = j / j6
            j2 = j % j6 / 100.toLong()
        } else {
            j3 = 0
            j2 = 0
        }
        if (j / j6 > j6) {
            val j7 = 1000000.toLong()
            j4 = j / j7
            j5 = j % j7 / 100000.toLong()
        } else {
            j5 = 0
            j4 = 0
        }
        val i = if (j4 > 0) 1 else if (j4 == 0L) 0 else -1
        if (i == 0 && j3 == 0L) {
            return j.toString()
        }
        if (i > 0) {
            val sb = StringBuilder()
            sb.append(j4)
            sb.append('.')
            sb.append(j5)
            sb.append(" M")
            return sb.toString()
        }
        val sb2 = StringBuilder()
        sb2.append(j3)
        sb2.append('.')
        sb2.append(j2)
        sb2.append(" K")
        return sb2.toString()
    }

}