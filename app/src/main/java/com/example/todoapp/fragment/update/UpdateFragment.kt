package com.example.todoapp.fragment.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.priorities_spinner
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import williamlopes.project.rtcontrol.helper.extension.toast

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val toDoViewModel: ToDoViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()
    private lateinit var updateData: ToDoData

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_save -> updateItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val title = current_title_et.text.toString()
        val priority = current_priorities_spinner.selectedItem.toString()
        val description = current_description_et.text.toString()
        val verify = sharedViewModel.verifyDataFromUser(title, description)

        if (verify){
            val currentItem = args.currentItem
            updateData = ToDoData(
                currentItem.id,
                title,
                sharedViewModel.parsePriority(priority),
                description
            )

            toDoViewModel.updateData(updateData)
            Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show()
            //context?.toast(getString(R.string.successfully_added))
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(context, "erro!", Toast.LENGTH_SHORT).show()
            //context?.toast(getString(R.string.please_fillout_fields))
        }
    }

}