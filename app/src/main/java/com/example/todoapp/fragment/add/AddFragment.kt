package com.example.todoapp.fragment.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragment.SharedViewModel
import kotlinx.android.synthetic.main.fragment_add.*
import kotlinx.android.synthetic.main.fragment_add.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class AddFragment : Fragment() {

    private val toDoViewModel: ToDoViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()
    private lateinit var newData: ToDoData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add, container, false)
        setHasOptionsMenu(true)
        view.priorities_spinner.onItemSelectedListener = sharedViewModel.listener
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val title = title_et.text.toString()
        val priority = priorities_spinner.selectedItem.toString()
        val description = description_et.text.toString()
        val validation = sharedViewModel.verifyDataFromUser(title, description)

        if (validation) {
            newData = ToDoData(0, title, sharedViewModel.parsePriority(priority), description)
            toDoViewModel.insertData(newData)
            Toast.makeText(activity, getString(R.string.successfully_added), Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)

        }else{
            Toast.makeText(activity, getString(R.string.please_fillout_fields), Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        const val HIGH_PRIORITY = "High Priority"
        const val MEDIUM_PRIORITY = "Medium Priority"
        const val LOW_PRIORITY = "Low Priority"
        const val FIRST_POSITION = 0
        const val SECOND_POSITION = 1
        const val THIRD_POSITION = 2
    }


}