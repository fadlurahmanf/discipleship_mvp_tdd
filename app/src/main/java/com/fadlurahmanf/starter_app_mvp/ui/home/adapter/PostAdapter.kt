package com.fadlurahmanf.starter_app_mvp.ui.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.facebook.shimmer.Shimmer
import com.facebook.shimmer.ShimmerDrawable
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ItemPostBinding

class PostAdapter(var listPost:ArrayList<PostResponse>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ItemPostBinding
    private lateinit var postClickCallBack: PostClickCallBack

    fun setOnClickCallBack(postClickCallBack: PostClickCallBack){
        this.postClickCallBack = postClickCallBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var post = listPost[position]
        var holder = holder as ViewHolder

        holder.itemView.setOnClickListener {
            postClickCallBack.onPostClicked(post)
        }

        binding.headerPost.ivMore.setOnClickListener {
            postClickCallBack.onMoreClicked(post)
        }

        val shimmer = Shimmer.ColorHighlightBuilder()
            .setBaseColor(ContextCompat.getColor(binding.root.context, R.color.teal_200))
            .setBaseAlpha(0.7f)
            .setHighlightAlpha(0.7f)
            .setHighlightColor(ContextCompat.getColor(binding.root.context, R.color.purple_700))
            .setDuration(1800)
            .setDirection(Shimmer.Direction.LEFT_TO_RIGHT)
            .setAutoStart(true)
            .build()

        val shimmerDrawable = ShimmerDrawable().apply {
            setShimmer(shimmer)
        }


        binding.headerPost.tvName.text = post.author?.firstName
        binding.headerPost.tvPostedTime.text = post.createdAt
        binding.headerPost.ivMore.visibility = if (post.accessible == true) View.VISIBLE else View.GONE
        binding.tvContent.text = post.content?:""
        binding.attachmentPost.clAttachment.visibility = if (post.attachments != null && post.attachments?.isNotEmpty() == true) View.VISIBLE else View.GONE

        var requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(shimmerDrawable)
            .error(R.drawable.grey_corner_10)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        if (post.attachments?.size == 1){
            Glide.with(binding.attachmentPost.iv1).load(post.attachments?.get(0)?.url).apply(requestOptions).into(binding.attachmentPost.iv1)
            binding.attachmentPost.iv1.visibility = View.VISIBLE
            binding.attachmentPost.iv2.visibility = View.GONE
            binding.attachmentPost.iv3.visibility = View.GONE
            binding.attachmentPost.iv4.visibility = View.GONE
        }else if (post.attachments?.size == 2){
            Glide.with(binding.attachmentPost.iv1).load(post.attachments?.get(0)?.url).placeholder(shimmerDrawable)
                .error(shimmerDrawable)
                .apply(requestOptions).into(binding.attachmentPost.iv1)
            Glide.with(binding.attachmentPost.iv2).load(post.attachments?.get(1)?.url).apply(requestOptions).into(binding.attachmentPost.iv2)
            binding.attachmentPost.iv1.visibility = View.VISIBLE
            binding.attachmentPost.iv2.visibility = View.VISIBLE
            binding.attachmentPost.iv3.visibility = View.GONE
            binding.attachmentPost.iv4.visibility = View.GONE
        }else if (post.attachments?.size == 3){
            Glide.with(binding.attachmentPost.iv1).load(post.attachments?.get(0)?.url).apply(requestOptions).into(binding.attachmentPost.iv1)
            Glide.with(binding.attachmentPost.iv2).load(post.attachments?.get(1)?.url).apply(requestOptions).into(binding.attachmentPost.iv2)
            Glide.with(binding.attachmentPost.iv3).load(post.attachments?.get(2)?.url).apply(requestOptions).into(binding.attachmentPost.iv3)
            binding.attachmentPost.iv1.visibility = View.VISIBLE
            binding.attachmentPost.iv2.visibility = View.VISIBLE
            binding.attachmentPost.iv3.visibility = View.VISIBLE
            binding.attachmentPost.iv4.visibility = View.GONE
        }else if (post.attachments?.size == 4){
            Glide.with(binding.attachmentPost.iv1).load(post.attachments?.get(0)?.url).apply(requestOptions).into(binding.attachmentPost.iv1)
            Glide.with(binding.attachmentPost.iv2).load(post.attachments?.get(1)?.url).apply(requestOptions).into(binding.attachmentPost.iv2)
            Glide.with(binding.attachmentPost.iv3).load(post.attachments?.get(2)?.url).apply(requestOptions).into(binding.attachmentPost.iv3)
            Glide.with(binding.attachmentPost.iv4).load(post.attachments?.get(3)?.url).apply(requestOptions).into(binding.attachmentPost.iv4)
            binding.attachmentPost.iv1.visibility = View.VISIBLE
            binding.attachmentPost.iv2.visibility = View.VISIBLE
            binding.attachmentPost.iv3.visibility = View.VISIBLE
            binding.attachmentPost.iv4.visibility = View.VISIBLE
        }
    }

    interface PostClickCallBack{
        fun onPostClicked(post:PostResponse?)
        fun onMoreClicked(post:PostResponse?)
    }

    override fun getItemCount(): Int {
        return listPost.size
    }
}