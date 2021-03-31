package io.ybiletskyi.fec.details

import io.ybiletskyi.fec.R

enum class MenuButtons(val titleRes: Int, val icRes: Int) {
    DELETE(R.string.str_delete, R.drawable.ic_delete),
    RESTORE(R.string.str_restore, R.drawable.ic_restore),
    MARK_READ(R.string.str_mark_read, R.drawable.ic_mark_email_read),
    MARK_UNREAD(R.string.str_mark_unread, R.drawable.ic_mark_email_unread);

    companion object {
        fun buildMenu(isDeleted: Boolean, isRead: Boolean): Array<MenuButtons> {
            return mutableListOf<MenuButtons>().apply {
                if (isRead) {
                    add(MARK_UNREAD)
                } else {
                    add(MARK_READ)
                }

                if (isDeleted) {
                    add(RESTORE)
                } else {
                    add(DELETE)
                }

            }.toTypedArray()
        }
    }
}