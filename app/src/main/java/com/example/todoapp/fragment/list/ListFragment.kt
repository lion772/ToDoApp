package com.example.todoapp.fragment.list

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoapp.R
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.fragment.SharedViewModel
import com.example.todoapp.fragment.update.UpdateFragment
import kotlinx.android.synthetic.main.dialog_progress.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.fragment_list.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import williamlopes.project.rtcontrol.helper.extension.toast


class ListFragment : Fragment() {

    private val toDoViewModel: ToDoViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        setUpListener(view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toDoViewModel.getAllData()
        setUpAdapter()
        setUpObservable()
    }

    private fun setUpObservable() {
        toDoViewModel.data.observe(viewLifecycleOwner) { dataList ->
            sharedViewModel.checkIfDatabaseEmpty(dataList)
            (recyclerView.adapter as ListAdapter).setData(dataList)
        }

        sharedViewModel.emptyDatabase.observe(viewLifecycleOwner) {isEmpty ->
            showEmptyDatabaseViews(isEmpty)
        }


        toDoViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            isLoading?.let {
                listProgressBar.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    no_data_textView.visibility = View.GONE
                    no_data_imageView.visibility = View.GONE
                }
            }
        }
    }

    private fun showEmptyDatabaseViews(emptyDatabase: Boolean) {
        if (emptyDatabase){
            view?.no_data_imageView?.visibility = View.VISIBLE
            view?.no_data_textView?.visibility = View.VISIBLE
        } else {
            view?.no_data_imageView?.visibility = View.GONE
            view?.no_data_textView?.visibility = View.GONE
        }
    }

    private fun setUpAdapter() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter()
        }
    }

    private fun setUpListener(view: View) {
        view.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmDelete()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDelete() {
        val builder = android.app.AlertDialog.Builder(context).also {
            it.setPositiveButton(YES) { _, _ ->
                toDoViewModel.deleteAllData()
                recyclerView.visibility = View.GONE

                context?.toast(getString(R.string.successfully_deleted))
            }.setNegativeButton(NO) { _, _ -> }
        }
        builder.apply {
            setTitle(R.string.delete_everything_question)
            setMessage(R.string.are_you_sure)
            create()
            show()
        }
    }

    companion object {
        private const val YES = "Yes"
        private const val NO = "No"
    }

}