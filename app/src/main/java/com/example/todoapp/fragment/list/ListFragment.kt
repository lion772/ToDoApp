package com.example.todoapp.fragment.list

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.data.viewmodel.ToDoViewModel
import com.example.todoapp.databinding.FragmentListBinding
import com.example.todoapp.fragment.SharedViewModel
import com.example.todoapp.fragment.list.adapter.ListAdapter
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import williamlopes.project.rtcontrol.helper.extension.toast


class ListFragment : Fragment() {

    private val toDoViewModel: ToDoViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()
    private var dataBinding: FragmentListBinding? = null
    private val listAdapter by lazy { dataBinding?.recyclerView?.adapter as ListAdapter }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = FragmentListBinding.inflate(inflater, container, false)
        dataBinding?.let {
            it.lifecycleOwner = this
            it.sharedViewModel = sharedViewModel
        }
        setHasOptionsMenu(true)
        return dataBinding?.root
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

        toDoViewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            listProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            if (isLoading) {
                no_data_textView.visibility = View.GONE
                no_data_imageView.visibility = View.GONE
            }
        }
    }

    private fun setUpAdapter() {
        dataBinding?.recyclerView?.let { recycler ->
            recycler.layoutManager =
                LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            recycler.adapter = ListAdapter()
            swipeToDelete(recyclerView)
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

    private fun swipeToDelete(recyclerView: RecyclerView?) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = listAdapter.dataList[viewHolder.adapterPosition]
                toDoViewModel.deleteData(deletedItem)
                retoreDeletedData(viewHolder.itemView, deletedItem, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun retoreDeletedData(view: View, deletedItem: ToDoData, position: Int) {
        val snackbar = Snackbar.make(view, "Deleted ${deletedItem.title}", Snackbar.LENGTH_LONG)
        snackbar.setAction(UNDO) {
            toDoViewModel.insertData(deletedItem)
            listAdapter.notifyItemChanged(position)
        }
        snackbar.show()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        dataBinding = null
    }

    companion object {
        private const val YES = "Yes"
        private const val NO = "No"
        private const val UNDO = "Undo"
    }

}