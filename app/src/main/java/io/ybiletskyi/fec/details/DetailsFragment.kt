package io.ybiletskyi.fec.details

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.common.ScreenSettings
import io.ybiletskyi.fec.common.fragment.BaseFragment
import io.ybiletskyi.fec.viewmodels.EmailDetailViewModel
import io.ybiletskyi.fec.viewmodels.EmailDetailViewModelFactory

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
    private val menuButtons = mutableListOf<MenuButtons>()

    private lateinit var emailContentView: View
    private lateinit var progressView: View
    private lateinit var errorTextView: TextView

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

        emailContentView = view.findViewById(R.id.email_content)
        progressView = view.findViewById(R.id.progress)
        errorTextView = view.findViewById(R.id.error)

        senderTextView = view.findViewById(R.id.sender)
        dateTextView = view.findViewById(R.id.date)
        subjectTextView = view.findViewById(R.id.subject)
        descriptionTextView = view.findViewById(R.id.description)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val emailId = requireArguments().getInt(EMAIL_ID)
        emailDetailViewModel = ViewModelProvider(this, EmailDetailViewModelFactory(emailId)).get(EmailDetailViewModel::class.java)
        emailDetailViewModel.emailData.observe(viewLifecycleOwner, { emailData ->
            when (emailData) {
                is DetailData.Loading -> handleLoading()
                is DetailData.InfoMessage -> handleError(emailData)
                is DetailData.EmailDetail -> handleData(emailData)
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.fragment_details

    override fun getScreenSettings(): ScreenSettings = ScreenSettings.Details

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        menu.clear()

        menuButtons.forEach { menuItem ->
            menu.add(Menu.NONE, menuItem.ordinal, Menu.NONE, menuItem.titleRes).apply {
                setIcon(menuItem.icRes)
                setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            MenuButtons.DELETE.ordinal -> {
                progressView.visibility = View.VISIBLE
                emailDetailViewModel.changeFolder(isDeleted = true)
                true
            }
            MenuButtons.RESTORE.ordinal -> {
                progressView.visibility = View.VISIBLE
                emailDetailViewModel.changeFolder(isDeleted = false)
                true
            }
            MenuButtons.MARK_READ.ordinal -> {
                progressView.visibility = View.VISIBLE
                emailDetailViewModel.changeState(isRead = true)
                true
            }
            MenuButtons.MARK_UNREAD.ordinal -> {
                progressView.visibility = View.VISIBLE
                emailDetailViewModel.changeState(isRead = false)
                true
            }
            else -> false
        }
    }

    private fun handleLoading() {
        emailContentView.visibility = View.GONE
        errorTextView.visibility = View.GONE
        progressView.visibility = View.VISIBLE
    }

    private fun handleError(error: DetailData.InfoMessage) {
        emailContentView.visibility = View.GONE
        progressView.visibility = View.GONE
        errorTextView.visibility = View.VISIBLE

        // show error message
        errorTextView.text = error.message

        // update menu
        menuButtons.clear()
        getMainActivity().invalidateOptionsMenu()
    }

    private fun handleData(emailData: DetailData.EmailDetail) {
        errorTextView.visibility = View.GONE
        progressView.visibility = View.GONE
        emailContentView.visibility = View.VISIBLE

        // update email content
        senderTextView.text = String.format(getString(R.string.str_from), emailData.sender)
        dateTextView.text = emailData.date
        subjectTextView.text = String.format(getString(R.string.str_subject), emailData.subject)
        descriptionTextView.text = emailData.description

        // update menu
        menuButtons.clear()
        menuButtons.addAll(MenuButtons.buildMenu(emailData.isDeleted, emailData.isRead))
        getMainActivity().invalidateOptionsMenu()
    }
}