package com.jspj.shoppingassistant.adapter
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import com.jpp.myhealthassistant.model.ItemsViewModel
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jpp.myhealthassistant.R
import com.jpp.myhealthassistant.R.layout
import com.jpp.myhealthassistant.model.LanguageViewModel

class LanguageAdapter(private val mList: List<LanguageViewModel>) : RecyclerView.Adapter<LanguageAdapter.ViewHolder>() {

    public val data=mList;
    private var onClickListener: OnClickListener? = null
    private var onLongClickListener: OnLongClickListener?=null
    private lateinit var holder:ViewHolder;
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(layout.language_card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val LanguageViewModel = mList[position]
        this.holder=holder;

        holder.imageView.setImageResource(LanguageViewModel.image)
        holder.textView.setEllipsize(TextUtils.TruncateAt.MIDDLE)
        holder.textView.text = LanguageViewModel.text
       // val chk = holder.cardView.findViewById<ImageView>(R.id.imgCheck);
       /* if(ItemsViewModel.checked)
        {
            chk.setImageResource(R.drawable.check);
            val tw = holder.textView
            tw.paintFlags=tw.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tw.setTextColor(Color.DKGRAY)
        }*/

        holder.itemView.setOnClickListener {
            if (onClickListener != null) {
                onClickListener!!.onClick(position, LanguageViewModel )
            }
        }

        holder.itemView.setOnLongClickListener{
            onLongClickListener!!.onLongClick(position,LanguageViewModel)

        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCardBgColor(color:Int)
    {
        holder.itemView.setBackgroundColor(Color.CYAN);
    }

    // A function to bind the onclickListener.
    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    fun setOnLongClickListener(onLongClickListener: OnLongClickListener)
    {
        this.onLongClickListener=onLongClickListener;
    }

    // onClickListener Interface
    interface OnClickListener {
        fun onClick(position: Int, model: LanguageViewModel)
    }

    interface OnLongClickListener{
        fun onLongClick(position:Int,model:LanguageViewModel):Boolean
        {
            return true;
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imgFlag)
        val textView: TextView = itemView.findViewById(R.id.textView);
        val cardView:View = itemView;
    }
}