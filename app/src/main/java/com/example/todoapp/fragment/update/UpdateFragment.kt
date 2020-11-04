package com.example.todoapp.fragment.update

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_update, container, false)
        setupBundleArgs(view)
        view.current_priorities_spinner.onItemSelectedListener = sharedViewModel.listener
        setHasOptionsMenu(true)
        return view
    }

    private fun setupBundleArgs(view: View) {
        view.current_title_et.setText(args.currentItem.title)
        view.current_description_et.setText(args.currentItem.description)
        view.current_priorities_spinner.setSelection(args.currentItem.priority.ordinal)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

}