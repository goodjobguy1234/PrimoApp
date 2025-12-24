package com.example.primohomepage.feature.home.adapter.viewholder

import android.text.Html
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.primohomepage.databinding.ItemArticleBinding
import com.example.primohomepage.domain.model.ArticleModel
import com.google.android.material.chip.Chip

class ArticleViewHolder(private val binding: ItemArticleBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(article: ArticleModel, onItemClick: (ArticleModel) -> Unit) {
        binding.headerTitleTv.text = article.title

        val cleanText =
            Html.fromHtml(article.descriptionHtmlText, Html.FROM_HTML_MODE_COMPACT).toString().trim()
        binding.descriptionTv.text = cleanText

        Glide.with(binding.root.context)
            .load(article.thumbnail)
            .centerCrop()
            .into(binding.imageViewArticle)

        binding.chipGroupCategories.apply {
            removeAllViews()
            article.categories.map { catagory ->
                Chip(context).apply {
                    this.text = catagory
                }
            }.forEach {
                addView(it)
            }
        }

        binding.root.setOnClickListener { onItemClick(article) }
    }
}