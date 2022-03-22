package com.fadlurahmanf.starter_app_mvp.ui.home.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.fadlurahmanf.starter_app_mvp.R
import com.fadlurahmanf.starter_app_mvp.core.extension.differenceWithNow
import com.fadlurahmanf.starter_app_mvp.core.extension.isoDateTimeToDate
import com.fadlurahmanf.starter_app_mvp.data.response.post.PostResponse
import com.fadlurahmanf.starter_app_mvp.databinding.ItemPostBinding

class PostAdapter(var listPost:ArrayList<PostResponse>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var binding: ItemPostBinding
    private lateinit var callBack: PostClickCallBack
    private lateinit var pdfAdapter:PdfAdapter
    private lateinit var context:Context

    fun setOnClickCallBack(postClickCallBack: PostClickCallBack){
        this.callBack = postClickCallBack
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        binding = ItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = listPost[position]
        val holder = holder as ViewHolder

        val listImageVideo = post.attachments?.filter { it -> it.type == "video" || it.type == "picture" }
        var listPdf = post.attachments?.filter { it -> it.type == "pdf" }

        holder.itemView.setOnClickListener {
            callBack.onPostClicked(post)
        }

        binding.headerPost.ivMore.setOnClickListener {
            callBack.onMoreClicked(post)
        }

        binding.headerPost.tvName.text = post.author?.firstName
        binding.headerPost.tvPostedTime.text = post.createdAt?.isoDateTimeToDate()?.differenceWithNow()
        binding.headerPost.ivMore.visibility = if (post.accessible == true) View.VISIBLE else View.GONE
        if (!post.content.isNullOrEmpty()){
            binding.tvContent.text = post.content
            binding.tvContent.visibility = View.VISIBLE
        }else{
            binding.tvContent.visibility = View.GONE
        }
        binding.attachmentPost.clAttachment.visibility = if (post.attachments != null && post.attachments?.isNotEmpty() == true) View.VISIBLE else View.GONE

        binding.reaction.tvLikeCount.text = "${post.reactions?.filter { it -> it.type == "like" }?.size}"
        if (post.reacted == true && post.reactedType == "like"){
            binding.reaction.ivFavorite.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.red))
            binding.reaction.tvLikeCount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }else{
            binding.reaction.ivFavorite.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.medium_grey))
            binding.reaction.tvLikeCount.setTextColor(ContextCompat.getColor(context, R.color.black_grey))
        }

        binding.reaction.tvPrayCount.text = "${post.reactions?.filter { it -> it.type == "pray" }?.size}"
        if (post.reacted == true && post.reactedType == "pray"){
            binding.reaction.tvPrayCount.setTextColor(ContextCompat.getColor(context, R.color.red))
        }else{
            binding.reaction.tvPrayCount.setTextColor(ContextCompat.getColor(context, R.color.black_grey))
        }

        binding.reaction.tvComment.text = "${post.comments?.size} ${if((post.comments?.size?:0) > 1) "comments" else "comment"}"

        var requestOptions = RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.grey_corner_10)
            .error(R.drawable.ic_broken_image)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)

        if (listImageVideo?.size == 1){
            Glide.with(binding.attachmentPost.iv1).load(listImageVideo[0].url).apply(requestOptions).into(binding.attachmentPost.iv1)
            binding.attachmentPost.ivPlay1.visibility = if (listImageVideo[0].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.rl1.visibility = View.VISIBLE
            binding.attachmentPost.rl2.visibility = View.GONE
            binding.attachmentPost.rl3.visibility = View.GONE
            binding.attachmentPost.rl4.visibility = View.GONE
        }else if (listImageVideo?.size == 2){
            Glide.with(binding.attachmentPost.iv1).load(listImageVideo[0].url).apply(requestOptions).into(binding.attachmentPost.iv1)
            Glide.with(binding.attachmentPost.iv2).load(listImageVideo[1].url).apply(requestOptions).into(binding.attachmentPost.iv2)
            binding.attachmentPost.ivPlay1.visibility = if (listImageVideo[0].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.ivPlay2.visibility = if (listImageVideo[1].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.rl1.visibility = View.VISIBLE
            binding.attachmentPost.rl2.visibility = View.VISIBLE
            binding.attachmentPost.rl3.visibility = View.GONE
            binding.attachmentPost.rl4.visibility = View.GONE
        }else if (listImageVideo?.size == 3){
            Glide.with(binding.attachmentPost.iv1).load(listImageVideo[0].url).apply(requestOptions).into(binding.attachmentPost.iv1)
            Glide.with(binding.attachmentPost.iv2).load(listImageVideo[1].url).apply(requestOptions).into(binding.attachmentPost.iv2)
            Glide.with(binding.attachmentPost.iv3).load(listImageVideo[2].url).apply(requestOptions).into(binding.attachmentPost.iv3)
            binding.attachmentPost.ivPlay1.visibility = if (listImageVideo[0].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.ivPlay2.visibility = if (listImageVideo[1].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.ivPlay3.visibility = if (listImageVideo[2].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.rl1.visibility = View.VISIBLE
            binding.attachmentPost.rl2.visibility = View.VISIBLE
            binding.attachmentPost.rl3.visibility = View.VISIBLE
            binding.attachmentPost.rl4.visibility = View.GONE
        }else if (listImageVideo?.size == 4){
            Glide.with(binding.attachmentPost.iv1).load(listImageVideo[0].url).apply(requestOptions).into(binding.attachmentPost.iv1)
            Glide.with(binding.attachmentPost.iv2).load(listImageVideo[1].url).apply(requestOptions).into(binding.attachmentPost.iv2)
            Glide.with(binding.attachmentPost.iv3).load(listImageVideo[2].url).apply(requestOptions).into(binding.attachmentPost.iv3)
            Glide.with(binding.attachmentPost.iv4).load(listImageVideo[3].url).apply(requestOptions).into(binding.attachmentPost.iv4)
            binding.attachmentPost.ivPlay1.visibility = if (listImageVideo[0].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.ivPlay2.visibility = if (listImageVideo[1].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.ivPlay3.visibility = if (listImageVideo[2].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.ivPlay4.visibility = if (listImageVideo[3].type == "video") View.VISIBLE else View.GONE
            binding.attachmentPost.rl1.visibility = View.VISIBLE
            binding.attachmentPost.rl2.visibility = View.VISIBLE
            binding.attachmentPost.rl3.visibility = View.VISIBLE
            binding.attachmentPost.rl4.visibility = View.VISIBLE
        }

        if (!listPdf.isNullOrEmpty()){
            binding.rvPdfFile.visibility = View.VISIBLE
            pdfAdapter = PdfAdapter(listPdf)
            binding.rvPdfFile.adapter = pdfAdapter
        }else{
            binding.rvPdfFile.visibility = View.GONE
        }

        binding.reaction.rlFavorite.setOnClickListener {
            callBack.onFavoriteClicked(post)
        }

        binding.reaction.rlPray.setOnClickListener {
            callBack.onPrayClicked(post)
        }
    }

    interface PostClickCallBack{
        fun onPostClicked(post:PostResponse?)
        fun onMoreClicked(post:PostResponse?)
        fun onFavoriteClicked(post: PostResponse?)
        fun onPrayClicked(post:PostResponse?)
    }

    override fun getItemCount(): Int {
        return listPost.size
    }
}