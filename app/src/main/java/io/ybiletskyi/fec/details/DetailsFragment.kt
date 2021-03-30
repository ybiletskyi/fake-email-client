package io.ybiletskyi.fec.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.common.fragment.BaseFragment
import io.ybiletskyi.fec.viewmodels.EmailDetailViewModel

class DetailsFragment : BaseFragment() {

    companion object {
        private const val EMAIL_ID = "email_id"

        fun newInstance(emilId: Int): DetailsFragment {
            return DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(EMAIL_ID, emilId)
                }
            }
        }
    }

    private lateinit var emailDetailViewModel: EmailDetailViewModel

    private lateinit var senderTextView: TextView
    private lateinit var dateTextView: TextView
    private lateinit var subjectTextView: TextView
    private lateinit var descriptionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        senderTextView = view.findViewById(R.id.sender)
        dateTextView = view.findViewById(R.id.date)
        subjectTextView = view.findViewById(R.id.subject)
        descriptionTextView = view.findViewById(R.id.description)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emailDetailViewModel = ViewModelProvider(this).get(EmailDetailViewModel::class.java)
        emailDetailViewModel.emailData.observe(viewLifecycleOwner, { emaildData ->
            senderTextView.text = String.format(getString(R.string.str_from), emaildData.sender)
            dateTextView.text = emaildData.date
            subjectTextView.text = String.format(getString(R.string.str_subject), emaildData.subject)
            descriptionTextView.text = emaildData.description
        })

        val emailId = requireArguments().getInt(EMAIL_ID)
        emailDetailViewModel.loadEmailDetail(emailId)
    }

    override fun getLayoutId(): Int = R.layout.fragment_details

    override fun getScreenSettings(): ScreenSettings = ScreenSettings.Details

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        emailDetailViewModel.emailData.value?.let {
            val buttons = MenuButtons.buildMenu(it.isDeleted, it.isViewed)
            buttons.forEach { menuItem ->
                menu.add(Menu.NONE, menuItem.ordinal, Menu.NONE, menuItem.titleRes).apply {
                    setIcon(menuItem.icRes)
                    setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
                }
            }
        }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MenuButtons.DELETE.ordinal -> {

                true
            }
            MenuButtons.MARK_READ.ordinal -> {

                true
            }
            MenuButtons.MARK_UNREAD.ordinal -> {

                true
            }
            else -> false
        }
    }
}