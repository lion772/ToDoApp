package com.example.todoapp.fragment.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.ToDoData
import com.example.todoapp.databinding.RowLayoutBinding
import com.example.todoapp.fragment.list.ListFragmentDirections
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyArray<ToDoData>()

    inner class MyViewHolder(var binding:RowLayoutBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.toDoData = dataList[position]
            binding.executePendingBindings()

            itemView.row_background.setOnClickListener{
                val action =
                    ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
                findNavController(it.findFragment()).navigate(action)
            }
        }
    }

    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData.toTypedArray()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MyViewHolder(DataBindingUtil.inflate(inflater, R.layout.row_layout, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) = holder.bind(position)

    override fun getItemCount() = dataList.size

    /* class CouponAdapter(private var listaCupom: ArrayList<CouponElement>) : RecyclerView.Adapter<CouponAdapter.CouponViewHolder>() {

         var onClickTest: ((CouponElement) -> Unit)? = null

         override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
             CouponViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_discount_coupon, parent, false))


         override fun getItemCount() = listaCupom.size

         override fun onBindViewHolder(holder: CouponViewHolder, position: Int) = holder.bind(position)

         fun setCoupons(list: List<CouponElement>) {
             listaCupom.clear()
             listaCupom.addAll(list)
             notifyDataSetChanged()
         }

         inner class CouponViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

             init {
                 itemView.setOnClickListener {
                     onClickTest?.invoke(listaCupom[adapterPosition])
                 }
             }

             fun bind(position: Int) {
                 val cupom = listaCupom[position]
                 val imageColor = itemView.findViewById<ImageView>(R.id.imStatus)
                 val textName = itemView.findViewById<TextView>(R.id.tvDiscount)
                 val textDiscountValue = itemView.findViewById<TextView>(R.id.tvDiscountValue)
                 val textMinimumPurchase = itemView.findViewById<TextView>(R.id.tvMinimumPurchaseAmount)
                 val textCouponExpiration = itemView.findViewById<TextView>(R.id.tvCouponExpiration)

                 if (cupom.couponStatus == "OPENED")
                     imageColor.setImageResource(R.drawable.green_ellipse)
                 else
                     imageColor.setImageResource(R.drawable.red_ellipse)

                 textName.text = cupom.name
                 if (cupom.isPercentDiscountApplied) {
                     textDiscountValue.text = Mask.formatarPercentualPre(cupom.totalAmount)
                 } else {
                     textDiscountValue.text = cupom.totalAmount.toString()
                 }
                 textMinimumPurchase.text = cupom.minimumPurchaseAmount.toString()
                 textCouponExpiration.text = DateUtils.getDateFormated(cupom.expirationDate)

             }
         }

     }*/
}