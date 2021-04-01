package io.ybiletskyi.fec.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import io.ybiletskyi.fec.R
import io.ybiletskyi.fec.details.MenuButtons
import io.ybiletskyi.fec.main.ShortData

class EmailEditBottomSheet : BottomSheetDialogFragment() {

    companion object {
        private const val EMAIL = "email"

        fun newInstance(email: ShortData.EmailShortData): EmailEditBottomSheet {
            return EmailEditBottomSheet().apply {
                arguments = Bundle().apply {
                    putParcelable(EMAIL, email)
                }
            }
        }
    }

    var onItemClickListener: (MenuButtons) -> Unit = {}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_edit_email, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val email = requireArguments().getParcelable(EMAIL) as? ShortData.EmailShortData
        val dataSet = email?.let { MenuButtons.buildMenu(it.isDeleted, it.isActive) } ?: emptyArray()

        val recyclerView = view.findViewById<RecyclerView>(R.id.menu_list)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = MenuAdapter(dataSet) { selectedItem ->
            onItemClickListener(selectedItem)
            dismiss()
        }
    }
}