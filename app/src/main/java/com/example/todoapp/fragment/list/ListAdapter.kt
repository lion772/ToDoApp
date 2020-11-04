package com.example.todoapp.fragment.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.findFragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.data.models.Priority
import com.example.todoapp.data.models.ToDoData
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = arrayListOf<ToDoData>()

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun bind(position: Int){
            itemView.title_txt.text = dataList[position].title
            itemView.description_txt.text = dataList[position].description
            itemView.row_background.setOnClickListener{
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
                findNavController(it.findFragment()).navigate(action)


            }

            when(dataList[position].priority){
                Priority.HIGH -> itemView.priority_indicator.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.red))
                Priority.MEDIUM -> itemView.priority_indicator.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.yellow))
                Priority.LOW -> itemView.priority_indicator.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.green))
            }
        }
    }

    fun setData(toDoData: List<ToDoData>){
        this.dataList.clear()
        this.dataList = toDoData as ArrayList<ToDoData>
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))



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