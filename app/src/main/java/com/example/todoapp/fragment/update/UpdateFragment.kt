package com.example.todoapp.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import williamlopes.project.rtcontrol.helper.extension.toast

class UpdateFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private val toDoViewModel: ToDoViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()
    private lateinit var updateData: ToDoData
    private lateinit var deleteData: ToDoData

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
            R.id.menu_delete -> deleteItemSelected()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteItemSelected() {
        val builder = AlertDialog.Builder(context).also {
            it.setPositiveButton(YES) {_,_ ->
                toDoViewModel.deleteData(args.currentItem)
                context?.toast(getString(R.string.successfully_added))
                findNavController().navigate(R.id.action_updateFragment_to_listFragment)
            }.setNegativeButton(NO) { _, _ ->}
        }
        builder.apply {
            setTitle("Delete '${args.currentItem.title}' ?")
            setMessage("Are you sure you want to delete '${args.currentItem.title}' ?")
            create()
            show()
        }
    }

    private fun updateItem() {
        val (title, priority, description) = collectUserFromUpdateLayout()
        val verify = sharedViewModel.verifyDataFromUser(title, description)

        if (verify){
            val currentItem = args.currentItem
            updateData = ToDoData(currentItem.id, title, sharedViewModel.parsePriority(priority), description)

            toDoViewModel.updateData(updateData)
            context?.toast(getString(R.string.successfully_added))
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            context?.toast(getString(R.string.please_fillout_fields))
        }
    }

    private fun collectUserFromUpdateLayout(): Triple<String, String, String> {
        val title = current_title_et.text.toString()
        val priority = current_priorities_spinner.selectedItem.toString()
        val description = current_description_et.text.toString()
        return Triple(title, priority, description)
    }

    companion object{
        private const val YES = "Yes"
        private const val NO = "No"
    }

}