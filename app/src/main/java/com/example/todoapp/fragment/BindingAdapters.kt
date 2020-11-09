package com.example.todoapp.fragment

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.fragment.add.AddFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.row_layout.view.*

class BindingAdapters {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean) {
            view.setOnClickListener {
                if (navigate) {
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: LiveData<Boolean>) {
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.GONE
            }
        }

        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(spinner: Spinner, priority: Priority) {
            when (priority) {
                Priority.HIGH -> spinner.setSelection(0)
                Priority.MEDIUM -> spinner.setSelection(1)
                Priority.LOW -> spinner.setSelection(2)
            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(view: CardView, priority: Priority) {
            when (priority) {
                Priority.HIGH -> view.priority_indicator.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.red))
                Priority.MEDIUM -> view.priority_indicator.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.yellow))
                Priority.LOW -> view.priority_indicator.setCardBackgroundColor(ContextCompat.getColor(view.context, R.color.green))
            }
        }
    }
}